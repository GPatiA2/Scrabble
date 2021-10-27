package Cliente;

import java.util.ArrayList;
import java.util.List;

import Servidor.LobbyObserver;
import controlador.Controller;
import controlador.ControllerLobby;
import modelo.Observable;

public class LobbyClient implements LobbyObserver<String>, Observable<LobbyObserver<String>>{

	private List<LobbyObserver<String>> listaObservadoresLobby;
	public LobbyClient() {
		this.listaObservadoresLobby = new ArrayList<>();
	}
	@Override
	public void registerOn(Controller c) {
		
	}
	@Override
	public void addObserver(LobbyObserver<String> o) {
		this.listaObservadoresLobby.add(o);
	}
	@Override
	public void removeObserver(LobbyObserver<String> o) {
		this.listaObservadoresLobby.remove(o);
	}
	@Override
	public void loginCorrect(String j) {
		//Para evitar ConcurrentModificationException que se da al iterar la
		//lista y modificarla a la vez
		List<LobbyObserver<String>> l = new ArrayList<>(this.listaObservadoresLobby);
		
		for(LobbyObserver<String> ob: l) {
			ob.loginCorrect(j);
		}
	}
	@Override
	public void Error(String j, String error) {
		for(LobbyObserver<String> ob: this.listaObservadoresLobby) {
			ob.Error(j, error);
		}
	}
	@Override
	public void InfoRequest(String j) {
		for(LobbyObserver<String> ob: this.listaObservadoresLobby) {
			ob.InfoRequest(j);
		}
	}

	@Override
	public void refresh(List<String> j, String creador) {
		for(LobbyObserver<String> ob: this.listaObservadoresLobby) {
			ob.refresh(j, creador);
		}
	}
	@Override
	public void start_game() {
		for(LobbyObserver<String> ob: this.listaObservadoresLobby) {
			ob.start_game();
		}
	}




}
