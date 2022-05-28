import java.util.ArrayList;

public class BinarySearchTree {
    
    private class BSTNode {

        News data;
        BSTNode right;
        BSTNode left;

        BSTNode(News data) {

            this.data = data;
            this.right = null;
            this.left = null;
        }
    }

    BSTNode root;
    ArrayList<News> treeValues = new ArrayList<>();

    public BinarySearchTree () {

        this.root = null;
    }

    public void insert(News data) {

        this.root = insertRec(root, data);
    }

    public BSTNode getRoot() {

        return this.root;
    }

    private BSTNode insertRec(BSTNode root, News data) {

        if(root == null) {

            root = new BSTNode(data);
            this.treeValues.add(data);
            return root;
        }

        if(data.getViewsCounter() > root.data.getViewsCounter()) {

            root.right = insertRec(root.right, data);
        }

        else if(data.getViewsCounter() <= root.data.getViewsCounter()) {

            root.left = insertRec(root.left, data);
        }

        return root;
    }

    public void printInorder() {

        inorderRec(this.root);
    }

    private boolean notViewedYet() {

        for(News item : this.treeValues) {

            if(item.getViewsCounter() != 0)
                return false;
        }

        return true;
    }

    private void inorderRec(BSTNode root) {

        if(root != null) {

            inorderRec(root.left);
            System.out.println("Category: " + root.data.getCategory() + "\nNumber of views: " + root.data.getViewsCounter());
            inorderRec(root.right);
        }
    }

    private ArrayList<News> getTreeValues() {

        ArrayList<News> temp = new ArrayList<>();

        for(News el : this.treeValues) {

            temp.add(el);
        }

        return temp;
    }

    private void clearTree() {

        this.root = null;
        this.treeValues.clear();
    }

    public void updateTree() {

        ArrayList<News> temp = getTreeValues();
        clearTree();

        for(News item : temp) {

            insert(item);
        }
    }

    public String getMostViewedCategory(BSTNode node) {

        if(notViewedYet())
            return "You haven't read any news yet.";

        while(node.right != null) {

            node = node.right;
        }

        return node.data.getCategory();
    }
}
