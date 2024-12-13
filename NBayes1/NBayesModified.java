package ai.AI_TriTueNhanTao.NBayes1;

import java.io.*;
import java.util.*;

public class NBayesModified {
    static class TrainSample {
        String[] features;
        String label;

        public TrainSample(String[] features, String label) {
            this.features = features;
            this.label = label;
        }
    }

    public static TrainSample[] loadData(String filename, boolean isTraining) {
        List<TrainSample> samples = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int n = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < n; i++) {
                String[] parts = br.readLine().split("\\s+");
                String[] features = Arrays.copyOfRange(parts, 0, parts.length - (isTraining ? 1 : 0));
                String label = isTraining ? parts[parts.length - 1] : null;
                samples.add(new TrainSample(features, label));
            }
        } catch (IOException e) {
            System.err.println("Loi file: " + filename);
        }
        return samples.toArray(new TrainSample[0]);
    }

    public static double calculateProbability(TrainSample[] dataset, String label) {
        double count = Arrays.stream(dataset).filter(sample -> sample.label.equals(label)).count();
        return (count + 1) / (dataset.length + 2); // Laplace Smoothing
    }

    public static double calculateConditionalProb(TrainSample[] dataset, String label, int index, String value) {
        long labelCount = Arrays.stream(dataset).filter(sample -> sample.label.equals(label)).count();
        long featureCount = Arrays.stream(dataset)
                .filter(sample -> sample.label.equals(label) && sample.features[index].equals(value))
                .count();
        return (featureCount + 1.0) / (labelCount + 2); // Laplace Smoothing
    }

    public static String predictLabel(TrainSample[] train, TrainSample testSample) {
        String[] labels = {"Y", "N"};
        double maxProb = -1;
        String bestLabel = null;
        Map<String, Double> probabilities = new HashMap<>();

        System.out.println("Predicting...");
        for (String label : labels) {
            double prob = calculateProbability(train, label);
            for (int i = 0; i < testSample.features.length; i++) {
                prob *= calculateConditionalProb(train, label, i, testSample.features[i]);
            }
            probabilities.put(label, prob);
            if (prob > maxProb) {
                maxProb = prob;
                bestLabel = label;
            }
        }

        probabilities.forEach((label, prob) ->
                System.out.printf("P(%s|features) = %.4f\n", label, prob));

        return bestLabel;
    }

    public static void main(String[] args) {
        TrainSample[] train = loadData("src/ai/NBayes1/Training.txt", true);
        TrainSample[] test = loadData("src/ai/NBayes1/Testing.txt", false);

        if (train != null && test != null) {
            System.out.println("Results:");
            for (TrainSample sample : test) {
                String predictedLabel = predictLabel(train, sample);
                System.out.printf("Features: %s -> Predicted: %s\n",
                        String.join(", ", sample.features), predictedLabel);
            }
        }
    }
}
