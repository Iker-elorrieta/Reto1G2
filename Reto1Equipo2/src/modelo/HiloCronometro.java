package modelo;

public class HiloCronometro extends Thread{
	private boolean corriendo = true;
	private int segundos = 0;

    @Override
    public void run() {
        while (corriendo) {
            segundos++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getSegundos() {
    	return segundos;
    }

    public void detener() {
        corriendo = false;
    }

}
