package Command;

import Excepciones.CommandExecuteException;

import Servidor.ProtocoloComunicacion;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoSalida
 * 
 * Esta clase permite salir
 * del juego.
 * 
 * @author Grupo 5
 *
 */
public class ComandoSalida extends Command{
	/**
	 * Informacion de ayuda sobre el comando de salida
	 */
	private static final String help = "Este comando permite abandonar la partida.";
	
	public ComandoSalida() {
		super("salir", ProtocoloComunicacion.LOGOUT_REQUEST,"s", "Salir del juego", help);
	}
	
	@Override
	public Command parse(String[] comandoCompleto) {
		//Llamo a matchCommandName para comprobar si el comando introducido
		//corresponde con este comando, si lo es devuelvo una objeto de 
		//este comando, si no, devuelvo null
		if(matchCommandName(comandoCompleto[0])) {
			ComandoSalida c_s = new ComandoSalida();
			return c_s;
		}
		else {
			return null;
		}
	}
	
	/**
	 * El execute del comando de salida devuelve false para que el siguiente
	 * turno no se ejecute y finalize la aplicacion
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		return false;
	}


	@Override
	protected boolean posible(Turno t) {
		return true;
	}


	@Override
	protected void addCambios(Turno t) {		
	}


}
