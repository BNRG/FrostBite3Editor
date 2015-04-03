package tk.captainsplexx.JavaFX;



import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import javafx.scene.image.ImageView;

public class TreeViewEntry{

	public String name;
	public ImageView graphic;
	public Object value;
	public EntryType type;
	
	public TreeViewEntry(String name, ImageView graphic, Object value, EntryType type) {
		this.name = name;
		this.graphic = graphic;
		this.value = value;
		this.type = type;
	}
	
	
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public ImageView getGraphic() {
		return graphic;
	}
	public void setGraphic(ImageView graphic) {
		this.graphic = graphic;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public EntryType getType() {
		return type;
	}
	public void setType(EntryType type) {
		this.type = type;
	}

	
}
