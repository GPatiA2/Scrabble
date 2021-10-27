package Test;

import static org.junit.Assert.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import modelo.AdminTurnos;
import modelo.Casilla;
import modelo.Ficha;
import modelo.Game;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Tablero;
import utils.Coordenadas;

/**
 * Clase CasillaTest
 * 
 * Contiene pruebas para verificar la funcionalidad
 * basica de las casillas para poner y quitar fichas.
 * En esta clase tambien se prueba el metodo encargado de
 * obtener la puntuacion de una palabra formada sobre 
 * el tablero.
 * 
 * @see /src/Modelo/Game.java contiene metodo para obtener la 
 * puntuacion de las palabras
 * @see /Documentos/Reglas.docx para ver como se forma la puntuacion
 * de una palabra
 * @see /src/Modelo/Casilla.java 
 * 
 * @author Grupo 5
 *
 */
public class CasillaTest {
	
	private Tablero tab;
	private AdminTurnos adminTurnos;
	private Game g;

	@Before
	public void init() throws IOException{
		tab = new Tablero();
		GeneradorMazo genMazo = new GeneradorMazo();
		List<Integrante> lJugadores = new ArrayList<Integrante>();
		lJugadores.add(new Jugador("j1"));
		adminTurnos = new AdminTurnos(genMazo, lJugadores);
		g = new Game(adminTurnos.getMazo());
	}
	
	/**
	 * Test para probar que los atributos de las casillas
	 * se actualizan correctamente tras colocar y quitar una 
	 * ficha en dichas casillas.
	 */
	@Test
	public void testColocaFicha() {
		Ficha ficha_prueba = new Ficha('A',1);
		
		//Casilla con ficha
		Casilla c = new Casilla(ficha_prueba, Casilla.CASILLA_NORMAL);
		assertFalse(c.empty());
		assertFalse(c.esDisponible());
		
		//Casilla sin ficha
		Casilla c2 = new Casilla(Casilla.CASILLA_DOBLE_LETRA);
		assertTrue(c2.empty());
		c2.add(ficha_prueba);
		assertFalse(c2.empty());
		assertFalse(c2.esDisponible());
		
	}
	
	/**
	 * Test para probar que los multiplicadores de las casillas
	 * son correctos para obtener los puntos de una palabra
	 * colocada en horizontal
	 * 
	 * Supongamos que formamos la palabra "C A S A" empezando en la
	 * casilla(6,8) del tablero, con la ficha C en una casilla doble letra,
	 * la A en una casilla normal, la S en una casilla doble letra y la A en una
	 * casilla normal.
	 * 
	 * La puntuacion esperada es: [ C(3ptos) * 2 + A(1ptos) * 1 + S(1ptos) * 2
	 * + A(1ptos) * 1 ] = 10 ptos
	 * 
	 * @see /Documentos/Reglas.docx
	 */
	@Test
	public void test1PuntuacionPalabra() {
		int expected = 10; //Puntuacion esperada
				
		Ficha fichaC = new Ficha('C', 3);
		Ficha fichaA = new Ficha('A', 1);
		Ficha fichaS = new Ficha('S', 1);
		
		tab.setFicha(fichaC, 8, 6);
		tab.setFicha(fichaA, 8, 7);
		tab.setFicha(fichaS, 8, 8);
		tab.setFicha(fichaA, 8, 9);
		
		Coordenadas coordIni = new Coordenadas(8,6);
		Coordenadas coordFin = new Coordenadas(8,9);
		int actual = g.valorPalabra(coordIni, coordFin, true, tab); //Puntuacion generada
		
		assertEquals(expected,actual);
	}
	
	/**
	 * Test para probar que los multiplicadores de las casillas
	 * son correctos para obtener los puntos de una palabra
	 * colocada en horizontal
	 * 
	 * Supongamos que formamos la palabra "C A S A" empezando en la
	 * casilla(4,10) del tablero, con la ficha C en una casilla doble palabra,
	 * la A en una casilla normal, la S en una casilla normal y la A en una
	 * casilla normal.
	 * 
	 * La puntuacion esperada es: [ C(3ptos) + A(1ptos) * 1 + S(1ptos) * 1
	 * + A(1ptos) * 1 ] * 2 = 12ptos
	 * 
	 * @see /Documentos/Reglas.docx
	 */
	@Test
	public void test2PuntuacionPalabra() {
		int expected = 12; //Puntuacion esperada
				
		Ficha fichaC = new Ficha('C', 3);
		Ficha fichaA = new Ficha('A', 1);
		Ficha fichaS = new Ficha('S', 1);
		
		tab.setFicha(fichaC, 10, 4);
		tab.setFicha(fichaA, 10, 5);
		tab.setFicha(fichaS, 10, 6);
		tab.setFicha(fichaA, 10, 7);
		
		Coordenadas coordIni = new Coordenadas(10,4);
		Coordenadas coordFin = new Coordenadas(10,7);
		int actual = g.valorPalabra(coordIni, coordFin, true, tab); //Puntuacion generada
		
		assertEquals(expected,actual);
	}
	
	/**
	 * Test para probar que los multiplicadores de las casillas
	 * son correctos para obtener los puntos de una palabra
	 * colocada en vertical
	 * 
	 * Supongamos que formamos la palabra "C A S A" empezando en la
	 * casilla(0,0) del tablero, con la ficha C en una casilla triple palabra,
	 * la A en una casilla normal, la S en una casilla normal y la A en una
	 * casilla doble letra.
	 * 
	 * La puntuacion esperada es: [ C(3ptos) + A(1ptos) * 1 + S(1ptos) * 1
	 * + A(1ptos) * 2 ] * 3 = 21ptos
	 * 
	 * @see /Documentos/Reglas.docx
	 */
	@Test
	public void test3PuntuacionPalabra() {
		int expected = 21; //Puntuacion esperada
				
		Ficha fichaC = new Ficha('C', 3);
		Ficha fichaA = new Ficha('A', 1);
		Ficha fichaS = new Ficha('S', 1);
		
		tab.setFicha(fichaC, 0, 0);
		tab.setFicha(fichaA, 1, 0);
		tab.setFicha(fichaS, 2, 0);
		tab.setFicha(fichaA, 3, 0);
		
		Coordenadas coordIni = new Coordenadas(0,0);
		Coordenadas coordFin = new Coordenadas(3,0);
		int actual = g.valorPalabra(coordIni, coordFin, false, tab); //Puntuacion generada
		
		assertEquals(expected,actual);
	}

}
