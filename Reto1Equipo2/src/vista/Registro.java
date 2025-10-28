package vista;

import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;
import modelo.Usuario;

public class Registro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtMail;
	private JTextField txtFecha_nac;
	private JPasswordField txtContra;

	public Registro(final Controlador ctr) {

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
		panel.setBounds(531, 202, 293, 513);
		panel_1.add(panel);
		panel.setBackground(new Color(110, 44, 44));
		panel.setLayout(null);

		txtNombre = new JTextField();
		txtNombre.setForeground(new Color(55, 55, 55));
		txtNombre.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtNombre.setBounds(49, 73, 199, 22);
		txtNombre.setBackground(new Color(176, 176, 176));
		txtNombre.setColumns(10);
		panel.add(txtNombre);

		txtApellidos = new JTextField();
		txtApellidos.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtApellidos.setBounds(49, 139, 199, 22);
		txtApellidos.setBackground(new Color(176, 176, 176));
		txtApellidos.setColumns(10);
		txtApellidos.setForeground(new Color(55, 55, 55));
		panel.add(txtApellidos);

		JLabel lblNewLabel = new JLabel("NOMBRE");
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(49, 42, 162, 22);
		panel.add(lblNewLabel);

		JLabel lblApellidos = new JLabel("APELLIDOS");
		lblApellidos.setForeground(Color.WHITE);
		lblApellidos.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblApellidos.setBounds(49, 106, 162, 22);
		panel.add(lblApellidos);

		JLabel lblContrasea = new JLabel("CONTRASEÑA");
		lblContrasea.setForeground(Color.WHITE);
		lblContrasea.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblContrasea.setBounds(49, 172, 162, 22);
		panel.add(lblContrasea);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setForeground(Color.WHITE);
		lblEmail.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblEmail.setBounds(49, 238, 162, 22);
		panel.add(lblEmail);

		txtMail = new JTextField();
		txtMail.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtMail.setColumns(10);
		txtMail.setBackground(new Color(176, 176, 176));
		txtMail.setBounds(49, 271, 199, 22);
		panel.add(txtMail);

		JLabel lblFehcaDeNacimiento = new JLabel("FECHA DE NACIMIENTO ");
		lblFehcaDeNacimiento.setForeground(Color.WHITE);
		lblFehcaDeNacimiento.setFont(new Font("Arial Black", Font.BOLD, 15));
		lblFehcaDeNacimiento.setBounds(49, 304, 222, 38);
		panel.add(lblFehcaDeNacimiento);

		txtFecha_nac = new JTextField();
		txtFecha_nac.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtFecha_nac.setColumns(10);
		txtFecha_nac.setBackground(new Color(176, 176, 176));
		txtFecha_nac.setBounds(49, 368, 199, 22);
		panel.add(txtFecha_nac);

		JLabel etiquetaImagen = new JLabel(iconoEscalado);
		etiquetaImagen.setBounds(550, 11, 254, 175);
		panel_1.add(etiquetaImagen);

		JLabel etiquetaFondo = new JLabel(iconoEscalado2);
		etiquetaFondo.setBounds(0, 0, 1354, 761);
		panel_1.add(etiquetaFondo);

		JButton btnNewButton = new JButton("REGISTRAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				final String fechaStr = txtFecha_nac.getText();
				final String nombre = txtNombre.getText();
				final String apellidos = txtApellidos.getText();
				final String clave = new String(txtContra.getPassword());
				final String email = txtMail.getText();

				if (nombre.equals("") || apellidos.equals("") || clave.equals("") || email.equals("")
						|| fechaStr.equals("")) {
					JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", null,
							JOptionPane.WARNING_MESSAGE);
				} else {
					boolean existe = ctr.verificarEmail(email);
					if (existe) {
						JOptionPane.showMessageDialog(null, "Este email ya está registrado", null,
								JOptionPane.WARNING_MESSAGE);
						txtMail.setText("");
					} else {
						try {
							final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							sdf.setLenient(false);
							final Date fechaNacimiento = sdf.parse(fechaStr);
							final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
							final int añoNacimiento = Integer.parseInt(sdfYear.format(fechaNacimiento));
							final int añoActual = Integer.parseInt(sdfYear.format(new Date()));

							if (añoNacimiento > añoActual) {
								JOptionPane.showMessageDialog(null, "El año de nacimiento no puede ser mayor al actual",
										null, JOptionPane.ERROR_MESSAGE);
								return;
							}

							final Usuario usuario = new Usuario(nombre, apellidos, clave, email, fechaNacimiento, 0);
							ctr.RegistrarUsuarioBDControlador(usuario);
							JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
							Login frame = new Login(ctr);
							frame.setVisible(true);
							dispose();
						} catch (ParseException ex) {
							JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Usa yyyy/MM/dd", null,
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 13));
		btnNewButton.setBackground(new Color(192, 192, 192));
		btnNewButton.setBounds(85, 412, 122, 38);
		panel.add(btnNewButton);

		JLabel lblNewLabel_1 = new JLabel("(yyyy/MM/dd)");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(49, 335, 122, 22);
		panel.add(lblNewLabel_1);

		JLabel lblVolver = new JLabel("Volver al login");
		lblVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblVolver.setBackground(new Color(192, 192, 192));
		lblVolver.setForeground(new Color(192, 192, 192));
		lblVolver.setFont(new Font("Arial Black", Font.BOLD, 13));
		lblVolver.setBounds(89, 460, 115, 22);
		lblVolver.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent e) {
				Login frame = new Login(ctr);
				frame.setVisible(true);
				dispose();
			}
		});
		panel.add(lblVolver);

		txtContra = new JPasswordField();
		txtContra.setBounds(49, 205, 199, 20);
		panel.add(txtContra);
	}
}