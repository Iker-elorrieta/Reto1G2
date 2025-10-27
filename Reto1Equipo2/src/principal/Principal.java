package principal;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import controlador.Controlador;

public class Principal {

    public static void main(String[] args) {
        Controlador ctr = new Controlador();
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-jar", "lib/Backup.jar");
            pb.redirectErrorStream(true);
            Process proceso = pb.start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println("[BACKUP] " + linea);
                }
            }

            int codigoSalida = proceso.waitFor();
            System.out.println("El proceso de backup terminó con código: " + codigoSalida);

        } catch (Exception e) {
            System.out.println("Error al iniciar el proceso de backup: " + e.getMessage());
            e.printStackTrace();
        }
        vista.Login frame = new vista.Login(ctr);
        frame.setVisible(true);

        
    }
}
	