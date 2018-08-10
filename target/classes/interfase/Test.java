package interfase;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Test() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 获取参数
		String v1 = request.getParameter("value1");
		String v2 = request.getParameter("value2");
		String m = request.getParameter("method");
		String res = "{";
		// 逻辑处理
		if (m == null) {
			m = "+";
		}
		switch (m) {
		case "+":
			try {
				res += "\"status\":200,\"result\":";
				res += Double.parseDouble(v1) + Double.parseDouble(v2);
			} catch (Exception e) {
				res += "\"status\":201,\"result\":0";
			}
			break;
		case "-":
			try {
				res += "\"status\":200,\"result\":";
				res += Double.parseDouble(v1) - Double.parseDouble(v2);
			} catch (Exception e) {
				res += "\"status\":201,\"result\":0";
			}
			break;
		case "*":
			try {
				res += "\"status\":200,\"result\":";
				res += Double.parseDouble(v1) * Double.parseDouble(v2);
			} catch (Exception e) {
				res += "\"status\":201,\"result\":0";
			}
			break;
		case "/":
			try {
				if (!(Double.parseDouble(v2) == 0)) {
					res += "\"status\":200,\"result\":";
					res += Double.parseDouble(v1) / Double.parseDouble(v2);
				} else {
					res += "\"status\":201,\"msg\":\"除数不能为0\"";
				}
			} catch (Exception e) {
				res += "\"status\":201,\"msg\":\"参数不能为空\"";
			}
			break;
		default:
			res += "\"status\":202,\"result\":-1";
		}
		res += "}";
		// 返回结果
		response.getWriter().append(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
