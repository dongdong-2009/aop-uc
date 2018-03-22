package com.casic.tenant.model;

import com.hotent.core.model.BaseModel;

/**
 * 收发货地址信息实体
 * @author ypchenga
 *
 */
public class TenantAddress extends BaseModel {
	    // ID
		private Long  id;
		//userId
		private Long  userId;
		//收货人
		private String contact;
		//国籍
		private String nation;
		//所在地址-省
		private String province;
		//所在地址-市
		private String city;
		//所在地址-县
		private String country;
		//所在地址-详细地址
		private String addresDetail;
		//邮政编码
		private String postcode;
		//固定电话
		private String mobile;
		//手机电话
		private String telephone;
		//是否默认收货信息
		private String isDefault;
		//是否为收货地址1为收货地址0为发货地址
		private String isReceviced;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getNation() {
			return nation;
		}
		public void setNation(String nation) {
			this.nation = nation;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getAddresDetail() {
			return addresDetail;
		}
		public void setAddresDetail(String addresDetail) {
			this.addresDetail = addresDetail;
		}
		public String getPostcode() {
			return postcode;
		}
		public void setPostcode(String postcode) {
			this.postcode = postcode;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getTelephone() {
			return telephone;
		}
		public void setTelephone(String telephone) {
			this.telephone = telephone;
		}
		public String getIsDefault() {
			return isDefault;
		}
		public void setIsDefault(String isDefault) {
			this.isDefault = isDefault;
		}
		public String getIsReceviced() {
			return isReceviced;
		}
		public void setIsReceviced(String isReceviced) {
			this.isReceviced = isReceviced;
		}
	  
		
		
		
		
	
}
