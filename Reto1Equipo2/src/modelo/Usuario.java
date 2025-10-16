package modelo;

import com.google.api.client.util.DateTime;

public class Usuario {
	
	private String nombre;
	private String apellidos;
	private String contraseña;
	private String email;
	private DateTime fechaNacimiento;
	
	
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
	public DateTime getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(DateTime fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Usuario(String nombre, String apellidos, String contraseña, String email, DateTime fechaNacimiento) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.contraseña = contraseña;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
	}
	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", apellidos=" + apellidos + ", contraseña=" + contraseña + ", email="
				+ email + ", fechaNacimiento=" + fechaNacimiento + "]";
	}
	
}
