package Test.CommandTests;

import static org.junit.Assert.assertFalse;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Command.ComandoCambiarFicha;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.Ficha;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Turno;

/**
 * Clase ComandoCambiarFichaTest
 *
 * Contiene pruebas unitarias para la clase ComandoCambiarFicha
 * 
 * Estas pruebas se centran en verificar que un jugador
 * puede cambiar fichas de su atril y recibir otras a 
 * cambio de forma aleatoria.
 * 
 * @author Grupo 5
 *
 */
public class ComandoCambiarFichaTest {

	//Atributos
	private static Integrante j;
	private static AdminTurnos adminTurnos;
	private static Turno turno;
	private Command c;
	
	@BeforeClass
	static public void init() throws IOException {
		j = new Jugador();
		
		GeneradorMazo genMazo = new GeneradorMazo();
			
		List<Integrante> lJugadores = new ArrayList<Integrante>();
		lJugadores.add(j);
		
		//Se crea un adminTurnos con la lista de jugadores para que rellene la mano del jugador
		adminTurnos = new AdminTurnos(genMazo, lJugadores);
		turno = new Turno(j);
		
	}
	
	/**
	 * Test para cambiar una ficha
	 * 
	 * Esta prueba consiste en descartar una ficha de la mano y obtener otra
	 * ficha nueva de forma aleatoria.
	 * 
	 * @throws CommandExecuteException
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testCambiarFicha() throws CommandExecuteException, FileNotFoundException {
		List<Ficha> mano = j.get_mano();
		assertFalse(mano.isEmpty());
		int size = mano.size();
		Ficha expected = mano.get(0);
		c = new ComandoCambiarFicha(expected.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		assertFalse(mano.contains(expected)); //Ficha que hemos descartado
		assertTrue(mano.size() == size); //Comprobamos que hemos annadido una nueva ficha
	}
	
	/**
	 * Test para verificar la regla de nuestra versión de Scrabble en la que como
	 * maximo se cambiar 7 veces las fichas en el mismo turno.
	 * 
	 * Esta prueba consiste en intentar cambiar 8 fichas en el mismo turno, lo cual 
	 * incumple la regla y por lo tanto se espera que se lanze una excepción.
	 * 
	 * @throws CommandExecuteException  
	 * @throws FileNotFoundException 
	 */
	@Test (expected = CommandExecuteException.class)
	public void testCambiarFichaMaxVeces() throws CommandExecuteException, FileNotFoundException {
		for(int i = 0; i < 7; ++i) {
			Ficha expected = j.get_mano().get(i);
			c = new ComandoCambiarFicha(expected.getLetra());
			c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		}
	}
}
