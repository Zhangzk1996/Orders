
public class Symbol {

	private String symbolName;
	private String company;
	private float lastSale;
	private String change_net;
	private long share_volume;

	public Symbol(String symbolName, String company, float lastSale, String change_net, long share_volume) {
		this.symbolName = symbolName;
		this.company = company;
		this.lastSale = lastSale;
		this.change_net = change_net;
		this.share_volume = share_volume;
	}

	public String getSymbolName() {
		return symbolName;
	}

	public void setSymbolName(String symbolName) {
		this.symbolName = symbolName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public float getLastSale() {
		return lastSale;
	}

	public void setLastSale(float lastSale) {
		this.lastSale = lastSale;
	}

	public String getChange_net() {
		return change_net;
	}

	public void setChange_net(String change_net) {
		this.change_net = change_net;
	}

	public long getShare_volume() {
		return share_volume;
	}

	public void setShare_volume(long share_volume) {
		this.share_volume = share_volume;
	}

}
