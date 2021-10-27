package Test.CommandTests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import Excepciones.CommandExecuteException;
import modelo.Ficha;
import modelo.Tablero;

class TableroTest {
	
	@Test
	void colocarCentro() {
		Tablero t = new Tablero();
		Ficha f = new Ficha('a',1);
		t.aniadeFicha(f, 7, 7);
		assertEquals(t.getCasilla(7, 7).getFicha(), f);
	}
	
	@Test
	void colocarBordes() {
		Tablero t = new Tablero();
		t.aniadeFicha(new Ficha('a',1), 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(t.esDisponible(7, 7+i)) {				
					t.aniadeFicha(new Ficha('a',1), 7, 7+i);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(t.esDisponible(7+i, 7)) {				
					t.aniadeFicha(new Ficha('a',1), 7+i, 7);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(t.esDisponible(7, 7-i)) {				
					t.aniadeFicha(new Ficha('a',1), 7, 7-i);
				}
				else {
					System.out.println("No disponible");				
				}				
				if(t.esDisponible(7-i, 7)) {				
					t.aniadeFicha(new Ficha('a',1), 7-i, 7);
				}
				else {
					System.out.println("No disponible");				
				}				
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		assertFalse(t.getCasilla(0, 7).empty());
		assertFalse(t.getCasilla(7, 0).empty());
		assertFalse(t.getCasilla(14, 7).empty());
		assertFalse(t.getCasilla(7, 14).empty());
	}
	
	@Test
	void quitarFicha() {
		Tablero t = new Tablero();
		t.aniadeFicha(new Ficha('a',1), 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(t.esDisponible(7, 7+i)) {				
					t.aniadeFicha(new Ficha('a',1), 7, 7+i);
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
			t.quitarFicha(7, 7);
		} catch (CommandExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(t.getCasilla(7, 7).empty());
	}
	
	@Test
	void disponibles() {
		Tablero t = new Tablero();
		t.aniadeFicha(new Ficha('a',1), 7, 7);

		for(int i = 1; i < 8; i++) {
			try {
				if(t.esDisponible(7, 7+i)) {				
					t.aniadeFicha(new Ficha('a',1), 7, 7+i);
				}
				else {
					System.out.println("No disponible");				
				}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		assertFalse(t.getCasilla(7, 8).esDisponible());
		assertTrue(t.getCasilla(8, 7).esDisponible());
		assertTrue(t.getCasilla(7, 6).esDisponible());
		assertTrue(t.getCasilla(6, 7).esDisponible());

	}

}
