package Lab2;

public class SegmentTree {
    // Range Sum Query (with lazy propagation)
    // 1-based DS, OOP

    int N;            //the number of elements in the array as a power of 2 (i.e. after padding)
    int[] sTree, lazy;

    SegmentTree(int size) {
        N = 1;
        while (N < size) //padding
            N = 2 * N;

        sTree = new int[2 * N];        //no. of nodes = 2*N - 1, we add one to cross out index zero
        lazy = new int[2 * N];
    }

    void update_point(int index, int val)            // adds the value to the given position in O(log n)
    {
        index += N - 1;  //the actual index in the segment tree
        sTree[index] += val; //update the value

        while (index > 1) { //update the ancestors
            index = index / 2;
            sTree[index] = sTree[2 * index] + sTree[2 * index + 1];
        }
    }


    void update_range(int left, int right, int val)        // adds the value to the given interval in O(log n)
    {
        update_range(1, 1, N, left, right, val);
    }

    void update_range(int node, int nodeStart, int nodeEnd, int left, int right, int val) {
        if (left > nodeEnd || right < nodeStart) // if the current node's interval doesn't intersect with the required interval(they are disjoint), return
            return;

        if (nodeStart >= left && nodeEnd <= right) { // if the current node's interval is completely covered by the required interval,  update the whole range
            sTree[node] += (nodeEnd - nodeStart + 1) * val;
            lazy[node] += val;
        } else {
            //update the left and right children

            int mid = (nodeStart + nodeEnd) / 2;

            propagate(node, nodeStart, mid, nodeEnd); //lazy propagation

            update_range(2 * node, nodeStart, mid, left, right, val);
            update_range(2 * node + 1, mid + 1, nodeEnd, left, right, val);

            sTree[node] = sTree[2 * node] + sTree[2 * node + 1];
        }

    }

    void propagate(int node, int b, int mid, int e) {
        //update the children of the lazy node
        lazy[2 * node] += lazy[node];
        lazy[2 * node + 1] += lazy[node];

        //update the children of the node
        sTree[2 * node] += (mid - b + 1) * lazy[node];
        sTree[2 * node + 1] += (e - mid) * lazy[node];

        //reset the lazy node
        lazy[node] = 0;
    }

    int query(int left, int right) { //gets the value of the given range in O(log n)
        return query(1, 1, N, left, right);
    }

    int query(int node, int nodeStart, int nodeEnd, int left, int right)
    {
        if (left > nodeEnd || right < nodeStart) // if the current node's interval doesn't intersect with the required interval(they are disjoint), return neutral value
            return 0;

        if (nodeStart >= left && nodeEnd <= right)// if the current node's interval is completely covered by the required interval,  return the value of the node
            return sTree[node];

        //get the value from the children

        int mid = (nodeStart + nodeEnd) / 2;

        propagate(node, nodeStart, mid, nodeEnd);//lazy propagation

        int leftChildQuery = query(2 * node, nodeStart, mid, left, right);
        int rightChildQuery = query(2 * node + 1, mid + 1, nodeEnd, left, right);

        return leftChildQuery + rightChildQuery;

    }
}
