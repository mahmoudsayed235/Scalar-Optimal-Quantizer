package scalaroptimalquantizer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author midoyass
 */
public class ScalarOptimalQuantizer {
    public static boolean equal(Vector<Vector<Double>> v,Vector<Vector<Double>> v1){
        for (int i = 0; i < v1.size(); i++) {
            for (int j = 0; j < v1.elementAt(i).size(); j++) {
               if(v1.elementAt(i).elementAt(j)!=v.elementAt(i).elementAt(j))return false;
            }
        }
        return true;
    }
    public static double calcAver(Vector<Double> v1) {
        double toAdd = 0;
        for (int i = 0; i < v1.size(); i++) {
            toAdd += v1.get(i);
        }
        return (toAdd / v1.size());
    }
////////////////////////////////////////////////////////////////////
    public static double calcDiff(double a, double b) {
        return (a - b < 0) ? (a - b) * -1 : a - b;
    }
////////////////////////////////////////////////////////////////////
    public static void Compress() throws FileNotFoundException {
        String ss ;
        ss = JOptionPane.showInputDialog(null,"Please Enter Number of Level: ");
        int Level = Integer.parseInt(ss);
        Image im = null;
        PrintStream pis = new PrintStream("compressed.txt");
            
        Vector<Double> v = new Vector<Double>();
        Vector<Double> vectors = new Vector<Double>();
        v=im.readImage("Winter.jpg");
        //////////////////////////////////////////////////////////////////////////////////
        int x=im.gethight("Winter.jpg");
        int y=im.getwidth("Winter.jpg");
        Double max = Collections.max(v);
        Double min = Collections.min(v);
        pis.println(x+" "+y);
        vectors.add(calcAver(v));
        Vector<Vector<Double>> prev = new Vector<Vector<Double>>();
        //////////////////////////////////////////////////////////////////////////////////
        for (int i = 0; i < Level; i++) {
            Vector<Double> temp = new Vector<Double>();
            for (int j = 0; j < vectors.size(); j++) {
                temp.add(vectors.get(j) - 1);
                temp.add(vectors.get(j) + 1);
            }
            vectors = temp;
            Vector<Vector<Double>> check = new Vector<Vector<Double>>();
            for (int j = 0; j < temp.size(); j++) {
                check.add(new Vector<Double>());
            }
            for (int j = 0; j < v.size(); j++) {
                int pos = 0;
                for (int k = 0; k < vectors.size(); k++) {
                    if (calcDiff(v.get(j), vectors.get(k)) <= calcDiff(v.get(j), vectors.get(pos))) {
                        pos = k;
                    }
                }
                check.elementAt(pos).add(v.get(j));
            }
            Vector<Double> result = new Vector<Double>();
            for (int j = 0; j < check.size(); j++) {
                result.add(calcAver(check.get(j)));
            }
            vectors = result;
            prev=check;
        }
        
        Vector<Double> ranges = new Vector<Double>();
        ranges.add(min);
        for (int i = 1; i < vectors.size(); i++) {
            ranges.add((vectors.get(i - 1) + vectors.get(i)) / 2.0);
        }
        for (int i = 0; i < vectors.size(); i++) {
            pis.println(vectors.get(i));
        }
        pis.println("#");
        for (int i = 0; i < v.size(); i++) {
            for (int j = ranges.size()-1; j >= 0; j--) {
                if(v.get(i)<=ranges.get(j))pis.println(j);
            }
        }
 ////////////////////////////////////////////////////////////////////////////////
    }
    public static void DeComp() throws IOException{
        PrintStream pis2 = new PrintStream("decompress.txt");

        FileInputStream fis = new FileInputStream("compressed.txt");
        BufferedInputStream bis = new BufferedInputStream(fis);
        DataInputStream dis = new DataInputStream(bis);
        Vector<Double> v = new Vector<Double>();
        String res = dis.readLine();
        StringTokenizer take = new StringTokenizer(res);  
        int x=Integer.parseInt(take.nextToken()),y=Integer.parseInt(take.nextToken());
        while(dis.available()>0){
            String str = dis.readLine();
            if(str.charAt(0)=='#')break;
            v.add(Double.parseDouble(str));
        }
        int [][] arr = new int[x][y];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double idx = v.get(Integer.parseInt(dis.readLine()));
                arr[i][j]=(int)idx;
            }
        }
        Image im = null;
        im.writeImage(arr, x, y, "copy.jpg");
        for(int f=0;f<x;f++) {
            for(int f2=0;f2<y;f2++) {
                pis2.println(arr[f][f2]);
            }

        }

    }
/////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Compress();
        DeComp();
    }
}
