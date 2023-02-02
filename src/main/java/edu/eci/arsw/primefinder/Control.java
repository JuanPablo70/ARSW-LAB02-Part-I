/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 2000;

    private final int NDATA = MAXVALUE / NTHREADS;

    private PrimeFinderThread pft[];

    private final List<Integer> primes;
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];
        this.primes = new LinkedList<>();
        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, primes, TMILISECONDS);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, primes, TMILISECONDS);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        Scanner input = new Scanner(System.in);
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        synchronized (primes) {
            while (sleptThreads()) {
                try {
                    System.out.println("ME DORMÍ #########################");
                    Thread.sleep(5000);
                    System.out.println("ME DESPERTÉ #########################");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                primes.notifyAll();
            }
        }
    }

    public boolean sleptThreads() {
        for (PrimeFinderThread thread : pft) {
            if (!thread.isSlept()) {
                return false;
            }
        }
        return true;
    }
}
