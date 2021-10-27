package modelo;

import java.util.List;

import utils.Coordenadas;
/**
 * Interfaz que implementan aquellos objetos que observan los cambios en la situacion
 * actual del juego
 * @author Grupo 5
 * @see Game
 */
public interface TableroObserver extends ModelObserver {

	/**
	 * Metodo para notificar que se ha actualizado una casilla del tablero
	 * @param coord Coordenadas de la casilla que ha sufrido un cambio
	 * @param t		Tablero que contiene a la casilla cambiada
	 * @see Game
	 * @see Casilla
	 */
	public void actualizaCasilla(Coordenadas coord, Casilla c);
	
	/**
	 * Metodo usado al añadir el observador para cargar las fichas de tablero en caso 
	 * de que se inicie la partida sobre una partida ya empezada
	 * @param grid  lista de casillas del tablero con sus propiedades
	 * @see Game
	 * @see Casilla
	 */
	public void onRegister(List<List<Casilla>> grid);

}
