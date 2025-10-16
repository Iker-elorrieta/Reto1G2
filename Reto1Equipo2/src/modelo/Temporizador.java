package modelo;

public class Temporizador extends Thread{

	private boolean corriendo = false;
	private int segundos;
	
	@Override
	public void run() {
		corriendo = true;
		while (corriendo) {
			try {
				Thread.sleep(1000);
				segundos++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void detener() {
		corriendo = false;
	}
}
