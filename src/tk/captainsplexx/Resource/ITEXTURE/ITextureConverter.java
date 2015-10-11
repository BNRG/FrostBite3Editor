package tk.captainsplexx.Resource.ITEXTURE;

import java.nio.ByteOrder;

import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.DDS.DDS_HEADER;
import tk.captainsplexx.Resource.DDS.DDS_PIXELFORMAT;

public class ITextureConverter {
	
	public static DDS_HEADER getDDSHeader(ITexture itexture){
		DDS_HEADER header = new DDS_HEADER();
		header.setDwSize(124);
		header.setDwFlags(659471);
		header.setDwHeight(itexture.getHeight());
		header.setDwWidth(itexture.getWidth());
		header.setDwPitchOrLinearSize(itexture.getMipSizes()[0]);
		header.setDwDepth(itexture.getDepth());
		header.setDwMipMapCount(itexture.getNumSizes());
		header.setDwReserved1(new int[11]);
		for (int i=0; i<header.getDwReserved1().length;i++){
			header.getDwReserved1()[i] = 0;
		}
		header.setDwCaps(4096);
		header.setDwCaps2(0);
		
		/*PixelFormat*/
		DDS_PIXELFORMAT pixelformat = new DDS_PIXELFORMAT(
				/*dwSize*/ 32,
				/*dwFlags*/ 4,
				/*dwFourCC*/ 827611204,
				/*dwRGBBitCount*/ 0,
				/*dwRBitMask*/ 0,
				/*dwGBitMask*/ 0,
				/*dwBBitMask*/ 0,
				/*dwABitMask)*/ 0
		);
		
		if (itexture.getTextureType()==ITexture.TT_Cube){
			header.setDwCaps2(65024);
			header.setDwCaps(header.getDwCaps() | 8);
		}
		
		if (!ITexture.PixelFormatTypes.containsKey(itexture.getPixelFormat())){
			
			switch (itexture.getPixelFormat()){
				case ITexture.TF_ABGR32F:
					pixelformat.setDwFourCC(0);
					pixelformat.setDwRGBBitCount((itexture.getPixelFormat() == ITexture.TF_ABGR32F) ? 128 : 32);
					pixelformat.setDwGBitMask(16711680);
					pixelformat.setDwGBitMask(65280);
					pixelformat.setDwBBitMask(-16777216);
					pixelformat.setDwFlags(65);
					
					header.setDwFlags(-524289);
					header.setDwFlags(header.getDwFlags() | 8);
					break;
				case ITexture.TF_L8:
					pixelformat.setDwFourCC(0);
					pixelformat.setDwRGBBitCount(8);
					pixelformat.setDwABitMask(255);
					pixelformat.setDwFlags(2);
					
					header.setDwFlags(-524289);
					header.setDwFlags(header.getDwFlags() | 8);
					break;
				case ITexture.TF_L16:
					pixelformat.setDwFourCC(0);
					pixelformat.setDwRGBBitCount(16);
					pixelformat.setDwRBitMask(65535);
					pixelformat.setDwFlags(131072);
					
					header.setDwFlags(-524289);
					header.setDwFlags(header.getDwFlags() | 8);
					break;
				case ITexture.TF_ARGB8888:
					break;
				case ITexture.TF_ABGR16:
					break;
				case ITexture.TF_ABGR16F:
					break;
			}
		}else{
			System.err.println("ITextureConverter needs help Line:84");
			/*header.getPixelformat().setDwFourCC(ITexture.PixelFormatTypes.get(itexture.getPixelFormat()));*/
			if (itexture.getPixelFormat() == ITexture.TF_DXT1A){
				header.getPixelformat().setDwFlags(header.getPixelformat().getDwFlags() | 1);
			}
		}
		header.setPixelformat(pixelformat);
		
		
		return header;
	}
	
