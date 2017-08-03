<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<html>
<head>
<title></title>
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.popupWindow.plugin.js"/>"></script>	
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript">
<%--function validate() {
	$("#frmexan01003K").validate("clearPrompt");
 	var bool = false;
 	for(var i=0;i<$(".condition").size();i++){
 		if($(".condition").val() != null && $(".condition").val() != ""){
 			bool = true;
 		}
 	}
//  	alert(bool);
 	for(var j=0;j<$(".checkboxCondition").size();j++){
 		if($("#dcDistArea_"+j).prop("checked")){
 			bool = true;
 		}
 	}
 	alert(bool);
 	if(!bool){
 		$(".validate").validate("sendPrompt",{message:"<s:text name='fix.00257' /><s:text name='eC.0233' />"});
 	}
 	return $("#frmExan01003K").validate("showPromptWithErrors");
} --%>
function getParameter() {
	var param = "labDcStaffMst.staffId=" + $("#tblGrid").getSelectedRow().find('td').eq(0).text();
	return param;
}
$(document).ready(function() {
	$("#tblGrid").initGrid({lines:3});
	$('#tb').initPopupWindow({dailogWidth:'960', dailogHeight:'640'});

});
</script>
</head>
<body> 
<s:form id="frmexan01003K" theme="simple" action="%{progAction}" >
	<div class="progTitle">
  		<s:include value="/WEB-INF/pages/include/include.Title.jsp" />
	</div>
	<div id="tb">
		<fieldset id="listFieldset">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="staffId"/>：</td>
				<td width="30%">
					<s:textfield name="labDcStaffMst.staffId" cssClass="enKey condition validate" maxlength="5" size="16" />
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="staffName"/>：</td>
				<td width="30%">
					<s:textfield name="labDcStaffMst.staffName" cssClass="condition validate" maxlength="96" size="32"/>
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcId"/>：</td>
				<td width="30%">
					<s:textfield name="dcId" cssClass="condition validate" maxlength="5" size="16"/>
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcName"/>：</td>
				<td width="30%">
					<s:textfield name="dcName" cssClass="condition validate" maxlength="32" size="32"/>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcDistArea"/>：</td>
				<td width="30%">
					<span>
						<s:iterator value="dcDistAreaList" status="status" var="obj">
							<input type="checkbox" id="dcDistArea_<s:property value="#status.index" />" class="checkboxCondition validate" name="distAreaList[<s:property value="#status.index" />]" value="<s:property value="optCde" />"
							<s:if test="distAreaList[#status.index] != null">checked</s:if> />
							<s:property value="#obj.optCdeNam" />
						</s:iterator>
					</span>
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="suppName"/>：</td>
				<td width="30%">
					<s:textfield name="suppName" cssClass="condition validate" maxlength="16" size="16"/>
				</td>
			</tr>
		</table>
		<!-- 按鍵組合 --><s:include value="/WEB-INF/pages/include/include.ListButton.jsp" /><!-- 按鍵組合 --> 
		</fieldset>
		<table id="tblGrid" class ="labDcMstListMap" width="100%" border="0" cellpadding="2" cellspacing="1">
			<thead>
				<tr align="center" bgcolor="#e3e3e3">
					<th width="3%"><s:text name="fix.00164" /></th>
					<th width="10%"><s:text name="fix.00090" /></th>
					<th width="20%"><s:text name="staffName" /></th>   
					<th width="22%"><s:text name="staffTitle" /></th>
					<th width="18%"><s:text name="dcName" /></th>
					<th width="18%"><s:text name="dcTimePeriod" /></th>
					<th><s:text name="dcDistArea" /></th>
				</tr>
			 </thead>
			 <tbody>
				 <s:iterator value="labDcStaffMstListMap" status="status">
				 	<tr>
				 		<td style="display: none;">
				 			<s:property value="STAFF_ID" />
				 		</td>
						<td width="3%" id="sn" align="center"><s:property value="#status.index+1" /></td>
						<!-- 表單按鍵 --> 
						<td width="10%"><s:include value="/WEB-INF/pages/include/include.actionButton.jsp" /></td>
						<!-- 表單按鍵 -->
						<td width="20%"><label><s:property value="STAFF_ID" />&nbsp;-&nbsp;<s:property value="STAFF_NAME" /></label></td>
						<td width="22%"><label><s:property value="OPT_STAFF_CDE" />&nbsp;-&nbsp;<s:property value="OPT_STAFF_CDE_NAM" /></label></td>
						<td width="18%"><label><s:property value="DC_ID" />&nbsp;-&nbsp;<s:property value="DC_NAME" /></label></td>	
						<td width="18%"><label><s:property value="OPT_TIME_CDE" />&nbsp;-&nbsp;<s:property value="OPT_TIME_CDE_NAM" /></label></td>
						<td align="center"><label><s:property value="OPT_AREA_CDE" />&nbsp;-&nbsp;<s:property value="OPT_AREA_CDE_NAM" /></label></td>
<!-- 						<td style="display: none;"> -->
				 			<s:hidden name="dcDistAreaName" value="OPT_AREA_CDE_NAM" />
<!-- 				 		</td> -->
					</tr>
				 </s:iterator>
			 </tbody>
		</table>
	</div>
	<!-- 分頁按鍵列 --><s:include value="/WEB-INF/pages/include/include.PaginationBar.jsp" /><!-- 分頁按鍵列 -->
</s:form>
</body>
</html>