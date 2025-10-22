package principal;

import controlador.Controlador;
import modelo.HiloBackupUsuarios;
import modelo.HiloBackupWorkouts;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador ctr = new Controlador();
		vista.Login frame = new vista.Login(ctr);
		frame.setVisible(true);
		HiloBackupWorkouts hilo1 = new HiloBackupWorkouts(ctr.listaWorkout);
		HiloBackupUsuarios hilo2 = new HiloBackupUsuarios(ctr.listaUsuarios);
		
		hilo1.run();
		hilo2.run();
	}

}
