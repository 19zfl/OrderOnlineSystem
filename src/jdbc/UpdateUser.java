package jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.UserService;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/updateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// �����Ӧ����out��������ı�������
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// ���������Ϣ�����ı���
		request.setCharacterEncoding("utf-8");
		UserService us=new UserService();
		// ��ȡ�û��������롢�绰����ַ
		String id = request.getParameter("Id");
		String un = request.getParameter("un");
		String pw = request.getParameter("pw");
		String tel = request.getParameter("tel");
		String addr = request.getParameter("addr");
		Map<String,String> m=new HashMap<String, String>();
		 m.put("username", un); m.put("password", pw);
		 m.put("telephone", tel); m.put("address", addr);
		 m.put("id", id);
		 int r=us.updateUser(m);
		 if(r==1) out.print("�޸ĳɹ���");
		else out.print(r+"�޸Ĳ��ɹ���");
		 out.print("<a href=pages/userList.jsp>�����û��б�ҳ��</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
