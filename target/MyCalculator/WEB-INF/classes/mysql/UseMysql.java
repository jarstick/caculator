package mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UseMysql {
	private Connection ct;

	// 初始化这个数据库连接对象
	public UseMysql(Connection ct1) {
		ct = ct1;
	}

	public Map<String, String> getUserInfo(String name) {
		String sql = "SELECT * FROM userinfo where username='" + name + "';";
		System.out.println(sql);
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql);

			// 执行更新，删除，插入操作
			// int rs1 = sm.executeUpdate(sql);
			// rs表示有多少行的内容
			if (rs != null && rs.next()) {
				// 这个集合，表示一行的内容
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if(!(rsmd.getColumnName(i).equals("pwd") || rsmd.getColumnName(i).equals("username")))
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// 这个功能在登录并没有用到，我只是教同学们怎么使用查询结果
				System.out.println(map.toString());
				// 如果查询结果不为空，就返回登录成功
				return map;
			}
			sm.close();
			rs.close();
		} catch (SQLException e) {
		}
		return null;
	}

	public boolean Login(String name, String pwd) {
		String sql = "SELECT * FROM userinfo where username='" + name + "' AND pwd='" + pwd + "';";
		System.out.println(sql);
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql);

			// 执行更新，删除，插入操作
			// int rs1 = sm.executeUpdate(sql);
			// rs表示有多少行的内容
			if (rs != null && rs.next()) {
				// 这个集合，表示一行的内容
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if(!(rsmd.getColumnName(i).equals("pwd") && rsmd.getColumnName(i).equals("name")))
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				// 这个功能在登录并没有用到，我只是教同学们怎么使用查询结果
				System.out.println(map.toString());
				// 如果查询结果不为空，就返回登录成功
				return true;
			}
			sm.close();
			rs.close();
		} catch (SQLException e) {
		}
		return false;
	}

	public boolean PLogin(String name, String pwd) {
		// 创建变量保存返回结果
		try {
			// 创建调用存储过程的状态
			CallableStatement cm = ct.prepareCall("{call login(?,?)}");
			// 给存储过程添加参数
			cm.setString(1, name);
			// cm.setInt(1, 1);
			cm.setString(2, pwd);
			// 获取执行结果
			ResultSet rs = cm.executeQuery();
			// 处理执行结果
			if (rs != null && rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				HashMap<String, String> map = new HashMap<String, String>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					map.put(rsmd.getColumnName(i), rs.getString(i));
				}
				System.out.println(map.toString());
				return true;
			}
			cm.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean regist(String nickname,String describe,String username,String pwd) {
		String sql = "insert into test_class.userinfo(nickname,describe,username,pwd) values('\"+nickname+\"','\"+describe+\"','\"+username+\"','\"+pwd+\"')";
		String sql1 = "SELECT * FROM userinfo where username='" + username + "';";
		// 保存结果集
		ResultSet rs = null;
		// 创建执行sql的操作状态
		Statement sm;
		try {
			sm = ct.createStatement();
			// 执行查询
			rs = sm.executeQuery(sql1);
			if(rs == null) {
				int	rs1 = sm.executeUpdate(sql1);
			}
			}catch (Exception e) {
				return false;
			}
			
		
		return false;
		}
	}
