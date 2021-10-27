package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Cliente.EstadoCliente;
import CommandLobby.CommandLogin;
import Servidor.LobbyObserver;
import controlador.Controller;
import controlador.ControllerLobby;


public class LoginPanel extends JPanel implements LobbyObserver<String>{
	
	private static final long serialVersionUID = 1L;
	
	private ControllerLobby<String> c;
	private MainWindow u;
	private JTextField nombreJugador;
	private Image fondo;
	
	private JLabel nombreLabel;
	private JLabel icon;
	private JButton login;
	
	public LoginPanel(ControllerLobby<String> c,MainWindow u) {
		this.c = c;
		this.u = u;
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();
		this.init();
		c.addLobbyObserver(this);
	}
	
	private void init() {
		
		this.setLayout(new BorderLayout());
		this.setOpaque(true);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.white);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		
		int w = 200, h = 200;
		
		JPanel l1 = new JPanel();
		JPanel l2 = new JPanel();
		JPanel l3 = new JPanel();
		JPanel l4 = new JPanel();
		
		l1.setOpaque(false);
		l2.setOpaque(false);
		l3.setOpaque(false);
		l4.setOpaque(false);

		l1.setPreferredSize(new Dimension(h,w));
		l2.setPreferredSize(new Dimension(h,w));
		l3.setPreferredSize(new Dimension(h,w));
		l4.setPreferredSize(new Dimension(h,w));
		
		this.add(l1, BorderLayout.PAGE_START);
		this.add(l2, BorderLayout.PAGE_END);
		this.add(l3, BorderLayout.LINE_START);
		this.add(l4, BorderLayout.LINE_END);
		this.add(mainPanel, BorderLayout.CENTER);
		
		// - - - - - - Panel superior - - - - - - - - - - -
		
		JPanel superior = new JPanel();
		superior.setLayout(new FlowLayout(FlowLayout.CENTER));
		superior.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		
		this.nombreLabel = new JLabel("Introduzca su nombre:");
		nombreLabel.setFont(new Font("Arial", Font.BOLD, 20));
		nombreLabel.setForeground(Color.black);
	
		this.nombreJugador = new JTextField("Jugador");
		nombreJugador.setFont(new Font("Arial", Font.BOLD, 15));
		nombreJugador.setPreferredSize(new Dimension(120, 40));
		nombreJugador.setEditable(true);

		superior.add(this.nombreLabel);
		superior.add(Box.createRigidArea(new Dimension(20,20)));
		superior.add(this.nombreJugador);
		
		// - - - - - - - Panel inferior - - - - - - - - - -
		
		JPanel inferior = new JPanel();
		inferior.setLayout(new FlowLayout(FlowLayout.CENTER));
		inferior.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
		
		this.login = new JButton("LOGIN");
	
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				c.executeCommandLobby(new CommandLogin(nombreJugador.getText()));
			}
		});		
	
		
		inferior.add(login);
		inferior.add(Box.createRigidArea(new Dimension(20,20)));
		
		// - - - - - - - - - - - - - - - - -
		
		this.icon = new JLabel(new ImageIcon("Dibujos/iconoLogin.png"));
		this.icon.setMaximumSize(new Dimension(40,40));
		
		mainPanel.add(superior, BorderLayout.PAGE_START);
		mainPanel.add(this.icon, BorderLayout.CENTER);
		mainPanel.add(inferior, BorderLayout.PAGE_END);
	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.fondo,0,0,this.getParent().getWidth(),this.getParent().getHeight(),this);
	}

	@Override
	public void loginCorrect(String j) {
		u.setVista(EstadoCliente.LOBBY);
	}
	@Override
	public void InfoRequest(String j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refresh(List<String> j, String creador) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start_game() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void Error(String j, String error) {
		this.u.mostrar(error);
	}

	@Override
	public void registerOn(Controller c) {
		// TODO Auto-generated method stub
		
	}


}
