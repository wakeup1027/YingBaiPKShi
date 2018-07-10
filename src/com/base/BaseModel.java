package com.base;

import java.util.List;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

public class BaseModel<M extends Model<M>> extends Model<M>{
	
	protected static final long serialVersionUID = 1L;
	protected static final Log log = Log.getLog(BaseModel.class);
	
	/**
	 * 新增分页方法
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<M> findByPage(int page, int rows,String orderBy){
		String sql = "SELECT * FROM "+getTable().getName()+" "+orderBy+" LIMIT "+((page-1)*rows)+", "+rows;
		return find(sql);
	}
	
	/**
	 * 查找并且分页
	 * @param page
	 * @param rows
	 * @param orderBy
	 * @return
	 */
	public List<M> findByPage(String whereStr, int page, int rows,String orderBy){
		String sql = " SELECT * FROM " + getTable().getName() +" "+whereStr+" ORDER BY "+orderBy+" DESC LIMIT "+((page-1)*rows)+", "+rows;
		return find(sql);
	}
	
	public long count(String sql){
		List<M> datecount = find(sql);
		return datecount.size();
	}
	
	/**
	 * findCache是重构版findByCache，使用自己的Cache
	 * @param cacheName
	 * @param key
	 * @param sql
	 * @param paras
	 * @return
	 */
	/*public List<M> findCache(String cacheName, String key, String sql) {
		Cache cache = CacheUntil.get(cacheName);
		List<M> result = cache.get(key);
		if (result == null) {
			result = find(sql);
			cache.add(key, result);
		}
		return result;
	}*/
	
	/**
	 * 根据条件查询
	 */
	public List<M> findByWhere(String where, Object... paras) { 
		return findByWhereAndColumns(where, "*", paras);
	}
	
	public List<M> findByWhereAndColumns(String where, String columns, Object... paras) {
		String sql = " select " + columns + " from " + getTable().getName() + " " + where;
		return find(sql, paras);
	}
	
	public Table getTable() {
		return TableMapping.me().getTable(getClass());
	}
}
