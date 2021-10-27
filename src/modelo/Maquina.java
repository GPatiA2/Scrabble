
package modelo;

import java.util.ArrayList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONObject;

import Command.Command;
import modelo.Estrategias.EasyStrategy;
import modelo.Estrategias.HardStrategy;
import modelo.Estrategias.MediumStrategy;
import modelo.Estrategias.RunStrategy;
import modelo.diccionario.Diccionario;
import utils.Coordenadas;
import utils.OrdenarCoordenadas;
/**
 * Representa a un jugador automatico dentro de la partida.
 *
 */
public class Maquina extends Integrante implements Originator{
	
	private String nivel;
	private Tablero tablero;
	private Game game;
	private Diccionario diccionario;
	private RunStrategy estrategia;
	private List<Character> letras;
	
	public Maquina(String nivel, int i, Tablero tablero, Game game) throws Exception{
		super();
		this.nick = "MAQUINA" + i;
		this.nivel = nivel;
		this.tablero = tablero;
		this.game = game;
		diccionario = Diccionario.getInstance();
		this.letras = new ArrayList<Character>();
		this.establecerEstrategia();
	}
	
	public Maquina(String nivel, int i) throws Exception {
		super();
		this.nivel = nivel;
		this.nick = "MAQUINA" + i;
		diccionario = Diccionario.getInstance();
		this.letras = new ArrayList<Character>();
		this.establecerEstrategia();
	}
	
	public Maquina() {
		
	}
	
	/**
	 * Inicializa el atributo estrategia en funcion del nivel 
	 * @throws Exception
	 */
	public void establecerEstrategia() throws Exception {
		if (nivel.equals("EASY")) {
			this.estrategia = new EasyStrategy(this.nivel);
		}
		else if (nivel.equals("MEDIUM")) {
			this.estrategia = new MediumStrategy(this.nivel);
		}
		else if (nivel.equals("HARD")) {
			this.estrategia = new HardStrategy(this.nivel);
		}
		else {
			throw new Exception("Nivel de dificultad no valido");
		}
	}
	
	@Override
	public void juegaTurno(Turno turno, AdminTurnos admin, List<Coordenadas> listaFichasNoFijas) {
		this.puedeJugar = true;
		letras.clear();
		for (Ficha f : mano) {
			letras.add(f.getLetra());
		}
		List<Command> comandos = this.estrategia.run(game, turno, listaFichasNoFijas, this, letras, diccionario);
		for (Command c : comandos) {
			System.out.println(c.toString());
			admin.runCommand(c);
		}
		this.acabaTurno();
	}
	
	@Override
	public void acabaTurno() {
		this.puedeJugar = false;
	}
	
