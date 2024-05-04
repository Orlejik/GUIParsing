package org.example.demo1.Progress;

public abstract class ProgressBarThread {
    static double progress=0.0;
    public static long progressSet(double i){
        progress+=i;
        return 0;
    }

}
