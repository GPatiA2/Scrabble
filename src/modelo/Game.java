package modelo;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import modelo.diccionario.Diccionario;
import utils.Coordenadas;
import utils.ScoredWord;
/**
 * Clase que se encarga de manejar las reglas del juego 
 * Se encarga de verificar que las palabras que los jugadores colocan en el tablero
 * son validas en el juego
 * @author Grupo 5
 *
 */
public class Game implements Originator{
	
	/**
	 * Conjunto de fichas que se pueden robar por los jugadores
	 * @see Mazo
	 * @see #iniMazo()
	 */
	private Mazo mazo;
	/**
	 * Conjunto de palabras validas dentro del juego
	 * @see Game#iniDiccionario()
	 */
	private Diccionario diccionario;
	
	/**
	 * Lista de palabras que han sido colocadas en el tablero por los jugadores
	 * @see ScoreWord
	 * @see #Verificar(List)
	 */
	private List<ScoredWord> listaPalabrasVerificadas;
	
	/**
	 * Constructor sin parametros
	 * Inicializa el mazo y el diccionario desde un fichero predeterminado
	 * @see #iniMazo()
	 * @see #iniDiccionario()
	 * @throws IOException
	 */
	public Game(Mazo m) throws IOException {
		mazo = m;
		diccionario = Diccionario.getInstance();
		listaPalabrasVerificadas = new ArrayList<ScoredWord>();
	}
	
		
	/**
	 * Recibe una lista de coordenadas en las que el jugador ha colocado una ficha
	 * y comprueba que se han formado palabras correctamente
	 * @param lista lista de coordenadas donde el jugador ha colocado fichas
	 * @param j jugador
	 * @param tablero tablero del juego
	 * @return b 	true si se han formado palabras correctamente
	 * @see ComandoPasarTurno
	 * @see ScoreWord
	 */
	public boolean Verificar(List<Coordenadas> lista, Integrante j, Tablero tablero) {
		
		String palabra;
		int puntos;
		ScoredWord verificada;
		Coordenadas coor_ini,coor_fin;
		HashSet<ScoredWord> listScoredWord = new HashSet<ScoredWord>();
		/*
		 * Recorro la lista evaluando para cada ficha colocada segun sus coordenadas
		 * vertical y horizontalmente si encuentro alguna palabra correcta 
		 */
		
		for(Coordenadas coor : lista) {
			//Columna - Vertical
			
			int fila_ini = coor.getFila(); 
			while(tablero.coordenadasValidas(fila_ini,coor.getColumna())&&(!tablero.emptyCasilla(fila_ini,coor.getColumna()))) {
				
				//inicializo palabra  y puntos a 0
				palabra = "";
				palabra += tablero.getFicha(fila_ini,coor.getColumna()).getLetra();
				puntos = 0;
				
				
				int fila_fin = fila_ini+1; //Porque no valen palabras de una sola letra
				
				//Avanzo hasta la letra que he colocado incluida
				while(fila_fin<=coor.getFila()) {
					palabra += tablero.getFicha(fila_fin,coor.getColumna()).getLetra();
					fila_fin++;
				}
				
				while(tablero.coordenadasValidas(fila_fin,coor.getColumna())
						&&(!tablero.emptyCasilla(fila_fin,coor.getColumna()))) {
					palabra += tablero.getFicha(fila_fin,coor.getColumna()).getLetra();
					
					if(diccionario.contains(palabra)) {
						//Construyo el ScoredWord
						coor_ini = new Coordenadas(fila_ini,coor.getColumna()); 
						coor_fin = new Coordenadas(fila_fin,coor.getColumna()); 
						puntos = this.valorPalabra(coor_ini,coor_fin,false, tablero );
						
						verificada = new ScoredWord(palabra,puntos,coor_ini,coor_fin);
						
						//Si hay alguna palabra ya validada que contiene 
						//a la nueva palabra o viceversa me quedo con la
						//mas larga (para plurales o al-ale)
						if(listScoredWord.isEmpty()) {
							listScoredWord.add(verificada);
						}
						else{
							HashSet<ScoredWord> borrar = new HashSet<ScoredWord>();
							HashSet<ScoredWord> anadir = new HashSet<ScoredWord>();
							for(ScoredWord s: listScoredWord) {			
								
								if(s.thisContenidaEnOther(verificada)) {
									borrar.add(s);
									anadir.add(verificada);
								}
								else if(verificada.thisContenidaEnOther(s)) {
									//No aniado la palabra porque hay otra mas grande
								}
								else {
									anadir.add(verificada);
								}
							}
							for (ScoredWord s: borrar) {
								listScoredWord.remove(s);
							}
							for (ScoredWord s: anadir) {
								listScoredWord.add(s);
							}
						}
						
					}
					fila_fin++;
				}
				fila_ini--;
			}
			
			//Filas   - horizontal
			
			
			/*
			 * La palabra a verificar se encuentra en la fila coor.getFila()
			 * y entre las columnas col_ini y col_fin (ambas incluidas)
			 */
			
			int col_ini = coor.getColumna(); 
			while(tablero.coordenadasValidas(coor.getFila(), col_ini)&&(!tablero.emptyCasilla(coor.getFila(), col_ini))) {
				
				//inicializo palabra  y puntos a 0
				palabra = "";
				palabra += tablero.getFicha(coor.getFila(),col_ini).getLetra();
				puntos = 0;
				
				
				int col_fin = col_ini+1; //Porque no valen palabras de una sola letra
				
				//Avanzo hasta la letra que he colocado incluida
				while(col_fin<=coor.getColumna()) {
					palabra += tablero.getFicha(coor.getFila(),col_fin).getLetra();
					col_fin++;
				}
				
				while(tablero.coordenadasValidas(coor.getFila(), col_fin)
						&&(!tablero.emptyCasilla(coor.getFila(), col_fin))) {
					palabra += tablero.getFicha(coor.getFila(),col_fin).getLetra();
					
					if(this.diccionario.contains(palabra)) {
						//Construyo el ScoredWord
						coor_ini = new Coordenadas(coor.getFila(),col_ini); 
						coor_fin = new Coordenadas(coor.getFila(),col_fin); 
						
						//TODO: valorPalabra con sentido vertical (en vez de true->false)
						puntos = this.valorPalabra(coor_ini,coor_fin,true, tablero );
						
						verificada = new ScoredWord(palabra,puntos,coor_ini,coor_fin);
						
						//Si hay alguna palabra ya validada que contiene 
						//a la nueva palabra o viceversa me quedo con la
						//mas larga (para plurales o al-ale)
						if(listScoredWord.isEmpty()) {
							listScoredWord.add(verificada);
						}
						else{
							HashSet<ScoredWord> borrar = new HashSet<ScoredWord>();
							HashSet<ScoredWord> anadir = new HashSet<ScoredWord>();
							for(ScoredWord s: listScoredWord) {			
								
								if(s.thisContenidaEnOther(verificada)) {
									borrar.add(s);
									anadir.add(verificada);
								}
								else if(verificada.thisContenidaEnOther(s)) {
									//No aniado la palabra porque hay otra mas grande
								}
								else {
									anadir.add(verificada);
								}
							}
							for (ScoredWord s: borrar) {
								listScoredWord.remove(s);
							}
							for (ScoredWord s: anadir) {
								listScoredWord.add(s);
							}
						}
						
					}
					col_fin++;
				}
				col_ini--;
			}

		}
		
		puntos = 0;
		for(ScoredWord s : listScoredWord) {
			puntos += s.getPuntos();
			System.out.println (s.getPalabra());
		}
		
		System.out.println("Se anadieron puntos: " + puntos);
		if(!listScoredWord.isEmpty()) {
			j.recibirPuntos(puntos);
			j.actualizarGanadas();
		}
		
		this.listaPalabrasVerificadas.addAll(listScoredWord);
		return !listScoredWord.isEmpty() ||lista.isEmpty();
	}
	
