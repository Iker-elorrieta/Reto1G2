package modelo;

public class Ejercicios extends Workouts{

	private int id;
	private String nombre;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Ejercicios(int id, int nivel, String nombre, String videoURL, int id2, String descripcion, String nombre2) {
		super(id, nivel, nombre, videoURL, descripcion);
		id = id2;
		nombre = nombre2;
	}
	@Override
	public String toString() {
		return "Ejercicios [id=" + id + ", nombre=" + nombre + "]";
	}

}
