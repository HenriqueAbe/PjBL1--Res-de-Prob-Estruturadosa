import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FloodFill {
    public enum Modo { PILHA, FILA }
    private BufferedImage imagem;
    private String pastaSaida;
    private int corAlvo;
    private int corSubstituta = Color.RED.getRGB();
    private int contadorPixels = 0;
    private int contadorFrames = 0;
    private final int INTERVALO_FRAME = 300;

    private Pilha pilha;
    private Fila fila;
    private Modo modoAtual;

    public FloodFill(String caminhoOriginal, String pastaSaida) {
        this.pastaSaida = pastaSaida;
        try {
            this.imagem = ImageIO.read(new File(caminhoOriginal));
            File pasta = new File(pastaSaida);
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

        if (modo == Modo.PILHA) {
            pilha = new Pilha();
            pintarEInserir(x, y, pilha);
        } else {
            fila = new Fila();
            pintarEInserir(x, y, fila);
        }
    }

    public boolean processarTudo() {
        while (true) {
            if (modoAtual == Modo.PILHA) {
                if (pilha.isEmpty()) break;
                Point p = pilha.pop();
                explorarVizinhos(p.x, p.y, pilha);
            } else {
                if (fila.isEmpty()) break;
                Point p = fila.dequeue();
                explorarVizinhos(p.x, p.y, fila);
            }
        }
        salvarFrame();
        return true;
    }

    private void explorarVizinhos(int x, int y, Object estrutura) {
        int[][] vizinhos = {{x+1, y}, {x-1, y}, {x, y+1}, {x, y-1}};
        for (int[] v : vizinhos) {
            if (podePintar(v[0], v[1])) {
                pintarEInserir(v[0], v[1], estrutura);
            }
        }
    }

    private boolean podePintar(int x, int y) {
        return x >= 0 && x < imagem.getWidth() &&
                y >= 0 && y < imagem.getHeight() &&
                imagem.getRGB(x, y) == corAlvo;
    }

    private void pintarEInserir(int x, int y, Object estrutura) {
        imagem.setRGB(x, y, corSubstituta);
        contadorPixels++;

        if (contadorPixels % INTERVALO_FRAME == 0) {
            salvarFrame();
        }

        Point p = new Point(x, y);
        if (estrutura instanceof Pilha) ((Pilha) estrutura).push(p); // push
        else if (estrutura instanceof Fila) ((Fila) estrutura).enqueue(p);
    }

    private void salvarFrame() {
        try {
            String nome = String.format("%s/frame_%04d.png", pastaSaida, contadorFrames++);
            ImageIO.write(imagem, "png", new File(nome));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
