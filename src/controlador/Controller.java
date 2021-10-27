package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import Command.Command;
import CommandLobby.CommandLobby;
import Excepciones.CommandExecuteException;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.Memento;
import modelo.TableroObserver;
import modelo.TManagerObserver;
/**
 * Interfaz que implementan aquellos objetos que ejecutan comandos y registran los observadores al modelo
 */
public interface Controller {
	
	public void addJugadorObserver(JugadorObserver ob, String jugador);
	
	public void addGameObserver(TableroObserver ob);
	
	public void addTManagerObserver(TManagerObserver ob);
	
	public void removeJugadorObserver(JugadorObserver ob, String string);
	
	public void removeGameObserver(TableroObserver ob);
	
	public void removeTManagerObserver(TManagerObserver ob);
	/**
	 * Ejecuta un comando
	 * @see Command
	 * @param c comando a ejecutar
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException;
	/**
	 * Dado un archivo, carga el estado del archivo en el juego
	 * @param input
	 * @throws IOException
	 */
	public void load(File fichero) throws FileNotFoundException;
	
	public void save(String fichero) throws FileNotFoundException;
}
