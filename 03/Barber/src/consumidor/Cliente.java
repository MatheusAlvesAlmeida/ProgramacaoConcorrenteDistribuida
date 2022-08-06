import main.Recepcao;

public class Cliente {
    private Recepcao cadeiras;

    public Cliente(Recepcao cadeiras) {
        this.cadeiras = cadeiras;
    }

    @Override
    public void run() {
        this.buffer.get();
        System.out.println(Thread.currentThread().getName() + " cortou o cabelo");
    }
}
