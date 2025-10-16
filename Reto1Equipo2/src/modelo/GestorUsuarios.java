package modelo;

import java.util.ArrayList;

import com.google.api.client.util.DateTime;

public class GestorUsuarios {
	
	ArrayList <Usuario> listaUsuarios = new ArrayList<>();
	
	public boolean loginUsuario(String usuario, String contraseña) {
		for(Usuario usu: listaUsuarios){
			if(usu.getNombre().equals(usuario) && usu.getContraseña().equals(contraseña)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean crearUsuario(String nombre, String apellidos, String contraseña, String email, DateTime fechaNacimiento){
		for (Usuario usu : listaUsuarios) {
	        if (usu.getEmail().equals(email)) {
	            return false;
	        }
	    }
		Usuario usu = new Usuario(nombre, apellidos, contraseña, email, fechaNacimiento);
		listaUsuarios.add(usu);
		return true;
	}

}
