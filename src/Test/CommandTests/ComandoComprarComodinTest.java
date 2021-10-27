package Test.CommandTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Command.ComandoComprarComodin;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.Ficha;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Turno;

/**
 * Clase ComandoComprarComodinTest
 *
 * Contiene pruebas unitarias para la clase ComandoComprarComodin
 * 
 * Estas pruebas se centran en verificar que se puede comprar un comodin
 * correctamente tras haber comprado esta ventaja por 5 monedas y haberse
 * descartado de una de las fichas del atril.
 * 
 * @author Grupo 5
 *
 */
public class ComandoComprarComodinTest {

	private static Integrante j;
	private static AdminTurnos adminTurnos;
	private static Turno turno;
	private Command c;
	
	@BeforeClass
	static public void init() throws IOException {
		j = new Jugador();
		j.recibirPuntos(20);
		j.actualizarGanadas();
		
		GeneradorMazo genMazo = new GeneradorMazo();
		GeneradorDiccionario genDic = new GeneradorDiccionario();
			
		List<Integrante> lJugadores = new ArrayList<Integrante>();
		lJugadores.add(j);
		
		//Se crea un adminTurnos con la lista de jugadores para que rellene la mano del jugador
		adminTurnos = new AdminTurnos(genMazo, genDic, lJugadores);
		turno = new Turno(adminTurnos.getJugando());
	}
	
	/**
	 * Test para comprar un comodin
	 * 
	 * Esta prueba consiste en comprar un comodin a cambio de descartarse de una de las fichas
	 * del integrante. Por esa razon nos descartamos, por ejemplo, de la primera ficha de nuestra
	 * mano y comprobamos que el integrante posee un comodin en su mano.
	 * 
	 * Para comprar un comodin se necesitan tener 5 monedas y cada 10 puntos recibes 3 monedas.
	 * Por esa razon le doy al integrante 20 puntos y actualizo sus monedas ganadas.
	 * 
	 * @throws CommandExecuteException
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testComprarComodin() throws CommandExecuteException, FileNotFoundException {
		List<Ficha> mano = j.get_mano();
		assertFalse(mano.isEmpty());
		Ficha expected = mano.get(0);
		c = new ComandoComprarComodin(expected.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		assertFalse(mano.contains(expected)); //Ficha que hemos descartado
		Ficha actual = mano.get(mano.size()-1); //Ficha que se acaba de insertar
		assertSame(actual.getLetra(), '*');
	}

}
