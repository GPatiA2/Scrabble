package modelo;

import org.json.JSONObject;

public interface IMemento {

	public JSONObject getState();
	
	public void setState(JSONObject j);
}
