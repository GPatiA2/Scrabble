package modelo;

import java.util.List;

/**
 * Interfaz que implementan los objetos que observan los cambios en 
 * el orden de la ejecucion de los turnos
 * @author Grupo 5
 * @see AdminTurnos
 *
 */
public interface TManagerObserver extends ModelObserver {
	/**
	 * Metodo para notificar que se ha avanzado en la partida, y que el jugador que tiene
	 * el turno actual y el siguiente han cambiado
	 * @param act Nombre del Integrante de la partida que tiene el turno actual
	 * @param sig Nombre del Integrante de la partida que tendra el siguiente turno
	 */
	public void mostrarTurnos(String act, String sig);
	
	
	/**
	 * Metodo para notificar a los observadores que se ha comenzado un nuevo turno
	 * @param mano Lista de fichas a mostrar por Pantalla
	 */
	public void nuevoTurno(Integrante i, String act, String sig);

	public void turnoAcabado();	
	
	/**
	 * Metodo para notificar un error a un jugador por la GUI
	 * @param nick 
	 * @param err
	 */
	public void onError(String err, String nick);	
	
	public void onRegister(String act, String sig);
	
}
