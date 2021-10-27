package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controlador.Registrador;
import modelo.Ficha;
import modelo.Integrante;
import modelo.TManagerObserver;
import utils.Coordenadas;

public class GamePanel extends JPanel implements TManagerObserver {

	private Registrador c;
	private JPanel manoCont;
	private PanelMano mano;
	private PanelTablero tablero;
	private PanelJugadores jugadores;
	private static boolean enable;
	
	public GamePanel(Registrador c) {
		this.c = c;
		initGui();
		registerOn(c);
	}
	
	private void initGui() {
		this.setLayout(new BorderLayout());

		JPanel panelOpciones = new JPanel();
		JPanel botones = new JPanel();
		
		panelOpciones.setLayout(new GridLayout(1,2));
		botones.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		botones.setBackground(Color.getHSBColor(MainWindow.colorBordeTablero[0], MainWindow.colorBordeTablero[1], MainWindow.colorBordeTablero[2]));
		
		Border b = BorderFactory.createLineBorder(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]), 2);
		botones.setBorder(BorderFactory.createTitledBorder(b,"Opciones"));
		((javax.swing.border.TitledBorder)botones.getBorder()).setTitleColor(Color.getHSBColor(MainWindow.colorBorder[0], MainWindow.colorBorder[1], MainWindow.colorBorder[2]));
		
		botones.add(new CambiarFichaBoton(c));
		botones.add(new PasarTurnoBoton(c));
		botones.add(new GuardarBoton(c));
		botones.add(new ComprarBoton(c));
		botones.add(Box.createRigidArea(new Dimension(20,50)));
		botones.add(new LogoBoton(this));
		
		jugadores = new PanelJugadores(c);
		tablero = new PanelTablero(c);
		mano = new PanelMano(c);
		manoCont = new JPanel();
		manoCont.setLayout(new BorderLayout());
		
		manoCont.add(mano, BorderLayout.CENTER);
		
		panelOpciones.add(manoCont);
		panelOpciones.add(botones);
		
		this.add(this.jugadores, BorderLayout.PAGE_START);
		this.add(this.tablero, BorderLayout.CENTER);
		this.add(panelOpciones, BorderLayout.PAGE_END);

		this.setVisible(true);
	}
	
	
	public Coordenadas getcoordenadas(int x, int y) {
		return this.tablero.Localizacion(new Point (x,y));
	}


	public List<Ficha> getMano() {
		return this.mano.getMano();
	}

	@Override
	public void registerOn(Registrador c) {
		c.addTManagerObserver(this);
	}

	@Override
	public void mostrarTurnos(String act, String sig) {	}

	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {
		mano = null;
		mano = new PanelMano(c);
		manoCont.add(mano, BorderLayout.CENTER);
		manoCont.validate();
		manoCont.repaint();
		manoCont.updateUI();
	}

	@Override
	public void onError(String err,String nick) {
		
	}

	@Override
	public void onRegister(String act, String sig) {}

	@Override
	public void turnoAcabado() {
		c.removeJugadorObserver(mano);
		manoCont.removeAll();
		manoCont.updateUI();
		manoCont.validate();
		manoCont.repaint();
	}
	

	public static void setEnableGame(boolean e) {
		enable=e;
	}
	public static boolean getEnable() {
		return enable;
	}
}


