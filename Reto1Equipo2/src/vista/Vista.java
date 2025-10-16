package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Vista extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtContraseña;


	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1015, 728);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(93, 93, 93));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("INICIO DE SESIÓN");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
		lblNewLabel.setBounds(299, 76, 423, 91);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(128, 128, 128));
		panel.setBounds(338, 208, 293, 361);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtUsuario = new JTextField("Usuario"); // ← Establece el texto inicial
		txtUsuario.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtUsuario.setBounds(49, 52, 199, 38);
		txtUsuario.setBackground(new Color(192, 192, 192));
		txtUsuario.setColumns(10);
		txtUsuario.setForeground(Color.GRAY); // ← Color gris para el placeholder
		panel.add(txtUsuario);

		// Lógica del placeholder
		txtUsuario.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtUsuario.getText().equals("Usuario")) {
		            txtUsuario.setText("");
		            txtUsuario.setForeground(Color.BLACK); // ← Color normal al escribir
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtUsuario.getText().isEmpty()) {
		            txtUsuario.setText("Usuario");
		            txtUsuario.setForeground(Color.GRAY); // ← Vuelve al color gris
		        }
		    }
		});

		
		
		txtContraseña = new JTextField("Contraseña"); // ← Texto inicial
		txtContraseña.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtContraseña.setBounds(49, 138, 199, 38);
		txtContraseña.setBackground(Color.LIGHT_GRAY);
		txtContraseña.setColumns(10);
		txtContraseña.setForeground(Color.GRAY); // ← Color gris para el placeholder
		panel.add(txtContraseña);

		// Lógica del placeholder
		txtContraseña.addFocusListener(new FocusAdapter() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (txtContraseña.getText().equals("Contraseña")) {
		            txtContraseña.setText("");
		            txtContraseña.setForeground(Color.BLACK); // ← Color normal al escribir
		        }
		    }

		    @Override
		    public void focusLost(FocusEvent e) {
		        if (txtContraseña.getText().isEmpty()) {
		            txtContraseña.setText("Contraseña");
		            txtContraseña.setForeground(Color.GRAY); // ← Vuelve al color gris
		        }
		    }
		});

		
		JButton btnNewButton = new JButton("INGRESAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(90, 223, 122, 38);
		panel.add(btnNewButton);
		
		JLabel lblCrearCuenta = new JLabel("Crear cuenta");
		lblCrearCuenta.setFont(new Font("Arial Black", Font.BOLD, 13));
		lblCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCrearCuenta.setBounds(98, 296, 102, 14);
		panel.add(lblCrearCuenta);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(64, 46, 497, 584);
		contentPane.add(panel_1);
		// Cargar la imagen desde archivo local
		ImageIcon icono = new ImageIcon("lib/logo.png"); // ← Cambia esta ruta

		// Crear un JLabel con la imagen
		JLabel etiquetaImagen = new JLabel(icono);

		// Ajustar el tamaño y posición dentro del panel
		etiquetaImagen.setBounds(0, 0, icono.getIconWidth(), icono.getIconHeight());
		panel_1.setLayout(null); // ← Permite posicionamiento manual
		panel_1.add(etiquetaImagen);

		
		lblCrearCuenta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
			}
		});

	}
}
