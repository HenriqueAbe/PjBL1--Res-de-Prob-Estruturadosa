import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloodFill {
    public enum Modo { PILHA, FILA }
    private BufferedImage imagem;
    private int corAlvo;
    private int corSubstituta = Color.RED.getRGB();
    private int contadorPixels = 0;
    private int contadorFrames = 0;
    private final int INTERVALO_FRAME = 300;

    private Pilha pilha;
    private Fila fila;
    private Modo modoAtual;

    public FloodFill(String caminhoOriginal) {
        try {
            this.imagem = ImageIO.read(new File(caminhoOriginal));
            File pasta = new File("todosFrames");
            if (pasta.exists()) {
                for (File f : pasta.listFiles()) f.delete();
            }
            pasta.mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void preparar(int x, int y, Modo modo) {
        this.modoAtual = modo;
        this.corAlvo = imagem.getRGB(x, y);
        if (corAlvo == corSubstituta) return;

        Point pInicial = new Point(x, y);
        if (modo == Modo.PILHA) {
            pilha = new Pilha();
            pilha.push(pInicial);
        } else {
            fila = new Fila();
            fila.enqueue(pInicial);
        }
    }

    public boolean processarTudo() {
        while (true) {
            Point p;
            if (modoAtual == Modo.PILHA) {
                if (pilha.isEmpty()) break;
                p = pilha.pop();
            } else {
                if (fila.isEmpty()) break;
                p = fila.dequeue();
            }

            if (imagem.getRGB(p.x, p.y) == corAlvo) {

                imagem.setRGB(p.x, p.y, corSubstituta);
                contadorPixels++;

                if (contadorPixels % INTERVALO_FRAME == 0) {
                    salvarFrame();
                    salvarImagemFinal();
                }
                explorarVizinhos(p.x, p.y);
            }
        }
        salvarFrame();
        salvarImagemFinal();
        System.out.println("Imagem final salvado");
        return true;
    }

    private void explorarVizinhos(int x, int y) {
        int[][] vizinhos = {{x - 1, y}, {x + 1, y}, {x, y - 1}, {x, y + 1}};

        for (int[] v : vizinhos) {
            int nx = v[0];
            int ny = v[1];

            if (podePintar(nx, ny)) {
                Point novoPonto = new Point(nx, ny);
                if (modoAtual == Modo.PILHA) {
                    pilha.push(novoPonto);
                } else {
                    fila.enqueue(novoPonto);
                }
            }
        }
    }

    private boolean podePintar(int x, int y) {
        return x >= 0 && x < imagem.getWidth() &&
                y >= 0 && y < imagem.getHeight() &&
                imagem.getRGB(x, y) == corAlvo;
    }

    private void salvarFrame() {
        try {
            String nome = String.format("todosFrames/frame_%04d.png", contadorFrames++);
            ImageIO.write(imagem, "png", new File(nome));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvarImagemFinal() {
        try {
            ImageIO.write(imagem, "png", new File("src/Resultado_Final.png"));
        } catch (IOException e) {
            System.err.println("Erro ao salvar imagem final: " + e.getMessage());
        }
    }
}
