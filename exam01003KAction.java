package com.ddsc.km.exam.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.common.comm.service.ICommOptCdeService;
import com.ddsc.core.action.AbstractAction;
import com.ddsc.core.action.IBaseAction;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.exception.DdscAuthException;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.entity.LabDcStaffMst;
import com.ddsc.km.exam.entity.LabDcSuppRel;
import com.ddsc.km.exam.service.ILabDcMstService;
import com.ddsc.km.exam.service.ILabDcStaffMstService;

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

public class exam01003KAction extends AbstractAction implements IBaseAction {
	
	private static final long serialVersionUID = 3970383235812850067L;
	
	private ILabDcStaffMstService labDcStaffMstService;
	private ICommOptCdeService commOptCdeService;		   
	private List<Map<String,String>> labDcStaffMstListMap; //search method: list.jap
	private List<CommOptCde> dcDistAreaList;			   //顯示區域 list.jsp
	private LabDcStaffMst labDcStaffMst;				   //條件
	private String dcId;								   //條件
	private String dcName;								   //條件
	private List<String> distAreaList;				   	   //request 條件
	private String suppName;							   //條件
	
	private List<CommOptCde> staffTitleList;			  //edit.jsp
	
	private ILabDcMstService labDcMstService;			//物流公司找供應商
	private List<Map<String, Object>> results;
	private String params;
	private String dcDistAreaName;
	@Override
	public String init() throws Exception {
		try {
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String search() throws Exception {
		try{
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("staffId", labDcStaffMst.getStaffId());
			conditions.put("staffName", labDcStaffMst.getStaffName());
			conditions.put("dcId", dcId);
			conditions.put("dcName", dcName);
			if(distAreaList!=null){
				List<String> alist = distAreaList;
				alist.removeAll(Collections.singleton(null));
				conditions.put("dcDistArea", alist);
			}
			conditions.put("suppName", suppName);
			Pager resultPager = getLabDcStaffMstService().searchByConditions(conditions, getPager(), this.getUserInfo());
			
			List<Map<String, String>> alist = null;
			alist = (List<Map<String, String>>) resultPager.getData();
			this.setLabDcStaffMstListMap(alist);
			setPager(resultPager);
			
			if (alist == null || alist.size() <= 0) {
				this.addActionError(this.getText("w.0001"));
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}

	@Override
	public String create() throws Exception {
		try {
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_CREATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String createSubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				setNextAction(ACTION_CREATE_CONFIRM);
				return RESULT_CONFIRM;
			} else {
				return this.createConfirm();
			}
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
			return RESULT_EDIT;
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
			return RESULT_EDIT;
		}
	}

	@Override
	public String createConfirm() throws Exception {
		try {
			this.getLabDcStaffMstService().create(labDcStaffMst, getUserInfo());
			
			setNextAction(ACTION_CREATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		try{
			labDcStaffMst = labDcStaffMstService.get(labDcStaffMst.getStaffId(), this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));

		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_UPDATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String updateSubmit() throws Exception {
		try {
			if (hasConfirm()) {
				setNextAction(ACTION_UPDATE_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				return this.updateConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateConfirm() throws Exception {
		try{
			getLabDcStaffMstService().update(labDcStaffMst, getUserInfo());
			setNextAction(ACTION_UPDATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		try{
			labDcStaffMst = labDcStaffMstService.get(labDcStaffMst.getStaffId(), this.getUserInfo());
			
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_DELETE_CONFIRM);
		return SUCCESS;
	}

	@Override
	public String deleteConfirm() throws Exception {
		try{
			labDcStaffMst = getLabDcStaffMstService().delete(labDcStaffMst, getUserInfo());
			setNextAction(ACTION_DELETE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String query() throws Exception {
		try{
			labDcStaffMst = this.getLabDcStaffMstService().get(labDcStaffMst.getStaffId(), this.getUserInfo());
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_QUERY);
		return SUCCESS;
	}

	@Override
	public String copy() throws Exception {
		try{
			labDcStaffMst = labDcStaffMstService.get(labDcStaffMst.getStaffId(), this.getUserInfo());
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_COPY_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String copySubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				// 有確認頁
				setNextAction(ACTION_COPY_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				return this.copyConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String copyConfirm() throws Exception {
		try{
			labDcStaffMst = getLabDcStaffMstService().create(labDcStaffMst, getUserInfo());
			
			setNextAction(ACTION_COPY);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}
	}
	
	@Override
	public void validate() {
		try {
			setUpInfo();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateCreateSubmit() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateCreateConfirm() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(更新頁)
	 */
	public void validateUpdateSubmit() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(更新頁)
	 */
	public void validateUpdateConfirm() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(刪除頁)
	 */
	public void validateDeleteConfirm() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(複製頁)
	 */
	public void validateCopySubmit() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(複製頁)
	 */
	public void validateCopyConfirm() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 資料檢核
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean checkValidateRule() throws Exception {
		setUpInfo();
		boolean isValid = true;

		// 檢查參數代碼是否存在 (職稱)
		if (null != labDcStaffMst.getStaffTitle() && StringUtils.isNotEmpty(labDcStaffMst.getStaffTitle().getOptCde())) {
			CommOptCde dcStaffTitle = getCommOptCdeService().getByKey("DC03", labDcStaffMst.getStaffTitle().getOptCde(), this.getUserInfo());
			if (dcStaffTitle == null) {
				this.addFieldError("StaffTitle", this.getText("StaffTitle")+ this.getText("eP.0003"));
				isValid = false;
			} else {
				labDcStaffMst.setStaffTitle(dcStaffTitle);
			}
		}
		
		// 檢查參數代碼是否存在 (職稱)
		if (null != labDcStaffMst.getLabDcSuppRel().getDcSuppOid() && StringUtils.isNotEmpty(labDcStaffMst.getLabDcSuppRel().getDcSuppOid())) {
			LabDcSuppRel labDcSuppRel = this.getLabDcMstService().getLDSR(labDcStaffMst.getLabDcSuppRel().getDcSuppOid(), this.getUserInfo());
			if (labDcSuppRel == null) {
				this.addFieldError("DcSuppOid", this.getText("DcSuppOid")+ this.getText("eP.0003"));
				isValid = false;
			} else {
				labDcStaffMst.setLabDcSuppRel(labDcSuppRel);
			}
		}

		return isValid;
	}
	
	/**
	 * 檢核ID是否重複
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean checkPrimaryKey() throws Exception {
		boolean isValid = true;
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("dcId",labDcStaffMst.getStaffId());
		if (labDcStaffMstService.getDataRowCountByConditions(conditions, this.getUserInfo()) > 0) {
			this.addFieldError("dcId", this.getText("dcId") + this.getText("eP.0004"));
			isValid = false;
		}
		return isValid;
	}

	public String querySuppId() throws Exception{
		try{
			UserInfo info = getUserInfo();
			if(params != null && StringUtils.isNotEmpty(params)){
				JSONObject jo = JSONObject.fromObject(params);
				
				
				String date = (String) jo.get("date");
				info.setLocale(this.getLocale());
				List<Map<String,Object>> aListMap = this.getLabDcMstService().getSuppList(date, info);
//				System.out.println(aListMap);
				results = aListMap;
				
			}else {
				results = new ArrayList<Map<String, Object>>();
			}
		}catch (Exception e) {
			return e.getMessage();
		}
		
		return SUCCESS;
	}
	
	public String queryDcTimeArea() throws Exception{
		try{
			UserInfo info = getUserInfo();
			if(params != null && StringUtils.isNotEmpty(params)){
				JSONObject jo = JSONObject.fromObject(params);
				
				String date = (String) jo.get("dcId");
				info.setLocale(this.getLocale());
				List<Map<String,Object>> aListMap = this.getLabDcMstService().getDcTimeArea(date, info);
//				System.out.println(aListMap);
				results = aListMap;
				
			}else {
				results = new ArrayList<Map<String, Object>>();
			}
		}catch (Exception e) {
			return e.getMessage();
		}
		return SUCCESS;
	}
	
	@Deprecated
	@Override
	public String approve() throws Exception {
		return null;
	}
	
	public List<CommOptCde> getDcDistAreaList() {
		if(dcDistAreaList == null){
			this.setDcDistAreaList(this.getCommOptCdeService().getList("DC02", this.getUserInfo()));
		}
		return dcDistAreaList;
	}

	public void setDcDistAreaList(List<CommOptCde> dcDistAreaList) {
		this.dcDistAreaList = dcDistAreaList;
	}
	
	@JSON(serialize = false)
	public ILabDcStaffMstService getLabDcStaffMstService() {
		return labDcStaffMstService;
	}

	public void setLabDcStaffMstService(ILabDcStaffMstService labDcStaffMstService) {
		this.labDcStaffMstService = labDcStaffMstService;
	}
	
	@JSON(serialize = false)
	public ICommOptCdeService getCommOptCdeService() {
		return commOptCdeService;
	}

	public void setCommOptCdeService(ICommOptCdeService commOptCdeService) {
		this.commOptCdeService = commOptCdeService;
	}
	
	public List<Map<String, String>> getLabDcStaffMstListMap() {
		return labDcStaffMstListMap;
	}

	public void setLabDcStaffMstListMap(List<Map<String, String>> labDcStaffMstListMap) {
		this.labDcStaffMstListMap = labDcStaffMstListMap;
	}

	public LabDcStaffMst getLabDcStaffMst() {
		return labDcStaffMst;
	}

	public void setLabDcStaffMst(LabDcStaffMst labDcStaffMst) {
		this.labDcStaffMst = labDcStaffMst;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	
	public List<String> getDistAreaList() {
		
		return distAreaList;
	}

	public void setDistAreaList(List<String> distAreaList) {
		this.distAreaList = distAreaList;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	public List<CommOptCde> getStaffTitleList() {
		if(staffTitleList == null){
			this.setStaffTitleList(this.getCommOptCdeService().getList("DC03", this.getUserInfo()));
		}
		return staffTitleList;
	}

	public void setStaffTitleList(List<CommOptCde> staffTitleList) {
		this.staffTitleList = staffTitleList;
	}
	
	@JSON(serialize = false)
	public ILabDcMstService getLabDcMstService() {
		return labDcMstService;
	}

	public void setLabDcMstService(ILabDcMstService labDcMstService) {
		this.labDcMstService = labDcMstService;
	}

	public List<Map<String, Object>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}
	
	@JSON(serialize = false)
	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
	
	public String getDcDistAreaName() {
		return dcDistAreaName;
	}

	public void setDcDistAreaName(String dcDistAreaName) {
		this.dcDistAreaName = dcDistAreaName;
	}
	
}
