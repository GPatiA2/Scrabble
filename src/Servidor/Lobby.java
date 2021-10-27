package Servidor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controlador.Controller;
import modelo.Game;
import modelo.Jugador;
import modelo.Observable;
/**
 * Lobby en el que se registran los jugadores antes de comenzar
 * la partida
 *
 */

public class Lobby{
	/**
	 * Creador del lobby
	 * */
	private JugadorConectado creador;
	/**
	 * lista de los jugadores conectados al lobby
	 */
	private List<JugadorConectado> listaJugadores;
	/**
	 * lista de los observadores del lobby
	 */
	private List<LobbyObserver> observers;
	/**
	 * maximo numero de jugadores que puede entrar al lobby
	 */
	private int maxJugadores;
	/**
	 * Dificultad de las IA
	 */
	private String nivel;
	/**
	 * Constructor sin parametros
	 * Inicializa {@link #listaJugadores} , {@link #observers} y {@link #maxJugadores}
	 */
	public Lobby(){
		maxJugadores = 1;
		listaJugadores = new ArrayList<>();
		observers = new ArrayList<>();
	}	
	
	/**
	 * Añade un jugador a {@link #listaJugadores} si el lobby no esta lleno.
	 * Si la lista es vacia, ademas de añadirlo, lo establece como creador del lobby.
	 * Notifica a los correspondientes {@link #observers} de los cambios realizados.
	 * @see #lobbyFull()
	 * @param j JugadorConectado
	 */
	private void addJugador(JugadorConectado j) {
		if(listaJugadores.isEmpty()){
			creador = j;
		}		
		if(!lobbyFull()) {
			this.listaJugadores.add(j);
			for(LobbyObserver o : this.observers) {
				o.loginCorrect(j);
			}
		}		
		else {
			for(LobbyObserver o : this.observers) {
				o.lobby_full(j);
			}
		}
	}
	/**
	 * Elimina a un jugador de la lista.
	 * Si el jugador a eliminar es el creador, el creador pasa a ser el siguiente 
	 * en la lista.
	 * @see #esCreador(JugadorConectado)
	 * @param j JugadorConectado
	 */
	public void removeJugador(JugadorConectado j) {	
		
		this.listaJugadores.remove(j);
		if (j.getNick()!=null && this.esCreador(j)){
			if (listaJugadores.isEmpty()) creador = null;
			
			else creador = listaJugadores.get(0);
			
		}
	}
	
	/**
	 * Devuelve la lista de jugadores.
	 * No puedes modificar lo que devuelve este metodo
	 * @return {@link #listaJugadores} lista de jugadores conectados al lobby
	 */	
	public List<JugadorConectado> getListaJugadores(){
		return this.listaJugadores;
	}
	/**
	 * Devuelve el numero de jugadores de {@link #listaJugadores}
	 * @see #getListaJugadores()
	 * @return tamaño de la lista de jugadores
	 */	
	public int getNumJugadoresJoined() {
		return this.getListaJugadores().size();
	}
	/**
	 * Devuelve el maximo numero de jugadores que pueden apuntarse al lobby
	 * @return {@link #maxJugadores} atributo de esta clase
	 */	
	public int getMaxJugadores() {
		return this.maxJugadores;
	}
	/**
	 * Devuelve un boolean que indica si el lobby esta lleno
	 * @see #getNumJugadoresJoined()
	 * @return <ul>
     *  <li>true: el lobby esta lleno</li>
     *  <li>false: el lobby no esta lleno</li>
     *  </ul>
     */
	public boolean lobbyFull() {
		return this.getNumJugadoresJoined()==maxJugadores;
	}
	/**
	 * Establece el atributo {@link #maxJugadores} al entero 
	 * que se pase como argumento
	 *	@param num maximo de jugadores
     * 
     */
	public void setMaxNumJugadores(int num) {
		this.maxJugadores = num;
	}
	/**
	 * Devuelve un boolean que informa sobre si el JugadorConectado pasado
	 * por argumentos es el creador del lobby
	 * @param j JugadorConectado
	 * @return <ul>
     *  <li>true: el jugador es el creador</li>
     *  <li>false: el jugador no es el creador</li>
     *  </ul>
     */
	public boolean esCreador(JugadorConectado j) {
		return this.creador.equals(j);
	}
	/**
	 * Añade al jugador a {@link #listaJugadores} del lobby.
	 * Si ya estuviera registrado informa a {@link #observers} 
	 * de que se ha producido un error al loggearse.
	 * Si o esta
	 */
	public void login(JugadorConectado j) {
		if (this.listaJugadores.contains(j)) {
			for(LobbyObserver o : this.observers) {
				o.loginError(j);
			}
		}
		else {
			this.addJugador(j);		
		}
		
	}
	/**
	 * Notifica a los {@link #observers} de que se quiere refrescar esta lista
	 */
	public void refresh() {
		for(LobbyObserver o : this.observers) {
			o.refresh(this.getListaJugadores(),creador);
		}
	}
	/**
	 * Notifica a los observadores si se puede comenzar el juego
	 * @param j JugadorConectado
	 * @see #esCreador(JugadorConectado)
	 * @return <ul>
     *  <li>true: si el jugador que quiere comenzar la partida es el creador</li>
     *  <li>false: si el jugador que quiere comenzar la partida no es el creador</li>
     *  </ul>
	 */
	public boolean play(JugadorConectado j) {
		if (esCreador(j)) {
			for(LobbyObserver o : this.observers) {
				o.start_game();
			}	
		}
		else {
			for(LobbyObserver o : this.observers) {
				o.start_game_error(j);
			}
		}
		return esCreador(j);
	}
	/**
	 * Si el jugador que quiere introducir el {@link #maxJugadores} es el creador
	 * del lobby se le notifica que puede hacerlo
	 * @see #esCreador(JugadorConectado)
	 * @param jugador
	 */
	public void numJugadores(JugadorConectado jugador) {
		if (esCreador(jugador)) {
			for(LobbyObserver o : this.observers) {				
				o.numJugadoresCorrect(jugador);							
			}
		}
	}
	/**
	 * Añade un observador a la lista de {@link #observers} del lobby
	 * @param o LobbyObserver
	 */
	public void addObserver(LobbyObserver o) {
		this.observers.add(o);
	}
	/**
	 * Permite establecer el nivel de las IAs
	 * @param nivel
	 */
	public void setDificultad(String nivel) {
		this.nivel = nivel;
	}
	/**
	 * Permite obtener el nivel de las IAs
	 * @return nivel nivel de las IAs
	 */
	public String getDificultad() {
		return this.nivel;
	}
	/**
	 * Método que pide la dificultad de la IA
	 */
	public void pedirDificultad() {
		for (LobbyObserver o : this.observers) {
			o.GetDificultad(creador);
		}
	}

}
