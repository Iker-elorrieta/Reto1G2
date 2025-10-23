package backupProceso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class HiloBackupUsuarios implements Runnable {

    private List<Map<String, Object>> listaUsuarios;

    public HiloBackupUsuarios(List<Map<String, Object>> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    private void guardarUsuarios(List<Map<String, Object>> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lib/usuarios.dat"))) {
            oos.writeObject(usuarios);
            System.out.println("Lista de usuarios guardada correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        guardarUsuarios(listaUsuarios);
    }
}
