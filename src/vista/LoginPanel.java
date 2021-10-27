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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Cliente.TraductorCliente;


public class LoginPanel extends JPanel {
	
	private MainWindow MainWindow;
	private JTextField nombreJugador;
	private Image fondo;
	private static int numJugadores = 1;
	
	private JLabel nombreLabel;
	private JLabel icon;
	private JButton login;
	private JButton returnB;
	
	public LoginPanel(MainWindow MainWindow) {
		this.MainWindow = MainWindow;
		fondo = new ImageIcon("Dibujos/fondo.jpg").getImage();
		this.init();
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
	
		this.nombreJugador = new JTextField("Jugador"+ numJugadores++);
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
				TraductorCliente.getTraductor().LoginRequest(nombreJugador.getText().replaceAll("\\s",""));
			}
		});		
	
		
		this.returnB = new JButton("RETURN");
		
		returnB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				MainWindow.setVista((Cliente.EstadoCliente.START));
			}
		});
		
		inferior.add(login);
		inferior.add(Box.createRigidArea(new Dimension(20,20)));
		inferior.add(returnB);
		
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

}
