package tk.captainsplexx.Resource;

import java.io.File;
import java.io.FileInputStream;

public class ChunkFinder {
	public String[] guids;
	public byte[] MeshBytes;
	public byte[] buffer;
	
	
	public String findChunkFile(String meshFilePath, ResourceHandler rsh) {
		this.MeshBytes = readFile(meshFilePath);
		buffer = readByte(MeshBytes, 0xC8, 16);
		return null /*rsh.getChunkFolderPath()+bytesToHex(buffer).toLowerCase()+".chunk"*/;//TODO
	}
		
	static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	// Inputstream Operations
	public byte[] readByte(byte[] fileArray, int offset, int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len; i++) {
			buffer[i] = fileArray[offset + i];
		}
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
}
