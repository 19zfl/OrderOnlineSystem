package util;

import java.sql.*;
import java.util.*;

public class DBUtil {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	public DBUtil() { }
	/**
	 * �����������������ݿ�����
	 */
	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/whtcc", "root", "512619");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Map<String,String>> getList(String sql,String[] params){
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		init();
		try {
			pstmt = conn.prepareStatement(sql);
			// ����sql�����У�ռλ������Ӧ��ֵ
			setParams(params);
			//ִ�в�ѯ������ؽ����
			rs= pstmt.executeQuery();
			ResultSetMetaData rsmd= rs.getMetaData();
			while(rs.next()) {
				Map<String,String> m=new HashMap<String, String>();
				//��ȡ����,����������ΪMap�ļ����Ѷ�Ӧ��������ֵ��ΪMap��ֵ
				for(int i=1;i<=rsmd.getColumnCount();i++) {
					String colName= rsmd.getColumnLabel(i);
					String v=rs.getString(colName);
					if(v!=null) m.put(colName, v);
				}
				list.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//�ر��ͷ�������Դ
			closeAll();
		}
		return list;
	}
	public List<Map<String,String>> getList(String sql){
		return this.getList(sql, null);
	}
	/**
	 * ��ȡList������еĵ�һ��Ԫ�أ�Map��
	 * @param sql
	 * @param params
	 * @return Map<String,String>
	 */
	public Map<String,String> getMap(String sql,String[] params){
		Map<String,String> m=null;
		//�Ȼ�ȡList<Map<String,String>>����
		List<Map<String,String>> list=this.getList(sql, params);
		if(list!=null&&list.size()!=0) 
			m=list.get(0);
		return m;
	}
	public Map<String,String> getMap(String sql){
		 
		return this.getMap(sql, null);
	}
	/**
	 * 
	 * @return
	 */
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/meal", "root", "whtcc");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	public void  setParams(String[] params) {
		try {
			if (params != null) {
		 
			for (int i = 0; i < params.length; i++)
				pstmt.setString(i + 1, params[i]);
		}
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * �ر��ͷ���Դ
	 */
	public void closeAll() {
		
			try {
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();
				if(conn!=null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * ִ����ɾ������
	 * @param sql
	 * @param params
	 * @return ��Ӱ�������
	 */
	public int update(String sql, String[] params) {
		int result = 0;
		init();
		try {
			pstmt = conn.prepareStatement(sql);
			// ����sql�����У�ռλ������Ӧ��ֵ
			setParams(params);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			//�ر��ͷ�������Դ
			closeAll();
		}

		return result;
	}
	public int update(String sql) {
		return this.update(sql, null);
	}
}
