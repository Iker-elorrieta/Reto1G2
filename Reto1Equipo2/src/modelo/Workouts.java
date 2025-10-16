package modelo;

public class Workouts {
	
	private int id;
	private int nivel;
	private String nombre;
	private String videoURL;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	
	public Workouts(int id, int nivel, String nombre, String videoURL) {
		super();
		this.id = id;
		this.nivel = nivel;
		this.nombre = nombre;
		this.videoURL = videoURL;
	}
	@Override
	public String toString() {
		return "Workouts [id=" + id + ", nivel=" + nivel + ", nombre=" + nombre + ", videoURL=" + videoURL + "]";
	}
	
	

}
