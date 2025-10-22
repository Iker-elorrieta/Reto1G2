package backupProceso;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import modelo.Historico;
import modelo.Usuario;
import modelo.Workouts;

public class Backups {


	public ArrayList<Usuario> cargarUsuarios() {
	    File archivo = new File("usuarios.dat");

	    if (!archivo.exists()) {
	        System.out.println("No hay usuarios guardados.");
	        return new ArrayList<>(); // devuelve lista vacía
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
	        ArrayList<Usuario> listaUsuarios = (ArrayList<Usuario>) ois.readObject();
	        System.out.println("Usuarios cargados: " + listaUsuarios.size());
	        return listaUsuarios;
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error al cargar los usuarios: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
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

	public ArrayList<Historico> cargarHistorial() {
	    File archivo = new File("historial.dat");
	    if (!archivo.exists()) {
	        return new ArrayList<>();
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
	        return (ArrayList<Historico>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) {
	        System.out.println("Error al cargar historial: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}


}
