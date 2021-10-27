package Command;

import Servidor.ProtocoloComunicacion;
import modelo.Game;
import modelo.Integrante;
import modelo.Mazo;
import modelo.Tablero;
import modelo.Turno;

public class ComandoInstrucciones extends Command {
	
	private static final String help = "Este comando permite imprimir las instrucciones del juego";
	
	
	public ComandoInstrucciones(){
		super("instrucciones", ProtocoloComunicacion.INSTRUCCIONES,"i", "Instrucciones", help);
	}
	
	public boolean execute(Tablero t, Mazo m, Integrante j) {
		
		return true;
	}
	
	@Override
	public Command parse(String[] comandoCompleto){
		if(matchCommandName(comandoCompleto[0])) {
			ComandoInstrucciones aux = new ComandoInstrucciones();
			return aux;			
		}
		
		return null;
	}

	@Override
	protected boolean posible(Turno t) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void addCambios(Turno t) {
		// TODO Auto-generated method stub
		
	}
}
