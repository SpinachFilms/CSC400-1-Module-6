import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== CustomLinkedList Demo ===");

        CustomLinkedList list = new CustomLinkedList();

        // 1) Basic insertion demo
        list.insert(1);
        list.insert(2);
        list.insert(3);
        System.out.print("After insert(1,2,3): ");
        printList(list); // expected: 1 2 3

        // 2) Deletion demo (delete first occurrence)
        boolean removed = list.delete(2);
        System.out.println("delete(2) -> " + removed);
        System.out.print("After delete(2): ");
        printList(list); // expected: 1 3

        // 3) Optional: load from file if provided
        if (args.length > 0) {
            String path = args[0];
            try {
                int loaded = list.loadFromFile(path);
                System.out.println("Loaded " + loaded + " integers from file: " + path);
                System.out.print("After loading from file: ");
                printList(list);
            } catch (FileNotFoundException e) {
                System.out.println("Could not read file: " + path + " (" + e.getMessage() + ")");
            }
        } else {
            System.out.println("Tip: run with a file path to load more integers, e.g.,");
            System.out.println("     java Main numbers.txt");
        }

        // 4) Final size
        System.out.println("Final size: " + list.size());
        System.out.println("=== End Demo ===");
    }

    /** Utility: iterate with the custom iterator and print elements on one line. */
    private static void printList(CustomLinkedList list) {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
    }
}

class CustomLinkedList implements Iterable<Integer> {

    /** Node for singly linked list. */
    private static class Node {
        int data;
        Node next;

        Node(int data) { this.data = data; }
    }

    private Node head;
    private int size;

    /** Inserts a new node with the given data at the end of the list. */
    public void insert(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = newNode;
        }
        size++;
    }

    public boolean delete(int data) {
        if (head == null) return false;

        if (head.data == data) {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node cur = head.next;

        while (cur != null && cur.data != data) {
            prev = cur;
            cur = cur.next;
        }

        if (cur == null) return false;

        prev.next = cur.next;
        size--;
        return true;
    }

    /** @return the current number of elements in the list. */
    public int size() {
        return size;
    }

    /**
     * Reads integers from a text file and appends them to the list.
     * Accepts whitespace and comma separators; supports negative numbers.
     * Examples of valid content:
     *   10 20 30
     *   1,2,3,-4, 5
     *
     * @param path Path to the text file
     * @return number of integers successfully read
     * @throws FileNotFoundException if the file cannot be opened
     */
    public int loadFromFile(String path) throws FileNotFoundException {
        int count = 0;
        try (Scanner sc = new Scanner(new File(path))) {
            // Allow commas or whitespace as delimiters; keep minus sign with numbers.
            sc.useDelimiter("[,\\s]+");
            while (sc.hasNext()) {
                if (sc.hasNextInt()) {
                    int value = sc.nextInt();
                    insert(value);
                    count++;
                } else {
                    // Skip non-integer token (robustness)
                    sc.next();
                }
            }
        }
        return count;
    }

    /** Returns an iterator for traversing the linked list. */
    public Iterator<Integer> iterator() {
        return new LinkedListIterator();
    }

    /** Iterator implementation for CustomLinkedList. */
    private class LinkedListIterator implements Iterator<Integer> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the list.");
            }
            int value = current.data;
            current = current.next;
            return value;
        }
    }
}
