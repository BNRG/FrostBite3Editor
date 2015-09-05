package tk.captainsplexx.Resource.EBX.Structure.Entry;

import java.util.HashMap;

public class EBXObjEnum {
	public HashMap<String, Boolean> values;

	//Constructor
	public EBXObjEnum(HashMap<String, Boolean> streamRealmEnums) {
		this.values = streamRealmEnums;
	}

	//Getter and Setter
	public HashMap<String, Boolean> getValues() {
		return values;
	}
	
	
	
}
