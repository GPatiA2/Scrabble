package Servidor;
/**
 * Clase final con las constantes necesarias para la comuncacion entre
 * el cliente y el servidor
 *
 */
public final class ProtocoloComunicacion {
	
	public static final String SOCKET_SERVIDOR = "[SOCKET_SERVIDOR]";
	public static final String SERVIDOR_INFO = "[SERVIDOR_INFO]";
	
	// Login constants

	public static final String LOGIN_REQUEST = "[LOGIN_REQUEST]";
	public static final String LOGIN_CORRECTO = "[LOGIN_CORRECTO]";
	public static final String LOGIN_ERROR = "[LOGIN_ERROR]";
	public static final String LOGOUT_REQUEST = "[LOGOUT_REQUEST]";
	public static final String LOGOUT_CORRECT = "[LOGOUT_CORRECT]";
	
	//Lobby
	public static final String LOBBY_FULL = "[LOBBY_FULL]";
	
	//Refresh
	
	public static final String REFRESH_JUGADORES_request = "[REFRESH_JUGADORES_request]";
	public static final String REFRESH_JUGADORES_SUCCESS = "[REFRESH_JUGADORES_success]";
	
	//Numero de jugadores
	
	public static final String NUM_JUGADORES = "[NUM_JUGADORES]";
	public static final String NUM_JUGADORES_REQUEST = "[NUM_JUGADORES_REQUEST]";
	public static final String NUM_JUGADORES_ACCEPT = "[NUM_JUGADORES_ACCEPT]";
	
	//IAs Dificultad

	public static final String DIFICULTAD_MAQUINAS_REQUEST = "[DIFICULTAD_MAQUINAS_REQUEST]"; 
	public static final String DIFICULTAD_MAQUINAS = "[DIFICULTAD_MAQUINAS]";
	// Game
	
	public static final String GAME_REQUEST = "[GAME_REQUEST]";
	public static final String GAME_START = "[GAME_START]";
	public static final String GAME_START_ERROR = "[GAME_START_ERROR]";
	public static final String ANIADIR_FICHA_MANO = "[ANIADIR_FICHA_MANO]";
	public static final String COLOCAR_FICHA = "[COLOCAR_FICHA]";
	public static final String ANIADIR_TABLERO_ERROR = "[ANIADIR_FICHA_MANO_ERROR]";	
	public static final String ACTUALIZAR_CASILLA = "[ACTUALIZAR_CASILLA]";
	public static final String BORRAR_FICHA_MANO = "[BORRAR_FICHA_MANO]";
	public static final String PASAR_TURNO = "[PASAR_TURNO]";
	public static final String QUITAR_FICHA_TABLERO = "[QUITAR_FICHA_TABLERO]";
	public static final String SALTAR_JUGADOR = "[SALTAR_JUGADOR]";
	public static final String CAMBIAR_FICHA = "[CAMBIAR_FICHA]";
	public static final String COMPRAR_COMODIN = "COMPRAR_COMODIN";
	public static final String INSTRUCCIONES = "[INSTRUCCIONES]";
	public static final String INVERTIR_SERNTIDO = "[INVETIR_SENTIDO]";
	public static final String MOSTRAR_TURNOS = "[MOSTRAR_TURNOS]";
	public static final String MOSTRAR_PUNTOS = "[MOSTRAR_PUNTOS]";
	public static final String MOSTRAR_MONEDAS = "[MOSTRAR_MONEDAS]";
	public static final String ON_REGISTER = "[ON_REGISTER]";
	
	//CARGAR-GUARDAR
	public static final String LOAD = "[LOAD_PARTIDA]";
	public static final String GUARDAR_PARTIDA = "[GUARDA_PARTIDA]";
	
	public static final String ERROR = "[ERROR]";
	
	
}