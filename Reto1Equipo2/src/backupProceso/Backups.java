package backupProceso;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


public class Backups {
	
	private static String projectID = "reto1grupo2";
    private static String nombreJSON = "lib/gimnasio.json";

	public static Firestore conectar() throws IOException {


        FileInputStream serviceAccount;
        Firestore firestore;
        try {
            serviceAccount = new FileInputStream(nombreJSON);

            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectID)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            firestore = firestoreOptions.getService();

            if (firestore == null) {
                throw new IOException("No se pudo obtener una instancia de Firestore.");
            }

            return firestore;

        } catch (FileNotFoundException e) {
            throw new IOException("Archivo de credenciales no encontrado: " + nombreJSON, e);
        } catch (IOException e) {
        	/*Llamar a los backups*/
            throw new IOException("Error al cargar credenciales o conectar con Firestore.", e);
        }
    }

	public List<Map<String, Object>> obtenerUsuarios() {
	    List<Map<String, Object>> listaUsuarios = new ArrayList<>();
	    try {
	        Firestore db = conectar();
	        String nombreColeccion = "USERS";
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            Map<String, Object> usuario = new HashMap<>();
	            usuario.put("NOMBRE", doc.getString("NOMBRE"));
	            usuario.put("CLAVE", doc.getString("CLAVE"));
	            usuario.put("EMAIL", doc.getString("EMAIL"));
	            usuario.put("APELLIDOS", doc.getString("APELLIDOS"));
	            usuario.put("NACIMIENTO", doc.getString("NACIMIENTO"));
	            usuario.put("NIVEL", doc.getLong("NIVEL"));
	            listaUsuarios.add(usuario);
	        }

	        db.close();
	    } catch (IOException | InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listaUsuarios;
	}


	public List<Map<String, Object>> obtenerWorkouts() {
	    List<Map<String, Object>> listaWorkouts = new ArrayList<>();
	    try {
	        Firestore db = conectar();
	        String nombreColeccion = "WORKOUT";
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            Map<String, Object> workout = new HashMap<>();
	            workout.put("NOMBRE", doc.getString("NOMBRE"));
	            workout.put("NIVEL", doc.getLong("NIVEL"));
	            workout.put("VIDEO", doc.getString("VIDEO"));
	            workout.put("DESCRIPCION", doc.getString("DESCRIPCION"));

	            List<Map<String, String>> ejercicios = new ArrayList<>();
	            List<QueryDocumentSnapshot> ejerciciosRef = doc.getReference()
	                .collection("EJERCICIO").get().get().getDocuments();

	            for (QueryDocumentSnapshot ej : ejerciciosRef) {
	                Map<String, String> ejercicio = new HashMap<>();
	                ejercicio.put("NOMBRE", ej.getString("NOMBRE"));
	                ejercicio.put("DESCRIPCION", ej.getString("DESCRIPCION"));
	                ejercicios.add(ejercicio);
	            }

	            workout.put("EJERCICIOS", ejercicios);
	            listaWorkouts.add(workout);
	        }

	        db.close();
	    } catch (IOException | InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listaWorkouts;
	}

	public List<Map<String, Object>> obtenerHistorico() {
	    List<Map<String, Object>> listaHistorico = new ArrayList<>();
	    try {
	        Firestore db = conectar();
	        String nombreColeccion = "HISTORIAL";
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            Map<String, Object> historico = new HashMap<>();
	            historico.put("FECHA", doc.getTimestamp("FECHA"));
	            historico.put("INTENTOS", doc.getLong("INTENTOS"));
	            historico.put("RATIOPORCENTAJE", doc.getLong("RATIOPORCENTAJE"));
	            historico.put("TIEMPO", doc.getLong("TIEMPO"));
	            historico.put("TIEMPOESPERADO", doc.getLong("TIEMPOESPERADO"));
	            listaHistorico.add(historico);
	        }

	        db.close();
	    } catch (IOException | InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listaHistorico;
	}


}
