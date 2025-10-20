package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class Backups {

	public void guardarUsuario(Usuario nuevoUsuario) {
		List<Usuario> usuarios = new ArrayList<>();

		File archivo = new File("usuarios.dat");
		if (archivo.exists()) {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
				usuarios = (List<Usuario>) ois.readObject();
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("No se pudo cargar usuarios existentes: " + e.getMessage());
			}
		}

		// Comprobar si el usuario ya existe en .dat para no volverlo a añadir
		boolean yaExiste = false;
		for (Usuario u : usuarios) {
			if (u.getNombre().equals(nuevoUsuario.getNombre()) && u.getEmail().equals(nuevoUsuario.getEmail())) {
				yaExiste = true;
				break;
			}
		}

		if (!yaExiste) {
			usuarios.add(nuevoUsuario);
		} else {
			System.out.println("El usuario ya está registrado.");
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("usuarios.dat"))) {
			oos.writeObject(usuarios);
			System.out.println("Usuario guardado correctamente en usuarios.dat");
		} catch (IOException e) {
			System.err.println("Error al guardar el usuario: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public Usuario buscarUsuario(String nombreIngresado, String contraseñaIngresada) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("usuarios.dat"))) {
			List<Usuario> usuarios = (List<Usuario>) ois.readObject();

			for (Usuario usuario : usuarios) {
				if (usuario.getNombre().equals(nombreIngresado)
						&& usuario.getContraseña().equals(contraseñaIngresada)) {
					System.out.println(usuario.toString());
					return usuario;
				}
			}

			System.out.println("Usuario no encontrado o credenciales incorrectas.");
			return null;

		} catch (IOException | ClassNotFoundException e) {
			System.err.println("Error al cargar usuarios: " + e.getMessage());
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
	

}
