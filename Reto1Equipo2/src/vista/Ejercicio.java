package vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import modelo.Ejercicios;
import modelo.HiloCronometro;
import modelo.HiloTemporizador;
import modelo.Series;
import modelo.Usuario;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Ejercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private HiloTemporizador hiloTiempo;
	private HiloTemporizador hiloDescanso;
	private HiloCronometro hiloCrono;

	public Ejercicio(Ejercicios ejercicio, Usuario usuarioActual, Controlador ctr) {

		int SerieActual = 0;

		ArrayList<Series> series = ejercicio.getSeries();
		Series serieActual = series.get(SerieActual);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1370, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel txtEjercicio = new JLabel(ejercicio.getNombre(), SwingConstants.CENTER);
		txtEjercicio.setBounds(400, 11, 550, 40);
		contentPane.add(txtEjercicio);

		JLabel lblDescripcion = new JLabel(ejercicio.getDescripcion(), SwingConstants.CENTER);
		lblDescripcion.setBounds(261, 64, 800, 20);
		contentPane.add(lblDescripcion);

		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(e -> {
			Inicio frame1 = new Inicio(ctr, usuarioActual);
			frame1.setVisible(true);
			dispose();
		});
		btnSalir.setBounds(695, 727, 89, 23);
		contentPane.add(btnSalir);

		JLabel lblDuracion = new JLabel("Duracion:");
		lblDuracion.setBounds(10, 116, 250, 23);
		contentPane.add(lblDuracion);

		JLabel lblCrono = new JLabel("0:00:00", SwingConstants.CENTER);
		lblCrono.setFont(new Font("Tahoma", Font.PLAIN, 80));
		lblCrono.setBounds(358, 319, 669, 164);
		contentPane.add(lblCrono);

		JLabel lblDescanso = new JLabel("0:00:00", SwingConstants.CENTER);
		lblDescanso.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblDescanso.setBounds(358, 494, 669, 40);
		contentPane.add(lblDescanso);

		JLabel lblNewLabel = new JLabel("Series Restantes: " + SerieActual);
		lblNewLabel.setBounds(10, 150, 250, 14);
		contentPane.add(lblNewLabel);

		JButton btnParar = new JButton("Iniciar");
		btnParar.setBounds(565, 727, 89, 23);
		contentPane.add(btnParar);

		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnParar.getText().equals("Iniciar")) {
					hiloCrono = new HiloCronometro();
					hiloTiempo = new HiloTemporizador(serieActual.getDuracion());
					hiloDescanso = new HiloTemporizador(serieActual.getDescanso());

					hiloCrono.start();
					hiloTiempo.start();

					btnParar.setText("Parar");
				} else {
					if (lblDuracion.getText().equals("0")) {
						hiloDescanso = new HiloTemporizador(serieActual.getDescanso());
						hiloDescanso.start();
					}
					if (hiloTiempo != null)
						hiloTiempo.detener();
					if (hiloCrono != null)
						hiloCrono.detener();

					btnParar.setText("Iniciar");
				}
			}
		});

		Timer timer = new Timer(200, e -> {
			if (hiloCrono != null)
				lblDuracion.setText("Duracion: " + hiloCrono.getSegundos());
			if (hiloTiempo != null)
				lblCrono.setText(hiloTiempo.getDuracion() + "");
			if (hiloDescanso != null)
				lblDescanso.setText(hiloDescanso.getDuracion() + "");
		});
		timer.start();

		System.out.println(ejercicio);

	}
}
