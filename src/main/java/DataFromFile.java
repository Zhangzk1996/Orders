import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataFromFile {

	public static void main(String[] args) {

		ArrayList<String[]> symbols = new ArrayList<String[]>();
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("C:/Users/zzk/Desktop/Technical Project/symbol_update.txt"));
			String line = br.readLine();
			while ((line != null) && (!line.isEmpty())) {
				String[] symbol = new String[6];

				line = br.readLine();
				// System.out.println("name: " + line);
				symbol[0] = line;
				line = br.readLine();
				int i = 1;
				if (line != null) {
					for (String s : line.split("\t")) {
						symbol[i++] = s;
						// System.out.println(i - 1 + ": " + s);
					}
				}
				// System.out.println("other: " + line);
				symbols.add(symbol);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("%5s\t%40s\t%40s\t%10s\t%15s\t%10s\n", "Symbol", "Company", "Picture", "Last Sales",
				"Change Net %", "Share Volumn");
		for (String[] symbol : symbols) {
			String str = "";
			System.out.printf("%5s\t%40s\t%40s\t%10s\t%15s\t%10s\n", symbol[0], symbol[1], symbol[2], symbol[3],
					symbol[4], symbol[5]);
			// for (String s : symbol) {
			// str += s;
			// str += "\t";
			// }
			// System.out.println(str);
		}

	}

}
