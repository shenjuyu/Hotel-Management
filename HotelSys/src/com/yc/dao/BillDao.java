package com.yc.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yc.commons.DbHepler;

/**
 * 账单dao
 * @author 沈俊羽
 *
 */
public class BillDao {

	private DbHepler db=new DbHepler();
	
	
	/**
	 * 根据房间类型
	 * 查找所有账单信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>>FindBillByRtype(Map<String, Object> map) throws Exception{
		
		StringBuffer sql=new StringBuffer();
		List<Object> params=new ArrayList<Object>();
		 sql.append("select b_id,now_date,r_id,r_type,com_date,numdays,leave_date,violatemoney,"
		 		+ "c_name,c_tel,r_deposit,r_price,b_qt,b_joinpay,b_returnpay,b_allpay from ht_bill where  ");
		 if (null!=map.get("R_TYPE")) {
				//sql语句拼接
				sql.append(" r_type= ? ");
				params.add(map.get("R_TYPE"));
			}
		return db.selectMutil(sql.toString(), params);
	}
	
	/**
	 * 同时执行多条sql语句
	 * 删除   
	 * 修改状态   
	 * 添加账单信息
	 * 16个'?'分别是
	 * 账单号,结账时间,房间号,房间类型,入住时间,入住天数,退房时间,
	 * 违约金,客户姓名,手机号,房间押金,住房消费,其他消费,本次应收金额,本次退还金额,本次服务共收
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int updateBillEnterRoom(List<List<Object>> params) throws SQLException{
		//删除入住信息
		String sql="delete ht_enter where r_id=?";
		//修改房间状态
		String sql1="update ht_room set r_state=? where r_id=?";
		//添加账单信息
		String sql2="insert into ht_bill values(?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?,?,to_date(?,'yyyy-MM-dd'),?,to_date(?,'yyyy-MM-dd'),?,?,?,?,?,?,?,?,?,null,null,null,null)";
		
		List<String> sqls=new ArrayList<String>();
		sqls.add(sql);
		sqls.add(sql1);
		sqls.add(sql2);
		return db.update(sqls, params);
	}
	
	/**
	 * 查找所有账单信息
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> findBill() throws Exception{
		String sql="select b_id,now_date,r_id,r_type,com_date,numdays,"
				+ "leave_date,violatemoney,c_name,c_tel,r_deposit,"
				+ "r_price,b_qt,b_joinpay,b_returnpay,b_allpay from ht_bill";
		return db.selectMutil(sql, null);
	}
	
	/**
	 * 添加账单信息
	 * 16个'?'分别是
	 * 账单号,结账时间,房间号,房间类型,入住时间,入住天数,退房时间,
	 * 违约金,客户姓名,手机号,房间押金,住房消费,其他消费,本次应收金额,本次退还金额,本次服务共收
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int addBill(List<Object> params) throws SQLException{
		String sql="insert into ht_bill values(?,"
				+ "to_date(?,'yyyy-MM-dd'),?,?,"
				+ "to_date(?,'yyyy-MM-dd'),?,"
				+ "to_date(?,'yyyy-MM-dd'),?,?"
				+ ",?,?,?,?,?,"
				+ "?,?,null,null,null,null);";
		return db.update(sql, params);
	}
}
