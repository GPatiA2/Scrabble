package Servidor.Interprete;

import java.io.File;
import java.io.FileNotFoundException;

import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.JugadorConectado;
import Servidor.Lobby;
import Servidor.ProtocoloComunicacion;
import Servidor.Servidor;

public class IntepreterServer extends Interpreter{

	public IntepreterServer(Lobby lobby, Servidor servidor) {
		super(lobby,servidor);
	}

	@Override
	public void execute(String mensaje, JugadorConectado j)
			throws CommandParseException, CommandExecuteException {
		try {
			if (mensaje.startsWith(ProtocoloComunicacion.GAME_REQUEST)){
				servidor.play(j);
			}		
			if (mensaje.startsWith(ProtocoloComunicacion.LOAD)) {
				servidor.load(new File(mensaje.split(" ")[1]));
			}
			else if(mensaje.startsWith(ProtocoloComunicacion.SAVE)) {
				servidor.save(mensaje.split(" ")[1]);
			}
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
