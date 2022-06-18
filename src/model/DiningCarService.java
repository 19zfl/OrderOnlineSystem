package model;

import java.util.*;
import java.util.Map;

import util.DBUtil;

public class DiningCarService {
	private DBUtil db=null;
	public DiningCarService() {
		 db=new DBUtil();
	}
	public int  delFormDC(String[] ids) {
		int r=0;
		if(ids!=null) {
			String sql="update food set hits=hits-1 where id=(select foodid from diningcar where id=?)";
			for(String id:ids) db.update(sql, new String[] {id});
			
			sql="delete from diningcar where id=?";
			for(String id:ids) r+=db.update(sql, new String[] {id});
		}
		return r;
	}
	public  Map<String,List<Map<String,String>>>  showAllDC() {
		Map<String,List<Map<String,String>>> dcs=new HashMap<String, List<Map<String,String>>>();
		String sql="SELECT distinct u.id,u.username FROM user u JOIN  diningcar dc on u.id=dc.userid";
		List<Map<String,String>> users=db.getList(sql);
		for(Map<String,String> u:users) {
			dcs.put(u.get("username"), this.showDC(u.get("id")));
		}
		return dcs;
	}
	/**
	 * ��ȡ�û������Ϣ
	 * @param userid
	 * @return ��ͼ�����ϢList<Map<String,String>>
	 */
	public List<Map<String,String>> showDC(String userid){
		String sql="SELECT f.*,ft.typename,dc.id as dcid FROM food f JOIN foodtype ft on f.type=ft.id join diningcar dc on f.id=dc.foodid WHERE dc.userid=? ORDER BY dcid DESC";
		return db.getList(sql, new String[] {userid});		 
	}
	/**
	 * ��Ӳ�Ʒ����ͳ�
	 * @param userid
	 * @param idsѡ��Ķ����Ʒid
	 * @return ��ӵ�����
	 */
	public int addToDC(String userid,String[] ids) {
		int r=0;
		if(ids!=null) {
			String sql="insert into diningcar values(null,?,?)";
			for(String foodid:ids) {
				String[] params= {userid,foodid};
				r+=db.update(sql, params);
			}
			//�޸ĵ������hits
			sql="update food set hits=hits+1 where id=?";
			for(String foodid:ids) db.update(sql, new String[] {foodid});
		}
		return r;
	}
}
