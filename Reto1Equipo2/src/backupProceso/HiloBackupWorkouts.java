package backupProceso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class HiloBackupWorkouts implements Runnable {

    private List<Map<String, Object>> listaWorkouts;

    public HiloBackupWorkouts(List<Map<String, Object>> listaWorkouts) {
        this.listaWorkouts = listaWorkouts;
    }

    private void guardarWorkouts(List<Map<String, Object>> workouts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lib/workouts.dat"))) {
            oos.writeObject(workouts);
            System.out.println("Workouts guardados correctamente en workouts.dat");
        } catch (IOException e) {
            System.out.println("Error al guardar los workouts: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        guardarWorkouts(listaWorkouts);
    }
}
