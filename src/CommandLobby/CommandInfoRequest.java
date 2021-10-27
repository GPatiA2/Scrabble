package CommandLobby;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.ProtocoloComunicacion;
import Servidor.Servidor;

public class CommandInfoRequest extends CommandLobby{
	
	private int max;
	private String dificultad;
	
	public CommandInfoRequest(int Max,String dificultad) {
		super(ProtocoloComunicacion.INFO_REQUEST);
		this.max = Max;
		this.dificultad = dificultad;
	}
	@Override
	public CommandLobby parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			return new CommandInfoRequest(Integer.parseInt(comandoCompleto[1]), comandoCompleto[2]);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(Lobby l, JugadorConectado j) throws CommandExecuteException {
		l.setMaxNumJugadores(max);
		l.setDificultad(this.dificultad);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + max + " " + dificultad;
	}
}
