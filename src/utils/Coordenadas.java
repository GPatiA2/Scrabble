package utils;

import org.json.JSONObject;

import modelo.Memento;
import modelo.Originator;

/**
 * Clase Coordenadas
 * 
 * Esta clase almacena informacion relativa a las coordenadas
 * dentro del tablero, asi como operaciones auxiliares sobre
 * sus atributos
 * 
 * @author Grupo 5
 *
 */
public class Coordenadas implements Originator {
	
	//Atributos 
	
	/**
	 * Dimensiones del tablero
	 */
	protected static final int COLS = 15;
	protected static final int ROWS = 15;
	
	/**
	 * Fila en el tablero
	 */
	private int fila;
	/**
	 * Columna en el tablero
	 */
	private int columna;
	
	/**
	 * Constructor vacio que asigna coordenadas (0,0)
	 */
	public Coordenadas() {
		fila = 0; columna =0;
	}
	/**
	 * Constructor con parametros
	 * @param f fila
	 * @param c columna
	 */
	public Coordenadas (int f, int c) {
		fila = f; columna = c;
	}
	
	
	/**
	 * Corrige las diferencias de enumeracion entre el usuario y
	 * la implementacion del tablero. El tablero empieza a enumerar desde
	 * el 0, mientras que el usuario empieza a enumerar desde el 1.
	 */
	public void corregir() {
		this.columna--;
		this.fila--;
	}
	
	public boolean menorOIgual(Coordenadas c) {
		//Coordenadas en horizontal
		if(fila == c.fila && columna <= c.columna) {
			return true;
		}
		//Coordenadasd en vertical
		else if (columna == c.columna && fila <= c.fila) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean mayorOIgual(Coordenadas c) {
		//Coordenadas en horizontal
		if(fila == c.fila && columna >= c.columna) {
			return true;
		}
		//Coordenadasd en vertical
		else if (columna == c.columna && fila >= c.fila) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + columna;
		result = prime * result + fila;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordenadas other = (Coordenadas) obj;
		if (columna != other.columna)
			return false;
		if (fila != other.fila)
			return false;
		return true;
	}
	
	/**
	 * Comprueba que las coordenadas introducidas son correctas, es decir,
	 * que no se salen del tablero
	 */
	public static boolean checkCommand(int x, int y) { 
		return x < COLS && x >= 0 && y < ROWS && y >= 0;
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...--------------------

	public int getFila() {
		return fila;
	}
		
	public int getColumna() {
		return columna;
	}
	
	public String toString(){
		return "" + (int)(fila) + " " + (columna);
	}
	
	
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------

	@Override
	public void setMemento(Memento m) {
		fila = m.getState().getInt("fila");
		columna = m.getState().getInt("columna");
	}
	
	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonCoordenadas = new JSONObject();
	
		jsonCoordenadas.put("fila", this.fila);
		jsonCoordenadas.put("columna", this.columna);
		
		memento.setState(jsonCoordenadas);
		
		return memento;
	}
	
 
}
