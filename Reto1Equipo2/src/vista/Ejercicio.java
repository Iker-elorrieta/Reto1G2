package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controlador.Controlador;
import modelo.Ejercicios;
import modelo.HiloCronometro;
import modelo.HiloTemporizador;
import modelo.Historico;
import modelo.Series;
import modelo.Usuario;
import modelo.Workouts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Ejercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private HiloTemporizador hiloTiempo;
	private HiloTemporizador hiloDescanso;
	private HiloCronometro hiloCronoTotal;
	private String estadoEjercicio = "inactivo";
	private int tiempoRestante = 0;
	private int SerieActual = 1;
	private Series serieActual;
	private ArrayList<Series> series;

	public Ejercicio(Ejercicios ejercicio, Usuario usuarioActual, Controlador ctr, Workouts workout) {

		series = ejercicio.getSeries();
		serieActual = series.getFirst();

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
		btnSalir.setBackground(new Color(255, 79, 83));
		btnSalir.addActionListener(e -> {
			if (estadoEjercicio.equals("finalizado")) {
				crearYEnviarHistorico(ejercicio, usuarioActual, workout, ctr);
			}
			Inicio frame1 = new Inicio(ctr, usuarioActual);
			frame1.setVisible(true);
			dispose();

		});
		btnSalir.setBounds(583, 698, 258, 40);
		contentPane.add(btnSalir);

		JLabel lblDuracion = new JLabel("Duracion:");
		lblDuracion.setBounds(25, 140, 250, 23);
		contentPane.add(lblDuracion);

		JLabel lblCrono = new JLabel("0:00:00", SwingConstants.CENTER);
		lblCrono.setFont(new Font("Tahoma", Font.PLAIN, 80));
		lblCrono.setBounds(358, 249, 669, 164);
		contentPane.add(lblCrono);

		JLabel lblDescanso = new JLabel("0:00:00", SwingConstants.CENTER);
		lblDescanso.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblDescanso.setBounds(358, 442, 669, 40);
		contentPane.add(lblDescanso);

		JLabel lblSerie = new JLabel("Serie : " + SerieActual);
		lblSerie.setBounds(25, 174, 250, 14);
		contentPane.add(lblSerie);

		JButton btnInicio = new JButton("Iniciar");
		btnInicio.setBackground(new Color(118, 249, 85));
		btnInicio.setBounds(676, 542, 149, 93);
		contentPane.add(btnInicio);

		JButton btnParar = new JButton("Parar");
		btnParar.setBounds(583, 542, 83, 93);
		contentPane.add(btnParar);

		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estadoEjercicio.equals("inactivo")) {
					btnInicio.setBackground(Color.ORANGE);

					// Solo iniciar el cronómetro total si aún no existe
					if (hiloCronoTotal == null) {
						hiloCronoTotal = new HiloCronometro();
						hiloCronoTotal.start();
					}

					hiloTiempo = new HiloTemporizador(serieActual.getDuracion());
					hiloTiempo.start();

					estadoEjercicio = "activo";
				}
			}
		});

		btnParar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (estadoEjercicio.equals("activo")) {
					if (hiloTiempo != null && hiloTiempo.getDuracion() > 0) {
						tiempoRestante = hiloTiempo.getDuracion();
						hiloTiempo.detener();

						btnParar.setBackground(Color.GREEN);
						btnParar.setText("Reanudar");
						estadoEjercicio = "pausado";
					} else {
						if (hiloTiempo != null) hiloTiempo.detener();

						hiloDescanso = new HiloTemporizador(serieActual.getDescanso());
						hiloDescanso.start();

						btnParar.setEnabled(false);
						btnInicio.setEnabled(false);
						estadoEjercicio = "descanso";
					}
				} else if (estadoEjercicio.equals("pausado")) {
					int duracion = tiempoRestante > 0 ? tiempoRestante : serieActual.getDuracion();
					hiloTiempo = new HiloTemporizador(duracion);
					hiloTiempo.start();

					btnParar.setBackground(Color.ORANGE);
					btnParar.setText("Parar");
					estadoEjercicio = "activo";
			 }
			}
		});

		Timer timer = new Timer(200, e -> {
			if (hiloCronoTotal != null) {
				lblDuracion.setText("Duración: " + formatoReloj(hiloCronoTotal.getSegundos()));
			}
			if (hiloTiempo != null) {
				lblCrono.setText(formatoReloj(hiloTiempo.getDuracion()));
			}
			if (hiloDescanso != null) {
				lblDescanso.setText(formatoReloj(hiloDescanso.getDuracion()));
			}

			if (estadoEjercicio.equals("activo") && hiloTiempo.getDuracion() == 0) {
				estadoEjercicio = "descanso";
				hiloDescanso = new HiloTemporizador(serieActual.getDescanso());
				hiloDescanso.start();
				hiloTiempo = null;

				btnParar.setEnabled(false);
				btnInicio.setEnabled(false);
			}

			if (estadoEjercicio.equals("descanso") && hiloDescanso.getDuracion() == 0) {
				hiloDescanso = null;
				SerieActual++;

				if (SerieActual > serieActual.getRepeticiones()) {
					if (hiloCronoTotal != null) hiloCronoTotal.detener();
					btnParar.setEnabled(false);
					btnInicio.setEnabled(false);
					btnParar.setText("Completado");
					estadoEjercicio = "finalizado";
				} else {
					lblSerie.setText("Serie : " + SerieActual);
					estadoEjercicio = "inactivo";
					btnParar.setEnabled(true);
					btnInicio.setEnabled(true);
				}
			}
		});
		timer.start();
	}

	private void crearYEnviarHistorico(Ejercicios ejercicio, Usuario usuarioActual, Workouts workout, Controlador ctr) {
		if (hiloCronoTotal != null) {
			int tiempoReal = hiloCronoTotal.getSegundos();
			int tiempoEsperado = ejercicio.getTiempoEsperado();
			int ratio = 0;

			if (tiempoEsperado > 0) {
				ratio = (tiempoReal * 100) / tiempoEsperado;
			}

			Historico historico = new Historico(
				com.google.cloud.Timestamp.now(),
				workout.getNivel(),
				ratio,
				tiempoReal,
				tiempoEsperado,
				usuarioActual.getId(),
				String.valueOf(workout.getId()),
				workout.getNombre()
			);
			System.out.println(historico);
			ctr.guardarHistorico(historico);
		}
	}
	
	private String formatoReloj(int segundosTotales) {
		int horas = segundosTotales / 3600;
		int minutos = (segundosTotales % 3600) / 60;
		int segundos = segundosTotales % 60;
		return String.format("%02d:%02d:%02d", horas, minutos, segundos); 
		// porcentaje para decir que es un formato, 02 para indicar que son dos numeros y la d de digitos
	}
}