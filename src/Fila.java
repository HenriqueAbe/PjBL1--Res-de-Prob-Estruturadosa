public class Fila {
    private Node first;
    private Node top;
    private int size;

    public Fila() {
        this.first = null;
        this.top = null;
        this.size = 0;
    }

    public void enqueue(Point e) {
        Node newNode = new Node(e);
        if (isEmpty()) {
            first = newNode;
            top = newNode;
        } else {
            top.next = newNode;
            top = newNode;
        }
        size++;
    }

    public Point dequeue() {
        if (isEmpty()) return null;
        Point p = first.value;
        first = first.next;
        if (first == null) {
            top = null;
        }
        size--;
        return p;
    }

    public boolean isEmpty() {
        return first == null;
    }
}