	public static ITexture getITextureHeader(byte[] ddsFile, ITexture originalITextureHeader, String newGUID){
		DDS_HEADER ddsHeader = new DDS_HEADER(ddsFile, null);
		ITexture itexture = new ITexture();
		
		itexture.setFirstMip(originalITextureHeader.getFirstMip());
		itexture.setNumSizes((byte) 0x1);
		
		if (originalITextureHeader.getTextureType() == ITexture.TF_NormalDXT1 
				&& ddsHeader.getPixelformat().getDwFourCC() != ITexture.DDS_DXT1)
		{
			System.err.println("DDS does NOT MATCH DXT1 normalmap.\n"
							 + "Make sure to use the original Format.\n"
							 + "--Canceling process!--");
			return null;
		}
		switch (ddsHeader.getPixelformat().getDwFourCC()){
			case 0:
				switch (ddsHeader.getPixelformat().getDwRGBBitCount()){
					case 8:
						itexture.setPixelFormat(ITexture.TF_L8);
						break;
					case 16:
						itexture.setPixelFormat(ITexture.TF_L16);
						break;
					case 128:
						itexture.setPixelFormat(ITexture.TF_ABGR32F);
						break;
					default:
						itexture.setPixelFormat(ITexture.TF_ARGB8888);
						break;
				}
				break;
			case ITexture.DDS_ABGR32F:
				itexture.setPixelFormat(ITexture.TF_ABGR32F);
				break;
			case ITexture.DDS_DXT1:
				itexture.setPixelFormat(ITexture.TF_NormalDXT1);
				break;
			case ITexture.DDS_NormalDXN:
				itexture.setPixelFormat(ITexture.TF_NormalDXN);
				break;
			case ITexture.DDS_DXT5:
				itexture.setPixelFormat(ITexture.TF_DXT5);
				break;
			default:
				itexture.setPixelFormat(ITexture.TF_Unknown);
				System.err.println("DDS File has an unknown format. Make sure to use a supported one!");
				return null;
		}
		
		itexture.setFirstMip((byte) 0x00);
		System.err.println("DSS->ITexture is using FirstMapLevel 0! - This value may needs to be 1");
		
		itexture.setTextureType(((ddsHeader.getDwCaps2() == 65024) ? ITexture.TT_Cube : ITexture.TT_2d));
		
		if (originalITextureHeader.getTextureType() == ITexture.TT_Cube && ddsHeader.getDwCaps2() != 65024)
		{
			System.err.println("Original texture type is a cubemap! - Can't convert to DDS!");
			return null;
		}
		
		itexture.setNameHash(originalITextureHeader.getNameHash());
		itexture.setWidth((short) ddsHeader.getDwWidth());
		itexture.setHeight((short) ddsHeader.getDwHeight());
		itexture.setDepth((byte) 0x1);
		itexture.setSliceCount((short) 1);
		itexture.setNumSizes((byte) ddsHeader.getDwMipMapCount());
		
		itexture.setChunkSize(ddsFile.length-128/*Substract Header with FourCC*/);
		itexture.setName(originalITextureHeader.getName());
		
		
		itexture.setMipSizes(new int[15]);
		int num = ddsHeader.getDwWidth();
		int num2 = ddsHeader.getDwHeight();
		if (ddsHeader.getPixelformat().getDwFourCC() == 0)
		{
			int num3 = 0;
			switch (itexture.getPixelFormat())
				{
				case ITexture.TF_ABGR16F:
					for (int i = 0; i < ddsHeader.getDwMipMapCount(); i++)
					{
						int num4 = num;
						int num5 = num2;
						num4 = ((num4 < 1) ? 1 : num4);
						num5 = ((num5 < 1) ? 1 : num5);
						itexture.getMipSizes()[i] = (num4 * num3 + 7) / 8 * num5;//this makes no sense num3
						num >>= 1;
						num2 >>= 1;
					}
					break;
			}
		}
		else
		{
			int num6 = 8;
			int pixelFormat = itexture.getPixelFormat();
			if (pixelFormat == ITexture.TF_DXT5 || pixelFormat == ITexture.TF_NormalDXN)
			{
				num6 = 16;
			}
			for (int j = 0; j < ddsHeader.getDwMipMapCount(); j++)
			{
				int num7 = (num + 3) / 4;
				int num8 = (num2 + 3) / 4;
				num7 = ((num7 < 1) ? 1 : num7);
				num8 = ((num8 < 1) ? 1 : num8);
				itexture.getMipSizes()[j] = num7 * num6 * num8;
				num >>= 1;
				num2 >>= 1;
			}
		}
		
		itexture.setChunkID(FileHandler.hexStringToByteArray(newGUID));
		System.out.println("ITexture Header created for Chunk "+newGUID);
		return itexture;
	}
	
	
	public static byte[] getBlockData(byte[] ddsFileBytes){
		if (ddsFileBytes!=null){
			//DDS Header has a size of 0x80!
			byte[] blockData = new byte[ddsFileBytes.length-0x80];
			for (int i=0; i<blockData.length; i++){
				blockData[i] = ddsFileBytes[i+0x80];
			}			
			return blockData;
		}else{
			System.err.println("DDS File can not be null. (ITextureConverter.getBlockData)!");
			return null;
		}
	}

}
