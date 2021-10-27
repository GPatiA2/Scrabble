package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Command.Command;
import CommandClient.CommandClient;
import CommandLobby.CommandLobby;
import Excepciones.CommandExecuteException;
import Servidor.LobbyObserver;
import Servidor.ProtocoloComunicacion;
import Servidor.InterpreterFactoria.InterpreterFactory;
import modelo.TableroObserver;
import modelo.JugadorObserver;
import modelo.TManagerObserver;

public class ControllerCliente implements Controller, ControllerLobby<String>{

	private LobbyClient lobby;
	private TableroClient tablero;
	private TManagerClient TManager;
	private JugadorClient jugador;
	private PrintWriter out;
	
	public ControllerCliente(PrintWriter out, LobbyClient lobby, TableroClient tablero, TManagerClient tManager,
			JugadorClient jugador) {
		this.lobby = lobby;
		this.tablero = tablero;
		TManager = tManager;
		this.jugador = jugador;
		this.out = out;
	}
	
	@Override
	public void addLobbyObserver(LobbyObserver<String> o) {
		this.lobby.addObserver(o);
	}

	@Override
	public void removeLobbyObserver(LobbyObserver<String> o) {
		this.lobby.removeObserver(o);
	}

	@Override
	public void executeCommandLobby(CommandLobby c) {
		out.println(InterpreterFactory.Estado.LOBB + " " + c.toString());
	}

	@Override
	public void addJugadorObserver(JugadorObserver ob, String j) {
		this.jugador.addObserver(ob);
	}

	@Override
	public void addGameObserver(TableroObserver ob) {
		this.tablero.addObserver(ob);
	}

	@Override
	public void addTManagerObserver(TManagerObserver ob) {
		this.TManager.addObserver(ob);
	}

	@Override
	public void removeJugadorObserver(JugadorObserver ob, String j) {
		this.jugador.removeObserver(ob);
	}

	@Override
	public void removeGameObserver(TableroObserver ob) {
		this.tablero.removeObserver(ob);
	}

	@Override
	public void removeTManagerObserver(TManagerObserver ob) {
		this.TManager.removeObserver(ob);
	}

	@Override
	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException {
		out.println(InterpreterFactory.Estado.GAME + " " + c.toString());
	}

	@Override
	public void load(File fichero) throws FileNotFoundException {
		out.println(InterpreterFactory.Estado.SERV + " " +ProtocoloComunicacion.LOAD + " " + fichero.getPath());
	}

	@Override
	public void save(String fichero) throws FileNotFoundException {
		out.println(InterpreterFactory.Estado.SERV + " " +ProtocoloComunicacion.SAVE + " "+fichero.toString());
	}

	public void executeCommandClient(CommandClient c,String mensaje) throws CommandExecuteException {
		c.execute(mensaje, this.tablero, jugador, this.TManager, lobby);
	}

	@Override
	public void gameRequest() {
		out.println(InterpreterFactory.Estado.SERV + " " +ProtocoloComunicacion.GAME_REQUEST);
	}

}
