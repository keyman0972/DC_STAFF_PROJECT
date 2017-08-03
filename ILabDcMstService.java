package com.ddsc.km.exam.service;

import java.util.List;
import java.util.Map;

import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.service.IBaseCRUDService;
import com.ddsc.core.service.IBaseSearchService;
import com.ddsc.km.exam.entity.LabDcMst;
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
 * <td>2017/7/18</td>
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

public interface ILabDcMstService extends IBaseCRUDService<LabDcMst, String>, IBaseSearchService<LabDcMst, String> {
	
	public int getDataRowCountByConditions(Map<String, Object> conditions, UserInfo info) throws DdscApplicationException;
	//AJAX供應商代碼
	public List<Map<String, Object>> getSuppList(String id,UserInfo userInfo)  throws DdscApplicationException;
	//AJAX區域、時段
	public List<Map<String, Object>> getDcTimeArea(String id, UserInfo userInfo) throws DdscApplicationException;
	//檢核用
	public LabDcSuppRel getLDSR(String id, UserInfo info) throws DdscApplicationException;
}
