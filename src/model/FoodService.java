package model;

import java.util.List;
import java.util.Map;

import util.DBUtil;

public class FoodService {
	private DBUtil db=null;
	public FoodService() {
		 db=new DBUtil();
	}
	public List<Map<String,String>> getHotFoods(){
		String sql="SELECT food.id,food.foodname,food.price,food.picture FROM food ORDER BY food.hits DESC LIMIT 0, 3";
		return db.getList(sql);
	}
	public List<Map<String,String>> getSpecialFoods(){
		String sql="SELECT food.id,food.foodname,food.comment,food.picture FROM food where comment>0 ORDER BY food.hits DESC LIMIT 0, 3";
		return db.getList(sql);
	}
	public List<Map<String,String>> getRecommFoods(){
		String sql="SELECT food.id,food.foodname,food.price,food.picture FROM food where comment=0 ORDER BY food.hits DESC LIMIT 0, 3";
		return db.getList(sql);
	}
	public Map<String,String> getFood(String id){
		String sql="SELECT f.*, ft.typename FROM food  f join foodtype  ft on f.type=ft.id WHERE f.id =?";
		//String sql="SELECT f.*, ft.typename FROM food as f , foodtype as ft on f.type=ft.id WHERE f.id =?";
		return db.getMap(sql,new String[] {id});
	}
	public List<Map<String,String>> getFoods(String s_fn,String s_type){
		String sql="SELECT f.*, ft.typename FROM food  f join foodtype  ft on f.type=ft.id where 1=1";
		String[] params=null;
		if(s_fn!=null&&s_type!=null) { 
			sql+=" and foodname like ? and type like ?";
		    params=new String[] {"%"+s_fn+"%","%"+s_type+"%"};}
		else if(s_fn==null&&s_type!=null){ 
			sql+=" and type like ?";
		    params=new String[] {"%"+s_type+"%"};}
		else if(s_fn!=null&&s_type==null){ 
			sql+=" and foodname like ?";
		    params=new String[] {"%"+s_fn+"%"};}
		sql+=" order by hits desc,type asc,f.id asc";
		return db.getList(sql, params);
	}
   public int editFood(String fn,String fea,String mat,String price,String type,String picture,String comment,String id) {
	   String sql="UPDATE food set foodname=?,feature=?,material=?,price=?,type=?";
	   if(picture!=null) sql+=",picture=?";
	   sql+=",comment=? where id=?";
	   if(picture!=null) return db.update(sql, new String[] {fn,fea,mat,price,type,picture,comment,id});
	   else   return db.update(sql, new String[] {fn,fea,mat,price,type,comment,id});
   }
   
   public int addFood(String fn,String fea,String mat,String price,String type,String picture,String comment) {
	   String sql="insert into food values(null,?,?,?,?,?,?,0,?)";
	 	   return db.update(sql, new String[] {fn,fea,mat,price,type,picture,comment});
	  
   }
   public int delFood(String id) {
	   String sql="delete from food where id=?";
	   return db.update(sql,new String[] {id});
   }
	public static void main(String[] args) {
		 FoodService fs=new FoodService();
		 Map<String,String> li=fs.getFood("14");
		 System.out.println(li.get("foodname"));

	}

}
