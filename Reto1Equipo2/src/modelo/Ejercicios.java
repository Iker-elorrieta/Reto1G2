package modelo;

import java.util.ArrayList;

public class Ejercicios {

	private String id;
	private String nombre;
	private String descripcion;
	private ArrayList<Series> series;
	
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
	
	public Ejercicios(String id, String nombre,String descripcion) {
		
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
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
	}
	@Override
	public String toString() {
		return "Ejercicios [id=" + id + ", nombre=" + nombre + "]";
	}

}

