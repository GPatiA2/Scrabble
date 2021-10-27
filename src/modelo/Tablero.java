package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Excepciones.CommandExecuteException;
import javafx.util.Pair;
import utils.Coordenadas;
import utils.MyStringUtils;

public class Tablero implements Originator, Observable<TableroObserver>{

	/**
	 * Vectores privados para comprobar las casillas adyacentes
	 */
	private final static int[] adyX = {0, 0, 1, -1};
	private final static int[] adyY = {1, -1, 0, 0};
	/**
	 * Dimensiones del tablero
	 */
	private static final int nFilas = 15;
	private static final int nCols = 15;
	private List<List<Casilla>> grid;
	private String[][] tab;
	
	private List<TableroObserver> observadores;
	
	final String space = " ";
	/**
	 * Puntero a la casilla central
	 */
	private Casilla center;
	
	//-------------------------CONSTRUCTOR--------------------------------

	/**
	 *  Constructor del Tablero. Para visualizar dicho tablero se debe
	 *  llamar al metodo toString con dicho tablero creado.
	 */
	public Tablero() {
		observadores = new ArrayList<TableroObserver>();
		grid = new ArrayList<List<Casilla>>(nFilas);		
		for(int i = 0; i < nFilas; i++) {
			grid.add(new ArrayList<Casilla>(nCols));
			for(int j = 0; j < nCols; j++) {
				Coordenadas coord = new Coordenadas(i,j);
				//Diagonales
				if (i == j || j == nCols - i - 1) {
					//Esquinas Triple Palabra
					if (i == 0 || i == 14) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_TRIPLE_PALABRA));
		
					}
					//Casilla Triple Letra
					else if ( i == 5 || i == 9) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_TRIPLE_LETRA));
						
					}
					//Casilla Doble Letra
					else if (i == 6 || i == 8) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_DOBLE_LETRA));
						
					}
					//Si no es la casilla central Doble Palabra
					else if (i != 7) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_DOBLE_PALABRA));
						
					}
					//Casilla central. La ponemos como normal
					else {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_NORMAL));
						
					}
				}
				
				//Triple Palabra restantes
				else if ((i == 7 && j == 0) || (i == 7 && j == 14) || (i == 0 && j == 7) || (i == 14 && j == 7)) {
					grid.get(i).add(j, new Casilla(Casilla.CASILLA_TRIPLE_PALABRA));
					
				}
				
				//Doble Palabra completados en las diagonales
				
				//Triple Letra restantes
				else if ((i == 1 && j == 5) || (i == 5 && j == 1) || (i == 1 && j == 9) || (i == 9 && j == 1) || 
						(i == 5 && j == 13) || (i == 13 && j == 5) || (i == 9 && j == 13) || (i == 13 && j == 9)) {
					grid.get(i).add(j, new Casilla(Casilla.CASILLA_TRIPLE_LETRA));
					
				}
				
				//Doble Letra restantes y casillas normales
				else {
					
					//Laterales
					if ((i == 0 && ((j == 3)|| j == 11)) || (j == 0 && ((i == 3)|| i == 11)) ||
						(i == 14 && ((j == 3)|| j == 11)) || (j == 14 && ((i == 3)|| i == 11))	) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_DOBLE_LETRA));
						
					}
					
					//Fila y columna central
					else if ((i == 7 && ((j == 3)||(j == 11))) || (j == 7 && ((i == 3)||(i == 11)))) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_DOBLE_LETRA));
						
					}
					
					//Resto de casillas Doble Letra
					else if (((i == 6)||(i == 8)) && ((j == 2)||(j == 6)||(j == 8)||(j == 12)) ||
							((j == 6)||(j == 8)) && ((i == 2)||(i == 6)||(i == 8)||(i == 12))) {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_DOBLE_LETRA));
						
					}
					
					//Casillas normales
					else {
						grid.get(i).add(j, new Casilla(Casilla.CASILLA_NORMAL));
						
					}
				}
			}
		}
		center = grid.get(nFilas/2).get(nCols/2);
		center.setDisponible(true);
		tab = new String[nFilas][nCols];
	}
	
	//--------------------METODOS PARA LAS OPERACIONES DEL TABLERO------------------------

	/**
	 * En caso de ser posible annade una ficha proporcionada al tablero y actualiza
	 * la disponibilidad de las fichas adyacentes
	 * @param f Ficha: ficha a annadir
	 * @param coordX int: coordenada X
	 * @param coordY int: coordenada Y
	 */
	public void aniadeFicha(Ficha f, int coordX, int coordY) {
		System.out.println("Coordenadas " + coordX + " " + coordY);
		
		if(this.coordenadasValidas(coordX, coordY)) {
			
			if(this.emptyCasilla(coordX, coordY) && grid.get(coordX).get(coordY).esDisponible()) {
					
				//Anadir la ficha a la casilla del tablero en la posicion que corresponda
				setFicha(f,coordX,coordY);
				for(TableroObserver o : this.observadores) {
					o.actualizaCasilla(new Coordenadas(coordX, coordY),this.getCasilla(coordX, coordY));
				}
				
				actDisponibles(coordX,coordY); //Actualiza casillas adyacentes

			}
			else {
				throw new IllegalArgumentException("La casilla dada ya tiene una ficha asignada");
			}
		}
		else {
			throw new IllegalArgumentException("Coordenadas no validas");
		}		
	}
	
	/**
	 * En caso de ser posible elimina una ficha en las coordenadas dadas
	 * @param coordX
	 * @param coordY
	 * @return f : Ficha eliminada del tablero
	 * @throws CommandExecuteException en caso de no haber ficha en la casilla indicada
	 */
	public Ficha quitarFicha(int coordX, int coordY) throws CommandExecuteException {
		//Si la casilla no esta vacia, se elimina la ficha y se actualiza su disponibilidad
		if(!emptyCasilla(coordX, coordY)) { 
			Ficha f = getFicha(coordX,coordY);
			grid.get(coordX).get(coordY).remove();
			for(TableroObserver t : this.observadores) {
				t.actualizaCasilla(new Coordenadas(coordX, coordY),this.getCasilla(coordX, coordY));
			}
			actDisponibles(coordX,coordY);
			return f;			
		}
		else {
			throw new CommandExecuteException("Debes seleccionar una casilla donde haya una ficha para poder quitarla");
		}
	}
	
	/**
	 * Actualiza las casillas disponibles cuando se coloca o se quita una ficha de la casilla (cX, cY) 
	 * @param cX
	 * @param cY
	 */
	public void actDisponibles(int cX, int cY) {
		
		Casilla act = grid.get(cX).get(cY);
		
		//Primero se calcula la disponibilidad de la casilla dada y se notifica a los observers
		calcDisponibles(cX,cY);
		
		
		//Despues se obtienen las casillas adyacentes y se calcula su disponibilidad en consecuencia
		List<Coordenadas> ady = adyacentes(cX, cY);
		for(Coordenadas c : ady) {
			calcDisponibles(c.getFila(),c.getColumna());
			
		}
		
	}
	
	/**
	 * Calcula la disponibilidad de la casilla situada en (x.y). 
	 * Si es el centro, que sea o no disponible depende de si tiene una ficha puesta o no.
	 * Si no es el centro, la casilla ser� disponible si no contiene ninguna ficha y alguna de las 
	 * casillas adyacentes contiene una ficha 
	 * @see /Documentos/Reglas.docx
	 * @param x
	 * @param y
	 */
	private void calcDisponibles(int x, int y) {
		Casilla cs = grid.get(x).get(y); //Casilla a comprobar disponibilidad
		
		if(x == nFilas/2 && y == nCols/2) { //CASILLA CENTRAL
			cs.setDisponible(cs.empty());
			
		}
		else { //CASILLA NO CENTRAL
			List<Coordenadas> ady = adyacentes(x, y);
			
			//Comprueba si hay alguna casilla adyacente llena
			boolean adyllena = false;
			for(Coordenadas c : ady) {
				adyllena = adyllena || !grid.get(c.getFila()).get(c.getColumna()).empty();
			}

			//Una casilla es disponible si esta vacia y tiene una adyacente que este llena
			boolean disp = adyllena && cs.empty();
			cs.setDisponible(disp);		
			
		}
	}
	
	/**
	 * Devuelve una lista con las casillas adyacentes a una casilla dada
	 * @param coordX : coordenada X de la casilla
	 * @param coordY : coordenada Y de la casilla
	 * @return List<Coordenadas> : lista con las casillas adyacentes
	 */
	private List<Coordenadas> adyacentes(int coordX, int coordY){
		List<Coordenadas> ady = new ArrayList<Coordenadas>();
		for(int i = 0; i < adyX.length; i++) {	
			int cX = coordX + adyX[i];
			int cY = coordY + adyY[i];
			//Si la casilla adyacente no se sale del tablero la annadimos a la lista
			if(coordenadasValidas(cX, cY)/*Coordenadas.checkCommand(cX,cY)*/) { 
				ady.add(new Coordenadas(cX,cY));			
			}
		}
		return ady;
	}
	
	//--------------------METODOS PARA VISUALIZAR EL TABLERO------------------------------

	/**
	 * Permite visualizar el tablero por consola
	 */
	public String toString() {
		encodeBoard();
		
		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		String intersect = space;
		String vIntersect = space;
		String hIntersect = "-";
		String corner = space;
		
		String cellDelimiter = MyStringUtils.repeat(hDelimiter, cellSize);
		
		String rowDelimiter = vIntersect + MyStringUtils.repeat(cellDelimiter + intersect, nCols-1) + cellDelimiter + vIntersect;
		String hEdge =  corner + MyStringUtils.repeat(cellDelimiter + hIntersect, nCols-1) + cellDelimiter + corner;
		
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineEdge = String.format("%n%s%s%n", margin, hEdge);
		String lineDelimiter = String.format("%n%s%s%n", margin, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		
		str.append(lineEdge);
		for(int i=0; i<nFilas; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<nCols; j++)
					str.append( MyStringUtils.centre(tab[i][j], cellSize)).append(vDelimiter);
				if (i != nFilas - 1) str.append(lineDelimiter);
				else str.append(lineEdge);	
		}
		
		return str.toString();
	}

	private void encodeBoard() {
		for(int i = 0; i < nFilas; i++) {
			for(int j = 0; j < nCols; j++) {
				tab[i][j] = grid.get(i).get(j).to_string(); 
			}
		}
	}
	
	public boolean emptyCasilla(int coordX, int coordY) {
		//devuelve si la casilla en la posicion CoorX, CoorY esta vacia
		return grid.get(coordX).get(coordY).empty();
	}
	
	public boolean coordenadasValidas(int coordX, int coordY) {
		return 0 <= coordX && coordX < nFilas && 0 <= coordY && coordY < nCols;
	}
	
	
	//--------------------METODOS DE LA INTERFAZ MEMENTO------------------------------

	@Override 
	public void setMemento(Memento m) {
		
		JSONArray jsonArrayGrid = m.getState().getJSONArray("grid");
		for(int i = 0; i < jsonArrayGrid.length(); ++i) {
			Memento mementoCasilla = new Memento();
			
			mementoCasilla.setState(jsonArrayGrid.getJSONObject(i));
			int coordX = jsonArrayGrid.getJSONObject(i).getInt("fila");
			int coordY = jsonArrayGrid.getJSONObject(i).getInt("columna");
			Casilla c = grid.get(coordX).get(coordY); //Obtener casilla en X,Y
			c.setMemento(mementoCasilla); //Establecer estado de la casilla	
		}
	}

	@Override
	public Memento createMemento() {
		Memento memento = new Memento();
		JSONObject jsonTablero = new JSONObject();
		JSONArray jsonArrayGrid = new JSONArray();
		
		for(int i = 0; i < nFilas; i++){
			for(int j = 0; j < nCols; j++) {
				JSONObject jsonCasilla = new JSONObject();
				
				if(!this.grid.get(i).get(j).empty()) {
					jsonCasilla = this.grid.get(i).get(j).createMemento().getState();
					jsonCasilla.put("fila", i);
					jsonCasilla.put("columna", j);
					jsonArrayGrid.put(jsonCasilla);
				}
			}
		}
		
		jsonTablero.put("grid", jsonArrayGrid);
		
		memento.setState(jsonTablero);
		
		return memento;
	}
	

	//--------------------METODOS DE LA INTERFAZ OBSERVABLE------------------------------


	@Override
	public void addObserver(TableroObserver o) {
		observadores.add(o);
		o.onRegister(Collections.unmodifiableList(this.grid));
	}

	@Override
	public void removeObserver(TableroObserver o) {
		observadores.remove(o);
	}

	public boolean centroVacio() {
		return !center.empty();
	}
	
	//--------------------METODOS AUXILIARES, GETTERS, SETTERS...------------------------
	
	/**
	 * Devuelve la disponibilidad de la casilla central
	 * @return b true si la casilla central esta disponible y vacia
	 */
	public boolean centroDisponible() {
		return center.esDisponible() && !center.empty();
	}
	
	/**
	 * Comprueba la disponibilidad de la casilla en (x,y)
	 * @param x coordenada
	 * @param y coordenada
	 * @return b true si la casilla esta disponible
	 */
	public boolean esDisponible(int x, int y) {
		return grid.get(x).get(y).esDisponible();
	}
	
	/**
	 * Pone la casilla (x,y) al valor proporcionado en disponible
	 * @param x
	 * @param y
	 * @param disponible
	 */
	public void setDisponible(int x, int y, boolean disponible) {
		grid.get(x).get(y).setDisponible(disponible);
	}
	
	public void setFicha(Ficha f, int x, int y) {
		grid.get(x).get(y).add(f);
	}
	
	public Ficha getFicha(int coordX, int coordY) {
		return grid.get(coordX).get(coordY).getFicha();
	}
	
	public int getMultiplicador(int coordX, int coordY) {
		return grid.get(coordX).get(coordY).getMultiplicador();
	}
	
	public Casilla getCasilla(int x, int y) {
		return grid.get(x).get(y);
	}
	
}
