package Servidor.Interprete;

import CommandLobby.CommandLobby;
import CommandLobby.CommandLobbyGenerator;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.Servidor;

public class InterpreteLobby extends Interpreter{

	public InterpreteLobby(Lobby lobby, Servidor servidor) {
		super(lobby,servidor);
	}

	@Override
	public void execute(String mensaje, JugadorConectado j) throws CommandParseException, CommandExecuteException {
		CommandLobby c =CommandLobbyGenerator.parseCommand(mensaje.split(" "));
		c.execute(this.lobby, j);
	}

	
}
