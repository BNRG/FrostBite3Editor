package tk.captainsplexx.Resource.EBX;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tk.captainsplexx.Game.Main;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.EBX.EBXHandler.FieldValueType;

public class EBXLoader {
	public byte[] ebxFileBytes;
	public String guidTablePath;
	//public HashMap<Integer, Integer> numDict = new HashMap<Integer, Integer>();
	public HashMap<Integer, String> keywordDict;
	

	public byte[] littleHeader = new byte[] { (byte) 0xCE, (byte) 0xD1,
			(byte) 0xB2, (byte) 0x0F };
	public byte[] bigHeader = new byte[] { (byte) 0x0F, (byte) 0xB2,
			(byte) 0xD1, (byte) 0xCE };

	public ByteOrder order;
	public EBXHeader header;

	public int arraySectionstart;
	public String trueFilename;
	public String fileGUID;
	public EBXExternalGUID[] externalGUIDs;
	public ArrayList<String> internalGUIDs;
	public EBXFieldDescriptor[] fieldDescriptors;
	public EBXComplexDescriptor[] complexDescriptors;
	public EBXInstanceRepeater[] instanceRepeaters;
	public EBXArrayRepeater[] arrayRepeaters;
	public ArrayList<EBXInstance> instances;
	public ArrayList<EBXField> fields;
	
	public FileSeeker seeker;
	
	public boolean isPrimaryInstance;
	public String filePath;
	
	public ArrayList<EBXInstance> getInstances(){
		return instances;
	}
	
	public String getTrueFilename(){
		return trueFilename;
	}
	
	public static String getGUID(byte[] ebxFile){
		//4Bytes Magic + 36Bytes Header == FileGUID Offset at 40. Byte (0x28)
		return FileHandler.bytesToHex(FileHandler.readByte(ebxFile, 0x28, 16)).toUpperCase();
	}

