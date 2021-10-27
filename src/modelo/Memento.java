package modelo;

import org.json.JSONObject;

/**
 * Clase Memento
 * 
 * Memento es una clase que almacena el estado de una clase en formato JSON 
 * (una clase puede envolver a varias a traves de instancias por lo que
 * un memento puede contener la informacion de otra clase en formato JSON tambien),
 * esto se hace sin romper la encapsulacion a traves de una 
 * cadena de llamadas al estilo de los reports en TP
 * 
 * @author Grupo 5
 * 
 */
public class Memento implements IMemento {

	/**
	 * Estado almacenado en un JSONObject
	 */
	private JSONObject state;
	
	/**
	 * Constructor del memento
	 */
	public Memento() {
		this.state = new JSONObject();
	}
	
	/**
	 * Devuelve el estado del memento en formato JSONObject
	 */
	@Override
	public JSONObject getState() {
		return this.state;
	}

	/**
	 * Almacena el estado del memento en formato JSONObject
	 */
	@Override
	public void setState(JSONObject j) {
		this.state = j;
	}

}
