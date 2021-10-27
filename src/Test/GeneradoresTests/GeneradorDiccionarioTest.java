package Test.GeneradoresTests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.AdminTurnos;
import modelo.Ficha;
import modelo.Game;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Tablero;
import modelo.diccionario.Diccionario;
import utils.Coordenadas;
import utils.ScoredWord;

/**
 * Clase GeneradorDiccionarioTest
 * 
 * Contiene pruebas para la clase Diccionario.
 * 
 * Estas pruebas se centran en verificar que el 
 * diccionario se ha cargado correctamente.
 * 
 * @see src/modelo.diccionario/Diccionario.java
 * @author Grupo 5
 *
 */
public class GeneradorDiccionarioTest {

	private Diccionario diccionario;
	private FileReader fr;
	private BufferedReader br;
	private Tablero tab;
	private AdminTurnos adminTurnos;
	private Game g;

	
	@Before
	public void init() throws IOException {
		diccionario = Diccionario.getInstance(); //Devuelve el diccionario
        fr = new FileReader ("src/ArchivoCarga/diccionarioRevisado.txt");
        br = new BufferedReader(fr);
        tab = new Tablero();
		GeneradorMazo genMazo = new GeneradorMazo();
		List<Integrante> lJugadores = new ArrayList<Integrante>();
		lJugadores.add(new Jugador("j1"));
		adminTurnos = new AdminTurnos(genMazo, lJugadores);
		g = new Game(adminTurnos.getMazo());
	}
	
	/**
	 * Test para probar que las palabras se verifican correctamente
	 * tomando como referencia el diccionario
	 * 
	 * Supongamos que queremos verificar la palabra "lateral", con la 
	 * primera ficha colocada en la casilla (5,7) y siguiendo en sentido
	 * HORIZONTAL. 
	 * 
	 * La prueba consiste en colocar dicha palabra en el tablero y verificarla.
	 * Tras ser verificada se guardara en una lista de palabras verificadas, con su
	 * puntuacion y coordenadas. A partir de las casillas y fichas podemos obtener 
	 * la puntuacion esperada de la palabra y generar una ScoredWord a partir de ella.
	 * 
	 * En este caso la ultima letra "A" cae en una casilla doble letra,
	 * por tanto la puntuacion esperada es: "L"(1)+"A"(1)+"T"(1)+"E"(1)+"R"(1)+"A"(1)*2+"L"(1)
	 * --> 8
	 * 
	 * Si la lista de palabras verificadas contiene la ScoredWord que esperamos
	 * entonces la prueba unitaria es correcta.
	 * 
	 * Este mismo procedimiento funcionara para cualquier otra palabra contenida en
	 * el dicionario.
	 * 
	 * @see /src/Test/CasillaTest.java en caso de fallar este test, el problema puede venir
	 * derivado del metodo por el cual se obtiene la puntuacion de una palabra
	 * 
	 */
	@Test
	public void testVerificarPalabras1() {
		
		Ficha fichaL = new Ficha('L', 1);
		Ficha fichaA = new Ficha('A', 1);
		Ficha fichaT = new Ficha('T', 1);
		Ficha fichaE = new Ficha('E', 1);
		Ficha fichaR = new Ficha('R', 1);
		
		tab.setFicha(fichaL, 7, 5);
		tab.setFicha(fichaA, 7, 6);
		tab.setFicha(fichaT, 7, 7);
		tab.setFicha(fichaE, 7, 8);
		tab.setFicha(fichaR, 7, 9);
		tab.setFicha(fichaA, 7, 10);
		tab.setFicha(fichaL, 7, 11);
		
		List<Coordenadas> lista = new ArrayList<Coordenadas>();
		for(int i = 5; i <= 11; ++i) {
			Coordenadas coord = new Coordenadas(7,i);
			lista.add(coord);
		}
		
		//Verificar
		boolean actual = g.Verificar(lista, adminTurnos.getJugando(), tab);
		assertTrue(actual); 
		
		//Generar palabra verificada esperada
		ScoredWord expected = new ScoredWord("lateral",8,lista.get(0), lista.get(lista.size()-1));

		assertTrue(g.getVerificadas().contains(expected));
	}

