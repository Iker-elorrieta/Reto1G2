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
	
	public Conexion conexion = new Conexion();	

	public ArrayList<Historico> obtenerHistorico(ArrayList<Historico> listaHistorico) {

	    try {
	        Firestore db = conexion.conectar();
	        String nombreColeccion = "HISTORICO";
	        ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
	        QuerySnapshot documentos = future.get();

	        for (QueryDocumentSnapshot doc : documentos) {
	            String fecha = doc.getString("FECHA");
	            Long nivelLong = doc.getLong("NIVEL");
	            Long ratioLong = doc.getLong("RATIOCOMPLETACION");
	            Long tiempoLong = doc.getLong("TIEMPO");
	            Long esperadoLong = doc.getLong("TIEMPOESPERADO");
	            String userID = doc.getString("USERID");
	            String workoutID = doc.getString("WORKOUTID");
	            String workoutNombre = doc.getString("WORKOUTNOMBRE");

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    return listaHistorico;
	}

	
	

}
