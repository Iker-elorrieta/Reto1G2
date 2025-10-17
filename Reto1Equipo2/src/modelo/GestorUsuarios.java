package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.client.util.DateTime;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;;

public class GestorUsuarios {

	ArrayList<Usuario> listaUsuarios = new ArrayList<>();

	public boolean loginUsuario(String usuario, String contraseña) {
		for (Usuario usu : listaUsuarios) {
			if (usu.getNombre().equals(usuario) && usu.getContraseña().equals(contraseña)) {
				return true;
			}
		}
		return false;
	}

	public boolean crearUsuario(String nombre, String apellidos, String contraseña, String email,
			DateTime fechaNacimiento) {
		for (Usuario usu : listaUsuarios) {
			if (usu.getEmail().equals(email)) {
				return false;
			}
		}
		Usuario usu = new Usuario(nombre, apellidos, contraseña, email, fechaNacimiento);
		listaUsuarios.add(usu);
		return true;
	}

	public void RegistrarUsuarios(String nombre, String apellidos, String contrasena, String email, Date fechaNac) {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean login(String nombre, String contraseña) {

		try {
			Firestore db = Conexion.conectar();
			String nombreColeccion = "USERS";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {

				if (doc.getString("NOMBRE").equals(nombre) && doc.getString("CLAVE").equals(contraseña)) {
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

}
