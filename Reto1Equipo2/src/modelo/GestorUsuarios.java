package modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;

import conexion.Conexion;;

public class GestorUsuarios {
	

	public boolean login(String email, String contraseña) {

		try {
			Firestore db = Conexion.conectar();
			String nombreColeccion = "USERS";
			ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
			QuerySnapshot documentos = future.get();

			for (QueryDocumentSnapshot doc : documentos) {

				if (doc.getString("EMAIL").equals(email) && doc.getString("CLAVE").equals(contraseña)) {
					Usuario usuario = new Usuario ();
					usuario.setId(doc.getId());
					usuario.setNombre(doc.getString("NOMBRE"));
					usuario.setContraseña(contraseña);
					usuario.setApellidos(doc.getString("APELLIDOS"));
					usuario.setEmail(email);
					usuario.setFechaNacimiento(doc.getString("NACIMIENTO"));
					Integer nivel = doc.getLong("NIVEL") != null ? doc.getLong("NIVEL").intValue() : 0;
					usuario.setNivel(nivel);
					System.out.println("Login correcto: " + usuario.toString());
					usuario.toString();
					return true;
				}
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

		return false;
	}

	public void RegistrarUsuarioBD(Usuario usuario) {
		try {

			Firestore db = Conexion.conectar();
			CollectionReference users = db.collection("USERS");
			DocumentReference nuevoId = users.document();

			Map<String, Object> usuarioNuevo = new HashMap<>();
			usuarioNuevo.put("APELLIDO", usuario.getApellidos());
			usuarioNuevo.put("NOMBRE", usuario.getNombre());
			usuarioNuevo.put("EMAIL", usuario.getEmail());
			usuarioNuevo.put("CLAVE", usuario.getContraseña());
			usuarioNuevo.put("NACIMINETO", usuario.getFechaNacimiento());
			usuarioNuevo.put("NIVEL", 0);

			nuevoId.set(usuarioNuevo);
			db.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Usuario> obtenerUsuarios(ArrayList<Usuario> listaUsuarios) {
		try {
		     Firestore db = Conexion.conectar();
		     String nombreColeccion = "USERS";
		     ApiFuture<QuerySnapshot> future = db.collection(nombreColeccion).get();
		     QuerySnapshot documentos = future.get(); 
		     
		     for (QueryDocumentSnapshot doc : documentos) {
		    	 
		    	    String nombre = doc.getString("NOMBRE");
		    	    String clave = doc.getString("CLAVE");
		    	    String email = doc.getString("EMAIL");
		    	    String apellido = doc.getString("APELLIDOS");
		    	    String nacimiento = doc.getString("NACIMIENTO");
		    	    Long nivelLong = doc.getLong("NIVEL");
		    	    int nivel = (nivelLong != null) ? nivelLong.intValue() : 0;
		    	   
		    	    
		    	    
		    	    Usuario usu = new Usuario(nombre, apellido, clave, email, nacimiento, nivel);
		    	    listaUsuarios.add(usu);
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
		
		return listaUsuarios;
	}

}
