package modelo;

import org.json.JSONObject;

/*
 * Memento es una clase que almacena el estado de una clase en formato JSON (una clase puede envolver a varias a traves de instancias por lo que
 * un memento puede contener la informacion de otra clase en formato JSON tambien), esto se hace sin romper la encapsulacion a traves de una 
 * cadena de llamadas al estilo de los reports en TP
 * 
 */
public class Memento implements IMemento {

	private JSONObject state;
	
	public Memento() {
		this.state = new JSONObject();
	}
	
	@Override
	public JSONObject getState() {
		return this.state;
	}

	@Override
	public void setState(JSONObject j) {
		this.state = j;
	}

}
