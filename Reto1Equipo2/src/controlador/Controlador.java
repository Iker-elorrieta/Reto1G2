package controlador;

import java.util.ArrayList;

import modelo.GestorHistorico;
import modelo.GestorUsuarios;
import modelo.GestorWorkout;
import modelo.Historico;
import modelo.Usuario;
import modelo.Workouts;

public class Controlador {

	public GestorWorkout gestorWorkout = new GestorWorkout();
	public GestorUsuarios gestorUsuarios = new GestorUsuarios();
	public GestorHistorico gestorHistorico = new GestorHistorico();
	public ArrayList<Workouts> listaWorkout = new ArrayList<Workouts>();
	public ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
	public ArrayList<Historico> listaHistoricos = new ArrayList<Historico>();
	
	
	public ArrayList<Historico> DevolverHistorico(){
		listaHistoricos = gestorHistorico.obtenerHistorico(listaHistoricos);
		return listaHistoricos;
	}
	
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
	
	public boolean LoginUsuarios(String nombre, char[] cs) {
		return gestorUsuarios.login(nombre, cs);
	}
	
	public Usuario UsuarioIniciado(String email) {
		DevolverUsuarios();
		for(Usuario usu : listaUsuarios ) {
			
			if(usu.getEmail().equals(email)){
				return usu;

			}
			
		}
		return null;
	}

	public boolean verificarEmail(String email) {
		boolean existe = gestorUsuarios.verificarEmail(email);
		return existe;
	}
	
}
