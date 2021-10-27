package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controlador.Registrador;

public class PanelTurnos extends JPanel {

	private JLabel turnoActlb;
	private JLabel turnoSiglb;
	private JLabel turnoAct;
	private JLabel turnoSig;
	
	public PanelTurnos() {
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new GridLayout(1,1));
		this.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));

		
		Border b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 2);
		Border b2 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		Border b3 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		Border b4 = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 1);
		
		this.setBorder(BorderFactory.createTitledBorder(b,"Turnos"));
		((javax.swing.border.TitledBorder)this.getBorder()).setTitleColor(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		// - - - - - - - - -
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); //contiene los componentes visuales de turno actual
		northPanel.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		northPanel.setBorder(b2);

		this.turnoAct = new JLabel("j1");
		this.turnoActlb = new JLabel("Turno actual:");
		this.turnoAct.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		this.turnoActlb.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));

		northPanel.add(this.turnoActlb);
		northPanel.add(Box.createRigidArea(new Dimension (27, 10)));
		northPanel.add(this.turnoAct);
		
		// - - - - - - - - -
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); //contiene los componentes visuales de turno siguiente	
		southPanel.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		southPanel.setBorder(b3);
		
		this.turnoSig = new JLabel("j2");
		this.turnoSiglb = new JLabel("Turno siguiente:");
		this.turnoSig.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		this.turnoSiglb.setForeground(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		southPanel.add(this.turnoSiglb);
		southPanel.add(Box.createRigidArea(new Dimension (10, 10)));
		southPanel.add(this.turnoSig);
	
		// - - - - - - - - -
		
		JPanel panelGeneral = new JPanel();
		panelGeneral.setLayout(new GridLayout(2,1));
		panelGeneral.setBorder(b4);
		
		panelGeneral.add(northPanel);
		panelGeneral.add(southPanel);
		
		this.add(panelGeneral);
	}
	
	public void setText(String act, String sig) {
		this.turnoAct.setText(act);
		this.turnoSig.setText(sig);
	}

	public String getActual() {
		return this.turnoAct.getText();
	}
	
}
