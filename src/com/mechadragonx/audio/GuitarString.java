package com.mechadragonx.audio;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GuitarString implements Guitar
{
    // Random Number Generator
    private static Random rng = new Random();
    // Sampling Rate Class Constant
    private final double SAMPLING_RATE = 44100.0;
    // Fields
    private double frequency;
    private double sampleLength;
    private Queue<Double> samples;
    private int time;

    public GuitarString(double frequency)
    {
        this.frequency = frequency;
        sampleLength = SAMPLING_RATE / frequency;
        populate();
        time = 0;
    }

    // Getters
    public double getFrequency() { return frequency; }
    public double getSampleLength() { return sampleLength; }
    public Queue<Double> getSamples() { return samples; }

    // Methods
    private void populate()
    {
        samples = new LinkedList<>();
        for(int i = 0; i < sampleLength; i++)
        {
            samples.add(ThreadLocalRandom.current().nextDouble(-0.5, 0.5));
        }
    }
    public void pluck()
    {
        double zero = samples.remove();
        double one = samples.peek();
        samples.add(0.996 * 0.5 * (zero + one)); // Karplus-Strong update
    }

    // Interface Methods
    @Override
    public void playNote(int pitch) throws NotImplementedException { throw new NotImplementedException(); }
    @Override
    public boolean hasString(char key) throws NotImplementedException { throw new NotImplementedException(); }
    @Override
    public void pluck(char key) throws NotImplementedException { throw new NotImplementedException(); }
    @Override
    public double sample()
    {
        double sample = 0;
        double item;
        for(int i = 0; i < sampleLength; i++)
        {
            item = samples.remove();
            sample += item;
            samples.add(item);
        }
        return sample;
    }
    @Override
    public void tic() { time++; }
    @Override
    public int time() { return time; }
}
