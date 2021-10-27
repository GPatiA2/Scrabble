
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
import modelo.Ficha;

public class CommandOnRegister extends CommandClient{
	
	private int puntosJugador;
	private String nick;
	private int monedas;
	private List<Ficha> mano;
	public CommandOnRegister(int p, String n, int m, List<Ficha> l) {
		super(ProtocoloComunicacion.ON_REGISTER);
		this.puntosJugador = p;
		this.nick = n;
		this.monedas = m;
		this.mano = l;
	}
	@Override
	public CommandClient parse(String[] comandoCompleto) throws CommandParseException {
		if(matchCommandName(comandoCompleto[0])) {

			String nick = comandoCompleto[1];
			int puntosJugador = Integer.parseInt(comandoCompleto[2]);	
			int monedasJugador = Integer.parseInt(comandoCompleto[3]);
			
			List<Ficha> mano = new ArrayList<>();
			for (int i =4 ; i <comandoCompleto.length;i++) {
				int puntos = comandoCompleto[i].charAt(2)- '0';
				Ficha f = new Ficha(comandoCompleto[i].charAt(0), puntos);
				f.setId(comandoCompleto[++i]);
				mano.add(f);
			}
			
			return new CommandOnRegister(puntosJugador, nick, monedasJugador, mano);
		}
		else {
			return null;
		}
	}
	
	@Override
	public void execute(String mensaje,TableroClient t, JugadorClient j, TManagerClient m, LobbyClient l) throws CommandExecuteException {
		j.mostrarPuntos(puntosJugador, nick);
		j.onRegister(nick, puntosJugador, monedas, mano);
	}
	
	@Override
	public String toString() {
		String mensaje = nick + " " +this.puntosJugador + " " +monedas;
		for (Ficha f : mano) mensaje += " " + f.toString() + " " + f.getId();
	
		return super.toString() + " " + mensaje;
	}
	
}
