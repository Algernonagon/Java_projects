/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING ANY
SOURCES OUTSIDE OF THOSE APPROVED BY THE INSTRUCTOR. Nathan Yang
*/



import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.*;

public class Sorting {
	
	//counting sort based off of the pseudocode algorithm
	public static void countingSort(long[] a) {
		long k=a[0];
		//loop to make k largest value in a
		for(int i=1; i<a.length; i++) {
			if(k < a[i]) k = a[i];
		}
		int[] b = new int[(int)k+1];	//"bucket" array
		long[] c = new long[a.length];	//array to store sorted array
		
		//makes each b[i] equal to the frequency of i in a
		for(int i=0; i<a.length; i++) {
			b[(int)a[i]]++;
		}

		//accumulation step, makes each b[i] equal to the frequency of all elements in a <= i
		for(int i=1; i<b.length; i++) {
			b[i] += b[i-1];
		}

		//sorting phase, decrement values in b to get indeces for where elements of a go in c to be sorted
		for(int i=a.length-1; i>=0; i--) {
			c[b[(int)a[i]]-1] = a[i];
			b[(int)a[i]]--;
		}

		//set a equal to the sorted array c
		for(int i=0; i<a.length; i++) {
			a[i] = c[i];
		}
	}

	//gets the nth digit starting at the ones place of a long x, e.g. if n=1 get first digit, n=2 get second digit, etc
	private static int getDigit(long x, int n) {
		return (int)((x/(long)Math.pow(10,n-1))%10);
	}

	//counting sort for radix that sorts values of a based off of their nth digit
	private static void radixCountSort(long[] a, int n) {
		int[] b = new int[10];
		long[] c = new long[a.length];
		for(int i=0; i<a.length; i++) {
			b[getDigit(a[i], n)]++;
		}
		for(int i=1; i<b.length; i++) {
			b[i] += b[i-1];
		}
		for(int i=a.length-1; i>=0; i--) {
			c[b[getDigit(a[i], n)]-1] = a[i];
			b[getDigit(a[i], n)]--;
		}
		for(int i=0; i<a.length; i++) {
			a[i] = c[i];
		}
	}

	//sorts elements in a one digit at a time starting from the ones place
	public static void radixSort(long[] a) {
		long max=a[0];
		//finds max value in a
		for(int i=1; i<a.length; i++) {
			if(max < a[i]) max = a[i];
		}

		int n = 1; //starts sorting at n=1, first digit (ones place)
		//keeps sorting digits using radixCountsort until n is greater than the number of digits in max
		while((int)Math.pow(10, n-1) <= max) {
			radixCountSort(a, n);
			n++;
		}
	}

	//shell sort based off of the pseudocode algorithm
	public static void shellSort(long[] a) {
		int[] gaps = gapSeq(a);
		for(int k=gaps.length-1; k>=0; k--) {
			int h = gaps[k];
			for(int i=h; i<a.length; i++) {
				long key = a[i];
				int j = i;
				while(j>=h && a[j-h]>key) {
					a[j] = a[j-h];
					j -= h;
				}
				a[j] = key;
			}
		}
	}

	//creates gap sequence using the 2^k+1 formula proposed by Papernov and Stasevich
	private static int[] gapSeq(long[] a) {
		int n = 0;	//number of entries to go in gap sequence
		int h = 1;
		//increments n and ensures that it does not generate a gap larger than the size of a
		while(true) {
			n++;
			h = (int)Math.pow(2,n)+1;
			if(h > a.length) break;
		}
		int[] gaps = new int[n];	//gap sequence of size n
		gaps[0] = 1;	//first gap has to be 1
		//creates gaps based off of the 2^k+1 formula
		for(int i=1; i<n; i++) {
			gaps[i] = (int)Math.pow(2,i)+1;
		}
		return gaps; //returns the gap sequence
	}

