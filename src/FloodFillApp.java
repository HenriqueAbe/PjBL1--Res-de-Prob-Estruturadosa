import javax.swing.*;

public class FloodFillApp {
    private final String pathOriginal = "src/Retangulo.png";
    private FloodFill floodFill;

    public FloodFillApp() {
        this.floodFill = new FloodFill(pathOriginal);
    }

    public void iniciar() {
        System.out.println("Iniciando processamento e salvando frames...");
        floodFill.preparar(60, 100, FloodFill.Modo.FILA);
        floodFill.processarTudo();

        System.out.println("Processamento concluído. Iniciando animação...");
        abrirJanela();
    }

    private void abrirJanela() {
        JFrame frame = new JFrame("Animação Flood Fill");
        JanelaAnimacao painel = new JanelaAnimacao("todosFrames");

        frame.add(painel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}