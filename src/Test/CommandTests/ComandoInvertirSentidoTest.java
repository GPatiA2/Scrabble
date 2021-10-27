package Test.CommandTests;

import static org.junit.Assert.*;





import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Command.ComandoInvertirSentido;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Turno;

/**
 * Clase ComandoInvertirSentidoTest
 *
 * Contiene pruebas unitarias para la clase ComandoInvertirSentido
 * 
 * Estas pruebas se centran en verificar que se puede invertir el sentido
 * correctamente tras haber comprado esta ventaja por 5 monedas.
 * 
 * @author Grupo 5
 *
 */
public class ComandoInvertirSentidoTest {

	//Atributos
	
	static final int numJugadores = 3;
	private static final String[] nicks = {"a","b","c"};
		
	private static List<Integrante> lJugadores;
	private static AdminTurnos adminTurnos;
	private static Turno turno;
	private Command c;

	@BeforeClass
	static public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
			
		lJugadores = new ArrayList<>();
		for(int i = 0; i < numJugadores; ++i) {
			Integrante j = new Jugador(nicks[i].toString());
			lJugadores.add(j);
		}
			
		//Se crea un adminTurnos la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, lJugadores);
		turno = new Turno(adminTurnos.getJugando());
	}
	
	
	/**
	 * Test para cambiar de sentido
	 * 
	 * Esta prueba consiste en pasar el turno, cambiar el sentido y volver a pasar el turno.
	 * De esta forma el jugador que tenia el primer turno debe ser el mismo que el que tiene 
	 * el ultimo turno.
	 * 
	 * Para invertir el sentido se necesitan tener 5 monedas y cada 10 puntos recibes 3 monedas.
	 * Por esa razon le doy al integrante 20 puntos y actualizo sus monedas ganadas.
	 * 
	 * @throws CommandExecuteException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCambiarSentido() throws FileNotFoundException, CommandExecuteException {
		int expected = adminTurnos.getActTurn();
		adminTurnos.sigTurno();
		assertNotEquals(expected, adminTurnos.getActTurn());
		c = new ComandoInvertirSentido();
		adminTurnos.getJugando().recibirPuntos(20);
		adminTurnos.getJugando().actualizarGanadas();
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		adminTurnos.sigTurno();
		int actual = adminTurnos.getActTurn();
		assertEquals(actual, expected);
	}

}
