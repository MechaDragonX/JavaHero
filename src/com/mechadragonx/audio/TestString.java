// Stuart Reges
// 4/6/12
//
// This program tests the GuitarString class.  The program reads from the file
// string.txt which has a series of test cases with the correct answers.

package com.mechadragonx.audio;

import java.util.*;
import java.io.*;

public class TestString {
    private static final double EPSILON = 1E-12;    

    public static void main(String[] args) {
        Scanner input = null;
        try {
            input = new Scanner(new File("./data/string.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("You must copy string.txt to \"/data\" before running the testing program.");
            System.exit(1);
        }
        // testWithArray(input);
        testWithFrequency(input);
        System.out.println("passed all tests");
    }

    // pre : input file has a series of test cases for specific arrays of
    //       values showing what the sample value should be as we make repeated
    //       calls on tic
    // post: program is halted if an error is encountered
//    public static void testWithArray(Scanner input) {
//        int cases = input.nextInt();
//        for (int i = 0; i < cases; i++) {
//            int size = input.nextInt();
//            double[] data = new double[size];
//            for (int j = 0; j < data.length; j++)
//                data[j] = input.nextDouble();
//            System.out.println("Testing GuitarString with this array:");
//            System.out.println(Arrays.toString(data));
//            GuitarString g = new GuitarString(data);
//
//            for (int time = 0; time < 10 * data.length; time++) {
//                double sample = input.nextDouble();
//                double sample2 = g.sample();
//                if (Math.abs(sample - sample2) > EPSILON) {
//                    System.out.println("ERROR: Sample mismatch");
//                    System.out.println("  when time = " + time);
//                    System.out.println("  sample should = " + sample);
//                    System.out.println("  string reports sample = " + sample2);
//                    System.exit(1);
//                }
//                g.tic();
//            }
//            System.out.println("passed");
//            System.out.println();
//        }
//    }

    // pre : input file has a series of test cases listing a frequency and ring
    //       buffer size to test
    // post: program is halted if an error is encountered
    public static void testWithFrequency(Scanner input) {
        int cases = input.nextInt();
        for (int i = 0; i < cases; i++) {
            int freq = input.nextInt();
            int size = input.nextInt();
            System.out.println("Testing GuitarString with frequency " + freq);
            GuitarString g = new GuitarString(freq);
            g.pluck();
            double sum1 = 0.0;
            double first = g.sample();
            boolean allZero = true;
            for (int j = 0; j < size - 1; j++) {
                double n = g.sample();
                sum1 = sum1 + n;
                if (n != 0.0) {
                    allZero = false;
                }
                if (n < -0.5 || n > 0.5) {
                    System.out.println("ERROR: sample #" + j + " = " + n);
                    System.exit(1);
                }
                g.tic();
            }
            if (allZero) {
                System.out.println("ERROR: all samples values are zero");
                System.exit(1);
            }
            double last = g.sample();
            sum1 = sum1 + last;
            g.tic();
            double sum2 = 0.0;
            for (int j = 0; j < size - 1; j++) {
                sum2 = sum2 + g.sample();
                g.tic();
            }
            double correctSum = (sum1 - first / 2.0 - last / 2.0) * 0.996;
            if (Math.abs(sum2 - correctSum) > EPSILON) {
                System.out.println("ERROR: sum of samples is not correct");
                System.out.println("ring buffer size should = " + size);
                System.exit(1);
            } else
                System.out.println("passed");
            System.out.println();
        }
    }
}
