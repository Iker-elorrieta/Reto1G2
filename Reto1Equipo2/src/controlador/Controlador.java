package controlador;

import java.util.ArrayList;

import modelo.GestorUsuarios;
import modelo.GestorWorkout;
import modelo.Usuario;
import modelo.Workouts;

public class Controlador {

	public GestorWorkout gestorWorkout = new GestorWorkout();
	public GestorUsuarios gestorUsuarios = new GestorUsuarios();
	public ArrayList<Workouts> listaWorkout = new ArrayList<Workouts>();
	public ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	
	
	public ArrayList<Usuario> DevolverUsuarios(){
		listaUsuarios = gestorUsuarios.obtenerUsuarios(listaUsuarios);
		return listaUsuarios;
	}
	
	public ArrayList<Workouts> DevolverWorkouts(){
		listaWorkout = gestorWorkout.obtenerWorkouts(listaWorkout);
		return listaWorkout;
	}

	public void RegistrarUsuarioBDControlador(Usuario usuario) {
		gestorUsuarios.RegistrarUsuarioBD(usuario);
	}
	
	public boolean LoginUsuarios(String nombre, String contrase) {
		return gestorUsuarios.login(nombre, contrase);
	}
	
}
