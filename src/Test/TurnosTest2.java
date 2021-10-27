package Test;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Command.ComandoColocarFicha;
import Command.ComandoPasarTurno;
import Excepciones.CommandExecuteException;
import controlador.Controller;
import modelo.Ficha;
import modelo.Integrante;
import modelo.Jugador;
import modelo.Scrabble;
import modelo.TManagerObserver;

public class TurnosTest2 {

	@Test
	void test() {
		List<Integrante> l = new ArrayList<Integrante>();
		List<Ficha> lf = new ArrayList<Ficha>();
		Ficha f1 = new Ficha('C', 0);
		Ficha f2 = new Ficha('A', 0);
		Ficha f3 = new Ficha('S', 0);
		Ficha f4 = new Ficha('A', 0);
		lf.add(f1); lf.add(f2); lf.add(f3); lf.add(f4);
		Jugador j1 = new Jugador("j1");
		robaFichas(j1,lf);
		Jugador j2 = new Jugador("j2");
		List<Ficha> lf2 = new ArrayList<Ficha>();
		Ficha f12 = new Ficha('C', 0);
		Ficha f22 = new Ficha('A', 0);
		Ficha f32 = new Ficha('S', 0);
		Ficha f42 = new Ficha('A', 0);
		lf2.add(f1); lf2.add(f2); lf2.add(f3); lf2.add(f4);
		robaFichas(j2,lf2);
		
		l.add(j1); l.add(j2);
		Scrabble sc = null;
		try {
			sc = new Scrabble(l);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		auxObserver auxO = new auxObserver();
		sc.addTManagerObserver(auxO);
		String actual = auxO.getAct();
		String siguiente = auxO.getSig();
		System.out.println("acutal " + actual);
		System.out.println("siguiente " + siguiente);
		try {
			sc.runCommand(new ComandoColocarFicha(f1.getId(), 8, 8, '0'));
			sc.runCommand(new ComandoColocarFicha(f2.getId(), 8, 9, '0'));
			sc.runCommand(new ComandoColocarFicha(f3.getId(), 8, 10, '0'));
			sc.runCommand(new ComandoColocarFicha(f4.getId(), 8, 11, '0'));
			sc.runCommand(new ComandoColocarFicha(f12.getId(), 8, 8, '0'));
			sc.runCommand(new ComandoColocarFicha(f22.getId(), 8, 9, '0'));
			sc.runCommand(new ComandoColocarFicha(f32.getId(), 8, 10, '0'));
			sc.runCommand(new ComandoColocarFicha(f42.getId(), 8, 11, '0'));
			sc.runCommand(new ComandoPasarTurno());
			System.out.println("acutal " + auxO.getAct());
			System.out.println("siguiente " + auxO.getSig());
			System.out.println(":::::::");
			assertEquals(auxO.getAct(), siguiente);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommandExecuteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void robaFichas(Jugador j, List<Ficha> f) {
		for(Ficha ficha : f) {
			j.robar(ficha);
		}
	}
	
	public class auxObserver implements TManagerObserver{

		String act;
		String sig;
		
		public auxObserver() {
			act = null;
			sig = null;
		}
		
		@Override
		public void registerOn(Controller c) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mostrarTurnos(String act, String sig) {
			// TODO Auto-generated method stub
			this.act = act;
			this.sig = sig;
			System.out.println("mostrarTurnos");

		}

		@Override
		public void nuevoTurno(Integrante i, String act, String sig) {
			// TODO Auto-generated method stub
			this.act = act;
			this.sig = sig;
			System.out.println("Nuevo Turno");

		}

		@Override
		public void turnoAcabado(String j) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onError(String err, String nick) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRegister(String act, String sig) {
			// TODO Auto-generated method stub
			this.act = act;
			this.sig = sig;
			System.out.println("On register");
		}

		@Override
		public void partidaAcabada(String nick) {
			// TODO Auto-generated method stub
			
		}
		
		public String getAct() {
			return act;
		}
		
		public String getSig() {
			return sig;
		}
		
	}
}
