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
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridEditList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.date.plugin.js"/>"></script>
<script language="javascript">
var oTable;
//畫面欄位檢核
function validate() {
	$("#frmSys01003K").validate("clearPrompt"); 
	
	$("#staffId").validateRequired({fieldText:'<s:text name="staffId" />'});
	$("#staffName").validateRequired({fieldText:'<s:text name="staffName" />'});
	
	$("#staffTitle").validateRequired({fieldText:'<s:text name="staffTitle" />'});
	$("#dcId").validateRequired({fieldText:'<s:text name="dcId" />'});
	$("#selectSuppId").validateRequired({fieldText:'<s:text name="suppId" />'});
	<%--if(typeof($("#staffBirth").val())!= undefined && $("#staffBirth").val()!= null && $("#staffBirth").val() !=''){
		if(getAge($("#staffBirth").val())<18){
			$("#staffBirth").validateRequired({fieldText:'<s:text name="staffBirth" />'});
		}
	}else{
		$("#staffBirth").validateRequired({fieldText:'<s:text name="staffBirth" />'});
	}
	 --%>
    return $("#frmSys01003K").validate("showPromptWithErrors");
}
//Ajax物流公司名稱
function getDcName(){
	var dcId = $("#dcId").val();
	if(dcId!=null && dcId!=""){
		$.ajax({
			type: 'post',
			url:'<s:url value="/ajax/km/queryDcTimeArea.action" />',
			async:false,
			data:{params: '{dcId: "' + dcId + '"}'},
			success: function (rtn_data) {
				if(rtn_data.results.length == 1 && rtn_data.results[0] != "" && rtn_data.results[0] != null){
					$("#dcName").html(rtn_data.results[0].DC_NAME);
					$("#dcTimeCde").html(rtn_data.results[0].DC_TIME_CDE);
					$("#dcTimeCdeName").html(rtn_data.results[0].DC_TIME_NAME);
					$("#dcDistAreaCde").html(rtn_data.results[0].DC_AREA_CDE);
					$("#dcDistAreaCdeName").html(rtn_data.results[0].DC_AREA_NAME);
					
					$("#hiddDcName").val(rtn_data.results[0].DC_NAME);
					$("#hiddDcTimeCde").val(rtn_data.results[0].DC_TIME_CDE);
					$("#hiddDcTimeCdeName").val(rtn_data.results[0].DC_TIME_NAME);
					$("#hiddDcDistAreaCde").val(rtn_data.results[0].DC_AREA_CDE);
					$("#hiddDcDistAreaCdeName").val(rtn_data.results[0].DC_AREA_NAME);
					getSuppId(rtn_data.results[0].DC_ID);
				}else{
					$("#dcName").html("<s:text name="eC.0037"/>");
					$("#dcTimeCde").html("");
					$("#dcTimeCdeName").html("");
					$("#dcDistAreaCde").html("");
					$("#dcDistAreaCdeName").html("");
				}

			}
		});
	}else{
		$("#dcName").html("");
	}
}
//Ajax供應商代碼
function getSuppId(date){
	
	var findDcSuppRelMst = $("#selectSuppId")[0];
	findDcSuppRelMst.length = 0;
	$.ajax({
		type: 'post',
		url:'<s:url value="/ajax/km/querySuppId.action" />',
		data:{params: '{date: "' + date + '"}'},
		success: function (rtnData) {
			if(rtnData.results.length > 0 && rtnData.results != "" && rtnData.results != null){
				var option = new Option("<s:text name="fix.00162"/>" , "");
				findDcSuppRelMst.options.add(option);
				for(var i=0;i<rtnData.results.length;i++){
					var option = new Option(rtnData.results[i].SUPP_ID + '-' + rtnData.results[i].SUPP_NAME, rtnData.results[i].DC_SUPP_OID);						
					findDcSuppRelMst.options.add(option);
				}
				
			}else{
				$("#suppId").html("<s:text name="eC.0037"/>");
			}
		}
	});
}
function sendSelected(){
	var str = $("#selectSuppId option:selected").text();
	var id = str.substring(0, str.indexOf("-"));
	var name = str.substring(str.indexOf("-")+1);
	$("#hiddSuppId").val(id);
	$("#hiddSuppName").val(name);
	
}
$(document).ready(function(){
	oTable = $('#tblGrid').initEditGrid({height:'250'});
	
	$("#dcId").bind("change", getDcName);
	$("#selectSuppId").bind("change", sendSelected);
});

