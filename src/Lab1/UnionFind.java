package Lab1;

public class UnionFind {
    int[] parent, rank, setSize;
    int numOfSets;

    public UnionFind(int numOfSets) {
        this.numOfSets = numOfSets;
        parent = new int[numOfSets+1];
        rank = new int[numOfSets+1];
        setSize = new int[numOfSets+1];
        for (int i = 0; i <= numOfSets; i++) {
            // Initially, all elements are in their own set.
            parent[i] = i;
            setSize[i] = 1;
        }
    }

    public int findRepresentative(int i) {
        if (parent[i] == i)//base case
            return i;
        int representative = findRepresentative(parent[i]); //get the representative of the parent
        parent[i] = representative; //update the parent(path compression)
        return representative;
    }

    public boolean isSameSet(int i, int j) {
        //check if they have the same representative.
        return findRepresentative(i) == findRepresentative(j);
    }

    public void unionSet(int i, int j) {
        if (isSameSet(i, j))//if they belong to the same set we do nothing.
            return;
        numOfSets--; // decrease the number of sets by 1 as we will merge two sets together into one.
        int x = findRepresentative(i), y = findRepresentative(j); //get the representative of each member.

        //union by rank
        if (rank[x] > rank[y]) {
            parent[y] = x;
            setSize[x] += setSize[y];
        } else {
            parent[x] = y;
            setSize[y] += setSize[x];
            if (rank[x] == rank[y]) rank[y]++;
        }
    }

    public int numOfDisjointSets() {
        return numOfSets;
    }

    public int sizeOfSet(int i) {
        return setSize[findRepresentative(i)];
    }

}
