package tk.captainsplexx.JavaFX.CellFactories;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import tk.captainsplexx.Game.Main;
import tk.captainsplexx.JavaFX.ModLoaderController;
import tk.captainsplexx.Mod.Mod;
import tk.captainsplexx.Resource.FileHandler;

public class ModLoaderListFactory extends ListCell<Mod>{

	public ModLoaderListFactory(){
		setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				Mod mod = getItem();
				ModLoaderController ctrlr = Main.getJavaFXHandler().getMainWindow().getModLoaderController();
				if (mod != null){
					Main.getGame().setCurrentMod(mod);
					Main.getModTools().getPackages().clear();
					Main.getModTools().fetchPackages();
					
					ctrlr.getModName().setText(mod.getName());
					ctrlr.getAuthorName().setText(mod.getAuthor()+" "+mod.getGame());
						
					ctrlr.getDesc().setWrapText(true);
					ctrlr.getDesc().setText(mod.getDesc());
					File image = new File(mod.getPath()+"/logo.png");
					if (image.exists()){
						ctrlr.getLogo().setImage(new Image(FileHandler.getStream(image.getAbsolutePath())));
					}else{
						ctrlr.getLogo().setImage(null);
					}
					ctrlr.getRunEditor().setDisable(false);
				}else{
					ctrlr.getRunEditor().setDisable(true);
					Main.getGame().setCurrentMod(null);
					ctrlr.getModName().setText("No mod currently selected!");
					ctrlr.getAuthorName().setText("");
						
					ctrlr.getDesc().setWrapText(true);
					ctrlr.getDesc().setText("");
					ctrlr.getLogo().setImage(null);
				}
			}
		});
	}
	
	@Override
	protected void updateItem(Mod item, boolean empty) {
		super.updateItem(item, empty);
		if (!empty){
			setText(item.getFolderName());
		}else{
			setText(null);
		}
	}
	
	

}
