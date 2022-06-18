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
	 * �ж��û����Ƿ����
	 * @param username
	 * @return true(�û����Ϸ�����)  false(�û����ѱ�ʹ��)
	 */
	public boolean check(String username) {
		boolean f=false;
		String sql = "select * from user where username=?";
		Map<String,String> u=db.getMap(sql, new String[] {username});
		if(u==null) f=true;
		return f;
	}
	/**
	 *  ע��
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
		//ģ������ ����like�ؼ���
		if(username!=null&&!"".equals(username)) {
			sql=sql+" where username like ?";
			params=new String[]{"%"+username+"%"};
		}
		return  db.getList(sql, params);
	}
	//ɾ���û�
	public int delUser(int id) {
		String sql="delete from user where id="+id;
		return db.update(sql);
	}
	/**
	 * ����Id�����û�
	 * @param id
	 * @return �û���Map<String,String> �ṹ
	 */
	public Map<String,String> getUser(String id){
		String sql="select * from user where id=?";
		String[] params= {id};
	    return db.getMap(sql, params);
	}
	/**
	 * �޸��û�
	 * @param �û���Map<String,String> �ṹ
	 * @return ��Ӱ�������
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
