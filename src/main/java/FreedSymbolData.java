import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class FreedSymbolData {

	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	// public static final String
	// URL="jdbc:mysql://localhost:3306/jdbc01?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";//链接的mysql
	static final String DB_URL = "jdbc:mysql://localhost:3306/order?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "";

	public static void main(String[] args) {
//		DataFromFile aff = new DataFromFile();
//		aff.dataAnalyse("C:/Users/zzk/Desktop/Technical Project/symbol_update.txt");
//		Map<String, Symbol> symbols = aff.getSymbols();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			// 注册 JDBC 驱动
			Class.forName("com.mysql.cj.jdbc.Driver");

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// 执行查询
			System.out.println(" 实例化Statement对象...");
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
			// 展开结果集数据库
			while (rs.next()) {
				// 通过字段检索
				String symbolName = rs.getString("symbolName");
				String company = rs.getString("company");
				float lastSale = rs.getFloat("lastSale");
				String change_net = rs.getString("change_net");
				long share_volume = rs.getLong("share_volume");

				// 输出数据
				System.out.printf("%5s\t%40s\t%10f\t%15s\t%10d\n", symbolName, company, lastSale, change_net,
						share_volume);
			}
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// 处理 JDBC 错误
			se.printStackTrace();
		} catch (Exception e) {
			// 处理 Class.forName 错误
			e.printStackTrace();
		} finally {
			// 关闭资源
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // 什么都不做
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
