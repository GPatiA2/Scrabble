package vista;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.Registrador;

public class CargarPartidaBoton extends JPanel{
	
	private Registrador c;
	private JButton boton;
	
	public CargarPartidaBoton(Registrador c) {
		this.c = c;
		initGUI();
	}
	
	private void initGUI() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		
		boton = new JButton("Cargar");
		boton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser fc = new JFileChooser();
					int sel = fc.showOpenDialog(p);
					if(sel == JFileChooser.APPROVE_OPTION) {
						c.load(fc.getSelectedFile());
					}
				} catch (IOException ex) { //Lanza excepcion si la partida no existe
					ex.printStackTrace();
				}	
			}
		});
		
		p.add(boton);
		this.add(p);
		
	}
}
