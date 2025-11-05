package modelo;

import java.io.Serializable;

import com.google.cloud.Timestamp;


public class Historico implements Serializable {
	
    private static final long serialVersionUID = 1L;
	private Timestamp fecha;
	private int nivel;
	private int ratiocompletacion;
	private int tiempo;
	private int tiempoesperado;
	private String userID;
	private String workoutID;
	private String workoutNombre;
	private Usuario usuario;
	private Workouts workout;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Workouts getWorkout() {
		return workout;
	}
	public void setWorkout(Workouts workout) {
		this.workout = workout;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getRatiocompletacion() {
		return ratiocompletacion;
	}
	public void setRatiocompletacion(int ratiocompletacion) {
		this.ratiocompletacion = ratiocompletacion;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public int getTiempoesperado() {
		return tiempoesperado;
	}
	public void setTiempoesperado(int tiempoesperado) {
		this.tiempoesperado = tiempoesperado;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getWorkoutID() {
		return workoutID;
	}
	public void setWorkoutID(String workoutID) {
		this.workoutID = workoutID;
	}
	public String getWorkoutNombre() {
		return workoutNombre;
	}
	public void setWorkoutNombre(String workoutNombre) {
		this.workoutNombre = workoutNombre;
	}
	public Historico(Timestamp fecha, int nivel, int ratiocompletacion, int tiempo, int tiempoesperado, String userID,
			String workoutID, String workoutNombre) {
		this.fecha = fecha;
		this.nivel = nivel;
		this.ratiocompletacion = ratiocompletacion;
		this.tiempo = tiempo;
		this.tiempoesperado = tiempoesperado;
		this.userID = userID;
		this.workoutID = workoutID;
		this.workoutNombre = workoutNombre;
	}
	
	@Override
	public String toString() {
		return "Historico [fecha=" + fecha + ", nivel=" + nivel + ", ratiocompletacion=" + ratiocompletacion
				+ ", tiempo=" + tiempo + ", tiempoesperado=" + tiempoesperado + ", userID=" + userID + ", workoutID="
				+ workoutID + ", workoutNombre=" + workoutNombre + "]";
	}

		
}
