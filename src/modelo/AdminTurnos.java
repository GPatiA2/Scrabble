package modelo;


import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Command.Command;
import Excepciones.CommandExecuteException;

/**
 * Clase AdminTurnos
 * 
 * Esta clase es la encargada de administrar los turnos respecto de una lista de jugadores
 * recibida
 * 
 * @author Grupo 5
 *
 */
public class AdminTurnos implements Originator , Observable<TManagerObserver>{
	
	private static final int MAXMANO = 7;

	/**
	 * Lista de jugadores que participan en la partida
	 * @see Integrante
	 */
	private List<Integrante> jugadores;
	/**
	 * Objeto que encapsula la l�gica que refiere a las reglas del juego
	 * @see Game
	 */
	private Game game;
	
	/**
	 * Objeto que representa el tablero fisico en el que los integrantes de la partida ponen fichas
	 * @see Tablero
	 */
	private Tablero tablero;
	
	/**
	 * Conjunto de fichas que roban los jugadores para utilizarlas
	 * @see Mazo
	 */
	private Mazo mazo;
	
	/**
	 * Coleccion de palabras validas en el juego
	 * @see ScoreWord
	 * @see Diccionario
	 */
	private Diccionario diccionario;
	
	/**
	 * Integrante de la partida que tiene el turno actual
	 * @see Turno
	 */
	private Integrante jugando;
	/**
	 * Indice de la lista de jugadores que indica el jugador que tiene el turno actual
	 * @see Integrante
	 * @see Turno
	 */
	private int actTurn;
	/**
	 * Entero que indica la cantidad de turnos que hay que tener en cuenta para la finalizacion de la partida
	 * @see #actPasados(boolean)
	 */
	private int pasados;
	/**
	 * Objeto que encapsula la logica y la informacion de lo sucedido durante un turno
	 * @see Turno
	 */
	private Turno turnoAct;

	/**
	 * Lista de observadores de este objeto
	 * @see TManagerObserver
	 */
	private List<TManagerObserver> observers;
	
	/**
	 * Variable que indica en que orden avanzan los turnos en la partida
	 * False indica orden inverso (restando al indice)
	 * True indica orden normal (sumando al indice)
	 * @see ComandoInvertirSentido
	 */
	private boolean orden;
	
	/**
	 * Variable que indica si se va a saltar al jugador siguiente
	 * False no se va a saltar al siguiente jugador
	 * True  se salta al siguiente jugador
	 * @see ComandoSaltarTurno
	 */
	private boolean saltando; 
	
	/**
	 * Constructor de la clase
	 * @param g	Clase que se encarga de manejar el traspaso de fichas entre el mazo, el tablero, y el jugador en activo
	 * @param lJugadores Lista de los jugadores que participan en la partida
	 * @param in Via de entrada para los comandos
	 * @param g_m Clase encargada de generar el mazo
	 * @throws IOException 
	 * 
	 * @see Integrante
	 * @see Game
	 * @see GeneradorMazo
	 * @see GeneradorDiccionario
	 */
	public AdminTurnos(GeneradorMazo g_m , GeneradorDiccionario g_d, List<Integrante> lJugadores) throws IOException {
		//Se inicializan los parametros del administrador
		game = new Game(mazo, g_d);
		jugadores = lJugadores;
		tablero = new Tablero();
		mazo = g_m.generate();
		diccionario = g_d.generate();
		actTurn = firstTurn();
		jugando = jugadores.get(actTurn);
		orden = true;
		turnoAct = new Turno(jugando);
		pasados = 0;
		observers = new ArrayList<TManagerObserver>();
		
		rellenarManos();
		
	}
	
	/**
	 * Constructor usado para cargar partidas
	 * @param g  Clase que se encarga de manejar el traspaso de fichas entre el mazo, el tablero, y el jugador en activo
	 * @param in Via de entrada para los comandos
	 * @see Integrante
	 * @see Game
	 */
	public AdminTurnos(Memento m) { //Este constructor se utiliza para cargar una partida
		this.setMemento(m);
	}
	
