package modelo;

import org.json.JSONObject;

/**
 * Interfaz que implementan los objetos que estan
 * relacionados con la aplicacion del patron 
 * Memento para guardar y cargar una partida
 * 
 * @author Grupo 5
 *
 */
public interface IMemento {

	/**
	 * Devuelve el estado del memento
	 * @return JSONObject
	 */
	public JSONObject getState();
	/**
	 * Almacena el estado del memento
	 * @param JSONObject
	 */
	public void setState(JSONObject j);
}
