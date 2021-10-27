
package modelo;

import java.util.ArrayList;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import Excepciones.CommandExecuteException;
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
public abstract class Integrante implements Originator, Observable<JugadorObserver>{
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
	 * Indica si el integrante puede jugar
	 */
	protected boolean puedeJugar;
	/**
	 * Indica si el integrante ha comprado la ventaja Invertir sentido en 
	 * los turnos. Este atributo es necesario para que el AdminTurnos pueda
	 * decidir en el turno actual cual es el sentido.
	 * @see src/Command/ComandoInvertirSentido
	 * @see src/Modelo/AdminTurnos
	 */
	protected boolean invertirSentido;
	/**
	 * Indica si el integrante ha comprado la ventaja Saltar un jugador en 
	 * los turnos. Este atributo es necesario para que el AdminTurnos pueda
	 * decidir en el turno actual si debe quitarle el turno al proximo jugador.
	 * @see src/Command/ComandoSaltarJugador
	 * @see src/Modelo/AdminTurnos
	 */
	private boolean saltarJugador;
	/**
	 * Atril del integrante
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
					if (bienColocada)  //Si esta en una casilla disponible se elimina de la mano y se notifica
						mano.remove(f);
						for(JugadorObserver j : this.observers) {
							j.borrarFichaMano(f, this, bienColocada);
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
	 * Añade una ficha robada del mazo al atril del jugador
	 * @param f
	 */
	public void robar(Ficha f) {
		mano.add(f);
		for(JugadorObserver ob: observers) {
			ob.fichaRobada(f, this);
		}
	}
	
	/**
	 * Añade los puntos recibidos a la puntuacion del Integrante
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

		for(JugadorObserver ob: observers) {			
			ob.mostrarMonedas(this.monedas_ganadas - this.monedas_gastadas, nick);
		}
	}
	
	/**
	 * Actualiza las monedas_ganadas segun la puntuacion del
	 * Integrante. Por cada 10 puntos se ganan 3 monedas.
	 */
	public void actualizarGanadas() {
		this.monedas_ganadas = YmonedasCadaXPuntos * (this.puntos/cadaXPuntos);
		for(JugadorObserver ob: observers) {			
			ob.mostrarMonedas(this.monedas_ganadas, nick);
		}
	}


	/**
	 * Elimina una ficha dada del atril del integrante
	 * @param ficha 
	 * @return Ficha : ficha eliminada
	 * @throws CommandExecuteException en caso de no existir en el atril la ficha que se quiere eliminar
	 */
	public Ficha eliminarFicha(char ficha) throws CommandExecuteException{
		boolean encontrado = false;
		int i = 0;
		while (!encontrado && i < mano.size()) {
			if (Character.toLowerCase(mano.get(i).getLetra()) == Character.toLowerCase(ficha)) {
				encontrado = true;
				Ficha f = mano.get(i);
				mano.remove(i);
				for(JugadorObserver j : this.observers) {
					j.borrarFichaMano(f, this, true);
				}
				return f;
			}
			i++;
		}
		
		throw new CommandExecuteException("No tienes la ficha que quieres descartar");
		
	}
	
	protected abstract void convertirMonedasPuntos();

	
	//Metodos utilizados en algunos test
	
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
	
	public abstract Memento createMemento();
	public abstract void setMemento(Memento m);


	//--------------------METODOS DE LA INTERFAZ OBSERVABLE------------------------

	@Override
	public void addObserver(JugadorObserver o) {
		observers.add(o);
		o.onRegister(nick, puntos, monedas_ganadas, mano);
	}

	@Override
	public void removeObserver(JugadorObserver o) {
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
		return this.monedas_ganadas - this.monedas_gastadas;
	}
	
	
	/**
	 * Devuelve una copia no modificable del atril
	 * @return
	 */
	public List<Ficha> get_mano() {
		return Collections.unmodifiableList(mano);
	}
	public int getNumFichasMano() {
		return this.mano.size();
	}
	public void setGame(Game game) {
	}

	public void setTablero(Tablero tablero) {	
	}

	public Map<Coordenadas, Character> esPosible(char[] cadena, Coordenadas pivote, int i, String palabra) {
		return null;
	}

	public void juegaTurno(Turno turno, AdminTurnos admin, List<Coordenadas> listaFichasNoFijas) {
		puedeJugar = true;
	}
	
	public  void acabaTurno() {
		puedeJugar = false;
	}
	
	public  boolean puedeActuar() {
		return puedeJugar;
	}
	
	public boolean InvertirSentido() {
		return invertirSentido;
	}

	public void setInvertirSentido(boolean invertirSentido) {
		this.invertirSentido = invertirSentido;
	}

	public boolean SaltarJugador() {
		return saltarJugador;
	}

	public void setSaltarJugador(boolean saltarJugador) {
		this.saltarJugador = saltarJugador;
	}
	
}

