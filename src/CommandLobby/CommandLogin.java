package CommandLobby;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.ProtocoloComunicacion;
import Servidor.Servidor;

public class CommandLogin extends CommandLobby{

	private String nick;
	public CommandLogin(String nick) {
		super(ProtocoloComunicacion.LOGIN_REQUEST);
		this.nick = nick;
	}
	@Override
	public CommandLobby parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			return new CommandLogin(comandoCompleto[1]);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(Lobby l, JugadorConectado j) throws CommandExecuteException {
		j.setNick(nick);
		l.login(j);
		l.refresh();
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.nick;
	}

}
