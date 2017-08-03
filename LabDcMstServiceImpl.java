package com.ddsc.km.exam.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.BeanUtilsHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabDcMstDao;
import com.ddsc.km.exam.dao.ILabDcSuppRelDao;
import com.ddsc.km.exam.entity.LabDcMst;
import com.ddsc.km.exam.entity.LabDcSuppRel;
import com.ddsc.km.exam.service.ILabDcMstService;
import com.ddsc.km.lab.entity.LabSuppMst;
import com.ddsc.km.lab.service.ILabSuppMstService;

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

public class LabDcMstServiceImpl implements ILabDcMstService {
	
	private ILabDcMstDao labDcMstDao;
	private ILabDcSuppRelDao labDcSuppRelDao;
	private ILabSuppMstService labSuppMstService;

	@Override
	public LabDcMst create(LabDcMst entity, UserInfo info) throws DdscApplicationException {
		try{
			labDcMstDao.save(entity, info);
			String dcId = entity.getDcId();
			LabSuppMst labSuppMstPo;
			LabDcMst labDcMstPo;
			for(LabDcSuppRel labDcSuppRel:entity.getLabDcSuppRelList()){
				labSuppMstPo = new LabSuppMst();
				labSuppMstPo = this.getLabSuppMstService().get(labDcSuppRel.getLabSuppMst().getSuppId(), info);
				labDcMstPo = new LabDcMst();
				labDcMstPo = this.getLabDcMstDao().get(dcId, info);
				labDcSuppRel.setLabDcMst(labDcMstPo);
				labDcSuppRel.setLabSuppMst(labSuppMstPo);
				this.labDcSuppRelDao.save(labDcSuppRel, info);	

				
			}
			
			return entity;
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabDcMst update(LabDcMst entity, UserInfo info) throws DdscApplicationException {
		try{
			LabDcMst labDcMstPo = this.labDcMstDao.get(entity.getDcId(), info);
			
			if(entity.getVer().getTime() == labDcMstPo.getVer().getTime()){
								
				List<LabDcSuppRel> labDcSuppRelList = this.getLabDcSuppRelDao().getList(entity.getDcId(), info);
				
				if(labDcSuppRelList != null){
					labDcMstPo.setLabDcSuppRelList(labDcSuppRelList);
				}else{
					labDcMstPo.setLabDcSuppRelList(new ArrayList<LabDcSuppRel>());
				}
				//全部移除
				for(LabDcSuppRel labDcSuppRel:labDcMstPo.getLabDcSuppRelList()){
					this.getLabDcSuppRelDao().delete(labDcSuppRel, info);
					this.getLabDcSuppRelDao().flush();
				}
				//全部新增
				for(LabDcSuppRel labDcSuppRel:entity.getLabDcSuppRelList()){
					if(labDcSuppRel!= null){
						LabSuppMst labDcSuppMstPo = this.getLabSuppMstService().get(labDcSuppRel.getLabSuppMst().getSuppId(), info);
						
						labDcSuppRel.setLabDcMst(this.getLabDcMstDao().get(entity.getDcId(), info));
						
						labDcSuppRel.setLabSuppMst(labDcSuppMstPo);
						this.getLabDcSuppRelDao().save(labDcSuppRel, info);						
					}
				}

				BeanUtilsHelper.copyProperties(labDcMstPo, entity, entity.obtainLocaleFieldNames());
				this.getLabDcMstDao().update(labDcMstPo, info);
				return labDcMstPo;
				
			}else {
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR, "eP.0013");
			}

		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabDcMst delete(LabDcMst entity, UserInfo info) throws DdscApplicationException {
		try{
			
			LabDcMst labDcMstPo = getLabDcMstDao().get(entity.getDcId(), info);
			if(labDcMstPo.getVer().getTime()==entity.getVer().getTime()){
				List<LabDcSuppRel> labDcSuppRelList = this.getLabDcSuppRelDao().getList(labDcMstPo.getDcId(), info);
				for(LabDcSuppRel labDcSuppRelPo:labDcSuppRelList){
					
					this.getLabDcSuppRelDao().delete(labDcSuppRelPo, info);
				}
				this.getLabDcMstDao().delete(labDcMstPo, info);
				this.getLabDcSuppRelDao().flush();
				this.getLabDcMstDao().flush();
				return labDcMstPo;				
			}else {
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR,"eP.0013");
			}

		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Override
	public LabDcMst get(String id, UserInfo info) throws DdscApplicationException {
		try{
			
			LabDcMst labDcMst = this.getLabDcMstDao().get(id, info);			
			
			List<LabDcSuppRel> labSuppDcRel = this.getLabDcSuppRelDao().getList(id, info);
			
			if(labSuppDcRel !=null){
				labDcMst.setLabDcSuppRelList(labSuppDcRel);
			}else{
				labDcMst.setLabDcSuppRelList(new ArrayList<LabDcSuppRel>());
			}
			return labDcMst;			
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Deprecated
	@Override
	public List<LabDcMst> searchAll(UserInfo info) throws DdscApplicationException {
		return null;
	}

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		try{
			return labDcMstDao.searchByConditions(conditions, pager, userInfo);			
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	@Deprecated
	@Override
	public List<LabDcMst> searchByConditions(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<Object> queryDataByParamsByService(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}

	@Override
	public int getDataRowCountByConditions(Map<String, Object> conditions, UserInfo info) throws DdscApplicationException {
			
		return 0;

	}
	
	public List<Map<String, Object>> getSuppList(String id,UserInfo userInfo)  throws DdscApplicationException{
		try{
			return this.getLabDcSuppRelDao().getSuppIdList(id, userInfo);
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	public List<Map<String, Object>> getDcTimeArea(String id, UserInfo userInfo) throws DdscApplicationException{
		try{
			return this.getLabDcMstDao().getDcTimeArea(id, userInfo);
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	

	public LabDcSuppRel getLDSR(String id, UserInfo info) throws DdscApplicationException {
		try{
			
			LabDcSuppRel labDcSuppRel = this.getLabDcSuppRelDao().get(id, info);
			
			return labDcSuppRel;			
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	public ILabDcMstDao getLabDcMstDao() {
		return labDcMstDao;
	}

	public void setLabDcMstDao(ILabDcMstDao labDcMstDao) {
		this.labDcMstDao = labDcMstDao;
	}

	public ILabDcSuppRelDao getLabDcSuppRelDao() {
		return labDcSuppRelDao;
	}

	public void setLabDcSuppRelDao(ILabDcSuppRelDao labDcSuppRelDao) {
		this.labDcSuppRelDao = labDcSuppRelDao;
	}

	public ILabSuppMstService getLabSuppMstService() {
		return labSuppMstService;
	}

	public void setLabSuppMstService(ILabSuppMstService labSuppMstService) {
		this.labSuppMstService = labSuppMstService;
	}
	
}
