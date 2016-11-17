<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>Test Execution Report</title>
		<style>
			.header {
				 background-color: AliceBlue;
				 cursor:pointer;
			}
			
			.stepheader {
				 display:none;
				 cursor:pointer;
			}
			
			.stepdetails {
				 display:none;
			}
			
			.spanHeader{
				padding-left: 5px;
				padding-right: 5px;
			}
		
			.totalFooter{
				font-weight: bold;
				text-align: right;
			}
		</style>
		<script src="https://code.jquery.com/jquery-1.10.2.js"></script>	
	</head>
<body>
<center><h1>Test Execution Report</h1>
<#assign currentDate=.now>
    <p class="byline">Generated on: ${currentDate?iso_local}</p>
</center>
<#if  (nodes?size) != 0>
	<table border="1" width="100%" align="center">
		<tr bgcolor ="E0E0E0">
			<td align="center" style="width:30%"> step name</td>
			<td align="center" style="width:50%">details</td>
			<td align="center" style="width:10%">status</td>
			<td align="center" style="width:10%">execution time (ms)</td>
		</tr>
		<#assign index=0>
		<#list nodes as child>
			<#assign index++> 
				<tr  class="header">
					<td colspan="2"  style="font-weight: bold;">
						<span class="spanHeader"></span>
						 - ${child.threadName}
					</td>
					<#if  (child.message) == "Ok">
						<td  style="color: Chartreuse; text-align: center;font-weight: bold;">
					<#else>
						<td  style="color: Red; text-align: center;font-weight: bold;">
					</#if>	
					 ${child.message}
						</td>
					<td style="text-align: center;font-weight: bold; ">${child.executionTime}</td>
				</tr>
				
				<#if  (child.children?size) == 0>
					<tr id="amain${index}" class="stepheader">
						<td colspan="4" ><span style="padding-left: 35px">No data</td>
					</tr>
				<#else>
					<#assign childindex=0>
					<#list child.children as log>
						<#assign childindex++> 
						<tr id="amain${index}" class="stepheader" >
							<td colspan="3">
								<span style="padding-left: 35px;padding-right: 5px;"></span>
							- ${log.stepName}
							</td>
							<#if  (log.level) != "ERROR">
								<td style="color: Chartreuse; text-align: center;font-weight: bold;">Ok
							<#else>
								<td style="font-weight: bold;color: Red; text-align: center">Failed
							</#if>
							</td>
						</tr>
						
						<#if (log.message?length) != 0 >
							<tr  id="a${index}${childindex}" class="stepdetails" >
								<td style="text-align: right;">Details:</td>
								<td colspan="3" >${log.message}</td>
							</tr>
						</#if>
						<tr  id="a${index}${childindex}" class="stepdetails">
							<td style="text-align: right;">Parameters:</td>
							<td colspan="3" >${log.parametersList}</td>
						</tr>
					</#list> 
				</#if>
		</#list>
		<tr class="totalFooter">
		<td colspan="3" >
			 Total time:
		</td>
		<td style="text-align: center;font-weight: bold; ">${total}</td>
	</tr>
	</table>
<#else>
    No results...
</#if>

<script>
$('.header').click(function(){
		if($(this).nextUntil('tr.header,tr.totalFooter','.stepheader').not(":hidden").length == 0){
		///all are hidden
			$(this).nextUntil('tr.header,tr.totalFooter','.stepheader').show();
		} else{
		///hide all
			$(this).nextUntil('tr.header,tr.totalFooter').hide();
		}
	});
$('.stepheader').click(function(){
//		window.alert($(this).nextUntil('tr.stepheader,tr.header,tr.totalFooter').not(":hidden").length);
		
		if($(this).nextUntil('tr.stepheader,tr.header,tr.totalFooter').not(":hidden").length == 0){
		///all are hidden
			$(this).nextUntil('tr.stepheader,tr.header,tr.totalFooter').show();
		} else{
		///hide all
			$(this).nextUntil('tr.stepheader,tr.header,tr.totalFooter').hide();
		}
});
</script>
</body>
</html>