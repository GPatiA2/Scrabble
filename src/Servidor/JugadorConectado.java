package Servidor;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

import modelo.Integrante;
import modelo.Jugador;
/**
 * Clase que guarda el nick del jugador y su PrinterWriter
 *
 */
public class JugadorConectado {
	/**
	 * Nombre del jugador
	 */
	private String nombre;
	/**
	 * Salida del jugador
	 */
	private PrintWriter output;
	/**
	 * Constructor de JugadorConectado. Recibe por argumentos la salida del jugador
	 * @param o PrintWriter
	 */
	public JugadorConectado(PrintWriter o) {
		output = o;
	}
	/**
	 * Devuelve la salida del jugador
	 * @return {@link #output}
	 */
	public PrintWriter getOutputStream() {
		return this.output;
	}
	/**
	 * Devuelve el nombre del jugador
	 * @return {@link #nombre}
	 */
	public String getNick() {
		return nombre;
	}
	/**
	 * Establece el nombre del jugador
	 * @param nombre 
	 */
	public void setNick(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * Sobreescribe equals de Object.
	 * Dos JugadorConectados son iguales si lo son sus nombres
	 * @return <ul>
     *  <li>true: son iguales</li>
     *  <li>false: no son iguales</li>
     *  </ul>
	 */
	@Override
	public boolean equals(Object o) {
		if ( o == this) return true;
		JugadorConectado aux = (JugadorConectado)o;
		if(aux.getNick().equals(this.getNick())) return true;
		
		return false;
		
	}
}
