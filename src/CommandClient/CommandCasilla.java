package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Casilla;
import modelo.Ficha;
import utils.Coordenadas;

public class CommandCasilla extends CommandClient{
	
	private Coordenadas coor;
	private Casilla c;
	public CommandCasilla(Coordenadas coor, Casilla c) {
		super(ProtocoloComunicacion.ACTUALIZAR_CASILLA);
		this.coor = coor;
		this.c = c;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			
			int coordX = Integer.parseInt(comandoCompleto[1]);
			int coordY = Integer.parseInt(comandoCompleto[2]);
			Casilla casilla;
			
			if (comandoCompleto.length==5) {
				int puntos = comandoCompleto[3].charAt(2) - '0';
				
				casilla= new Casilla(new Ficha(comandoCompleto[3].charAt(0), puntos),Integer.parseInt(comandoCompleto[4]));
			}
			else {casilla = new Casilla(Integer.parseInt(comandoCompleto[2]));}
			return new CommandCasilla(new Coordenadas(coordX,coordY), casilla);
			
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		t.actualizaCasilla(coor, c);
	}
	
	@Override
	public String toString() {
		return super.toString()+ " " + coor.toString() + " " + c.to_string();
	}
	
}
