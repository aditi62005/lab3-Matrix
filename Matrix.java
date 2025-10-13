import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Matrix {
    private int[][] data;

    public Matrix(int rows, int cols) {
        if (rows <= 0 || cols <= 0) throw new IllegalArgumentException("Invalid size");
        this.data = new int[rows][cols];
    }

    public Matrix(int[][] data) {
        if (data == null || data.length == 0 || data[0].length == 0)
            throw new IllegalArgumentException("Invalid array");
        int r = data.length, c = data[0].length;
        this.data = new int[r][c];
        for (int i = 0; i < r; i++) {
            if (data[i].length != c) throw new IllegalArgumentException("Jagged array");
            System.arraycopy(data[i], 0, this.data[i], 0, c);
        }
    }

    public void populateRandom() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[0].length; j++)
                data[i][j] = rnd.nextInt(1, 11);
    }

    public Matrix add(Matrix other) {
        if (other == null) throw new IllegalArgumentException("null");
        if (data.length != other.data.length || data[0].length != other.data[0].length)
            throw new IllegalArgumentException("Dimension mismatch");
        int r = data.length, c = data[0].length;
        Matrix res = new Matrix(r, c);
        for (int i = 0; i < r; i++)
            for (int j = 0; j < c; j++)
                res.data[i][j] = this.data[i][j] + other.data[i][j];
        return res;
    }

    public Matrix multiply(Matrix other) {
        if (other == null) throw new IllegalArgumentException("null");
        int r1 = data.length, c1 = data[0].length, r2 = other.data.length, c2 = other.data[0].length;
        if (c1 != r2) throw new IllegalArgumentException("Dimension mismatch");
        Matrix res = new Matrix(r1, c2);
        for (int i = 0; i < r1; i++) {
            for (int k = 0; k < c1; k++) {
                int a = this.data[i][k];
                if (a == 0) continue;
                for (int j = 0; j < c2; j++) {
                    res.data[i][j] += a * other.data[k][j];
                }
            }
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(Arrays.toString(data[i]));
            if (i != data.length - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public int rows() { return data.length; }
    public int cols() { return data[0].length; }

    public static void main(String[] args) {
        Matrix A = new Matrix(2, 3);
        Matrix B = new Matrix(3, 2);
        A.populateRandom();
        B.populateRandom();
        System.out.println("A:\n" + A);
        System.out.println("B:\n" + B);
        System.out.println("A * B:\n" + A.multiply(B));
        Matrix C = new Matrix(new int[][]{{1,2,3},{4,5,6}});
        Matrix D = new Matrix(new int[][]{{7,8,9},{10,11,12}});
        System.out.println("C + D:\n" + C.add(D));
    }
}