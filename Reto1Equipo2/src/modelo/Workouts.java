package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Workouts implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private int nivel;
	private String nombre;
	private String videoURL;
	private String descripcion;
	private ArrayList<Ejercicios> ejercicios;

	
	public ArrayList<Ejercicios> getEjercicios() {
		return ejercicios;
	}
	public void setEjercicios(ArrayList<Ejercicios> ejercicios) {
		this.ejercicios = ejercicios;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getVideoURL() {
		return videoURL;
	}
	public void setVideoURL(String videoURL) {
		this.videoURL = videoURL;
	}
	
	public Workouts(String id, int nivel, String nombre, String videoURL, String descripcion, ArrayList <Ejercicios> ejercicios) {
		super();
		this.id = id;
		this.nivel = nivel;
		this.nombre = nombre;
		this.videoURL = videoURL;
		this.descripcion = descripcion;
		this.ejercicios = ejercicios;
	}
	@Override
	public String toString() {
	    return "\n===== Workout =====\n" +
	           "Nivel:        " + nivel + "\n" +
	           "Nombre:       " + nombre + "\n" +
	           "Video URL:    " + videoURL + "\n" +
	           "Descripción:  " + descripcion + "\n" +
	           "Ejercicios:   " + (ejercicios != null ? ejercicios.size() : 0) + " ejercicios\n";
	}

	
	

}