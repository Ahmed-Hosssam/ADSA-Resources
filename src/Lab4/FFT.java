package Lab4;

import java.util.Arrays;

public class FFT {

    static class Complex {
        double real, img;

        public Complex(double real, double img) {
            this.real = real;
            this.img = img;
        }

        public Complex(double theta) {
            this(Math.cos(theta), Math.sin(theta));
        }

        public void mul(Complex x) {
            double newReal = real * x.real - img * x.img;
            double newImg = img * x.real + x.img * real;
            this.real = newReal;
            this.img = newImg;
        }

        @Override
        public String toString() {
            return "Complex{" +
                    "real=" + real +
                    ", img=" + img +
                    '}';
        }
    }

    static Complex mul(Complex a, Complex b) {
        return new Complex(a.real * b.real - a.img * b.img, a.img * b.real + a.real * b.img);
    }

    static Complex add(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.img + b.img);
    }

    public static Complex[] fft(Complex[] A, boolean invert) { // Length of A must be a power of 2
        if (A.length == 1) {
            return new Complex[]{new Complex(A[0].real, A[0].img)};
        }
        // A(X) = A0(X^2) + X * A1(X^2)
        // we want to substitute by nth roots of unity
        // For example if n == 8: sub by w8 ^ 0, w8 ^ 1, w8 ^ 2, ... w8 ^ 7
        int n = A.length;
        Complex[] A0 = new Complex[n / 2]; // even indices
        Complex[] A1 = new Complex[n / 2]; // odd indices
        for (int i = 0; i < n; i += 2) {
            A0[i / 2] = A[i];
            A1[i / 2] = A[i + 1];
        }
        // sub in both even and odd coefficients by using the (n/2)th roots of unity
        // sub by w8 ^ 0, w8 ^ 2, w8 ^ 4, w8 ^ 6 (the squares of the nth roots of unity)
        // which are equal to w4 ^ 0, w4 ^ 1, w4 ^ 2, w4 ^ 3 (the (n/2)th roots of unity)
        Complex[] A0FFT = fft(A0, invert);
        Complex[] A1FFT = fft(A1, invert);

        // FFT we use wn , inverse FFT we use -wn
        Complex wn = new Complex(2 * Math.PI / n * (invert ? -1 : 1));
        Complex w = new Complex(1, 0); // this should be wn ^ i in iteration i

        Complex[] AFFT = new Complex[n];

        // A(X) = A0(X^2) + X * A1(X^2)
        // note that A0FFT now has A0(w4 ^ 0),A0(w4 ^ 1), A0(w4 ^ 2), A0(w4 ^ 3)
        // which are equal to A0((w8 ^ 0) ^ 2),A0((w8 ^ 1) ^ 2), A0((w8 ^ 2) ^ 2), A0((w8 ^ 3) ^ 2)
        // and are also equal to A0((w8 ^ 4) ^ 2),A0((w8 ^ 5) ^ 2), A0((w8 ^ 6) ^ 2), A0((w8 ^ 7) ^ 2)
        // A1FFT is similar
        for (int i = 0; i < n; i++, w.mul(wn)) {
            if (i < n / 2) {
                AFFT[i] = add(A0FFT[i], mul(w, A1FFT[i]));
            } else {
                AFFT[i] = add(A0FFT[i - n / 2], mul(w, A1FFT[i - n / 2]));
            }
            // divide by 2 if we do inverse FFT
            if (invert) {
                AFFT[i].real /= 2;
                AFFT[i].img /= 2;
            }
        }
        return AFFT;
    }

    public static int[] multiply(int[] A, int[] B) {
        int N = 1;
        while (N < A.length + B.length) {
            N <<= 1;
        }
        Complex[] CA = new Complex[N];
        Complex[] CB = new Complex[N];

        for (int i = 0; i < N; i++) {
            if (i < A.length)
                CA[i] = new Complex(A[i], 0);
            else
                CA[i] = new Complex(0, 0);
        }

        for (int i = 0; i < N; i++) {
            if (i < B.length)
                CB[i] = new Complex(B[i], 0);
            else
                CB[i] = new Complex(0, 0);
        }

        Complex[] FFTA = fft(CA, false);
        Complex[] FFTB = fft(CB, false);

        for (int i = 0; i < N; i++) {
            FFTA[i].mul(FFTB[i]);
        }

        Complex[] res = fft(FFTA, true);

        int[] intres = new int[N];
        for (int i = 0; i < N; i++) {
            intres[i] = (int) Math.round(res[i].real);
        }

        return intres;
    }

    public static void main(String[] args) {
        int[] A = {2, 1};
        int[] B = {1, 2};
        int[] C = multiply(A, B);
        System.out.println(Arrays.toString(C));
    }
}
