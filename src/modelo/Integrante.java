
package modelo;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import Command.Command;
import Command.CommandGenerator;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import javafx.util.Pair;
import utils.Coordenadas;

/**
 * Clase Integrante
 * 
 * Esta clase contiene la informacion relativa a un 
 * integrante del juego (nick, puntos, monedas...)
 * 
 * @author Grupo 5
 *
 */
public class Integrante implements Originator, Observable<JugadorObserver>{
	/**
	 * Cada X puntos damos una cantidad Y de monedas
	 */
	private static final int cadaXPuntos = 10;
	/**
	 * Cada X puntos damos una cantidad Y de monedas
	 */
	private static final int YmonedasCadaXPuntos = 3;

	/**
	 * Puntuacion del integrante
	 */
	protected int puntos;
	/**
	 * Nick del integrante
	 */
	protected String nick;
	/**
	 * Monedas de las que dispone el integrante.
	 * Para simplificar las cuentas se han creado dos atributos de
	 * las monedas ganadas y gastadas que se actualizan por separado.
	 * 
	 * Para guardar y cargar partidas usamos las monedas gastadas.
	 * Para obtener las monedas actuales basta con llamar a la funcion actualizarGanadas().
	 * 
	 */
	protected int monedas_ganadas;
	/**
	 * Monedas gastadas por el integrante
	 */
	protected int monedas_gastadas;
	/**
	 * Estrategia que sigue el integrante dependiendo de si es un
	 * jugador humano (PlayerStrategy) o una maquina(MaquinaStrategy)
	 *@see src/Modelo/RunStrategy
	 *@see src/Modelo/PlayerStrategy
	 *@see src/Modelo/Maquina
	 */
	protected RunStrategy estrategia;
	/**
	 * Indica el numero de turnos seguidos que ha pasado el jugador
	 */
	private int turnosPasados;
	/**
	 * Indica si el integrante puede jugar
	 */
	private boolean puedeJugar;
	/**
	 * Indica si el integrante ha comprado la ventaja Invertir sentido en 
	 * los turnos. Este atributo es necesario para que el AdminTurnos pueda
	 * decidir en el turno actual cual es el sentido.
	 * @see src/Command/ComandoInvertirSentido
	 * @see src/Modelo/AdminTurnos
	 */
	private boolean invertirSentido;
	/**
	 * Indica si el integrante ha comprado la ventaja Saltar un jugador en 
	 * los turnos. Este atributo es necesario para que el AdminTurnos pueda
	 * decidir en el turno actual si debe quitarle el turno al proximo jugador.
	 * @see src/Command/ComandoSaltarJugador
	 * @see src/Modelo/AdminTurnos
	 */
	private boolean saltarJugador;
	/**
	 * Atril del integrande
	 */
	protected List<Ficha> mano;
	/**
	 * Lista de observadores
	 */
	protected List<JugadorObserver> observers;
	
	public Integrante() {
		puedeJugar = false;
		invertirSentido = false;
		saltarJugador = false;
		puntos = 0;
		monedas_ganadas = 0;
		monedas_gastadas = 0;
		mano = new ArrayList<Ficha>();
		observers = new ArrayList<JugadorObserver>();
	}
	
	public void robar(Ficha f) {
		mano.add(f);
		letras.add(f.getLetra());
	}
	
