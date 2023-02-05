package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private final List<Integer> primes;

    private final int time;
	
	public PrimeFinderThread(int a, int b, List<Integer> primes, int time) {
		super();
		this.a = a;
		this.b = b;
        this.primes = primes;
        this.time = time;
	}

    @Override
	public void run(){
        synchronized (primes) {
            long tiempo = System.currentTimeMillis();
            for (int i = a; i < b; i++) {
                if (isPrime(i)) {
                    primes.add(i);
                    //System.out.println(i);
                }
                if (System.currentTimeMillis() - tiempo > time) {
                    System.out.println("------------------ TIEMPO ------------------");
                    try {
                        primes.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    tiempo = System.currentTimeMillis();
                }
            }
        }
        //System.out.println("------------------ ACABÉ ------------------");
	}
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

}
