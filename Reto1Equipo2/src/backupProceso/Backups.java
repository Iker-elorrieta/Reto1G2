package backupProceso;

import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;


public class Backups {
	
	private final String historicoF = "HISTORIAL";
    private final String fechaF = "FECHA";
    private final String nivelF = "NIVEL";
    private final String ratioF = "RATIOCOMPLETACION";
    private final String tiempoF = "TIEMPO";
    private final String tiempoEsperadoF = "TIEMPOESPERADO";
    private final String userIDF = "USERID";
    private final String workoutIDF = "WORKOUTID";
    private final String workoutNombreF = "WORKOUTNOMBRE";
    private final String workoutF = "WORKOUT";
    private final String nombreF = "NOMBRE";
    private final String videoF = "VIDEO";
    private final String descripcionF = "DESCRIPCION";
    private final String ejercicioF = "EJERCICIO";
    private final String userF = "USERS";
	private final String claveF = "CLAVE";
	private final String apellidosF = "APELLIDOS";
	private final String nacimientoF = "NACIMIENTO";
	private final String emailF = "EMAIL";

    
	private final Firestore db;

    public Backups(Firestore db) {
        this.db = db;
    }

	public List<Map<String, Object>> obtenerUsuarios() {
	    List<Map<String, Object>> listaUsuarios = new ArrayList<>();
	    try {
	        Firestore db = this.db;
	        String nombreColeccion = userF;
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            Map<String, Object> usuario = new HashMap<>();
	            usuario.put(nombreF, doc.getString(nombreF));
	            usuario.put("CLAVE", doc.getString("CLAVE"));
	            usuario.put("EMAIL", doc.getString("EMAIL"));
	            usuario.put("APELLIDOS", doc.getString("APELLIDOS"));
	            usuario.put(nacimientoF, doc.getDate(nacimientoF));
	            usuario.put(nivelF, doc.getLong(nivelF));
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
	        String nombreColeccion = workoutF;
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            Map<String, Object> workout = new HashMap<>();
	            workout.put(nombreF, doc.getString(nombreF));
	            workout.put(nivelF, doc.getLong(nivelF));
	            workout.put("VIDEO", doc.getString("VIDEO"));
	            workout.put("DESCRIPCION", doc.getString("DESCRIPCION"));

	            List<Map<String, String>> ejercicios = new ArrayList<>();
	            List<QueryDocumentSnapshot> ejerciciosRef = doc.getReference()
	                .collection("EJERCICIO").get().get().getDocuments();

	            for (QueryDocumentSnapshot ej : ejerciciosRef) {
	                Map<String, String> ejercicio = new HashMap<>();
	                ejercicio.put(nombreF, ej.getString(nombreF));
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
	        String nombreColeccion = historicoF;
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	        	Map<String, Object> historico = new HashMap<>();

	            historico.put("ID", doc.getId());
	            historico.put(fechaF, doc.get(fechaF));
	            historico.put(nivelF, doc.get(nivelF));
	            historico.put(ratioF, doc.getDouble(ratioF));
	            historico.put(tiempoF, doc.get(tiempoF));
	            historico.put(tiempoEsperadoF, doc.get(tiempoEsperadoF));

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