	/**
	 * Genera aleatoriamente un numero entre 0 y jugadores.size()-1 que indica la posicion del jugador que incia la partida en la 
	 * lista de jugadores
	 * @return index Posicion del jugador que inicia la partida en la lista de jugadores
	 * 
	 * @see jugadores
	 */
	private int firstTurn() {
		//He utilizado la funcion Math.random para generar un numero aleatorio entre 0 y el numero de jugadores - 1
		return (int)(Math.random()*(jugadores.size()-1));
	}
	
	/**
	 * Genera el turno para el siguiente jugador y lo ejecuta
	 * Cuando acaba la ejecucion del turno se actualizan los turnos que cuentan para acabar la partida en funcion del resultado
	 * de la ejecucion del turno
	 * @return b Indica si la partida ha terminado o no
	 * @throws CommandExecuteException
	 * @throws FileNotFoundException
	 */
	public void sigTurno() throws CommandExecuteException, FileNotFoundException {
		
		rellenarManos();
		
		//Se pide al game que calcule a quien le toca ahora
		actTurn = getSigTurno(actTurn);
		
		//Restauro el saltar a su valor por defecto
		saltando = false;
		
		//Se obtiene el jugador al que le toca
		jugando = jugadores.get(actTurn);
		for(TManagerObserver ob : observers) {
			ob.turnoAcabado();
		}
		
		//Se crea un turno para ese jugador
		this.turnoAct = new Turno(jugando);
		
		updateObservers();
					
	}
	
	/**
	 * Calcula el indice del jugador siguiente teniendo en cuenta si el integrante
	 * que esta jugando ha invertido el sentido o no
	 * @param act Indice en la lista de jugadores del turno actual
	 * @return aux Indice en la lista de jugadores del jugador siguiente 
	 * @see ComandoSaltarJugador
	 * @see ComandoInvertirSentido
	 * @see AdminTurnos
	 */
	private int getSigTurno(int act) {
		//Obtener del jugador el orden y si ha decidido saltar al siguiente jugador
		//Actualizar las ventajas del jugador, una vez usadas ya no dispone de ellas
		orden = !jugando.InvertirSentido();
		jugando.setInvertirSentido(false);
		saltando = jugando.SaltarJugador();
		jugando.setSaltarJugador(false);
		
		
		
		int aux;
		if (orden) {
			aux = act-1;
			if (saltando) {
				aux--;
			}
		}
		else {
			aux = act+1;
			if (saltando) {
				aux++;
			}
		}
		System.out.println("LE TOCA A " + actTurn);
		
		//Si el orden es normal (false) y he llegado al final de la lista de jugadores, paso al principio
		if(!orden && aux >= jugadores.size()) {
			aux = (aux % jugadores.size());
		}
		//Si el orden es contrario (true) y he llegado al principio de la lsita de jugadores paso al final
		if (orden && aux < 0) {
			aux = jugadores.size() + aux; 
		}
		
		return aux;
	}
	
	/**
	 * Comprueba si la partida ha acabado o no
	 * @param pasado Resultado del ultimo turno ejecutado
	 * @return fin	 Indica si la partida finaliza o no
	 */
	//Es este metodo el que cuenta cuantos turnos seguidos han pasado.
	private boolean actPasados(boolean pasado) {
		if(pasados == 0 && pasado) {
			pasados++;
		}
		else if(pasados < jugadores.size()*2 && pasados > 0 && pasado) {
			pasados++;
		}
		else {
			pasados = 0;
		}
		return pasados == jugadores.size()*2;
	}
	
	/**
	 * Metodo que convierte la informacion de esta clase en un string para mostrarla
	 */
	public String toString() {
		//esta funcion sirve para mostrar por pantalla los turnos, funciona de la siguiente forma:
		//cont es un contador que se inicializa al numero de jugadores de la partida, con cada bucle
		//se decrementa en 1, i es la variable del bucle y se inicializa a actTurn
		String s = "Turnos: ";
		int i = this.actTurn;
		
		System.out.println(this.actTurn);
		// Se muestra por pantalla los nicks de  los jugadores y su puntuacion.
		for (Integrante jug: jugadores) {
			//System.out.println(jug); // Esto mostraria toda la informacion de los jugadores: nick, mano y puntuacion.
			System.out.println(jug.getNick() + " --> " + jug.getScore() + " puntos.");
			System.out.println();
		}
		
		//Muestra el orden de turnos
		
		
		if(!orden && i == this.jugadores.size()) { //orden normal
			i = (i  % this.jugadores.size()); 
		}			
		else if (orden && i == -1) { //orden invertido
			i = this.jugadores.size() - 1;
		}
		
		String jugadorAct = this.jugadores.get(i).nick;
		
		if(!orden) {
			i++;
		}
		else {
			i--;
		}
		
		if(!orden && i == this.jugadores.size()) { //si i = jugadores.size pero cont > 0 tengo que dar la vuelta a la lista
			i = (i  % this.jugadores.size()); 
		}			
		else if (orden && i == -1) {
			i = this.jugadores.size() - 1;
		}
		
		String jugadorSig = this.jugadores.get(i).nick;
		
		s = s + jugadorAct + " --> " + jugadorSig;
		
		return s;
	}
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------

