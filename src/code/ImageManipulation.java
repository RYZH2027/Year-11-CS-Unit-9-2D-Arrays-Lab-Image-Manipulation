package code;
import image.APImage;
import image.Pixel;



import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageManipulation {

    /**
     * CHALLENGE 0: Display Image
     * Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        String pathOfFile = "cyberpunk2077.jpg";
//        APImage image = new APImage(pathOfFile);

//        CHALLENGE 0: Display Image
//        image.draw();

//        CHALLENGE 1
//        grayScale(pathOfFile);

//        CHALLENGE 2
//        blackAndWhite(pathOfFile);
//.       Challenge 3
//        int threshold = 30; // Adjust threshold as needed meaning that a lower threshold will record more edges
//        edgeDetection(pathOfFile, threshold);
       // CHALLENGE 4
//        reflectImage(pathOfFile);
        //Challenge 5
        rotateImage(pathOfFile);





    }

    /**
     * CHALLENGE ONE: Grayscale
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     * <p>
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value.
     */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Pixel currentPixel = image.getPixel(i, j);
                int averageColor = getAverageColour(currentPixel);
                currentPixel.setRed(averageColor);
                currentPixel.setBlue(averageColor);
                currentPixel.setGreen(averageColor);

                image.setPixel(i, j, currentPixel);
            }
        }
        image.draw();

    }

    /**
     * A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     *
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {

        return (pixel.getBlue() + pixel.getGreen() + pixel.getRed()) / 3;

    }

    /**
     * CHALLENGE TWO: Black and White
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     * <p>
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white
     */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                Pixel currentPixel = image.getPixel(i, j);
                int averageColor = getAverageColour(currentPixel);
                if (averageColor < 128) {
                    currentPixel.setRed(0);
                    currentPixel.setBlue(0);
                    currentPixel.setGreen(0);
                } else {
                    currentPixel.setRed(255);
                    currentPixel.setBlue(255);
                    currentPixel.setGreen(255);
                }
                image.setPixel(i, j, currentPixel);
            }
        }
        image.draw();


    }

    /**
     * CHALLENGE Three: Edge Detection
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     * <p>
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     * <p>
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     */
    public static void edgeDetection(String pathToFile, int threshold) {
        try {
            // Read the input image
            File inputFile = new File(pathToFile);
            BufferedImage inputImage = ImageIO.read(inputFile);

            // Get image dimensions
            int width = inputImage.getWidth();
            int height = inputImage.getHeight();

            // Create a new image for the output
            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            // Process each pixel in the image (except the last row and column)
            for (int y = 0; y < height - 1; y++) {
                for (int x = 0; x < width - 1; x++) {
                    // Get the current pixel and its neighbors
                    Color currentPixel = new Color(inputImage.getRGB(x, y));
                    Color leftPixel = new Color(inputImage.getRGB(x + 1, y)); // Pixel to the right
                    Color belowPixel = new Color(inputImage.getRGB(x, y + 1)); // Pixel below

                    // Calculate average color values
                    int currentAvg = (currentPixel.getRed() + currentPixel.getGreen() + currentPixel.getBlue()) / 3;
                    int leftAvg = (leftPixel.getRed() + leftPixel.getGreen() + leftPixel.getBlue()) / 3;
                    int belowAvg = (belowPixel.getRed() + belowPixel.getGreen() + belowPixel.getBlue()) / 3;

                    // Calculate differences
                    int diffLeft = Math.abs(currentAvg - leftAvg);
                    int diffBelow = Math.abs(currentAvg - belowAvg);

                    // Apply threshold to determine if this is an edge
                    if (diffLeft > threshold || diffBelow > threshold) {
                        // This is an edge - set pixel to black
                        outputImage.setRGB(x, y, Color.BLACK.getRGB());
                    } else {
                        // Not an edge - set pixel to white
                        outputImage.setRGB(x, y, Color.WHITE.getRGB());
                    }
                }
            }

            // Handle the last row and column (set to white to avoid edge artifacts)
            for (int x = 0; x < width; x++) {
                outputImage.setRGB(x, height - 1, Color.WHITE.getRGB());
            }
            for (int y = 0; y < height; y++) {
                outputImage.setRGB(width - 1, y, Color.WHITE.getRGB());
            }

            // Save the output image (with "_edge" added to the filename)
            String outputPath = pathToFile.substring(0, pathToFile.lastIndexOf('.')) + "_edge" +
                    "_threshold" + threshold + ".png";
            File outputFile = new File(outputPath);
            ImageIO.write(outputImage, "png", outputFile);

            System.out.println("Edge detection completed. Output saved to: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error processing image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * CHALLENGE Four: Reflect Image
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);

        APImage newImage = new APImage(image.getWidth(), image.getHeight());

        for (int i = 0; i < newImage.getWidth(); i++) {
            for (int j = 0; j < newImage.getHeight(); j++) {
                Pixel currentPixel = image.getPixel(i, j);
                newImage.setPixel(newImage.getWidth() - i - 1, j, currentPixel.clone());
            }
        }
        newImage.draw();


    }

    /**
     * CHALLENGE Five: Rotate Image
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);

        // Create a new image with swapped dimensions
        APImage newImage = new APImage(image.getHeight(), image.getWidth());

        // Loop through each pixel in the original image
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                // Get the pixel from the original image
                Pixel currentPixel = image.getPixel(col, row);

                // Calculate the new position after rotation
                int newCol = row;
                int newRow = image.getWidth() - 1 - col;

                // Set the pixel in the new image
                newImage.setPixel(newCol, newRow, currentPixel.clone());
            }
        }
        newImage.draw();
    }
}
