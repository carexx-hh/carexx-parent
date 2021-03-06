package com.sh.carexx.model.uc;

import java.util.Date;

public class UserOAuth {
	private Long id;

	private Integer userId;

	private Byte identityType;

	private String identifier;

	private Byte identity;
	
	private String credential;

	private Integer staffId;
	
	private Integer userAcctId;
	
	private Integer instId;
	
	private Byte identityStatus;

	private Date createTime;

	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Byte getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Byte identityType) {
		this.identityType = identityType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier == null ? null : identifier.trim();
	}

	public Byte getIdentity() {
		return identity;
	}

	public void setIdentity(Byte identity) {
		this.identity = identity;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential == null ? null : credential.trim();
	}
	
	public Integer getStaffId() {
		return staffId;
	}

	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}

	public Integer getUserAcctId() {
		return userAcctId;
	}

	public void setUserAcctId(Integer userAcctId) {
		this.userAcctId = userAcctId;
	}
	
	public Integer getInstId() {
		return instId;
	}

	public void setInstId(Integer instId) {
		this.instId = instId;
	}

	public Byte getIdentityStatus() {
		return identityStatus;
	}

	public void setIdentityStatus(Byte identityStatus) {
		this.identityStatus = identityStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
}