package modelo;

import org.json.JSONObject;

/* Esta clase se utilizar� en futuros splints para derivar de ella las diferentes casillas que existen en el juego asi como las 
 * que consideremos meter
 */

public class Casilla implements Originator{
	public static final int CASILLA_NORMAL = 1;
	public static final int CASILLA_DOBLE_LETRA = 2;
	public static final int CASILLA_TRIPLE_LETRA = 3;
	public static final int CASILLA_DOBLE_PALABRA = 4;
	public static final int CASILLA_TRIPLE_PALABRA = 5;
	
	
	private Ficha ficha;	
	//Este boolean se actualiza a cada ficha que se pone o quita para las casillas afectadas
	//Indica si puedo poner o no una ficha en esta casilla
	private boolean disponible;
	
	private int multiplicador; 
	
	public Casilla(int puntos){ //constructor vacio por si es una casilla vacia;
		this.ficha = null;
		disponible = false;
		this.multiplicador = puntos;
	}
	public Casilla(Ficha f,int puntos){
		this.ficha = f;
		this.multiplicador = puntos;
	}
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
	
	public String to_string() {
		if(this.ficha != null) {
			return this.ficha.toString() + " "+this.multiplicador;
		}
		else {
			return "" +this.multiplicador;
		}
	}
	
	void remove() {
		this.ficha = null;
	}
	
	@Override
	public void setMemento(Memento m) {
		disponible = m.getState().getBoolean("disponible");
		multiplicador = m.getState().getInt("multiplicador");
		Memento mementoFicha = new Memento();
		mementoFicha.setState(m.getState().getJSONObject("ficha"));
		this.ficha = new Ficha();
		ficha.setMemento(mementoFicha);	
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonCasilla = new JSONObject();
		
		jsonCasilla.put("multiplicador", this.multiplicador);
		jsonCasilla.put("disponible", this.disponible);
		jsonCasilla.put("ficha", this.ficha.createMemento().getState());
		
		memento.setState(jsonCasilla);
		
		return memento;
	}
	
}
