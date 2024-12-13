package ai.AI_TriTueNhanTao.NBayes;
import java.io.File;
import java.util.List;
import java.util.Scanner;

class Point1 {
    double a, b, c;
    char label;

    Point1(double a, double b, double c, char label) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.label = label;
    }
}

public class NaiveBayes {

    public static Point1[] nhapTrain(String fileName) {
        Point1[] train = null;
        try {
            Scanner sc = new Scanner(new File(fileName));
            int n = sc.nextInt();
            train = new Point1[n];
            for (int i = 0; i < n; i++) {
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                char label = sc.next().charAt(0);
                train[i] = new Point1(a, b, c, label);
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return train;
    }

    public static Point1[] nhapTest(String fileName) {
        Point1[] test = null;
        try {
            Scanner sc = new Scanner(new File(fileName));
            int n = sc.nextInt();
            test = new Point1[n];
            for (int i = 0; i < n; i++) {
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                test[i] = new Point1(a, b, c, ' '); // Không có nhãn
            }
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
        return test;
    }

    public static double tinhXS(List<Point1> dataset, char label) {
        double dem = 0;
        for (Point1 p : dataset) {
            if (p.label == label) {
                dem++;
            }
        }
        double xs = dem / dataset.size();
        System.out.printf("Xác suất P(%c) = %.4f\n", label, xs);
        return xs;
    }

    public static double tinhXSDK(List<Point1> dataset, char label, char feature, double value) {
        int demLabel = 0, demFeature = 0;
        for (Point1 p : dataset) {
            if (p.label == label) {
                demLabel++;
                if ((feature == 'a' && p.a == value) || (feature == 'b' && p.b == value) || (feature == 'c' && p.c == value)) {
                    demFeature++;
                }
            }
        }
        double xs = demLabel == 0 ? 0 : (double) demFeature / demLabel;
        System.out.printf("P(%c|%c = %.0f) = %.4f\n", label, feature, value, xs);
        return xs;
    }

    public static char duDoan(Point1 test, List<Point1> train) {
        System.out.printf("\nDự đoán cho điểm: a = %.0f, b = %.0f, c = %.0f như sau: \n", test.a, test.b, test.c);
        double pCong = tinhXS(train, '+');
        double pTru = tinhXS(train, '-');
        double pAcong = tinhXSDK(train, '+', 'a', test.a);
        double pBcong = tinhXSDK(train, '+', 'b', test.b);
        double pCcong = tinhXSDK(train, '+', 'c', test.c);
        double pAtru = tinhXSDK(train, '-', 'a', test.a);
        double pBtru = tinhXSDK(train, '-', 'b', test.b);
        double pCtru = tinhXSDK(train, '-', 'c', test.c);

        double pCongTest = pCong * pAcong * pBcong * pCcong;
        double pTruTest = pTru * pAtru * pBtru * pCtru;

        System.out.printf("P(+|Test)  = %.4f\n", pCongTest);
        System.out.printf("P(-|Test)  = %.4f\n", pTruTest);

        return pCongTest > pTruTest ? '+' : '-';
    }

    public static void main(String[] args) {
        Point1[] train = nhapTrain("src/ai/NBayes/Training.txt");
        Point1[] test = nhapTest("src/ai/NBayes/Testing.txt");
        List<Point1> dsTrain = List.of(train);

        for (Point1 p : test) {
            char label = duDoan(p, dsTrain);
            System.out.printf("Kết quả dự đoán: Nhãn -> %c\n", label);
        }
    }
}

