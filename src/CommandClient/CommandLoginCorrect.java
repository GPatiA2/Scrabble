package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;

public class CommandLoginCorrect extends CommandClient{
	
	private String nick;
	public CommandLoginCorrect(String nick) {
		super(ProtocoloComunicacion.LOGIN_REQUEST);
		this.nick = nick;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			return new CommandLoginCorrect(comandoCompleto[1]);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		l.loginCorrect(nick);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + nick;
	}
}
