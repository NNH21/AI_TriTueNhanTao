package ai.AI_TriTueNhanTao.NBayes1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NBayes2 {
    static class TrainSample {
        String[] features;
        String label;

        public TrainSample(String[] features, String label) {
            this.features = features;
            this.label = label;
        }
    }

    public static TrainSample[] nhapTrain(String filename) {
        TrainSample[] train = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            train = new TrainSample[n];
            for (int i = 0; i < n; i++) {
                String[] features = new String[4];
                for (int j = 0; j < 4; j++) {
                    features[j] = sc.next();
                }
                String label = sc.next();
                train[i] = new TrainSample(features, label);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Khong the mo file: " + filename);
        }
        return train;
    }

    public static TrainSample[] nhapTest(String filename) {
        TrainSample[] test = null;
        try {
            Scanner sc = new Scanner(new File(filename));
            int n = sc.nextInt();
            test = new TrainSample[n];
            for (int i = 0; i < n; i++) {
                String[] features = new String[4];
                for (int j = 0; j < 4; j++) {
                    features[j] = sc.next();
                }
                test[i] = new TrainSample(features, null);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Khong the mo file: " + filename);
        }
        return test;
    }

    public static double tinhXS(TrainSample[] dataset, String label) {
        double count = 0;
        for (TrainSample sample : dataset) {
            if (sample.label.equals(label)) {
                count++;
            }
        }
        return count / dataset.length;
    }

    public static double tinhXSDK(TrainSample[] dataset, String label, int index, String value) {
        double dLabel = 0;
        double dFeature = 0;
        for (TrainSample sample : dataset) {
            if (sample.label.equals(label)) {
                dLabel++;
                if (sample.features[index].equals(value)) {
                    dFeature++;
                }
            }
        }
        return dFeature / dLabel;
    }

    public static String duDoan(TrainSample[] train, TrainSample testSample) {
        String[] labels = {"Y", "N"};
        double[] probs = new double[labels.length];
        double maxProb = -1;
        String bestLabel = null;
        System.out.println("------------------------------------------------------");
        System.out.println(" \uD83D\uDEA7 Dự đoán:");
        for (int j = 0; j < labels.length; j++) {
            probs[j] = tinhXS(train, labels[j]);
            System.out.printf(" \uD83D\uDE06 P(%s) = %.3f\t\t ", labels[j], probs[j]);
        }
        System.out.println();

        for (int i = 0; i < testSample.features.length; i++) {
            for (int j = 0; j < labels.length; j++) {
                double featureProb = tinhXSDK(train, labels[j], i, testSample.features[i]);
                probs[j] *= featureProb;
                System.out.printf(" \uD83D\uDE06 P(%s|%s) = %.3f \t\t\t", testSample.features[i], labels[j], featureProb);
            }
            System.out.println();
        }

        for (int j = 0; j < labels.length; j++) {
            System.out.printf(" \uD83C\uDF20 P(%s|features) = %.4f\t", labels[j], probs[j]);
            if (probs[j] > maxProb) {
                maxProb = probs[j];
                bestLabel = labels[j];
            }
        }
        System.out.println();

        return bestLabel;
    }

    public static void main(String[] args) {
        TrainSample[] train = nhapTrain("src/ai/NBayes1/Training.txt");
        TrainSample[] test = nhapTest("src/ai/NBayes1/Testing.txt");

        if (train != null && test != null) {
            for (TrainSample sample : test) {
                String predictedLabel = duDoan(train, sample);
                System.out.println(" \uD83C\uDF14 "+String.join(" ", sample.features) + " -> " + predictedLabel);
                System.out.println("------------------------------------------------------");

            }
        }
    }
}