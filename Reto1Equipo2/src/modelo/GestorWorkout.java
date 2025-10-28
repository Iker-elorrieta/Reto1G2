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

	public Conexion conexion = new Conexion();

	public ArrayList<Workouts> obtenerWorkouts(final ArrayList<Workouts> listaWorkouts) {

		try {
			Firestore db = conexion.conectar();
			final String nombreColeccionPrincipal = "WORKOUT";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccionPrincipal).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {
				final ArrayList<Ejercicios> ejercicios = new ArrayList<>();

				final String nombre = doc.getString("NOMBRE");
				final int nivelInt = doc.getLong("NIVEL").intValue();
				final String video = doc.getString("VIDEO");
				final String descripcion = doc.getString("DESCRIPCION");

				DocumentReference ref = doc.getReference();
				final List<QueryDocumentSnapshot> ejerciciosRef = ref.collection("EJERCICIO").get().get()
						.getDocuments();

				for (QueryDocumentSnapshot docs : ejerciciosRef) {
					final String nombreEjer = docs.getString("NOMBRE");
					final String descripcionEjer = docs.getString("DESCRIPCION");

					final Ejercicios ejer = new Ejercicios("", nombreEjer, descripcionEjer);
					ejercicios.add(ejer);
				}

				final Workouts w = new Workouts(0, nivelInt, nombre, video, descripcion, ejercicios);
				listaWorkouts.add(w);
			}

			try {
				db.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return listaWorkouts;
	}
}