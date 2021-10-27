package modelo;

import org.json.JSONObject;

/**
 * Clase Casilla
 * 
 * Contiene la informacion de cada casilla
 * (tipo de casilla, si esta vacia...)
 * 
 * @author Grupo 5
 *
 */
public class Casilla implements Originator{
	/**
	 * Tipos de casillas
	 */
	public static final int CASILLA_NORMAL = 1;
	public static final int CASILLA_DOBLE_LETRA = 2;
	public static final int CASILLA_TRIPLE_LETRA = 3;
	public static final int CASILLA_DOBLE_PALABRA = 4;
	public static final int CASILLA_TRIPLE_PALABRA = 5;
	
	/**
	 * Ficha sobre la casilla
	 */
	private Ficha ficha;
	/**
	 * Indica si se puede poner o no una ficha en la casilla.
	 * Se actualiza con cada ficha que se pone o se quita de la casilla.
	 */
	private boolean disponible;
	/**
	 * Multiplicador de la puntuacion de la ficha puesta sobre la casilla
	 * para obtener la puntuacion de una palabra.
	 * 
	 * @see Documentos/Reglas.docx
	 */
	private int multiplicador; 
	
	/**
	 * Constructor para una casilla sin ficha
	 * @param multiplicador de la casilla
	 */
	public Casilla(int multiplicador){ 
		this.ficha = null;
		disponible = false;
		this.multiplicador = multiplicador;
	}
	/**
	 * Constructor para una casilla con ficha
	 * @param f ficha sobre la casilla
	 * @param multiplicador de la casilla
	 */
	public Casilla(Ficha f,int multiplicador){
		this.ficha = f;
		this.multiplicador = multiplicador;
	}
	
	public String to_string() {
		if(this.ficha != null) {
			return this.ficha.toString() + " "+this.multiplicador;
		}
		else {
			return "" +this.multiplicador;
		}
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------

	public int getMultiplicador(){
		return this.multiplicador;
	}
	
	public void setMultiplicador(int m) {
		multiplicador = m;
	}

	public void setDisponible(boolean b) {
		disponible = b;
	}
	
	public boolean esDisponible() {
		return disponible;
	}

	public void add(Ficha f) {
		this.ficha = f;
	}
	
	public int getPoints() {
		return this.ficha.getPuntos();
	}
	
	public boolean empty() {
		return ficha == null;
	}
	
	public Ficha getFicha() {
		return ficha;
	}
	
	void remove() {
		this.ficha = null;
	}
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------

	@Override
	public void setMemento(Memento m) {
		disponible = m.getState().getBoolean("disponible");
		
		if(m.getState().has("ficha")) {			
			Memento mementoFicha = new Memento();
			mementoFicha.setState(m.getState().getJSONObject("ficha"));
			this.ficha = new Ficha();
			ficha.setMemento(mementoFicha);	
		}
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonCasilla = new JSONObject();
		
		jsonCasilla.put("disponible", this.disponible);
		
		if(this.ficha != null) {
			jsonCasilla.put("ficha", this.ficha.createMemento().getState());			
		}
		
		memento.setState(jsonCasilla);
		
		return memento;
	}
	
}
