package vista;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import controlador.Controller;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.TManagerObserver;


public class PanelJugadores extends JPanel implements TManagerObserver,JugadorObserver {


	private static final long serialVersionUID = 1L;

	private Controller c;
	
	private PanelTurnos panelTurnos;
	private PanelJugadorActual panelJugadorActual;
	
	public PanelJugadores(Controller c) {
		this.c = c;
		initPanel();
		registerOn(this.c);
	}
	
	private void initPanel() {
		this.setLayout(new GridLayout(1,2));
		
		this.panelTurnos = new PanelTurnos(); //contiene todos los componentes de la visualizacion de turnos
		this.panelJugadorActual = new PanelJugadorActual();
		
		this.add(this.panelJugadorActual);
		this.add(this.panelTurnos);
	}
	


	@Override
	public void mostrarTurnos(String act, String sig) {		
		this.panelTurnos.setText(act,sig);
		if(act.equals(this.panelJugadorActual.getNick())) {
			GamePanel.setEnableGame(true);
		
		}
		else {
			GamePanel.setEnableGame(false);
		}
	}

	@Override
	public void registerOn(Controller c) {
		c.addTManagerObserver(this);
		c.addJugadorObserver(this, panelTurnos.getActual());
	}

	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {
		panelTurnos.setText(act, sig);
		panelJugadorActual.mostrarMonedas(i.getCoin());
		panelJugadorActual.mostrarPuntos(i.getScore(), i.getNick());
		if(i.getNick().equals(act)) {
			GamePanel.setEnableGame(true);
		
		}
		else {
			GamePanel.setEnableGame(false);
		}
	}

	@Override
	public void onError(String err,String nick) {}

	@Override
	public void onRegister(String act, String sig) {			
		panelTurnos.setText(act, sig);
	}

	@Override
	public void turnoAcabado(String j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fichaRobada(Ficha f, Integrante j) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fichaUsada(Ficha f, Integrante player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrarFichaMano(Ficha f, Integrante j, boolean bienColocada) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mostrarPuntos(int puntos, String nick) {
		this.panelJugadorActual.mostrarPuntos(puntos, nick);
	}

	@Override
	public void mostrarMonedas(int monedas, String nick) {
		this.panelJugadorActual.mostrarMonedas(monedas);
	}

	@Override
	public void onRegister(String nick, int puntos, int monedas, List<Ficha> mano) {
		
		this.panelJugadorActual.mostrarPuntos(puntos, nick);
		this.panelJugadorActual.mostrarMonedas(monedas);
		if(nick.equals(this.panelTurnos.getActual())) {
			GamePanel.setEnableGame(true);
		}
		else {
			GamePanel.setEnableGame(false);
		}
	}

	@Override
	public void partidaAcabada(String nick) {
		
	}

	public String getAct() {
		return panelTurnos.getActual();
	}
	
}
