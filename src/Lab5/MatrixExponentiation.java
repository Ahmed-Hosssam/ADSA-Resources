package Lab5;

public class MatrixExponentiation {
    public static Matrix matPow(Matrix base, long power) {
        int n = base.rows;
        Matrix res = Matrix.Identity(n);
        while (power > 0) {
            if ((power & 1) == 1)
                res = res.multiply(base);
            base = base.multiply(base);
            power >>= 1;
        }
        return res;
    }

    public static class Matrix {
        int[][] mat;
        int rows, cols;

        public Matrix(int[][] mat) {
            this.mat = mat;
            rows = mat.length;
            cols = mat[0].length;
        }

        public static Matrix Identity(int n) {
            int[][] mat = new int[n][n];
            for (int i = 0; i < n; i++)
                mat[i][i] = 1;
            return new Matrix(mat);
        }

        public Matrix multiply(Matrix other) {
            int[][] res = new int[rows][other.cols];
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < other.cols; j++)
                    for (int k = 0; k < cols; k++)
                        res[i][j] += mat[i][k] * other.mat[k][j];
            return new Matrix(res);
        }
    }
}
