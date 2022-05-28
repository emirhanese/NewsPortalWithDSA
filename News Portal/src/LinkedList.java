public class LinkedList<T> {

    Node<T> root;
    
    public LinkedList() {

        this.root = null;
    }

    public void add(T data) {

        Node<T> nodeToBeAdded = new Node<>(data);

        if(this.root == null) {

            this.root = nodeToBeAdded;
        }

        else {

            nodeToBeAdded.next = this.root;
            this.root = nodeToBeAdded;
        }
    }

    public int indexOf(T data) {

        Node<T> temp = this.root;

        int index = 0;

        while(temp != null) {

            if(temp.data == data) {

                break;
            }

            index++;
            temp = temp.next;
        }

        return index;
    }

    public T get(int index) {

        Node<T> temp = this.root;

        int counter = 0;

        while(temp != null) {

            if(counter == index) {

                break;
            }

            counter++;
            temp = temp.next;
        }

        return temp.data;
    }

    public int length() {

        Node<T> temp = this.root;

        int counter = 0;

        while(temp != null) {

            counter++;
            temp = temp.next;
        }

        return counter;
    }

    public void printList() {

        Node<T> temp = this.root;

        int ctr = 0;

        while(temp != null) {

            System.out.println(ctr + 1 + " " + temp.data);
            ctr++;
            temp = temp.next;
        }
    }
}
