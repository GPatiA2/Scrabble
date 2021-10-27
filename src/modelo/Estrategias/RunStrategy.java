
package modelo.Estrategias;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Command.ComandoColocarFicha;
import Command.Command;
import javafx.util.Pair;
import modelo.Game;
import modelo.Integrante;
import modelo.Maquina;
import modelo.Turno;
import modelo.diccionario.Diccionario;
import utils.Coordenadas;
import utils.ScoredWord;

public abstract class RunStrategy<T extends Integrante>{

	private String tipo;
	
	public RunStrategy(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Este metodo obtiene una lista de comandos a ejecutar para el turno de un jugador
	 * controlado por la maquina. Esta lista o contiene unicamente el comando pasar turno
	 * o contiene una lista de comandos colocar ficha seguidos de pasar turno
	 * @see Diccionario
	 * @see Maquina
	 * @return lista de comandos
	 */
	public abstract List<Command> run(Game game, Turno t, List<Coordenadas> listaFichasNoFijas,
			Maquina maquina, List<Character> letras, Diccionario diccionario);
	
	
	protected List<Command> generarComandos(Map<Coordenadas, Character> lado, Integrante maquina){
		
		List<Command> comandos = new ArrayList<Command>();
		
		Command comando;
		
		for (Coordenadas c : lado.keySet()) {
			String string ="";
			string += lado.get(c);
			comando = new ComandoColocarFicha(string, c.getFila() + 1, c.getColumna() + 1,'0');
			comandos.add(comando);
		}
		
		return comandos;
	}
	
	/*	Esta funcion devuelve una lista de posibles pivotes para la palabra verificada dada
	 *  teniendo en cuenta la condicion de si el pivote debe ser vocal o no
	 */
	protected List<Pair<Character, Coordenadas>> buscarPivotes(boolean vocal, ScoredWord palabra) {
		
		List<Pair<Character, Coordenadas>> lista = new ArrayList<>();
		
		int numLetras = palabra.getPalabra().length();
		
		boolean horizontal = false;
		
		if (palabra.getCoorIni().getFila() == palabra.getCoorFin().getFila()) {
			horizontal = true;
		}
		
		int fila = palabra.getCoorIni().getFila();
		int columna = palabra.getCoorIni().getColumna();
		
		for (int i = 0; i<numLetras; i++) {
			
			if (vocal) {
				if (esVocal(palabra.getPalabra().charAt(i))) {
					lista.add(new Pair<Character, Coordenadas>(palabra.getPalabra().charAt(i), new Coordenadas(fila, columna)));
				}
			}
			else {
				lista.add(new Pair<Character, Coordenadas>(palabra.getPalabra().charAt(i), new Coordenadas(fila, columna)));
			}
			
			if (horizontal) {columna++;}
			else {fila++;}
		}
		
		
		return lista;
	}
	
	protected boolean esVocal(char letra){
		if ((letra == 'A') || (letra == 'E') || (letra == 'I') || (letra == 'O') || (letra == 'U')) {return true;}
		return false;
	}
}
