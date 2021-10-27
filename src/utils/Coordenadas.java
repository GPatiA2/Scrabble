package utils;

import org.json.JSONObject;

import modelo.IMemento;
import modelo.Memento;
import modelo.Originator;

public class Coordenadas implements Originator {
	
	protected static final int COLS = 15;
	protected static final int ROWS = 15;
	
	private int fila,columna;
	
	public Coordenadas() {
		fila = 0; columna =0;
	}
	public Coordenadas (int f, int c) {
		
		fila = f; columna = c;
	}
	
	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna;
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
	
	
	public static boolean checkCommand(int x, int y) { //Comprueba que las coordenadas introducidad son correctas
		return x < COLS && x >= 0 && y < ROWS && y >= 0;
	}
	
	public String toString(){
		return "" + (int)(fila) + " " + (columna);
	}
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
	public void corregir() {
		this.columna--;
		this.fila--;
	}
 
}
