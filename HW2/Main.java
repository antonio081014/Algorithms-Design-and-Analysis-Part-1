import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Antonio081014
 * @since Sat Jul 20 09:53:40 PDT 2013
 */
public class Main {

	private long comparisons;

	private static final int array_size = 10000;

	public static void main(String[] args) {
		Main main = new Main();
		int[] array = new int[array_size];
		try {
			Scanner in = new Scanner(new FileReader("QuickSort.txt"));
			for (int i = 0; i < array_size; i++)
				array[i] = in.nextInt();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
		}
		// System.out.println(main.partition(array, 0, array.length - 1));
		main.comparisons = 0;
		main.quicksort(array, 0, array.length - 1);
		main.print(array, 0, array.length - 1);
		System.out.println(main.comparisons);
	}

	private int partition(int[] array, int start, int end) {
		this.comparisons += end - start;
		int p = pivot1(array, start, end);
		int i = start + 1;
		for (int j = start + 1; j <= end; j++) {
			if (array[j] < p) {
				int tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
				i++;
			}
		}
		int tmp = array[start];
		array[start] = array[i - 1];
		array[i - 1] = tmp;
		return i - 1;
	}

	/**
	 * Using first element as the pivot;<br />
	 * Answer: 162085;
	 * */
	private int pivot1(int[] array, int start, int end) {
		return array[start];
	}

	/**
	 * Using last element as the pivot<br />
	 * Answer: 164123;
	 * */
	private int pivot2(int[] array, int start, int end) {
		int tmp = array[start];
		array[start] = array[end];
		array[end] = tmp;
		return array[start];
	}

	/**
	 * Using the median of first, last and middle element;<br />
	 * Answer: 138382;
	 * */
	private int pivot3(int[] array, int start, int end) {
		int p1 = array[start];
		int p2 = array[(end + start) / 2];
		int p3 = array[end];
		if ((p2 > p1 && p2 < p3) || (p2 < p1 && p2 > p3)) {
			int tmp = array[start];
			array[start] = array[(end + start) / 2];
			array[(end + start) / 2] = tmp;
			return array[start];
		} else if ((p3 > p1 && p3 < p2) || (p3 < p1 && p3 > p2)) {
			return pivot2(array, start, end);
		} else
			return pivot1(array, start, end);
	}

	private void quicksort(int[] array, int start, int end) {
		if (start >= end)
			return;
		// print(array, start, end);
		// this.comparisons += array.length - 1;
		int p = partition(array, start, end);
		quicksort(array, start, p - 1);
		quicksort(array, p + 1, end);
	}

	private void print(int[] array, int start, int end) {
		for (int i = start; i <= end; i++) {
			System.out.print(" " + array[i]);
		}
		System.out.println();
	}

}
