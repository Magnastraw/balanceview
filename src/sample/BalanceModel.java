package sample;

public class BalanceModel {

    private double a = 0, b = 1, sigma = 0.5, eps = 0.1, k_pos = 0.3, k1 = 2.0, k2 = 3.0, h, tau, Ua = 0.0, Ub = 1.0, Meps;

    private int N = 10;
    private double[] U0;
    private double[] U1;

    private double[] A;
    private double[] B;
    private double[] C;
    private double[] F;

    private double[] X = new double[N + 1];

    public double[] getU() {
        return U0;
    }

    public double[] getX() {
        return X;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setB(double b) {
        this.b = b;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public void setN(int N) {
        this.N = N;
    }

    public void setK1(double k1) {
        this.k1 = k1;
    }

    public void setK2(double k2) {
        this.k2 = k2;
    }

    private double[] progonka(int n, double[] a, double[] c, double[] b, double[] f) {
        double m;
        double[] x = new double[n + 1];

        for (int i = 1; i < n; i++) {
            m = a[i] / c[i - 1];
            c[i] = c[i] - m * b[i - 1];
            f[i] = f[i] - m * f[i - 1];
        }
        x[n - 1] = f[n - 1] / c[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            x[i] = (f[i] - b[i] * x[i + 1]) / c[i];
        }


        return x;
    }

    private double k(double x) {
        if ((x >= a) && (x <= k_pos)) {
            return k1;
        } else if ((x >= k_pos) && (x <= b)) {
            return k2;
        } else {
            throw new RuntimeException("Error k1-k2 input");
        }
    }

    private double x_n1(double x) {
        return (2 * k(x + h) * k(x)) / (k(x) + k(x + h));
    }

    private double x_n(double x) {
        return (2 * k(x - h) * k(x)) / (k(x) + k(x - h));
    }

    public void balance() {
        U0 = new double[N + 1];
        U1 = new double[N + 1];

        A = new double[N];
        B = new double[N];
        C = new double[N];
        F = new double[N];

        X = new double[N + 1];

        h = (b - a) / N;

        tau = h;

        for (int i = 0; i < N + 1; i++) {
            X[i] = a + i * h;
        }

        for (int i = 0; i < N + 1; i++) {
            U0[i] = Ua;
        }

        for (int i = 1; i < N; i++) {
            A[i-1] = -sigma * x_n(a + i * h);
            C[i-1] = -sigma * x_n1(a + i * h);
            B[i-1] = (h * h) / (tau) - C[i] - A[i];//(возможно он тут вынес одно К)
        }

        do {
            Meps = 0.0;

            for (int i = 1; i < N; i++) {
                F[i-1] = ((1 - sigma) * x_n(a + i * h)) * U0[i - 1] +
                        (((h * h) / tau) - (1 - sigma) * x_n(a + i * h) - (1 - sigma) * x_n1(a + i * h)) * U0[i] +
                        ((1 - sigma) * x_n1(a + i * h)) * U0[i + 1];
            }

            System.out.println("F:");
            for (int i = 0; i < N; i++) {
                System.out.println(F[i]);
            }

            U1[0] = Ua;
            U1[N] = Ub;
            F[0] -= A[0] * U1[0];//??
            F[N - 1] -= C[N - 1] * U1[N];//??
            System.out.println("F1:");
            for (int i = 0; i < N; i++) {
                System.out.println(F[i]);
            }
            for (int i = 1; i < N - 1; i++) {
                B[i + 1] = B[i + 1] * B[i] - A[i + 1] * C[i];
                C[i + 1] = C[i + 1] * B[i];
                F[i + 1] = F[i + 1] * B[i] - A[i + 1] * F[i];
            }


            U1[N - 1] = F[N - 1] / B[N - 1];
            for (int i = N - 2; i > 0; i--) {
                U1[i] = (F[i] - C[i] * U1[i + 1]) / B[i];
            }


            // U1 = progonka(N,A,B,C,F);

            for (int i = 1; i < N; i++) {
                A[i] = -sigma * x_n(a + i * h);
                C[i] = -sigma * x_n1(a + i * h);
                B[i] = (h * h) / (tau) - C[i] - A[i];//(возможно он тут вынес одно К)
            }

            for (int i = 0; i < N + 1; i++) {
                if (Math.abs(U0[i] - U1[i]) >= Meps) {
                    Meps = Math.abs(U0[i] - U1[i]);
                }
                U0[i] = U1[i];
            }
            System.out.println("U:");

            for (int i = 0; i < N + 1; i++) {
                System.out.println(U0[i]);
            }
        } while (Meps > eps);


        for (int i = 0; i < N + 1; i++) {
            System.out.println(U0[i]);
        }
    }


}
