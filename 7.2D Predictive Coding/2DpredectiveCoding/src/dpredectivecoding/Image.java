package dpredectivecoding;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;
import javax.imageio.ImageIO;

public class Image {

    public static Vector<Integer> readImage(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        PrintStream pis = new PrintStream("image.txt");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        Vector<Integer> arr = new Vector<Integer>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xff;
                int r = (rgb >> 16) & 0xff;
                int g = (rgb >> 8) & 0xff;
                int b = (rgb >> 0) & 0xff;
                arr.add(r);
            }
        }
        return arr;
    }

    public static void writeImage(int[][] pixels, int height, int width, String outputFilePath) {
        File fileout = new File(outputFilePath);
        BufferedImage image2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image2.setRGB(x, y, (pixels[y][x] << 16) | (pixels[y][x] << 8) | (pixels[y][x]));
            }
        }
        try {
            ImageIO.write(image2, "jpg", fileout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int gethight(String path) throws FileNotFoundException {
        File file = new File(path);
        PrintStream pis = new PrintStream("image.txt");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        return height;
    }

    public static int getwidth(String path) throws FileNotFoundException {
        File file = new File(path);
        PrintStream pis = new PrintStream("image.txt");
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = image.getWidth();
        int height = image.getHeight();
        return width;
    }

    public static void main(String[] args) {
    }
}
