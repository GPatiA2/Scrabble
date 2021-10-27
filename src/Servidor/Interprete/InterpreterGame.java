package Servidor.Interprete;

import java.io.FileNotFoundException;

import Command.Command;
import Command.CommandGenerator;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.Servidor;

public class InterpreterGame extends Interpreter{

	public InterpreterGame(Lobby lobby, Servidor servidor) {
		super(lobby, servidor);
	}

	@Override
	public void execute(String mensaje, JugadorConectado j) throws CommandParseException, CommandExecuteException {
		Command c = CommandGenerator.parseCommand(mensaje.split(" "));
		try {
			servidor.executeCommand(c);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
