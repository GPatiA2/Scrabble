package Test.CommandTests;

import static org.junit.Assert.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import Command.ComandoColocarFicha;
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
* Clase ComandoColocarFichaTest
*
* Contiene pruebas unitarias para la clase ComandoColocarFicha
* 
* Estas pruebas se centran en la interacción con el usuario, por
* tanto nos interesa validar el comportamiento del sistema sin 
* centrarnos tanto en la organización interna del mismo.
* 
* En estas pruebas se intenta verificar que se cumplen con las
* reglas del juego descritas en el documento "Reglas.docx"
* @see Documentos/Reglas.docx
*
* @author Grupo 5
*/
public class ComandoColocarFichaTest {
	
	//Atributos
	private static final int coordXCentro = 8, coordYCentro = 8;
	
	private Command c;
	private AdminTurnos adminTurnos;
	private Turno turno;
	private List<Integrante> lJugadores;
	private Ficha fichaPrueba; 
	private int coordX, coordY;
	
	@Before
	public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
		GeneradorDiccionario genDic = new GeneradorDiccionario();
		
		
		//Se crea una lista de jugadores  
		lJugadores = new ArrayList<Integrante>();
		int numJugadores = 3; //Numero de jugadores
		for(int i = 0; i < numJugadores;++i) {
			lJugadores.add(new Jugador());
		}
		
