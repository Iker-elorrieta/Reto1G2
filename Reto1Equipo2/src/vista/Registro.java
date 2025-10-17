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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.google.api.client.util.DateTime;

import controlador.Controlador;

public class Registro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtContra;
    private JTextField txtMail;
    private JTextField txtFecha_nac;


    public Registro() {
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
        // Cargar la imagen desde archivo local
        ImageIcon iconoLogo = new ImageIcon("lib/logo.png");
        Image imagenEscalada = iconoLogo.getImage().getScaledInstance(254, 203, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
        ImageIcon iconoFondo = new ImageIcon("lib/fondo.png");
        Image imagenEscalada2 = iconoFondo.getImage().getScaledInstance(1354, 761, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado2 = new ImageIcon(imagenEscalada2);
        panel_1.setLayout(null); // ← Permite posicionamiento manual

        JPanel panel = new JPanel();
        panel.setBounds(592, 271, 293, 465);
        panel_1.add(panel);
        panel.setBackground(new Color(110, 44, 44));
        panel.setLayout(null);


        txtNombre = new JTextField(); // ← Establece el texto inicial
        txtNombre.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtNombre.setBounds(49, 73, 199, 22);
        txtNombre.setBackground(new Color(242, 242, 242));
        txtNombre.setColumns(10);
        panel.add(txtNombre);

            
        txtApellidos = new JTextField(); // ← Texto inicial
        txtApellidos.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtApellidos.setBounds(49, 139, 199, 22);
        txtApellidos.setBackground(new Color(176, 176, 176));
        txtApellidos.setColumns(10);
        txtApellidos.setForeground(Color.GRAY); // ← Color gris para el placeholder
        panel.add(txtApellidos);
            
        
        txtContra = new JTextField();
        txtContra.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtContra.setColumns(10);
        txtContra.setBackground(new Color(176, 176, 176));
        txtContra.setBounds(49, 205, 199, 22);
        panel.add(txtContra);
        
        JLabel lblNewLabel = new JLabel("NOMBRE");
        lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        lblNewLabel.setForeground(new Color(255, 255, 255));
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
        
        JLabel lblFehcaDeNacimiento = new JLabel("FECHA DE NACIMIENTO");
        lblFehcaDeNacimiento.setForeground(Color.WHITE);
        lblFehcaDeNacimiento.setFont(new Font("Arial Black", Font.BOLD, 15));
        lblFehcaDeNacimiento.setBounds(49, 304, 222, 22);
        panel.add(lblFehcaDeNacimiento);
        
        txtFecha_nac = new JTextField();
        txtFecha_nac.setFont(new Font("Arial Black", Font.PLAIN, 14));
        txtFecha_nac.setColumns(10);
        txtFecha_nac.setBackground(new Color(176, 176, 176));
        txtFecha_nac.setBounds(49, 337, 199, 22);
        panel.add(txtFecha_nac);

        JLabel etiquetaImagen = new JLabel(iconoEscalado);
        etiquetaImagen.setBounds(611, 28, 254, 203);
        panel_1.add(etiquetaImagen);
        
        JLabel etiquetaFondo = new JLabel(iconoEscalado2);
        etiquetaFondo.setBounds(0, 0, 1354, 761);
        panel_1.add(etiquetaFondo);

        
        
        JButton btnNewButton = new JButton("REGISTRAR");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
            	String fechaStr = txtFecha_nac.getText();
            	Date fechaNac = null;
				try {
					fechaNac = sdf.parse(fechaStr);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            	Controlador.RegistrarUsuario(txtNombre.getText(), txtApellidos.getText(), txtContra.getText(), txtMail.getText(), fechaNac);
            }

			
        });
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setFont(new Font("Arial Black", Font.BOLD, 13));
        btnNewButton.setBackground(new Color(192, 57, 43));
        btnNewButton.setBounds(89, 393, 122, 38);
        panel.add(btnNewButton);
        
    }
}