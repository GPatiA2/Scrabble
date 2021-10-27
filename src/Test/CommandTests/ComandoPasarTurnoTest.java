package Test.CommandTests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import Command.ComandoPasarTurno;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Turno;


/**
 * Clase ComandoPasarTurnoTest
 *
 * Contiene pruebas unitarias para la clase ComandoPasarTurno
 * y para la clase AdminTurnos
 * 
 * Estas pruebas se centran en verificar que se puede pasar de turno
 * correctamente a través de comandos y algunas otras funcionalidades
 * del Administrador de turnos.
 * 
 * @author Grupo 5
 *
 */
public class ComandoPasarTurnoTest {
	
	//Atributos
	
	static final int numJugadores = 3;
	private static final String[] nicks = {"a","b","c"};
	private static final int MAXMANO = 7;
	
	private static List<Integrante> lJugadores;
	private static AdminTurnos adminTurnos;
	
	private Turno turno;
	private Command c;

	@BeforeClass
	static public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
		GeneradorDiccionario genDic = new GeneradorDiccionario();
		
		lJugadores = new ArrayList<>();
		for(int i = 0; i < numJugadores; ++i) {
			Integrante j = new Jugador(nicks[i].toString());
			lJugadores.add(j);
		}
		
		//Se crea un adminTurnos con el game y la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, genDic, lJugadores);
	}
	
	/**
	 * Test para probar la regla del Scrabble "Pasar(saltarse un turno)"
	 * 
	 * Esta prueba consiste en pasar el turno y comprobar que el turno anterior
	 * y el turno actual no son iguales
	 * 
	 * @see Documentos/Reglas.docx
	 * @throws FileNotFoundException
	 * @throws CommandExecuteException
	 */
	@Test
	public void testPasarTurno() throws FileNotFoundException, CommandExecuteException {
		int expected = adminTurnos.getActTurn();
		c = new ComandoPasarTurno();
		turno = new Turno(adminTurnos.getJugando());
		c.perform(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando(), turno);
		adminTurnos.sigTurno();
		int actual = adminTurnos.getActTurn();
		assertNotEquals(actual, expected);
	}
	
	
	/**
	 * Test para rellenar las manos automaticamente
	 * 
	 * Esta prueba consiste en vaciar parcialmente la mano de un integrante y comprobar que en el
	 * siguiente turno el integrante tiene su mano llena (el numero maximo de fichas en la mano
	 * es 7)
	 * 
	 * @throws CommandExecuteException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testRellenarMano() throws FileNotFoundException, CommandExecuteException {
		int turnoIntegranteTest; //Expected
		Integrante integrante;
		
		//Mano completamente vaciada
		turnoIntegranteTest = adminTurnos.getActTurn();
		integrante = lJugadores.get(adminTurnos.getActTurn());
		while(integrante.get_mano().size() > 4) {integrante.erase_ficha_posicion(0);}
		assertTrue(integrante.get_mano().size() < MAXMANO);
		adminTurnos.sigTurno();
		integrante = lJugadores.get(adminTurnos.getActTurn());
		integrante.setInvertirSentido(true);
		adminTurnos.sigTurno();
		assertEquals(turnoIntegranteTest, adminTurnos.getActTurn());
		assertTrue(integrante.get_mano().size() == MAXMANO);
		
	}
}
