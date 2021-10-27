package vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;


public class LogoBoton extends JButton {

	private GamePanel parent;
	private DialogoCreditos dialog;
	
	public LogoBoton(GamePanel p) {
		initGUI();
	}
	
	private void initGUI() {
		this.setIcon(new ImageIcon("Dibujos/logo.png"));
		setPreferredSize(new Dimension(130,50));
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		
		this.dialog = new DialogoCreditos(parent);
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//dialog.setVisible(true);
			}
			
		});
	}
}
