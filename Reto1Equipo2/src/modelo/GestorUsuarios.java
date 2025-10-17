package modelo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;

import com.google.api.client.util.DateTime;
;

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

	public static void RegistrarUsuarios(String nombre, String apellidos, String contrasena, String email, Date fechaNac) {
		// TODO Auto-generated method stub
		try {
			
			FileInputStream serviceAccount = new FileInputStream("lib/gimnasio.json");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
