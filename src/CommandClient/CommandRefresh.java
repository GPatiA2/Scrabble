package CommandClient;

import java.util.ArrayList;
import java.util.List;

import Cliente.JugadorClient;
import Cliente.LobbyClient;
import Cliente.TManagerClient;
import Cliente.TableroClient;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import Servidor.ProtocoloComunicacion;

public class CommandRefresh extends CommandClient{
	
	private List<String > listaJugadores;
	private String creador;
	public CommandRefresh(List<String> s, String creador) {
		super(ProtocoloComunicacion.REFRESH);
		listaJugadores = s;
		this.creador = creador;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {
		
			List<String> listaju = new ArrayList<>();
			String[] lista = comandoCompleto;
			boolean creador = false;
			String c = null;
			for(String l : lista ) {
				if(!l.equals(this.nombreProtocolo)) {
					if (l.equals("creador")){
						creador = true;
					}
					
					else if(!l.equals("invitado")) {
	
						if (creador) {
							c=l;
							creador = false;
						}
						listaju.add(l);
					}
				}
			}
			
			return new CommandRefresh(listaju,c);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		l.refresh(this.listaJugadores, this.creador);
	}
	
	@Override
	public String toString() {
		String str = "";
		String ocupacion ;
		for (String jugadores : this.listaJugadores) {
			if (jugadores.equals(creador)) ocupacion = "creador";
			else ocupacion = "invitado";
			str += ocupacion + " " +jugadores+ " ";
		}
		return super.toString() + " " + str;
	}
}