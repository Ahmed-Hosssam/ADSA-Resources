package Lab3;

public class BinaryTrie {
    Node root;
    
    public BinaryTrie(){
        root = new Node();
    }
    
    void insert(int value) {
        Node cur = root;
        for (int i = 30; i >= 0; i--) {
            //TODO loop through the bits and insert them one by one
        }
    }

    int getMax(int value) {
        int answer = 0;
        Node cur = root;
        for (int i = 30; i >= 0; i--) {
            //TODO construct integer X such that X xor value is maximized
        }
        return answer;
    }


    static class Node {
        Node one, zero;

        public Node() {
        }
    }
}
