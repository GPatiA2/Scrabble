package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import controlador.Registrador;


public class ComprarBoton extends JButton implements MouseListener{
	
	private Registrador res;
	
	public ComprarBoton(Registrador res) {
		this.res = res;
		initGUI();
	}
	
	private void initGUI() {
		this.setIcon(new ImageIcon("Dibujos/Buy.png"));
		this.setPreferredSize(new Dimension(50,50));
		this.setToolTipText("Permite comprar ventajas por monedas");
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.addMouseListener(this);
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(GamePanel.getEnable()) {
					ComprarVentajasDialog dialogo = new ComprarVentajasDialog(res);
					dialogo.setLocationRelativeTo(null);
					
					dialogo.open(MainWindow.getMano());
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
		this.setIcon(new ImageIcon("Dibujos/Buy_g.png"));
		this.updateUI();
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		this.setIcon(new ImageIcon("Dibujos/Buy.png"));
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