package Servidor;

import java.util.List;

import modelo.ModelObserver;
/**
 * Interfaz que contiene los métodos necesarios para informar a clientes
 * de cambios en el lobby
 * @param <T>
 *
 */
public interface LobbyObserver<T> extends ModelObserver  {
	/**
	 * Informa al jugador de que el loggeo ha sido correcto
	 * @param j jugador que quiere loggearse
	 */
	public void loginCorrect(T j);
	/**
	 * Informa al jugador de que se produjo un error
	 * @param j jugador que quiere loggearse
	 */
	public void Error(T j, String error);
	/**
	 *	Solicita al jugador el numero maximo de jugadores y la dificultad
	 * @param j jugador que solicita el envio
	 */
	public void InfoRequest(T j);
	/**
	 * Informa a todos los jugadores que se ha modificado la lista de jugadores del lobby
	 * @param j lista de jugadoresm conectados al lobby
	 * @param creador creador del lobby
	 */
	public void refresh(List<T> j, T creador);
	/**
	 * Informa de que se puede comenzar el juego
	 */
	public void start_game();
	
}
