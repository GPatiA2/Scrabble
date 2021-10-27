package Test;

import static org.junit.Assert.*;



import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Excepciones.CommandExecuteException;
import modelo.Ficha;
import modelo.Tablero;

/**
 * Clase TableroTest
 * 
 * Contiene pruebas para la clase Tablero.
 * Estas pruebas se basan en comprobar la funcionalidad
 * básica del tablero sin tener en cuenta restricciones 
 * del reglamento del juego.
 * 
 * Para comprobar la funcionalidad del juego consultar los
 * test correspondientes a los comandos.
 * 
 * @author Grupo 5
 *
 */
@RunWith(value = Parameterized.class) 
public class TableroTest {
	
	private static final int nFilas = 15;
	private static final int nCols = 15;

	@Parameters  
	public static Iterable<Object[]> getData() { 
		return Arrays.asList(new Object[][] { 
			{new Ficha('A', 1), 7, 7}
		});
	} 
	
	//Atributos
	
	private Tablero tab;
	private Ficha expected;
	private int coordX, coordY;
	
	public TableroTest(Ficha expected, int coordX, int coordY){
		this.expected = expected;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	@Before
	public void init() {
		tab = new Tablero();
	}
	
	/**
	 * Test para añadir una ficha al tablero
	 */
	@Test
	public void testAnadirFicha() {
		assertTrue(tab.emptyCasilla(coordX, coordY));
		tab.setFicha(expected,coordX,coordY);
		Ficha actual = tab.getFicha(coordX, coordY);
		assertSame("Annadir ficha ha fallado", expected, actual);
	}
	
	/**
	 * Test para colocar una ficha en la casilla central
	 */
	@Test
	public void testColocarCentro() {
		Ficha f = new Ficha('a',1);
		tab.aniadeFicha(f, 7, 7);
		assertEquals(tab.getCasilla(7, 7).getFicha(), f);
	}
	
	/**
	 * Test para quitar una ficha del tablero
	 * @throws CommandExecuteException si se intenta quitar una ficha de una casilla sin ficha
	 */
	@Test
	public void testQuitarFicha1() throws CommandExecuteException {
		tab.setFicha(expected,coordX,coordY);
		tab.quitarFicha(coordX, coordY);
		Ficha actual = tab.getFicha(coordX, coordY);
		assertNull(actual);
	}
	
	/**
	 * Test para quitar varias fichas, variante del primer test para quitar fichas
	 */
	@Test
	public void testQuitarFicha2() {
		tab.aniadeFicha(new Ficha('a',1), 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(tab.esDisponible(7, 7+i)) {				
					tab.aniadeFicha(new Ficha('a',1), 7, 7+i);
				}
				else {
					System.out.println("No disponible");				
				}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		try {
			tab.quitarFicha(7, 7);
		} catch (CommandExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(tab.getCasilla(7, 7).empty());
	}
	
	/**
	 * Test para comprobar que todas las casillas se encuentran correctamente 
	 * inicializadas. Al empezar el juego la unica casilla disponible para 
	 * colocar una ficha es la central. Ademas, todas las casillas deben estar 
	 * vacias.
	 * @throws IOException en caso de no poder generar el mazo
	 */
	@Test
	public void testTableroPrimerTurno() throws IOException {
		assertTrue(tab.centroDisponible()); 
		for(int i = 0; i < nFilas; i++) {
			for(int j = 0; j < nCols; j++) {

				assertTrue(tab.getCasilla(i, j).empty());
				if(i != 7 && j != 7) { //Si no es el centro
					assertFalse(tab.getCasilla(i, j).esDisponible());
				}
			}
		}
	}
	
	/**
	 * Test para colocar fichas en los bordes. La prueba consiste en ir
	 * colocando fichas desde la casilla central hasta llegar a los bordes 
	 * ya que la disponibilidad de las casillas se va actualizando cada vez 
	 * que una casilla adyacente recibe un cambio. 
	 */
	@Test
	public void colocarBordes() {
		tab.aniadeFicha(expected, 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(tab.esDisponible(7, 7+i)) {				
					tab.aniadeFicha(new Ficha('a',1), 7, 7+i);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(tab.esDisponible(7+i, 7)) {				
					tab.aniadeFicha(new Ficha('a',1), 7+i, 7);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(tab.esDisponible(7, 7-i)) {				
					tab.aniadeFicha(new Ficha('a',1), 7, 7-i);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(tab.esDisponible(7-i, 7)) {				
					tab.aniadeFicha(new Ficha('a',1), 7-i, 7);
				}
				else {
					System.out.println("No disponible");				
				}				
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		assertFalse(tab.getCasilla(0, 7).empty());
		assertFalse(tab.getCasilla(7, 0).empty());
		assertFalse(tab.getCasilla(14, 7).empty());
		assertFalse(tab.getCasilla(7, 14).empty());
	}
	
	
	/**
	 * Test para comprobar que la disponibilidad de las casillas en el tablero
	 * se actualiza correctamente al colocar una ficha en una casilla
	 * adyacente
	 */
	@Test
	public void disponibles() {
		tab.aniadeFicha(expected, 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(tab.esDisponible(7, 7+i)) {				
					tab.aniadeFicha(expected, 7, 7+i);
				}
				else {
					System.out.println("No disponible");				
				}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		assertFalse(tab.getCasilla(7, 8).esDisponible());
		assertTrue(tab.getCasilla(8, 7).esDisponible());
		assertTrue(tab.getCasilla(7, 6).esDisponible());
		assertTrue(tab.getCasilla(6, 7).esDisponible());

	}

}
