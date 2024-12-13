package ai.AI_TriTueNhanTao.NBayes;


import java.io.File;
import java.util.List;
import java.util.Scanner;

class Point {
    double a, b, c;
    char label;

    Point(double a, double b, double c, char label) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.label = label;
    }
}

public class NBayes1 {

    public static Point[] nhapTrain(String fileName) {
        Point[] train = null;
        try {
            Scanner sc = new Scanner(new File(fileName));
            int n = sc.nextInt();
            train = new Point[n];
            for (int i = 0; i < n; i++) {
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                char label = sc.next().charAt(0);
                train[i] = new Point(a, b, c, label);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return train;
    }

    public static Point[] nhapTest(String fileName) {
        Point[] test = null;
        try {
            Scanner sc = new Scanner(new File(fileName));
            int n = sc.nextInt();
            test = new Point[n];
            for (int i = 0; i < n; i++) {
                double a = sc.nextDouble();
                double b = sc.nextDouble();
                double c = sc.nextDouble();
                test[i] = new Point(a, b, c, ' ');
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return test;
    }

    public static double tinhXS(List<Point> dataset, char label) {
        double count = 0;
        for (Point p : dataset) {
            if (p.label == label) {
                count++;
            }
        }
        return count / dataset.size();
    }

    public static double tinhXSDK(List<Point> dataset, char label, char feature, double value) {
        int demLabel = 0, demFeature = 0;
        for (Point p : dataset) {
            if (p.label == label) {
                demLabel++;
                if ((feature == 'a' && p.a == value) || (feature == 'b' && p.b == value) || (feature == 'c' && p.c == value)) {
                    demFeature++;
                }
            }
        }
        return demLabel == 0 ? 0 : demFeature / demLabel;
    }

    public static char duDoan(Point test, List<Point> train) {
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
        return pCongTest > pTruTest ? '+' : '-';
    }

    public static void main(String[] args) {
        Point[] train = nhapTrain("src/ai/NBayes/Training.txt");
        Point[] test = nhapTest("src/ai/NBayes/Testing.txt");
        List<Point> dsTrain = List.of(train);
        double pCong = tinhXS(dsTrain, '+');
        double pTru = tinhXS(dsTrain, '-');
        for (Point p : test) {
            char label = duDoan(p, dsTrain);
            System.out.printf("Du doan cho diem: %.0f %.0f %.0f => Nhan: %c\n", p.a, p.b, p.c, label);

        }
    }
}
