package modelo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.api.client.util.DateTime;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import conexion.Conexion;
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
			
			Firestore db = Conexion.conectar();
			CollectionReference users = db.collection("USERS");
			DocumentReference nuevoId = users.document();
			
			Map<String, Object> usuarioNuevo = new HashMap<>();
			usuarioNuevo.put("APELLIDO", apellidos);
			usuarioNuevo.put("NOMBRE", nombre);
			usuarioNuevo.put("EMAIL", email);
			usuarioNuevo.put("CLAVE", contrasena);
			usuarioNuevo.put("NACIMINETO", fechaNac);
			
			nuevoId.set(usuarioNuevo);
			db.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
