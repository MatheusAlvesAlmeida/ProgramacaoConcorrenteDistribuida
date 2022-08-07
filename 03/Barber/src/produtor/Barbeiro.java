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
            System.out.println("Barber is sleeping");
            this.cadeiras.cortarCabelo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}