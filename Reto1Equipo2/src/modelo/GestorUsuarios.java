package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;;

public class GestorUsuarios {

	private final String userF = "USERS";
	private final String nombreF = "NOMBRE";
	private final String claveF = "CLAVE";
	private final String apellidosF = "APELLIDOS";
	private final String nacimientoF = "NACIMIENTO";
	private final String nivelF = "NIVEL";
	private final String emailF = "EMAIL";
	
	public Conexion conexion = new Conexion();

	public boolean login(String email, char[] cs) {
		Firestore db = null;
		try {
			db = conexion.conectar();
			String nombreColeccion = userF;
			String claveIngresada = new String(cs);
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).whereEqualTo("EMAIL", email).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				String claveGuardada = doc.getString(claveF);
				if (claveGuardada != null && claveGuardada.equals(claveIngresada)) {
					String nombre = doc.getString(nombreF);
					String apellidos = doc.getString(apellidosF);
					Timestamp nacimiento = doc.getTimestamp(nacimientoF);
					Long nivelLong = doc.getLong(nivelF);
					int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
					Usuario usuario = new Usuario(nombre, apellidos, claveGuardada, email, nacimiento, nivel);

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
			CollectionReference users = db.collection(userF);
			DocumentReference nuevoId = users.document();

			Map<String, Object> usuarioNuevo = new HashMap<>();
			
			String apellidos = usuario.getApellidos();
			String nombre = usuario.getNombre();
			String email = usuario.getEmail();
			String contraseña = usuario.getContraseña();
			Timestamp fechaNacimiento = usuario.getFechaNacimiento();
			
			usuarioNuevo.put(apellidosF, apellidos);
			usuarioNuevo.put(nombreF, nombre);
			usuarioNuevo.put(emailF, email);
			usuarioNuevo.put(claveF, contraseña);
			usuarioNuevo.put(nacimientoF, fechaNacimiento);
			usuarioNuevo.put(nivelF, 0);

			nuevoId.set(usuarioNuevo);
			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Usuario> obtenerUsuarios(ArrayList<Usuario> listaUsuarios) {
		try {
			Firestore db = conexion.conectar();
			String nombreColeccion = userF;
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				String nombre = doc.getString(nombreF);
				String clave = doc.getString(claveF);
				String email = doc.getString(emailF);
				String apellido = doc.getString(apellidosF);
				Timestamp nacimiento = doc.getTimestamp(nacimientoF);
				Long nivelLong = doc.getLong(nivelF);
				int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;

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

	public boolean verificarEmail(String email) {
		try {
			Firestore db = conexion.conectar();
			String nombreColeccion = userF;
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();
			for (QueryDocumentSnapshot doc : documentos) {
				String emailVerificar = doc.getString(emailF);
				if (email.equals(emailVerificar)) {
					return true;
				}
			}
			try {
				db.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}