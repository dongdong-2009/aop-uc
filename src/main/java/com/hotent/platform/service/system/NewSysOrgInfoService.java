package com.hotent.platform.service.system;

import com.hotent.core.db.BaseDao;
import com.hotent.core.web.query.QueryFilter;
import com.hotent.platform.model.system.NewSysOrgInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class NewSysOrgInfoService extends BaseDao<NewSysOrgInfo> {
	public Class<?> getEntityClass() {
		return NewSysOrgInfo.class;
	}

	public List<NewSysOrgInfo> getAllSysOrgInfo(int lmt, int pageSize,NewSysOrgInfo newSysOrgInfo) {
		// TODO Auto-generated method stub
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("cur", lmt);
		params.put("size", pageSize);
		params.put("createtime", newSysOrgInfo.getCreatetime());
		params.put("updatetime", newSysOrgInfo.getUpdatetime());
		return getBySqlKey("getAllSysOrgInfo",params);
	}

	public int getCount() {
		int size = 0;
		List<Integer> list = new ArrayList<Integer>();
		list = this.getSqlSessionTemplate().selectList("com.hotent.platform.model.system.NewSysOrgInfo.getCount");
		if(list!=null){
			size = list.get(0);
		}
		return size;
	}

}