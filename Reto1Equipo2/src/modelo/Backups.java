package modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.*;

public class Backups {
	
	public void guardarBackup() {

        FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("lib/gimnasio.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection("WORKOUTS").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("backup.dat"))) {
            for (QueryDocumentSnapshot document : documents) {
                Map<String, Object> data = document.getData();
                oos.writeObject(data);
            }
        }

        System.out.println("Datos guardados");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
