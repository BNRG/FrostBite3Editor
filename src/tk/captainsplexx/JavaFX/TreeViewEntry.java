package tk.captainsplexx.JavaFX;



import tk.captainsplexx.JavaFX.JavaFXMainWindow.EntryType;
import javafx.scene.image.ImageView;

public class TreeViewEntry implements Cloneable{

	public String name;
	public String tooltip;
	public ImageView graphic;
	public Object value;
	public EntryType type;
	public short ebxType;
	
	public TreeViewEntry(String name, ImageView graphic, Object value, EntryType type) {
		this.name = name;
		this.graphic = graphic;
		this.value = value;
		this.type = type;
		this.ebxType = 0;
		this.tooltip = null;
	}
	
	
	
	public String getTooltip() {
		return tooltip;
	}



	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
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
	
	public short getEBXType() {
		return ebxType;
	}

	public void setEBXType(short ebxType) {
		this.ebxType = ebxType;
	}

	public TreeViewEntry clone(){
		try {
			return (TreeViewEntry) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
