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

import Command.ComandoCambiarFicha;
import Excepciones.CommandExecuteException;
import controlador.Controller;

public class CambiarFichaBoton extends JButton implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	private Controller c;
	
	public CambiarFichaBoton(Controller c) {
		this.c = c;
		initGUI();
	}
	
	private void initGUI() {
		this.setIcon(new ImageIcon("Dibujos/Swap.png"));
		this.setToolTipText("Permite cambiar fichas de tu mano por otras del mazo");
		this.setPreferredSize(new Dimension(50,50));
		
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(GamePanel.getEnable()) {
					// TODO Auto-generated method stub
					//Se pide la ficha para cambiar
					String ficha = JOptionPane.showInputDialog("¿Que ficha quieres cambiar?");
					if(ficha.length() != 1) {
						//Si se introduce algo que no es una ficha
						JOptionPane.showMessageDialog(null, "Debes introducir una sola letra para cambiar");
					}
					else {
						//Se extrae el caracter indicado por el usuario
						char f = ficha.charAt(0);
						if(Character.isAlphabetic(f)) {
							//Si es una letra, se cambia la ficha
							try {
								c.runCommand(new ComandoCambiarFicha(f));
							} catch (FileNotFoundException | CommandExecuteException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}						
						}
						else {
							//Si no, se indica el error
							JOptionPane.showMessageDialog(null, "Lo que introduzcas debe ser una letra");
						}
					}
				}//GamePanel.getEnable
				
			}
			
		});
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Swap_g.png"));
		this.updateUI();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Swap.png"));
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
