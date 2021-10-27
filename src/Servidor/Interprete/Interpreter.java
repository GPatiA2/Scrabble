package Servidor.Interprete;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.Servidor;

public abstract class Interpreter {
	protected Lobby lobby;
	protected Servidor servidor;
	
	public Interpreter(Lobby lobby,Servidor servidor) {
		super();
		this.lobby = lobby;
		this.servidor = servidor;
	}

	public abstract void execute(String mensaje, JugadorConectado j) throws CommandParseException,CommandExecuteException ;
}
