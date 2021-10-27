package Command;

import Excepciones.CommandExecuteException;

import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase ComandoComprarComodin
 * 
 * Esta clase permite a un jugador comprar un comodin
 * a cambio de 5 monedas y descartando una de sus fichas.
 * 
 * @author Grupo 5
 *
 */
public class ComandoComprarComodin extends Command{
	/**
	 * Informacion de ayuda sobre el comando comprar un comodin
	 */
	private static final String help = "Este comando permite comprar un comodin a cambio de 5 monedas descartando una ficha.";
	/**
	 * Ficha descartada
	 */
	private char id_ficha;
	/**
	 * Coste en monedas que tiene comprar un comodin
	 */
	private static final int coste = 5;
	
	public ComandoComprarComodin(char id_ficha) {
		super("comodin",ProtocoloComunicacion.COMPRAR_COMODIN, "cc", "comodin <letra>", help);
		this.id_ficha = id_ficha;
	}
	
	@Override
	public Command parse(String[] comandoCompleto) throws CommandParseException {
		
		ComandoComprarComodin c_c = null;
		
		if(matchCommandName(comandoCompleto[0])) {
			if(comandoCompleto.length != 2) {
				throw new CommandParseException("numero de argumentos invalidos");
			}	
			String letra = comandoCompleto[1];
			c_c = new ComandoComprarComodin(letra.charAt(0));
		}
		
		return c_c;
	}

	/**
	 * El execute de comprar un comodin comprueba que el jugador puede comprar dicho 
	 * comodin y, en caso de ser posible, annade al atril del jugador el comodin y
	 * elimina la ficha de la que se ha descartado
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		try {
			j.gastarMonedas(coste); 
			Ficha f = j.eliminarFicha(id_ficha);
			f = new Ficha('*', 0);
			j.anadirFicha(f);
			return true;
		}
		catch(Exception e) {
			throw new CommandExecuteException(e.getMessage());
		}
	}

	@Override
	protected boolean posible(Turno t) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void addCambios(Turno t) {
		// TODO Auto-generated method stub
	}
	@Override
	public String toString() {
		return this.nombreProtocolo + " " + this.id_ficha;
	}

}
