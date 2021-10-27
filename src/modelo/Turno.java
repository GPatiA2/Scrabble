package modelo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.Coordenadas;
/**
 * Esta clase se encarga de comunicar a los jugadores con el Game para ejecutar comandos
 * y jugar los turnos.
 * Ademas se encarga de registrar la informacion de las acciones que realizan los jugadores
 * durante el turno 
 * @author Grupo 5
 *
 */
public class Turno implements Originator{
	/**
	 * Jugador que va a realizar las acciones en el turno
	 * @see Integrante
	 */
	private Integrante integrante;
	/*
	 * Estos atributos almacenan datos de lo sucedido durante la ejecucion del turno 
	 */
	/**
	 * Lista de coordenadas donde se han colocado fichas durante este turno
	 * @see Ficha
	 */
	private List<Coordenadas> listaFichasNoFijas;
	/**
	 * Numero de cambios de una ficha por otra del mazo realizados en este turno
	 */
	private int numeroCambios;
	
	/**
	 * Atributo que indica si el turno ha sido pasado (true) o sigue en curso (false)
	 * @see ComandoPasarTurno
	 */
	private boolean estado;	
	/**
	 * Constructor por defecto, recibe un integrante de la partida
	 * @param j	 Integrante de la partida que realiza las acciones durante el turno
	 * @param admin Administrador de turnos
	 */
	public Turno(Integrante j) {
		System.out.println("TURNO DE "+j.getNick());
		integrante = j;
		estado = false;
		//Inicializo los parametros que indican lo que ha pasado durante el turno
		numeroCambios = 0;
		listaFichasNoFijas = new ArrayList<Coordenadas>();
	}
		
	//--------------METODOS ACCIONES OCURRIDAS DURANTE EL TURNO--------------------

	/**
	 * Inserta unas coordenadas en la lista de coordenadas donde el jugador ha colocado fichas durante este 
	 * turno
	 * @param p Coordenadas a insertar en la lista
	 * @see listaFichasNoFijas
	 */
	public void addFichaPuesta(Coordenadas p) {	
		listaFichasNoFijas.add(p);		
	}
	/**
	 * Elimina unas coordenadas de la lista de coordenadas donde el jugador ha colocado fichas durante este
	 * turno
	 * @param p Coordenadas a eliminar de la lista
	 * @see listaFichasNoFijas
	 */
	public void quitarFichaPuesta(Coordenadas p) {
		listaFichasNoFijas.remove(p);
	}
	/**
	 * Comprueba si unas coordenadas estan en la lista de las coordenadas donde el jugador ha colocado fichas
	 * durante este turno
	 * @param p Coordenadas que se buscan en la lista de fichas
	 * @return b True si las coordenadas estan en la lista / False si no
	 * @see listaFichasNoFijas
	 */
	public boolean puedoQuitar(Coordenadas p) {
		return this.listaFichasNoFijas.contains(p);
	}
	/**
	 * Comprueba si se ha cambiado una ficha de la mano por otra del mazo menos de siete veces durante este
	 * turno
	 * @return b True si el jugador que tiene el turno puede cambiar una ficha de su mano por otra del mazo / False si no
	 * @see numeroCambios
	 */
	public boolean puedoCambiar() {
		return numeroCambios < 7;
	}
	
	/**
	 * Suma 1 al numero de cambios de una ficha de la mano del jugador por otra del mazo
	 * @see numeroCambios
	 */
	public void addCambios() {
		numeroCambios++;
	}
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------
	
	@Override
	public void setMemento(Memento m) {
		
		Memento mementoJugador = new Memento();
		mementoJugador.setState(m.getState().getJSONObject("jugador actual"));
		
		integrante.setMemento(mementoJugador); //Establecer estado del jugador 
		numeroCambios = m.getState().getInt("numeroCambios");
		estado = m.getState().getBoolean("estado");
		
		listaFichasNoFijas.clear(); //Vaciar lista de fichas en caso de que contuviera fichas
		JSONArray jsonArrayFichasNoFijas = m.getState().getJSONArray("listaFichasNoFijas");
		
		for(int i = 0; i < jsonArrayFichasNoFijas.length(); ++i) { 
			Coordenadas c = new Coordenadas();
			Memento mementoCoord = new Memento();
			mementoCoord.setState(jsonArrayFichasNoFijas.getJSONObject(i));
			c.setMemento(mementoCoord); //Establecer estado de las coordenadas
			listaFichasNoFijas.add(i, c); //Añadir a la lista
		}
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonTurno = new JSONObject();
		JSONArray jsonArrayFichasNoFijas = new JSONArray();
		
		jsonTurno.put("jugador actual", this.integrante.createMemento().getState());
		jsonTurno.put("numeroCambios", this.numeroCambios);
		jsonTurno.put("estado", this.estado);
		
		for(Coordenadas c : this.listaFichasNoFijas) {
			JSONObject jsonCoordenadas = new JSONObject();
			jsonCoordenadas = c.createMemento().getState();
			jsonArrayFichasNoFijas.put(jsonCoordenadas);
		}
		
		jsonTurno.put("listaFichasNoFijas", jsonArrayFichasNoFijas);
		
		memento.setState(jsonTurno);
		
		return memento;
		
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------

	
	public void endTurn() {
		estado = true;
		System.out.println("ACABANDO");
	}

	public boolean acabado() {
		return estado;
	}
	
	public List<Coordenadas> getlistaFichasNoFijas() {
		return this.listaFichasNoFijas;
	}

	public boolean valido() {
		return listaFichasNoFijas.size() == 0;
	}

	public void noAcabado() {
		estado = false;
	}


}
