import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;

public class feedforward {

    public static double[] pixelData() {
        try {
            System.out.print("Enter the image file name: ");
            Scanner scan = new Scanner(System.in);
            String image = "numbers/" + scan.nextLine();
            scan.close();

            BufferedImage img = ImageIO.read(new File(image));
            // get pixel data
            double[] dummy = null;
            double[] X = img.getData().getPixels(0, 0, img.getWidth(),
                img.getHeight(), dummy);
            for (int i = 0; i < 784; i++) {
                X[i] = X[i] / 255;
            }
            return X;
        } catch (IOException e) {
            return null;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        double[] inputs = pixelData(), inputNeurons = new double[300], outputNeurons = new double[10];
        int cntInput = 0, cntOutput = 0;
        File inputFile = new File("weights/hidden-weights.txt"), outputFile = new File("weights/output-weights.txt");
        Scanner in = new Scanner(inputFile), out = new Scanner(outputFile);
        
        while (in.hasNextLine()) {
            String row = in .nextLine();
            String[] rowArray = row.split(" ");
            for (int i = 0; i < 784; i++) {
                double num = Double.parseDouble(rowArray[i]);
                inputNeurons[cntInput] = inputNeurons[cntInput] + (inputs[i] * num);
            }
            double bias = Double.parseDouble(rowArray[784]);
            inputNeurons[cntInput] = inputNeurons[cntInput] + bias;
            inputNeurons[cntInput] = (1 / (1 + Math.pow(Math.E, (-1 * inputNeurons[cntInput]))));

            cntInput = cntInput + 1;
        } in .close();

        while (out.hasNextLine()) {
            String row = out.nextLine();
            String[] rowArray = row.split(" ");

            for (int i = 0; i < 300; i++) {
                double num = Double.parseDouble(rowArray[i]);
                outputNeurons[cntOutput] = outputNeurons[cntOutput] + (inputNeurons[i] * num);
            }
            double bias = Double.parseDouble(rowArray[300]);
            outputNeurons[cntOutput] = outputNeurons[cntOutput] + bias;
            outputNeurons[cntOutput] = (1 / (1 + Math.pow(Math.E, (-1 * outputNeurons[cntOutput]))));

            cntOutput = cntOutput + 1;
        }
        out.close();

        double max = outputNeurons[0];
        int prediction = 0;
        for (int i = 0; i < outputNeurons.length; i++) {
            if (outputNeurons[i] > max) {
                max = outputNeurons[i];
                prediction = i;
            }
        }

        System.out.println("The network prediction is " + prediction);
    }
}
