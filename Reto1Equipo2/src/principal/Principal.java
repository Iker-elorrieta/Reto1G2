package principal;

import controlador.Controlador;

public class Principal {

	public static void main(String[] args) {
		final Controlador ctr = new Controlador();
		vista.Login frame = new vista.Login(ctr);
		frame.setVisible(true);

	}
}
