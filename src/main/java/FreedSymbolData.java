import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class FreedSymbolData {

	// JDBC �����������ݿ� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// public static final String
	// URL="jdbc:mysql://localhost:3306/jdbc01?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";//���ӵ�mysql
	static final String DB_URL = "jdbc:mysql://localhost:3306/order?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";

	// ���ݿ���û��������룬��Ҫ�����Լ�������
	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) {
//		DataFromFile aff = new DataFromFile();
//		aff.dataAnalyse("C:/Users/zzk/Desktop/Technical Project/symbol_update.txt");
//		Map<String, Symbol> symbols = aff.getSymbols();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			// ע�� JDBC ����
			Class.forName("com.mysql.cj.jdbc.Driver");

			// ������
			System.out.println("�������ݿ�...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// ִ�в�ѯ
			System.out.println(" ʵ����Statement����...");
			String sql;

//			sql = "INSERT INTO symbols(symbolName, company, LastSale, change_net, share_volume) VALUES(?, ?, ?, ?, ?)";
//			for (Map.Entry<String, Symbol> entry : symbols.entrySet()) {
//				Symbol symbol = entry.getValue();
//				stmt = conn.prepareStatement(sql);
//				stmt.setString(1, symbol.getSymbolName());
//				stmt.setString(2, symbol.getCompany());
//				stmt.setFloat(3, symbol.getLastSale());
//				stmt.setString(4, symbol.getChange_net());
//				stmt.setLong(5, symbol.getShare_volume());
//				stmt.executeUpdate();
//			}

			sql = "SELECT * FROM symbols";
			stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			System.out.printf("%5s\t%40s\t%10s\t%15s\t%10s\n", "Symbol", "Company", "Picture", "Last Sales",
					"Change Net %", "Share Volumn");
			// չ����������ݿ�
			while (rs.next()) {
				// ͨ���ֶμ���
				String symbolName = rs.getString("symbolName");
				String company = rs.getString("company");
				float lastSale = rs.getFloat("lastSale");
				String change_net = rs.getString("change_net");
				long share_volume = rs.getLong("share_volume");

				// �������
				System.out.printf("%5s\t%40s\t%10f\t%15s\t%10d\n", symbolName, company, lastSale, change_net,
						share_volume);
			}
			// ��ɺ�ر�
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// ���� JDBC ����
			se.printStackTrace();
		} catch (Exception e) {
			// ���� Class.forName ����
			e.printStackTrace();
		} finally {
			// �ر���Դ
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // ʲô������
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}

}
