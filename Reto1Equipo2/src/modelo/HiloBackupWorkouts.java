package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class HiloBackupWorkouts implements Runnable {

    private ArrayList<Workouts> listaWorkouts;

    public HiloBackupWorkouts(ArrayList<Workouts> listaWorkouts) {
        this.listaWorkouts = listaWorkouts;
    }

    private void guardarWorkouts(ArrayList<Workouts> workouts) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lib/workouts.dat"))) {
            oos.writeObject(workouts);
            System.out.println(workouts);
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
