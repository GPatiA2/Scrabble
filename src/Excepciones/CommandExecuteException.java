package Excepciones;

public class CommandExecuteException extends Exception{
	
	public CommandExecuteException(){
		super();
	}
	
	public CommandExecuteException(String e){
		super(e);		
	}

}
