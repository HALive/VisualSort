package test;

import java.util.Scanner;

public class Main {

        public static void main(String[] args) {
            Scanner scn = new Scanner(System.in);
            System.out.printf("Enter n: ");
            int n = scn.nextInt();

            int fTrue = 0;

            for(int i = 0; i < Math.pow(2, n); i++) {
                boolean[] booleans = new boolean[n];
                for(int x = 0; x < n; x++) {
                    int j = (i >> x) & 0x1;
                    booleans[x] = j == 1;
                }
                boolean b = doFunc(booleans);
                if(b) {
                    fTrue++;
                    System.out.println(fTrue +": "+getBits(booleans));
                }
            }
            System.out.println(fTrue);
        }

        public static boolean doFunc(boolean[] b) {
            boolean last = true;
            for(int i = 0; i < b.length-1; i++) {
                last = last & (!b[i] | b[i+1]);
            }
            return last;
        }

        public static String getBits(boolean[] b) {
            String s = "";
            for (int i = 0; i < b.length; i++) {
                s += (b[i] ? "0 " : "1 ");
            }
            return s;
        }
}