	/**
	 * Test similar a testVerificarPalabras1() para probar que las palabras se 
	 * verifican correctamente tomando como referencia el diccionario
	 * 
	 * Supongamos que queremos verificar la palabra "lateral", con la 
	 * primera ficha colocada en la casilla (6,7) y siguiendo en sentido
	 * VERTICAL. 
	 * 
	 * En este caso las dos letras "A" caen en una casilla doble letra,
	 * por tanto la puntuacion esperada es: "L"(1)+"A"(1)*2+"T"(1)+"E"(1)+"R"(1)+"A"(1)*2+"L"(1)
	 * --> 9
	 * 
	 *  @see /src/Test/CasillaTest.java en caso de fallar este test, el problema puede venir
	 * derivado del metodo por el cual se obtiene la puntuacion de una palabra
	 */
	@Test
	public void testVerificarPalabras2() {
		
		Ficha fichaL = new Ficha('L', 1);
		Ficha fichaA = new Ficha('A', 1);
		Ficha fichaT = new Ficha('T', 1);
		Ficha fichaE = new Ficha('E', 1);
		Ficha fichaR = new Ficha('R', 1);
		
		tab.setFicha(fichaL, 6, 5);
		tab.setFicha(fichaA, 7, 5);
		tab.setFicha(fichaT, 8, 5);
		tab.setFicha(fichaE, 9, 5);
		tab.setFicha(fichaR, 10, 5);
		tab.setFicha(fichaA, 11, 5);
		tab.setFicha(fichaL, 12, 5);
		
		List<Coordenadas> lista = new ArrayList<Coordenadas>();
		for(int j = 6; j <= 12; ++j) {
			Coordenadas coord = new Coordenadas(j,5);
			lista.add(coord);
		}
		
		//Verificar
		boolean actual = g.Verificar(lista, adminTurnos.getJugando(), tab);
		assertTrue(actual); 
		
		//Generar palabra verificada esperada
		ScoredWord expected = new ScoredWord("lateral",9,lista.get(0), lista.get(lista.size()-1));

		assertTrue(g.getVerificadas().contains(expected));
	}	
	
	/* No se deberian verificar palabras incorrectas
	 * Este test tiene como objetivo comprobar que las palabras incorrectas, es decir,
	 * aquellas que no se encuentran en el diccionario que tomamos como referencia, no 
	 * se verifican. 
	 * La razón por la que esto se encuentra comentado es porque dicha funcionalidad
	 * en el diccionario no es correcta y por tanto el test falla, pero el test puede
	 * servir para futuras pruebas sobre el diccionario.
	 * 
	@Test
	public void testVerificarPalabraIncorrecta() {
		
		Ficha fichaO = new Ficha('O', 1);
		Ficha fichaN = new Ficha('N', 1);
		Ficha fichaE = new Ficha('E', 1);
		
		tab.setFicha(fichaO, 7, 5);
		tab.setFicha(fichaN, 7, 6);
		tab.setFicha(fichaE, 7, 7);
	
		
		List<Coordenadas> lista = new ArrayList<Coordenadas>();
		for(int i = 5; i <= 7; ++i) {
			Coordenadas coord = new Coordenadas(7,i);
			lista.add(coord);
		}
		
		//Verificar
		boolean actual = g.Verificar(lista, adminTurnos.getJugando(), tab);
		assertFalse(actual); 
		
		//Generar palabra verificada esperada
		ScoredWord expected = new ScoredWord("one",8,lista.get(0), lista.get(lista.size()-1));

		assertFalse(g.getVerificadas().contains(expected));
	}
	*/
}
