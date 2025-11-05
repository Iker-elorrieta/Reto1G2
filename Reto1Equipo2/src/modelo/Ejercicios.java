package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Ejercicios implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String descripcion;
	private ArrayList<Series> series;
	private int tiempoEsperado;

	public Ejercicios(String id, String nombre, String descripcion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.series = new ArrayList<>();
		this.tiempoEsperado = 0;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<Series> getSeries() {
		return series;
	}
	public void setSeries(ArrayList<Series> series) {
		this.series = series;
		calcularTiempoEsperado();
	}

	public int getTiempoEsperado() {
		return tiempoEsperado;
	}
	public void setTiempoEsperado(int tiempoEsperado) {
		this.tiempoEsperado = tiempoEsperado;
	}

	// Método para calcular el tiempo total esperado del ejercicio
	public void calcularTiempoEsperado() {
		int total = 0;
		if (series != null) {
			for (Series s : series) {
				total += s.getDuracion() * s.getRepeticiones();
			}
		}
		this.tiempoEsperado = total;
	}

	@Override
	public String toString() {
	    return "\n===== Ejercicio =====\n" +
	           "ID:           " + id + "\n" +
	           "Nombre:       " + nombre + "\n" +
	           "Descripción:  " + descripcion + "\n" +
	           "Series:       " + (series != null ? series.size() : 0) + " definidas\n";
	}
}