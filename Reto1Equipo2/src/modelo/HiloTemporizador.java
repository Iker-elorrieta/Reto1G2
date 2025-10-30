package modelo;

public class HiloTemporizador extends Thread {
	private boolean corriendo = true;
    private int duracion;

    public HiloTemporizador(int duracion) {
        this.duracion = duracion;
    }

    @Override
    public void run() {
        while (duracion > 0 && corriendo) {
            duracion--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public int getDuracion() {
    	return duracion;
    }
    
    public void detener() {
        corriendo = false;
    }
}
