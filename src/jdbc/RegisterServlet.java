package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		// ��ȡ�û��������롢�绰����ַ
		String un = request.getParameter("un");
		String pw = request.getParameter("pw");
		String tel = request.getParameter("tel");
		String addr = request.getParameter("addr");
		if (un != null && pw != null && tel!=null&&addr!=null ) {
			// 1.��ѯ�û����Ƿ���� select ���û��������ظ���
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal", "root", "512619");
				String sql1 = "select * from user where username=?";
				pstmt = conn.prepareStatement(sql1);
				//���ã���������Ӧ��ֵ
				pstmt.setString(1, un);
				//ִ������
				rs=pstmt.executeQuery();
				if(rs.next()) //��ʾ�û����Ѵ���
				{
					out.print("�û��������ã�");
					out.print("<a href=pages/homepage.html>����</a>");
					return;
				}
				else // 2.����û��������ڣ���insert ע��
				{
					String sql2="insert into user values(null,?,?,0,?,?)";
					pstmt=conn.prepareStatement(sql2);
					//���ã���������Ӧ��ֵ
					pstmt.setString(1, un);pstmt.setString(2, pw);
					pstmt.setString(3, tel);pstmt.setString(4, addr);
					//ִ�в�������
					int n=pstmt.executeUpdate();//������Ӱ�������
					if(n==1) out.print("ע��ɹ���");
					else out.print("ע�᲻�ɹ���");
					out.print("<a href=pages/homepage.html>����</a>");
				}
				 
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					if(rs!=null) rs.close();
					if(pstmt!=null) pstmt.close();
					if(conn!=null) conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		else // ������ϢΪ��
		{
			out.print("��������������<a href=pages/homepage.html>ע����Ϣ</a>");
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
