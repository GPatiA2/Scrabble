package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;


public class CommandStartGame extends CommandClient{
	
	
	public CommandStartGame() {
		super(ProtocoloComunicacion.GAME_REQUEST);
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			return new CommandStartGame();
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		l.start_game();
	}
}
