package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.cloud.Timestamp;

public class GestorBackups {

	public ArrayList<Usuario> cargarUsuario(ArrayList<Usuario> listaUsuario){
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lib/usuarios.dat"))){
			while(ois.available() > 0) {
				Usuario usuario = (Usuario) ois.readObject();
				listaUsuario.add(usuario);
			}
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return listaUsuario;
	}

	public ArrayList<Workouts> cargarWorkout(ArrayList<Workouts> listaWorkout) {
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lib/workouts.dat"))){
			while(ois.available() > 0) {
				Workouts workouts = (Workouts) ois.readObject();
				listaWorkout.add(workouts);
			}
		} catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return listaWorkout;
	}

	public ArrayList<Historico> cargarHistorico(ArrayList<Historico> listaHistoricos) {
		
		File archivo = new File("lib/historial.xml");
		DocumentBuilderFactory dbFactoria = DocumentBuilderFactory.newInstance();
		
		try {
            DocumentBuilder dBuilder = dbFactoria.newDocumentBuilder();
            Document doc = dBuilder.parse(archivo);
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("REGISTRO");
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element eElement = (Element) nNode;
                    String fechaStr = eElement.getElementsByTagName("FECHA").item(0).getTextContent();
                    String nivelStr = eElement.getElementsByTagName("NIVEL").item(0).getTextContent();
                    String ratioStr = eElement.getElementsByTagName("RATIOCOMPLETACION").item(0).getTextContent();
                    String tiempoStr = eElement.getElementsByTagName("TIEMPO").item(0).getTextContent();
                    String tEsperadoStr = eElement.getElementsByTagName("TIEMPOESPERADO").item(0).getTextContent();
                    String userId = eElement.getElementsByTagName("USERID").item(0).getTextContent();
                    String workoutId = eElement.getElementsByTagName("WORKOUTID").item(0).getTextContent();
                    String workoutNombre = eElement.getElementsByTagName("WORKOUTNOMBRE").item(0).getTextContent();
                    Timestamp fecha = Timestamp.parseTimestamp(fechaStr);
                    
                    int nivel;
                    if (nivelStr.equals("null")) {
                        nivel = -1;
                    } else {
                        nivel = Integer.parseInt(nivelStr);
                    }
                    int ratio = (int)Double.parseDouble(ratioStr);
                    int tiempo = Integer.parseInt(tiempoStr);
                    int tiempoEsperado = Integer.parseInt(tEsperadoStr);
                    
                    Historico historico = new Historico(fecha, nivel, ratio, tiempo, tiempoEsperado, userId, workoutId, workoutNombre);
                    listaHistoricos.add(historico);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return listaHistoricos;
	}
	
	

}
