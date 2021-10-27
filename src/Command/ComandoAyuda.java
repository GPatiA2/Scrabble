package Command;

import Excepciones.CommandExecuteException;
import modelo.Game;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoAyuda
 * 
 * Esta clase permite a un jugador obtener
 * informacion de ayuda sobre los comandos
 * del juego.
 * 
 * @author Grupo 5
 *
 */
public class ComandoAyuda extends Command {
	/**
	 * Informacion de ayuda sobre el comando ayuda
	 */
	private static final String help = "Este comando permite obtener una lista de comandos";
	
	
	public ComandoAyuda(){
		super("help","", "h", "Help", help);
	}
	
	@Override
	public Command parse(String[] comandoCompleto){
		if(matchCommandName(comandoCompleto[0])) {
			ComandoAyuda aux = new ComandoAyuda();
			return aux;			
		}
		
		return null;
	}

	@Override
	protected boolean posible(Turno t) {
		return true;
	}

	@Override
	protected void addCambios(Turno t) {		
	}

	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		return false;
	}

}
