package Cliente;

import java.util.ArrayList;
import java.util.List;

import controlador.Controller;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.Observable;


public class JugadorClient implements JugadorObserver, Observable<JugadorObserver>{

	private List<JugadorObserver> listaObservadoresJugador;
	
	public JugadorClient() {
		listaObservadoresJugador = new ArrayList<>();
	}
	

	@Override
	public void addObserver(JugadorObserver o) {
		this.listaObservadoresJugador.add(o);
	}

	@Override
	public void removeObserver(JugadorObserver o) {
		this.listaObservadoresJugador.remove(o);
		
	}

	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		for (JugadorObserver o : this.listaObservadoresJugador) {
			o.fichaRobada(f, j);
		}
	}

	@Override
	public void fichaUsada(Ficha f, Integrante player) {
		for (JugadorObserver o : this.listaObservadoresJugador) {
			o.fichaUsada(f, player);
		}
	}

	@Override
	public void borrarFichaMano(Ficha f, Integrante j, boolean bienColocada) {
		for (JugadorObserver o : this.listaObservadoresJugador) {
			o.borrarFichaMano(f, j, bienColocada);
		}
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) {
		for (JugadorObserver o : this.listaObservadoresJugador) {
			o.mostrarPuntos(puntos, nick);
		}
	}

	@Override
	public void mostrarMonedas(int monedas, String nick) {
		// TODO Auto-generated method stub
		for (JugadorObserver o : this.listaObservadoresJugador) {
			o.mostrarMonedas(monedas, nick);
		}
	}

	@Override
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		// TODO Auto-generated method stub
		for(JugadorObserver ob: listaObservadoresJugador) {
			ob.onRegister(nick, puntos, monedas,mano);
		}
	}


	@Override
	public void registerOn(Controller c) {
		// TODO Auto-generated method stub
		
	}


}
