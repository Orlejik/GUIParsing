package org.example.demo1.Progress;

public abstract class ProgressBarThread implements Runnable{
    static double progress=0.0;
    public static long progressSet(double i){
        progress+=i;
        return 0;
    }

}
