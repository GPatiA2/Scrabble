package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;

public class CommandError extends CommandClient{
	
	private String mensaje;
	private boolean lobby;
	public CommandError(String mensaje,boolean lobby) {
		super(ProtocoloComunicacion.ERROR);
		this.mensaje = mensaje;
		this.lobby = lobby;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			String mensaje = "";
			for(String i : comandoCompleto) {
				if(i!=this.nombreProtocolo) mensaje += i + " ";
			}
			return new CommandError(mensaje.substring(ProtocoloComunicacion.ERROR.length()+1+comandoCompleto[1].length()),comandoCompleto[1].equals("true"));
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		if(lobby)l.Error("",this.mensaje);
		else m.onError(mensaje, "");
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + lobby +" " + mensaje;
	}
	
}
