package modelo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class HiloBackupUsuarios implements Runnable {

    private List<Usuario> listaUsuarios;

    public HiloBackupUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    private void guardarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lib/usuarios.dat"))) {
            oos.writeObject(usuarios);
            System.out.println(usuarios);
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
