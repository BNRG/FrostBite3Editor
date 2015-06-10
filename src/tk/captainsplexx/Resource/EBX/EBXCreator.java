package tk.captainsplexx.Resource.EBX;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;

public class EBXCreator {
	
	private EBXHeader header;
	private ArrayList<Byte> headerBytes;
//	private FileSeeker headerByteSeeker;
	
	private ArrayList<EBXExternalGUID> externalGUIDs;
	private ArrayList<Byte> externalGUIDBytes;
//	private FileSeeker externalGUIDByteSeeker;
	
	private ArrayList<String> names;
	private ArrayList<Byte> nameBytes;
//	private FileSeeker nameByteSeeker;
	
	private ArrayList<EBXFieldDescriptor> fieldDescriptors;
	private ArrayList<Byte> fieldDescriptorBytes;
//	private FileSeeker fieldDescriptorByteSeeker;	
	
	private ArrayList<EBXComplexDescriptor> complexDescriptors;
	private ArrayList<Byte> complexDescriptorBytes;
//	private FileSeeker complexDescriptorByteSeeker;
	
	private ArrayList<EBXInstanceRepeater> instanceRepeaters;
	private ArrayList<Byte> instanceRepeaterBytes;
//	private FileSeeker instanceRepeaterByteSeeker;
	
	private ArrayList<EBXArrayRepeater> arrayRepeaters;
	private ArrayList<Byte> arrayRepeaterBytes;
//	private FileSeeker arrayRepeaterByteSeeker;
	
	private ArrayList<String> strings;
	private ArrayList<Byte> stringBytes;
//	private FileSeeker stringByteSeeker;
	
	private ArrayList<Byte> payloadData;
//	private FileSeeker payloadDataSeeker;
	
	private ArrayList<ArrayList<Byte>> finalDataArrays;
	
	private ArrayList<Byte> filler = new ArrayList<>();
	
	private boolean firstRun = true;
	
	public void init(){
		firstRun = false;
		
		finalDataArrays = new ArrayList<>();
		
		headerBytes = new ArrayList<>();
		externalGUIDBytes = new ArrayList<>();
		nameBytes = new ArrayList<>();
		fieldDescriptorBytes = new ArrayList<>();
		complexDescriptorBytes = new ArrayList<>();
		instanceRepeaterBytes = new ArrayList<>();
		arrayRepeaterBytes = new ArrayList<>();
		stringBytes = new ArrayList<>();
		payloadData = new ArrayList<>();
		
		header = new EBXHeader();
		names = new ArrayList<>();
		externalGUIDs = new ArrayList<>();
		fieldDescriptors = new ArrayList<>();
		complexDescriptors = new ArrayList<>();
		instanceRepeaters = new ArrayList<>();
		arrayRepeaters = new ArrayList<>();
		strings = new ArrayList<>();
	}
	
	public byte[] createEBX(EBXFile ebxFile){
		if (firstRun){
			init();
		}

		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xAA);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		filler.add((byte) 0xBB);
		
		//TODO
		
		//PAYLOAD
		int payloadSize = 0;
		
		//NOTE - TRUEFILENAME does actually not exist, it uses the String value of the first 'Name' field in the primaryInstance
		
		for (EBXInstance instance : ebxFile.getInstances()){
			if (!proccInstance(instance)){
				System.err.println("Couldn't processing EBXInstance. GUID: "+instance.getGuid());
			}
		}
		
		
		writeFieldDescriptors();
		writeComplexDescriptors();
		writeInstanceRepeaters();
		/*DEBUG*///FileHandler.writeFile("output/ebxInstanceRepeater_part", FileHandler.toByteArray(instanceRepeaterBytes));
		writeArrayRepeaters();
		writeExternalGUIDs();
		writeNames();
		/*DEBUG*///FileHandler.writeFile("output/ebxNames_part", FileHandler.toByteArray(nameBytes));
		
		
		int stringOffset = /*headerBytes.size()*/ 64 + externalGUIDBytes.size() + nameBytes.size() + fieldDescriptorBytes.size() +
				complexDescriptorBytes.size() + instanceRepeaterBytes.size() + arrayRepeaterBytes.size();
		header.setAbsStringOffset(stringOffset);
		writeStrings();
		
		int fileSize = stringOffset + stringBytes.size() + payloadSize;
		header.setLenStringToEOF(fileSize - stringOffset);
		
