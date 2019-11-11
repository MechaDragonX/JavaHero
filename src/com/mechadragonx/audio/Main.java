package com.mechadragonx.audio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Queue;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        double[] strings = { 82.41,  110.00, 146.83, 196.00, 246.94, 329.63 };
        for(double frequency : strings)
        {
            GuitarString string = new GuitarString(frequency);
            System.out.println("Testing GuitarString with frequency\"" + frequency + "\".");
            testSamples(string);
        }

    }

    private static void testSamples(GuitarString string) {
        double size = string.getSampleLength();
        Queue<Double> samples = string.getSamples();
        double sample;
        for(int i = 0; i < size; i++)
        {
            sample = samples.remove();
            if(sample < -0.5 || sample > 0.5)
            {
                System.out.println("ERROR: sample #" + i + " = " + sample);
                System.exit(1);
            }
            samples.add(sample);
        }
    }
    private static void write() throws IOException
    {
        double[] strings = { 82.41,  110.00, 146.83, 196.00, 246.94, 329.63 };
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("./data/rags_test.txt"), StandardCharsets.US_ASCII);
        String line;
        for(double num : strings)
        {
            line = Double.toString(num) + "\r\n";
            try
            {
                writer.write(line, 0, line.length());
            }
            catch(IOException e)
            {
                System.err.format("IOException: %s%n", e);
            }
        }
        writer.close();
    }
}
