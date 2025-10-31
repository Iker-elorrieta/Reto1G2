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
import java.awt.Color;

public class Ejercicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private HiloTemporizador hiloTiempo;
	private HiloTemporizador hiloDescanso;
	private HiloCronometro hiloCronoTotal;
	private String estadoEjercicio = "inactivo";
	// almacena tiempo restante cuando se pausa el temporizador principal
	private int tiempoRestante = 0;
	private int SerieActual = 0;
	private Series serieActual;
	private ArrayList<Series> series;
	private int serie = 1;

	public Ejercicio(Ejercicios ejercicio, Usuario usuarioActual, Controlador ctr) {

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
			Inicio frame1 = new Inicio(ctr, usuarioActual);
			frame1.setVisible(true);
			dispose();
		});
		btnSalir.setBounds(603, 698, 222, 40);
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

		JLabel lblNewLabel = new JLabel("Serie : " + SerieActual);
		lblNewLabel.setBounds(25, 174, 250, 14);
		contentPane.add(lblNewLabel);

		JButton btnParar = new JButton("");
		btnParar.setBackground(new Color(118, 249, 85));
		btnParar.setForeground(new Color(0, 128, 0));
		btnParar.setBounds(603, 542, 222, 93);
		contentPane.add(btnParar);		
		
		btnParar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(estadoEjercicio.equals("inactivo")) {
		    		btnParar.setBackground(Color.orange);
		    		hiloCronoTotal = new HiloCronometro();
		            hiloCronoTotal.start();
		    	}
		        /*if (estadoEjercicio.equals("inactivo")) {
		            // Iniciar ejercicio
		            hiloTiempo = new HiloTemporizador(serieActual.getDuracion());
		            hiloTiempo.start();
		            btnParar.setBackground(Color.orange);
		            hiloCrono = new HiloCronometro();
		            hiloCrono.start();

		            btnParar.setText("Parar");
		            estadoEjercicio = "activo";
		        } else if (estadoEjercicio.equals("activo")) {
		            // Si el temporizador del ejercicio todavía tiene tiempo, entonces solo pausarlo
		            if (hiloTiempo != null && hiloTiempo.getDuracion() > 0) {
		                // guardar tiempo restante y detener el hilo
		                tiempoRestante = hiloTiempo.getDuracion();
		                hiloTiempo.detener();
			            btnParar.setBackground(Color.GREEN);

		                btnParar.setText("Reanudar");
		                estadoEjercicio = "pausado";
		            } else {
		                // Si el tiempo ya llegó a 0, iniciar descanso
		                if (hiloTiempo != null) {
		                    hiloTiempo.detener();
		                }

		                hiloDescanso = new HiloTemporizador(serieActual.getDescanso());
		                hiloDescanso.start();

		                btnParar.setText("Reanudar");
		                estadoEjercicio = "descanso";
		            }
		        } else if (estadoEjercicio.equals("descanso") || estadoEjercicio.equals("pausado")) {
		            // Reanudar ejercicio: si venimos de descanso, usar la duracion completa;
		            // si venimos de pausa, usar el tiempo restante guardado
		            if (estadoEjercicio.equals("descanso")) {
		                if (hiloDescanso != null) {
		                    hiloDescanso.detener();
		                }

		                hiloTiempo = new HiloTemporizador(serieActual.getDuracion());
		                hiloTiempo.start();
		            } else { // pausa
		                // usar tiempoRestante si está disponible, sino reiniciar
		            	int dur;
		            	if (tiempoRestante > 0) {
		            	    dur = tiempoRestante;
		            	} else {
		            	    dur = serieActual.getDuracion();
		            	}
		            	hiloTiempo = new HiloTemporizador(dur);
		                hiloTiempo.start();
		                

		            btnParar.setText("Parar");
		            estadoEjercicio = "activo";
		        }
		            serie++;
		            if (serie >= serieActual.getRepeticiones()) {
		                // finalizar ejercicio
		                if (hiloTiempo != null) hiloTiempo.detener();
		                if (hiloCrono != null) hiloCrono.detener();
		                btnParar.setEnabled(false);
		                btnParar.setText("Completado");
		                estadoEjercicio = "finalizado";
		                return;
		            }

		            // avanzar a la siguiente serie
		            lblNewLabel.setText("Serie : " + serie);
		            tiempoRestante = 0;
	            }*/
		    }
		});

		Timer timer = new Timer(200, e -> {
		    if (hiloCronoTotal != null)
		        lblDuracion.setText("Duración: " + formatoReloj(hiloCronoTotal.getSegundos()));
		    if (hiloTiempo != null)
		        lblCrono.setText(formatoReloj(hiloTiempo.getDuracion()));
		    if (hiloDescanso != null)
		        lblDescanso.setText(formatoReloj(hiloDescanso.getDuracion()));
		});
		timer.start();

		System.out.println(ejercicio);
		

	}
	
	private String formatoReloj(int segundosTotales) {
	    int horas = segundosTotales / 3600;
	    int minutos = (segundosTotales % 3600) / 60;
	    int segundos = segundosTotales % 60;
	    return String.format("%02d:%02d:%02d", horas, minutos, segundos);// % indica que es un marcador de formato, 02 indica que debe tener al menos 2 dígitos, d indica que es un número entero
	}
}