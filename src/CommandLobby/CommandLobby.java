package CommandLobby;


import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;

public abstract class CommandLobby {
	
	protected final String nombreProtocolo;
	
	//Constructor
	public CommandLobby(String nombreProtocolo) {
		this.nombreProtocolo = nombreProtocolo;
	}
	
	/**
	 * Realiza cambios en el Lobby
	 * @param l Lobby
	 * @param j lista de jugadores conectados en el servidor
	 * @param s Servidor
	 * @throws CommandExecuteException
	 */
	public abstract void execute(Lobby l,JugadorConectado j) throws CommandExecuteException;
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
	public CommandLobby parse(String[] comandoCompleto) throws CommandParseException{
		CommandLobby command = CommandLobbyGenerator.parseCommand(comandoCompleto);
		return command;
	}
	
	@Override
	public String toString() {
		return this.nombreProtocolo;
	}
	
}
