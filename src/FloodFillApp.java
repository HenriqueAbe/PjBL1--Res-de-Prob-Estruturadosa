import javax.swing.*;

public class FloodFillApp {
    private final String pathOriginal = "src/Retangulo.png";
    private final String pastaSaida = "output_frames";
    private FloodFill motor;

    public FloodFillApp() {
        this.motor = new FloodFill(pathOriginal, pastaSaida);
    }

    public void iniciar() {
        System.out.println("Iniciando processamento e salvando frames...");
        motor.preparar(60, 100, FloodFill.Modo.PILHA);
        motor.processarTudo();

        System.out.println("Processamento concluído. Iniciando animação...");
        abrirJanela();
    }

    private void abrirJanela() {
        JFrame frame = new JFrame("Animação Flood Fill");
        JanelaAnimacao painel = new JanelaAnimacao(pastaSaida);

        frame.add(painel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}