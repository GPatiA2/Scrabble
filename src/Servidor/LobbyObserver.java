package Servidor;

import java.util.List;
/**
 * Interfaz que contiene los métodos necesarios para informar a clientes
 * de cambios en el lobby
 *
 */
public interface LobbyObserver {
	/**
	 * Informa al jugador de que el loggeo ha sido correcto
	 * @param j jugador que quiere loggearse
	 */
	public void loginCorrect(JugadorConectado j);
	/**
	 * Informa al jugador de que el loggeo ha sido incorrecto
	 * @param j jugador que quiere loggearse
	 */
	public void loginError(JugadorConectado j);
	/**
	 * Informa al jugador que puede enviar el numero maximo de 
	 * jugadores para el lobby
	 * @param j jugador que solicita el envio del numero maximo de jugadores
	 */
	public void numJugadoresCorrect(JugadorConectado j);
	/**
	 * Informa a todos los jugadores que se ha modificado la lista de jugadores del lobby
	 * @param j lista de jugadoresm conectados al lobby
	 * @param creador creador del lobby
	 */
	public void refresh(List<JugadorConectado> j, JugadorConectado creador);
	/**
	 * Informa de que se puede comenzar el juego
	 */
	public void start_game();
	/**
	 * Informa que no se puede comenzar el juego debido a un error
	 * @param j jugador que ha solicitado el inicio de juego
	 */
	public void start_game_error(JugadorConectado j);
	/**
	 * Informa a un jugador que el lobby esta lleno
	 * @param j jugador que quiere añadirse al lobby
	 */
	public void lobby_full(JugadorConectado j);
	/**
	 * Pide al jugador que introduzca una dificultad
	 * @param j
	 */
	public void GetDificultad(JugadorConectado j);
}