		@Override
	public void setMemento(Memento m) {
			
		// - - - Memento de Game - - -	
		Memento mementoGame = new Memento();
		mementoGame.setState(m.getState().getJSONObject("Game"));
		game.setMemento(mementoGame); //Cargar datos de Game
		
		// - - - Memento de Tablero - - -	
		Memento mementoTablero = new Memento();
		mementoTablero.setState(m.getState().getJSONObject("Tablero"));
		tablero.setMemento(mementoTablero); //Cargar datos de Tablero
		
		// - - - Memento de Turno - - -	
		Memento mementoTurno = new Memento();
		mementoTurno.setState(m.getState().getJSONObject("Turno"));
		turnoAct.setMemento(mementoTurno); //Cargar datos de Turno
		
		// - - - Memento de Mazo - - -	
		Memento mementoMazo = new Memento();
		mementoMazo.setState(m.getState().getJSONObject("Mazo"));
		mazo.setMemento(mementoMazo); //Cargar datos de Mazo
	
		
		// - - - Memento de Jugador - - -	
		Memento mementoJugador = new Memento();
		mementoJugador.setState(m.getState().getJSONObject("Jugador"));
		jugando.setMemento(mementoJugador); //Cargar datos de Jugador
				
		//- - - Memento de AdminTurno - - -
		
		Memento mementoAdminTurnos = new Memento();
		mementoAdminTurnos.setState(m.getState().getJSONObject("AdminTurnos"));
		
		actTurn = mementoAdminTurnos.getState().getInt("actTurno");
		pasados = mementoAdminTurnos.getState().getInt("pasados");
		orden = mementoAdminTurnos.getState().getBoolean("orden");
		saltando = mementoAdminTurnos.getState().getBoolean("saltando");

		this.jugadores.clear();
		JSONArray jsonArrayJugadores = mementoAdminTurnos.getState().getJSONArray("lista jugadores");
		for(int i = 0; i < jsonArrayJugadores.length(); i++) {
			Memento mementoJugadores = new Memento();
			
			mementoJugadores.setState(jsonArrayJugadores.getJSONObject(i));
			Jugador j = new Jugador();
			j.setMemento(mementoJugadores); //Establecer estado del jugador
			jugadores.add(i, j); //Annadir jugador a la lista
		}
	}
	
	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		
		//- - - Guardamos datos de AdminTurnos - - -
		JSONObject jsonModelo = new JSONObject();
		JSONObject jsonAdminTurnos = new JSONObject();
		JSONArray jsonArrayJugadores = new JSONArray();
		
		jsonAdminTurnos.put("actTurno", this.actTurn);
		jsonAdminTurnos.put("pasados", this.pasados);
		jsonAdminTurnos.put("orden", this.orden);
		jsonAdminTurnos.put("saltando", this.saltando);
		
		for(Integrante j : this.jugadores) {
			JSONObject jsonJugador = new JSONObject();
			jsonJugador = j.createMemento().getState();
			jsonArrayJugadores.put(jsonJugador);
		}
		
		jsonAdminTurnos.put("lista jugadores", jsonArrayJugadores);
		
		
		//- - - Guardamos datos del resto de clases a preservar - - - 
		
		jsonModelo.put("Game", this.game.createMemento().getState());
		jsonModelo.put("Tablero", this.tablero.createMemento().getState());
		jsonModelo.put("Turno", this.turnoAct.createMemento().getState());
		jsonModelo.put("Mazo", this.mazo.createMemento().getState());
		jsonModelo.put("Jugador", this.jugando.createMemento().getState());
		jsonModelo.put("AdminTurnos", jsonAdminTurnos);
		
