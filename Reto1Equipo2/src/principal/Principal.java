package principal;

import controlador.Controlador;

public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador ctr = new Controlador();
		vista.Login frame = new vista.Login(ctr);
		frame.setVisible(true);
	}

}
