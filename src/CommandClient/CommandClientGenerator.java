package CommandClient;
import Excepciones.CommandParseException;

public abstract class CommandClientGenerator extends CommandClient{
	//atributos
	private static CommandClient[] listaCommandos = {
			new CommandLoginCorrect(""),
			new CommandInfoAsk(),
			new CommandRefresh(null,""),
			new CommandStartGame(),
			new CommandError("",false),
			new CommandTurnos("",""),
			new CommandAniadirMano(null),
			new CommandOnRegister(0,null,0,null),
			new CommandPuntos(0,""),
			new CommandMonedas(0,""),
			new CommandBorrarMano(null,false),
			new CommandCasilla(null, null)
			
	};
	
	//constructor
	public CommandClientGenerator() {
		super("");
	}
	
	public static CommandClient parseCommand(String[] comandoCompleto) throws CommandParseException {

		CommandClient c = null;
		int i =0;
		while(i<listaCommandos.length && c == null) {
			c = CommandClientGenerator.listaCommandos[i].parse(comandoCompleto);
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
