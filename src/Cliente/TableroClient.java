package Cliente;

import java.util.ArrayList;
import java.util.List;

import controlador.Controller;
import modelo.Casilla;
import modelo.Observable;
import modelo.TableroObserver;
import utils.Coordenadas;

public class TableroClient implements TableroObserver, Observable<TableroObserver>{

	private List<TableroObserver> listaObservadores;
	
	public TableroClient() {
		this.listaObservadores = new ArrayList<>();
	}
	
	@Override
	public void registerOn(Controller c) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		for (TableroObserver o : this.listaObservadores) {
			o.actualizaCasilla(coord, c);
		}
	}

	@Override
	public void onRegister(List<List<Casilla>> grid) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addObserver(TableroObserver o) {
		this.listaObservadores.add(o);
	}


	@Override
	public void removeObserver(TableroObserver o) {
		this.listaObservadores.remove(o);
	}

}
