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
        	    "Fecha", "Nivel", "Ratio", "Tiempo", "Tiempo Esperado", "Usuario", "Workout Nombre"
        	};
        String[][] datos = new String[historico.size()][8];

        for (int i = 0; i < historico.size(); i++) {
        	
            Historico h = historico.get(i);
            if(h.getUsuario().getEmail().equals(usuarioActual.getEmail())) {
            datos[i][0] = h.getFecha().toString(); // o formateado si prefieres
            datos[i][1] = String.valueOf(h.getNivel());
            datos[i][2] = String.valueOf(h.getRatiocompletacion());
            datos[i][3] = String.valueOf(h.getTiempo());
            datos[i][4] = String.valueOf(h.getTiempoesperado());
            datos[i][5] = h.getUsuario().getNombre();
            datos[i][6] = h.getWorkout().getNombre();
            }
        }
        
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // para que no se pueda editar
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