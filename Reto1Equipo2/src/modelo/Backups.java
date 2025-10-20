package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.*;

public class Backups {

	public void guardarUsuario(Usuario nuevoUsuario) {
		List<Usuario> usuarios = new ArrayList<>();

		File archivo = new File("usuarios.dat");
		if (archivo.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				usuarios = (List<Usuario>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("No se pudo cargar usuarios existentes: " + e.getMessage());
			}
		}
		
		//	Comprobar si el usuario ya existe en .dat para no volverlo a añadir
		boolean yaExiste = false;
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(nuevoUsuario.getNombre()) && u.getEmail().equals(nuevoUsuario.getEmail())) {
				yaExiste = true;
				break;
			}
		}

		if (!yaExiste) {
			usuarios.add(nuevoUsuario);
		} else {
			System.out.println("El usuario ya está registrado.");
		}

		usuarios.add(nuevoUsuario);

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"))) {
			oos.writeObject(usuarios);
			System.out.println("Usuario guardado correctamente en usuarios.dat");
		} catch (IOException e) {
			System.err.println("Error al guardar el usuario: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Usuario buscarUsuario(String nombreIngresado, String contraseñaIngresada) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("usuarios.dat"))) {
			List<Usuario> usuarios = (List<Usuario>) ois.readObject();

			for (Usuario usuario : usuarios) {
				if (usuario.getNombre().equals(nombreIngresado)
						&& usuario.getContraseña().equals(contraseñaIngresada)) {
					System.out.println(usuario.toString());
					return usuario;
				}
			}

			System.out.println("Usuario no encontrado o credenciales incorrectas.");
			return null;

		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error al cargar usuarios: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	public void guardarBackupWorkouts() {
		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("lib/gimnasio.json");

			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();

			if (FirebaseApp.getApps().isEmpty()) {
				FirebaseApp.initializeApp(options);
			}

			Firestore db = FirestoreClient.getFirestore();

			ApiFuture<QuerySnapshot> future = db.collection("WORKOUTS").get();
			List<QueryDocumentSnapshot> workoutDocs = future.get().getDocuments();

			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("backup.dat"))) {
				List<Map<String, Object>> backupData = new ArrayList<>();

				for (QueryDocumentSnapshot workoutDoc : workoutDocs) {
					Map<String, Object> workoutData = new HashMap<>(workoutDoc.getData());

					// Subcolección Ejercicios
					List<Map<String, Object>> ejerciciosData = new ArrayList<>();
					ApiFuture<QuerySnapshot> ejerciciosFuture = workoutDoc.getReference().collection("EJERCICIO").get();
					List<QueryDocumentSnapshot> ejercicioDocs = ejerciciosFuture.get().getDocuments();

					for (QueryDocumentSnapshot ejercicioDoc : ejercicioDocs) {
						Map<String, Object> ejercicioData = new HashMap<>(ejercicioDoc.getData());

						// Subcoleccion Series
						List<Map<String, Object>> seriesData = new ArrayList<>();
						ApiFuture<QuerySnapshot> seriesFuture = ejercicioDoc.getReference().collection("SERIE").get();
						List<QueryDocumentSnapshot> serieDocs = seriesFuture.get().getDocuments();

						for (QueryDocumentSnapshot serieDoc : serieDocs) {
							seriesData.add(serieDoc.getData());
						}

						ejercicioData.put("SERIE", seriesData);
						ejerciciosData.add(ejercicioData);
					}

					workoutData.put("EJERCICIO", ejerciciosData);
					backupData.add(workoutData);
				}

				oos.writeObject(backupData);
				System.out.println("Datos guardados en backup.dat");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
