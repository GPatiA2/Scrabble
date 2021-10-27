package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;

public class CommandMonedas extends CommandClient{
	
	private int puntos;
	private String nick;
	public CommandMonedas(int puntos, String nick) {
		super(ProtocoloComunicacion.MOSTRAR_MONEDAS);
		this.puntos = puntos;
		this.nick = nick;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			
			return new CommandMonedas(Integer.parseInt(comandoCompleto[1]),comandoCompleto[2]);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		j.mostrarMonedas(puntos, nick);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + puntos + " " + nick;
	}
	
}
