import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 * @author antonio081014
 * @time May 28, 2013, 10:33:07 PM
 */
public class Main {

	public static void main(String[] args) {
		Main main = new Main();
		main.run("IntegerArray.txt");
		System.exit(0);
	}

	private void run(String fileName) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			List<Integer> array = new ArrayList<Integer>();
			String line = null;
			while ((line = in.readLine()) != null) {
				array.add(new Integer(line));
			}
			in.close();
			System.out.println(merge_count(array, 0, array.size() - 1));
		} catch (Exception e) {
			System.out.println("Error: " + e.getLocalizedMessage());
		}
	}

	private long merge_count(List<Integer> array, int start, int end) {
		if (start >= end)
			return 0;
		int mid = (start + end) / 2;
		long left = merge_count(array, start, mid);
		long right = merge_count(array, mid + 1, end);
		long count = 0;
		// List<Integer> copyArray = array.subList(start, end);
		int[] copyArray = new int[end - start + 1];
		for (int p = start; p <= end; p++)
			copyArray[p - start] = array.get(p);
		// System.out.print(" " + ());
		// System.out.println();
		int i = start;
		int j = mid + 1;
		int index = start;
		while (i <= mid && j <= end) {
			if (copyArray[i - start] <= copyArray[j - start]) {
				array.set(index++, copyArray[i++ - start]);
			} else {
				count += mid - i + 1;
				array.set(index++, copyArray[j++ - start]);
			}
		}
		while (i <= mid)
			array.set(index++, copyArray[i++ - start]);
		while (j <= end)
			array.set(index++, copyArray[j++ - start]);
		return left + right + count;
	}
}
