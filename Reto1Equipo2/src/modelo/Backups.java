package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Backups {

	public void guardarUsuario(Usuario usuarioLogueado) {
	    File archivo = new File("usuarios.dat");

	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
	        oos.writeObject(usuarioLogueado);
	        System.out.println("Usuario logueado guardado correctamente en usuarios.dat");
	    } catch (IOException e) {
	        System.err.println("Error al guardar el usuario: " + e.getMessage());
	        e.printStackTrace();
	    }
	}


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
	        System.err.println("Error al cargar el usuario: " + e.getMessage());
	        e.printStackTrace();
	        return null;
	    }
	}

	public void guardarWorkouts(ArrayList<Workouts> listaWorkouts) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("workouts.dat"))) {
			oos.writeObject(listaWorkouts);
			System.out.println("Workouts guardados correctamente en workouts.dat");
		} catch (IOException e) {
			System.err.println("Error al guardar los workouts: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public ArrayList<Workouts> cargarWorkouts() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("workouts.dat"))) {
			return (ArrayList<Workouts>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error al cargar workouts: " + e.getMessage());
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
	
	public void guardarHistorial(List<Historico> historial) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("historial.dat"))) {
	        oos.writeObject(historial);
	        System.out.println("Historial guardado correctamente en historial.dat");
	    } catch (IOException e) {
	        System.err.println("Error al guardar el historial: " + e.getMessage());
	        e.printStackTrace();
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
	        System.err.println("Error al cargar historial: " + e.getMessage());
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}


}
