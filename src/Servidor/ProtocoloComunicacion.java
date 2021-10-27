package Servidor;
/**
 * Clase final con las constantes necesarias para la comuncacion entre
 * el cliente y el servidor
 *
 */
public final class ProtocoloComunicacion {
	
	// Login constants

	public static final String LOGIN_REQUEST = "[LOGIN_REQUEST]";
	public static final String LOGOUT_REQUEST = "[LOGOUT_REQUEST]";
	public static final String LOGOUT_CORRECT = "[LOGOUT_CORRECT]";
	
	//Refresh
	
	public static final String REFRESH = "[REFRESH]";
	
	//Numero de jugadores
	
	public static final String INFO_REQUEST = "[INFO_REQUEST]";
	
	// Game
	
	public static final String GAME_REQUEST = "[GAME_REQUEST]";
	
	public static final String ANIADIR_FICHA_MANO = "[ANIADIR_FICHA_MANO]";
	public static final String COLOCAR_FICHA = "[COLOCAR_FICHA]";	
	public static final String ACTUALIZAR_CASILLA = "[ACTUALIZAR_CASILLA]";
	public static final String BORRAR_FICHA_MANO = "[BORRAR_FICHA_MANO]";
	public static final String PASAR_TURNO = "[PASAR_TURNO]";
	public static final String QUITAR_FICHA_TABLERO = "[QUITAR_FICHA_TABLERO]";
	public static final String SALTAR_JUGADOR = "[SALTAR_JUGADOR]";
	public static final String CAMBIAR_FICHA = "[CAMBIAR_FICHA]";
	public static final String COMPRAR_COMODIN = "COMPRAR_COMODIN";
	public static final String INVERTIR_SERNTIDO = "[INVETIR_SENTIDO]";
	public static final String MOSTRAR_TURNOS = "[MOSTRAR_TURNOS]";
	public static final String MOSTRAR_PUNTOS = "[MOSTRAR_PUNTOS]";
	public static final String MOSTRAR_MONEDAS = "[MOSTRAR_MONEDAS]";
	public static final String ON_REGISTER = "[ON_REGISTER]";
	
	//CARGAR-GUARDAR
	public static final String LOAD = "[LOAD_PARTIDA]";
	public static final String SAVE = "[SAVE]";
	
	public static final String ERROR = "[ERROR]";
	public static final String INSTRUCCIONES = "[INTRUCCIONES]";
	
	
}