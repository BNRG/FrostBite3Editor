package tk.captainsplexx.EBX;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import tk.captainsplexx.EBX.EBXHandler.FieldValueType;
import tk.captainsplexx.Resource.FileSeeker;

public class EBXLoader {
	// Constructor on end of class!
	public byte[] ebxFileBytes;
	public String guidTablePath;
	public HashMap<Integer, Integer> numDict = new HashMap<Integer, Integer>();
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
				readInt(ebxFileBytes, seeker), //absolute offset for string section start 0x04 4bytes
				readInt(ebxFileBytes, seeker), //length from string section start to EOF 0x08 4bytes
				readInt(ebxFileBytes, seeker), //number of external GUIDs 0x0C 4bytes
				readShort(ebxFileBytes, seeker), //total number of instance repeaters 0x10 2byte
				readShort(ebxFileBytes, seeker), //instance repeaters with GUID 0x12 2bytes
				readShort(ebxFileBytes, seeker), //0x14 2bytes ??!
				readShort(ebxFileBytes, seeker), //number of complex entries 0x16 2bytes
				readShort(ebxFileBytes, seeker), //number of field entries 0x18 2bytes
				readShort(ebxFileBytes, seeker), //length of name section including padding 0x1A 2bytes
				readInt(ebxFileBytes, seeker), //length of string section including padding 0x1C 4bytes
				readInt(ebxFileBytes, seeker), //num array repeater 0x20 4bytes
				readInt(ebxFileBytes, seeker)); //length of normal payload section; the start of the array payload section is absStringOffset+lenString+lenPayload 0x24 4bytes	
		
		//Calculate ArraySection Start Offset
		arraySectionstart=header.getAbsStringOffset()+header.getLenString()+header.getLenPayload();
		//Read out file GUID as HEXSTRING!
		fileGUID=bytesToHex(readByte(ebxFileBytes, 16, seeker)).toUpperCase();
		//Calc of externalGUIDs
		while (seeker.getOffset()%16!=0){ seeker.seek(1); } //#padding
		externalGUIDs = new EBXExternalGUID[header.getNumGUID()];
		for (int i=0;i<externalGUIDs.length;i++){
			externalGUIDs[i] = new EBXExternalGUID((bytesToHex(readByte(ebxFileBytes, 16, seeker)).toUpperCase()), (bytesToHex(readByte(ebxFileBytes, 16, seeker)).toUpperCase()));
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
			fieldDescriptors[i] = new EBXFieldDescriptor(keywordDict.get(readInt(ebxFileBytes, seeker)),
					readShort(ebxFileBytes, seeker),
					readShort(ebxFileBytes, seeker),
					readInt(ebxFileBytes, seeker),
					readInt(ebxFileBytes, seeker));
		}		
		//ComplexDescriptor
		complexDescriptors = new EBXComplexDescriptor[header.getNumComplex()];
		for (int i=0;i<complexDescriptors.length;i++){
			complexDescriptors[i] = new EBXComplexDescriptor(keywordDict.get(readInt(ebxFileBytes, seeker)),
					readInt(ebxFileBytes, seeker), 
					(char) readByte(ebxFileBytes, 1, seeker)[0], 
					(char) readByte(ebxFileBytes, 1, seeker)[0],
					readShort(ebxFileBytes, seeker),
					readShort(ebxFileBytes, seeker),
					readShort(ebxFileBytes, seeker));
		}
		//instanceRepeaters
		//??????????????????????????????????????????????????????????????????????????????
		instanceRepeaters = new EBXInstanceRepeater[header.getNumInstanceRepeater()];
		for (int i=0;i<instanceRepeaters.length;i++){
			instanceRepeaters[i] = new EBXInstanceRepeater(
					readShort(ebxFileBytes, seeker), 
					readShort(ebxFileBytes, seeker));
		}
		
		//arrayRepeaters
		
		while (seeker.getOffset()%16!=0){ seeker.seek(1);} // #padding
		
		arrayRepeaters = new EBXArrayRepeater[header.getNumArrayRepeater()];
		for (int i=0; i<arrayRepeaters.length;i++){
			arrayRepeaters[i] = new EBXArrayRepeater(
					readInt(ebxFileBytes, seeker),
					readInt(ebxFileBytes, seeker),
					readInt(ebxFileBytes, seeker));
		}
		
		
		//PayLoad aka. assign internal guids and nonGUIDindex to a complex
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
					tempGUID = bytesToHex(readByte(ebxFileBytes, 16, seeker));
				}else{
					tempGUID = bytesToHex(ByteBuffer.allocate(4).putInt(nonGUIDindex).array());
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
		
		if (fieldDesc.getType() == (short) 0x0029|| fieldDesc.getType() == (short) 0xd029 || fieldDesc.getType() == (short) 0x0000 || fieldDesc.getType() == (short) 0x8029){ //COMPLEX
			field.setValue(readComplex(fieldDesc.getRef(), false), EBXHandler.FieldValueType.Complex);
		}else if (fieldDesc.getType() == 0x0041){ // ARRAYREPEATER aka ARRAYCOMPLEX
			int indexasdf = readInt(ebxFileBytes, seeker);
			EBXArrayRepeater arrayRepeater = arrayRepeaters[indexasdf];
			EBXComplexDescriptor arrayComplexDesc = complexDescriptors[fieldDesc.getRef()];
			seeker.setOffset(arraySectionstart+arrayRepeater.offset);
			EBXComplex arrayComplex = new EBXComplex(arrayComplexDesc);
			EBXField[] fields = new EBXField[arrayRepeater.getRepetitions()];
			for (int i=0; i<fields.length;i++){
				fields[i] = readField(arrayComplexDesc.getFieldStartIndex());
			}
			arrayComplex.setFields(fields);
			field.setValue(arrayComplex, FieldValueType.ArrayComplex);
		}else if (fieldDesc.getType() == (short) 0x407D || fieldDesc.getType() == (short) 0x409D){//STRING
			int stringOffset = readInt(ebxFileBytes, seeker);
			if (stringOffset==-1){
				field.setValue("", FieldValueType.String);
			}else{
				field.setValue(readString(ebxFileBytes, header.getAbsStringOffset()+stringOffset),FieldValueType.String);
			}
			//TRUEFILENAME!
			if (isPrimaryInstance && fieldDesc.getName().equals("Name") && trueFilename.equals("")){
				trueFilename = (String) field.getValue();
			}
		}else if (fieldDesc.getType() == (short) 0x0089 || fieldDesc.getType() == (short) 0xC089){//ENUM GIVES STRING BACK - incomplete implementation
			int compareValue = readInt(ebxFileBytes, seeker);
			EBXComplexDescriptor enumComplex = complexDescriptors[fieldDesc.getRef()];
			
			if (enumComplex.getNumField()==0){
				field.setValue("*nullEnum*", FieldValueType.Enum);
			}else{
				for (int index=0; index<enumComplex.getNumField(); index++){
					if (fieldDescriptors[index].getOffset()==compareValue){
						field.setValue(fieldDescriptors[index], FieldValueType.Enum);
					}
				}
			}
		}else if (fieldDesc.getType()== (short) 0xC15D){ //GUID
			field.setValue(bytesToHex(readByte(ebxFileBytes, 16, seeker)), FieldValueType.Guid);
		}else if (fieldDesc.getType()== (short) 0x417D){ //8HEX
			field.setValue(bytesToHex(readByte(ebxFileBytes, 8, seeker)), FieldValueType.Hex8);
		}else if (fieldDesc.getType()==(short) 0xC13D){//FLOAT
			field.setValue(readFloat(ebxFileBytes, seeker), FieldValueType.Float);
		}else if (fieldDesc.getType()==(short) 0xC10D){//uint ===???
			field.setValue((readInt(ebxFileBytes, seeker) & 0xffffffffL), FieldValueType.UIntegerAsLong);
		}else if(fieldDesc.getType() == (short) 0xc0fd){ // signed int ?=??
			field.setValue(readInt(ebxFileBytes, seeker), FieldValueType.Integer);
		}else if (fieldDesc.getType() == (short) 0xc0ad){//BOOL
			field.setValue(((readByte(ebxFileBytes, 1, seeker)[0])!=0x0), FieldValueType.Bool);
		}else if (fieldDesc.getType() == (short) 0xc0ed){//short
			field.setValue(readShort(ebxFileBytes, seeker), FieldValueType.Short);			
		}else if (fieldDesc.getType() == (short) 0xc0cd){//BYTE
			field.setValue(readByte(ebxFileBytes, 1, seeker)[0], FieldValueType.Byte);
		}else if(fieldDesc.getType() == (short) 0x0035){ // ##guid and guidTable stuff | guidtable needs to be created first
			int tempValue = readInt(ebxFileBytes, seeker);
			if (tempValue == 0x0){
				field.setValue("*nullGUID*", FieldValueType.Guid);
			}else{ /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////!!!!
				try{
					field.setValue(externalGUIDs[(tempValue & 0x7fffffff)].getFileGUID(), FieldValueType.Guid); // INTERNAL ?? EXTERNAL ??
				}catch (Exception e){
					//field.setValue(internalGUIDs.get(tempValue-1), FieldValueType.Guid);// <'=_._Ö_._='> //TODO
				}
			}
		}
		else{
			System.err.println("Unknown field type: "+Integer.toHexString(fieldDesc.getType())+" File name: "+filePath);
			field.setValue("*unknown field type*", FieldValueType.Unknown);
		}		
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
	
	public byte[] readByte(byte[] fileArray, int len, FileSeeker seeker) {
		byte[] buffer = readByte(fileArray, seeker.offset, len);
		seeker.seek(buffer.length);
		return buffer;
	}

	public byte[] readFile(String filepath) {
		try {
			File file = new File(filepath);
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int) file.length()];
			fin.read(fileContent);
			fin.close();
			return fileContent;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public short readShort(byte[] fileArray, int offset) {
		return ByteBuffer.wrap(readByte(fileArray, offset, 2)).order(order)
				.getShort();
	}
	
	public short readShort(byte[] fileArray, FileSeeker seeker){
		return ByteBuffer.wrap(readByte(fileArray, 2, seeker)).order(order).getShort();
	}

	public int readInt(byte[] fileArray, int offset) {
		return ByteBuffer.wrap(readByte(fileArray, offset, 4)).order(order)
				.getInt();
	}
	
	public int readInt(byte[] fileArray, FileSeeker seeker){
		return ByteBuffer.wrap(readByte(fileArray, 4, seeker)).order(order).getInt();
	}

	public byte[] readMagic(byte[] fileArray) {
		return readByte(fileArray, 0, 4);
	}

	public static String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
	
	public float readHalfFloat(byte[] fileArray, int offset){
		return convertHalfToFloat(ByteBuffer.wrap(readByte(fileArray, offset, 2)).order(ByteOrder.LITTLE_ENDIAN).getShort());
	}
	
	public float readFloat(byte[] fileArray, FileSeeker seeker){
		return ByteBuffer.wrap(readByte(fileArray, 4, seeker)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	
	public float readFloat(byte[] fileArray, int offset){
		return ByteBuffer.wrap(readByte(fileArray, offset, 4)).order(ByteOrder.LITTLE_ENDIAN).getFloat();
	}
	
	public long readLong(byte[] fileArray, int offset){
		return ByteBuffer.wrap(readByte(fileArray, offset, 8)).order(ByteOrder.LITTLE_ENDIAN).getLong();
	}
	
	public float convertHalfToFloat(short half) {
        switch ((int) half) {
            case 0x0000:
                return 0f;
            case 0x8000:
                return -0f;
            case 0x7c00:
                return Float.POSITIVE_INFINITY;
            case 0xfc00:
                return Float.NEGATIVE_INFINITY;
            default:
                return Float.intBitsToFloat(((half & 0x8000) << 16)
                        | (((half & 0x7c00) + 0x1C000) << 13)
                        | ((half & 0x03FF) << 13));
        }
    }
	
	public byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

	// Constructor
	public EBXLoader() {
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
	}
}
