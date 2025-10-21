package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import controlador.Controlador;
import modelo.Ejercicios;
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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Inicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private ArrayList<Workouts> workouts = new ArrayList<Workouts>();
	DefaultTableModel modelo;

	public Inicio(Controlador ctr, Usuario usuarioActual) {

		DefaultTableModel modelo = new DefaultTableModel(new String[] { "Workouts" }, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		
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
		
		actualizarWorkout(modelo, usuarioActual.getNivel());
		
		JComboBox NivelCB = new JComboBox();
		NivelCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nivelSeleccionado = NivelCB.getSelectedItem().toString();
				if (nivelSeleccionado.equals("Nivel 0")) {

					actualizarWorkout(modelo, 0);

				}
				if (nivelSeleccionado.equals("Nivel 1")) {

					actualizarWorkout(modelo, 1);

				}
				if (nivelSeleccionado.equals("Nivel 2")) {

					actualizarWorkout(modelo, 2);

				}

			}
		});

		
		String[] niveles = new String[usuarioActual.getNivel() + 1];
		for (int i = 0; i <= usuarioActual.getNivel() ; i++) {
		    niveles[i] = "Nivel " + i;
		}

		NivelCB.setModel(new DefaultComboBoxModel<>(niveles));
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
		lblUsuario.setBounds(814, 30, 190, 29);
		PanelUsuario.add(lblUsuario);
		setResizable(false);

		lblUsuario.setText(usuarioActual.getNombre());

		JProgressBar nivelWorkout = new JProgressBar();
		nivelWorkout.setValue(50);
		nivelWorkout.setForeground(new Color(69, 217, 26));
		nivelWorkout.setBounds(814, 103, 190, 20);
		PanelUsuario.add(nivelWorkout);
		
		JLabel lblNivel = new JLabel("");
		lblNivel.setForeground(Color.WHITE);
		lblNivel.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblNivel.setBounds(814, 70, 190, 29);
		PanelUsuario.add(lblNivel);

		lblNivel.setText("Nivel: "+usuarioActual.getNivel());
		
		table.setVisible(true);

		scrollPane.setViewportView(table);

		workouts = ctr.DevolverWorkouts();

		for (Workouts workout : workouts) {
			Object[] fila = { workout.getNombre() };
			modelo.addRow(fila);
			table.setModel(modelo);
		}

		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int filaSeleccionada = table.getSelectedRow();
				if (filaSeleccionada >= 0) {
					String nombreWorkout = (String) table.getValueAt(filaSeleccionada, 0);

					Workouts seleccionado = workouts.stream().filter(w -> w.getNombre().equals(nombreWorkout))
							.findFirst().orElse(null);

					if (seleccionado != null) {
						actualizarEjercicios(PanelEjercicios, seleccionado);
					}
				}
			}
		});

	}

	public void actualizarWorkout(DefaultTableModel modelo, int nivel) {
		modelo.setRowCount(0);

		for (Workouts workout : workouts) {

			
			
			if (workout.getNivel() == nivel) {
				Object[] fila = { workout.getNombre() };
				modelo.addRow(fila);
				table.setModel(modelo);
			}
		}

	}

	public void actualizarEjercicios(JPanel PanelEjercicios, Workouts seleccionado) {
	    PanelEjercicios.removeAll();
	    PanelEjercicios.setLayout(null);

	    int y = 20;

	    // Info general del workout
	    JLabel lblNombre = new JLabel("Workout: " + seleccionado.getNombre());
	    lblNombre.setBounds(20, y, 400, 20);
	    lblNombre.setFont(new Font("Arial", Font.BOLD, 16));
	    lblNombre.setForeground(Color.WHITE);
	    PanelEjercicios.add(lblNombre);

	    JLabel lblNivel = new JLabel("Nivel: " + seleccionado.getNivel());
	    lblNivel.setBounds(20, y + 25, 200, 20);
	    lblNivel.setForeground(Color.WHITE);
	    PanelEjercicios.add(lblNivel);

	    JLabel lblDescripcion = new JLabel("Descripción: " + seleccionado.getDescripcion());
	    lblDescripcion.setBounds(20, y + 45, 600, 20);
	    lblDescripcion.setForeground(Color.WHITE);
	    PanelEjercicios.add(lblDescripcion);

	    JLabel lblVideo = new JLabel("<html><u>Ver video</u></html>");
	    lblVideo.setBounds(20, y + 70, 300, 20);
	    lblVideo.setForeground(Color.CYAN);
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

	    y += 110;

	    // Paneles por cada ejercicio
	    for (Ejercicios ejercicio : seleccionado.getEjercicios()) {
	        JPanel panelEjer = new JPanel();
	        panelEjer.setLayout(null);
	        panelEjer.setBackground(new Color(60, 60, 60));
	        panelEjer.setBounds(20, y, 980, 60);

	        JLabel lblEjerNombre = new JLabel("Ejercicio: " + ejercicio.getNombre());
	        lblEjerNombre.setBounds(10, 5, 400, 20);
	        lblEjerNombre.setForeground(Color.WHITE);
	        panelEjer.add(lblEjerNombre);

	        JLabel lblEjerDesc = new JLabel("Descripción: " + ejercicio.getDescripcion());
	        lblEjerDesc.setBounds(10, 25, 900, 20);
	        lblEjerDesc.setForeground(Color.LIGHT_GRAY);
	        panelEjer.add(lblEjerDesc);

	        PanelEjercicios.add(panelEjer);
	        y += 70;
	    }

	    PanelEjercicios.revalidate();
	    PanelEjercicios.repaint();
	}
}
