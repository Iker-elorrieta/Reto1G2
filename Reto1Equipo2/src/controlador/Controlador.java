package controlador;

import java.util.Date;

import javax.swing.JTextField;

import modelo.GestorUsuarios;

public class Controlador {

	public static void RegistrarUsuario(String nombre, String apellidos, String contrasena, String email,
			Date fechaNac) {
		// TODO Auto-generated method stub
		GestorUsuarios.RegistrarUsuarios(nombre, apellidos, contrasena, email, fechaNac);
	}

}
