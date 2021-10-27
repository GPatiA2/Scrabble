package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import Cliente.EstadoCliente;

import CommandLobby.CommandInfoRequest;
import Servidor.LobbyObserver;
import controlador.Controller;
import controlador.ControllerLobby;

public class LobbyPanel extends JPanel implements LobbyObserver<String> {
	
	private static final long serialVersionUID = 1L;
	
	private MainWindow MainWindow;
	private ControllerLobby<String> c;
	private Image fondo;
	private JPanel listaJugadores;
	private JLabel creadorLabel;
	private JList<String> lista;
	
	public LobbyPanel(ControllerLobby<String> c, MainWindow MainWindow) {
		this.MainWindow = MainWindow;
		this.c = c;
		lista = new JList<String>();
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();		
		this.init();
		c.addLobbyObserver(this);
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
				c.gameRequest();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.fondo,0,0,this.getParent().getWidth(),this.getParent().getHeight(),this);
	}

	@Override
	public void loginCorrect(String j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InfoRequest(String j) {
		int max = this.MainWindow.PedirNumJugadores();
		String nivel = this.MainWindow.PedirDificultadMaquinas();
		this.c.executeCommandLobby(new CommandInfoRequest(max,nivel));
	}

	@Override
	public void refresh(List<String> j, String creador) {
		DefaultListModel<String> modelo = new DefaultListModel<String>();
		for(String i : j) {
			modelo.addElement(i);
		}
		this.lista.setModel(modelo);
		this.creadorLabel.setText("Creador: "+creador);
	}

	@Override
	public void start_game() {
		this.MainWindow.setVista(EstadoCliente.GAME);
	}


	@Override
	public void Error(String j, String error) {
		this.MainWindow.mostrar(error);
	}

	@Override
	public void registerOn(Controller c) {
		// TODO Auto-generated method stub
		
	}

}
