package controlador;

import java.util.ArrayList;
import java.util.Date;

import modelo.GestorUsuarios;
import modelo.GestorWorkout;
import modelo.Workouts;

public class Controlador {

	public GestorWorkout gestorWorkout = new GestorWorkout();
	public ArrayList<Workouts> listaWorkout = new ArrayList<Workouts>();
	
	public static void RegistrarUsuario(String nombre, String apellidos, String contrasena, String email,
			Date fechaNac) {
		// TODO Auto-generated method stub
		GestorUsuarios.RegistrarUsuarios(nombre, apellidos, contrasena, email, fechaNac);
	}
	
	public ArrayList<Workouts> DevolverWorkouts(){
		listaWorkout = gestorWorkout.obtenerWorkouts(listaWorkout);
		return listaWorkout;
	}
	
}
