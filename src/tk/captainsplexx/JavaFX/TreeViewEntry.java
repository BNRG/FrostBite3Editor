package tk.captainsplexx.JavaFX;

import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import javafx.scene.image.Image;

public class TreeViewEntry {
	public String name;
	public Image graphic;
	public Object value;
	public EntryType type;
	
	
	
	public TreeViewEntry(String name, Image graphic, Object value,
			EntryType type) {
		super();
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
	public Image getGraphic() {
		return graphic;
	}
	public void setGraphic(Image graphic) {
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
