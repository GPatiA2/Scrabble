package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import Command.Command;
import Excepciones.CommandExecuteException;

public class Scrabble {

	private AdminTurnos tManager;
	
	public Scrabble(List<Integrante> jugadores) throws IOException {
		this.tManager = new AdminTurnos(new GeneradorMazo(), new GeneradorDiccionario(), jugadores);
	}
	
	public Scrabble(Memento m) {
		tManager = new AdminTurnos(m);
	}
	
	public void addGameObserver(TableroObserver ob) {
		tManager.addGameObserver(ob);
	}
	
	public void addTManagerObserver(TManagerObserver ob) {
		tManager.addObserver(ob);
	}

	public void removeGameObserver(TableroObserver ob) {
		tManager.removeGameObserver(ob);
	}

	public void removeTManagerObserver(TManagerObserver ob) {
		tManager.removeObserver(ob);
	}
	

	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException {
		 tManager.runCommand(c);
	}


	public void addJugadorObserver(JugadorObserver ob) {
		tManager.addJugadorObserver(ob);
	}


	public void removeJugadorObserver(JugadorObserver ob) {
		tManager.removeJugadorObserver(ob);
	}

	
	public void save(String fichero) throws FileNotFoundException {
		ModeloDAO Dao = new ModeloDAO();
		Dao.guardarPartida(this.tManager.createMemento(), fichero);
	}


	public void load(File fichero) throws FileNotFoundException {
		ModeloDAO Dao = new ModeloDAO();
		this.tManager.setMemento(Dao.cargarPartida(fichero));
	}
}
