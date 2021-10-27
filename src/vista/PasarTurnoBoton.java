package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Command.ComandoPasarTurno;
import Excepciones.CommandExecuteException;
import controlador.Registrador;

public class PasarTurnoBoton extends JButton implements MouseListener{
	
	private Registrador c;
	
	public PasarTurnoBoton(Registrador c) {
		this.c = c;
		initGUI();
	}
	
	private void initGUI() {
		this.setIcon(new ImageIcon("Dibujos/Pasar.png"));
		this.setToolTipText("Permite pasar turno");
		this.setPreferredSize(new Dimension(50,50));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(GamePanel.getEnable()) {
					// TODO Auto-generated method stub
					try {
						c.runCommand(new ComandoPasarTurno());
	
					} catch (FileNotFoundException | CommandExecuteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
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
		this.setIcon(new ImageIcon("Dibujos/Pasar_g.png"));
		this.updateUI();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Pasar.png"));
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
