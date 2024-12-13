package ai.AI_TriTueNhanTao.ann.singlelayer.and;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Point {
    double x, y, z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

public class SingleLayer {
    public static Point[] nhapTrain(String filename) {
        Point[] diemTrain = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            diemTrain = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                double z = sc.nextDouble();
                diemTrain[i] = new Point(x, y, z);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file " + filename);
        }
        return diemTrain;
    }

    public static Point[] nhapTest(String filename) {
        Point[] diemTest = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            diemTest = new Point[n];
            for (int i = 0; i < n; i++) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                diemTest[i] = new Point(x, y, 0);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file " + filename);
        }
        return diemTest;
    }

    public static void duDoan( Point[] test) {
        for (Point p : test) {
            p.z = (p.x == 1 && p.y == 1) ? 1 : 0;
        }
    }

    public static void main(String[] args) {
        Point diemTrain[] = nhapTrain("src/ai/ann/singlelayer/and/Trainning.txt");
        Point diemTest[] = nhapTest("src/ai/ann/singlelayer/and/Testing.txt");

        System.out.printf("Du doan ket qua cho %d diem test\n", diemTest.length);
        duDoan(diemTest);
        List<Point> dsTest = new ArrayList<>(List.of(diemTest));
        for (Point p : dsTest) {
            System.out.printf("x = %.0f, y = %.0f, z = %.0f\n", p.x, p.y, p.z);
        }
    }
}