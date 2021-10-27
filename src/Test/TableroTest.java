package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import Command.ComandoColocarFicha;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.Ficha;
import modelo.Game;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Tablero;

/**
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
			{new Ficha('A', 2), 7, 7}
		});
	} 
	
	//Atributos
	
	private static Tablero tab;
	private Ficha expected;
	private int coordX, coordY;
	
	public TableroTest(Ficha expected, int coordX, int coordY){
		this.expected = expected;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	@BeforeClass
	static public void init() {
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
	 * Test para quitar una ficha del tablero
	 * @throws CommandExecuteException si se intenta quitar una ficha de una casilla sin ficha
	 */
	@Test
	public void testQuitarFicha() throws CommandExecuteException {
		tab.setFicha(expected,coordX,coordY);
		tab.quitarFicha(coordX, coordY);
		Ficha actual = tab.getFicha(coordX, coordY);
		assertNull(actual);
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
				if(i != 7 && j != 7) {
					assertFalse(tab.getCasilla(i, j).esDisponible());
				}
			}
		}
	}
	
	

}
