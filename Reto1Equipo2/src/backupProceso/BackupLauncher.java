package backupProceso;

import java.util.ArrayList;
import java.util.List;

import modelo.Historico;
import modelo.Usuario;
import modelo.Workouts;

public class BackupLauncher {
    public static void main(String[] args) {
    	
    	Backups bk = new Backups();
		
        ArrayList<Usuario> usuarios = bk.cargarUsuarios();
        ArrayList<Workouts> workouts = bk.cargarWorkouts();
        ArrayList<Historico> historial = bk.cargarHistorial();

        
    }
}

