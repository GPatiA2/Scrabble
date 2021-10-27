package Excepciones;

public class CommandParseException extends Exception {
	public CommandParseException(){
		super();
	}
	
	public CommandParseException(String e){
		super(e);		
	}

}
