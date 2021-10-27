package vista;

import java.awt.BorderLayout;

import javax.swing.JDialog;

public class DialogoCreditos extends JDialog{

	private static final String title = "Creditos";
	private static final String info = "Grupo 2ºA, aplicación creada para IS2 por: ";
	private static final String grupo[] = {"Tania Romero Segura", "David Cruza Sesmero", "Guillermo Garcia Patiño-Lenza", "Maria Cristina Alameda Salas", "Gema Blanco Nuñez", "Alejandro Rivera León"};
	
	public DialogoCreditos(GamePanel p) {
		this.setLocationRelativeTo(p);
		initGUI();
	}
	
	private void initGUI() {	
		//wip
	}
}
