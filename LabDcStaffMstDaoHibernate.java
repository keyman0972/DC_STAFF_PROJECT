package com.ddsc.km.exam.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabDcStaffMstDao;
import com.ddsc.km.exam.entity.LabDcStaffMst;

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

public class LabDcStaffMstDaoHibernate extends GenericDaoHibernate<LabDcStaffMst,String> implements ILabDcStaffMstDao {

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		
		String staffNam_lang = LocaleDataHelper.getPropertityWithLocalUpper("STAFF_NAME", userInfo.getLocale());
		String optCdeNam_lang = LocaleDataHelper.getPropertityWithLocalUpper("OPT_CDE_NAM", userInfo.getLocale());
		String suppName_lang = LocaleDataHelper.getPropertityWithLocalUpper("SUPP_NAME", userInfo.getLocale());
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT LDSM.STAFF_ID, LDSM."+staffNam_lang+" AS STAFF_NAME, LDSM.STAFF_TITLE, LDSM.STAFF_BIRTH, ");
		sbsql.append("	COC3.OPT_CDE AS OPT_STAFF_CDE, COC3."+optCdeNam_lang+" AS OPT_STAFF_CDE_NAM, ");
		sbsql.append("	 LDCM.DC_ID, LDCM.DC_NAME, TIMEOID.OPT_CDE AS OPT_TIME_CDE, TIMEOID."+optCdeNam_lang+" AS OPT_TIME_CDE_NAM, ");
		sbsql.append("	AREA.OPT_CDE AS OPT_AREA_CDE, AREA."+optCdeNam_lang+" AS OPT_AREA_CDE_NAM ");
		sbsql.append("FROM LAB_DC_STAFF_MST LDSM ");
		sbsql.append("INNER JOIN LAB_DC_SUPP_REL LDSR ON LDSM.DC_SUPP_OID = LDSR.DC_SUPP_OID ");
		sbsql.append("INNER JOIN LAB_DC_MST LDCM ON LDSR.DC_ID = LDCM.DC_ID ");
//		if (StringUtils.isNotBlank((String) conditions.get("suppName"))&&StringUtils.isNotEmpty((String) conditions.get("suppName"))){
//			sbsql.append("INNER JOIN LAB_SUPP_MST LSM ON LDSR.SUPP_ID = LSM.SUPP_ID ");
//		}
		sbsql.append("INNER JOIN COMM_OPT_CDE COC3 ON LDSM.STAFF_TITLE = COC3.OPT_CDE_OID ");
		sbsql.append("INNER JOIN COMM_OPT_CDE TIMEOID ON LDCM.DC_TIME_PERIOD = TIMEOID.OPT_CDE_OID ");
		sbsql.append("INNER JOIN COMM_OPT_CDE AREA ON LDCM.DC_DIST_AREA = AREA.OPT_CDE AND AREA.OPT_CTG_CDE = 'DC02' ");
		
		String keyword = "WHERE ";
		List<Object> value = new ArrayList<Object>();
		if (StringUtils.isNotEmpty((String) conditions.get("staffId"))) {
			sbsql.append(keyword + "LDSM.STAFF_ID LIKE ? ");
			value.add(conditions.get("staffId") + "%");
			keyword = "AND ";
		}
		
		if (StringUtils.isNotEmpty((String) conditions.get("staffName"))) {
			sbsql.append(keyword + "LDSM."+staffNam_lang+" LIKE ? ");
			value.add("%"+conditions.get("staffName") + "%");
			keyword = "AND ";
		}
		
		if (StringUtils.isNotEmpty((String) conditions.get("dcId"))) {
			sbsql.append(keyword + "LDCM.DC_ID LIKE ? ");
			value.add(conditions.get("dcId") + "%");
			keyword = "AND ";
		}
		
		if (StringUtils.isNotEmpty((String) conditions.get("dcName"))) {
			sbsql.append(keyword + "LDCM.DC_NAME LIKE ? ");
			value.add("%"+conditions.get("dcName") + "%");
			keyword = "AND ";
		}
		
		List<String> dcDistArea = (List<String>) conditions.get("dcDistArea");
		if (dcDistArea != null && !dcDistArea.isEmpty()) {
			sbsql.append(keyword +"COC1.OPT_CDE IN( "+ this.getSqlQuestionMark(dcDistArea.size())+" ) ");
			value.addAll(dcDistArea);
			keyword = "AND ";
		}
		
//		if (StringUtils.isNotEmpty((String) conditions.get("suppName"))) {
//			sbsql.append(keyword + "LSM."+suppName_lang+" LIKE ? ");
//			value.add(conditions.get("suppId") + "%");
//			keyword = "AND ";
//		}
		
		sbsql.append("ORDER BY LDSM.STAFF_ID, LDCM.DC_ID ");
		
		// 建立List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>(); 並add
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("STAFF_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("STAFF_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_STAFF_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_STAFF_CDE_NAM", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_TIME_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_TIME_CDE_NAM", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_AREA_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_AREA_CDE_NAM", Hibernate.STRING));
		
		// 回傳
		return super.findBySQLQueryMapPagination(sbsql.toString(), pager, scalarList, value, userInfo);
	}

}
