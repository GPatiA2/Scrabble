package Servidor.InterpreterFactoria;

import Servidor.Lobby;
import Servidor.Servidor;
import Servidor.Interprete.IntepreterServer;
import Servidor.Interprete.InterpreteLobby;
import Servidor.Interprete.Interpreter;
import Servidor.Interprete.InterpreterGame;

public class InterpreterFactory implements InterpreterFactoryInterface{
	private Lobby lobby;
	private Servidor servidor;
	
	
	public InterpreterFactory(Lobby lobby, Servidor servidor) {
		super();
		this.lobby = lobby;
		this.servidor = servidor;
	}

	public enum Estado{
		LOBB,GAME,SERV;
		public static Estado isState(String name) {
			for(Estado e : values()){
				if(name.equals(e.toString())) {
					return e;
				}
			};
			
			return null;
		}
	}	
	

	@Override
	public Interpreter buildInterpreter(Estado e) {
		if(e.equals(Estado.LOBB)) {
			return new InterpreteLobby(lobby,servidor);
		}
		else if(e.equals(Estado.GAME)) {
			return new InterpreterGame(lobby, servidor);
		}
		else if(e.equals(Estado.SERV)) {
			return new IntepreterServer(lobby,servidor);
		}
		else return null;
	}
}
