package tk.captainsplexx.CAS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileSeeker;

public class CasCatManager {
	public byte[] header = new byte[] {
			(byte) 0x4E, (byte) 0x79, (byte) 0x61, (byte) 0x6E, 
			(byte) 0x4E, (byte) 0x79, (byte) 0x61, (byte) 0x6E,
			(byte) 0x4E, (byte) 0x79, (byte) 0x61, (byte) 0x6E,
			(byte) 0x4E, (byte) 0x79, (byte) 0x61, (byte) 0x6E}; //  << NyanNyanNyanNyan >>
	public ArrayList<CasCatEntry> entries;
	public FileSeeker seeker;
	public byte[] cat;
	
	public boolean readCAT(byte[] fileArray) {
		System.out.println("Reading cas.cat file!");
		for (int i = 0; i <header.length; i++){
			if (header[i] != fileArray[i]){
				System.err.println("given fileArray does not match header of cat.cas file :/");
				return false;
			}
		}
		entries = new ArrayList<CasCatEntry>();
		seeker = new FileSeeker();
		seeker.seek(16);
		while (seeker.getOffset() < fileArray.length){
			CasCatEntry en = new CasCatEntry();
			en.setSHA1(readSHA1(fileArray, seeker));
			en.setOffset(readInt(fileArray, seeker));
			en.setProcSize(readInt(fileArray, seeker));
			en.setCasFile(readInt(fileArray, seeker));
			entries.add(en);
		}//EOF
		return true;
	}
	public byte[] getCAT(){
		System.out.println("Generating cas.cat file with a size of: "+(entries.size()*32)+16+" Byte!");
		seeker = new FileSeeker();
		cat = new byte[(entries.size()*32)+16];
		addBytes(header); //HEADER 16bytes
		for (CasCatEntry e : entries){
			addBytes(hexStringToByteArray(e.getSHA1())); //SHA1 20bytes
			addBytes(toBytes(e.getOffset())); //OFFSET 4bytes
			addBytes(toBytes(e.getProcSize())); //PROCSIZE 4bytes
			addBytes(toBytes(e.getCasFile())); //CASFILE 4bytes
		}
		return cat;
	}
	void addBytes(byte[] arr){
		for (Byte b : arr){
			cat[seeker.getOffset()] = b;
			seeker.seek(1);
		}
	}
	byte[] toBytes(int i)
	{
		byte[] result = new byte[4]; //LITTLE
		result[3] = (byte) (i >> 24);
		result[2] = (byte) (i >> 16);
		result[1] = (byte) (i >> 8);
		result[0] = (byte) (i /*>> 0*/);
		return result;
	}
	
	//DEBUG PRINT
	public void dumpDebug(){
		for (CasCatEntry e : entries){
			System.out.println(e.getSHA1()+" "+e.getCasFile()+"\n");
		}
	}
	
	// stream Operations
	byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	String bytesToHex(byte[] in) {
		final StringBuilder builder = new StringBuilder();
		for (byte b : in) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}
	int readInt(byte[] fileArray, FileSeeker seeker) {
		return ByteBuffer.wrap(readByte(fileArray, seeker, 4))
				.order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	String readSHA1(byte[] fileArray, FileSeeker seeker) {
		return bytesToHex(readByte(fileArray, seeker, 20));
	}
	byte readByte(byte[] input, FileSeeker seeker) {
		byte b = 0x0;
		try {
			b = input[seeker.getOffset()];
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ReadByte out of bounds. " + seeker.getOffset());
		}
		seeker.seek(1);
		return b;
	}
	byte[] readByte(byte[] input, FileSeeker seeker, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			buffer[i] = readByte(input, seeker);
		}
		return buffer;
	}
	
	//GETTER AND SETTERS
	public ArrayList<CasCatEntry> getEntries() {
		return entries;
	}
}
