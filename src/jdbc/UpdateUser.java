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
		// 解决相应对象out输出的中文编码问题
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 解决请求信息的中文编码
		request.setCharacterEncoding("utf-8");
		UserService us=new UserService();
		// 获取用户名和密码、电话、地址
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
		 if(r==1) out.print("修改成功！");
		else out.print(r+"修改不成功！");
		 out.print("<a href=pages/userList.jsp>返回用户列表页面</a>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
