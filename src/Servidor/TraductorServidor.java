package Servidor;

import java.util.ArrayList;
import java.util.List;

import CommandClient.*;
import controlador.Controller;
import modelo.Casilla;
import modelo.Ficha;
import modelo.TableroObserver;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.TManagerObserver;
import utils.Coordenadas;
/**
 * Clase especializada que se encarga de traducir los mensajes 
 * del servidor a el formato del socket.
 */
public class TraductorServidor implements TableroObserver,TManagerObserver,JugadorObserver, LobbyObserver<JugadorConectado>{

	private List<JugadorConectado> l;
	/**
	 * Controlador
	 * @see Controller
	 */
	private Controller ctrl;	
	
	
	//Metodos de GameObserver
	@Override
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(new CommandCasilla(coord, c));
		}
		try {
			Thread.sleep(400);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				CommandClient c = new CommandAniadirMano(f);
				jugador.getOutputStream().println(c);
			}
		}
	}

	@Override
	public void fichaUsada(Ficha f, Integrante j) {
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				jugador.getOutputStream().println(new CommandBorrarMano(f, true));
			}
		}
	}

	@Override
	public void borrarFichaMano(Ficha f, Integrante j,boolean colocada) {
		
		for(JugadorConectado jugador : this.l) {
			if (jugador.getNick().equals(j.getNick())){
				jugador.getOutputStream().println(new CommandBorrarMano(f, colocada));}
		}
	}
	//Metodos TManagerObserver
	@Override
	public void mostrarTurnos(String act, String sig) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(new CommandTurnos(act,sig));
		}
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) {
		for(JugadorConectado jugador : this.l) {
			if(jugador.getNick().equals(nick)) {
				jugador.getOutputStream().println(new CommandPuntos(puntos, nick));
			}
		}
	}

	@Override
	public void mostrarMonedas(int monedas, String nick) {
		for(JugadorConectado jugador : this.l) {
			if(jugador.getNick().equals(nick)) {
				jugador.getOutputStream().println(new CommandMonedas(monedas, nick));
			}
		}
	}
	
	//Metodos LobbyObserver

	@Override
	public void loginCorrect(JugadorConectado j) {
		CommandClient c = new CommandLoginCorrect(j.getNick());
		j.getOutputStream().println(c);
	}

	@Override
	public void InfoRequest(JugadorConectado j) {
		CommandClient c = new CommandInfoAsk();
		j.getOutputStream().println(c);
		
	}

	@Override
	public void refresh(List<JugadorConectado> j, JugadorConectado creador) {
		this.l = j;
		List<String> nombres = new ArrayList<>();
		for(JugadorConectado jugador : j) {
			nombres.add(jugador.getNick());
		}
		CommandClient c = new CommandRefresh(nombres, creador.getNick());
		for (JugadorConectado jugadores : this.l) {
			jugadores.getOutputStream().println(c);			
		}
		
	}
	@Override
	public void start_game() {
		for(JugadorConectado j : this.l) {
			j.getOutputStream().println(new CommandStartGame());
		}
	}

	@Override
	public void Error(JugadorConectado j, String error) {
		j.getOutputStream().println(new CommandError(error,true));	
	}
	

	/*******************************/
	@Override
	public void registerOn(Controller c) {
		ctrl = c;
		ctrl.addGameObserver(this);
		ctrl.addTManagerObserver(this);
		for(JugadorConectado j : this.l) {
			ctrl.addJugadorObserver(this,j.getNick());	
		}
	}
	@Override
	public void onError(String err, String nick) {
		for(JugadorConectado jugador : this.l) {
			if (nick.equals(jugador.getNick())){	
				jugador.getOutputStream().println(new CommandError(err,false));
			}
			
		}
	}
	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {

	}
	@Override
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		for(JugadorConectado jugador : this.l) {
			if (nick.equals(jugador.getNick())){
				jugador.getOutputStream().println(new CommandOnRegister(puntos, nick, monedas, mano));
			}
			
		}
	}
	@Override
	public void onRegister(String act, String sig) {
		for(JugadorConectado jugador : this.l) {
			jugador.getOutputStream().println(new CommandTurnos(act, sig));
		}
	}
	@Override
	public void turnoAcabado(String j) {
		ctrl.removeJugadorObserver(this, j);
		ctrl.addJugadorObserver(this,j);
		
	}
	@Override
	public void onRegister(List<List<Casilla>> grid) {
		// TODO Auto-generated method stub		
	}


	@Override
	public void partidaAcabada(String nick) {
		// TODO Auto-generated method stub
		
	}


}
