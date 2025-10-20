package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;;

public class GestorUsuarios {

	ArrayList<Usuario> listaUsuarios = new ArrayList<>();

	public boolean crearUsuario(String nombre, String apellidos, String contraseña, String email,
			Date fechaNacimiento) {
		for (Usuario usu : listaUsuarios) {
			if (usu.getEmail().equals(email)) {
				return false;
			}
		}
		Usuario usu = new Usuario(nombre, apellidos, contraseña, email, fechaNacimiento);
		listaUsuarios.add(usu);
		return true;
	}

	

	public boolean login(String nombre, String contraseña) {

		try {
			Firestore db = Conexion.conectar();
			String nombreColeccion = "USERS";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {

				if (doc.getString("NOMBRE").equals(nombre) && doc.getString("CLAVE").equals(contraseña)) {
					System.out.println("Login correcto con nombre "+ nombre + " y contraseña " + contraseña);
					return true;
				}
			}

			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return false;
	}

	public void RegistrarUsuarioBD(Usuario usuario) {
		try {

			Firestore db = Conexion.conectar();
			CollectionReference users = db.collection("USERS");
			DocumentReference nuevoId = users.document();

			Map<String, Object> usuarioNuevo = new HashMap<>();
			usuarioNuevo.put("APELLIDO", usuario.getApellidos());
			usuarioNuevo.put("NOMBRE", usuario.getNombre());
			usuarioNuevo.put("EMAIL", usuario.getEmail());
			usuarioNuevo.put("CLAVE", usuario.getContraseña());
			usuarioNuevo.put("NACIMINETO", usuario.getFechaNacimiento());

			nuevoId.set(usuarioNuevo);
			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
