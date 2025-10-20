package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import modelo.Usuario;
import modelo.Workouts;
import java.awt.Color;
import java.awt.Desktop;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private ArrayList<Workouts> workouts = new ArrayList<Workouts>();
	/**
	 * Create the frame.
	 * @param ctr 
	 */
	public Inicio(Controlador ctr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1370, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel PanelEjercicios = new JPanel();
		PanelEjercicios.setBackground(Color.GRAY);
		PanelEjercicios.setBounds(317, 160, 1038, 601);
		contentPane.add(PanelEjercicios);
		PanelEjercicios.setLayout(null);

		JPanel PanelWorkouts = new JPanel();
		PanelWorkouts.setBackground(Color.LIGHT_GRAY);
		PanelWorkouts.setBounds(0, 0, 317, 761);
		contentPane.add(PanelWorkouts);
		PanelWorkouts.setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(-28, 158, 345, 2);
		separator.setBackground(new Color(0, 0, 0));
		PanelWorkouts.add(separator);

		JComboBox NivelCB = new JComboBox();
		NivelCB.setModel(new DefaultComboBoxModel(new String[] { "Nivel 0", "Nivel 1", "Nivel 2", "Nivel 3" }));
		NivelCB.setBounds(65, 83, 182, 22);
		PanelWorkouts.add(NivelCB);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 158, 316, 603);
		PanelWorkouts.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel PanelUsuario = new JPanel();
		PanelUsuario.setBackground(Color.DARK_GRAY);
		PanelUsuario.setBounds(317, 0, 1038, 161);
		contentPane.add(PanelUsuario);
		PanelUsuario.setLayout(null);

		JLabel lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblUsuario.setBounds(814, 58, 190, 29);
		PanelUsuario.add(lblUsuario);
		setResizable(false);

		Usuario usuario = new Usuario("Ibai", "", "", "", null);
		lblUsuario.setText(usuario.getNombre());

		JProgressBar nivelWorkout = new JProgressBar();
		nivelWorkout.setValue(50);
		nivelWorkout.setForeground(new Color(69, 217, 26));
		nivelWorkout.setBounds(814, 103, 190, 20);
		PanelUsuario.add(nivelWorkout);

		table.setVisible(true);

		scrollPane.setViewportView(table);


		workouts = ctr.DevolverWorkouts();
		
		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Workouts" }, 0);
		for (Workouts workout : workouts) {
			Object[] fila = { workout.getNombre()};
			modelo.addRow(fila);
			table.setModel(modelo);
		}

		table.getSelectionModel().addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        int filaSeleccionada = table.getSelectedRow();
		        if (filaSeleccionada >= 0) {
		            String nombreWorkout = (String) table.getValueAt(filaSeleccionada, 0);

		            Workouts seleccionado = workouts.stream()
		                .filter(w -> w.getNombre().equals(nombreWorkout))
		                .findFirst()
		                .orElse(null);

		            if (seleccionado != null) {
		                actualizarEjercicios(PanelEjercicios,seleccionado);
		            }
		        }
		    }
		});

		
	}
	
	public void actualizarEjercicios(JPanel PanelEjercicios, Workouts seleccionado) {
	    PanelEjercicios.removeAll();
	    PanelEjercicios.setLayout(null);

	    int y = 60;

	    JLabel lblNombre = new JLabel("Nombre: " + seleccionado.getNombre());
	    lblNombre.setBounds(20, y, 300, 20);
	    PanelEjercicios.add(lblNombre);

	    JLabel lblNivel = new JLabel("Nivel: " + seleccionado.getNivel());
	    lblNivel.setBounds(40, y + 20, 200, 20);
	    lblNivel.setForeground(Color.WHITE);
	    PanelEjercicios.add(lblNivel);

	    JLabel lblVideo = new JLabel("<html><u>Ver video</u></html>");
	    lblVideo.setBounds(40, y + 40, 300, 20);
	    lblVideo.setForeground(Color.BLUE);
	    lblVideo.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            try {
	                Desktop.getDesktop().browse(new URI(seleccionado.getVideoURL()));
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                JOptionPane.showMessageDialog(null, "No se pudo abrir el enlace.");
	            }
	        }
	    });

	    PanelEjercicios.add(lblVideo);

	    
	    JLabel lblDescripcion= new JLabel("Descripcion: " + seleccionado.getDescripcion());
	    lblDescripcion.setBounds(40, 120, 574, 20);
	    lblDescripcion.setForeground(Color.WHITE);
	    PanelEjercicios.add(lblDescripcion);

	    PanelEjercicios.revalidate();
	    PanelEjercicios.repaint();
	}


}