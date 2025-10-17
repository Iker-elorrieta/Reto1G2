package controlador;

import java.util.ArrayList;
import java.util.Date;

import modelo.GestorUsuarios;
import modelo.GestorWorkout;
import modelo.Usuario;
import modelo.Workouts;

public class Controlador {

	public GestorWorkout gestorWorkout = new GestorWorkout();
	public ArrayList<Workouts> listaWorkout = new ArrayList<Workouts>();
	
	
	
	public ArrayList<Workouts> DevolverWorkouts(){
		listaWorkout = gestorWorkout.obtenerWorkouts(listaWorkout);
		return listaWorkout;
	}

	public static void RegistrarUsuarioBDControlador(Usuario usuario) {
		// TODO Auto-generated method stub
		GestorUsuarios.RegistrarUsuarioBD(usuario);
	}
	
}
