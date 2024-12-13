package ai.AI_TriTueNhanTao.knn3;


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Point {
    double x, y;
    char label;

    Point(double x, double y, char label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }

    double distance(Point p) {
        return Math.sqrt((x - p.x) * (x - p.x) + (y - p.y) * (y - p.y));
    }
}

public class KNN3 {
    public static Point[] nhapTrain(String filename) {
        Point train[] = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            train = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                char label = sc.next().charAt(0);
                train[i] = new Point(x, y, label);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return train;
    }

    public static Point[] nhapTest(String filename) {
        Point test[] = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            test = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                test[i] = new Point(x, y, ' ');
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return test;
    }

    public static char phanLoai(List<Point> dataset, int k, Point p) {
        int demCong = 0, demTru = 0;
        for (int i = 0; i < k; i++) {
            char nhan = dataset.get(i).label;
            if (nhan == '+') demCong++;
            else demTru++;
        }
        return (demCong > demTru) ? '+' : '-';
    }

    public static void main(String[] args) {
        Point train[] = nhapTrain("src/ai/knn3/Training.txt");
        Point test[] = nhapTest("src/ai/knn3/Testing.txt");
        int kTest[] = {1, 3, 5, 9};
        List<Point> dsTrain = new ArrayList<>(List.of(train));
        for (Point p : test) {
            System.out.printf("Diem test (%.0f;%.0f):\n", p.x, p.y);
            dsTrain.sort(Comparator.comparingDouble(o -> o.distance(p)));
            System.out.println("Khoang cach sau khi tinh va sap xep: ");
            for (Point q : dsTrain) {
                System.out.printf("Diem (%.0f;%.0f) co dis = %.2f => Nhan: %s%n", q.x, q.y, q.distance(p), q.label);

            }
        }
        for (int k : kTest) {
            System.out.println("k = " + k);
            for (Point p : test) {
                char nhan = phanLoai(dsTrain, k, p);
                System.out.printf("Diem test: (%.0f;%.0f) => Nhan: %s\n",p.x,p.y,nhan);
            }
        }

    }
}
