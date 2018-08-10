package mysql;

import java.sql.*;

//用来连接mysql的
public class Mysql {

	public Connection ct;

	public Mysql() { // Mysql地址配置
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 创建一个连接
			ct = DriverManager.getConnection(
					"jdbc:mysql://118.25.208.99:3306/test_class?useUnicode=true&autoReconnect=true&characterEncoding=utf-8",
					"root", "123456");
			// 设置超时时间
			DriverManager.setLoginTimeout(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
