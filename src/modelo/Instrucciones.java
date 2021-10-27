package modelo;

public class Instrucciones {
	
	private String Descripcion() {
		return "1. Descripción básica \n "
				+ "El juego consiste en formar palabras de dos o más letras, y colocarlas en el tablero, "
				+ "horizontal o verticalmente, de manera que puedan ser leídas de izquierda a derecha o "
				+ "de arriba hacia abajo. \n\n";
	}
	
	private String VerificacionFichas() {
		return "2. Verificación de fichas \n "
				+ "Los jugadores pueden verificar la cantidad  de las letras (97 fichas) antes de empezar"
				+ "cada una de los turnos; no se aceptarán reclamos posteriores. \n\n";				
	}
	
	private String TurnoSalida() {
		return "3. Turno de salida \n "
				+ "El turno de salida será definido por sorteo.";
	}
	
	private String ComienzoPartida() {
		return "4. Comienzo de la partida \n "
				+"Al comenzar la partida, el primer jugador retira siete (7) fichas de la bolsa. Luego "
				+ "combina dos o más de sus letras para	formar una palabra, y la coloca en el tablero "
				+ "horizontal o verticalmente. Está obligado a poner una de las letras que forman su "
				+ "palabra en la casilla central (marcada con una estrella). \n\n ";
	}
	
	private String ColocarFichas() {
		return "5. Colocación de letras \n"
				+ "Todas las fichas jugadas en un turno deben colocarse en una línea horizontal o vertical "
				+ "y tienen que formar una palabra completa. Si esas fichas están en contacto con letras "
				+ "de filas o columnas contiguas, también con ellas deben formar palabras completas. El  "
				+ "jugador obtiene puntos por todas las nuevas palabras que forme durante su turno. \n\n";
				
	}
	
	private String Comodines() {
		return "6. Comodines \n"
				+"El SCRUMBBLE® incluye dos fichas en blanco, o comodines, los cuales puedenutilizarse "
				+ "en sustitución de cualquier letra. El jugador que coloca un comodín debe indicar  la "
				+ "letra que este representa, lo que no puede modificarse durante la partida. Está "
				+ "permitido usar los dos comodines en una misma palabra. El premio de 50 puntos otorgado "
				+ "por utilizar las siete fichas del atril se otorga inclusoutilizando los dos comodines. "
				+ "El valor de los comodines es de cero (0) puntos para calcular el valor de la(s) "
				+ "palabras(s) formada(s). \n\n";
	}
	
	private String Puntuacion() {
		return "7. Recuento de Puntos \n"
				+"El recuento de puntos de una palabra se realiza sumando las puntuaciones de las letras "
				+ "que conforman la palabra  por la puntuacion de casilla (si la casilla fuera de doble/"
				+ "triple letra). A continuación se multiplicara el resultado por el valor acumulado de "
				+ "de las casillas doble/triple palabra";
	}
	
	private String CambioFichas() {
		return "8. Cambio de fichas \n" 
				+"Durante el turno, el jugador podrá cambiar las fichas de su mano, de manera limitada "
				+ "a 7 veces maximo por turno.";
	}
	
	private String Finalizar() {
		return "9. Finalización de una partida \n"
				+ "La partida finaliza cuando : ningun jugador puede colocar fichas en el tablero."
				+ "Un jugador coloca todas sus fichas de su mano y no quedan más fichas en la bolsa."
				+ "Todos los jugadores pasan dos veces seguidas el turno.";
	}
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(Descripcion());
		r.append(VerificacionFichas());
		r.append(TurnoSalida());
		r.append(ComienzoPartida());
		r.append(ColocarFichas());
		r.append(Comodines());
		r.append(Puntuacion());
		r.append(CambioFichas());
		return r.toString();
	}
	
}
