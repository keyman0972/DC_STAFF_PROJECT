<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script language="javascript">
$(document).ready(function() {
    $("#tblGrid").initGrid({height:'480'});   
});
</script>
</head>
<body>
<s:form method="post" theme="simple" action="%{progAction}">
	<s:hidden name="labDcStaffMst.ver" />
	<div class="progTitle"> 
       <!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.ConfirmTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="staffId" />：</td>
				<td width="30%">
					<s:property  value="labDcStaffMst.staffId" />
					<s:hidden name="labDcStaffMst.staffId"/></td>
				<td width="20%" class="colNameAlign required">*<s:text name="staffName" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.staffName" />
					<s:hidden name="labDcStaffMst.staffName"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="staffBirth" />：</td>
				<td width="30%">
					<s:property  value="labDcStaffMst.staffBirth" />
					<s:hidden name="labDcStaffMst.staffBirth"/>
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="staffTitle" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.staffTitle.optCde" />&nbsp;-&nbsp;<s:property value="labDcStaffMst.staffTitle.optCdeNam" />
					<s:hidden name="labDcStaffMst.staffTitle.optCde"/>
					<s:hidden name="labDcStaffMst.staffTitle.optCdeNam"/>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcId" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.labDcSuppRel.labDcMst.dcId"/>&nbsp;-&nbsp;<s:property value="labDcStaffMst.labDcSuppRel.labDcMst.dcName"/>
					<s:hidden name="labDcStaffMst.labDcSuppRel.labDcMst.dcId" />
					<s:hidden name="labDcStaffMst.labDcSuppRel.labDcMst.dcName" />
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="suppId" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.labDcSuppRel.labSuppMst.suppId"/>&nbsp;-&nbsp;<s:property value="labDcStaffMst.labDcSuppRel.labSuppMst.suppName"/>
					<s:hidden name="labDcStaffMst.labDcSuppRel.labSuppMst.suppId" />
					<s:hidden name="labDcStaffMst.labDcSuppRel.labSuppMst.suppName" />
					<s:hidden name="labDcStaffMst.labDcSuppRel.dcSuppOid" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcTimePeriod" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCde"/>&nbsp;-&nbsp;
					<s:property value="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCdeNam"/>
					<s:hidden name="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCde" />
					<s:hidden name="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCdeNam" />
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcDistArea" />：</td>
				<td width="30%">
					<s:property value="labDcStaffMst.labDcSuppRel.labDcMst.dcDistArea"/>
					<s:property value="dcDistAreaName"/>
					<s:hidden name="labDcStaffMst.labDcSuppRel.labDcMst.dcDistArea" />
					<s:hidden name="dcDistAreaName" />
				</td>
			</tr>
		</table>
    </div>
    <!-- 按鍵組合 -->
        <s:include value="/WEB-INF/pages/include/include.ConfirmButton.jsp" /> 
    <!-- 按鍵組合 -->
</s:form>
</body>
</html>