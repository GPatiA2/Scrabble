package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import Excepciones.CommandExecuteException;
import javafx.util.Pair;
import modelo.Diccionario.Diccionario;
import utils.Coordenadas;
import utils.OrdenarCoordenadas;
import utils.PalabraPosible;
import utils.ScoredWord;
/**
 * Clase que se encarga de manejar las reglas del juego 
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
	 * Variable bool que indica si se va a saltar el turno del jugador siguiente
	 * @see Game#SaltarJugador()
	 */
	private boolean saltar;
	/**
	 * Variable que indica en que orden avanzan los turnos en la partida
	 * False indica orden inverso (restando al indice)
	 * True indica orden normal (sumando al indice)
	 * @see #invertirSentido()
	 */
	private boolean orden; 
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
	public Game() throws IOException {
		salir = false;
		iniMazo();	
		diccionario = Diccionario.getInstance();
		tablero = new Tablero();
		player = null;
		saltar = false;
		listaPalabrasVerificadas = new ArrayList<ScoredWord>();
	}
	
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
	 * Rellena las manos del jugador activo en este momento
	 * @see #player
	 */
	public void update() {
		rellenarManos();
	}
	public void rellenarManos() {
		while (player.size() < numFichasMaximo ) {
			Ficha f = mazo.robar();
			player.robar(f);
			updateMano(f,this.player);
		}
	}
		
	public void exit() {
		salir = true;
	}
	
	public boolean PartidaAcabada() {
		return salir;
	}
	
	/**
	 * Metodo que coloca una ficha en el tablero
	 * Retira el la ficha con el ID pasado por parametro de la mano del jugador activo y la coloca en el tablero
	 * 
	 * @param ficha 	id unico de la ficha que se va a colocar o letra de la ficha
	 * @param letra		si fuera un comodin, letra por la que quiero sustituir
	 * @param c 		coordenadas (fila/columna) donde se coloca la ficha
	 * 
	 * @see Tablero
	 * @see ComandoColocarFicha
	 */
	
	public  void colocaFicha (String ficha,char letra, Coordenadas c) {
		Ficha ficha_colocar = null;
		boolean colocada = false;
		try {
			
			//Checkeo si son validas : ya que si estan fuera del tablero no debo
			//colocar la ficha
			if (Coordenadas.checkCommand(c.getFila(), c.getColumna())) {
					//Busco que la ficha este en la mano del jugador
				if(player.size() > 0 && tablero.emptyCasilla(c.getFila(), c.getColumna())) {
					if( tablero.esDisponible(c.getFila(), c.getColumna()) ) {
						
						ficha_colocar = player.ExisteFicha(ficha);//Si no existe la ficha lanzo una excepcion
						if(ficha_colocar !=null) {
							if(letra != '0') {
								ficha_colocar.setFicha(letra, 0);
							}
							//Si todo ha salido bien, a�ado la ficha al tablero en las coordenadas correspondientes
							
							tablero.aniadeFicha(ficha_colocar, c.getFila(), c.getColumna());
							//Actualizo la GUI del tablero
							
							this.updateTablero(c,ficha_colocar);
							colocada = true;
						}			
						else {
							
							throw new CommandExecuteException("Esa ficha no pertenece a su mano");
						}									
					}
					else {
						throw new CommandExecuteException("Solo puedes poner una ficha al lado de una casilla que contenga otra, o si es el primer turno, en el centro");
					}
				}	
				else {
					throw new CommandExecuteException("Debes tener una ficha en la mano para poder colocarla");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		for(GameObserver o : this.observadores) {
			if(ficha_colocar == null) ficha_colocar = new Ficha(); 
			if(!ficha.equals("*"))ficha_colocar.setId(ficha);
			o.borrarFichaMano(ficha_colocar, this.player, colocada);
		}		
	}
	
	/**
	 * Quita una ficha del tablero y la devuelve a la mano del jugador
	 * @param c Coordenadas donde se encuentra la ficha que se quiere quitar
	 * @throws CommandExecuteException
	 * @see Tablero
	 * @see ComandoQuitarFicha
	 */
	public void quitarFicha(Coordenadas c) throws CommandExecuteException {
		Ficha f = tablero.quitarFicha(c.getFila(),c.getColumna());
		player.robar(f);
		for(GameObserver ob : observadores) {
			ob.actualizaCasilla(c, tablero.getCasilla(c.getFila(),c.getColumna()));
			ob.fichaRobada(f, player);
		}
	}
	
	/**
	 * Comprueba si la casilla central es disponible 
	 * @return b atributo disponible de la casilla central
	 * @see Casilla
	 */
	public boolean checkCentro() {
		return tablero.centroDisponible();
	}
	
	//Funcion que calcula cual es el siguiente turno. La he puesto aqui para que en un futuro, las ventajas
	//	sean capaces de alterar el orden de los turnos. (saltarse uno por ej)
	/**
	 * Calcula el indice del jugador siguiente 
	 * @param act Indice en la lista de jugadores del turno actual
	 * @return aux Indice en la lista de jugadores del jugador siguiente 
	 * 
	 * @see AdminTurnos
	 */
	public int getSigTurno(int act) {
		
		int aux;
		
		if (orden) {
			aux = act-1;
			if (saltar) {
				aux--;
				saltar = false;
			}
		}
		
		else {
			aux = act+1;
			if (saltar) {
				aux++;
				saltar = false;
			}
		}
		
		return aux;
	}

	/**
	 * Inicializa el mazo haciendo uso de la clase GeneradorMazo
	 * @see mazo
	 * @see GeneradorMazo
	 * @throws IOException
	 */
	private void iniMazo() throws IOException {
		
		/*
		 * Este metodo genera un mazo a partir de un archivo .json
		 * A continuacion, el GeneradorMazo se encarga de leer la entrada del fichero y
		 * de mezclar las fichas que recibe. Devuelve una coleccion de solo lectura que tiene
		 * las fichas en un orden aleatorio y se construye el mazo con esa coleccion 
		 *
		 */
		
		InputStream entrada = new FileInputStream(new File("src/ArchivoCarga/mazo.json"));
		GeneradorMazo g_m= new GeneradorMazo(entrada);
		mazo = new mazo(g_m.getRandomStack());
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Numero de fichas restantes en el mazo : " + mazo.getNumFichas() + '\n');
		return sb.toString();
	}
		
	/**
	 * Recibe una lista de coordenadas en las que el jugador ha colocado una ficha
	 * y comprueba que se han formado palabras correctamente
	 * @param lista lista de cooredendas donde el jugador ha colocado fichas
	 * @return b 	true si se han formado palabras correctamente
	 * @see ComandoPasarTurno
	 * @see ScoredWord
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
		if(dir) {
			for(int i = c1.getColumna();i<=c2.getColumna();i++) {
				if(tablero.getMultiplicador(c1.getFila(),i)<4) {
				puntos += tablero.getFicha(c1.getFila(), i).getPuntos()* tablero.getMultiplicador(c1.getFila(),i);
				}
				else {
					puntos += tablero.getFicha(c1.getFila(), i).getPuntos();
					multiplicador_palabra *= (tablero.getMultiplicador(c1.getFila(),i) -2);
				}
			}
		}
		else {
			for(int i = c1.getFila();i<=c2.getFila();i++) {
				if(tablero.getMultiplicador(i,c1.getColumna())<4) {
					puntos += tablero.getFicha(i, c1.getColumna()).getPuntos()* tablero.getMultiplicador(i,c1.getColumna());
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
	
	//metodos de la interfaz Originator
	
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
		
	public static boolean isNumeric(String str)	{

		//Metodo que devuelve si un string es un numero
		  return str.matches("-?\\d+(\\.\\d+)?");  
	}

}
