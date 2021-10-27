package modelo;

import java.awt.Point;

import controlador.Controller;
import controlador.Registrador;
import utils.Coordenadas;
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
	public void registerOn(Registrador c);
	
}

