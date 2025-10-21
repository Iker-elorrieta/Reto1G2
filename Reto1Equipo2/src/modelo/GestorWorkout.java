package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;


public class GestorWorkout {
	
public Conexion conexion = new Conexion();	

	public ArrayList<Workouts> obtenerWorkouts(ArrayList<Workouts> listaWorkouts) {
		
		try {
		     Firestore db = conexion.conectar();
		     String nombreColeccion = "WORKOUT";
		     ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
		     QuerySnapshot documentos = future.get(); 
		     
		     for (QueryDocumentSnapshot doc : documentos) {
		    	 ArrayList<Ejercicios> ejercicios = new ArrayList<Ejercicios>();
		    	 
		    	    String nombre = doc.getString("NOMBRE");
		    	    int nivelInt = doc.getLong("NIVEL").intValue();
		    	    String video = doc.getString("VIDEO");
		    	    String descripcion = doc.getString("DESCRIPCION");
		    	    nombreColeccion = "EJERCICIO";
		    	    for (QueryDocumentSnapshot docs : documentos) {
		    	    	String nombreEjer = docs.getString("NOMBRE");
		    	    	String descripcionEjer = docs.getString("DESCRIPCION");
		    	    	
		    	    	Ejercicios ejer = new Ejercicios("",nombreEjer,descripcionEjer);
		    	    	ejercicios.add(ejer);
		    	    }
		    	    
		    	    Workouts w = new Workouts(0,nivelInt,nombre,video,descripcion,ejercicios);
		    	    listaWorkouts.add(w);
		     }
		     
		     try {
		         db.close();
		     } catch (Exception e) {
		         e.printStackTrace();
		     }

		 } catch (IOException e) {
		     e.printStackTrace();
		 } catch (InterruptedException e) {
		     e.printStackTrace();
		 } catch (ExecutionException e) {
		     e.printStackTrace();
		 }
		
		return listaWorkouts;
	}
	
}

