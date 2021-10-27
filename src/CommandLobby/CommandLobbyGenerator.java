package CommandLobby;

import Excepciones.CommandParseException;

public abstract class CommandLobbyGenerator extends CommandLobby{
	//atributos
	private static CommandLobby[] listaCommandos = {
			new CommandLogin(""),
			new CommandLogout(),
			new CommandInfoRequest(0,"")
	};
	
	//constructor
	public CommandLobbyGenerator() {
		super("");
	}
	
	public static CommandLobby parseCommand(String[] comandoCompleto) throws CommandParseException {

		CommandLobby c = null;
		int i =0;
		while(i<listaCommandos.length && c == null) {
			c = CommandLobbyGenerator.listaCommandos[i].parse(comandoCompleto);
			i++;
		}
		if(c != null) {
			return c;
		}
		else {
			throw new CommandParseException("Debes introducir un comando valido, prueba help para ver los comandos disponibles");
		}
	}
}
