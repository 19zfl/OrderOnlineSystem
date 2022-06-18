package ctrl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import model.DiningCarService;
import model.FoodService;
import model.FoodTypeService;
import model.UserService;
import util.FileUploadUtil;

/**
 * Servlet implementation class CenterController
 */

@WebServlet("*.action")
@MultipartConfig(maxFileSize = 5*1024*1024)
public class CenterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CenterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String path= request.getServletPath();//��ȡ������Դ��url��ַ
		//��ȡ�Ӵ�
		path=path.substring(path.lastIndexOf('/')+1, path.indexOf(".action"));
		if(path.equals("homepage")) {//���������ҳ
			//��ȡ�Ȳˡ��ؼ۲ˡ���ʦ�Ƽ��ļ��ϣ�����
			FoodService fs=new FoodService();
			request.setAttribute("hot", fs.getHotFoods());
			request.setAttribute("special", fs.getSpecialFoods());
			request.setAttribute("recomm", fs.getRecommFoods());
			//����ת����Դ����
			request.getRequestDispatcher("/pages/homepage.jsp").forward(request, response);
		}
		else if(path.equals("show_detail")) {
			//��Ʒ����ҳ
			FoodService fs=new FoodService();
			String id=request.getParameter("id");
			request.setAttribute("food", fs.getFood(id));
			//����ת����Դ����
			request.getRequestDispatcher("/pages/show_detail.jsp").forward(request, response);
			
		}
		else if(path.equals("login")) {//�û���¼
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			// ��ȡ�û���������
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			//�ж��û���������
			   //�����Ϊ��
			if (un != null && pw != null && !"".equals(un) && !"".equals(pw)) {
				UserService us=new UserService();
				 Map<String,String> m=us.login(un,pw);
			     //�ҵ�����û�����û���ҵ�  ��ת��������ʾҳ��
				 if(m==null) { //��¼���ɹ�
					 //����ʾ��Ϣ���浽request���������
					 request.setAttribute("msg", "�û������������");
					 request.setAttribute("href", request.getContextPath()+"/homepage.action");
					 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				 }
			     //�ҵ�����û������ҵ�  ����һ���жϵ�¼�û������
				 else { //��½�ɹ�
					 String ident=m.get("ident");
					// ����½�û���Ϣ���浽�ػ�����session��
						session.setAttribute("loginID", m.get("id"));
						session.setAttribute("loginName", m.get("username"));
						session.setAttribute("ident", ident);
			          if(ident.equals("0"))  // �������ͨ�û�   �ض���ҳ��.../user/user_index.action
			        	  response.sendRedirect(request.getContextPath()+"/user/user_index.action");
			          else		// ����ǹ���Ա   �ض���ҳ��.../admin/admin_index.action
			        	  response.sendRedirect(request.getContextPath()+"/admin/admin_index.action");
				 }
			}
			else {
				  //û�е�¼������ʾ�ȵ�¼
				request.setAttribute("msg", "���ȵ�¼");
				 request.setAttribute("href", request.getContextPath()+"/homepage.action");
				 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}
			
		}
		else if(path.equals("logout")) {
			HttpSession session = request.getSession();
			session.removeAttribute("loginID");
			session.removeAttribute("loginName");
			session.removeAttribute("ident");
			session.invalidate();
			 response.sendRedirect(request.getContextPath()+"/homepage.action");
		}
		else if(path.equals("user_index")) {//ת�û�ҳ��
			//��ȡ������Ϣ
			String s_fn=request.getParameter("s_fn");
			String s_type=request.getParameter("s_type");
			FoodService fs=new FoodService();
			request.setAttribute("foods", fs.getFoods(s_fn, s_type));
			FoodTypeService ft=new FoodTypeService();
			request.setAttribute("types",ft.getAllTypes());
			request.getRequestDispatcher("/pages/user/user_index.jsp").forward(request, response);
			}
		else if(path.equals("user_add_dc")) {
			HttpSession session = request.getSession();
			String userid=(String)session.getAttribute("loginID");
			String[] ids=request.getParameterValues("ids");
			DiningCarService dc=new DiningCarService();
			int r=dc.addToDC(userid, ids);
			//ת�����ҳ��
			request.setAttribute("msg", "�ɹ���"+r+"����Ʒ�����ͳ���");
			 request.setAttribute("href", request.getContextPath()+"/user/user_show_dc.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("user_show_dc")) {
			//
			HttpSession session = request.getSession();
			String userid=(String)session.getAttribute("loginID");
			DiningCarService dc=new DiningCarService();
			request.setAttribute("dc", dc.showDC(userid));
			 request.getRequestDispatcher("/pages/user/user_show_dc.jsp").forward(request, response);
		}
		else if(path.equals("user_del_dc")) {
			DiningCarService dc=new DiningCarService();
			String[] ids=request.getParameterValues("ids");
			int r=dc.delFormDC(ids);
			request.setAttribute("msg", "�ɹ���"+r+"����Ʒ�ӵ�ͳ�ɾ����");
			 request.setAttribute("href", request.getContextPath()+"/user/user_show_dc.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("user_upd_info")) {
			// ��ȡ�û��������롢�绰����ַ
			String id = request.getParameter("id");
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			String tel = request.getParameter("tel");
			String addr = request.getParameter("addr");
			Map<String,String> m=new HashMap<String, String>();
			 m.put("username", un); m.put("password", pw);
			 m.put("telephone", tel); m.put("address", addr);
			 m.put("id", id);
			 UserService us=new UserService();
			 int r=us.updateUser(m);
			 if(r==1) { request.setAttribute("msg", "�޸��û���Ϣ�ɹ���");
			 HttpSession session = request.getSession();
			 session.setAttribute("loginName", un);
			 }
			 else  request.setAttribute("msg", "�޸��û���Ϣʧ�ܣ�");
			 request.setAttribute("href", request.getContextPath()+"/pages/user/user_index.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("admin_index")) {//ת����Աҳ��
			request.getRequestDispatcher("/pages/admin/admin_index.jsp").forward(request, response);
		  }
		else if(path.equals("admin_show_dc")) {
			DiningCarService dc=new DiningCarService();
			request.setAttribute("dcs", dc.showAllDC());
			request.getRequestDispatcher("/pages/admin/admin_dc_show.jsp").forward(request, response);
		}	 
		else if(path.equals("admin_list_food")) {
			String s_fn=request.getParameter("s_fn");
			String s_type=request.getParameter("s_type");
			FoodService fs=new FoodService();
			request.setAttribute("foods", fs.getFoods(s_fn, s_type));
			FoodTypeService ft=new FoodTypeService();
			request.setAttribute("types",ft.getAllTypes());
			request.getRequestDispatcher("/pages/admin/admin_list_food.jsp").forward(request, response);
		}
		else if(path.equals("admin_edit_food")) {
			String id=request.getParameter("id");
			//��ȡ���в�Ʒ����
			request.setAttribute("types", new FoodTypeService().getAllTypes());
			//��ȡ���޸ĵ������Ʒ
			request.setAttribute("food", new FoodService().getFood(id));
			request.getRequestDispatcher("/pages/admin/admin_edit_food.jsp").forward(request, response);
		}
		else if(path.equals("admin_edit_food_do")) {
			try {
				String id = request.getParameter("id");
				String foodname = request.getParameter("fn");
				String feature = request.getParameter("fea");
				String material = request.getParameter("mat");
				String type = request.getParameter("type");
				String price = request.getParameter("price");
				String comment = request.getParameter("com");
				String picture = null;
				Part img = request.getPart("img");
				// �ж��ϴ��ļ�����չ���Ƿ����Ҫ��
				String fileExtName = FileUploadUtil.getFileExtName(img);
				if (!fileExtName.equals("") && !fileExtName.equalsIgnoreCase(".jpg")
						&& !fileExtName.equalsIgnoreCase(".png") && !fileExtName.equalsIgnoreCase(".gif")) {
					request.setAttribute("msg", "�ϴ��ļ�����չ��ӦΪjpg,png��gif��");
					request.setAttribute("href", "javascript:history.back()");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				} else {
					picture = FileUploadUtil.uploadSingleFile(img, request);
					// ����ģ�Ͳ������ݿ�
					FoodService f = new FoodService();
					int r = f.editFood(foodname, feature, material, price, type, picture, comment, id);
					request.setAttribute("msg", r == 1 ? "�޸Ĳ�Ʒ�ɹ���" : "�޸Ĳ�Ʒʧ�ܣ�");
					request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				}
			} catch (IllegalStateException e) {
				request.setAttribute("msg", "�ϴ��ļ���СӦС��5M��");
				request.setAttribute("href", "javascript:history.back()");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}}
		else if(path.equals("admin_add_food")) {
			String id=request.getParameter("id");
			//��ȡ���в�Ʒ����
			request.setAttribute("types", new FoodTypeService().getAllTypes());
			 
			request.getRequestDispatcher("/pages/admin/admin_add_food.jsp").forward(request, response);
		}
		else if(path.equals("admin_add_food_do")) {
			try{String foodname = request.getParameter("fn");
			String feature = request.getParameter("fea");
			String material = request.getParameter("mat");
			String type = request.getParameter("type");
			String price = request.getParameter("price");
			String comment = request.getParameter("com");
			String picture = null;
			Part img = request.getPart("img");
			// �ж��ϴ��ļ�����չ���Ƿ����Ҫ��
			String fileExtName = FileUploadUtil.getFileExtName(img);
			if (!fileExtName.equals("") && !fileExtName.equalsIgnoreCase(".jpg")
					&& !fileExtName.equalsIgnoreCase(".png") && !fileExtName.equalsIgnoreCase(".gif")) {
				request.setAttribute("msg", "�ϴ��ļ�����չ��ӦΪjpg,png��gif��");
				request.setAttribute("href", "javascript:history.back()");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			} else {
				picture = FileUploadUtil.uploadSingleFile(img, request);
				// ����ģ�Ͳ������ݿ�
				FoodService f = new FoodService();
				int r = f.addFood(foodname, feature, material, price, type, picture, comment);
				request.setAttribute("msg", r == 1 ? "�޸Ĳ�Ʒ�ɹ���" : "�޸Ĳ�Ʒʧ�ܣ�");
				request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}
		} catch (IllegalStateException e) {
			request.setAttribute("msg", "�ϴ��ļ���СӦС��5M��");
			request.setAttribute("href", "javascript:history.back()");
			request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		}else if(path.equals("admin_de"
				+ ""
				+ "l_food")) {
			String id = request.getParameter("id");
			FoodService fs=new FoodService();
			int r=fs.delFood(id);
			request.setAttribute("msg", r == 1 ? "ɾ����Ʒ�ɹ���" : "ɾ����Ʒʧ�ܣ�");
			request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
			request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("admin_list_user")) {
			String s_un = request.getParameter("un");
			UserService us = new UserService();
			request.setAttribute("users", us.getUsers(s_un));
			request.getRequestDispatcher("/pages/admin/userList.jsp").forward(request, response);
		}
		else if(path.equals("admin_edit_user.action")) {
			
		}
		else if(path.equals("admin_list_foodtype")) {
			FoodTypeService ft=new FoodTypeService();
			request.setAttribute("types",ft.getAllTypes());
			request.getRequestDispatcher("/pages/admin/admin_list_type.jsp").forward(request, response);
		}
		else if(path.equals("register")) {
			request.setCharacterEncoding("utf-8");
			// ��ȡ�û��������롢�绰����ַ
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			String tel = request.getParameter("tel");
			String addr = request.getParameter("addr");
			UserService us=new UserService();
			if(us.check(un)) {//�û�������
				int r=us.register(un, pw, tel, addr);
				if(r==1) request.setAttribute("msg", "ע��ɹ���");
				else request.setAttribute("msg", "ע��ʧ�ܣ�");
			}
			
			else {
				request.setAttribute("msg", "�û����Ѵ��ڣ�");
			}
			 request.setAttribute("href", request.getContextPath()+"/homepage.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		} 
		      
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
