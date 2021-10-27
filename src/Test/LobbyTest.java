package Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Servidor.JugadorConectado;
import Servidor.Lobby;
/**
 * Clase LobbyTest
 * 
 * Contiene pruebas para verificar
 * la funcionalidad de la clase Lobby.java,
 * clase en la que se registran los jugadores antes
 * de comenzar la partida
 * 
 * @author Grupo 5
 *
 */
public class LobbyTest {

	
	@Test
	public void removeJugador() {
		
		Lobby l = new Lobby();
		
		l.setMaxNumJugadores(4);
		JugadorConectado j1 = new JugadorConectado (null);
		j1.setNick("1");
		JugadorConectado j2 = new JugadorConectado (null);
		j2.setNick("2");
		JugadorConectado j3 = new JugadorConectado (null);
		j3.setNick("3");
		
		l.login(j1);
		l.login(j2);
		l.login(j3);
		
		
		l.removeJugador(j3);
		assertTrue(l.getNumJugadoresJoined()==2);
		assertTrue(l.esCreador(l.getListaJugadores().get(0)));
		
		l.removeJugador(l.getListaJugadores().get(0));
		
		assertTrue(l.getNumJugadoresJoined()==1);
		assertTrue(l.esCreador(l.getListaJugadores().get(0)));
		
	}
	
	@Test
	public void lobbyFull() {
		Lobby l = new Lobby();
		l.setMaxNumJugadores(2);
		JugadorConectado j1 =new JugadorConectado(null);
				j1.setNick("1");
		JugadorConectado j2 =new JugadorConectado(null);
				j2.setNick("2");
		
		
		l.login(j1);
		
		assertFalse(l.lobbyFull());
		
		l.login(j2);
		
		assertTrue(l.lobbyFull());
		
	}
	
	@Test
	public void esCreador() {
		Lobby l= new Lobby();
		
		JugadorConectado j = new JugadorConectado(null);
		j.setNick("j1");
		
		l.login(j);
		
		assertTrue(l.esCreador(j));		
		
	}
	
	@Test
	public void login() {
		Lobby l= new Lobby();
		
		l.setMaxNumJugadores(2);
		JugadorConectado j1 = new JugadorConectado(null);
		j1.setNick("j1");
				
		l.login(j1);
		
		//Compruebo que el jugador se añade a la lista
		assertTrue(l.getNumJugadoresJoined()==1);		
		assertTrue(l.getListaJugadores().contains(j1));
		
		JugadorConectado j2 = new JugadorConectado(null);
		j2.setNick("j1");
		l.login(j2);
		
		//Como el jugador ya esta en la lista compruebo
		//que no lo añade
		assertTrue(l.getNumJugadoresJoined()==1);		
		
		JugadorConectado j3 = new JugadorConectado(null);
		j3.setNick("j3");
		
		l.login(j3);
		
		//Compruebo que el jugador se añade a la lista
		assertTrue(l.getNumJugadoresJoined()==2);
		assertTrue(l.getListaJugadores().contains(j3));
		
		JugadorConectado j4 = new JugadorConectado(null);
		j4.setNick("j4");
		
		l.login(j4);
		
		//Compruebo que al superar el numero Maximo de jugadores
		//no se añade a la lista
		assertFalse(l.getListaJugadores().contains(j4));
	}
	@Test
	public void setDificultad() {
		Lobby l = new Lobby();
		String nivel = "EASY";
		l.setDificultad(nivel);
		
		assertTrue(nivel.equals(l.getDificultad()));
	}
	
	@Test
	public void getDificultad() {
		Lobby l = new Lobby();
		String nivel = "EASY";
		l.setDificultad(nivel);
		
		assertTrue(nivel.equals(l.getDificultad()));
	}
	
	@Test
	public void setMaxNumJugadores() {
		Lobby l = new Lobby();
		l.setMaxNumJugadores(2);
		assertTrue(l.getMaxJugadores()==2);
	}
	
}
