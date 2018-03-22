
package com.hotent.platform.model.system;

import com.hotent.core.model.BaseModel;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class SubSystem extends BaseModel {
	public static String CURRENT_SYSTEM = "CURRENT_SYSTEM";

	public static String DEFAULT_LOG = "/styles/default/images/resicon/home.png";

	public static short isLocal_Y = 1;
	public static short isLocal_N = 0;
	private long systemId;
	private String sysName;
	private String alias;
	private String logo;
	private String defaultUrl;
	private String memo;
	private Date createtime;
	private String creator;
	private Short allowDel;
	private Short needOrg;
	private Short isActive;
	private Long parentId;
	private Short isLocal;
	private String homePage;
	private String namespace;
	private Integer isShow;
	private Integer sync;//默认值为0. 0代表不同步，1代表企业用户同步，2代表企业用户角色同步
	
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	List<SysRole> roleList;

	public SubSystem() {
		this.systemId = 0L;

		this.allowDel = Short.valueOf((short) 0);

		this.isActive = Short.valueOf((short) 1);

		this.parentId = Long.valueOf(0L);

		this.isLocal = Short.valueOf((short) 1);
		
		this.isShow = Integer.valueOf(0);
		
		this.sync = Integer.valueOf(0);
	}

	public String getHomePage() {
		return this.homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public Short getIsLocal() {
		return this.isLocal;
	}

	public void setIsLocal(Short isLocal) {
		this.isLocal = isLocal;
	}

	public void setSystemId(long systemId) {
		this.systemId = systemId;
	}

	public long getSystemId() {
		return this.systemId;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysName() {
		return this.sysName;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getLogo() {
		if (this.logo != null) {
			return this.logo;
		}
		return DEFAULT_LOG;
	}

	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}

	public String getDefaultUrl() {
		return this.defaultUrl;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setAllowDel(Short allowDel) {
		this.allowDel = allowDel;
	}

	public Short getAllowDel() {
		return this.allowDel;
	}

	public void setNeedOrg(Short needOrg) {
		this.needOrg = needOrg;
	}

	public Short getNeedOrg() {
		return this.needOrg;
	}

	public void setIsActive(Short isActive) {
		this.isActive = isActive;
	}

	public Short getIsActive() {
		return this.isActive;
	}

	public List<SysRole> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(List<SysRole> roleList) {
		this.roleList = roleList;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	

	public Integer getSync() {
		return sync;
	}

	public void setSync(Integer sync) {
		this.sync = sync;
	}

	public int hashCode() {
		HashCodeBuilder has = new HashCodeBuilder();
		has.append(this.systemId);
		return has.toHashCode();
	}

	public boolean equals(Object object) {
		if (!(object instanceof SubSystem)) {
			return false;
		}
		SubSystem rhs = (SubSystem) object;
		return new EqualsBuilder().append(this.systemId, rhs.systemId).isEquals();
	}
}