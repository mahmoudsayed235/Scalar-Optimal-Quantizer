/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpredectivecoding;

import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;
import java.io.BufferedInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Joe
 */
public class Main {

    public static int checkQuant(Vector<Integer> ranges, int s) {
        for (int i = 0; i < ranges.size(); i++) {
            if (s < ranges.get(i)) {
                return i;
            }
        }
        return ranges.size()-1;
    }

    public static void comp() throws IOException {
        Vector<Integer> reading = new Vector<Integer>();
        reading = new Image().readImage("Winter.jpg");
        int ranges;
        String input = JOptionPane.showInputDialog(null, "Please Enter the range :");
        ranges = Integer.parseInt(input);
        int x = new Image().gethight("Winter.jpg"), y = new Image().getwidth("Winter.jpg");
        int[][] image = new int[x][y];
        int max = Collections.max(reading), min = Collections.min(reading);
        for (int i = 0; i < y; i++) {
            image[0][i] = reading.get(i);
        }
        int cnt = y;
        for (int i = 1; i < x; i++) {
            image[i][0] = reading.get(cnt);
            cnt += y;
        }
       
        for (int i = 0; i < x - 1; i++) {
            for (int j = 0; j < y - 1; j++) {
                if ((image[i][j] < image[i][j + 1]) && (image[i][j] < image[i + 1][j])) {
                    if (image[i][j + 1] > image[i + 1][j]) {
                        image[i + 1][j + 1] = image[i][j + 1];
                    } else {
                        image[i + 1][j + 1] = image[i + 1][j];
                    }
                }
                if ((image[i][j] > image[i][j + 1]) && (image[i][j] > image[i + 1][j])) {
                    if (image[i][j + 1] < image[i + 1][j]) {
                        image[i + 1][j + 1] = image[i][j + 1];
                    } else {
                        image[i + 1][j + 1] = image[i + 1][j];
                    }
                } else {
                    if (image[i][j] == 0) {
                        image[i + 1][j + 1] = (image[i + 1][j] + image[i][j + 1]);
                    } else {
                        image[i + 1][j + 1] = (image[i + 1][j] + image[i][j + 1]) / image[i][j];
                    }
                }
            }
        }
        
        PrintStream pis = new PrintStream("Compression.txt");
        Vector<Integer> Q = new Vector<Integer>();
        pis.println(x+" "+y);
        Q.add(ranges + min);
        pis.println(ranges + min);
        while (true) {
            int res = ranges + Q.get(Q.size() - 1);
            if (res > max) {
                break;
            }
            Q.add(res);
            pis.println(res);

        }
        Q.add(max);
        pis.println(max);
        pis.println("#");
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (i == 0 || j == 0) {
                    pis.print(image[i][j] + " ");
                    continue;
                }
                image[i][j] = (checkQuant(Q, image[i][j]));
                pis.print(image[i][j] + " ");
            }
            pis.println();
        }
    }
    public static void decom()throws IOException{
        FileInputStream fis = new FileInputStream("Compression.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        Vector<Integer> Ranges = new  Vector<Integer>();
        StringTokenizer token = new StringTokenizer(dis.readLine());
        int x = Integer.parseInt(token.nextToken()),y=Integer.parseInt(token.nextToken());
        while(dis.available()>0){
            String s= dis.readLine();
            if(s.charAt(0)=='#')break;
            Ranges.add(Integer.parseInt(s));
        }
        int arr[][]=new int[x][y];
        int i=0,j=0;
        while(dis.available()>0){
            j=0;
            String in= dis.readLine();
            StringTokenizer tok = new StringTokenizer(in);
            while(tok.hasMoreTokens()){
                if(i==0 || j==0)arr[i][j]= Integer.parseInt(tok.nextToken());
                else arr[i][j]= Ranges.get(Integer.parseInt(tok.nextToken()));
                j++;
            }
            i++;
        }
        Image im =null;
        im.writeImage(arr, x, y, "Copy.jpg");
    }
    public static void main(String[] args) throws IOException {
        comp();
        decom();
    }
}
