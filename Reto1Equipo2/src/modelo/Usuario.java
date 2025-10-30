package modelo;

import java.io.Serializable;
import com.google.cloud.Timestamp;

public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String nombre;
	private String apellidos;
	private String contraseña;
	private String email;
	private Timestamp fechaNacimiento;
	private int nivel;
	
	
	
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getContraseña() {
		return contraseña;
	}
	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Timestamp getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Timestamp fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Usuario(String nombre, String apellidos, String contraseña, String email, Timestamp fechaNacimiento, int nivel) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contraseña = contraseña;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.nivel = nivel;
	}
	public Usuario() {
		this.id = "";
		this.nombre = "";
		this.apellidos = "";
		this.contraseña = "";
		this.email = "";
		this.fechaNacimiento = null;
		this.nivel = 0;
	}
	@Override
	public String toString() {
	    return "\n===== Usuario =====\n" +
	           "Nombre:           " + nombre + "\n" +
	           "Apellidos:        " + apellidos + "\n" +
	           "Nivel:        	  " + nivel + "\n" +
	           "Contraseña:       " + contraseña + "\n" +
	           "Email:            " + email + "\n" +
	           "Fecha nacimiento: " + fechaNacimiento + "\n";
	}

	
	
}
