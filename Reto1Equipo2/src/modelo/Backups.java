package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

public class Backups {


	public Usuario cargarUsuario() {
	    File archivo = new File("usuarios.dat");

	    if (!archivo.exists()) {
	        System.out.println("No hay usuario guardado.");
	        return null;
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
	        Usuario usuario = (Usuario) ois.readObject();
	        System.out.println("Usuario cargado: " + usuario);
	        return usuario;
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error al cargar el usuario: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	public ArrayList<Workouts> cargarWorkouts() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workouts.dat"))) {
			return (ArrayList<Workouts>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error al cargar workouts: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	public List<Historico> cargarHistorial() {
	    File archivo = new File("historial.dat");
	    if (!archivo.exists()) {
	        return new ArrayList<>();
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
	        return (List<Historico>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error al cargar historial: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}


}
