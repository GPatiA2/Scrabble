package Command;

import Excepciones.CommandExecuteException;

import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;


/**
 * Clase ComandoInvertirSentido
 * 
 * Esta clase permite a un jugador invertir el sentido en el que
 * se pasan los turnos a cambio de 5 monedas.
 * 
 * @author Grupo 5
 *
 */
public class ComandoInvertirSentido extends Command{
	/**
	 * Coste en monedas que tiene invertir el sentido
	 */
	private static final int coste = 5;
	
	public ComandoInvertirSentido() {
		super("invertir", ProtocoloComunicacion.INVERTIR_SERNTIDO,"is");
	}
	
	@Override
	public Command parse(String[] comandoCompleto) throws CommandParseException {
		
		if(matchCommandName(comandoCompleto[0])) {
			ComandoInvertirSentido i_s = new ComandoInvertirSentido();
			return i_s;
		}
		else {
			return null;
		}
	}

	/**
	 * El execute de invertir sentido comprueba que el jugador puede comprar la ventaja y, en caso
	 * de ser asi, invierte el sentido de los turnos. Tras comprar la ventaja finaliza el turno del
	 * jugador.
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		try {
			j.gastarMonedas(coste);
			j.setInvertirSentido(true);
			j.acabaTurno();
			return true;
		}
		catch(Exception e) {
			throw new CommandExecuteException(e.getMessage());
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