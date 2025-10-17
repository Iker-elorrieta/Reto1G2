package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
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

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsuario;
	private JTextField txtContraseña;

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1015, 728);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(93, 93, 93));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 999, 689);
		contentPane.add(panel_1);
		
		ImageIcon iconoLogo = new ImageIcon("lib/logo.png");
		Image imagenEscalada = iconoLogo.getImage().getScaledInstance(254, 203, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		ImageIcon iconoFondo = new ImageIcon("lib/fondo.png");
		panel_1.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(353, 145, 293, 361);
		panel_1.add(panel);
		panel.setBackground(new Color(128, 128, 128));
		panel.setLayout(null);

		txtUsuario = new JTextField("Usuario");
		txtUsuario.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtUsuario.setBounds(49, 52, 199, 38);
		txtUsuario.setBackground(new Color(192, 192, 192));
		txtUsuario.setColumns(10);
		txtUsuario.setForeground(Color.GRAY);
		panel.add(txtUsuario);

		// Lógica del placeholder
		txtUsuario.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtUsuario.getText().equals("Usuario")) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtUsuario.getText().isEmpty()) {
					txtUsuario.setText("Usuario");
					txtUsuario.setForeground(Color.GRAY);
				}
			}
		});

		txtContraseña = new JTextField("Contraseña");
		txtContraseña.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtContraseña.setBounds(49, 138, 199, 38);
		txtContraseña.setBackground(Color.LIGHT_GRAY);
		txtContraseña.setColumns(10);
		txtContraseña.setForeground(Color.GRAY);
		panel.add(txtContraseña);

		// Lógica del placeholder
		txtContraseña.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtContraseña.getText().equals("Contraseña")) {
					txtContraseña.setText("");
					txtContraseña.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtContraseña.getText().isEmpty()) {
					txtContraseña.setText("Contraseña");
					txtContraseña.setForeground(Color.GRAY);
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

		JLabel etiquetaImagen = new JLabel(iconoEscalado);
		etiquetaImagen.setBounds(29, 43, 254, 203);
		panel_1.add(etiquetaImagen);
		
		JLabel etiquetaFondo = new JLabel(iconoFondo);
		etiquetaFondo.setBounds(0, 0, 999, 689);
		panel_1.add(etiquetaFondo);

		lblCrearCuenta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Registro frame = new Registro();
				frame.setVisible(true);
				dispose();
			}
		});

	}
}
