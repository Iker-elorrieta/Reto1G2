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

	public ArrayList<Historico> obtenerHistorico(final ArrayList<Historico> listaHistorico) {

		try {
			final Firestore db = conexion.conectar();
			final String nombreColeccion = "HISTORICO";
			final ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			final QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				final String fecha = doc.getString("FECHA");
				final Long nivelLong = doc.getLong("NIVEL");
				final Long ratioLong = doc.getLong("RATIOCOMPLETACION");
				final Long tiempoLong = doc.getLong("TIEMPO");
				final Long esperadoLong = doc.getLong("TIEMPOESPERADO");
				final String userID = doc.getString("USERID");
				final String workoutID = doc.getString("WORKOUTID");
				final String workoutNombre = doc.getString("WORKOUTNOMBRE");

				final int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
				final int ratiocompletacion = (ratioLong != null) ? ratioLong.intValue() : 0;
				final int tiempo = (tiempoLong != null) ? tiempoLong.intValue() : 0;
				final int tiempoesperado = (esperadoLong != null) ? esperadoLong.intValue() : 0;

				final Historico h = new Historico(fecha, nivel, ratiocompletacion, tiempo, tiempoesperado, userID,
						workoutID, workoutNombre);
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