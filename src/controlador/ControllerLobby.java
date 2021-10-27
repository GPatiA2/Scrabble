package controlador;

import CommandLobby.CommandLobby;
import Servidor.LobbyObserver;

public interface ControllerLobby<T> {
	/**
	 * Añade un observador 
	 * @param o LobbyObserver
	 */
	public void addLobbyObserver(LobbyObserver<T> o);
	/**
	 * Elimina un observador 
	 * @param o LobbyObserver
	 */
	public void removeLobbyObserver(LobbyObserver<T> o);

	/**
	 * Ejecuta un comando del lobby
	 * @see CommandLobby
	 * @param c comando a ejecutar
	 */
	public void  executeCommandLobby(CommandLobby c);
	/**
	 * Comienza el juego
	 */
	public void gameRequest();
}
