package conexion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

import backupProceso.Backups;


public class Conexion {
	
    private static String projectID = "reto1grupo2";
    private static String nombreJSON = "lib/gimnasio.json";
    
    
    public static Firestore conectar() throws IOException {


        FileInputStream serviceAccount;
        Firestore firestore;
        try {
            serviceAccount = new FileInputStream(nombreJSON);

            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectID)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            firestore = firestoreOptions.getService();

            if (firestore == null) {
                throw new IOException("No se pudo obtener una instancia de Firestore.");
            }

            return firestore;

        } catch (FileNotFoundException e) {
            throw new IOException("Archivo de credenciales no encontrado: " + nombreJSON, e);
        } catch (IOException e) {
        	/*Llamar a los backups*/
            throw new IOException("Error al cargar credenciales o conectar con Firestore.", e);
        }
    }

}