	/**
	 * Este metodo calcula la puntuacion total de una palabra
		Primero : comprueba la direccion de la palabra a puntuar
		Segundo : acumula en la variable puntos la puntuacion de la palabra, para ello
		suma las puntuaciones de las letras colocadas por su multiplicador de letra de la
		casilla en la que se encuentra la ficha
		Si el multiplicador es de palabra lo acumula en la variable multiplicador_palabra
		Tercero : multiplica la puntuacion obtenida por el multiplicador_palabra
	 * @param c1	Coordenadas donde comienza la palabra
	 * @param c2	Coordenadas donde termina la palabra
	 * @param dir	True si es horizontal y False si es vertical
	 * @return
	 */
	public int valorPalabra(Coordenadas c1,Coordenadas c2,boolean dir, Tablero tablero) {
		int puntos  = 0;
		int multiplicador_palabra=1;
		if(dir) { //Horizontal
			for(int i = c1.getColumna();i<=c2.getColumna();i++) {
				if(tablero.getMultiplicador(c1.getFila(),i)<4) {
				puntos += tablero.getFicha(c1.getFila(), i).getPuntos() * tablero.getMultiplicador(c1.getFila(),i);
				}
				else {
					puntos += tablero.getFicha(c1.getFila(), i).getPuntos();
					multiplicador_palabra *= (tablero.getMultiplicador(c1.getFila(),i) -2);
				}
			}
		}
		else { //Vertical
			for(int i = c1.getFila();i<=c2.getFila();i++) {
		
				if(tablero.getMultiplicador(i,c1.getColumna())<4) {
					puntos += tablero.getFicha(i, c1.getColumna()).getPuntos() * tablero.getMultiplicador(i,c1.getColumna());
				}
				else {
					puntos += tablero.getFicha(i, c1.getColumna()).getPuntos();
					multiplicador_palabra *= (tablero.getMultiplicador(i,c1.getColumna()) -2);
				}
			}
		}	
		puntos *= multiplicador_palabra;
		return puntos;
	}
	
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------

