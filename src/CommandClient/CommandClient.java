package CommandClient;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;

public abstract class CommandClient {
	
	protected final String nombreProtocolo;
	
	//Constructor
	public CommandClient(String nombreProtocolo) {
		this.nombreProtocolo = nombreProtocolo;
	}
	
	/**
	 * Realiza cambios en la vista
	 * @param mensaje String mesaje enviado por el servidor
	 * @param t tablero
	 * @param j jugador
	 * @param m tmanager
	 * @param l lobby
	 * @throws CommandExecuteException
	 */
	public abstract void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException;
	/**
	 * Comprueba si el primer argumento del comando que introduce el usuario coincide con este comando
	 * @param nombre_introducido Primer argumento del comando introducido por le usuario
	 * @return b Si el primer argumento del comando coincide con el nombre del comando (true)
	 * @see Command 
	 */
	protected boolean matchCommandName(String nombre_introducido) {		
		return this.nombreProtocolo.equals(nombre_introducido);		
	}
	
	/**
	 * Transforma la entrada del usuario en un comando completo
	 * @param comandoCompleto String  
	 * @return
	 * @throws CommandParseException
	 */
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException{
		CommandClient command = CommandClientGenerator.parseCommand(comandoCompleto);
		return command;
	}
	
	@Override
	public String toString() {
		return this.nombreProtocolo;
	}
}
