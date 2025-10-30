package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;

public class GestorWorkout {

    private final String workoutF = "WORKOUT";
    private final String nombreF = "NOMBRE";
    private final String nivelF = "NIVEL";
    private final String videoF = "VIDEO";
    private final String descripcionF = "DESCRIPCION";
    private final String ejercicioF = "EJERCICIO";

    public Conexion conexion = new Conexion();

    public ArrayList<Workouts> obtenerWorkouts(ArrayList<Workouts> listaWorkouts) {

        try {
            Firestore db = conexion.conectar();
            String nombreColeccionPrincipal = workoutF;
            ApiFuture<QuerySnapshot> future = db.collection(nombreColeccionPrincipal).get();
            QuerySnapshot documentos = future.get();

            for (QueryDocumentSnapshot doc : documentos) {
                ArrayList<Ejercicios> ejercicios = new ArrayList<>();

                String nombre = doc.getString(nombreF);
                Long nivelLong = doc.getLong(nivelF);
                int nivelInt = (nivelLong != null) ? nivelLong.intValue() : 0;
                String video = doc.getString(videoF);
                String descripcion = doc.getString(descripcionF);

                DocumentReference ref = doc.getReference();
                List<QueryDocumentSnapshot> ejerciciosRef = ref.collection(ejercicioF).get().get().getDocuments();

                for (QueryDocumentSnapshot docs : ejerciciosRef) {
                    String nombreEjer = docs.getString(nombreF);
                    String descripcionEjer = docs.getString(descripcionF);

                    Ejercicios ejer = new Ejercicios("", nombreEjer, descripcionEjer);
                    ejercicios.add(ejer);
                }

                Workouts w = new Workouts(0, nivelInt, nombre, video, descripcion, ejercicios);
                listaWorkouts.add(w);
            }

            db.close();

        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaWorkouts;
    }
}
