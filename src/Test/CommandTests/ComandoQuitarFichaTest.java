package Test.CommandTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Command.ComandoColocarFicha;
import Command.ComandoQuitarFicha;
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
* Clase ComandoQuitarFichaTest
*
* Contiene pruebas unitarias para la clase ComandoQuitarFicha
* 
* Estas pruebas se centran en verificar que se pueden sacar
* correctamente las fichas del tablero y de que el comando
* funciona correctamente.
*
* @author Grupo 5
*/
public class ComandoQuitarFichaTest {

	//Atributos
	private static final int coordXCentro = 8, coordYCentro = 8;
		
	private Command c;
	private AdminTurnos adminTurnos;
	private Turno turno;
	private List<Integrante> lJugadores;
	private Ficha fichaPrueba; 
		
	@Before
	public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
		GeneradorDiccionario genDic = new GeneradorDiccionario();
			
		//Se crea una lista de jugadores  
		lJugadores = new ArrayList<Integrante>();
		lJugadores.add(new Jugador());
			
		//Se crea un adminTurnos con la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, genDic, lJugadores);
	}
		
	/**
	 * Test para probar que se pueden quitar las fichas del tablero
	 * 
	 * Esta prueba se centra en verificar unicamente el metodo execute
	 * y consiste en colocar y quitar una ficha en cada una de las casillas.
	 * 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	@Test
	public void testQuitarFicha() throws FileNotFoundException, CommandExecuteException {
		for(int i = 0; i < coordXCentro; ++i) {
			for(int j = 0; j < coordYCentro; ++j) {
				fichaPrueba = adminTurnos.getJugando().get_ficha_posicion(0);
				adminTurnos.getTablero().setFicha(fichaPrueba, i, j);
				assertFalse(adminTurnos.getTablero().getCasilla(i, j).empty());
				c = new ComandoQuitarFicha(i,j);
				c.execute(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando());
				assertTrue(adminTurnos.getTablero().getCasilla(i, j).empty());
			}
		}
	}
	
	/**
	 * Test que consiste en intentar quitar una ficha de una casilla vacia, se
	 * espera que lanze excepcion.
	 * 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	@Test(expected = CommandExecuteException.class)
	public void testQuitarFichaCasillaVacia() throws FileNotFoundException, CommandExecuteException {
		
		fichaPrueba = adminTurnos.getJugando().get_ficha_posicion(0);
		assertTrue(adminTurnos.getTablero().getCasilla(5, 6).empty());
		c = new ComandoQuitarFicha(6,7);
		c.execute(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando());
	
	}

	/**
	 * Test para probar el comando quitar una ficha.
	 * 
	 * Esta prueba consiste en colocar una ficha en el centro, ya que es la unica casilla
	 * disponible durante el primer turno, y despues quitarla. 
	 * 
	 * Esta prueba es dependiente del ComandoColocarFicha ya que el ComandoQuitarFicha necesita
	 * verificar que la ficha que se quiere quitar pertenece a una de las fichas del integrante 
	 * colocadas en ese mismo turno y, para ello, se registra la ficha colocada durante el 
	 * ComandoColocarFicha en la clase Turno.
	 * 
	 * En caso de fallo por el ComandoColocarFicha acudir a:
	 * @see /src/Command/ComandoColocarFicha.java
	 * @see /src/Test.CommandTests/ComandoColocarFichaTest.java
	 * 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	@Test
	public void testQuitarFichaComando() throws FileNotFoundException, CommandExecuteException {
		Turno turno = new Turno(adminTurnos.getJugando());
		
		fichaPrueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(fichaPrueba.getId(), coordXCentro, coordYCentro, fichaPrueba.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		assertFalse(adminTurnos.getTablero().getCasilla(coordXCentro-1, coordYCentro-1).empty());
		c = new ComandoQuitarFicha(coordXCentro,coordYCentro);
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		assertTrue(adminTurnos.getTablero().getCasilla(coordXCentro-1, coordYCentro).empty());
			
	}
}


