package controlador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import Cliente.TraductorCliente;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.Casilla;
import modelo.Ficha;
import modelo.TableroObserver;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.Memento;
import modelo.TManagerObserver;
import utils.Coordenadas;

public class Sustituto implements TableroObserver,TManagerObserver, JugadorObserver, Registrador{

	private List<TableroObserver> listaObservadoresGame;
	private List<TManagerObserver> listaObservadoresTManager;
	private List<JugadorObserver> listaObservadoresJugador;
	
	public Sustituto(){
		listaObservadoresGame = new ArrayList<>();
		listaObservadoresTManager = new ArrayList<>();
		listaObservadoresJugador = new ArrayList<>();
	}
	
	@Override
	public void registerOn(Registrador c) {
		
	}

	@Override
	public void mostrarTurnos(String act, String sig) {
		for (TManagerObserver o : this.listaObservadoresTManager) {
			o.mostrarTurnos(act, sig);
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
	public void actualizaCasilla(Coordenadas coord, Casilla c) {
		for (TableroObserver o : this.listaObservadoresGame) {
			o.actualizaCasilla(coord, c);
		}
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
	public void addGameObserver(TableroObserver ob) {
		this.listaObservadoresGame.add(ob);
	}

	@Override
	public void addTManagerObserver(TManagerObserver ob) {
		this.listaObservadoresTManager.add(ob);
	}
	
	@Override
	public void addJugadorObserver(JugadorObserver ob) {
		// TODO Auto-generated method stub
		listaObservadoresJugador.add(ob);
	}

	@Override
	public void removeGameObserver(TableroObserver ob) {
		this.listaObservadoresGame.remove(ob);
		
	}

	@Override
	public void removeTManagerObserver(TManagerObserver ob) {
		this.listaObservadoresTManager.remove(ob);
		
	}
	
	@Override
	public void removeJugadorObserver(JugadorObserver ob) {
		// TODO Auto-generated method stub

		listaObservadoresJugador.remove(ob);
	}

	@Override
	public void runCommand(Command c) throws FileNotFoundException, CommandExecuteException {
		TraductorCliente.getTraductor().runCommand(c);
	}

	@Override
	public void onError(String err,String nick) {
		// TODO Auto-generated method stub
		for(TManagerObserver ob : this.listaObservadoresTManager) {
			ob.onError(err,nick);
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
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		// TODO Auto-generated method stub
		for(JugadorObserver ob: listaObservadoresJugador) {
			ob.onRegister(nick, puntos, monedas,mano);
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
	public void turnoAcabado() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load(File fichero) throws FileNotFoundException {
		TraductorCliente.getTraductor().load(fichero);
	}

	@Override
	public void save(String fichero) throws FileNotFoundException {
		TraductorCliente.getTraductor().save(fichero);
	}

	@Override
	public void onRegister(List<List<Casilla>> grid) {
		// TODO Auto-generated method stub
		
	}


}
