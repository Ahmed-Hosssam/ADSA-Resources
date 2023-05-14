package Lab9;

import java.util.ArrayList;
import java.util.Arrays;

public class Tree {
    ArrayList<Integer>[] adjL;
    int n, root, logN;

    public Tree(int n, int root, ArrayList<Integer>[] adjL) {
        this.n = n;
        this.root = root;
        this.adjL = adjL;
        this.logN = (int) Math.ceil(Math.log(n) / Math.log(2)) +1;
        preprocessParents();
    }

    public int[] getFurthestNode(int node) { //returns the furthest node and the distance to it
        return getFurthestNode(node, node);
    }

    public int[] getFurthestNode(int currentNode, int parent) {
        int[] answer = {currentNode, 0};//base case

        for (int child : adjL[currentNode]) {
            if (child == parent)
                continue;
            int[] temp = getFurthestNode(child, currentNode);
            if (temp[1] + 1 > answer[1]) {
                answer[1] = temp[1] + 1;
                answer[0] = temp[0];
            }
        }
        return answer;
    }

    /*
     * Algorithm 1: Parent Sparse Table
     */
    static int N, L[], P[][];            // P[i][j] --> the 2^j th ancestor of node i

    void preprocessParents()    // O(n log n)
    {

        P = new int[N][logN];

        for (int i = 0; i < N; i++)
            Arrays.fill(P[i], -1);

        fillParents(root, -1);
    }

    void fillParents(int currentNode, int parent) {
        P[currentNode][0] = parent;

        for (int j = 1; j <= logN; j++)
            if (P[currentNode][j - 1] != -1)
                P[currentNode][j] = P[P[currentNode][j - 1]][j - 1];

        for (int child : adjL[currentNode]) {
            if (child == parent)
                continue;

            fillParents(child, currentNode);
        }
    }

    static int query(int p, int q)        // O(log n)
    {
        //if p is situated on a higher level than q, swap them
        if (L[p] < L[q]) {
            p ^= q;
            q ^= p;
            p ^= q;
        }

        //find largest k such that 2^k is a parent of p
        int k = 0;
        while (1 << k + 1 <= L[p])
            ++k;

        //find the ancestor of p situated on the same level with q
        for (int i = k; i >= 0; --i)
            if (L[p] - (1 << i) >= L[q])
                p = P[p][i];

        if (p == q)
            return p;

        //go up to lowest (non-common) ancestors for p and q
        for (int i = k; i >= 0; --i)
            if (P[p][i] != -1 && P[p][i] != P[q][i]) {
                p = P[p][i];
                q = P[q][i];
            }

        return P[p][0];
    }
}
