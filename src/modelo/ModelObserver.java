package modelo;

import controlador.Controller;
/**
 * Interfaz que implementan aquellos objetos que necesitan observar al modelo
 * @author Grupo 5
 */
public interface ModelObserver {
	/**
	 * Metodo que sirve para registrar a quien implementa esta interfaz para registrarse como
	 * observador del modelo a traves del controlador que se recibe como parametro
	 * @param c Controlador que hace el papel de intermediario entre interfaz y modelo
	 */
	public void registerOn(Controller c);
	
}

