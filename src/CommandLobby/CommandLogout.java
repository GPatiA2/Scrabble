package CommandLobby;

import java.io.FileNotFoundException;

import Command.ComandoSalida;
import Command.Command;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.ProtocoloComunicacion;
import Servidor.Servidor;

public class CommandLogout extends CommandLobby{

	public CommandLogout() {
		super(ProtocoloComunicacion.LOGOUT_REQUEST);
	}
	@Override
	public CommandLobby parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			return new CommandLogout();
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(Lobby l, JugadorConectado j) throws CommandExecuteException {
		l.removeJugador(j);
		l.refresh();
	}

}
