package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;

public class CommandBorrarMano extends CommandClient{
	
	private Ficha f;
	private boolean bienColocada;
	public CommandBorrarMano(Ficha f,boolean bienColocada) {
		super(ProtocoloComunicacion.BORRAR_FICHA_MANO);
		this.f = f;
		this.bienColocada = bienColocada;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			
			Ficha f =new Ficha(); f.setId(comandoCompleto[1]);
			return new CommandBorrarMano(f,comandoCompleto[2].equals("true"));
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		j.borrarFichaMano(f, null, bienColocada);
	}
	
	@Override
	public String toString() {
		return super.toString()+ " " + f.getId()+  " "  + bienColocada;
	}
	
}
