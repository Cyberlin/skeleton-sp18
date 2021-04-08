import java.util.Arrays;
import java.util.InputMismatchException;

public class ArrayDeque<T> {
    private final int EXPAND_SCALE =2;
    private final int DEFAULT_CAPACITY = 8;
    private final int MIN_CAPACITY = 64;
    private Object[] items;
    private int size;
    //assuming that p (size*p) is the scale,then expanding or shrinking happens with head being ((p+2)*size/2) and tail being ((p-2)*size/2)
    private int head;
    private int tail;

    public ArrayDeque() {
        items = new Object[DEFAULT_CAPACITY];
    }//head and tail point to the place where next element will be placed

    private static final int dec(int i, int mod) {
        if (--i < 0)
            i = mod - 1;
        return i;
    }
    private static final int inc(int i, int mod) {
        if (++i >= mod) {
            i = 0;
        }
        return i;
    }


    private void grow(int newCapacity) {
        if (newCapacity - Integer.MAX_VALUE > 0) {
            return ;
        }
        int oldCapacity = items.length;
        final Object[] es = items = Arrays.copyOf(items, newCapacity);
        int space = newCapacity - oldCapacity, jump;
        jump = head + space;
        if (head > tail || (head == tail && items[head] != null)) {
            System.arraycopy(items,head,items,jump,oldCapacity-head);
        }

        for (int i = head, to = (head += space); i < to; i++) {
            items[i] = null;//ArrayDeque can't store the value 'null'
        }
    }
    private void trimToSize(int newCapacity) {
        Object[] result = new Object[newCapacity];
        int j = 0;
        int headTmp = head, tailTmp = tail;
        if (items[inc(head, items.length)] == null) {
            for (int i = head; i < tail; i = dec(i, items.length)) {
                result[j++] = items[i];
            }
        } else {
            for (int i = head; i != tail; i = inc(i, items.length)) {
                result[j++] = items[i];
            }
        }
        items = result;
        head = 0;
        tail = head + size;
    }
    //tail指向即将添加的而head指向第一个,两者需要有隔着一个差别
    public void addFirst(T item) {
        if (item == null) {
            return;
        }
        items[head = (dec(head, items.length))]=item;
        size++;
        if (head == tail) {
            grow(EXPAND_SCALE * items.length);
        }
    }
    public void addLast(T item) {
        if (item == null) {
            return;
        }
        items[tail] = item;
        size++;
        if (head == (tail=inc(tail,items.length))) {
            grow(EXPAND_SCALE * items.length);
        }
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            int oldCapacity = items.length;
            double ratio = 1.0;
            ratio = ((double) (size)) / items.length;
            if (items.length > 8 && ratio < 0.25) {
                trimToSize(oldCapacity >> 1);
            }
            final Object es = items[head];
            size--;
            items[head] = null;
            head = inc(head, items.length);
            return (T) es;
        }
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            int oldCapacity = items.length;
            double ratio = 1.0;
            ratio = ((double) (size)) / items.length;
            if (items.length > 10 && ratio < 0.25) {
                trimToSize(oldCapacity >> 1);
            }
            final Object es = items[tail = (dec(tail, items.length))];
            items[tail] = null;
            size--;
            return (T) es;

        }
    }
    public T get(int index) {
        if (index > items.length) {
            return null;
        }
        int cur = head;
        if (items[inc(head, items.length)] == null) {
            for (int i = 0; i < index; i++) {
                cur = dec(cur, items.length);
            }
        } else {
            for (int i = 0; i < index; i++) {
                cur = inc(cur, items.length);
            }
        } 

        return (T) items[cur];
    }
    public boolean isEmpty(){
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size == 0) {
            return;
        } else {
            if (items[inc(head, items.length)] == null) {
                for (int i = head; i != tail; i = dec(i, items.length)) {
                    System.out.print(items[i]);
                }
            } else {
                for (int i = head; i != tail; i = inc(i, items.length)) {
                    System.out.print(items[i]);
                }
            }
        }

    }
}
