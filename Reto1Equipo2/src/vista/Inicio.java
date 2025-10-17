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
import modelo.Usuario;
import modelo.Workouts;

import java.awt.Color;

import javax.swing.JSeparator;
import javax.swing.JLabel;
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
		NivelCB.setModel(new DefaultComboBoxModel(new String[] {"Nivel 0", "Nivel 1", "Nivel 2", "Nivel 3"}));
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
		
		Usuario usuario = new Usuario("Ibai","","","",null);
		lblUsuario.setText(usuario.getNombre());
		
		JProgressBar nivelWorkout = new JProgressBar();
		nivelWorkout.setValue(50);
		nivelWorkout.setForeground(new Color(69, 217, 26));
		nivelWorkout.setBounds(814, 103, 190, 20);
		PanelUsuario.add(nivelWorkout);

		table.setVisible(true); 
		
		scrollPane.setViewportView(table);

        		DefaultTableModel modelo = new DefaultTableModel(
        			    new String[] { "Workouts" }, 0
        			);
        		 for (Workouts workout : workouts) {
			            Object[] fila = {
			            		workout.getNombre(),
			            	
			            };
			            modelo.addRow(fila);
        table.setModel(modelo);
        
        
		

	}
        		 
        		 List<Workouts> listaWorkouts = new ArrayList<>();
        		 try {
        		     Firestore db = conexion.conectar();

        		     // Nombre de la colección
        		     String nombreColeccion = "WORKOUTS";

        		     // Obtener todos los documentos
        		     ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();

        		     // Capturamos todas las excepciones necesarias
        		     QuerySnapshot documentos = future.get(); // puede lanzar InterruptedException y ExecutionException

        		     for (QueryDocumentSnapshot doc : documentos) {
        		    	    String nombre = doc.getString("NOMBRE");
        		    	    int nivelInt = doc.getLong("NIVEL").intValue();
        		    	    String video = doc.getString("VIDEO");

        		    	    Workouts w = new Workouts(0,nivelInt,nombre,video);
        		    	   

        		    	    listaWorkouts.add(w);
        		    	  
        		     }
        		     
        		     // Cerrar la conexión
        		     try {
        		         db.close();
        		     } catch (Exception e) {
        		         e.printStackTrace();
        		     }

        		 } catch (IOException e) {
        		     e.printStackTrace();
        		 } catch (InterruptedException e) {
        		     e.printStackTrace();
        		 } catch (ExecutionException e) {
        		     e.printStackTrace();
        		 }
        	        

        	       
        	        int y = 60;

        	        for (Workouts w : listaWorkouts) {
        	            JLabel lblNombre = new JLabel("Nombre: " + w.getNombre());
        	            System.out.println("Nombre: " + w.getNombre()+"mantec");
        	            lblNombre.setBounds(20, y, 300, 20);
        	            PanelEjercicios.add(lblNombre);

        	            JLabel lblNivel = new JLabel("Nivel: " + w.getNivel());
        	            lblNivel.setBounds(40, y + 20, 200, 20);
        	            lblNivel.setForeground(Color.GRAY);
        	            PanelEjercicios.add(lblNivel);

        	            JLabel lblVideo = new JLabel("Video: " + w.getVideoURL());
        	            lblVideo.setBounds(40, y + 40, 300, 20);
        	            lblVideo.setForeground(Color.BLUE);
        	            PanelEjercicios.add(lblVideo);

        	            y += 70; // espacio entre cada workout
        	        }
}
	
	
	
}