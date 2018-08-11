import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataFromFile {

	private Map<String, Symbol> symbols;

	public DataFromFile() {
		this.symbols = new HashMap<String, Symbol>();
	}

	public Map<String, Symbol> getSymbols() {
		return symbols;
	}

	public void setSymbols(Map<String, Symbol> symbols) {
		this.symbols = symbols;
	}

	public void dataAnalyse(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = br.readLine();
			while ((line != null) && (!line.isEmpty())) {
				String[] symbol = new String[6];

				line = br.readLine();
				// System.out.println("name: " + line);
				symbol[0] = line;
				line = br.readLine();
//				System.out.println("line: " + line);
				int i = 1;
				if (line != null) {
					for (String s : line.split("\t")) {
						symbol[i++] = s;
						// System.out.println(i - 1 + ": " + s);
					}
					String symbolName = symbol[0];
					String company = symbol[1];
					String[] sales = symbol[3].split(" ");
					float lastSale = Float.valueOf(sales[1].replaceAll(",", ""));
					String change_net = symbol[4];
					symbol[5] = symbol[5].replaceAll(",", "");
					if (symbol[5].startsWith("$")) {
						symbol[5] = (symbol[5].split(" "))[1];
					}
					long share_volume = Long.valueOf(symbol[5]);
					Symbol sym = new Symbol(symbolName, company, lastSale, change_net, share_volume);
					// System.out.println("other: " + line);
					if (!this.symbols.containsKey(sym.getSymbolName())) {
						this.symbols.put(sym.getSymbolName(), sym);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		DataFromFile aff = new DataFromFile();
		aff.dataAnalyse("C:/Users/zzk/Desktop/Technical Project/symbol_update.txt");
		Map<String, Symbol> symbols = aff.getSymbols();

		System.out.printf("%5s\t%40s\t%40s\t%10s\t%15s\t%10s\n", "Symbol", "Company", "Picture", "Last Sales",
				"Change Net %", "Share Volumn");
		for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
			// String str = "";
			Symbol symbol = entry.getValue();
			System.out.printf("%5s\t%40s\t%10f\t%15s\t%10d\n", symbol.getSymbolName(), symbol.getCompany(),
					symbol.getLastSale(), symbol.getChange_net(), symbol.getShare_volume());
			// for (String s : symbol) {
			// str += s;
			// str += "\t";
			// }
			// System.out.println(str);
		}

	}

}
