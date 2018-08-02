package DAO;


import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtil.DBUtil;
/**
 * 模块说明： DAO基类，用于选择AdminDAO或者StudentDAO,并且包括init和destroy DAO的方法，
 * 
 */
public abstract class BaseDAO {
	protected final DBUtil db = DBUtil.getDBUtil();
	protected ResultSet rs;
	private static BaseDAO baseDAO;

	public BaseDAO() {
		init();
	}

	private void init() {
		// buildAbilityDAO();
	}

	// protected abstract void buildAbilityDAO();

	public static synchronized BaseDAO getAbilityDAO(DAO dao) {
		switch (dao) {
		case AdminDAO:
			if (baseDAO == null || baseDAO.getClass() != AdminDAO.class) {
				baseDAO = AdminDAO.getInstance();
			}
			break;
		case StudentDAO:
			if (baseDAO == null || baseDAO.getClass() != StudentDAO.class) {
				baseDAO = StudentDAO.getInstance();
			}
			break;
		default:
			break;
		}
		return baseDAO;
	}

	protected void destroy() {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			db.close();
		}
	}
}
