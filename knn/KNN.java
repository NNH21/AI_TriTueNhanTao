package ai.AI_TriTueNhanTao.knn;

import java.io.*;
import java.util.*;

class Point {
    double x, y;
    char label;

     Point(double x, double y, char label) {
        this.x = x;
        this.y = y;
        this.label = label;
    }
    double dis(Point p){
         return Math.sqrt(Math.pow(x - p.x, 2) +Math.pow(y - p.y, 2));
    }
}

public class KNN {
    public static Point[] nhapTrain(String filename) {
        Point[] points = null;
        try (Scanner sc = new Scanner(new File(filename))) {
            int n = sc.nextInt();
            points = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                char label = sc.next().charAt(0);
                points[i] = new Point(x, y, label);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không thể mở file " + filename);
        }
        return points;
    }

    public static Point[] nhapTest(String filename) {
        Point[] points = null;
        try (Scanner sc = new Scanner(new File(filename))) {
            int n = sc.nextInt();
            points = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                points[i] = new Point(x, y, ' ');
            }
        } catch (Exception e) {
            System.out.println("Không thể mở file " + filename);
        }
        return points;
    }

    public static char KNN2(List<Point> dataset, int k, Point p){
        int demA = 0, demB = 0;
        for(int i = 0; i < k; i++){
            char nhan = dataset.get(i).label;
            if(nhan == 'A') demA++;
            else demB++;
        }
        return (demA > demB) ? 'A' : 'B';
    }

    public static void main(String[] args) {
        Point[] training = nhapTrain("src/ai/knn/Training.txt");
        Point[] testing = nhapTest("src/ai/knn/Testing.txt");

        if (training == null || training.length == 0) {
            System.out.println("Không có dữ liệu training!");
            return;
        }
        List<Point> dsTrain = new ArrayList<>(List.of(training));
        int kTest[] = {1,3,5,7};

        for (Point p : testing) {
            System.out.printf("Diem test (%.1f,%.1f):\n", p.x, p.y);
            dsTrain.sort(Comparator.comparingDouble(o -> o.dis(p)));
            System.out.println("Khoang cach sau khi tinh va sap xep: ");
            for (Point q : dsTrain) {
                System.out.printf("Diem (%.1f,%.1f) co dis = %.2f => Nhan: %s%n", q.x, q.y, q.dis(p), q.label);

            }
        }
        for (int k : kTest) {
            System.out.println("k = " + k);
            for (Point p : testing) {
                char nhan = KNN2(dsTrain, k, p);
                System.out.printf("Diem test: (%.1f,%.1f) => Nhan: %s\n",p.x,p.y,nhan);
            }
        }
    }
}