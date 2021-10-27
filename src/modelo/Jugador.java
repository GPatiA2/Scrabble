package modelo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Coordenadas;


/**
 * Clase Jugador
 * 
 * Contiene informacion relativa a un jugador
 * humano
 * 
 * @author Grupo 5
 *
 */
public class Jugador extends Integrante implements Originator {
	/**
	 * Indica si puede actuar
	 */
	private boolean canAct;
	
	/**
	 * Constructor de jugador
	 * @param nick del jugador
	 */
	public Jugador(String nick){
		super();
		this.nick = nick;
	}
	
	/**
	 * Constructor de jugador sin parametros
	 * necesario para cargar una partida
	 */
	public Jugador() { 
		mano = new ArrayList<Ficha>();
	}
	
	@Override
	protected void convertirMonedasPuntos() {
		int m = this.monedas_ganadas - this.monedas_gastadas;
		this.puntos += m*2;
	}
	
	public String toString() {
		String str = "Turno de " + nick + ": " + puntos + " puntos." + "\n";
		
		for (Ficha f : mano) {
			str += f.toString() + " ";
		}
		
		return str;
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------
	
	public String Devuelve() {return nick;}

	@Override
	public void juegaTurno(Turno turno, AdminTurnos admin, List<Coordenadas> listaFichasNoFijas) {
		canAct = true;
	}

	@Override
	public void acabaTurno() {
		canAct = false;
	}

	@Override
	public boolean puedeActuar() {
		return canAct;
	}
	
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------
	
	@Override
	public void setMemento(Memento m) {
		
		if(m.getState().getString("tipo").equals("jugador")) {
			nick = m.getState().getString("nick");
			monedas_gastadas = m.getState().getInt("monedas gastadas");
			monedas_ganadas = m.getState().getInt("monedas ganadas");
			puntos = m.getState().getInt("puntos");
			puedeJugar = m.getState().getBoolean("puedeJugar");
			invertirSentido = m.getState().getBoolean("invertirSentido");
			
			JSONArray jsonArrayMano = m.getState().getJSONArray("mano");
			mano.clear(); //Limpiar mano en caso de que contuviera fichas
			
			for(int i = 0; i < jsonArrayMano.length(); ++i) {
				Memento mementoFicha = new Memento();
				
				mementoFicha.setState(jsonArrayMano.getJSONObject(i));
				Ficha f = new Ficha();
				f.setMemento(mementoFicha); //Establecer estado de la ficha
				mano.add(i, f); //Annadir ficha a la mano del jugador
			}
		}
		
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonJugador = new JSONObject();
		JSONArray jsonArrayMano = new JSONArray();
		
		jsonJugador.put("tipo", "jugador");
		jsonJugador.put("nick", this.nick);
		jsonJugador.put("puntos", this.puntos);
		jsonJugador.put("monedas ganadas", this.monedas_ganadas);
		jsonJugador.put("monedas gastadas", this.monedas_gastadas);
		jsonJugador.put("puedeJugar", this.puedeJugar);
		jsonJugador.put("invertirSentido", this.invertirSentido);
		
		for(Ficha f : this.mano) {
			JSONObject jsonFicha = new JSONObject();
			jsonFicha = f.createMemento().getState();
			jsonArrayMano.put(jsonFicha);
		}
		
		jsonJugador.put("mano", jsonArrayMano);
		
		memento.setState(jsonJugador);
		
		return memento;
	}
	
}
