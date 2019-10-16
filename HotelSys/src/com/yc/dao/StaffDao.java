package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 员工信息Dao
 * 所有的员工都是管理员(可登陆系统)
 * @author 沈俊羽
 *
 */
public class StaffDao {
	
	private DbHepler db=new DbHepler();
	
	/**
	 * 删除员工信息
	 * @param sid
	 * @return
	 * @throws SQLException
	 */
	public int deleteStaffById(String sid) throws SQLException{
		String sql="delete ht_staff where s_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(sid);
		return db.update(sql, params);
	}
	
	/**
	 * 根据员工编号修改员工密码
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateStaff(List<Object> params) throws SQLException{
		String sql="update ht_staff set s_pwd=? where s_id=?";
		return db.update(sql, params);
	}

	/**
	 * 根据员工编号查找员工信息
	 * 登陆时查找密码用
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findByID(String id) throws Exception{
		String sql="select s_pwd,s_power from ht_staff where s_id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(id);
		return db.selectSingle(sql, params);
	}
	
	/**
	 * 查询员工信息
	 * @param attribute 查询条件(如果是下拉框选择条件,该形参为下拉框得到的值  
	 * @param map  下拉框的相应文本框中的字符   将所有的键添加到map中,值全部都是文本框中的值用
	 * 例：
		String attribute = combo.getText().trim();
		String tends = text.getText().trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("I_ID", tends);
		map.put("U_ID", tends);
		map.put("BOR_STATUS", tends);
		try {
			List<Map<String, Object>> list = borrowDao.findBorrow(attribute, map);
			showTableBooks(table, list); //显示到表格中
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findStaffComposite(String attribute,Map<String, Object> map,int top,int low) throws Exception{
		StringBuffer sql=new StringBuffer();
		sql.append("select b.s_id,b.s_pwd,b.s_name,b.s_sex,b.s_id_number,b.s_tel,b.s_image,b.s_power,b.rn from (select a.s_id,a.s_pwd,a.s_name,a.s_sex,a.s_id_number,a.s_tel,a.s_image,a.s_power,rownum as rn from (select s_id,s_pwd,s_name,s_sex,s_id_number,s_tel,s_image,s_power from ht_staff  ");
		if (null==attribute||"".equals(attribute)) {
			return null;
		}
		List<Object> params=new ArrayList<Object>();
		
		if (attribute.equals("查找所有")) {
			params.add(top);
			params.add(low);
			sql.append(" order by s_id) a where rownum<=?) b where rn>=?");
			return db.selectMutil(sql.toString(), params);
		}
		
		if (attribute.equals("员工编号")) {
			sql.append(" where s_id=? ");
			params.add(map.get("S_ID"));
		}else if(attribute.equals("员工姓名")){
			sql.append(" where s_name=? ");
			params.add(map.get("S_NAME"));
		}else if(attribute.equals("性别")){
			sql.append(" where s_sex=? ");
			params.add(map.get("S_SEX"));
		}else if(attribute.equals("手机号")){
			sql.append(" where s_tel=? ");
			params.add(map.get("S_TEL"));
		}

		sql.append(" order by s_id) a where rownum<=?) b where rn>=?");
		
		params.add(top);
		params.add(low);
		return db.selectMutil(sql.toString(), params);
	}
	
	/**
	 * 查询员工信息
	 * @param attribute 查询条件(如果是下拉框选择条件,该形参为下拉框得到的值  
	 * @param map  下拉框的相应文本框中的字符   将所有的键添加到map中,值全部都是文本框中的值用
	 * 例：
		String attribute = combo.getText().trim();
		String tends = text.getText().trim();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("I_ID", tends);
		map.put("U_ID", tends);
		map.put("BOR_STATUS", tends);
		try {
			List<Map<String, Object>> list = borrowDao.findBorrow(attribute, map);
			showTableBooks(table, list); //显示到表格中
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findStaff(String attribute,Map<String, Object> map) throws Exception{
		StringBuffer sql=new StringBuffer();
		sql.append("select s_id,s_pwd,s_name,s_sex,s_id_number,s_tel,s_image,s_power from ht_staff ");
		if (null==attribute||"".equals(attribute)) {
			return null;
		}
		if (attribute.equals("查找所有")) {
			sql.append(" order by s_id");
			return db.selectMutil(sql.toString(), null);
		}
		List<Object> params=new ArrayList<Object>();
		if (attribute.equals("员工编号")) {
			sql.append(" where s_id=? ");
			params.add(map.get("S_ID"));
		}else if(attribute.equals("员工姓名")){
			sql.append(" where s_name=? ");
			params.add(map.get("S_NAME"));
		}else if(attribute.equals("性别")){
			sql.append(" where s_sex=? ");
			params.add(map.get("S_SEX"));
		}else if(attribute.equals("手机号")){
			sql.append(" where s_tel=? ");
			params.add(map.get("S_TEL"));
		}
		
		sql.append(" order by s_id");
		return db.selectMutil(sql.toString(), params);
	}
	
	/**
	 * 添加员工信息
	 * 六个'?'分别是
	 * 登陆密码、员工姓名、性别、身份证号、手机号、照片、权限
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int addStaff(List<Object> params) throws SQLException{
		String sql="insert into ht_staff values(seq_staff_id.nextval,?,?,?,?,?,?,?,null,null)";
		return db.update(sql, params);
	}
}
