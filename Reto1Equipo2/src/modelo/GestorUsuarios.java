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

	public boolean login(final String email, final char[] cs) {
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
					Usuario usuario = new Usuario();
					usuario.setId(doc.getId());
					usuario.setNombre(doc.getString("NOMBRE"));
					usuario.setContraseña(doc.getString("CLAVE"));
					usuario.setApellidos(doc.getString("APELLIDOS"));
					usuario.setEmail(email);
					usuario.setFechaNacimiento(doc.getDate("NACIMIENTO"));
					final Integer nivel = doc.getLong("NIVEL") != null ? doc.getLong("NIVEL").intValue() : 0;
					usuario.setNivel(nivel);

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

	public void RegistrarUsuarioBD(final Usuario usuario) {
		try {
			Firestore db = conexion.conectar();
			CollectionReference users = db.collection("USERS");
			DocumentReference nuevoId = users.document();

			Map<String, Object> usuarioNuevo = new HashMap<>();
			usuarioNuevo.put("APELLIDO", usuario.getApellidos());
			usuarioNuevo.put("NOMBRE", usuario.getNombre());
			usuarioNuevo.put("EMAIL", usuario.getEmail());
			usuarioNuevo.put("CLAVE", usuario.getContraseña());
			usuarioNuevo.put("NACIMINETO", usuario.getFechaNacimiento());
			usuarioNuevo.put("NIVEL", 0);

			nuevoId.set(usuarioNuevo);
			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Usuario> obtenerUsuarios(final ArrayList<Usuario> listaUsuarios) {
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

				Usuario usu = new Usuario(nombre, apellido, clave, email, nacimiento, nivel);
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

	public boolean verificarEmail(final String email) {
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