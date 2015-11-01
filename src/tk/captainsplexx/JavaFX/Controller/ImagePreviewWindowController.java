package tk.captainsplexx.JavaFX.Controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import tk.captainsplexx.Game.Core;
import tk.captainsplexx.Mod.ModTools;
import tk.captainsplexx.Mod.Package;
import tk.captainsplexx.Resource.FileHandler;
import tk.captainsplexx.Resource.CAS.CasDataReader;
import tk.captainsplexx.Resource.ITEXTURE.ITexture;
import tk.captainsplexx.Resource.ResourceHandler.LinkBundleType;
import tk.captainsplexx.Resource.ResourceHandler.ResourceType;
import tk.captainsplexx.Resource.TOC.ResourceLink;

public class ImagePreviewWindowController {
	@FXML
	ImageView imageView;
	
	private ResourceLink resourceLink;
	private File ddsFile;
	
	private Stage parentStage;
	
	public void importImage(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Image");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("DirectDraw Surface", "*.dds"));
        
        File importFile = fileChooser.showOpenDialog(parentStage);
		if (importFile!=null){
			if (importFile.exists()){
				String resPath = resourceLink.getName()+".dds";		
				String curToc = Core.getGame().getCurrentToc().getName();
				Package pack = Core.getModTools().getPackage(curToc);
				if (pack!=null){
					boolean succ = Core.getModTools().extendPackage(
							LinkBundleType.BUNDLES,
							Core.getGame().getCurrentSB().getPath(), 
							ResourceType.ITEXTURE,
							resPath,
							pack
					);
					if (succ){
						byte[] fileBytes = FileHandler.readFile(importFile.getAbsolutePath());
						if (fileBytes!=null){
							FileHandler.writeFile(Core.getGame().getCurrentMod().getPath()+ModTools.RESOURCEFOLDER+resPath, fileBytes);
							
							
							//This will be moved over into main save.
							Core.getModTools().writePackages();
							System.out.println("Image successfully imported!");
						}else{
							System.err.println("Error! Could not read the file.. Permission Denied!");
						}
					}else{
						System.err.println("The Package could not get extended!...");
					}
				}else{
					System.err.println("Error! Package not found to put in.");
				}		
			}else{
				System.err.println("Hey mate, why u did this ?? :) The selected file does not exist!");
			}
		}else{
			System.err.println("No File Selected!");
		}
	}
	
	public boolean showInformations(){
		if (resourceLink!=null){
			
			byte[] itexture = CasDataReader.readCas(resourceLink.getBaseSha1(),
					resourceLink.getDeltaSha1(),
					resourceLink.getSha1(),
					resourceLink.getCasPatchType());
			
			ITexture itextureHeader = new ITexture(itexture, null);
			
			
			Core.getJavaFXHandler().getDialogBuilder().showInfo("Info",
					"ITexture Entry: \n"+
					"  SHA1: "+resourceLink.getSha1()+"\n"+
					"  BaseSHA1: "+resourceLink.getBaseSha1()+"\n"+
					"  DeltaSHA1: "+resourceLink.getDeltaSha1()+"\n"+
					"  OriginalSize: "+resourceLink.getOriginalSize()+" Bytes\n"+
					"  CasPatchType: "+resourceLink.getCasPatchType()+"\n"+
					"  ResMeta : "+FileHandler.bytesToHex(resourceLink.getResMeta())+"\n"+
					"  ResRid: "+resourceLink.getResRid()+"\n"+
					"  ResType: "+resourceLink.getResType()+"\n\n"+
					
					"ITexture: \n"+
					"  GUID: "+FileHandler.bytesToHex(itextureHeader.getChunkID())+"\n"+
					"  Height: "+itextureHeader.getHeight()+" Pixels\n"+
					"  Width: "+itextureHeader.getWidth()+" Pixels\n"+
					"  Mips: "+itextureHeader.getNumSizes()+"\n"+
					"  First Mip: "+itextureHeader.getFirstMip()+"\n"+
					"  PixelFormat: "+itextureHeader.getPixelFormat()+"\n"+
					"  PixelType: "+itextureHeader.getTextureType(),
				null, null);
			return true;
		}
		return false;
	}
	
	public boolean exportImage(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        //resourceLink.setName("asdf");
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("DirectDraw Surface", "*.dds"));
        fileChooser.setInitialFileName(resourceLink.getName().replace("/", "_"));
        
        File exportfile = fileChooser.showSaveDialog(parentStage);
        if (exportfile!=null){
        	if (exportfile.exists()){
        		System.err.println("File does already exist!\nSry, JavaFX's FileChooser has no option to test if override was yes :((");
        		return false;
        	}
        	if (ddsFile!=null){
	        	byte[] tempDDS = FileHandler.readFile(ddsFile.getAbsolutePath());
	        	if (tempDDS!=null){
	        		FileHandler.writeFile(exportfile.getAbsolutePath(), tempDDS);
	        	}else{
	        		System.err.println("Temporary DDS file was not found!");
	        	}
        	}else{
        		System.err.println("ImagePreview Export debug file save.");
        		FileHandler.writeFile(exportfile.getAbsolutePath(), new byte[]{(byte) 0xB0, (byte) 0x0B});
        	}
        }
        return true;
	}
	
	public void close(){
		Core.getJavaFXHandler().getMainWindow().destroyImagePreviewWindow(parentStage);
		parentStage.close();
	}

	public ImageView getImageView() {
		return imageView;
	}

	public Stage getParentStage() {
		return parentStage;
	}

	public void setParentStage(Stage parentStage) {
		this.parentStage = parentStage;
	}

	public void setResourceLink(ResourceLink resourceLink) {
		this.resourceLink = resourceLink;
	}

	public void setDdsFile(File ddsFile) {
		this.ddsFile = ddsFile;
	}

	public ResourceLink getResourceLink() {
		return resourceLink;
	}

	public File getDdsFile() {
		return ddsFile;
	}
	
	
}
