package vista;

import java.awt.BorderLayout;

import javax.swing.JDialog;

public class DialogoCreditos extends JDialog{

	private static final String title = "Creditos";
	private static final String info = "Grupo 2�A, aplicaci�n creada para IS2 por: ";
	private static final String grupo[] = {"Tania Romero Segura", "David Cruza Sesmero", "Guillermo Garcia Pati�o-Lenza", "Maria Cristina Alameda Salas", "Gema Blanco Nu�ez", "Alejandro Rivera Le�n"};
	
	public DialogoCreditos(GamePanel p) {
		this.setLocationRelativeTo(p);
		initGUI();
	}
	
	private void initGUI() {	
		//wip
	}
}
