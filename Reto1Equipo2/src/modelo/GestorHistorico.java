package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class GestorHistorico {

	private final String historicoF = "HISTORIAL";
	private final String fechaF = "FECHA";
	private final String nivelF = "NIVEL";
	private final String ratioF = "RATIOCOMPLETACION";
	private final String tiempoF = "TIEMPO";
	private final String tiempoEsperadoF = "TIEMPOESPERADO";
	private final String userIDF = "USERID";
	private final String workoutIDF = "WORKOUTID";
	private final String workoutNombreF = "WORKOUTNOMBRE";
	private final String NombreF = "NOMBRE";

	public Conexion conexion = new Conexion();

	public ArrayList<Historico> obtenerHistorico(ArrayList<Historico> listaHistorico) {
		try {
			Firestore db = conexion.conectar();
			String nombreColeccion = historicoF;
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				Timestamp fecha = doc.getTimestamp(fechaF);
				Long nivelLong = doc.getLong(nivelF);
				Long ratioLong = doc.getLong(ratioF);
				Long tiempoLong = doc.getLong(tiempoF);
				Long esperadoLong = doc.getLong(tiempoEsperadoF);
				String userID = doc.getString(userIDF);
				String workoutID = doc.getString(workoutIDF);
				String workoutNombre = doc.getString(workoutNombreF);

				int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
				int ratiocompletacion = (ratioLong != null) ? ratioLong.intValue() : 0;
				int tiempo = (tiempoLong != null) ? tiempoLong.intValue() : 0;
				int tiempoesperado = (esperadoLong != null) ? esperadoLong.intValue() : 0;

				Historico h = new Historico(fecha, nivel, ratiocompletacion, tiempo, tiempoesperado, userID, workoutID,
						workoutNombre);
				listaHistorico.add(h);
			}

			db.close();

		} catch (IOException | InterruptedException | ExecutionException e) {
			System.out.println("Error al obtener historial: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaHistorico;
	}

	public ArrayList<Historico> obtenerHistorico2(ArrayList<Historico> listaHistorico) {
		try {
			Firestore db = conexion.conectar();
			ApiFuture<QuerySnapshot> future = db.collection(historicoF).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				Timestamp fecha = doc.getTimestamp(fechaF);
				Long nivelLong = doc.getLong("NIVEL");
				int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
				int ratiocompletacion = doc.getLong(ratioF).intValue();
				int tiempo = doc.getLong(tiempoF).intValue();
				int tiempoesperado = doc.getLong(tiempoEsperadoF).intValue();

				// Referencias
				DocumentReference userRef = (DocumentReference) doc.get(userIDF);
				DocumentReference workoutRef = (DocumentReference) doc.get(workoutIDF);
				DocumentReference workoutNombreRef = (DocumentReference) doc.get(workoutNombreF);

				// Obtener documentos referenciados
				DocumentSnapshot userDoc = userRef.get().get();
				DocumentSnapshot workoutDoc = workoutRef.get().get();
				DocumentSnapshot workoutNombreDoc = workoutNombreRef.get().get();

				String clave = null;
				if (userDoc.contains("CLAVE")) {
				    clave = userDoc.getString("CLAVE");
				}
				// Construir Usuario
				Usuario usuario = new Usuario(
					userDoc.getString("NOMBRE"),
					userDoc.getString("APELLIDOS"),
					clave,
					userDoc.getString("EMAIL"),
					userDoc.getTimestamp("NACIMIENTO"),
					userDoc.getLong("NIVEL").intValue()
				);
				usuario.setId(userDoc.getId());

				// Construir Workout
				Workouts workout = new Workouts(
					workoutDoc.getId(),
					workoutDoc.getLong("NIVEL").intValue(),
					workoutDoc.getString("NOMBRE"),
					workoutDoc.getString("VIDEOURL"),
					workoutDoc.getString("DESCRIPCION"),
					new ArrayList<>()
				);

				// Obtener nombre del workout desde el documento referenciado
				String workoutNombre = workoutNombreDoc.exists()
					? workoutNombreDoc.getString("NOMBRE")
					: workoutNombreRef.getId();

				// Crear histórico
				Historico h = new Historico(
					fecha,
					nivel,
					ratiocompletacion,
					tiempo,
					tiempoesperado,
					userRef.getId(),
					workoutRef.getId(),
					workoutNombre
				);
				h.setUsuario(usuario);
				h.setWorkout(workout);
				listaHistorico.add(h);
			}

			db.close();

		} catch (Exception e) {
			System.out.println("Error al obtener historial: " + e.getMessage());
			e.printStackTrace();
		}

		return listaHistorico;
	}
	public void guardarHistorico(Historico historico) throws Exception {
	    Firestore db = conexion.conectar();

	    // Crear referencias
	    DocumentReference userRef = db.collection("USERS").document(historico.getUserID());
	    DocumentReference workoutRef = db.collection("WORKOUT").document(historico.getWorkoutID());
	    DocumentReference workoutNombreRef = workoutRef;

	    // Obtener ejercicios del workout
	    List<QueryDocumentSnapshot> ejerciciosDocs = workoutRef.collection("EJERCICIOS").get().get().getDocuments();
	    int totalEjercicios = ejerciciosDocs.size();
	    int ejerciciosCompletados = contarEjerciciosCompletados(ejerciciosDocs);

	    int ratio = 0;
	    if (totalEjercicios > 0) {
	        ratio = (ejerciciosCompletados * 100) / totalEjercicios;
	    }

	    // Construir datos del histórico
	    Map<String, Object> datos = new HashMap<>();
	    datos.put("FECHA", historico.getFecha());
	    datos.put("RATIOCOMPLETACION", ratio);
	    datos.put("TIEMPO", historico.getTiempo());
	    datos.put("TIEMPOESPERADO", historico.getTiempoesperado());
	    datos.put("USERID", userRef);
	    datos.put("WORKOUTID", workoutRef);
	    datos.put("WORKOUTNOMBRE", workoutNombreRef);

	    ApiFuture<DocumentReference> future = db.collection("HISTORIAL").add(datos);
	    System.out.println("Histórico guardado con ID: " + future.get().getId());

	    db.close();
	}
	
	private int contarEjerciciosCompletados(List<QueryDocumentSnapshot> ejerciciosDocs) {
	    int completados = 0;
	    for (QueryDocumentSnapshot ejerDoc : ejerciciosDocs) {
	        Boolean completado = ejerDoc.getBoolean("COMPLETADO"); // o tu lógica
	        if (completado != null && completado) {
	            completados++;
	        }
	    }
	    return completados;
	}

}
