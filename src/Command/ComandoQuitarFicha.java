package Command;


import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;
import modelo.Ficha;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;
import utils.Coordenadas;

/**
 * Clase ComandoQuitarFicha
 * 
 * Esta clase permite a un jugador quitar
 * una ficha del tablero durante su turno.
 * 
 * @author Grupo 5
 *
 */
public class ComandoQuitarFicha extends Command{
	/**
	 * Coordenadas de la ficha a quitar
	 */
	private Coordenadas coor;
	
	public ComandoQuitarFicha(int coorX, int coorY) {
		super("quitar",ProtocoloComunicacion.QUITAR_FICHA_TABLERO, "q");
		coor = new Coordenadas(coorX,coorY);
	}

	public Command parse(String[] comandoCompleto) throws CommandParseException {
		//Llamo a matchCommandName para comprobar si el comando introducido
		//corresponde con este comando, si lo es devuelvo una objeto de 
		//este comando, si no, devuelvo null
		ComandoQuitarFicha q_f = null;
		
		if(matchCommandName(comandoCompleto[0])) {
			try {
				if (comandoCompleto.length!=3) {
					throw new CommandParseException("Numero de argumentos invalidos");
				}
			
				int x,y;
				x = Integer.parseInt(comandoCompleto[1]);
				y = Integer.parseInt(comandoCompleto[2]);
				if(Coordenadas.checkCommand(x-1, y-1)) {
					q_f = new ComandoQuitarFicha(x,y);
				}
				else {
					throw new CommandParseException("Casilla fuera de rango.");
				}
			}
			catch(NumberFormatException nfe) {
				throw new CommandParseException("Los argumentos coorX, coorY deben ser numeros");
			}
		}
		
		return q_f;
	}

	/**
	 * El execute de quitar una ficha primero comprueba que la casilla en las coordenadas
	 * dadas tiene una ficha y, en caso de ser asi, elimina dicha del tablero, actualizando
	 * la disponibilidad de la casilla en cuestion y de sus casillas adyacentes. Tras quitar
	 * la ficha del tablero se le devuelve a la mano del jugador.
	 * 
	 * @throws CommandExecuteException si se intenta quitar una ficha en una casilla vacia
	 */
	@Override
	public boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException {
		
		try {
			Ficha f = t.quitarFicha(coor.getFila(),coor.getColumna());
			j.robar(f);		
		}
		catch(Exception e){
			throw new CommandExecuteException(e.getMessage());
		}
		return true;
		
	}

	/**
	 * Antes de ejecutar el comando se corrigen la diferencia de enumeracion
	 * en el tablero entre el usuario y la aplicacion
	 */
	@Override
	protected boolean posible(Turno t) {
		
		System.out.println("EJECUTANDO EL POSIBLE");
		System.out.println("ASDGKMSD" + coor);
		coor.corregir();
		return t.puedoQuitar(coor);
	}

	/**
	 * Tras finalizar el comando se notifica al turno de los cambios realizados.
	 */
	@Override
	protected void addCambios(Turno t) {
		t.quitarFichaPuesta(coor);
	}

	@Override
	public String toString() {
		return ProtocoloComunicacion.QUITAR_FICHA_TABLERO + " " + this.coor;
	}
}
