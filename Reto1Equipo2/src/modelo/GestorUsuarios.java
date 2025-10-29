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

	public Conexion conexion = new Conexion();

	public boolean login(String email, char[] cs) {
		Firestore db = null;
		try {
			db = conexion.conectar();
			final String nombreColeccion = "USERS";
			final String claveIngresada = new String(cs);
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).whereEqualTo("EMAIL", email).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				final String claveGuardada = doc.getString("CLAVE");
				if (claveGuardada != null && claveGuardada.equals(claveIngresada)) {
					final String nombre = doc.getString("NOMBRE");
					final String apellidos = doc.getString("APELLIDOS");
					final Date nacimiento = doc.getDate("NACIMIENTO");
					final Long nivelLong = doc.getLong("NIVEL");
					final int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
					final Usuario usuario = new Usuario(nombre, apellidos, claveGuardada, email, nacimiento, nivel);

					System.out.println("Login correcto: " + usuario.toString());
					return true;
				}
			}

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			if (db != null) {
				try {
					db.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	public void RegistrarUsuarioBD(Usuario usuario) {
		try {
			Firestore db = conexion.conectar();
			CollectionReference users = db.collection("USERS");
			DocumentReference nuevoId = users.document();

			final Map<String, Object> usuarioNuevo = new HashMap<>();
			
			final String apellidos = usuario.getApellidos();
			final String nombre = usuario.getNombre();
			final String email = usuario.getEmail();
			final String contraseña = usuario.getContraseña();
			final Date fechaNacimiento = usuario.getFechaNacimiento();
			
			usuarioNuevo.put("APELLIDO", apellidos);
			usuarioNuevo.put("NOMBRE", nombre);
			usuarioNuevo.put("EMAIL", email);
			usuarioNuevo.put("CLAVE", contraseña);
			usuarioNuevo.put("NACIMINETO", fechaNacimiento);
			usuarioNuevo.put("NIVEL", 0);

			nuevoId.set(usuarioNuevo);
			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Usuario> obtenerUsuarios(ArrayList<Usuario> listaUsuarios) {
		try {
			Firestore db = conexion.conectar();
			final String nombreColeccion = "USERS";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				final String nombre = doc.getString("NOMBRE");
				final String clave = doc.getString("CLAVE");
				final String email = doc.getString("EMAIL");
				final String apellido = doc.getString("APELLIDOS");
				final Date nacimiento = doc.getDate("NACIMIENTO");
				final Long nivelLong = doc.getLong("NIVEL");
				final int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;

				final Usuario usu = new Usuario(nombre, apellido, clave, email, nacimiento, nivel);
				listaUsuarios.add(usu);
			}

			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return listaUsuarios;
	}

	public boolean verificarEmail(String email) {
		try {
			Firestore db = conexion.conectar();
			final String nombreColeccion = "USERS";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();
			for (QueryDocumentSnapshot doc : documentos) {
				final String emailVerificar = doc.getString("EMAIL");
				if (email.equals(emailVerificar)) {
					return true;
				}
			}

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}