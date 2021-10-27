package Command;

import Excepciones.CommandExecuteException;

import Servidor.ProtocoloComunicacion;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoPasarTurno
 * 
 * Esta clase permite a un jugador pasar
 * el turno.
 * 
 * @author Grupo 5
 *
 */
public class ComandoPasarTurno extends Command {

	public ComandoPasarTurno() {
		super("pasar",ProtocoloComunicacion.PASAR_TURNO, "p");
	}

	
	@Override
	public Command parse(String[] comandoCompleto) {
		//Llamo a matchCommandName para comprobar si el comando introducido
		//corresponde con este comando, si lo es devuelvo una objeto de 
		//este comando, si no, devuelvo null
		if(matchCommandName(comandoCompleto[0])) {
			ComandoPasarTurno q_f = new ComandoPasarTurno();
			return q_f;
		}
		else {
			return null;
		}
	}

	/**
	 * El execute del pasar el turno comprueba que hay una ficha colocada en el centro, en caso
	 * afirmativo entonces se puede pasar el turno sin más restricciones.
	 * Esto se debe a la regla #9 de nuestra versión
	 * de Scrabble.
	 * 
	 * @see /Documentos/Reglas.docx
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		if(t.centroVacio()) {
			System.out.println("CENTRO DISPONIBLE");
			return true;
		}
		else {
			System.out.println("CENTRO NO DISPONIBLE");
			System.out.println(t.getCasilla(7, 7).esDisponible());
			return false;
		}
		
	}

	@Override
	protected boolean posible(Turno t) {
		return true;
	}

	/**
	 * Si ha sido posible ejecutar el comando se indica que se ha acado el turno
	 */
	@Override
	protected void addCambios(Turno t) {
		t.endTurn();
	}

}
