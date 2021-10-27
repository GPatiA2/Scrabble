package Test.CommandTests;

import static org.junit.Assert.*;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Command.ComandoSaltarJugador;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Turno;

/**
 * Clase ComandoSaltarJugadorTest
 *
 * Contiene pruebas unitarias para la clase ComandoSaltarJugador
 * 
 * Estas pruebas se centran en verificar que se puede saltar a un jugador
 * correctamente tras haber comprado esta ventaja por 3 monedas.
 * 
 * @author Grupo 5
 *
 */
public class ComandoSaltarJugadorTest {

	//Atributos
	
	static final int numJugadores = 3;
	private static final String[] nicks = {"a","b","c"};
			
	private static List<Integrante> lJugadores;
	private static AdminTurnos adminTurnos;
	
	private Command c;
		
	@BeforeClass
	static public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
			
		lJugadores = new ArrayList<Integrante>();
		for(int i = 0; i < numJugadores; ++i) {
			Integrante j = new Jugador(nicks[i].toString());
			lJugadores.add(j);
		}
			
		//Se crea un adminTurnos con la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, lJugadores);
	}
	
	/**
	 * Test para saltar a un jugador 
	 * 
	 * Esta prueba consiste en saltar a un jugador en el siguiente turno. El orden se ha invertido
	 * para que resulte mas comodo implementar la prueba, de esta forma la lista se recorre en el
	 * orden natural de los numeros 0,1,2...
	 * 
	 * Para saltar a un jugador se necesitan tener 3 monedas y cada 10 puntos recibes 3 monedas.
	 * Por esa razon le doy al integrante 10 puntos y actualizo sus monedas ganadas.
	 * 
	 * @throws CommandExecuteException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testSaltarJugador() throws FileNotFoundException, CommandExecuteException {
		c = new ComandoSaltarJugador();
		adminTurnos.getJugando().recibirPuntos(10);
		adminTurnos.getJugando().actualizarGanadas();
		adminTurnos.getJugando().setInvertirSentido(true); //Recorre la lista desde 0 en adelante
		int expected = adminTurnos.getActTurn();
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), new Turno(adminTurnos.getJugando()));
		adminTurnos.sigTurno();
		int actual = adminTurnos.getActTurn();
		expected = (expected + 2) % lJugadores.size();
		assertEquals(actual, expected);
	}
	

}
