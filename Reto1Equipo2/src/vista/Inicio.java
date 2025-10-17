package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;
import controlador.Controlador;
import modelo.Usuario;
import modelo.Workouts;

import java.awt.Color;

import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
	private Controlador controlador = new Controlador();
	private Conexion conexion = new Conexion();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Inicio() {
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


		workouts = controlador.DevolverWorkouts();
		
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

		

		System.out.println(workouts.size());

		
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
	    lblNivel.setForeground(Color.GRAY);
	    PanelEjercicios.add(lblNivel);

	    JLabel lblVideo = new JLabel("Video: " + seleccionado.getVideoURL());
	    lblVideo.setBounds(40, y + 40, 300, 20);
	    lblVideo.setForeground(Color.BLUE);
	    PanelEjercicios.add(lblVideo);

	    PanelEjercicios.revalidate();
	    PanelEjercicios.repaint();
	}


}