package tk.captainsplexx.Maths;

import java.nio.ByteOrder;
import java.util.ArrayList;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.FileSeeker;

public class LZ4 {
	public ArrayList<Byte> output;
	private FileSeeker seeker;
	private int procced;
	private boolean first;
	
	public byte[] decompress(byte[] input){
		/*
		All individual files are compressed with an LZ77 algorithm.

		A compressed file consists of several blocks, with no global metadata.
		The blocks are set to have a size of 0x010000 when decompressed, except for the last one which is usually smaller.

		Structure of a compressed block (big endian):
			4 bytes: decompressed size (0x10000 or less)
			2 bytes: compression type (0970 for LZ77, 0070/0071 for uncompressed data, 0000 for empty payload)
			2 bytes: compressed size (null for type 0071 and type 0000) of the payload (i.e. without the header)
			compressed payload

		Decompress each block and glue the decompressed parts together to obtain the file.

		The compression is an LZ77 variant. It requires 3 parameters:
			Copy offset: Move backwards by this amount of bytes and start copying a certain number of bytes following that position.
			Copy length: How many bytes to copy. If the length is larger than the offset, start at the offset again and copy the same values again.
			Proceed length: The number of bytes that were not compressed and can be read directly.

		Note that the offset is defined in regards to the already decompressed data which e.g. does not contain any compression metadata.

		The three values are split up however; while the copy length and proceed length are
		stated together in a single byte, before an uncompressed section, the relevant offset 
		is given after the uncompressed section: 
			Use the proceed length to read the uncompressed data, at which point you arrive at the start of the offset value. 
			Read this value, then move to the offset and copy a number of bytes (given by copy length)
			to the decompressed data. Afterwards, the next copy and proceed length are given and the process starts anew.

		The offset has a constant size of 2 bytes, in little endian.

		The two lengths share the same byte. The first half of the byte belongs to the proceed length,
		whereas the second half belongs to the copy length. 

		When the half-byte of the proceed length is f, then the length is extended by another byte,
		which is placed directly after the byte that contains both lengths. The value of that byte
		is added to the value of the proceed length (i.e. f). However, if the extra byte is ff, one more
		byte is read (and so on) and all values are added together.

		The copy length can be extended in the same manner. However, the possible extra bytes are
		located at the end, right after the offset.
		Additionally, a constant value of 4 is added to obtain the actual copy length.

		Finally, it is possible that a file ends without specifying an offset (as the last few bytes
		in the file were not compressed). The proceed length is not affected by that (and the copy
		length is of no relevance).

		As an example, consider the length byte B2:
			Proceed length: B
			Copy length: 2 + 4 = 6

		Another example, F23C:
			Proceed length: F + 3C = 4B
			Copy length: 2 + 4 = 6
			
		*/
		
		this.seeker = new FileSeeker(); //Keep track of current offset in input file!
		this.output = new ArrayList<Byte>(); //return element
		this.procced = 0;
		first = true;
		int nextAmount = 0;
		boolean extInc = false;
		while (true){
			if (first){ //Start of new LZ4 file ? - procced |..header..|
				byte a = FileHandler.readByte(input, seeker);
				procced = readHeigh(a); //How many bytes should be copied directly from input ? 1111-0000
				nextAmount = readLow(a); //Get next copy lenght.	0000-1111
				if (nextAmount == 0x0F){
					extInc = true; //copy lenght is bigger as 15 -> add byte after offset for next operation.
				}
				nextAmount += 4; //constant of +4
				if (procced == 0x0F){ //procced needs extra byte ?
					while (true){
						int b = FileHandler.readByte(input, seeker) & 0xFF; // get byte as unsigned int
						procced += b;
						if (b!=0xFF){ // is not 255 ? - break out infi. loop
							break;
						}
					}
				}
				first = false; //first done, continue normal!
			}
			int amount = nextAmount; //Previus calculated copy lenght to current one.
			while (procced>0){ //procced stack not emty
				output.add(FileHandler.readByte(input, seeker));
				procced -= 1;
			}
			if (seeker.getOffset()>=input.length){ //EOF
				break;
			}
			if (procced == 0){//begin next operation
				int offset = FileHandler.readShort(input, seeker, ByteOrder.LITTLE_ENDIAN);
				if (extInc){ //is external increased copy lenght ? -> +byte after offset
					while (true){
						int am = FileHandler.readByte(input, seeker) & 0xFF; //unsigned
						amount += am;
						if (am!=0xFF){
							break;
						}
					}
					extInc = false;
				}
				byte b = FileHandler.readByte(input, seeker);
				procced = readHeigh(b);
				nextAmount = readLow(b);
				if (nextAmount == 0x0F){
					extInc = true;
				}
				nextAmount += 4;
				if (procced == 0x0F){
					while (true){
						int a = FileHandler.readByte(input, seeker) & 0xFF; //unsigned
						procced += a;
						if (a!=0xFF){
							break;
						}
					}
				}
				if (!first){ //decompression
					int offsetBuffer = output.size()-offset;
					for (int i=0; i <amount; i++){
						output.add(output.get(offsetBuffer+(i%offset)));
					}
				}
			}
		}
		
		byte[] data = new byte[output.size()];
		for (int i = 0; i < data.length; i++) {
		    data[i] = (byte) output.get(i);
		}
		return data;		
	}
	
	int readHeigh(byte b){
		return b >> 4 & 0xF;
	}
	
	int readLow(byte b){
		return b & 0x0F;
	}
}
