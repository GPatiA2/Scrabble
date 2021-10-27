package controlador;

import java.io.File;
import java.io.FileNotFoundException;

import Command.Command;
import CommandLobby.CommandLobby;
import Excepciones.CommandExecuteException;
import modelo.TableroObserver;
import modelo.Jugador;
import modelo.JugadorObserver;
import modelo.Scrabble;
import modelo.TManagerObserver;

/**
 * Los objetos de esta clase son los que actuan de controlador cuando
 * se esta jugando una partida local
 *
 */
public class ControllerLocal implements Controller{
	
	private Scrabble scrabble;

	public ControllerLocal(Scrabble scrabble) {
		this.scrabble = scrabble;
	}	
	
	public void addJugador(Jugador j) {
		
	}	
	
	@Override
	public void addGameObserver(TableroObserver ob) {
		scrabble.addGameObserver(ob);
	}
	@Override
	public void addTManagerObserver(TManagerObserver ob) {
		scrabble.addTManagerObserver(ob);
	}
	@Override
	public void removeGameObserver(TableroObserver ob) {
		scrabble.removeGameObserver(ob);
	}
	@Override
	public void removeTManagerObserver(TManagerObserver ob) {
		scrabble.removeTManagerObserver(ob);
	}
	
	@Override
	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException {
		scrabble.runCommand(c);
	}

	@Override
	public void addJugadorObserver(JugadorObserver ob, String j) {
		scrabble.addJugadorObserver(ob, j);
	}


	@Override
	public void removeJugadorObserver(JugadorObserver ob, String j) {
		scrabble.removeJugadorObserver(ob, j);
	}

	
	@Override
	public void save(String fichero) throws FileNotFoundException {
		scrabble.save(fichero);
	}


	@Override
	public void load(File fichero) throws FileNotFoundException {
		scrabble.load(fichero);
	}


}
