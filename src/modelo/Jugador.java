package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import Command.Command;
import Command.CommandGenerator;
import Excepciones.CommandExecuteException;
import Excepciones.CommandParseException;
import utils.Coordenadas;

public class Jugador extends Integrante implements Originator {
	
	private boolean canAct;
	
	public Jugador(String nick){
		super();
		this.nick = nick;
	}
	
	public Jugador() { //Constructora sin parametros para cargar una partida
		mano = new ArrayList<Ficha>();
	}
	
	public String toString() {
		String str = "Turno de " + nick + ": " + puntos + " puntos." + "\n";
		
		for (Ficha f : mano) {
			str += f.toString() + " ";
		}
		
		return str;
	}
	
	public String Devuelve() {return nick;}
	
	@Override
	public List<Command> realizarJugada(Game game, AdminTurnos tManager, Turno t, Scanner in, List<Coordenadas> listaFichasNoFijas){
		boolean valido = false;
		List<Command> lista = new ArrayList<Command>();
		
		while (!valido) {
			System.out.println(tManager);
			System.out.println(game);
			//toLowerCase() pasa a minusculas
			//trim()  elimina espacios al principio y final
			//split() divide una cadena para introducirlo en un array
			
			//Esta parte del codigo es la que habia en el controller antes
			
			System.out.println("Introduzca el comando: ");
			String[] args = in.nextLine().trim().split ("\\s+");
			
			Command c = null;
			try {
				c = CommandGenerator.parseCommand(args);
				valido = true;
			} 
			catch(CommandParseException cpe) {
				System.err.println(cpe.getMessage());
				System.err.println("Escribe un comando valido, prueba help para ver los comandos disponibles");
			}

			lista.add(c);
		}
		
		return lista;
	}

	@Override
	public void juegaTurno() {
		// TODO Auto-generated method stub
		canAct = true;
	}

	@Override
	public void acabaTurno() {
		// TODO Auto-generated method stub
		canAct = false;
	}

	@Override
	public boolean puedeActuar() {
		// TODO Auto-generated method stub
		return canAct;
	}
	
	
}
