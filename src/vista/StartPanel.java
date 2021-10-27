package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Cliente.EstadoCliente;
import Cliente.TraductorCliente;
import Servidor.ProtocoloComunicacion;

public class StartPanel extends JPanel {
	/*****ATRIBUTOS******/
	private MainWindow mainWindow;
	private Image fondo;
	
	/******CONSTRUCTOR*****/
	public StartPanel(MainWindow mainWindow) {
		this.mainWindow = mainWindow;		
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();		
		this.initGUI();
	}
	
	/***********METODOS********/
	private void initGUI() {
		
		setLayout(null);

		JButton multijugador = new JButton("MULTIJUGADOR");
		multijugador.setBounds(250, 345, 220, 75);
		multijugador.setFont(new Font("Arial", Font.PLAIN, 20));
		/*multijugador.setIcon(new ImageIcon("img/btnmultijugador.png"));
		multijugador.setOpaque(false);
		multijugador.setContentAreaFilled(false);
		multijugador.setBorderPainted(false);*/
		add(multijugador);
		
		
		JButton salir = new JButton("SALIR");
		salir.setBounds(250,430,220, 75);
		salir.setFont(new Font("Arial", Font.PLAIN, 20));
		/*salir.setIcon(new ImageIcon("img/btnsalir.png"));
		salir.setOpaque(false);
		salir.setContentAreaFilled(false);
		salir.setBorderPainted(false);*/
		add(salir);		
		
		
		multijugador.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainWindow.setVista(EstadoCliente.LOGIN);
			}
		});
		
		salir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TraductorCliente.getTraductor().LogoutRequest();
				System.exit(0);
			}
		});
	
	}
	
}
