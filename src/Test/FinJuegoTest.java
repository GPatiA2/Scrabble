package Test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import Command.ComandoPasarTurno;
import Command.Command;
import Excepciones.CommandExecuteException;
import modelo.AdminTurnos;
import modelo.Ficha;
import modelo.GeneradorDiccionario;
import modelo.GeneradorMazo;
import modelo.Integrante;
import modelo.Jugador;

public class FinJuegoTest {
	
	//Atributos
	private AdminTurnos adminTurnos;
	private List<Integrante> lJugadores;
	
	@Before
	public void init() throws IOException {
		GeneradorMazo genMazo = new GeneradorMazo();
		GeneradorDiccionario genDic = new GeneradorDiccionario();
		
		//Se crea una lista de jugadores  
		lJugadores = new ArrayList<Integrante>();
		int numJugadores = 3; //Numero de jugadores
		for(int i = 0; i < numJugadores; ++i) {
			lJugadores.add(new Jugador());
		}
		
		//Se crea un adminTurnos con la lista de jugadores
		adminTurnos = new AdminTurnos(genMazo, genDic, lJugadores);
	}

	/**
	 * Test para probar la regla del Scrabble por la que el juego finaliza
	 * si todos los jugadores han pasado el turno dos veces consecutivas
	 * @see /Documentos/Reglas.docx
	 * 
	 * @throws CommandExecuteException 
	 * @throws FileNotFoundException 
	 * 
	 */
	@Test
	public void testFinPasarTurnos() throws CommandExecuteException, FileNotFoundException {
		Command c = new ComandoPasarTurno();
		adminTurnos.getTablero().setFicha(adminTurnos.getJugando().get_ficha_posicion(0), 7, 7);
		for(int i = 0; i < lJugadores.size() * 2; ++i) {
			c.execute(adminTurnos.getTablero(), adminTurnos.getMazo(), adminTurnos.getJugando());
			adminTurnos.sigTurno();
		}
		
	
	}

}