	/**
	 * Devuelve una referencia a la lista de palabras colocadas en el tablero
	 * No puedes modificar lo que devuelve esta metodo
	 * @return listaPalabrasVerificadas
	 * @see #listaPalabrasVerificadas
	 */
	public List<ScoredWord> getVerificadas() {
		return Collections.unmodifiableList(this.listaPalabrasVerificadas);
	}
	
	/**
	 * Sirve a los jugadores maquina para saber si son los primeros en colcoar
	 * una palabra
	 * @return t longitud de la lista de palabras ya colocadas
	 */
	public boolean primeraPalabra() {
		return this.listaPalabrasVerificadas.size() == 0;
	}

	/**
	 * Devuelve el numero de fichas restantes en el mazo
	 * @return string
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Numero de fichas restantes en el mazo : " + mazo.getNumFichas() + '\n');
		return sb.toString();
	}
	
	public static boolean isNumeric(String str)	{

		//Metodo que devuelve si un string es un numero
		  return str.matches("-?\\d+(\\.\\d+)?");  
	}
	
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------
	
	@Override
	public void setMemento(Memento m) {

		listaPalabrasVerificadas.clear(); //Vaciar en caso de que la lista contuviera palabras
		JSONArray jsonArrayPalabrasVerificadas = m.getState().getJSONArray("palabras verificadas");
		for(int i = 0; i < jsonArrayPalabrasVerificadas.length(); ++i) {
			ScoredWord sw = new ScoredWord();
			Memento mementoSW = new Memento();
			
			mementoSW.setState(jsonArrayPalabrasVerificadas.getJSONObject(i));
			sw.setMemento(mementoSW); //A�adir estado a la ficha
			listaPalabrasVerificadas.add(sw); //A�adir ficha a la lista
		}	
	}
	

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonGame = new JSONObject();
		JSONArray jsonArrayPalabrasVerificadas = new JSONArray();
		
		for(ScoredWord sw : this.listaPalabrasVerificadas) {
			JSONObject jsonScoredWords = new JSONObject();
			jsonScoredWords = sw.createMemento().getState();
			
			jsonArrayPalabrasVerificadas.put(jsonScoredWords);
		}
		
		jsonGame.put("palabras verificadas", jsonArrayPalabrasVerificadas);
		memento.setState(jsonGame);
		
		return memento;
	}
		

}
