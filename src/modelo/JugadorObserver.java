package modelo;

import java.util.List;

public interface JugadorObserver extends ModelObserver {
	/**
	 * Metodo para notificar que un integrante de la partida ha robado una ficha
	 * @param f Ficha que ha sido robada del mazo
	 * @param j Integrante de la partida que ha robado el mazo
	 * @see Integrante
	 * @see Ficha
	 */
	public void fichaRobada(Ficha f, Integrante j);
	
	/**
	 * Metodo que se emplea para notificar que un integrante de la partida ha retirado una ficha de
	 * su mano
	 * @param f 	 Ficha que ha sido retirada de la mano del jugador
	 * @param player Jugador de cuya mano se retira la ficha
	 */
	public void fichaUsada(Ficha f, Integrante player);
	
	/**
	 * Metodo para borrar un ficha de la mano del jugador
	 * @param f ficha a borrar
	 * @param j jugador que tiene esa ficha
	 * @param bienColocada boolean que indica si la ficha se ha colocado de manera correcta
	 */
	public void borrarFichaMano(Ficha f, Integrante j,boolean bienColocada);
	
	/**
	 * Metodo para notificar que los puntos de un integrante han cambiado
	 * @param puntos Puntos que tiene el jugador
	 * @param nick	 Nombre del integrante que tiene los puntos pasados por parametro
	 */
	public void mostrarPuntos(int puntos, String nick);
	
	/**
	 * Metodo para notificar que las monedas a mostrar por la GUI han cambiado
	 * @param monedas
	 */
	public void mostrarMonedas(int monedas, String nick);
	
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano);
}
