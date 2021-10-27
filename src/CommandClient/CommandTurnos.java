package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;

public class CommandTurnos extends CommandClient{
	
	private String act;
	private String sig;
	public CommandTurnos(String act, String sig) {
		super(ProtocoloComunicacion.MOSTRAR_TURNOS);
		this.act = act;
		this.sig = sig;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			
			return new CommandTurnos(comandoCompleto[1],comandoCompleto[2]);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		m.mostrarTurnos(this.act, this.sig);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + act + " " +sig;
	}
	
}
