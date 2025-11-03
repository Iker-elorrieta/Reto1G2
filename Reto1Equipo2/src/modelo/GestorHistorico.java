package modelo;

import java.io.IOException;
import java.util.ArrayList;
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

                Historico h = new Historico(fecha, nivel, ratiocompletacion, tiempo, tiempoesperado, userID, workoutID, workoutNombre);
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
            String nombreColeccion = historicoF;
            ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
            QuerySnapshot documentos = future.get();

            for (QueryDocumentSnapshot doc : documentos) {
                // Extraer campos del documento de historial
                Timestamp fecha = doc.getTimestamp(fechaF);
                Long nivelLong = doc.getLong(nivelF);
                Long ratioLong = doc.getLong(ratioF);
                Long tiempoLong = doc.getLong(tiempoF);
                Long esperadoLong = doc.getLong(tiempoEsperadoF);

                // Referencias a documentos relacionados
                DocumentReference userRef = (DocumentReference) doc.get(userIDF);
                DocumentReference workoutRef = (DocumentReference) doc.get(workoutIDF);
                DocumentReference workoutNombreRef = (DocumentReference) doc.get(workoutNombreF);

                if (userRef != null && workoutRef != null && workoutNombreRef != null) {
                    // Obtener documentos referenciados
                    DocumentSnapshot userDoc = userRef.get().get();
                    userDoc.getData().forEach((key, value) -> System.out.println(key + ": " + value));
                    DocumentSnapshot workoutDoc = workoutRef.get().get();
                    DocumentSnapshot workoutNombreDoc = workoutNombreRef.get().get();

                    // Datos del workout
                    String workoutID = workoutRef.getId();
                    String workoutNombre = workoutNombreDoc.exists() ? workoutNombreDoc.getString(NombreF) : workoutNombreRef.getId();

                    // Conversión segura de valores numéricos
                    int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
                    int ratiocompletacion = (ratioLong != null) ? ratioLong.intValue() : 0;
                    int tiempo = (tiempoLong != null) ? tiempoLong.intValue() : 0;
                    int tiempoesperado = (esperadoLong != null) ? esperadoLong.intValue() : 0;

                    // Construcción del objeto Usuario con campos reales de Firestore
                    Usuario usuario = new Usuario(
                        userDoc.getString("NOMBRE"),
                        userDoc.getString("APELLIDOS"),
                        userDoc.contains("CLAVE") ? userDoc.getString("CLAVE") : null,
                        userDoc.getString("EMAIL"),
                        userDoc.getTimestamp("NACIMIENTO"),
                        userDoc.getLong("NIVEL") != null ? userDoc.getLong("NIVEL").intValue() : 0
                    );
                    
                    Workouts workout = new Workouts(
                    	    workoutDoc.getLong("ID") != null ? workoutDoc.getLong("ID").intValue() : 0,
                    	    workoutDoc.getLong("NIVEL") != null ? workoutDoc.getLong("NIVEL").intValue() : 0,
                    	    workoutDoc.getString("NOMBRE"),
                    	    workoutDoc.getString("VIDEOURL"),
                    	    workoutDoc.getString("DESCRIPCION"),
                    	    new ArrayList<>() // Aquí puedes cargar ejercicios si están en una subcolección
                    	);

                    // Debug opcional
                    System.out.println("Usuario cargado: " + usuario.getNombre() + " - " + usuario.getEmail());

                    // Construcción del objeto Historico
                    Historico h = new Historico(fecha, nivel, ratiocompletacion, tiempo, tiempoesperado, userRef.getId(), workoutID, workoutNombre);
                    h.setUsuario(usuario);
                    h.setWorkout(workout);
                    listaHistorico.add(h);
                }
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


}
