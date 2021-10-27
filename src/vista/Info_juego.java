package vista;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Info_juego extends JFrame {


	private static final long serialVersionUID = 1L;


	public Info_juego (Object info){
		super ("INFORMACION DEL JUEGO");
		JPanel p = new JPanel ();
		JLabel l = new JLabel ();
		JOptionPane.showMessageDialog(null, info());
		l.setVisible(true);
		p.add(l);
		p.setVisible(true);
	}


	public String info (){
		return "1. Descripci�n b�sica \n "
				+ "El juego consiste en formar palabras de dos o m�s letras, y colocarlas en el tablero, "
				+ "horizontal o verticalmente, de manera que puedan ser le�das de izquierda a derecha o "
				+ "de arriba hacia abajo. \n\n" +
				
				"2. Verificaci�n de fichas \n "
				+ "Los jugadores pueden verificar la cantidad  de las letras (97 fichas) antes de empezar"
				+ "cada una de los turnos; no se aceptar�n reclamos posteriores. \n\n"+
				
				"3. Turno de salida \n "
				+ "El turno de salida ser� definido por sorteo. \n\n" +
				
				"4. Comienzo de la partida \n "
				+"Al comenzar la partida, el primer jugador retira siete (7) fichas de la bolsa. Luego "
				+ "combina dos o m�s de sus letras para	formar una palabra, y la coloca en el tablero \n"
				+ "horizontal o verticalmente. Est� obligado a poner una de las letras que forman su "
				+ "palabra en la casilla central (marcada con una estrella). \n\n "+
				
				"5. Colocaci�n de letras \n"
				+ "Todas las fichas jugadas en un turno deben colocarse en una l�nea horizontal o vertical "
				+ "y tienen que formar una palabra completa."+" Si esas fichas est�n en contacto con letras\n "
				+ "de filas o columnas contiguas, tambi�n con ellas deben formar palabras completas."
				+ " El jugador obtiene puntos por todas las nuevas palabras que forme durante su turno. \n\n"+
		
				"6. Comodines \n"
				+"El SCRUMBBLE�  incluye dos fichas en blanco, o comodines, los cuales puedenutilizarse "
				+ "en sustituci�n de cualquier letra. El jugador que coloca un comod�n debe indicar \n"+"la "
				+ "letra que este representa, lo que no puede modificarse durante la partida. "
				+ "Est� permitido usar los dos comodines en una misma palabra.\n" +"El premio de 50 puntos otorgado "
				+ "por utilizar las siete fichas del atril se otorga incluso utilizando los dos comodines."
				+ "El valor de los comodines es de cero (0) puntos \n"+" para calcular el valor de la(s) "
				+ "palabras(s) formada(s). \n\n" +
				
				"7. Recuento de Puntos \n"
				+"El recuento de puntos de una palabra se realiza sumando las puntuaciones de las letras "
				+ "que conforman la palabra  por la puntuacion de casilla (si la casilla fuera de doble/"
				+ "triple letra). \n"+"A continuaci�n se multiplicara el resultado por el valor acumulado de "
				+ "de las casillas doble/triple palabra \n\n" +
		
				"8. Cambio de fichas \n" 
				+"Durante el turno, el jugador podr� cambiar las fichas de su mano, de manera limitada "
				+ "a 7 veces maximo por turno. \n\n" +
				
				"9. Finalizaci�n de una partida \n"
				+ "La partida finaliza cuando : ningun jugador puede colocar fichas en el tablero."
				+ " Un jugador coloca todas sus fichas de su mano y no quedan m�s fichas en la bolsa. \n"
				+ "Todos los jugadores pasan dos veces seguidas el turno.";
}
				
	
	public static void main(String[] args) {
		Info_juego info = new Info_juego("");
		info.setDefaultCloseOperation(EXIT_ON_CLOSE);;
		info.pack();
		info.setVisible(true);
	}
}

