package Command;

import java.io.FileNotFoundException;


import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

/**
 * Clase Command
 * 
 * Esta clase es usada para implementar el 
 * patron Command, contiene metodos abstractos
 * para que cada comando concreto pueda implementar
 * su funcionalidad.
 * 
 * @author Grupo
 *
 */
public abstract class Command {
	
	protected final String nombreProtocolo;
	protected final String nombre;
	protected final String shortcut;
	private   final String detalles;
	private   final String help;
	
	/**
	 * Constructor de Command
	 * @param nombre
	 * @param nombreProtocolo
	 * @param shortcut
	 * @param detalles
	 * @param help
	 */
	public Command(String nombre, String nombreProtocolo,String shortcut, String detalles, String help ) {
		this.nombre = nombre;
		this.nombreProtocolo = nombreProtocolo;
		this.shortcut = shortcut;
		this.detalles = detalles;
		this.help = help;
	}
	
	/**
	 * Hace interactuar a los diferentes elementos del modelo para que se realicen los cambios propios
	 * del comando sobre el modelo
	 * @param t Tablero sobre el que los integrantes colocan y quitan fichas
	 * @param m Conjunto de fichas disponibles para los jugadores
	 * @param j Jugador que tiene el turno en curso
	 * @param g Encargado de verificar palabras empleando el diccionario
	 * @return b Si la ejecucion se ha realizado con exito(true)
	 * @throws CommandExecuteException
	 */
	public abstract boolean execute(Tablero t, Mazo m, Integrante j) throws CommandExecuteException;
	/**
	 * Comprueba si el jugador puede realizar los cambios que permite el comando con los valores del Turno que
	 * indican lo sucedido anteriormente durante este turno.
	 * @param t Turno en curso
	 * @param g Game
	 * @return b Si se puede pasar al metodo execute del comando(true)
	 * @see Turno
	 */
	protected abstract boolean posible(Turno t);
	/**
	 * Metodo para registrar los cambios que producen las acciones de este comando en el juego
	 * @param t Turno en curso
	 * @throws FileNotFoundException
	 */
	protected abstract void addCambios(Turno t) throws FileNotFoundException;
	/**
	 * Metodo para que los integrantes de la partida realicen cambios sobre el juego
	 * @param tb Tablero sobre el que colocar fichas
	 * @param m  Conjunto de fichas con el que los integrantes interactuan
	 * @param j  Integrante que ejecuta el comando
	 * @param g  Encargado de verificar las palabras empleando el diccionario
	 * @param t	 Turno en curso
	 * @return b Si la ejecucion se ha completado con exito(true) 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	public boolean perform(Tablero tb, Mazo m, Integrante j, Turno t) throws FileNotFoundException, CommandExecuteException {
		if(posible(t)) {
			boolean b = false;
			try {
				b = execute(tb,m,j);
			} 
			catch (CommandExecuteException e1) {
				
				throw new CommandExecuteException(e1.getMessage());
				
			}
			if(b) {				
				try{
					addCambios(t);
				}
				catch(FileNotFoundException e) {
					System.out.println("Nombre del fichero no valido");
					throw new CommandExecuteException("No puedo ejecutar el comando, revisa las reglas para entender por que");
				}
			}

			return b;
		}
		else {
			throw new CommandExecuteException("No puedo ejecutar el comando, revisa las reglas para entender por que");
			
		}
	}
	/**
	 * Comprueba si el primer argumento del comando que introduce el usuario coincide con este comando
	 * @param nombre_introducido Primer argumento del comando introducido por le usuario
	 * @return b Si el primer argumento del comando coincide con el nombre del comando (true)
	 * @see Command 
	 */
	protected boolean matchCommandName(String nombre_introducido) {
		// si el comando introducido coincide con su nombre o su shortcut devuelvo true;
		// Uso equalsIngoreCase(String n) para obviar las mayusculas y minusculas 
		// Este metodo lo heredan las subclases de command
		
		return this.shortcut.equalsIgnoreCase(nombre_introducido) || this.nombre.equalsIgnoreCase(nombre_introducido) 
				|| this.nombreProtocolo.equals(nombre_introducido);
		
	}
	
	/**
	 * Transforma la entrada del usuario en un comando completo
	 * @param comandoCompleto String que 
	 * @return
	 * @throws CommandParseException
	 */
	public Command parse(String[] comandoCompleto) throws CommandParseException{
		Command command = CommandGenerator.parseCommand(comandoCompleto);
		return command;
	}
	
	public String Ayuda() {
		return nombre + "(" + shortcut + ") : " + detalles + System.lineSeparator() + "\t" + help;
	}
	
	@Override
	public String toString() {
		return this.nombreProtocolo;
	}

	
}