	//-------------------------------------------------------------
	//------- Below is an implementation of Selection Sort --------
	//-------------------------------------------------------------		
	public static void SelectionSort(long[] a) {
		int N = a.length;
	    for (int i = 0; i < N; i++) {
	    	int min = i;
	        for (int j = i+1; j < N; j++) {
	        	if (a[j] < a[min]) min = j;
	        }
	        exch(a, i, min);
	    }
	}
	
	
	//-----------------------------------------------------------------------
	//---------- Below is an implementation of Insertion Sort ----------
	//-----------------------------------------------------------------------
	public static void InsertionSort(long[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && a[j] < a[j-1]; j--) {
                exch(a, j, j-1);
            }
        }
	}


	//-----------------------------------------------------------------------
	//---------- Below is an implementation of recursive MergeSort ----------
	//-----------------------------------------------------------------------
    private static void merge(long[] a, long[] aux, int lo, int mid, int hi) {

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (aux[j] < aux[i]) 	a[k] = aux[j++];
            else                        a[k] = aux[i++];
        }
    }

    
    // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
    private static void sort(long[] a, long[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void MergeSort(long[] a) {
        long[] aux = new long[a.length];
        sort(a, aux, 0, a.length-1);
    }
    
	//------------------------------------------------------
	//---------- below are several helper methods ----------
	//------------------------------------------------------
	
	// This tests whether your sorted result is correct by comparing it to reference result
	public static boolean testSort(long[] a) {
		long[] a2 = new long[a.length];
		System.arraycopy(a, 0, a2, 0, a.length);
		Arrays.sort(a);
		for(int i = 0; i < a.length; i++)
			if(a2[i] != a[i]) 
				return false;
		return true;
	}
	
	
	// This creates an array with n randomly generated elements between (0, n*10]
	private static long[] randArray(int n) {
		long[] rand = new long[n];
		for(int i=0; i<n; i++)
			rand[i] = (int) (Math.random() * n * 10);
		return rand;
	}
	
	private static void startTimer() { 
		timestamp = System.nanoTime();
	}
	
	private static double endTimer() {
		return (System.nanoTime() - timestamp)/1000000.0;
	}
	        
    // exchange a[i] and a[j]
    private static void exch(long[] a, int i, int j) {
        long swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
	
	private static long timestamp;
	
	//---------------------------------------------
	//---------- This is the main method ----------
	//---------------------------------------------		
	public static void main(String[] args) {
	
		// run experiments
		final int SELECT = 0, INSERT = 1, MERGE = 2, COUNT = 3, RADIX = 4, SHELL = 5;
		int[] algorithms = {SELECT, INSERT, MERGE, COUNT, RADIX, SHELL};
		
		// max defines the maximum size of the array to be tested, which is 2^max
		// runs defines the number of rounds to be performed per test, in order to get an average running time.
		int max = 15, runs = 5;
		double[][] stats = new double[algorithms.length][max];
		for(int i=0; i<algorithms.length; i++) {             //loop through each sorting algorithm
			switch(i) {
				case SELECT: System.out.print("Running Selection Sort ..."); break;
				case INSERT: System.out.print("Running Insertion Sort ..."); break;
				case MERGE: System.out.print("Running MergeSort ..."); break;
				case COUNT: System.out.print("Running Counting Sort ..."); break;
				case RADIX: System.out.print("Running Radix Sort ..."); break;
				case SHELL: System.out.print("Running Shellsort ..."); break;
			}
			for(int j=0; j<max; j++) {        //loop through each array size 
				double avg = 0;
				for(int k=0; k<runs; k++) {    // loop through each run
					long[] a = randArray((int) Math.pow(2, j+1));
					startTimer();
					switch(i) {
						case SELECT: SelectionSort(a); break;
						case INSERT: InsertionSort(a); break;
						case MERGE: MergeSort(a); break;
						case COUNT: countingSort(a); break;
						case RADIX: radixSort(a); break;
						case SHELL: shellSort(a); break;
					}
					avg += endTimer();
					if (testSort(a) == false)
						System.out.println("The sorting is INCORRECT!" + "(N=" + a.length + ", round=" + k + ").");
				}
				avg /= runs;
				stats[i][j] = avg;
			}
			System.out.println("done.");
		}
		
		DecimalFormat format = new DecimalFormat("0.0000");
		System.out.println();
		System.out.println("Average running time:");
		System.out.println("N\t Selection Sort\t Insertion Sort\t MergeSort\t Counting Sort\t Radix Sort\t Shellsort");
		for(int i=0; i<stats[0].length; i++) {
			System.out.print((int) Math.pow(2, i+1) + "\t  ");
			for(int j=0; j<stats.length; j++) {
				System.out.print(format.format(stats[j][i]) + "\t  ");
			}
			System.out.println();
		}
	}
	
}
