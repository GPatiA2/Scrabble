package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Cliente.TraductorCliente;

public class LobbyPanel extends JPanel {
	
	private MainWindow MainWindow;
	private Image fondo;
	private JPanel listaJugadores;
	private JLabel creadorLabel;
	private JList<String> lista;
	
	public LobbyPanel(MainWindow MainWindow) {
		this.MainWindow = MainWindow;
		lista = new JList<String>();
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();
		this.init();
	}
	
	private void init() {
		this.setLayout(null);
		
		//Label jugadores
		listaJugadores = new JPanel();
		listaJugadores.setBounds(200,250,400, 300);
		listaJugadores.setBackground(Color.white);
		listaJugadores.add(this.lista);
		this.add(listaJugadores);
		
		JLabel nombreLabel = new JLabel("ESPERANDO JUGADORES ...");
		nombreLabel.setForeground(Color.white);
		nombreLabel.setBackground(Color.white);
		nombreLabel.setBounds(200, 150, 300, 30);
		nombreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(nombreLabel);
		
		creadorLabel= new JLabel("CREADOR :");
		creadorLabel.setForeground(Color.white);
		creadorLabel.setBackground(Color.white);
		creadorLabel.setBounds(200, 190, 300, 30);
		creadorLabel.setFont(new Font("Arial", Font.BOLD, 20));
		this.add(creadorLabel);
		
		JButton start = new JButton("START GAME");
		start.setBounds(200, 650, 150, 40);
		this.add(start);		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				TraductorCliente.getTraductor().gameRequest();
			}
		});
		
		JButton returnB = new JButton("RETURN");
		returnB.setBounds(400, 650, 150, 40);
		this.add(returnB);
		
		returnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				MainWindow.setVista((Cliente.EstadoCliente.START));
			}
		});
		TraductorCliente.getTraductor().RefreshRequest();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("OEOPFO");
		g.drawImage(this.fondo,0,0,this.getParent().getWidth(),this.getParent().getHeight(),this);
	}

	public void mostrarLista(String jugadoreslista) {
		
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		
		String[] lista = jugadoreslista.split(" ");
		boolean creador = false;
		for(String l : lista ) {
			if (l.equals("creador")){
				creador = true;
			}
			else if(!l.equals("invitado")) {

				if (creador) {
					this.creadorLabel.setText("CREADOR : "+l);
					creador = false;
				}
				modelo.addElement(l);
			}
			
		}
		
		this.lista.setModel(modelo);
	
	}

}
