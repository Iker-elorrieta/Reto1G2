package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
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

    public Conexion conexion = new Conexion();

    public ArrayList<Historico> obtenerHistorico(ArrayList<Historico> listaHistorico) {
        try {
            Firestore db = conexion.conectar();
            String nombreColeccion = historicoF;
            ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
            QuerySnapshot documentos = future.get();

            for (QueryDocumentSnapshot doc : documentos) {
                String fecha = doc.getString(fechaF);
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
}
