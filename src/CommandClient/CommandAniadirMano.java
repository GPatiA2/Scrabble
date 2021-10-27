package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;

public class CommandAniadirMano extends CommandClient{
	
	private Ficha f;
	public CommandAniadirMano(Ficha f) {
		super(ProtocoloComunicacion.ANIADIR_FICHA_MANO);
		this.f = f;;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			int puntos = comandoCompleto[2].charAt(2) - '0';
			Ficha f = new Ficha(comandoCompleto[2].charAt(0), puntos);
			f.setId(comandoCompleto[1]);
			return new CommandAniadirMano(f);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		j.fichaRobada(f, null);
	}
	
	@Override
	public String toString() {
		return super.toString() + " " +f.getId() + " " +f.toString();
	}

	
}
