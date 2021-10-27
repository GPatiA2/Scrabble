package Command;

import Excepciones.CommandExecuteException;

import Servidor.ProtocoloComunicacion;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoSaltarJugador
 * 
 * Esta clase permite al jugador del turno actual saltar
 * al siguiente jugador en el próximo turno a cambio de
 * 3 monedas.
 * 
 * @author Grupo 5
 *
 */
public class ComandoSaltarJugador extends Command {
	/**
	 * Coste en monedas que tiene saltar a un jugador
	 */
	private static final int coste = 3;
	
	
	public ComandoSaltarJugador() {
		super("jump", ProtocoloComunicacion.SALTAR_JUGADOR,"j");
	}

	
	@Override
	public Command parse(String[] comandoCompleto) {
		
		if(matchCommandName(comandoCompleto[0])) {
			ComandoSaltarJugador s_t = new ComandoSaltarJugador();
			return s_t;
		}
		else {
			return null;
		}
	}

	/**
	 * El execute de saltar a un jugador comprueba que el jugador puede comprar la ventaja y, en caso
	 * de ser asi, invierte el sentido de los turnos. Tras comprar la ventaja finaliza el turno del
	 * jugador.
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		j.gastarMonedas(coste);
		j.setSaltarJugador(true);
		j.acabaTurno();
		return true;
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