	public int size() {
		return mano.size();

	/**
	 * Antes de colocar una ficha en el tablero se comprueba que dicha ficha existe
	 * en el atril del jugador. En caso de existir, si esta bien colocada dentro del 
	 * tablero entonces se elimina dicha ficha del atril y se notifica a los observadores.
	 * 
	 * @param ficha 
	 * @param bienColocada booleano que indica si la casilla esta disponible y sus coord son correctas
	 * @return null si no existe la ficha
	 * @return f Ficha bien colocada en el tablero que se ha eliminado del atril del jugador
	 * @throws IllegalArgumentException en caso de no existir la ficha en el atril del jugador
	 */
	public Ficha ExisteFicha(String ficha, boolean bienColocada) throws IllegalArgumentException{
		for(Ficha f : mano) {
			if(f.igual_id(ficha)) {
				if (bienColocada) {  //Si esta en una casilla disponible se elimina de la mano y se notifica
					mano.remove(f);
					for(JugadorObserver j : this.observers) {
						j.borrarFichaMano(f, this, bienColocada);
					}
				}
				else { //Si no esta bien colocada notifico a los observadores para que la ficha vuelva a aparecer en el atril
					for(JugadorObserver ob: observers) {
						ob.fichaRobada(f, this);
					}
				}
				
				return f;
			}
		}
		for(Ficha f : mano) {
			if(f.igual_letra(ficha.charAt(0))) {
				if (bienColocada) {  //Si esta en una casilla disponible se elimina de la mano y se notifica
					mano.remove(f);
					for(JugadorObserver j : this.observers) {
						j.borrarFichaMano(f, this, bienColocada);
					}
				}
				else { //Si no esta bien colocada notifico a los observadores para que la ficha vuelva a aparecer en el atril
					for(JugadorObserver ob: observers) {
						ob.fichaRobada(f, this);
					}
				}
				
				return f;
			}
		}
		return null;
	}
	
	/**
	 * A�ade una ficha robada del mazo al atril del jugador
	 * @param f
	 */
	public void robar(Ficha f) {
		mano.add(f);
		for(JugadorObserver ob: observers) {
			ob.fichaRobada(f, this);
		}
	}
	
	/**
	 * A�ade una ficha al atril del integrante
	 * @param f
	 */
	public void anadirFicha(Ficha f) {
		//TODO: revisar, cuando se utiliza esto?? se supone que al robar una ficha se usa la funcion robar()
		mano.add(f);
	}
	
	
	/**
	 * Elimina una ficha dada del atril del integrante
	 * @param f : ficha 
	 * @throws Exception si el integrante no tiene la ficha en su atril
	 */
	public void poner(Ficha f) throws Exception{ 
		//TODO: en que momento se usa esto?
		if (mano.contains(f)) {
			mano.remove(f);
			for(JugadorObserver ob: observers) {
				ob.fichaUsada(f, this);
			}
		}
		else {
			throw new Exception("No tienes la ficha indicada");
		}
	}
	
	/**
	 * A�ade los puntos recibidos a la puntuacion del Integrante
	 * @param p
	 */
	public void recibirPuntos(int p) {
		puntos += p;
		actualizarGanadas();
		for(JugadorObserver ob: observers) {			
			ob.mostrarPuntos(puntos, nick);
		}
	}
	
	/**
	 * Gasta las monedas recibidas por parametros en caso de ser posible,
	 * actualizando el numero de monedas gastadas y el numero de monedas de 
	 * las que dispone el integrante
	 * @param m
	 * @throws CommandExecuteException en caso de no disponer de suficientes 
	 * monedas para gastar
	 */
	public void gastarMonedas(int m) throws CommandExecuteException{
		if (this.monedas_ganadas - this.monedas_gastadas < m) {
			throw new CommandExecuteException("No tienes suficientes monedas");
		}
		this.monedas_gastadas += m;
	}
	
	/**
	 * Actualiza las monedas_ganadas segun la puntuacion del
	 * Integrante. Por cada 10 puntos se ganan 3 monedas.
	 */
	public void actualizarGanadas() {
		this.monedas_ganadas = YmonedasCadaXPuntos * (this.puntos/cadaXPuntos);
		for(JugadorObserver ob: observers) {			
			ob.mostrarMonedas(getCoin(), nick);
		}
	}

	/**
	 * El integrante recibe las monedas dadas por parametros y notifica
	 * a los observadores
	 * @param monedas
	 */
	public void recibirMonedas(int monedas) {
		this.monedas_gastadas = monedas;
		for(JugadorObserver ob: observers) {			
			ob.mostrarMonedas(getCoin(), nick);
		}
	}

	/**
	 * Elimina una ficha dada del atril del integrante
	 * @param ficha 
	 * @return Ficha : ficha eliminada
	 * @throws CommandExecuteException en caso de no existir en el atril la ficha que se quiere eliminar
	 */
	public Ficha eliminarFicha(char ficha) throws CommandExecuteException{
		//TODO: revisar, en que momento se usa eso??? para eliminar una ficha se usa el metodo borrarFichaMano()
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < mano.size()) {
			if (Character.toLowerCase(mano.get(i).getLetra()) == Character.toLowerCase(ficha)) {
				encontrado = true;
				Ficha f = mano.get(i);
				mano.remove(i);
				letras.remove(f.getLetra());
				return f;
			}
			i++;
		}
		
		throw new CommandExecuteException("No tienes la ficha que quieres descartar");
		
	}

	public void anadirFicha(Ficha f) {
		mano.add(f);
	}
	
	public Ficha get_ficha_posicion(int indice) {
		return mano.get(indice);
	}
	/**
	 * Metodo utilizado por la maquina para coger una letra y colocarla en el tablero
	 * @param letra
	 * @return
	 */
	public Ficha get_ficha_letra(char letra) {
		//TODO: revisar, otra vez lo mismo, se supone que se elimina con el metodo borrarFichaMano
		for (Ficha f : this.mano) {
			if (f.getLetra() == letra) {
				mano.remove(f); //Se elimina la ficha de la mano
				return f;
			}
		}
		return null;
	}
	
	/**
	 * Devuelve la ficha del atril que esta en la posicion dada por parametros
	 * @param indice: indice en la lista del atril
	 * @return Ficha
	 */
	public Ficha get_ficha_posicion(int indice) {
		return mano.get(indice);
	}
	
	/**
	 * Elimina la ficha del atril dada una posicion
	 * @param indice: indice en la lista de fichas 
	 */
	public void erase_ficha_posicion(int indice){
		mano.remove(indice);
	}
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------


	@Override
	public void setMemento(Memento m) {
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

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonJugador = new JSONObject();
		JSONArray jsonArrayMano = new JSONArray();
		
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



	//--------------------METODOS DE LA INTERFAZ OBSERVABLE------------------------

	@Override
	public void addObserver(JugadorObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
		o.onRegister(nick, puntos, monedas_ganadas, mano);
	}

	@Override
	public void removeObserver(JugadorObserver o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...--------------------

	public int size() {
		return mano.size();
	}
	
	public String getNick() {
		return this.nick;
	}
	
	public int getScore() {
		return this.puntos;
	}
	
	public int getCoin() {
		return this.monedas_ganadas;
	}
	
	public int getGastadas() {
		return this.monedas_gastadas;
	}
	
	/**
	 * Devuelve la estrategia del jugador
	 * @return RunStrategy
	 */
	public RunStrategy get_estrategia() {
		return this.estrategia;
	}
	
	public String getNivel() {
		return null;
	}

	/**
	 * Devuelve una copia no modificable del atril
	 * @return
	 */
	public List<Ficha> get_mano() {
		return Collections.unmodifiableList(mano);
	}

	public void borrarMonedas() {
		this.monedas_gastadas = this.monedas_ganadas;
	}

	public void setGame(Game game) {
	}

	public void setTablero(Tablero tablero) {	
	}

	public Map<Coordenadas, Character> esPosible(char[] cadena, Coordenadas pivote, int i, String palabra) {
		return null;
	}

	public List<Command> realizarJugada(Game game, AdminTurnos tManager, Turno t, Scanner in, List<Coordenadas> listaFichasNoFijas){
		return null;
	}
}

