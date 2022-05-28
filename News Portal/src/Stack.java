public class Stack<T> {
    
    Node<T> headNode;

    public Stack() {

        this.headNode = null;
    }

    public void push(T title) {

        Node<T> node = new Node<>(title);

        if(headNode == null) {

            this.headNode = node;
        }

        else {

            node.next = this.headNode;
            headNode = node;
        }
    }

    public T pop() {

        T poppedElement = null;

        if(!isEmpty()) {

            poppedElement = headNode.data;
            this.headNode = headNode.next;
        }

        return poppedElement;
    }

    public boolean isEmpty() {

        return this.headNode == null;
    }

    public void printStack() {

        Node<T> temp = headNode;

        while(temp != null) {

            System.out.println("Data: " + temp.data);
            temp = temp.next;
        }
    }
}
