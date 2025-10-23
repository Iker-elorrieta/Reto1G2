package backupProceso;

import java.util.List;
import java.util.Map;

public class BackupLauncher {
    public static void main(String[] args) {

        Backups bk = new Backups();

        List<Map<String, Object>> usuarios = bk.obtenerUsuarios();
        List<Map<String, Object>> workouts = bk.obtenerWorkouts();
        List<Map<String, Object>> historial = bk.obtenerHistorico();

        HiloBackupUsuarios hilo1 = new HiloBackupUsuarios(usuarios);
        HiloBackupWorkouts hilo2 = new HiloBackupWorkouts(workouts);
        HiloBackupHistorico hilo3 = new HiloBackupHistorico(historial);
        
        hilo1.run();
        hilo2.run();
        hilo3.run();
    }
}
