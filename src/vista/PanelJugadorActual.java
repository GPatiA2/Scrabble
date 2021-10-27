package vista;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class PanelJugadorActual extends JPanel {
	
	private JLabel nick;
	private JLabel nickArea;
	
	private JLabel puntuacion;
	private JLabel puntuacionArea;
	
	private JLabel monedas;
	private JLabel monedasArea;
	
	public PanelJugadorActual() {
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new GridLayout(1,1));
		this.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));

		
		Border b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 2);
		Border b2 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		Border b3 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		Border b4 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		
		this.setBorder(BorderFactory.createTitledBorder(b,"Jugador Actual"));
		((javax.swing.border.TitledBorder)this.getBorder()).setTitleColor(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		// - - - - - - - - -
		
		JPanel panelNick = new JPanel();
		panelNick.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNick.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		panelNick.setBorder(b2);
		
		this.nick = new JLabel("Nick:");
		this.nickArea = new JLabel("Jugador actual");
		this.nick.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		this.nickArea.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		panelNick.add(this.nick);
		panelNick.add(this.nickArea);
		
		// - - - - - - - - -
		
		JPanel panelPuntos = new JPanel();
		panelPuntos.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelPuntos.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		
		this.puntuacion = new JLabel("Puntuacion:");
		this.puntuacionArea = new JLabel("" + 0); 
		this.puntuacion.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		this.puntuacionArea.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		panelPuntos.add(this.puntuacion);
		panelPuntos.add(this.puntuacionArea);
		
		// - - - - - - - - -
		
		JPanel panelMonedas = new JPanel();
		panelMonedas.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelMonedas.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));

		this.monedas = new JLabel("Monedas:");
		this.monedasArea = new JLabel("" + 0); 
		this.monedas.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		this.monedasArea.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		panelMonedas.add(this.monedas);
		panelMonedas.add(this.monedasArea);
		
		// - - - - - - - - -
		
		JPanel panelPuntosMonedas = new JPanel(); //panel que contiene el panel de monedas y el panel de puntuacion
		panelPuntosMonedas.setLayout(new GridLayout(1,2));
		panelPuntosMonedas.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		panelPuntosMonedas.setBorder(b3);
		
		panelPuntosMonedas.add(panelPuntos);
		panelPuntosMonedas.add(panelMonedas);

		JPanel PanelGeneral = new JPanel(); //panel que contiene al panel nick y al panel puntosmonedas
		PanelGeneral.setLayout(new GridLayout(2,1));
		PanelGeneral.setBorder(b4);
		
		PanelGeneral.add(panelNick);
		PanelGeneral.add(panelPuntosMonedas);
		
		this.add(PanelGeneral);

	}
	

	public void mostrarPuntos(int puntos, String nick) {
		this.puntuacionArea.setText("" + puntos);
		this.nickArea.setText(nick);
		updateUI();
	}

	public void mostrarMonedas(int monedas) {
		this.monedasArea.setText("" + monedas);
		updateUI();
	}
	public String getNick() {
		
		return this.nickArea.getText();
	}
}
