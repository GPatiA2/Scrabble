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
		return "Durante la partida cuentas con 4 botones a la derecha de tu mano, cada botón está asociado a una determinada acción" + 
				"\r\n" + "\r\n" + 
				"Cambiar ficha :"+ 
				"\r\n" + 
				"Puede cambiar hasta 7 fichas de tu mano por otras del mazo, esta acción se puede hacer cada vez que sea tu turno." + 
				"\r\n" + "\r\n" + 
				"Pasar turno :" +
				"\r\n" +
				"	Das por finalizado tu turno y pasaría a jugar el siguiente jugador." + 
				"\r\n" + "\r\n" + 
				"Guardar partida :" +
				"\r\n" + 
				"	Permite guardar la partida durante tu turno pudiendo darle el nombre que quieras a esta" + 
				"\r\n" + "\r\n" + 
				"Comprar ventajas :" +
				"\r\n" + 
				"Puedes comprar ventajas usando monedas, estas ventajas van desde la posibilidad de saltar el turno de un jugador hasta poder comprar fichas comodín" + 
				"\r\n" + "\r\n" + 
				"Si mantienes el raton encima de un botón se te mostrara la acción que realiza este." +
				"";
		}
}
