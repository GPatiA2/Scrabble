package vista;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.Integrante;
import modelo.Jugador;


public class Tabla extends JDialog {
	

	private JTable table;
	private DefaultTableModel dtm;
	private static final long serialVersionUID = 1L;
	private List <Integrante> lista;
	
	public Tabla(Frame Owner) {
		super (Owner,true);
		
		lista = new ArrayList <Integrante>();
		
		JPanel panel = new JPanel ();
		panel.setLayout(new BoxLayout(panel,BoxLayout.LINE_AXIS));
		
		JPanel bP = new JPanel();
		bP.setLayout(new BoxLayout(bP,BoxLayout.PAGE_AXIS));

		Object[] data = new Object[2];
		
		// creamos el modelo de Tabla
		dtm= new DefaultTableModel();
		
		// se crea la Tabla con el modelo DefaultTableModel
		table = new JTable(dtm);
		
		
		// Inserto las columnas
		for(int column = 0; column < 2; column++){ 
		  if (column == 0)dtm.addColumn("Num_jugs ");
		  else dtm.addColumn("Nick");
		}
		
		
		for(int row = 0; row < 1; row++) {// Cambiar por 2 o 4 segun seleccione el jugador
				for(int column = 0; column < 1; column++) {
					data[column] = row+1;
				}
			dtm.addRow(data);
		}
		
		//Defino el tamaño de mi tabla
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		
		//Creamos un JscrollPane y le agregamos la JTable
		JScrollPane scrollPane = new JScrollPane(table);
		
		//Agregamos el JScrollPane al panel
		panel.add(scrollPane, BorderLayout.CENTER);
		
		
		
		//manejamos la salida
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	 
		// Este boton servira para guardar los jugadores que se incluyan en la tabla
		
		JButton guardar = new JButton ("GUARDAR");
		class guarda implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					//Añadir la funcionalidad de listJugadores
					generaLista();
					panel.setVisible(false);
					dispose();
			}

			private void generaLista() {
				// TODO Auto-generated method stub
				int i = 0;
				while (i < dtm.getRowCount()) {
					lista.add(new Jugador((String) dtm.getValueAt(i , 1)));
					System.out.println("NICK " + (String) dtm.getValueAt(i , 1));
					i++;
				}
			}
		}
		//
		
		JButton anadirJugador = new JButton ("AÑADIR JUGADOR");
		class anadir implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					int r = dtm.getRowCount() - 1;
					Object [] row = {dtm.getRowCount() + 1 + "",""};
					if (dtm.getRowCount() < 4) {
						dtm.addRow(row);
						dtm.fireTableRowsInserted(r, dtm.getRowCount()- 1);
					}
					else {
						JOptionPane.showMessageDialog(null, "NO  PUEDE AÑADIR MAS DE 4 JUGADORES","ERROR", JOptionPane.ERROR_MESSAGE);
					}
					
					//Añadir la funcionalidad de listJugadores
					// Panel.setVisible(false);
			}
		}
		
		JButton elimino = new JButton ("ELIMINAR");
		class eliminar implements ActionListener {
			public void actionPerformed(ActionEvent e) {
					int r = dtm.getRowCount() - 1;
					
					if (dtm.getRowCount() != 1) {
						dtm.removeRow(r);
						dtm.fireTableRowsDeleted(r, r);
					}
					else {
						JOptionPane.showMessageDialog(null, "NO SE PUEDEN ELIMINAR MAS JUGADORES","ERROR", JOptionPane.ERROR_MESSAGE);
					}
					
					//Añadir la funcionalidad de listJugadores
					// Panel.setVisible(false);
			}
		}
		
		// AÑADIR BOTON DE ANADIR MAQUINA
		
		elimino.addActionListener(new eliminar ());
		anadirJugador.addActionListener(new anadir());
		guardar.addActionListener(new guarda());
		
		bP.add(anadirJugador);
		bP.add(elimino);
		bP.add(guardar);
		
		panel.add(bP);
		//
		
		setContentPane(panel);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		panel.setVisible(true);
		
		}

	public List<Integrante> getList() {
		return lista;
	}
}

	
		
		
