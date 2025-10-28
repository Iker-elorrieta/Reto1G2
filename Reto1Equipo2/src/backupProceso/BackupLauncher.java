package backupProceso;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

public class BackupLauncher {
    public static void main(String[] args) {
        Firestore db = null;

        try {
            String projectID = "reto1grupo2";
            String nombreJSON = "lib/gimnasio.json";

            FileInputStream serviceAccount = new FileInputStream(nombreJSON);
            FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance().toBuilder()
                .setProjectId(projectID)
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

            db = firestoreOptions.getService();

            if (db == null) {
                throw new RuntimeException("No se pudo obtener una instancia de Firestore.");
            }

            Backups bk = new Backups(db);
            List<Map<String, Object>> usuarios = bk.obtenerUsuarios();
            List<Map<String, Object>> workouts = bk.obtenerWorkouts();
            List<Map<String, Object>> historial = bk.obtenerHistorico();

            Thread hilo1 = new Thread(new HiloBackupUsuarios(usuarios));
            Thread hilo2 = new Thread(new HiloBackupWorkouts(workouts));
            Thread hilo3 = new Thread(new HiloBackupHistorico(historial));

            hilo1.start();
            hilo2.start();
            hilo3.start();

            hilo1.join();
            hilo2.join();
            hilo3.join();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                try {
                    db.close();
                } catch (Exception e) {
                    System.err.println("Error al cerrar Firestore: " + e.getMessage());
                }
            }
        }
    }
}