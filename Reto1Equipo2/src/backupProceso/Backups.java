package backupProceso;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


public class Backups {
    
	private final Firestore db;

    public Backups(Firestore db) {
        this.db = db;
    }

	public List<Map<String, Object>> obtenerUsuarios() {
	    List<Map<String, Object>> listaUsuarios = new ArrayList<>();
	    try {
	        Firestore db = this.db;
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

	    } catch (InterruptedException | ExecutionException e) {
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
	        Firestore db = this.db;
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

	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return listaWorkouts;
	}

	public List<Map<String, Object>> obtenerHistorico() {
	    List<Map<String, Object>> listaHistorico = new ArrayList<>();
	    Firestore db = null;
	    try {
	        db = this.db;
	        String nombreColeccion = "HISTORIAL";
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	        	Map<String, Object> historico = new HashMap<>();

	            historico.put("ID", doc.getId());
	            historico.put("FECHA", doc.get("FECHA"));
	            historico.put("NIVEL", doc.get("NIVEL"));
	            historico.put("RATIO_COMPLETACION", doc.getDouble("RATIOCOMPLETACION"));
	            historico.put("TIEMPO", doc.get("TIEMPO"));
	            historico.put("TIEMPO_ESPERADO", doc.get("TIEMPOESPERADO"));

	            DocumentReference usuarioRef = (DocumentReference) doc.get("USERID");
	            historico.put("USUARIO", usuarioRef);

	            DocumentReference workoutRef = (DocumentReference) doc.get("WORKOUTID");
	            historico.put("WORKOUT", workoutRef);

	            DocumentReference workoutNombre = (DocumentReference) doc.get("WORKOUTNOMBRE");
                historico.put("WORKOUTNOMBRE", workoutNombre);
                
	            
	            listaHistorico.add(historico);
	        }

	    } catch (InterruptedException | ExecutionException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return listaHistorico;
	}



}
