package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Usuario;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtContraseña;

	public Login(Controlador ctr) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1370, 800);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(93, 93, 93));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 1354, 761);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		ImageIcon iconoLogo = new ImageIcon("lib/logo.png");
		Image imagenEscalada = iconoLogo.getImage().getScaledInstance(254, 203, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);

		ImageIcon iconoFondo = new ImageIcon("lib/fondo.png");
		Image imagenEscalada2 = iconoFondo.getImage().getScaledInstance(1354, 761, Image.SCALE_SMOOTH);
		ImageIcon iconoEscalado2 = new ImageIcon(imagenEscalada2);

		JPanel panel = new JPanel();
		panel.setBounds(531, 270, 293, 361);
		panel_1.add(panel);
		panel.setBackground(new Color(110, 44, 44));
		panel.setLayout(null);

		txtEmail = new JTextField("Email");
		txtEmail.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtEmail.setBounds(47, 52, 199, 38);
		txtEmail.setBackground(new Color(192, 192, 192));
		txtEmail.setColumns(10);
		txtEmail.setForeground(Color.GRAY);
		panel.add(txtEmail);

		txtEmail.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtEmail.getText().equals("Email")) {
					txtEmail.setText("");
					txtEmail.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (txtEmail.getText().isEmpty()) {
					txtEmail.setText("Email");
					txtEmail.setForeground(Color.GRAY);
				}
			}
		});

		JButton btnNewButton = new JButton("INGRESAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean login = ctr.LoginUsuarios(txtEmail.getText(), txtContraseña.getPassword());

				if (login) {
					iniciarBackups();
					Usuario usuario = ctr.UsuarioIniciado(txtEmail.getText());
					Inicio frame = new Inicio(ctr, usuario);
					dispose();
					frame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void iniciarBackups() {
				try {
					ProcessBuilder pb = new ProcessBuilder("java", "-jar", "lib/Backup.jar");
					pb.redirectErrorStream(true);
					Process proceso = pb.start();

					try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
						String linea;
						while ((linea = br.readLine()) != null) {
							System.out.println("[BACKUP] " + linea);
						}
					}

					int codigoSalida = proceso.waitFor();
					System.out.println("El proceso de backup terminó con código: " + codigoSalida);

				} catch (Exception e) {
					System.out.println("Error al iniciar el proceso de backup: " + e.getMessage());
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(85, 221, 122, 38);
		panel.add(btnNewButton);

		JLabel lblCrearCuenta = new JLabel("Crear cuenta");
		lblCrearCuenta.setForeground(new Color(192, 192, 192));
		lblCrearCuenta.setBackground(new Color(192, 192, 192));
		lblCrearCuenta.setFont(new Font("Arial Black", Font.BOLD, 13));
		lblCrearCuenta.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblCrearCuenta.setBounds(95, 270, 102, 14);
		panel.add(lblCrearCuenta);

		txtContraseña = new JPasswordField();
		txtContraseña.setBounds(47, 141, 199, 38);
		panel.add(txtContraseña);

		JLabel etiquetaImagen = new JLabel(iconoEscalado);
		etiquetaImagen.setBounds(550, 26, 254, 203);
		panel_1.add(etiquetaImagen);

		JLabel etiquetaFondo = new JLabel(iconoEscalado2);
		etiquetaFondo.setBounds(0, 0, 1354, 761);
		panel_1.add(etiquetaFondo);

		lblCrearCuenta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Registro frame = new Registro(ctr);
				frame.setVisible(true);
				dispose();
			}
		});
	}
}