
package modelo.Estrategias;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import Command.ComandoPasarTurno;
import Command.Command;
import javafx.util.Pair;
import modelo.Game;
import modelo.Maquina;
import modelo.Turno;
import modelo.diccionario.Diccionario;
import utils.Coordenadas;
import utils.OrdenarCoordenadas;
import utils.OrdenarCoordenadasInversa;
import utils.ScoredWord;


public class EasyStrategy  extends RunStrategy<Maquina>{

	public EasyStrategy(String tipo) {
		super(tipo);
	}
	
	@Override
	public List<Command> run(Game game, Turno t, List<Coordenadas> listaFichasNoFijas,
			Maquina maquina, List<Character> letras, Diccionario diccionario) {
		
		List<Command> comandos = new ArrayList<Command>();
		Map<Coordenadas, Character> coordenadas = new TreeMap<Coordenadas, Character>(new OrdenarCoordenadas());
		
		if (game.primeraPalabra()) {
			
			Map<Integer, List<String>> lista_palabras_posibles = new TreeMap<Integer, List<String>>();
			lista_palabras_posibles.putAll(diccionario.obtenerCombinaciones(letras, ' ', true));
			
			if (!lista_palabras_posibles.isEmpty()) {
				
				String palabra;
				
				if (lista_palabras_posibles.containsKey(2)) {
					palabra = lista_palabras_posibles.get(2).get(0);
				}
				else if (lista_palabras_posibles.containsKey(3)) {
					palabra = lista_palabras_posibles.get(3).get(0);
				}
				else {
					palabra = lista_palabras_posibles.get(4).get(0);
				}
	
			
				int fila = 7; int columna = 7;
				char[] cadena = palabra.toCharArray();
				
				for (int i = 0; i<cadena.length; i++) {
					coordenadas.put(new Coordenadas(fila, columna), cadena[i]);
					columna++;
				}
				
				comandos.addAll(generarComandos(coordenadas, maquina));
			}
		}
		
		else {
			
			List<ScoredWord> lista_palabras = game.getVerificadas();
			
			boolean esPosibleColocar = false;
			
			//Es mas facil encontrar un pivote donde colocar una palabra en la ulitma palabra colocada
			//durante gran parte del juego. Casi en todos los casos, esta palabra sera la que tenga mas
			//espacio al rededor para colocar.
			
			int indice_scoredWords = lista_palabras.size()-1;
			while (indice_scoredWords >= 0 && !esPosibleColocar) {
				
				//Obtenemos la lista de posibles pivotes para esta palabra (en este caso solo las vocales)
				List<Pair<Character, Coordenadas>> pivotes = this.buscarPivotes(true, lista_palabras.get(indice_scoredWords));
				
				int indice_pivotes = 0;
				while (indice_pivotes < pivotes.size() && !esPosibleColocar) {
					
					List<Character> lista_letras = new ArrayList<Character>();
					lista_letras.addAll(letras);
					lista_letras.add(pivotes.get(indice_pivotes).getKey());
					
					Map<Integer, List<String>> palabras_posibles = diccionario.obtenerCombinaciones(lista_letras, pivotes.get(indice_pivotes).getKey(), false);
					
					int indice_tamanio = 2;
					while (!esPosibleColocar && indice_tamanio < 7) {
						
							if (palabras_posibles.containsKey(indice_tamanio)) {
							List<String> lista_actual = palabras_posibles.get(indice_tamanio);
							
							int indice_palabras = 0;
							while (!esPosibleColocar && indice_palabras < lista_actual.size()) {
								
								String palabra = lista_actual.get(indice_palabras);
								int pos_pivote = 0;
								for (int i = 0; i<palabra.length(); i++) {
									if (palabra.charAt(i) == pivotes.get(indice_pivotes).getKey()) {
										pos_pivote = i;
									}
								}
								coordenadas = maquina.esPosible(palabra.toCharArray(), pivotes.get(indice_pivotes).getValue(), pos_pivote, palabra);
								
								if (!coordenadas.isEmpty()) {
									esPosibleColocar = true;
									
									Comparator<Coordenadas> comp= new OrdenarCoordenadas();
									
									Map<Coordenadas, Character> lado1 = new TreeMap<Coordenadas, Character>(new OrdenarCoordenadas());
									Map<Coordenadas, Character> lado2 = new TreeMap<Coordenadas, Character>(new OrdenarCoordenadasInversa());

									for (Coordenadas c : coordenadas.keySet()){
										if (comp.compare(c, pivotes.get(indice_pivotes).getValue()) == 1) {
											lado1.put(c, coordenadas.get(c));
										}
										else if (comp.compare(c, pivotes.get(indice_pivotes).getValue()) == -1){
											lado2.put(c, coordenadas.get(c));
										}
									}
										
									//Ahora se genera la lista de comandos. Se llama para ambos lados del pivote
									comandos.addAll(generarComandos(lado1, maquina));
									comandos.addAll(generarComandos(lado2, maquina));
								}
								
								indice_palabras++;
							}
						}
						
						indice_tamanio++;
					}
					
					lista_letras.remove(pivotes.get(indice_pivotes).getKey());
					
					indice_pivotes++;
				}
				
				indice_scoredWords--;
			}
		}
		comandos.add(new ComandoPasarTurno());
		return comandos;
	}
	
}
