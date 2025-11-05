package modelo;

import java.io.Serializable;

public class Series implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private int descanso;
	private int duracion;
	private int repeticiones;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDescanso() {
		return descanso;
	}
	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public int getRepeticiones() {
		return repeticiones;
	}
	public void setRepeticiones(int repeticiones) {
		this.repeticiones = repeticiones;
	}
	
	public Series(int descanso, int duracion, int repeticiones) {
		this.descanso = descanso;
		this.duracion = duracion;
		this.repeticiones = repeticiones;
	}
	@Override
	public String toString() {
		return "Series [id=" + id + ", descanso=" + descanso + ", duracion=" + duracion + ", repeticiones="
				+ repeticiones + "]";
	}
	

}