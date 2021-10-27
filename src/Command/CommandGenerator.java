package Command;

import Excepciones.CommandParseException;

/**
 * Clase CommandGenerator
 * 
 * Esta clase es usada para implementar el 
 * patron Command, el cual permite encapsular
 * las operaciones concretas de cada comando.
 * 
 * @author Grupo 5
 *
 */
public abstract class CommandGenerator extends Command{
	
	/**
	 * Lista de comandos del juego
	 */
	private static Command[] listaCommandos = {
			new ComandoColocarFicha("",0,0,'0'),
			new ComandoQuitarFicha(0,0),
			new ComandoSalida(), 
			new ComandoAyuda(), 
			new ComandoPasarTurno(),
			new ComandoInstrucciones(),
			new ComandoCambiarFicha('.'),
			new ComandoSaltarJugador(), 
			new ComandoComprarComodin('.'),
			new ComandoInvertirSentido()
	};
	
	/**
	 * Constructor de CommandGenerator
	 * @param nombre
	 * @param shortcut
	 * @param detalles
	 * @param help
	 */
	public CommandGenerator(String nombre, String shortcut, String detalles, String help ) {
		super(nombre, "",shortcut,detalles, help);
	}
	
	/**
	 * Permite obtener el comando concreto introducido por el usuario, en caso
	 * de que dicho comando exista.
	 * @param comandoCompleto comando introducido por el usuario
	 * @return Command comando correspondiente al introducido por el usuario
	 * @throws CommandParseException si el comando introducido es incorrecto
	 */
	public static Command parseCommand(String[] comandoCompleto) throws CommandParseException {

		Command c = null;
		int i =0;
		while(i<listaCommandos.length && c == null) {
			c = CommandGenerator.listaCommandos[i].parse(comandoCompleto);
			i++;
		}
		if(c != null) {
			return c;
		}
		else {
			throw new CommandParseException("Debes introducir un comando valido, prueba help para ver los comandos disponibles");
		}
	}
	
	/**
	 * Devuelve la informacion de ayuda de todos los comandos
	 * @return string 
	 */
	public static String comandoAyuda() {
		int i =0; 
		String text = "";
		while(i<listaCommandos.length) {
			text += CommandGenerator.listaCommandos[i].Ayuda() + "\n" + "\n";
			i++;
		}
		return text;
	}
}
