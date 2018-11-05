package com.spring.starter.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="internet_banking")
public class InternetBanking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int internetBankingId;

	@NotNull
	@Size(min = 2)
	@Pattern(regexp = "^([A-Za-z0-9_])*$")
	private String internetBankingUserId;
	
	private boolean activeUser = false; 
	
	private boolean inactiveUser = false;

	private boolean canselInternetBanking = false;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="csrId")
	private CustomerServiceRequest customerServiceRequest;

	private boolean  softReject = false;

	public InternetBanking() {
		super();
	}

	public InternetBanking(@NotNull @Size(min = 2) @Pattern(regexp = "^([A-Za-z0-9_])*$") String internetBankingUserId,
						   boolean activeUser, boolean inactiveUser, boolean canselInternetBanking,
						   CustomerServiceRequest customerServiceRequest) {
		this.internetBankingUserId = internetBankingUserId;
		this.activeUser = activeUser;
		this.inactiveUser = inactiveUser;
		this.canselInternetBanking = canselInternetBanking;
		this.customerServiceRequest = customerServiceRequest;
	}

	public int getInternetBankingId() {
		return internetBankingId;
	}

	public void setInternetBankingId(int internetBankingId) {
		this.internetBankingId = internetBankingId;
	}

	public boolean isActiveUser() {
		return activeUser;
	}

	public void setActiveUser(boolean activeUser) {
		this.activeUser = activeUser;
	}

	public boolean isInactiveUser() {
		return inactiveUser;
	}

	public void setInactiveUser(boolean inactiveUser) {
		this.inactiveUser = inactiveUser;
	}

	public CustomerServiceRequest getCustomerServiceRequest() {
		return customerServiceRequest;
	}

	public void setCustomerServiceRequest(CustomerServiceRequest customerServiceRequest) {
		this.customerServiceRequest = customerServiceRequest;
	}

	public boolean isCanselInternetBanking() {
		return canselInternetBanking;
	}

	public void setCanselInternetBanking(boolean canselInternetBanking) {
		this.canselInternetBanking = canselInternetBanking;
	}

	public String getInternetBankingUserId() {
		return internetBankingUserId;
	}

	public void setInternetBankingUserId(String internetBankingUserId) {
		this.internetBankingUserId = internetBankingUserId;
	}

	public boolean isSoftReject() {
		return softReject;
	}

	public void setSoftReject(boolean softReject) {
		this.softReject = softReject;
	}
}
