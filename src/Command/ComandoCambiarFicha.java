package Command;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;
import modelo.Game;
import modelo.TableroObserver;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoCambiarFicha
 * 
 * Esta clase permite a un jugador cambiar una de sus
 * fichas por otra aleatoriamente.
 * 
 * @author Grupo 5
 *
 */
public class ComandoCambiarFicha extends Command {

	/**
	 * Informacion de ayuda sobre el comando cambiar de ficha
	 */
	private final static String help = "Este comando te permite sustituir una de las fichas de tu mano por otra escogida al azar del mazo";
	/**
	 * Letra de la ficha a cambiar
	 */
	private char letra;

	
	public ComandoCambiarFicha(char letra) {
		super("Cambiar ficha" ,ProtocoloComunicacion.CAMBIAR_FICHA, "sw", "Sw / swap <letra>", help);
		this.letra = letra;
	}
	
	public Command parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
			if(comandoCompleto.length == 2) {
				String l = comandoCompleto[1];
				if(l.length() == 1) {
					char aux = l.charAt(0);
					return new ComandoCambiarFicha(aux);
				}
				else {
					throw new CommandParseException("Debes introducir un solo caracter como segundo argumento del comando");
				}
			}
			else {
				throw new CommandParseException("Los argumentos para este comando son sw/swap <letra>");
			}
		}
		else {
			return null;
		}
	}

	/**
	 * El execute de cambiar una ficha primero comprueba si el jugador tiene en su atril la
	 * ficha que quiere cambiar y, en caso afirmativo, devuelve la ficha al mazo de fichas
	 * y se extrae aleatoriamente una ficha del mazo que pasa a estar en el atril del
	 * jugador.
	 * 
	 * @throws CommandExecuteException en caso de no tener en el atril la ficha que se quiere
	 * 			cambiar
	 * @return cambiado true si se ha podido cambiar la ficha
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		boolean cambiado = false;
		
		//Comprueba que la ficha existe en la mano
		Ficha f = j.ExisteFicha(Character.toString(letra),true); 
		
		if(f == null) {
			throw new CommandExecuteException("Para poder cambiar una ficha, debes tener esa ficha en la mano");
		}
		else {
			cambiado = true;
			m.anniadir(f); //Se mete la ficha que se quiere cambiar en el mazo
			f = m.robar(); //Se extrae una ficha del mazo
			j.robar(f);  //Se le da la ficha extraida al jugador
		}
		
		return cambiado;
	}

	/**
	 * Comprueba si es posible ejecutar el comando cambiar ficha.
	 * En este caso es posible siempre que el jugador no cambie mas
	 * de 7 fichas por turno.
	 * @return b true si el jugador no ha cambiado ficha mas de 7 veces
	 */
	@Override
	protected boolean posible(Turno t) {
		return t.puedoCambiar();
	}

	/**
	 * Una vez ejecutado el comando se actualiza el contador del 
	 * numero de fichas cambiadas en este turno.
	 */
	@Override
	protected void addCambios(Turno t) {
		t.addCambios();
	}
	@Override
	public String toString() {
		return this.nombreProtocolo + " " + this.letra;
	}

}
