package tk.captainsplexx.Resource.ITEXTURE;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.TOC.ConvertedTocFile;
import tk.captainsplexx.Resource.TOC.TocSBLink;

public class ITextureHandler {

	public static byte[] dds_template = { 0x44, 0x44, 0x53, 0x20, 0x7C, 0x00,
			0x00, 0x00, 0x07, 0x10, 0x00, 0x00 };

	public static byte[] dds_template1 = { 0x00, 0x00, 0x02, 0x00, 0x00, 0x00,
			0x02, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x04,
			0x00, 0x00, 0x00 };

	public static byte[] dds_template2 = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00 };

	public static byte[] dds_DXT1 = { 0x44, 0x58, 0x54, 0x31 }; // 44585431 =
																// DXT1
	public static byte[] dds_DXT5 = { 0x44, 0x58, 0x54, 0x35 }; // 44585435 =
																// DXT5
	public static byte[] dds_ATI2 = { 0x41, 0x54, 0x49, 0x32 }; // 41544932 =
																// ATI2

	public static byte[] getDSS(byte[] itextureData) {
		FileSeeker seeker = new FileSeeker();

		seeker.setOffset(12);
		int dds_type = FileHandler.readInt(itextureData, seeker,
				ByteOrder.LITTLE_ENDIAN);

		seeker.setOffset(18);
		short dds_width = FileHandler.readShort(itextureData, seeker,
				ByteOrder.LITTLE_ENDIAN);

		seeker.setOffset(20);
		short dds_height = FileHandler.readShort(itextureData, seeker,
				ByteOrder.LITTLE_ENDIAN);

		seeker.setOffset(28);
		String guid = FileHandler.bytesToHex(FileHandler.readByte(itextureData,
				seeker, 16));

		String sha1 = Core.getGame().getChunkGUIDSHA1().get(guid.toLowerCase());

		if (sha1 == null) {
			System.out
					.println("SHA1 could not be found in GUID-SHA1 Database!");
			System.out.println("Try common chunks.");
			for (ConvertedTocFile commonChunk : Core.getGame()
					.getCommonChunks()) {
				for (TocSBLink chunk : commonChunk.getChunks()) {
					if (chunk.getGuid().equalsIgnoreCase(guid)) {
						sha1 = chunk.getSha1();
						System.out.println("Chunk successfully found!");
						break;
					}
				}
				if (sha1 != null) {
					break;
				}
			}
		}

		if (sha1 == null) {
			System.err.println("Nothing found ;(");
			return null;
		}
		byte[] data = CasDataReader.readCas(null, null, sha1, 666);
		if (data != null) {
			ArrayList<Byte> output = new ArrayList<Byte>();

			for (byte b : dds_template) {
				output.add(new Byte(b));
			}
			for (byte b : FileHandler.toBytes((int) dds_height,
					ByteOrder.LITTLE_ENDIAN)) {
				output.add(new Byte(b));
			}
			for (byte b : FileHandler.toBytes((int) dds_width,
					ByteOrder.LITTLE_ENDIAN)) {
				output.add(new Byte(b));
			}
			for (byte b : dds_template1) {
				output.add(new Byte(b));
			}

			if (dds_type == 0 || dds_type == 20) {
				for (byte b : dds_DXT1) {
					output.add(new Byte(b));
				}
			} else if (dds_type == 19) {
				for (byte b : dds_ATI2) {
					output.add(new Byte(b));
				}
			} else if (dds_type == 3) {
				for (byte b : dds_DXT5) {
					output.add(new Byte(b));
				}
			}

			for (byte b : dds_template2) {
				output.add(new Byte(b));
			}
			for (byte b : data) {
				output.add(new Byte(b));
			}

			return FileHandler.convertFromList(output);
		} else {
			System.err.println("Could not get data from cas.");
			return null;
		}
	}
}