	public void loadEBX(byte[] ebxFileBytes){
		this.seeker = new FileSeeker();
		this.ebxFileBytes = ebxFileBytes;
		this.trueFilename = "";
		
		if (Arrays.equals(readMagic(ebxFileBytes), littleHeader)){
			order = ByteOrder.LITTLE_ENDIAN;
		}else if (Arrays.equals(readMagic(ebxFileBytes), bigHeader)){
			order = ByteOrder.BIG_ENDIAN;
		}else{
			System.err.println("EBX File does not match Header's: "+filePath);
		}
		
		//Decrypt Header 36 Bytes long 3I6H3I
		seeker.setOffset(0x04);
		header = new EBXHeader(
				FileHandler.readInt(ebxFileBytes, seeker, order), //absolute offset for string section start 0x04 4bytes
				FileHandler.readInt(ebxFileBytes, seeker, order), //length from string section start to EOF 0x08 4bytes
				FileHandler.readInt(ebxFileBytes, seeker, order), //number of external GUIDs 0x0C 4bytes
				FileHandler.readShort(ebxFileBytes, seeker, order), //total number of instance repeaters 0x10 2byte
				FileHandler.readShort(ebxFileBytes, seeker, order), //instance repeaters with GUID 0x12 2bytes
				FileHandler.readShort(ebxFileBytes, seeker, order), //0x14 2bytes ??!
				FileHandler.readShort(ebxFileBytes, seeker, order), //number of complex entries 0x16 2bytes
				FileHandler.readShort(ebxFileBytes, seeker, order), //number of field entries 0x18 2bytes
				FileHandler.readShort(ebxFileBytes, seeker, order), //length of name section including padding 0x1A 2bytes
				FileHandler.readInt(ebxFileBytes, seeker, order), //length of string section including padding 0x1C 4bytes
				FileHandler.readInt(ebxFileBytes, seeker, order), //num array repeater 0x20 4bytes
				FileHandler.readInt(ebxFileBytes, seeker, order)); //length of normal payload section; the start of the array payload section is absStringOffset+lenString+lenPayload 0x24 4bytes	
		
		//Calculate ArraySection Start Offset
		arraySectionstart=header.getAbsStringOffset()+header.getLenString()+header.getLenPayload();
		//Read out file GUID as HEXSTRING!
		fileGUID=FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes, seeker, 16)).toUpperCase();
		//Calc of externalGUIDs
		while (seeker.getOffset()%16!=0){ seeker.seek(1); } //#padding
		externalGUIDs = new EBXExternalGUID[header.getNumGUID()];
		for (int i=0;i<externalGUIDs.length;i++){
			externalGUIDs[i] = new EBXExternalGUID((FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes, seeker, 16)).toUpperCase()), (FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes, seeker, 16)).toUpperCase()));
		}
		//Get Keywords and Calculate Hashes
		this.keywordDict = new HashMap<Integer, String>();
		int startKeyOffset = 0x40+((header.getNumGUID())*0x20);
		int keyOffset = 0;
		while (startKeyOffset+keyOffset < startKeyOffset+header.getLenName()){
			String s = readString(ebxFileBytes, startKeyOffset+keyOffset);
			keywordDict.put(hasher(s.getBytes()), s);
			keyOffset += s.length()+1;
		}
		//fieldDescriptors
		seeker.setOffset(startKeyOffset+header.getLenName());
		this.fieldDescriptors = new EBXFieldDescriptor[header.getNumField()];
		for (int i=0;i<fieldDescriptors.length;i++){
			fieldDescriptors[i] = new EBXFieldDescriptor(keywordDict.get(FileHandler.readInt(ebxFileBytes, seeker)),
					FileHandler.readShort(ebxFileBytes, seeker, order),
					FileHandler.readShort(ebxFileBytes, seeker, order),
					FileHandler.readInt(ebxFileBytes, seeker, order),
					FileHandler.readInt(ebxFileBytes, seeker, order));
		}		
		//ComplexDescriptor
		complexDescriptors = new EBXComplexDescriptor[header.getNumComplex()];
		for (int i=0;i<complexDescriptors.length;i++){
			complexDescriptors[i] = new EBXComplexDescriptor(keywordDict.get(FileHandler.readInt(ebxFileBytes, seeker, order)),
					FileHandler.readInt(ebxFileBytes, seeker, order), 
					(char) FileHandler.readByte(ebxFileBytes, seeker), 
					(char) FileHandler.readByte(ebxFileBytes, seeker),
					FileHandler.readShort(ebxFileBytes, seeker, order),
					FileHandler.readShort(ebxFileBytes, seeker, order),
					FileHandler.readShort(ebxFileBytes, seeker, order));
		}
		//instanceRepeaters
		instanceRepeaters = new EBXInstanceRepeater[header.getNumInstanceRepeater()];
		for (int i=0;i<instanceRepeaters.length;i++){
			instanceRepeaters[i] = new EBXInstanceRepeater(
					FileHandler.readShort(ebxFileBytes, seeker, order), 
					FileHandler.readShort(ebxFileBytes, seeker, order));
		}
		
		//arrayRepeaters
		
		while (seeker.getOffset()%16!=0){ seeker.seek(1);} // #padding
		
		arrayRepeaters = new EBXArrayRepeater[header.getNumArrayRepeater()];
		for (int i=0; i<arrayRepeaters.length;i++){
			arrayRepeaters[i] = new EBXArrayRepeater(
					FileHandler.readInt(ebxFileBytes, seeker, order),
					FileHandler.readInt(ebxFileBytes, seeker, order),
					FileHandler.readInt(ebxFileBytes, seeker, order));
		}
		
		
		//PayLoad
		internalGUIDs = new ArrayList<String>(); 
		fields = new ArrayList<EBXField>(); 
		instances = new ArrayList<EBXInstance>(); 
		seeker.setOffset(header.absStringOffset+header.lenString);
		int nonGUIDindex = 0;
		String tempGUID = "";
		isPrimaryInstance = true;
		for (int repeater=0; repeater<instanceRepeaters.length;repeater++){
			EBXInstanceRepeater ir = instanceRepeaters[repeater];
			for (int repetition=0; repetition<ir.getRepetitions();repetition++){
				while (seeker.getOffset()%complexDescriptors[ir.complexIndex].alignment!=0){ seeker.seek(1); } //#obey alignment of the instance; peek into the complex for that
				if (repeater<header.getNumGUIDRepeater()){
					tempGUID = FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes, seeker, 16));
				}else{
					tempGUID = FileHandler.bytesToHex(ByteBuffer.allocate(4).putInt(nonGUIDindex).array());
					nonGUIDindex++;
				}
				internalGUIDs.add(tempGUID);
				instances.add(new EBXInstance(tempGUID, readComplex(ir.getComplexIndex(), true)));
				isPrimaryInstance = false;
			}
		}
		ebxFileBytes = null;
	}
	
	private EBXComplex readComplex(int complexIndex, boolean isInstance) {
		EBXComplexDescriptor complexDesc = complexDescriptors[complexIndex];
		EBXComplex cmplx = new EBXComplex(complexDesc);
		cmplx.setOffset(seeker.getOffset());
		
		EBXField[] fields = new EBXField[complexDesc.getNumField()];
		//#alignment 4 instances require subtracting 8 for all field offsets and the complex size 
		int obfuscationShift=0;
		if (isInstance && cmplx.getComplexDescriptor().getAlignment()==4){
			obfuscationShift = 8;
		}
		for (int fieldIndex=0; fieldIndex<complexDesc.getNumField(); fieldIndex++){
			seeker.setOffset(cmplx.getOffset()+fieldDescriptors[complexDesc.getFieldStartIndex()+fieldIndex].getOffset()-obfuscationShift);
	    	fields[fieldIndex] = readField(complexDesc.getFieldStartIndex()+fieldIndex);
		}
		cmplx.fields = fields;
		seeker.setOffset(cmplx.getOffset()+complexDesc.getSize()-obfuscationShift);
		return cmplx;
	}

	public EBXField readField(int fieldIndex){
		EBXFieldDescriptor fieldDesc = fieldDescriptors[fieldIndex];
		EBXField field = new EBXField(fieldDesc, seeker.getOffset());
		fields.add(field);
		
		
		/*<DECODE>*/
		if (fieldDesc.getType() == (short) 0x0029|| fieldDesc.getType() == (short) 0xd029 || fieldDesc.getType() == (short) 0x0000 || fieldDesc.getType() == (short) 0x8029){ //COMPLEX
			field.setValue(readComplex(fieldDesc.getRef(), false), EBXHandler.FieldValueType.Complex);
		}else if (fieldDesc.getType() == 0x0041){ //ARRAYCOMPLEX
			int indexasdf = FileHandler.readInt(ebxFileBytes, seeker);
			EBXArrayRepeater arrayRepeater = arrayRepeaters[indexasdf];
			EBXComplexDescriptor arrayComplexDesc = complexDescriptors[fieldDesc.getRef()];
			seeker.setOffset(arraySectionstart+arrayRepeater.offset);
			EBXComplex arrayComplex = new EBXComplex(arrayComplexDesc);
			EBXField[] fields = new EBXField[arrayRepeater.getRepetitions()];
			for (int i=0; i<fields.length;i++){
				fields[i] = readField(arrayComplexDesc.getFieldStartIndex());
				if (fields[i].getFieldDescritor().getType() == (short) 0x0029|| fields[i].getFieldDescritor().getType() == (short) 0xd029 || fields[i].getFieldDescritor().getType() == (short) 0x0000 || fields[i].getFieldDescritor().getType() == (short) 0x8029){ //COMPLEX
					fields[i].setType(FieldValueType.Complex);
				}else if (fields[i].getFieldDescritor().getType() == 0x0041){ //ARRAYCOMPLEX
					fields[i].setType(FieldValueType.ArrayComplex);
				}else if (fields[i].getFieldDescritor().getType() == (short) 0x407D || fields[i].getFieldDescritor().getType() == (short) 0x409D){//STRING
					fields[i].setType(FieldValueType.String);
				}else if (fields[i].getFieldDescritor().getType() == (short) 0x0089 || fields[i].getFieldDescritor().getType() == (short) 0xC089){//ENUM
					fields[i].setType(FieldValueType.Enum);
				}else if (fields[i].getFieldDescritor().getType()== (short) 0xC15D){ //GUID
					fields[i].setType(FieldValueType.Guid);
				}else if (fields[i].getFieldDescritor().getType()== (short) 0x417D){ //8HEX
					fields[i].setType(FieldValueType.Hex8);
				}else if (fields[i].getFieldDescritor().getType()==(short) 0xC13D){//FLOAT
					fields[i].setType(FieldValueType.Float);
				}else if (fields[i].getFieldDescritor().getType()==(short) 0xC10D){//uint ===???
					fields[i].setType(FieldValueType.UInteger);
				}else if(fields[i].getFieldDescritor().getType() == (short) 0xc0fd){ // signed int ?=??
					fields[i].setType(FieldValueType.Integer);
				}else if (fields[i].getFieldDescritor().getType() == (short) 0xc0ad){//BOOL
					fields[i].setType(FieldValueType.Bool);
				}else if (fields[i].getFieldDescritor().getType() == (short) 0xc0ed){//short
					fields[i].setType(FieldValueType.Short);			
				}else if (fields[i].getFieldDescritor().getType() == (short) 0xc0cd){//BYTE
					fields[i].setType(FieldValueType.Byte);
				}else if(fields[i].getFieldDescritor().getType() == (short) 0x0035){ // ##guid
					fields[i].setType(FieldValueType.Guid);
				}
			}
			arrayComplex.setFields(fields);
			field.setValue(arrayComplex, FieldValueType.ArrayComplex);
		}else if (fieldDesc.getType() == (short) 0x407D || fieldDesc.getType() == (short) 0x409D){//STRING
			int stringOffset = FileHandler.readInt(ebxFileBytes, seeker);
			if (stringOffset==-1){
				field.setValue("", FieldValueType.String);
			}else{
				field.setValue(readString(ebxFileBytes, header.getAbsStringOffset()+stringOffset),FieldValueType.String);
			}
			//TRUEFILENAME!
			if (isPrimaryInstance && fieldDesc.getName().equals("Name") && trueFilename.equals("")){
				trueFilename = (String) field.getValue();
			}
		}else if (fieldDesc.getType() == (short) 0x0089 || fieldDesc.getType() == (short) 0xC089){//ENUM GIVES STRING BACK - incomplete implementation //TODO
			int compareValue = FileHandler.readInt(ebxFileBytes, seeker);
			EBXComplexDescriptor enumComplex = complexDescriptors[fieldDesc.getRef()];
			
			if (enumComplex.getNumField()==0){
				field.setValue("*nullEnum*", FieldValueType.Enum);
			}else{
				for (int index=0; index<enumComplex.getNumField(); index++){ //TODO check ??
					if (fieldDescriptors[enumComplex.getFieldStartIndex()+index].getOffset()==compareValue){
						field.setValue(fieldDescriptors[enumComplex.getFieldStartIndex()+index], FieldValueType.Enum);
						break;
					}
				}
			}
		}else if (fieldDesc.getType()== (short) 0xC15D){ //GUID CHUNK
			field.setValue(FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes, seeker,16)), FieldValueType.ChunkGuid);
		}else if (fieldDesc.getType()== (short) 0x417D){ //8HEX
			field.setValue(FileHandler.bytesToHex(FileHandler.readByte(ebxFileBytes,seeker, 8)), FieldValueType.Hex8);
		}else if (fieldDesc.getType()==(short) 0xC13D){//FLOAT
			field.setValue(FileHandler.readFloat(ebxFileBytes, seeker), FieldValueType.Float);
		}else if (fieldDesc.getType()==(short) 0xC10D){//uint
			field.setValue((FileHandler.readInt(ebxFileBytes, seeker, order) & 0xffffffff), FieldValueType.UInteger);
		}else if(fieldDesc.getType() == (short) 0xc0fd){ //signed int
			field.setValue(FileHandler.readInt(ebxFileBytes, seeker), FieldValueType.Integer);
		}else if (fieldDesc.getType() == (short) 0xc0ad){//BOOL
			field.setValue(((FileHandler.readByte(ebxFileBytes, seeker))!=0x0), FieldValueType.Bool);
		}else if (fieldDesc.getType() == (short) 0xc0ed){//short
			field.setValue(FileHandler.readShort(ebxFileBytes, seeker, order), FieldValueType.Short);			
		}else if (fieldDesc.getType() == (short) 0xc0cd){//BYTE
			field.setValue(FileHandler.readByte(ebxFileBytes, seeker), FieldValueType.Byte);
		}else if(fieldDesc.getType() == (short) 0x0035){ // #guid
			int tempValue = FileHandler.readInt(ebxFileBytes, seeker);
			if(((tempValue>>31) & 0xFFFFFFFF) == -1){
				EBXExternalGUID guid = externalGUIDs[(tempValue & 0x7fffffff)];
				String fileGUID = Main.getGame().getEBXFileGUIDs().get(guid.getFileGUID().toUpperCase());
				if (fileGUID != null){
					field.setValue(fileGUID+" "+guid.getInstanceGUID(), FieldValueType.Guid);
				}else{
					field.setValue(guid.getFileGUID()+" "+guid.getInstanceGUID(), FieldValueType.Guid);
				}
			}else if (tempValue == 0x0){
				field.setValue("*nullGUID*", FieldValueType.Guid);
			}else{
				//System.err.println("INT"); //TODO WE NEED A RECURSE!
				field.setValue("INTERNAL GUID TODO", FieldValueType.Guid);
				//field.setValue(internalGUIDs.get(tempValue-1), FieldValueType.Guid);
			}
		}else{
			System.err.println("Unknown field type: "+Integer.toHexString(fieldDesc.getType())+" File name: "+filePath);
			field.setValue("*unknown field type*", FieldValueType.Unknown);
			/*
			 try:
                (typ,length)=numDict[fieldDesc.type]
                num=self.unpack(typ,f.read(length))[0]
                field.value=num
            except:
                print "Unknown field type: "+str(fieldDesc.type)+" File name: "+self.relPath
                field.value="*unknown field type*"
			 */
		}
		/*<END OF DECODE>*/
		return field;
		
	}

	public int hasher(byte[] bytes) {
		int hash = 5381;
		for (Byte b : bytes) {
			hash = hash * 33 ^ b;
		}
		return hash;
	}

	public String readString(byte[] fileArray, int offset) {
		String tmp = "";
		for (int i = 0; i < 1000; i++) {
			byte[] b = readByte(fileArray, offset + i, 1);
			if (b[0] != 0x0) {
				String str;
				try {
					str = new String(b, "UTF-8");
					tmp += str;
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					i = 1500;
				}
			} else {
				break;
			}
		}
		return tmp;
	}

	public byte[] readByte(byte[] fileArray, int offset, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			try{
				buffer[i] = fileArray[offset + i];
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("END OF FILE REACHED!!");
				buffer[i] = 0;
			}
		}
		return buffer;
	}


	public byte[] readMagic(byte[] fileArray) {
		return readByte(fileArray, 0, 4);
	}

	// Constructor
	public EBXLoader() {
		/*
		numDict.put(0xC12D, 8);
		numDict.put(0xc0cd, 1);
		numDict.put(0x0035, 4);
		numDict.put(0xc10d, 4);
		numDict.put(0xc14d, 8);
		numDict.put(0xc0ad, 1);
		numDict.put(0xc0fd, 4);
		numDict.put(0xc0bd, 1);
		numDict.put(0xc0ed, 2);
		numDict.put(0xc0dd, 2);
		numDict.put(0xc13d, 4);
		*/
	}
}
