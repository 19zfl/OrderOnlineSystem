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
		String path= request.getServletPath();//获取请求资源的url地址
		//截取子串
		path=path.substring(path.lastIndexOf('/')+1, path.indexOf(".action"));
		if(path.equals("homepage")) {//请求的是首页
			//获取热菜、特价菜、厨师推荐的集合，保存
			FoodService fs=new FoodService();
			request.setAttribute("hot", fs.getHotFoods());
			request.setAttribute("special", fs.getSpecialFoods());
			request.setAttribute("recomm", fs.getRecommFoods());
			//请求转向，资源共享
			request.getRequestDispatcher("/pages/homepage.jsp").forward(request, response);
		}
		else if(path.equals("show_detail")) {
			//菜品详情页
			FoodService fs=new FoodService();
			String id=request.getParameter("id");
			request.setAttribute("food", fs.getFood(id));
			//请求转向，资源共享
			request.getRequestDispatcher("/pages/show_detail.jsp").forward(request, response);
			
		}
		else if(path.equals("login")) {//用户登录
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			// 获取用户名和密码
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			//判断用户名和密码
			   //如果不为空
			if (un != null && pw != null && !"".equals(un) && !"".equals(pw)) {
				UserService us=new UserService();
				 Map<String,String> m=us.login(un,pw);
			     //找到这个用户了吗：没有找到  ，转到错误提示页面
				 if(m==null) { //登录不成功
					 //将提示信息保存到request请求对象中
					 request.setAttribute("msg", "用户名或密码错误");
					 request.setAttribute("href", request.getContextPath()+"/homepage.action");
					 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				 }
			     //找到这个用户了吗：找到  ，进一步判断登录用户的身份
				 else { //登陆成功
					 String ident=m.get("ident");
					// 将登陆用户信息保存到回话对象session中
						session.setAttribute("loginID", m.get("id"));
						session.setAttribute("loginName", m.get("username"));
						session.setAttribute("ident", ident);
			          if(ident.equals("0"))  // 身份是普通用户   重定向到页面.../user/user_index.action
			        	  response.sendRedirect(request.getContextPath()+"/user/user_index.action");
			          else		// 身份是管理员   重定向到页面.../admin/admin_index.action
			        	  response.sendRedirect(request.getContextPath()+"/admin/admin_index.action");
				 }
			}
			else {
				  //没有登录，则提示先登录
				request.setAttribute("msg", "请先登录");
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
		else if(path.equals("user_index")) {//转用户页面
			//获取搜索信息
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
			//转到结果页面
			request.setAttribute("msg", "成功将"+r+"个菜品加入点餐车！");
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
			request.setAttribute("msg", "成功将"+r+"个菜品从点餐车删除！");
			 request.setAttribute("href", request.getContextPath()+"/user/user_show_dc.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("user_upd_info")) {
			// 获取用户名和密码、电话、地址
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
			 if(r==1) { request.setAttribute("msg", "修改用户信息成功！");
			 HttpSession session = request.getSession();
			 session.setAttribute("loginName", un);
			 }
			 else  request.setAttribute("msg", "修改用户信息失败！");
			 request.setAttribute("href", request.getContextPath()+"/pages/user/user_index.action");
			 request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		else if(path.equals("admin_index")) {//转管理员页面
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
			//获取所有菜品分类
			request.setAttribute("types", new FoodTypeService().getAllTypes());
			//获取待修改的这道菜品
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
				// 判断上传文件的扩展名是否符合要求
				String fileExtName = FileUploadUtil.getFileExtName(img);
				if (!fileExtName.equals("") && !fileExtName.equalsIgnoreCase(".jpg")
						&& !fileExtName.equalsIgnoreCase(".png") && !fileExtName.equalsIgnoreCase(".gif")) {
					request.setAttribute("msg", "上传文件的扩展名应为jpg,png或gif！");
					request.setAttribute("href", "javascript:history.back()");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				} else {
					picture = FileUploadUtil.uploadSingleFile(img, request);
					// 调用模型插入数据库
					FoodService f = new FoodService();
					int r = f.editFood(foodname, feature, material, price, type, picture, comment, id);
					request.setAttribute("msg", r == 1 ? "修改菜品成功！" : "修改菜品失败！");
					request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
					request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
				}
			} catch (IllegalStateException e) {
				request.setAttribute("msg", "上传文件大小应小于5M！");
				request.setAttribute("href", "javascript:history.back()");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}}
		else if(path.equals("admin_add_food")) {
			String id=request.getParameter("id");
			//获取所有菜品分类
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
			// 判断上传文件的扩展名是否符合要求
			String fileExtName = FileUploadUtil.getFileExtName(img);
			if (!fileExtName.equals("") && !fileExtName.equalsIgnoreCase(".jpg")
					&& !fileExtName.equalsIgnoreCase(".png") && !fileExtName.equalsIgnoreCase(".gif")) {
				request.setAttribute("msg", "上传文件的扩展名应为jpg,png或gif！");
				request.setAttribute("href", "javascript:history.back()");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			} else {
				picture = FileUploadUtil.uploadSingleFile(img, request);
				// 调用模型插入数据库
				FoodService f = new FoodService();
				int r = f.addFood(foodname, feature, material, price, type, picture, comment);
				request.setAttribute("msg", r == 1 ? "修改菜品成功！" : "修改菜品失败！");
				request.setAttribute("href", request.getContextPath() + "/admin/admin_list_food.action");
				request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
			}
		} catch (IllegalStateException e) {
			request.setAttribute("msg", "上传文件大小应小于5M！");
			request.setAttribute("href", "javascript:history.back()");
			request.getRequestDispatcher("/pages/result.jsp").forward(request, response);
		}
		}else if(path.equals("admin_de"
				+ ""
				+ "l_food")) {
			String id = request.getParameter("id");
			FoodService fs=new FoodService();
			int r=fs.delFood(id);
			request.setAttribute("msg", r == 1 ? "删除菜品成功！" : "删除菜品失败！");
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
			// 获取用户名和密码、电话、地址
			String un = request.getParameter("un");
			String pw = request.getParameter("pw");
			String tel = request.getParameter("tel");
			String addr = request.getParameter("addr");
			UserService us=new UserService();
			if(us.check(un)) {//用户名可用
				int r=us.register(un, pw, tel, addr);
				if(r==1) request.setAttribute("msg", "注册成功！");
				else request.setAttribute("msg", "注册失败！");
			}
			
			else {
				request.setAttribute("msg", "用户名已存在！");
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
