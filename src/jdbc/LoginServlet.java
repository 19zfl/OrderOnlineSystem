package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	Statement stmt;
	ResultSet rs;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// �����Ӧ����out��������ı�������
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// ���������Ϣ�����ı���
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		// ��ȡ�û���������
		String un = request.getParameter("un");
		String pw = request.getParameter("pw");
		try {
			if (un != null && pw != null && !"".equals(un) && !"".equals(pw)) {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/whtcc", "root", "512619");
				String sql = "select * from user where username='" + un + "' and password='" + pw + "'";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				if (rs.next()) {// �û���������ƥ�䵽�û���ļ�¼
					// ��½�ɹ���
					String ident = rs.getString("ident");
					// ����½�û���Ϣ���浽�ػ�����session��
					session.setAttribute("loginID", rs.getInt(1));
					session.setAttribute("loginName", rs.getString("username"));
					session.setAttribute("ident", ident);
					// ���ݵ�½�û�����ݣ�ת����ͬ��ҳ�棨�����ͬ����ʾ��Ϣ��
					if (ident.equals("1"))
						out.print("����Ա��½�ɹ���");
					else
						out.print("��ͨ�û���½�ɹ���");
				} else // ��½ʧ��
				{
					out.print("�û������������");
					out.print("<a href=pages/homepage.html>����</a>");
				}

			} else // ������ϢΪ��
			{
				out.print("����<a href=pages/homepage.html>��½</a>");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO: handle exception
		}
		finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
