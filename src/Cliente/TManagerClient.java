package Cliente;

import java.util.ArrayList;
import java.util.List;

import controlador.Controller;
import modelo.Integrante;
import modelo.Observable;
import modelo.TManagerObserver;

public class TManagerClient implements TManagerObserver, Observable<TManagerObserver> {
	private List<TManagerObserver> listaObservadoresTManager;
	
	public TManagerClient() {
		this.listaObservadoresTManager = new ArrayList<>();
	}
	@Override
	public void registerOn(Controller c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addObserver(TManagerObserver o) {
		this.listaObservadoresTManager.add(o);
	}

	@Override
	public void removeObserver(TManagerObserver o) {
		this.listaObservadoresTManager.remove(o);
	}

	@Override
	public void mostrarTurnos(String act, String sig) {
		for (TManagerObserver o : this.listaObservadoresTManager) {
			o.mostrarTurnos(act, sig);
		}
	}
	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {
		// TODO Auto-generated method stub
		for (TManagerObserver o : this.listaObservadoresTManager) {
			o.nuevoTurno(i, act, sig);
		}
	}

	@Override
	public void turnoAcabado(String j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err,String nick) {
		// TODO Auto-generated method stub
		for(TManagerObserver ob : this.listaObservadoresTManager) {
			ob.onError(err,nick);
		}
	}

	@Override
	public void onRegister(String act, String sig) {
		// TODO Auto-generated method stub
		for(TManagerObserver ob: listaObservadoresTManager) {
			ob.onRegister(act, sig);
		}
	}
	@Override
	public void partidaAcabada(String nick) {
		//TERMINARLO
	}

}
