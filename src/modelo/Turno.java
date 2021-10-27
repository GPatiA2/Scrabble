package modelo;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import Command.Command;
import Command.CommandGenerator;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import modelo.Estrategias.RunStrategy;
import utils.Coordenadas;
import utils.ScoredWord;
/**
 * Esta clase se encarga de comunicar a los jugadores con el Game para ejecutar comandos
 * y jugar los turnos
 * @author Grupo 5
 *
 */
public class Turno implements Originator{
	/**
	 * Jugador que va a realizar las acciones en el turno
	 * @see Integrante
	 * @see Game
	 */
	private Integrante integrante;
	/**
	 * Via de entrada para los comandos (consola)
	 */
	private Scanner in;
	/**
	 * Clase que encapsula las reglas del juego
	 * @see Game
	 */
	private Game game;
	/**
	 * Clase encargada de generar los turnos y manejar el orden de 
	 * los jugadores 
	 * @see AdminTurnos
	 */
	private AdminTurnos tManager;
	
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
	private int numeroCambios = 0;
	/**
	 * Estrategia empleada por el jugador para realizar acciones durante el turno
	 * @see Integrante
	 */
	private RunStrategy estrategia;
	
	
	private boolean continuar;
	
	/**
	 * Constructor por defecto, recibe un integrante de la partida, una via de entrada para comandos
	 * , la clase que contiene las reglas del juego, y la clase encargada de manejar los turnos
	 * @param j	 Integrante de la partida que realiza las acciones durante el turno
	 * @param in Via de entrada para los comandos (consola)
	 * @param g  Reglas del juego
	 * @param at Clase que crea los turnos y controla el fin de la partida
	 */
	public Turno(Integrante j, Scanner in, Game g, AdminTurnos at) {
		integrante = j;
		this.in = in;
		game = g;
		tManager = at;
		continuar = true;
		//Inicializo los parametros que indican lo que ha pasado durante el turno
		numeroCambios = 0;
		listaFichasNoFijas = new ArrayList<Coordenadas>();
		//Para cada turno, se le indica al game que jugador esta jugando ahora, para que el game sepa
		//	sobre que jugador tiene que actuar
		game.setPlayer(integrante);
	}
	
	public Turno(Scanner in, Game g, AdminTurnos at) {
		integrante = new Integrante();
		this.in = in;
		game = g;
		tManager = at;
		listaFichasNoFijas = new ArrayList<Coordenadas>();
		continuar = true;
	}
	
	/**
	 * Ejecucion de los turnos
	 * @return b True si el turno es significativo para el final de la partida / False si no lo es
	 * @throws CommandExecuteException
	 * @throws FileNotFoundException
	 */
	public boolean run() throws CommandExecuteException, FileNotFoundException {
		
		boolean continuar = true;
		
		//Para eso vale este valor, cuenta las iteraciones, y si es 1, se ha pasado el turno nada mas 
		//recibirlo y cuenta para  acabar la partida.
		int iter = 0;  
		
		//Se rellena la mano del jugador
		//La maquina, durante su turno, devuelve esas fichas al mazo porque no las usa
		game.update();
		
		//Como puse en mi diagrama, el bucle del turno continua obedeciendo a lo que devuelven los comandos
		
			while(continuar) {
				
				List<Command> lista_comandos = new ArrayList<Command>();
				try {
					lista_comandos = this.integrante.realizarJugada(this.game, this.tManager, this, this.in, this.listaFichasNoFijas);
					
					for (Command comando : lista_comandos) {
						continuar = comando.perform(game, this);
						iter++;
					}
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		
		
		return iter == 1;
	
	}
	
	
	
	//	Manejo de acciones que han sucedido durante el turno
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
	
	public void guardarPartida(String fichero) throws FileNotFoundException {
		this.tManager.guardarMemento();
		this.tManager.guardarPartida(fichero);
	}

	//metodos de la interfaz Originator
	
	@Override
	public void setMemento(Memento m) {
		Memento mementoJugador = new Memento();
		mementoJugador.setState(m.getState().getJSONObject("jugador"));
		integrante.setMemento(mementoJugador); //Establecer estado del jugador 
		numeroCambios = m.getState().getInt("numeroCambios");
		
		listaFichasNoFijas.clear(); //Vaciar lista de fichas en caso de que contuviera fichas
		JSONArray jsonArrayFichasNoFijas = m.getState().getJSONArray("listaFichasNoFijas");
		for(int i = 0; i < jsonArrayFichasNoFijas.length(); ++i) { 
			Coordenadas c = new Coordenadas();
			Memento mementoCoord = new Memento();
			mementoCoord.setState(jsonArrayFichasNoFijas.getJSONObject(i));
			c.setMemento(mementoCoord); //Establecer estado de las coordenadas
			listaFichasNoFijas.add(i, c); //A�adir a la lista
		}
		
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonTurno = new JSONObject();
		JSONArray jsonArrayFichasNoFijas = new JSONArray();
		
		jsonTurno.put("jugador", this.integrante.createMemento().getState());
		jsonTurno.put("numeroCambios", this.numeroCambios);
		
		for(Coordenadas c : this.listaFichasNoFijas) {
			JSONObject jsonCoordenadas = new JSONObject();
			jsonCoordenadas = c.createMemento().getState();
			jsonArrayFichasNoFijas.put(jsonCoordenadas);
		}
		
		jsonTurno.put("listaFichasNoFijas", jsonArrayFichasNoFijas);
		
		memento.setState(jsonTurno);
		
		return memento;
		
	}
	
	public void endTurn() {
		continuar = false;
	}

	public List<Coordenadas> getlistaFichasNoFijas() {
		return this.listaFichasNoFijas;
	}


}
package modelo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import utils.Coordenadas;
/**
 * Esta clase se encarga de comunicar a los jugadores con el Game para ejecutar comandos
 * y jugar los turnos
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
	 */
	public Turno(Integrante j) {
		integrante = j;
		estado = false;
		//Inicializo los parametros que indican lo que ha pasado durante el turno
		numeroCambios = 0;
		listaFichasNoFijas = new ArrayList<Coordenadas>();
		j.juegaTurno();
	}
	
	public Turno(Integrante j, int cambios) {
		integrante = new Integrante();
		listaFichasNoFijas = new ArrayList<Coordenadas>();
		estado = false;
		numeroCambios = cambios;
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
		mementoJugador.setState(m.getState().getJSONObject("jugador"));
		
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
			listaFichasNoFijas.add(i, c); //A�adir a la lista
		}
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonTurno = new JSONObject();
		JSONArray jsonArrayFichasNoFijas = new JSONArray();
		
		jsonTurno.put("jugador", this.integrante.createMemento().getState());
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
