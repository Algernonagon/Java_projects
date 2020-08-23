public class MethodTester {

	private static int getDigit(long x, int n) {
		return (int)((x/(long)Math.pow(10,n-1))%10);
	}

	private static void radixCountSort(long[] a, int n) {
		int k = getDigit(a[0],n);
		for(int i=1; i<a.length; i++) {
			if(k < getDigit(a[i],n)) k = getDigit(a[i],n);
		}
		int[] b = new int[k+1];
		long[] c = new long[a.length];
		for(int i=0; i<a.length; i++) {
			b[getDigit(a[i],n)]++;
		}
		for(int i=1; i<b.length; i++) {
			b[i] += b[i-1];
		}
		for(int i=a.length-1; i>=0; i--) {
			c[b[getDigit(a[i],n)]-1] = a[i];
			b[getDigit(a[i],n)]--;
		}
		for(int i=0; i<a.length; i++) {
			a[i] = c[i];
		}
	}

	public static void radixSort(long[] a) {
		long max=a[0];
		for(int i=1; i<a.length; i++) {
			if(max < a[i]) max = a[i];
		}
		int digit = 1;
		while((int)Math.pow(10, digit-1) <= max) {
			radixCountSort(a, digit);
			digit++;
		}
	}


	public static void main(String[] args) {
		long[] a = {10, 10, 5, 7, 8, 5, 3, 6, 3, 10, 9, 1};
		radixSort(a);
		long[] b = a;
		for(int i=0; i<a.length; i++) {
			System.out.println(b[i]);
		}
	}

}