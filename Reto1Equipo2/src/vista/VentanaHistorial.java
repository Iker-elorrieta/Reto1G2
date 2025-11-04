package vista;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controlador.Controlador;
import modelo.Ejercicios;
import modelo.Historico;
import modelo.Usuario;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class VentanaHistorial extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ArrayList<Historico> historico;
    private JTable table;
    private JButton btnVolver;

    
    public VentanaHistorial(Usuario usuarioActual, Controlador ctr) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1370, 800);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(77, 53, 1192, 647);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        historico = ctr.DevolverHistorico2();
        String[] columnas = {
        	    "Fecha", "Nivel", "Ratio", "Tiempo", "Tiempo Esperado", "Usuario", "Workout Nombre", "Porcentaje de completado"
        	};
        ArrayList<String[]> filas = new ArrayList<>();

        for (Historico h : historico) {
            if (h.getUsuario().getEmail().equals(usuarioActual.getEmail())) {
                String[] fila = new String[8];
                fila[0] = h.getFecha().toString();
                fila[1] = String.valueOf(h.getNivel());
                fila[2] = String.valueOf(h.getRatiocompletacion());
                fila[3] = String.valueOf(h.getTiempo());
                fila[4] = String.valueOf(h.getTiempoesperado());
                fila[5] = h.getUsuario().getNombre();
                fila[6] = h.getWorkout().getNombre();
                fila[7] = h.getTiempoesperado() > 0 ? (h.getTiempo() * 100 / h.getTiempoesperado()) + "%" : "0%";
                filas.add(fila);
            }
        }

        String[][] datos = filas.toArray(new String[0][8]);
        
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        table.setModel(modelo);
        
        btnVolver = new JButton("Volver");
        btnVolver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		historico.clear();
        		Inicio inicio = new Inicio(ctr,usuarioActual);
        		inicio.setVisible(true);
        		dispose();
        		
     
        	}
        });
        btnVolver.setBounds(10, 11, 89, 23);
        contentPane.add(btnVolver);
        
        
        
        
        

    }
}