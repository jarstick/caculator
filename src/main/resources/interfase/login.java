package interfase;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mysql.Mysql;
import mysql.UseMysql;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public login() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String name = request.getParameter("loginName");
		String pwd = request.getParameter("Pwd");
		// 定义返回
		String res = "{";
		// 调用mysql登录
		if ((name != null || pwd != null) && name.length()>3 && pwd.length()>3) {
			//校验用户名密码是否包含字母和数字
			String regEx = "[^a-zA-Z0-9]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(name);
			Matcher m1 = p.matcher(pwd);
			//判断用户名和密码都没包含特殊字符登录
			if(!m.find() && !m1.find()) {
			//当前有用户登录
				if(request.getSession().getAttribute("loginName") == null) {
			// 实例化mysql 创建数据库连接
				Mysql mysql = new Mysql();
			// 调用数据库查询
				UseMysql useMysql = new UseMysql(mysql.ct);
			// 调用登录方法，如果返回true则表示登录成功，false表示登录失败
					if (useMysql.Login(name, pwd)) {
						res += "\"status\":200,\"msg\":\"恭喜您，登录成功！\"";
						request.getSession().setAttribute("loginName",name);
					} else {
						res += "\"status\":201,\"msg\":\"用户名或密码错误！\"";
					} 
				} else {
					if(request.getSession().getAttribute("loginName").equals(name)) {
						res += "\"status\":405,\"msg\":\"用户重复登录！\"";
					} 
				}
			} else {
			res += "\"status\":202,\"msg\":\"参数错误！\"";
			} 
		}else {
			res += "\"status\":203,\"msg\":\"用户名或密码不能为空！\"";
		}
		res += "}";
		response.getWriter().append(res);
	}
		
}
