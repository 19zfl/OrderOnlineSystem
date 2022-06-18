package model;

import java.util.List;
import java.util.Map;

import util.DBUtil;

public class UserService {
	private DBUtil db=null;

	public UserService() {
		 db=new DBUtil();
	}
	/**
	 * 判断用户名是否可用
	 * @param username
	 * @return true(用户名合法可用)  false(用户名已被使用)
	 */
	public boolean check(String username) {
		boolean f=false;
		String sql = "select * from user where username=?";
		Map<String,String> u=db.getMap(sql, new String[] {username});
		if(u==null) f=true;
		return f;
	}
	/**
	 *  注册
	 * @param un
	 * @param pw
	 * @param tel
	 * @param addr
	 * @return
	 */
	public int register(String un,String pw,String tel,String addr) {
		String sql="insert into user values(null,?,?,0,?,?)";
		String[] params= {un,pw,tel,addr};
		return db.update(sql, params);
	}
	public Map<String,String> login(String un,String pw) {
		String sql="select * from user where username=? and password=?";
		String[] params= {un,pw};
		return db.getMap(sql, params);
				
	}
	public List<Map<String,String>> getUsers(String username){
		String sql="select * from user";
		String[] params=null;
		//模糊查找 ，用like关键字
		if(username!=null&&!"".equals(username)) {
			sql=sql+" where username like ?";
			params=new String[]{"%"+username+"%"};
		}
		return  db.getList(sql, params);
	}
	//删除用户
	public int delUser(int id) {
		String sql="delete from user where id="+id;
		return db.update(sql);
	}
	/**
	 * 根据Id查找用户
	 * @param id
	 * @return 用户的Map<String,String> 结构
	 */
	public Map<String,String> getUser(String id){
		String sql="select * from user where id=?";
		String[] params= {id};
	    return db.getMap(sql, params);
	}
	/**
	 * 修改用户
	 * @param 用户的Map<String,String> 结构
	 * @return 受影响的行数
	 */
	public int updateUser(Map<String,String> m) {
		String sql="update user set username=?,password=?,telephone=?,address=? where id=?";
		String[] params= {m.get("username"),m.get("password"),m.get("telephone"),m.get("address"),m.get("id")};
		return db.update(sql, params);
	}
 public static void main(String[] args) {
	 UserService us=new UserService();
	 Map<String,String> m=us.login("user1", "123");
	 System.out.println(m.get("ident"));
	 
//	  List<Map<String,String>> list= us.getUsers("");
//	  for(Map<String,String> m:list) {
//		  System.out.println(m.get("username"));
//	  }
	  
 }
}
