package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 顾客信息Dao
 * 
 * @author 沈俊羽
 *
 */
public class CustomerDao {

	private DbHepler db = new DbHepler();

	// 查询所有

	/**
	 * 查询顾客信息
	 */
	public List<Map<String, Object>> findCustomer2(String attribute, Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select cu.c_name,cu.c_id_number,cu.c_tel,cu.c_sex,cu.c_id,ro.r_type,ro.r_price,ro.r_deposit,re.e_res_comdate,re.e_reservedate ,re.e_leave_date from ht_reserve re,ht_room ro,ht_customer cu "
						+ "where re.c_id=cu.c_id and re.r_id=ro.r_id   ");
		if (null == attribute || "".equals(attribute)) {
			return null;
		}
		if (attribute.equals("查找所有")) {
			sql.append(" order by c_id ");
			return db.selectMutil(sql.toString(), null);
		}
		List<Object> params = new ArrayList<Object>();

		System.out.println(sql.toString());
		return db.selectMutil(sql.toString(), params);
	}

	/**
	 * 根据电话号码查找客户信息
	 * 
	 * @param tel
	 * @return
	 * @throws Exception
	 */
	public boolean find(String tel) throws Exception {
		String sql = "select c_id from ht_customer  where  c_tel=?";
		List<Object> params = new ArrayList<Object>();
		params.add(tel);
		Map<String, Object> map = db.selectSingle(sql, params);
		if (map == null)
			return false;
		return true;
	}

	/**
	 * 修改客户的手机号
	 * 
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateCustomer(List<Object> params) throws SQLException {
		String sql = "update ht_customer set c_tel=? where c_id=?";
		return db.update(sql, params);
	}

	/**
	 * 修改客户的手机号根据客户姓名
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateCustomer1(List<Object> params) throws Exception {
		String sql = "update ht_customer set c_tel=? where c_name=?";
		return db.update(sql, params);
	}

	/**
	 * 添加顾客信息 四个'?'分别是 客户姓名、手机号、身份证号、性别
	 * 
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int addCustomer(List<Object> params) throws SQLException {
		String sql = "insert into ht_customer values(seq_customer_id.nextval,?,?,?,?,null,null,null)";
		return db.update(sql, params);
	}

	/**
	 * 查询顾客信息
	 * 
	 * @param attribute
	 *            查询条件(如果是下拉框选择条件,该形参为下拉框得到的值
	 * @param map
	 *            下拉框的相应文本框中的字符 将所有的键添加到map中,值全部都是文本框中的值用 例： String attribute =
	 *            combo.getText().trim(); String tends = text.getText().trim();
	 *            Map<String, Object> map = new HashMap<String, Object>();
	 *            map.put("I_ID", null); map.put("U_ID", tends);
	 *            map.put("BOR_STATUS", null); try { List<Map<String, Object>>
	 *            list = borrowDao.findBorrow(attribute, map);
	 *            showTableBooks(table, list); //显示到表格中 } catch (Exception e1) {
	 *            // TODO Auto-generated catch block e1.printStackTrace(); }
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findCustomer(String attribute, Map<String, Object> map) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select c_id,c_name,c_tel,c_id_number,c_sex from ht_customer ");
		if (null == attribute || "".equals(attribute)) {
			return null;
		}
		if (attribute.equals("查找所有")) {
			sql.append(" order by c_id ");
			return db.selectMutil(sql.toString(), null);
		}
		List<Object> params = new ArrayList<Object>();
		sql.append(" where ");
		if (null != map.get("C_ID") && attribute.equals("客户编号")) {
			sql.append(" c_id=? ");
			params.add(map.get("C_ID"));
		} else if (null != map.get("C_NAME") && attribute.equals("客户姓名")) {
			sql.append(" c_name=? ");
			params.add(map.get("C_NAME"));
		} else if (null != map.get("C_SEX") && attribute.equals("性别")) {
			sql.append(" c_sex=? ");
			params.add(map.get("C_SEX"));
		} else if (null != map.get("C_TEL") && attribute.equals("手机号")) {
			sql.append(" c_tel=? ");
			params.add(map.get("C_TEL"));
		}

		sql.append(" order by c_id ");
		return db.selectMutil(sql.toString(), params);
	}
}
