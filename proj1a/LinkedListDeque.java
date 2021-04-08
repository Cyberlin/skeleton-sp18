public class LinkedListDeque<T> {
    private static class Node<T> {
        T data;
        Node ahead;
        Node next;

        public Node(T x, Node former,Node latter) {
            data = x;
            this.next = latter;
            this.ahead = former;
        }
    }
    private Node pioneer;
    private Node backing;
    private int size;

    public LinkedListDeque() {
        pioneer = new Node<String>("^begin", null, null);
        backing = new Node<String>("^end", null, null);
        pioneer.next = backing;
        pioneer.ahead = backing;
        backing.ahead = pioneer;
        backing.next = pioneer;
        //bug occur, pioneer.ahead should be point to backing  


    }
    public LinkedListDeque(T x) {
        pioneer = new Node<String>("^begin", null, null);
        backing = new Node<String>("^end", null, null);
        pioneer.next = new Node(x, pioneer, backing);
        backing.ahead = pioneer.next;
        size += 1;
    }

    public void addFirst(T item) {
        Node Tmp = new Node(item, pioneer, pioneer.next);
        pioneer.next.ahead=Tmp;
        pioneer.next = Tmp;
        size += 1;

    }
    public void addLast(T item) {
        Node Tmp = new Node(item, backing.ahead, backing);
        backing.ahead.next = Tmp;
        backing.ahead = Tmp;
        size += 1;

    }
    public boolean isEmpty(){
        if (size == 0) {
        return true;
        } else {
        return false;
        }
    }
    public int size(){
        return size;
    }

    public T removeFirst(){
        T Tmp ;
        Tmp = (T) pioneer.next.data;
        //it is weird,why data is a object\
        if (size == 0) {
            return null;
        }
        pioneer.next = pioneer.next.next;
        pioneer.next.ahead = pioneer;
        size = size - 1;
        return Tmp;

    }
    public T removeLast(){
        T Tmp;
        Tmp = (T) backing.ahead.data;
        if (size == 0) {
            return null;
        }
        backing.ahead.ahead.next = backing;
        backing.ahead = backing.ahead.ahead;
        size = size - 1;
        return Tmp;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }

        Node currentNode = pioneer.next;
        while (index != 0) {
            currentNode = currentNode.next;
            index -= 1;
        }
        return (T)currentNode.data;
    }

    public void printDeque() {
        Node p = pioneer.next;
        while (p != backing) {
            System.out.print(p.data+" ");
            p = p.next;
        }
    }

    private T getRecursiveHelper(Node currentNode, int index) {
        if (index == 0) {
            return (T)currentNode.data;
        }

        return getRecursiveHelper(currentNode.next, index - 1);
    }
    /** Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth. If no such items exists, returns null.
     * @Rule: not alter the deque !
     * @Rule: Must use recursion !
     */
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }

        return getRecursiveHelper(pioneer.next, index);
    }

}