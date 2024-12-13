package ai.AI_TriTueNhanTao.knn2;


import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Point {
    double x;
    char y;
    double dis;

    public Point(double x, char y) {
        this.x = x;
        this.y = y;
    }

    public double tinhKc(double z) {
        this.dis = Math.abs(this.x - z);
        return this.dis;
    }
}

public class KNN2 {

    public static Point[] nhapTraining(String filename) {
        Point[] diemTrain = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            diemTrain = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                char y = sc.next().charAt(0);
                diemTrain[i] = new Point(x, y);
            }
        } catch (Exception e) {
            System.out.println("Loi: " + e.getMessage());
        }
        return diemTrain;
    }

    public static char phanLoai(List<Point> dataset, int k, double z) {

        int demCong = 0, demTru = 0;
        for (int i = 0; i < k; i++) {
            char nhan = dataset.get(i).y;
            if (nhan == '+') demCong++;
            else demTru++;
        }
        return (demCong > demTru) ? '+' : '-';
    }

    public static void main(String[] args) {
        Point diemTrain[] = nhapTraining("src/ai/knn2/Training.txt");
        List<Point> dsTrain = new ArrayList<>(List.of(diemTrain));
        double z = 5.0;
        int kTest[] = {1, 3, 5, 9};
        dsTrain.sort(Comparator.comparingDouble(p -> p.tinhKc(z)));
        System.out.println("Khoang cach sau khi sap xep:");
        for (Point p : dsTrain) {
            System.out.printf("Diem: %.1f co dis = %.1f => %c\n", p.x, p.dis, p.y);
        }
        for (int k : kTest) {
            System.out.println("Voi k = " + k + " => " + phanLoai(dsTrain, k, z));
        }
    }

}