	/** 
	 * Devuelve el nivel de dificultad de la maquina
	 */
	public String getNivel() {
		return this.nivel;
	}
	
	
	/**
	 * Cambia el atributo game al valor especificado
	 */
	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	/**
	 * Cambia el atributo tablero al valor especificado
	 */
	@Override
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	
	/**
	 * Este metodo decide si una palabra se puede colocar en el tablero utilizando las coordenadas
	 * dadas como pivote. La i es la posicion que ocupa la letra del pivote en la palabra.
	 * 
	 * Si se puede devuelve un mapa con las letras que se van a colocar y las coordenadas
	 * donde se colocaran. Si no devuelve un mapa vacio.
	 */
	@Override
	public Map<Coordenadas, Character> esPosible(char[] cadena, Coordenadas pivote, int i, String palabra) {

		Map<Coordenadas, Character> coordenadas = new TreeMap<Coordenadas, Character>(new OrdenarCoordenadas());
		boolean valido = true;
		
		boolean horizontal = false;
		
		if (pivote.getColumna() == 0 && pivote.getFila() == 0 && tablero.emptyCasilla(pivote.getFila(), pivote.getColumna() + 1)) {
			horizontal = true;
		}
		else if (pivote.getColumna() == 0 && pivote.getFila() == 14 && tablero.emptyCasilla(pivote.getFila(), pivote.getColumna() + 1)) {
			horizontal = true;
		}
		else if (pivote.getColumna() == 14 && pivote.getFila() == 0 && tablero.emptyCasilla(pivote.getFila(), pivote.getColumna() - 1)) {
			horizontal = true;
		}
		else if (pivote.getColumna() == 14 && pivote.getFila() == 14 && tablero.emptyCasilla(pivote.getFila(), pivote.getColumna() - 1)) {
			horizontal = true;
		}
		else if (pivote.getColumna() == 0 || pivote.getColumna() == 14) {
			if (!tablero.emptyCasilla(pivote.getFila()-1, pivote.getColumna()) || !tablero.emptyCasilla(pivote.getFila()+1, pivote.getColumna()) ) {
				horizontal = true;
			}
		}
		else if (tablero.emptyCasilla(pivote.getFila(), pivote.getColumna()-1) && tablero.emptyCasilla(pivote.getFila(), pivote.getColumna()+1)) { 
			horizontal = true;
		}
		
		int columna;
		int fila;
		
		if (horizontal) {
			columna = pivote.getColumna() - i;
			fila = pivote.getFila();
		}
		else {
			columna = pivote.getColumna();
			fila = pivote.getFila() - i;
		}
		
		int j = 0;
		while (valido && j<cadena.length) {
			
			if (j < 0 || j > 15) {
				valido = false;
			}
			
			if (fila < 0 || fila > 14){
				valido = false;
			}
			
			else if (columna < 0 || fila < 0) {
				valido = false;
			}
			
			else if (tablero.emptyCasilla(fila, columna)) {
				/*
				 * Si la casilla esta vacia pasamos a comprobar si hay alguna razon por la que no se podria colocar la palabra
				 * Las siguientes condiciones describen situaciones en las que no se puede colocar la ficha en esa casilla.
				 * Si se cumple alguna de esas condiciones valido pasa a ser false y la palabra no se puede colocar
				 */
				
				if (j == 0 && columna > 0 && !tablero.emptyCasilla(fila, columna-1)) {
					valido = false;
				}
				else if (j == cadena.length-1 && columna < 14 && !tablero.emptyCasilla(fila, columna+1)) {
					valido = false;
				}
				else if (j == 0 && !tablero.emptyCasilla(fila, columna-1)) {
					valido = false;
				}
				else if (j == cadena.length-1 && !tablero.emptyCasilla(fila, columna+1)) {
					valido = false;
				}
				else if (horizontal && (!tablero.emptyCasilla(fila+1, columna) || !tablero.emptyCasilla(fila-1, columna))) {
					valido = false;
				}
				else if (!horizontal && (!tablero.emptyCasilla(fila, columna+1) || !tablero.emptyCasilla(fila, columna-1))) {
					valido = false;
				}
				else {
					coordenadas.put(new Coordenadas(fila, columna), cadena[j]);
				}
			}
			
			//Si la casilla no esta vacia y no es el pivote
			else if (fila != pivote.getFila() && columna != pivote.getColumna()) {
				//Si no coincide la letra
				if (cadena[j] != tablero.getCasilla(fila,columna).getFicha().getLetra()) {
					valido = false;
				}
				else if (!horizontal && !tablero.emptyCasilla(fila+1, columna)) {
					valido = false;
				}
				else if (!horizontal && !tablero.emptyCasilla(fila+1, columna)) {
					valido = false;
				}
			}
			
			j++;
			if (horizontal) {columna++;}
			else {fila++;}
		}
		
		//Al final, si por alguna razon el booleano valido ha pasado a ser false vaciamos el mapa
		if (!valido) {
			coordenadas.clear();
		}
		
		return coordenadas;
	}
	
	@Override //Las maquinas no ganan puntos por monedas
	protected void convertirMonedasPuntos() {
		// TODO Auto-generated method stub
	}
	
	public String toString() {
		String str = "Turno de " + nick + ": " + puntos + " puntos." + "\n";
		return str;
	}
	
	//--------------------METODOS DE LA INTERFAZ ORIGINATOR------------------------
	
		@Override
		public void setMemento(Memento m) {
			
			if(m.getState().getString("tipo").equals("maquina")) {				
				nick = m.getState().getString("nick");
				nivel = m.getState().getString("nivel");
				puntos = m.getState().getInt("puntos");
				puedeJugar = m.getState().getBoolean("puedeJugar");
				
				try {
					this.establecerEstrategia();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				JSONArray jsonArrayMano = m.getState().getJSONArray("mano");
				mano.clear(); //Limpiar mano en caso de que contuviera fichas
				
				for(int i = 0; i < jsonArrayMano.length(); ++i) {
					Memento mementoFicha = new Memento();
					
					mementoFicha.setState(jsonArrayMano.getJSONObject(i));
					Ficha f = new Ficha();
					f.setMemento(mementoFicha); //Establecer estado de la ficha
					mano.add(i, f); //Annadir ficha a la mano del jugador
				}
			}
			
		}

		@Override
		public Memento createMemento() {
			Memento memento = new Memento();
			JSONObject jsonMaquina = new JSONObject();
			JSONArray jsonArrayMano = new JSONArray();
			
			jsonMaquina.put("tipo", "maquina");
			jsonMaquina.put("nick", this.nick);
			jsonMaquina.put("puntos", this.puntos);
			jsonMaquina.put("nivel", this.nivel);
			jsonMaquina.put("puedeJugar", this.puedeJugar);
			
			for(Ficha f : this.mano) {
				JSONObject jsonFicha = new JSONObject();
				jsonFicha = f.createMemento().getState();
				jsonArrayMano.put(jsonFicha);
			}
			
			jsonMaquina.put("mano", jsonArrayMano);
			
			memento.setState(jsonMaquina);
			
			return memento;
		}

	
}
