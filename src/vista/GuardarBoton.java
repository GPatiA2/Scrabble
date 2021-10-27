package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controlador.Controller;

public class GuardarBoton extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Controller c;
	
	public GuardarBoton(Controller c) {
		this.c = c;
		initGUI();
	}
	
	private void initGUI() {
		this.setIcon(new ImageIcon("Dibujos/Save.png"));
		this.setToolTipText("Permite guardar la partida");
		this.setPreferredSize(new Dimension(50,50));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
		
		this.addActionListener(new ActionListener() {
			
			private String fichero;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				do {
					this.fichero = JOptionPane.showInputDialog("¿Con que nombre se guardará la partida?");
				}
				while(fichero.equals(""));
				
				try {
					c.save(fichero);
					JOptionPane.showMessageDialog(null, "Partida guardada con exito en Saves/" + this.fichero);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(getParent(), "Nombre de fichero no valido, no se ha guardado la partida", "Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});	
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Save_g.png"));
		this.updateUI();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Save.png"));
		this.updateUI();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
