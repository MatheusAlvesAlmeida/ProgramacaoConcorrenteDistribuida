package consumidor;

import main.Recepcao;

public class Cliente extends Thread {
    private Recepcao cadeiras;

    public Cliente(Recepcao recepcao) {
        this.cadeiras = recepcao;
    }

    @Override
    public void run() {
        try {
            this.cadeiras.desejoCortarCabelo(Thread.currentThread().getName());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
