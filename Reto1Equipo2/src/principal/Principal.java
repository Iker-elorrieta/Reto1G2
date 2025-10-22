package principal;

import controlador.Controlador;
import modelo.HiloBackupHistorico;
import modelo.HiloBackupUsuarios;
import modelo.HiloBackupWorkouts;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador ctr = new Controlador();
		ctr.DevolverUsuarios();
		ctr.DevolverWorkouts();
		ctr.DevolverHistorico();
		vista.Login frame = new vista.Login(ctr);
		frame.setVisible(true);
		HiloBackupWorkouts hilo1 = new HiloBackupWorkouts(ctr.listaWorkout);
		HiloBackupUsuarios hilo2 = new HiloBackupUsuarios(ctr.listaUsuarios);
		HiloBackupHistorico hilo3 = new HiloBackupHistorico(ctr.listaHistoricos);
		
		hilo1.run();
		hilo2.run();
		hilo3.run();
	}

}
