// Stuart Reges
// 4/6/12
//
// This program constructs a Guitar object that it allows the user to play.

package com.mechadragonx.audio;

public class GuitarHero {
    public static void main(String[] args) {
        GuitarLite g = new GuitarLite();
        // this is an infinite loop--user must quit the application
        for (;;) {
            // check if the user has typed a key; if so, process it   
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (g.hasString(key)) {
                    g.pluck(key);
//                    if(key == 'a') System.out.println(g.stringA.getSamples());
//                    else if(key == 'c') System.out.println(g.stringC.getSamples());
                } else {
                    System.out.println("bad key: " + key);
                    System.out.println((int) key);
                }
            }
            // send the result to the sound card
            StdAudio.play(g.sample());
            g.tic();
        }
    }
}