		writeHeader();//needs extend by payload! 4bytes fourCC + 36bytes
		FileHandler.addBytes(FileHandler.hexStringToByteArray(ebxFile.getGuid()), headerBytes); //+16Bytes GUID
		for (int i=0; i<8;i++){
			headerBytes.add((byte) 0x00);//+8Bytes padding
		}/*TOTAL HEADER SIZE = 64Bytes*/
		
		
		finalDataArrays.add(headerBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(externalGUIDBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(nameBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(fieldDescriptorBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(complexDescriptorBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(instanceRepeaterBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(arrayRepeaterBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(stringBytes);
			finalDataArrays.add(filler);//TEST
		finalDataArrays.add(payloadData);
		
		byte[] data = FileHandler.toBytes(finalDataArrays);
		init(); //as cleanUp!
		return data;
	}
	
	
	public boolean proccInstance(EBXInstance ebxInstance){
		//TODO where does the guid go ?
		short index = proccComplex(ebxInstance.getComplex());
				
		EBXInstanceRepeater repeater = new EBXInstanceRepeater(index, 0);
		/*sow, id clud be ´tha we cud makke a repeatzer 4 eavery one yoooo, in the original one as it always 0. we should be fine with that.*/
		instanceRepeaters.add(repeater);
		return true;
	}
	
	public short proccComplex(EBXComplex ebxComplex){
		//return index of complex
		//TODO
		int totalSize = 0; //its acc. a short but wanna have a use for secondary size ^__^
		for (EBXField field : ebxComplex.getFields()){
			int fieldSize = proccField(field);
			if(fieldSize>=0){
				totalSize += fieldSize;
			}else{
				System.err.println("Couldn't processing EBXComplex's field! Name: "+field.getFieldDescritor().getName());
			}
		}
		return proccComplexDescriptor(ebxComplex.getComplexDescriptor(), ebxComplex.getFields().length, totalSize);
	}
	
	public short proccComplexDescriptor(EBXComplexDescriptor desc, Integer numFields, int totalSize){
		//return index
		
		//TODO
		desc.setFieldStartIndex(fieldDescriptors.size()-numFields);
		
		desc.setSize((short) totalSize);
		desc.setSecondarySize((short) (totalSize>>16 & 0xFFFF)); //?? maybe ??
		
		desc.setNumField((char) (numFields & 0xFF));
		
		
		//PAYLOAD ??
		
		complexDescriptors.add(desc);
		
		return (short) complexDescriptors.size();
	}
	
	public boolean proccFieldDescriptor(EBXFieldDescriptor desc){
		//TODO
		
		//PAYLOAD ??
		
		fieldDescriptors.add(desc);
		
		return true;
	}
	
	public int proccField(EBXField ebxField){
		//return size.//TODO
		EBXFieldDescriptor desc = ebxField.getFieldDescritor();
		byte[] data = null;
		if (ebxField.getType()!=null){//if newly added with TreeView, it does not have a type as SHORT) 
			switch (ebxField.getType()) {
				case ArrayComplex:
					desc.setType((short) 0x0041);
					break;
				case Bool:
					desc.setType((short) 0xc0ad);
					break;
				case Byte:
					desc.setType((short) 0xc0cd);
					break;
				case ChunkGuid:
					desc.setType((short) 0xC15D);
					break;
				case Complex:
					desc.setType((short) 0x0029);//OR WHATEVER ??
					break;
				case Enum:
					desc.setType((short) 0x0089);
					break;
				case ExternalGuid:
					desc.setType((short) 0x0035);
					break;
				case Float:
					desc.setType((short) 0xC13D);
					break;
				case Guid:
					desc.setType((short) 0x0035); //same as external ID!
					break;
				case Hex8:
					desc.setType((short) 0x417D);
					break;
				case Integer:
					desc.setType((short) 0xc0ad);
					break;
				case Short:
					desc.setType((short) 0xc0ed);
					break;
				case String:
					desc.setType((short) 0x407D);//OR WHATEVER ??
					break;
				case UInteger:
					desc.setType((short) 0xC10D);
					break;
				case Unknown:
					desc.setType((short) 0xFFFF);//ERROR
					break;
				}
		}
		short h = ebxField.getFieldDescritor().getType();
		if (h==0xFFFF){
			//DEFUQ ?
		}else if(h==0x407D||h==0x409D){//STRING
			strings.add((String) ebxField.getValue());
			int relOffset = 0;
			for (String s : strings){
				relOffset += s.length()+1;
			}			
			data = FileHandler.toBytes((int) relOffset, ByteOrder.LITTLE_ENDIAN);
			FileHandler.addBytes(data, payloadData);
		}else if(h==0xC13D){//FLOAT
			data = FileHandler.toBytes((float) ebxField.getValue(), ByteOrder.LITTLE_ENDIAN);
			FileHandler.addBytes(data, payloadData);
		}else if(h==0x0029||h==0xd029||h==0x8029){//COMPLEX
			
			data = new byte[] {0x00, 0x00, 0x00, 0x00};
			FileHandler.addBytes(data, payloadData);//?? what is the value/size ?? //TODO
			
			short index = proccComplex(ebxField.getValueAsComplex());
			if (index==-1){
				return -1;
			}
			desc.setRef(index);
		}else if (h==0xC089||h==0x0089){//ENUM
			if (ebxField.getValue() instanceof String){
				System.err.println("NULL ENUM (STRING)");
				//TODO NULL ENUM
			}else if(ebxField.getValue() instanceof HashMap<?, ?>){
				/*  value in field (unsigned integer) represents the relative index from the ENUMComplex fields!
				 *  desc.ref redirects to the enumComplex. 
				 *  for field in enum Complex (rel index starts at 0)
				 *  	if (field.desc.offset) == value in field aka. index^
				 *  		desc.name == SELECTED ENUM.
				 *  
				 *  
				 */
			}else{
				System.err.println("ENUM ERROR");
				//TODO ENUM ERROR...
			}
		}else{
			data = new byte[] {(byte) 0xFF}; //TODO temp testing
			FileHandler.addBytes(data, payloadData);
		}
		
		
		if (!proccFieldDescriptor(desc)){
			return -1;
		}
		

		return data.length;
	}
	
	
	public int proccName(String name){
		//returns FNV_1 hash
		for (String s : names){
			if (name.equals(s)){
				return EBXHandler.hasher(name.getBytes());
			}
		}
		names.add(name);
		return EBXHandler.hasher(name.getBytes());
	}
		
	
	public void writeHeader(){
		FileHandler.addBytes(new byte[]{(byte) 0xCE, (byte) 0xD1, (byte) 0xB2, (byte) 0x0F}, headerBytes); //FourCC _little
		FileHandler.addBytes(FileHandler.toBytes(header.getAbsStringOffset(), ByteOrder.LITTLE_ENDIAN), headerBytes); //AbsString offset section start
		FileHandler.addBytes(FileHandler.toBytes(header.getLenStringToEOF(), ByteOrder.LITTLE_ENDIAN), headerBytes); //len from string section to EOF.
		FileHandler.addBytes(FileHandler.toBytes(header.getNumGUID(), ByteOrder.LITTLE_ENDIAN), headerBytes); //num of external GUIDs (FileGUID|InstanceGUID)
		FileHandler.addBytes(FileHandler.toBytes((short) header.getNumInstanceRepeater(), ByteOrder.LITTLE_ENDIAN), headerBytes); //num of Instance Repeaters
		FileHandler.addBytes(FileHandler.toBytes((short) header.getNumGUIDRepeater(), ByteOrder.LITTLE_ENDIAN), headerBytes); //num of InstanceRepeaters with GUID aka. GUIDRepeater
		FileHandler.addBytes(FileHandler.toBytes((short) header.getUnknown()/*TODO*/, ByteOrder.LITTLE_ENDIAN), headerBytes);
		FileHandler.addBytes(FileHandler.toBytes((short) header.getNumComplex(), ByteOrder.LITTLE_ENDIAN), headerBytes); //total number of complex entries
		FileHandler.addBytes(FileHandler.toBytes((short) header.getNumField(), ByteOrder.LITTLE_ENDIAN), headerBytes); //total number of field entries
		FileHandler.addBytes(FileHandler.toBytes((short) header.getLenName(), ByteOrder.LITTLE_ENDIAN), headerBytes); //len of name
		FileHandler.addBytes(FileHandler.toBytes(header.getLenString(), ByteOrder.LITTLE_ENDIAN), headerBytes); //len of string
		FileHandler.addBytes(FileHandler.toBytes(header.getNumArrayRepeater(), ByteOrder.LITTLE_ENDIAN), headerBytes); //total number of array repeater
		FileHandler.addBytes(FileHandler.toBytes(header.getLenPayload(), ByteOrder.LITTLE_ENDIAN), headerBytes); //len of normal payload - the start of the ARRAY payload section is absStringOffset+lenString+lenPayload		
	}
	
	public void writeExternalGUIDs(){
		for (EBXExternalGUID guid : externalGUIDs){
			FileHandler.addBytes(FileHandler.hexStringToByteArray(guid.getFileGUID()), externalGUIDBytes);//file guid 16 bytes
			FileHandler.addBytes(FileHandler.hexStringToByteArray(guid.getInstanceGUID()), externalGUIDBytes);//instance guid 16 bytes
		}
	}
	
	public void writeNames(){
		for (String s : names){
			FileHandler.addBytes(s.getBytes(), nameBytes);
			nameBytes.add((byte) 0x00);//may not needed, for cancel out.
		}
		while (nameBytes.size()%16!=0){
			nameBytes.add((byte) 0x00);//line padding.
		}
		header.setLenName(nameBytes.size());
	}
	
	public void writeFieldDescriptors(){
		for (EBXFieldDescriptor fdsc : fieldDescriptors){
			Integer name = proccName(fdsc.getName()/*MAY NEED TAILING NULL*/);
			
			FileHandler.addBytes(FileHandler.toBytes(name, ByteOrder.LITTLE_ENDIAN), fieldDescriptorBytes); //Hashed name as FNV_1 hash with modi. base and modf.
			FileHandler.addBytes(FileHandler.toBytes((short)fdsc.getType(), ByteOrder.LITTLE_ENDIAN), fieldDescriptorBytes); //type as short
			FileHandler.addBytes(FileHandler.toBytes((short)fdsc.getRef(), ByteOrder.LITTLE_ENDIAN), fieldDescriptorBytes); //ref as short
			FileHandler.addBytes(FileHandler.toBytes(fdsc.getOffset(), ByteOrder.LITTLE_ENDIAN), fieldDescriptorBytes); //offset (unsigned) in payload section; replative to the complex containing it.
			FileHandler.addBytes(FileHandler.toBytes(fdsc.getSecondaryOffset(), ByteOrder.LITTLE_ENDIAN), fieldDescriptorBytes); //2nd'ary offset (unsigned)
		}
		header.setNumField(fieldDescriptors.size());
	}
	
	public void writeComplexDescriptors(){		
		for (EBXComplexDescriptor cdsc : complexDescriptors){
			Integer name = proccName(cdsc.getName()/*MAY NEED TAILING NULL*/);
			
			FileHandler.addBytes(FileHandler.toBytes(name, ByteOrder.LITTLE_ENDIAN), complexDescriptorBytes); //Hashed name as FNV_1 hash with modi. base and modf.
			FileHandler.addBytes(FileHandler.toBytes(cdsc.getFieldStartIndex(), ByteOrder.LITTLE_ENDIAN), complexDescriptorBytes); //the index of the first field belonging to the complex
			complexDescriptorBytes.add((byte) cdsc.getNumField()); //total number of fields belonging to the complex
			complexDescriptorBytes.add((byte) cdsc.getAlignment()); //alignment
			FileHandler.addBytes(FileHandler.toBytes((short)cdsc.getType(), ByteOrder.LITTLE_ENDIAN), complexDescriptorBytes); // type as short
			FileHandler.addBytes(FileHandler.toBytes((short)cdsc.getSize(), ByteOrder.LITTLE_ENDIAN), complexDescriptorBytes); //total length of the complex in the payload section.
			FileHandler.addBytes(FileHandler.toBytes((short)cdsc.getSecondarySize(), ByteOrder.LITTLE_ENDIAN), complexDescriptorBytes); //seems deprecated or may for padding.
		}
		header.setNumComplex(complexDescriptors.size());
	}
	
	public void writeInstanceRepeaters(){
		for (EBXInstanceRepeater rep : instanceRepeaters){
			FileHandler.addBytes(FileHandler.toBytes((short)rep.getComplexIndex(), ByteOrder.LITTLE_ENDIAN), instanceRepeaterBytes); //represents the complex
			FileHandler.addBytes(FileHandler.toBytes((short)rep.getRepetitions(), ByteOrder.LITTLE_ENDIAN), instanceRepeaterBytes); //total number of repetitions in the complex.
		}
		header.setNumInstanceRepeater(instanceRepeaters.size());
	}
	
	public void writeArrayRepeaters(){		
		for (EBXArrayRepeater rep : arrayRepeaters){
			FileHandler.addBytes(FileHandler.toBytes(rep.getOffset(), ByteOrder.LITTLE_ENDIAN), arrayRepeaterBytes);//offset in array payload section
			FileHandler.addBytes(FileHandler.toBytes(rep.getRepetitions(), ByteOrder.LITTLE_ENDIAN), arrayRepeaterBytes);//number of array repetitions
/*TODO*/	FileHandler.addBytes(FileHandler.toBytes(rep.getComplexIndex(), ByteOrder.LITTLE_ENDIAN), arrayRepeaterBytes);//the complex belonging to the array - not necessary for extraction.
		}
		while (arrayRepeaterBytes.size()%16!=0){//TODO temp testing
			arrayRepeaterBytes.add((byte) 0x00);//line padding.
		}
		header.setNumArrayRepeater(arrayRepeaters.size());
	}
	
	
	public void writeStrings(){
		for (String s : strings){
			FileHandler.addBytes(s.getBytes(), stringBytes);
			stringBytes.add((byte) 0x00);//may not needed, for cancel out.
		}
		while (stringBytes.size()%16!=0){
			stringBytes.add((byte) 0x00);//line padding.
		}
		header.setLenString(stringBytes.size());
	}
	
}
