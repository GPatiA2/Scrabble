package vista;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Comandos {
	public Comandos (Object info){
		super ();
		JPanel p = new JPanel ();
		JLabel l = new JLabel ();
		JOptionPane.showMessageDialog(null, info());
		l.setVisible(true);
		p.add(l);
		p.setVisible(true);
	}


	public String info (){
		return "colocar(c) : colocar <ficha><coorX><corrY>\r\n" + 
				"	Este comando permite colocar una ficha en el tablero.\r\n" + 
				"\r\n" + 
				"quitar(q) : quitar <coorX><coorY>\r\n" + 
				"	Este comando permite quitar una ficha del tablero.\r\n" + 
				"\r\n" + 
				"salir(s) : Salir del juego\r\n" + 
				"	Este comando permite abandonar la partida.\r\n" + 
				"\r\n" + 
				"help(h) : Help\r\n" + 
				"	Este comando permite obtener una lista de comandos\r\n" + 
				"\r\n" + 
				"pasar(p) : Pasar de turno\r\n" + 
				"	Este comando permite que un jugador pase de turno.\r\n" + 
				"\r\n" + 
				"instrucciones(i) : Instrucciones\r\n" + 
				"	Este comando permite imprimir las instrucciones del juego\r\n" + 
				"\r\n" + 
				"Cambiar Ficha(sw) : Este comando te permite sustituir una de las fichas de tu mano por otra escogida al azar del mazo\r\n" + 
				"	 Sw / swap <letra>\r\n" + 
				"\r\n" + 
				"jump(j) : jump\r\n" + 
				"	Este comando permite que un jugador salte el turno del jugador siguiente a cambio de 5 monedas.\r\n" + 
				"\r\n" + 
				"comodin(cc) : comodin <letra>\r\n" + 
				"	Este comando permite comprar un comodin a cambio de monedas descartando una ficha.\r\n" + 
				"\r\n" + 
				"invertir(is) : invertir\r\n" + 
				"	Este comando permite invertir el orden de los turnos a cambio de 5 monedas.\r\n" + 
				"\r\n" + 
				"Guardar(g) : g <nombre de fichero>\r\n" + 
				"	Este comando permite guardar el estado de la partida en un fichero.\r\n" + 
				"";
		}
}
