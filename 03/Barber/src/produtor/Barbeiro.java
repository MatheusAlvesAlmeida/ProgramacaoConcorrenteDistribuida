package produtor;

import main.Recepcao;

public class Barbeiro extends Thread {
    private Recepcao cadeiras;

    public Barbeiro(Recepcao recepcao) {
        this.cadeiras = recepcao;
    }

    @Override
    public void run() {
        try {
            this.cadeiras.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}