		//Se crea un adminTurnos con la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, genDic, lJugadores);
		turno = new Turno(adminTurnos.getJugando());
	}
	
	/**
	 * Para correr esta prueba cambiar la visibilidad del metodo execute() de la
	 * clase Command y descomentar la linea del test
	 * @see src/Command/Command.java
	 * 
	 * Test para probar regla del Scrabble "Poner la primera palabra"
	 * 
	 * Esta prueba consiste en intentar introducir en el primer turno una ficha en una casilla
	 * que no es la central. Esto no respeta las reglas del Scrabble. Por tanto, se espera que 
	 * el usuario no pueda colocar la ficha y que el programa lanze una excepción.
	 * 
	 * @throws CommandExecuteException si se introduce una ficha en una casilla que no sea el centro del tablero si es el primer turno
	 * @throws FileNotFoundException 
	 * @see Documentos/Reglas.docx
	 */
	@Test(expected = CommandExecuteException.class)
	public void testColocarFichaPrimerTurno() throws FileNotFoundException, CommandExecuteException {
		this.coordX = 4;
		this.coordY = 3;
		fichaPrueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(fichaPrueba.getId(), coordX, coordY, fichaPrueba.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		fichaPrueba = adminTurnos.getTablero().getFicha(coordX, coordY);
		assertNull("La ficha se ha colocado en una casilla que no es la central en el primer turno", fichaPrueba);
	}

	/**
	 * Test para probar regla del Scrabble "Poner la primera palabra"
	 * 
	 * Esta prueba consiste en intentar introducir en el segundo turno una ficha en una casilla
	 * que no es la central y que no tiene casillas adyacentes con fichas. Esto no respeta las 
	 * reglas del Scrabble. Por tanto, se espera que el usuario no pueda colocar la ficha y que 
	 * el programa lanze una excepción. 
	 * 
	 * Despues se colocará una ficha en una casilla adyacente a la central, la cual tendrá una 
	 * ficha (puesta en el primer turno). Esto respeta las normas del juego, por lo que la prueba
	 * comprobará que la ficha ha sido insertada con éxito.
	 * 
	 * @throws CommandExecuteException 
	 * @throws FileNotFoundException 
	 * @see Documentos/Reglas.docx
	 */
	@Test(expected = CommandExecuteException.class)
	public void testColocarFichaSiguienteTurno() throws FileNotFoundException, CommandExecuteException {
		Ficha f;
		Ficha ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		
		//Colocar ficha en el centro en el primer turno
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro, coordXCentro, ficha_prueba.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro-1, coordYCentro-1);
		assertNotNull("La ficha no se ha podido colocar en el centro el primer turno", f);
		
		//Generar un nuevo turno
		adminTurnos.sigTurno();
		
		//Colocar en el segundo turno ficha en una casilla con casillas adyacentes sin fichas
		this.coordX = 4;
		this.coordY = 3;
		assertTrue(adminTurnos.getTablero().emptyCasilla(coordX, coordY) && !adminTurnos.getTablero().esDisponible(coordX, coordY));
		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordX, coordY, ficha_prueba.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordX-1, coordY-1);
		assertNull("Se ha podido colocar una ficha en una casilla con casillas adyacentes sin fichas", f);
		
		//Colocar en el segundo turno una ficha en una casilla con casillas adyacentes con ficha
		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro+1, coordYCentro, ficha_prueba.getLetra());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro+1-1, coordYCentro-1);
		assertNotNull("No se ha podido colocar una ficha en el segundo turno", f);
	}
	
	/**
	 * Test para probar regla del Scrabble "Poner la primera palabra"
	 * 
	 * Esta prueba consiste en colocar una ficha en el centro y otras cuatro fichas en las casillas
	 * adyacentes a la central (en vertical y horizontal).
	 * 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException si se introduce una ficha en una casilla que no sea el centro del tablero si es el primer turno,
	 * 		o si se coloca una ficha en una casilla que no tiene casillas adyacentes con fichas
	 */
	@Test
	public void testColocarFichasAdyacentes() throws FileNotFoundException, CommandExecuteException {
		Ficha f;
		Ficha ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro, coordYCentro, ficha_prueba.getLetra()); //Colocar ficha en el centro
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro-1, coordYCentro-1); //Resta uno porque el grid del tablero empieza a contar desde 0, mientras que el usuario empieza a contar desde 1
		assertNotNull("No se ha colocado la primera ficha en el centro", f);

		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro+1, coordYCentro, ficha_prueba.getLetra()); //Colocar ficha a la derecha
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro+1-1, coordYCentro-1);
		assertNotNull("No se ha podido colocar la segunda ficha a la derecha", f);

		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro-1, coordYCentro, ficha_prueba.getLetra()); //Colocar ficha a la izquierda
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
	    f = adminTurnos.getTablero().getFicha(coordXCentro-1-1, coordYCentro-1);
		assertNotNull("No se ha podido colocar la tercera ficha a la izquierda", f);

		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro, coordYCentro+1, ficha_prueba.getLetra()); //Colocar ficha encima
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro-1, coordYCentro+1-1);
		assertNotNull("No se ha podido colocar la cuarta ficha encima", f);

		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro, coordYCentro-1, ficha_prueba.getLetra()); //Colocar ficha debajo
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
	    f = adminTurnos.getTablero().getFicha(coordXCentro-1, coordYCentro-1-1);
		assertNotNull("No se ha podido colocar la quinta ficha debajo", f);
	
	}
	
	/**
	 * Test para probar regla del Scrabble "Poner la primera palabra"
	 * 
	 * Esta prueba consiste en colocar una ficha en el centro e intentar colocar otra ficha en 
	 * una casilla diagonal adyacente a la central. Esto va en contra de las reglas del Scrabble 
	 * y por tanto se espera que el programa lanze una excepción.
	 * 
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException si se introduce una ficha en una casilla que no sea el centro del tablero si es el primer turno,
	 * 		o si se coloca una ficha en una casilla que no tiene casillas adyacentes con fichas
	 */
	@Test(expected = CommandExecuteException.class)
	public void testColocarFichasAdyacentesDiagonal() throws FileNotFoundException, CommandExecuteException {
		Ficha f;
		Ficha ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
	
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro, coordYCentro, ficha_prueba.getLetra()); //Colocar ficha en el centro
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro-1, coordYCentro-1); 
		assertNotNull("No se ha colocado la primera ficha en el centro", f);

		ficha_prueba = adminTurnos.getJugando().get_ficha_posicion(0);
		c = new ComandoColocarFicha(ficha_prueba.getId(), coordXCentro+1, coordYCentro+1, ficha_prueba.getLetra()); //Colocar ficha en la diagonal
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), lJugadores.get(adminTurnos.getActTurn()), turno);
		f = adminTurnos.getTablero().getFicha(coordXCentro+1-1, coordYCentro-1);
		assertNull("Se ha podido colocar una ficha en diagonal", f);
	}
	

}
