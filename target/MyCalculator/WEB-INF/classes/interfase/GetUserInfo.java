package interfase;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mysql.Mysql;
import mysql.UseMysql;

/**
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserInfo() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		// 获取接口需传入的参数：用户名和密码

		// 定义返回
		String res = "{";
		// 调用mysql登录
		if (request.getSession().getAttribute("loginName") != null) {
			// 实例化mysql 创建数据库连接
			Mysql mysql = new Mysql();
			// 调用数据库查询
			UseMysql useMysql = new UseMysql(mysql.ct);
			// 调用登录方法，如果返回true则表示登录成功，false表示登录失败
			Map<String, String> map = useMysql.getUserInfo(request.getSession().getAttribute("loginName").toString());
			if (map != null) {
				for (String key : map.keySet()) {
					res += "\"" + key + "\":\"" + map.get(key) + "\",";
				}
				res += "\"status\":200,\"msg\":\"查询成功！\"";

			} else {
				res += "\"status\":201,\"msg\":\"用户名不存在！\"";
			}
		} else {
			res += "\"status\":202,\"msg\":\"权限错误！\"";
		}
		res += "}";

		response.getWriter().append(res);
	}

}
