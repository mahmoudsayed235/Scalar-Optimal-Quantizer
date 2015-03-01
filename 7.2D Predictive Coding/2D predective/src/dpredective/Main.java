/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpredective;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Joe
 */
public class Main {

    public static int check(Vector<Integer> v, int num) {
        for (int i = 0; i < v.size(); i++) {
            if (num <= v.get(i)) {

                return i;

            }
        }
        return -1;

    }

    /**4
     *
     * @param args the command line arguments
     */
    public static void compress() throws FileNotFoundException {
        PrintStream pis = new PrintStream("compression.txt");
        //FileInputStream fis = new FileInputStream("image.txt");
            String s1 = JOptionPane.showInputDialog(null,"Please Enter Your Number Of Levels : ");
    
        image img = null;
        Vector<Integer> Pixels = img.readImage("Desert.jpg");
        int x = img.gethight("Desert.jpg");
        int y = img.getwidth("Desert.jpg");
        pis.println(x);
        pis.println(y);
        int[][] array = new int[x][y];
        int count = 0;
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                array[i][j] = Pixels.get(count);
                count++;
            }
        }

        Vector<Integer> Quant = new Vector<Integer>();

        int levels;
        levels = Integer.parseInt(s1);
        //int n =levels;

        for (int i = 0; i < x - 1; i++) {
            for (int j = 0; j < y - 1; j++) {

                if (array[i][j + 1] < array[i][j] && array[i + 1][j] < array[i][j]) {
                    if (array[i][j + 1] < array[i + 1][j]) {
                        array[i + 1][j + 1] = array[i][j + 1];
                    }
                } else if (array[i][j + 1] < array[i][j] && array[i + 1][j] > array[i][j]) {
                    if (array[i][j + 1] > array[i + 1][j]) {
                        array[i + 1][j + 1] = array[i][j + 1];


                    }
                } else {

                    array[i + 1][j + 1] = array[i + 1][j] + array[i][j + 1] - array[i][j];

                }
            }
        }
        int max = -591951955;
        int min = 989849898;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (max < array[i][j]) {
                    max = array[i][j];
                }
                if (min > array[i][j]) {
                    min = array[i][j];
                }
            }
        }
        int Width = (int) ((double) (max - min) / levels);

        int temp = min + Width;
        for (int i = 0; i < levels; i++) {
            Quant.add(temp);
            temp += Width;
        }

        Quant.add(max);
        Vector<Integer> Average = new Vector<Integer>();
        Average.add(Quant.get(0) + min / 2);
        for (int i = 1; i < Quant.size(); i++) {
            Average.add((Quant.get(i) + Quant.get(i - 1)) / 2);
            pis.println(Average.get(Average.size() - 1));
        }
        pis.println('#');

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i == 0 || j == 0) {
                    pis.print(array[i][j] + " ");
                } else {
                    pis.print(check(Quant, array[i][j]) + " ");
                }
            }
            pis.println();
        }
    }

    public static void Decomp() throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream("compression.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        String input = dis.readLine();
        int x = Integer.parseInt(input);
        int y = Integer.parseInt(dis.readLine());
        Vector<Integer> Quant = new Vector<Integer>();
        while (dis.available() > 0) {
            input = dis.readLine();
            if (input.charAt(0) == '#') {
                break;
            }
            Quant.add(Integer.parseInt(input));
        }
        int[][] array = new int[x][y];
        int i = 0;
        while (dis.available() > 0) {
            int j = 0;
            String str = dis.readLine();
            StringTokenizer ss = new StringTokenizer(str);
//            System.out.println(ss.countTokens());
            while (ss.hasMoreTokens()) {
                array[i][j] = Integer.parseInt(ss.nextToken());
                j++;
            }
            i++;
        }
        System.out.println(Quant.size());
        for (int j = 1; j < x; j++) {
            for (int k = 1; k < y; k++) {
                array[j][k] = Quant.get(array[j][k]);
            }


        }
        image im = null;
        im.writeImage(array, x, y, "copy.jpg");

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        compress();
        Decomp();
    }
}