		//- - - - - - - - - - - - - - - - - -
		
		memento.setState(jsonModelo);
		
		return memento;
	}
	
	//--------------------METODOS PATRON OBSERVER------------------------

	@Override
	public void addObserver(TManagerObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
		System.out.println("EL INDICE ES " + getSigTurno(actTurn));
		
		o.onRegister(jugando.getNick(), jugadores.get(getSigTurno(actTurn)).getNick());
	}

	@Override
	public void removeObserver(TManagerObserver o) {
		observers.remove(o);
	}

	public void runCommand(Command c) {
		try {
			
			//Se ejecuta el comando
			c.perform(tablero, mazo, jugando, turnoAct);
			System.out.println(turnoAct.acabado());
			//Si el turno se ha acabado
			if(turnoAct.acabado()) {
				System.out.println("Turno acabado");
				//Se comprueba si las palabras son validas
				boolean correcto = game.Verificar(turnoAct.getlistaFichasNoFijas(), jugando, tablero);
				//Si lo son
				if(correcto) {
					//Se actualizan los turnos que cuentan para que acabe la partida
					//Si la partida acaba
					if(actPasados(turnoAct.valido())) {
						//owo para que se acabe la partida
					}
					//Si la partida no acaba
					else {
						//Se genera el siguiente turno 
						this.sigTurno();
					}
				}
				//Si no eran todo palabras
				else {
					//Se restaura el estado del turno a no acabado y se sigue ese turno
					turnoAct.noAcabado();
					for(TManagerObserver ob: observers) {
						ob.onError("No puedes pasar turno en estas condiciones", jugando.getNick());
					}
				}
			}
		} catch (FileNotFoundException | CommandExecuteException e) {
			// TODO Auto-generated catch block
			for(TManagerObserver ob: observers) {
				ob.onError(e.getMessage(),jugando.getNick());
			}
		}			
	}

	public void addGameObserver(TableroObserver ob) {
		// TODO Auto-generated method stub
		tablero.addObserver(ob);
	}

	public void removeGameObserver(TableroObserver ob) {
		// TODO Auto-generated method stub
		tablero.removeObserver(ob);
	}
	
	/**
	 * Notifica a los observadores de este elemento de los cambios producidos
	 * @see TManagerObserver
	 */
	private void updateObservers() {
		for(TManagerObserver ob: observers) {
			ob.mostrarTurnos(jugando.getNick(), jugadores.get(getSigTurno(actTurn)).getNick());
			ob.nuevoTurno(jugando, jugando.getNick(), jugadores.get(getSigTurno(actTurn)).getNick());
		}
	}
	
	//--------------------METODOS DE LA INTERFAZ OBSERVABLE------------------------

	
	public void addJugadorObserver(JugadorObserver ob) {
		jugando.addObserver(ob);
	}
		
	public void removeJugadorObserver(JugadorObserver ob) {
		jugando.removeObserver(ob);
	}
	
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------

	/**
	 * Invierte el orden en el que se pasan los turnos
	 */
	public void invierteSentido() {
		orden = !orden;
		updateObservers();
	}
	
	
	public void saltar() {
		saltando = true;
	}
	
	/**
	 * Rellena las manos de todos los jugadores hasta estar completas
	 * (el maximo de fichas en la mano es 7)
	 */
	public void rellenarManos() {
		for(Integrante j : jugadores) {
			for(int i  = j.get_mano().size(); i < MAXMANO; i++) {
				j.robar(mazo.robar());
			}
		}
	}

	/**
	 * Devuelve el indice en la lista de Integrantes del Integrante
	 * que tiene el turno actual
	 * @return int : indice en la lista
	 */
	public int getActTurn() {
		return actTurn;
	}

	/**
	 * Devuelve el tablero del juego
	 * Este metodo es usado en pruebas unitarias
	 * @return Tablero
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/**
	 * Devuelve el integrante que esta jugando
	 * Este metodo es usado en pruebas unitarias
	 * @return
	 */
	public Integrante getJugando() {
		return jugando;
	}

	/**
	 * Devuelve el mazo de fichas
	 * Este metodo es usado en pruebas unitarias
	 * @return Mazo
	 */
	public Mazo getMazo() {
		return mazo;
	}
	
	
	
	
}
