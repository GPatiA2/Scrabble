package vista;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JPanel;
import controlador.Registrador;
import modelo.Ficha;
import modelo.Integrante;
import modelo.JugadorObserver;
import modelo.TManagerObserver;


public class PanelJugadores extends JPanel implements TManagerObserver,JugadorObserver {

	private Registrador c;
	
	private PanelTurnos panelTurnos;
	private PanelJugadorActual panelJugadorActual;
	
	public PanelJugadores(Registrador c) {
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
	public void registerOn(Registrador c) {
		c.addTManagerObserver(this);
		c.addJugadorObserver(this);
	}

	@Override
	public void nuevoTurno(Integrante i, String act, String sig) {
		// TODO Auto-generated method stub
		
		panelTurnos.setText(act, sig);
		panelJugadorActual.mostrarMonedas(i.getCoin());
		panelJugadorActual.mostrarPuntos(i.getScore(), i.getNick());
		
	}

	@Override
	public void onError(String err,String nick) {}

	@Override
	public void onRegister(String act, String sig) {
		// TODO Auto-generated method stub		
		panelTurnos.setText(act, sig);
	}

	@Override
	public void turnoAcabado() {
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

}
