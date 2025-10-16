package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		
		txtUsuario = new JTextField();
		txtUsuario.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtUsuario.setBounds(49, 52, 199, 38);
		panel.add(txtUsuario);
		txtUsuario.setBackground(new Color(192, 192, 192));
		txtUsuario.setColumns(10);
		
		txtContraseña = new JTextField();
		txtContraseña.setFont(new Font("Arial Black", Font.PLAIN, 14));
		txtContraseña.setBounds(49, 138, 199, 38);
		panel.add(txtContraseña);
		txtContraseña.setColumns(10);
		txtContraseña.setBackground(Color.LIGHT_GRAY);
		
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
		
		JLabel lblNewLabel_1 = new JLabel("Crear cuenta");
		lblNewLabel_1.setFont(new Font("Arial Black", Font.BOLD, 13));
		lblNewLabel_1.setBounds(104, 297, 102, 14);
		panel.add(lblNewLabel_1);

	}
}