</script>
</head>
<body>
<s:form id="frmSys01003K" method="post" theme="simple" action="%{progAction}" target="ifrConfirm">
<s:hidden name="labDcStaffMst.ver" />
 	<div class="progTitle"> 
		<s:include value="/WEB-INF/pages/include/include.EditTitle.jsp" />
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="staffId" />：</td>
				<td width="30%">
					<s:textfield id="staffId" name="labDcStaffMst.staffId" cssClass="enKey" readonly="progAction == 'updateSubmit'" maxlength="5" size="16" />
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="staffName" />：</td>
				<td width="30%">
					<s:textfield id="staffName" name="labDcStaffMst.staffName" maxlength="96" size="32" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="staffBirth" />：</td>
				<td width="30%">
					<s:textfield id="staffBirth" name="labDcStaffMst.staffBirth" maxlength="10" size="15" cssClass="inputDate" />
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="staffTitle" />：</td>
				<td width="30%">
					<s:select id="staffTitle" name="labDcStaffMst.staffTitle.optCde" headerValue="%{getText('fix.00162')}" headerKey="" 
					list="staffTitleList" listKey="optCde" listValue="optCde + '-' + optCdeNam" value="labDcStaffMst.staffTitle.optCde" />
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcId" />：</td>
				<td width="30%">
					<s:textfield id="dcId" name="labDcStaffMst.labDcSuppRel.labDcMst.dcId" maxlength="10" size="15" />
					<input type="image" id="imgCustId" class="imgPopUp" src="<s:url value="/image_icons/search.png"/>" />
					<s:label id="dcName" name="labDcStaffMst.labDcSuppRel.labDcMst.dcName" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
					<s:hidden id="hiddDcName" name="labDcStaffMst.labDcSuppRel.labDcMst.dcName" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="suppId" />：</td>
				<td width="30%">
					<select id="selectSuppId" name="labDcStaffMst.labDcSuppRel.dcSuppOid" selected="selected">
					</select>
					<s:hidden id="hiddSuppId" name="labDcStaffMst.labDcSuppRel.labSuppMst.suppId" value="%{labDcStaffMst.labDcSuppRel.labSuppMst.suppId}" />
					<s:hidden id="hiddSuppName" name="labDcStaffMst.labDcSuppRel.labSuppMst.suppName" value="%{labDcStaffMst.labDcSuppRel.labSuppMst.suppName}" />

				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcTimePeriod" />：</td>
				<td width="30%">
					<s:label id="dcTimeCde" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCde}" />&nbsp;-&nbsp;
					<s:label id="dcTimeCdeName" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCdeNam}" />
					<s:hidden id="hiddDcTimeCde" name="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCde" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
					<s:hidden id="hiddDcTimeCdeName" name="labDcStaffMst.labDcSuppRel.labDcMst.dcTimePerIod.optCdeNam" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcDistArea" />：</td>
				<td  width="30%">
					<s:label id="dcDistAreaCde" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcDistArea}" />&nbsp;-&nbsp;
					<s:label id="dcDistAreaCdeName" name="%{'dcDistAreaName'}" value="%{dcDistAreaName}" />
					<s:hidden id="hiddDcDistAreaCde" name="labDcStaffMst.labDcSuppRel.labDcMst.dcDistArea" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
					<s:hidden id="hiddDcDistAreaCdeName" name="dcDistAreaName" value="%{labDcStaffMst.labDcSuppRel.labDcMst.dcName}" />
				</td>
			</tr>
	</table>
    </div>
	<!-- 按鍵組合 --> 
	<s:include value="/WEB-INF/pages/include/include.EditButton.jsp" />
	<!-- 按鍵組合 -->
</s:form>
<iframe id="ifrConfirm" name="ifrConfirm" width="100%" height="768" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="display:none; border: 0px none"></iframe>
</body>
</html>