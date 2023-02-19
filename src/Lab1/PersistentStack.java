package Lab1;

import java.util.ArrayList;

public class PersistentStack {
    ArrayList<Node> versions;

    public PersistentStack() {
        versions = new ArrayList<>();
        versions.add(new Node());
    }

    public void push(int value, int version) {

    }

    public Integer pop(int version) {
        return null;
    }

    public Integer getMin(int version) {
        return null;
    }


    static class Node {
        Integer top, min;
        Node parent;

        public Node() {

        }

        public Node(Integer top, Node parent) {
            this.top = top;
            this.parent = parent;

            if (this.parent == null || this.parent.min == null) this.min = this.top;
            else this.min = Math.min(this.parent.min, this.top);
        }

        public Node getParent() {
            if (parent == null)
                return new Node();
            return parent;
        }
    }
}


