public class Pilha {
    private Node top;
    private int size;

    public Pilha() {
        this.top = null;
        this.size = 0;
    }

    public void push(Point e) {
        Node newNode = new Node(e);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public Point pop() {
        if (isEmpty()) return null;
        Point p = top.value;
        top = top.next;
        size--;
        return p;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}