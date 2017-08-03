package com.ddsc.km.exam.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.core.entity.BaseEntity;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.km.exam.entity.LabDcSuppRel;

/**
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2017/7/28</td>
 * <td>新建檔案</td>
 * <td>"keyman"</td>
 * </tr>
 * </table>
 * @author "keyman"
 *
 * 類別說明 :
 *
 *
 * 版權所有 Copyright 2008 © 中菲電腦股份有限公司 本網站內容享有著作權，禁止侵害，違者必究。 <br>
 * (C) Copyright Dimerco Data System Corporation Inc., Ltd. 2009 All Rights
 */

@Entity
@Table(name="LAB_DC_STAFF_MST")
public class LabDcStaffMst extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3197753773279323233L;
	
	private String staffId;
	private String staffName;
	private String staffName_lang1;
	private String staffName_lang2;
	private String staffName_lang3;
	private String staffBirth;
	private CommOptCde staffTitle;
//	private String dcSuppOid;
	private LabDcSuppRel labDcSuppRel;
	
	@Id
	@Column(name="STAFF_ID")
	public String getStaffId() {
		return staffId;
	}
	
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	@Transient
	public String getStaffName() {
		if(null != this.staffName && this.staffName.length()==0){
			return null;
		}else{
			return staffName;
			
		}
	}
	
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	
	@Column(name="STAFF_NAME_LANG1")
	public String getStaffName_lang1() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_1)) {
			return this.getStaffName();
		}else{
			return staffName_lang1;			
		}
	}

	public void setStaffName_lang1(String staffName_lang1) {
		this.staffName_lang1 = staffName_lang1;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_1)) {
			this.staffName = staffName_lang1;
		}
	}
	
	@Column(name="STAFF_NAME_LANG2")
	public String getStaffName_lang2() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_2)) {
			return this.getStaffName();
		}else{
			return staffName_lang2;			
		}
	}

	public void setStaffName_lang2(String staffName_lang2) {
		this.staffName_lang2 = staffName_lang2;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_2)) {
			this.staffName = staffName_lang2;
		}
	}
	
	@Column(name="STAFF_NAME_LANG3")
	public String getStaffName_lang3() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_3)) {
			return this.getStaffName();
		}else{
			return staffName_lang3;			
		}
	}

	public void setStaffName_lang3(String staffName_lang3) {
		this.staffName_lang3 = staffName_lang3;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_3)) {
			this.staffName = staffName_lang3;
		}
	}
	
	@Column(name="STAFF_BIRTH")
	public String getStaffBirth() {
		return staffBirth;
	}

	public void setStaffBirth(String staffBirth) {
		this.staffBirth = staffBirth;
	}
	
	@OneToOne(targetEntity = CommOptCde.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "STAFF_TITLE", referencedColumnName = "OPT_CDE_OID")
	public CommOptCde getStaffTitle() {
		return staffTitle;
	}

	public void setStaffTitle(CommOptCde staffTitle) {
		this.staffTitle = staffTitle;
	}	
	
//	@Column(name="DC_SUPP_OID")
//	public String getDcSuppOid() {
//		return dcSuppOid;
//	}
//
//	public void setDcSuppOid(String dcSuppOid) {
//		this.dcSuppOid = dcSuppOid;
//	}
	
	@OneToOne(targetEntity = LabDcSuppRel.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "DC_SUPP_OID", referencedColumnName = "DC_SUPP_OID")
	public LabDcSuppRel getLabDcSuppRel() {
		return labDcSuppRel;
	}

	public void setLabDcSuppRel(LabDcSuppRel labDcSuppRel) {
		this.labDcSuppRel = labDcSuppRel;
	}
	
}
