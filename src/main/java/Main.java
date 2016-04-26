import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		int[] a = new int[3];
		int[] b = new int[3];
		Scanner cin = new Scanner(System.in);
		a[0] = cin.nextInt();
		a[1] = cin.nextInt();
		a[2] = cin.nextInt();
		sort(a);
		int cr,cy,cb;
		cr=cy=cb=0;
		int ans =  0;
		String s = cin.next();
		char c;
		for (int i=0;i<s.length();i++){
			c= s.charAt(i);
//			System.out.println(c);
			if (c=='R') cr++; else 
			if (c=='Y') cy++; else 
			if (c=='B') cb++;
			ans = Math.max(ans,cr+cy+cb);
			b[0] = Math.abs(cr-cy);
			b[1] = Math.abs(cy-cb);
			b[2] = Math.abs(cb-cr);
			sort(b);
			if (isEqual(a,b)){
				cr = cy = cb = 0;
			}
		}
		System.out.println(ans);
	}

	private static boolean isEqual(int[] a, int[] b) {
		for (int i=0;i<3;i++){
			if (a[i] != b[i]) return false;
		}
		return true;
	}

	private static void sort(int[] a) {
		if (a[0] > a[1]) swap(a,0,1);
		if (a[0] > a[2]) swap(a,0,2);
		if (a[1] > a[2]) swap(a,1,2);
	}

	private static void swap(int[] a, int i, int j) {
		// TODO Auto-generated method stub
		int t = a[i];
		a[i]=a[j];
		a[j]=t;
	}
	
}
