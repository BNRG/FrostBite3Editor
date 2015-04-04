package tk.captainsplexx.CAS;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
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
			en.setSHA1(FileHandler.readSHA1(fileArray, seeker));
			en.setOffset(FileHandler.readInt(fileArray, seeker));
			en.setProcSize(FileHandler.readInt(fileArray, seeker));
			en.setCasFile(FileHandler.readInt(fileArray, seeker));
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
			addBytes(FileHandler.hexStringToByteArray(e.getSHA1())); //SHA1 20bytes
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
		
	//GETTER AND SETTERS
	public ArrayList<CasCatEntry> getEntries() {
		return entries;
	}
}
