<!-- Header Begin -->
<jsp:include flush="true" page="/templates/header.jsp"></jsp:include>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<!-- Header End -->

<div align="center">
	<table class="zebra">
		<tr>
			<td colspan="2"> ${errmsg} </td>
		</tr>
		<tr>
			<th width="10%">Id</th>
			<td>${node.id}</td>
		</tr>
		<tr>
			<th> time </th>
			<td> ${node.time} </td>
		</tr>
		<tr>
			<th> username </th>
			<td> <c:out value="${node.username}" escapeXml="true"></c:out> </td>
		</tr>
		<tr>
			<th> topic </th>
			<td> <c:out value="${node.topic}" escapeXml="true"></c:out> </td>
		</tr>
		<tr>
			<th> type </th>
			<td> <c:out value="${node.type}" escapeXml="true"></c:out> </td>
		</tr>
		<tr>
			<th> info </th>
			<td align="left"> <c:out value="${node.info}" escapeXml="true"></c:out> </td>
		</tr>
		<tr>
			<th> detail </th>
			<td align="left"> <c:out value="${node.detail}" escapeXml="true"></c:out> </td>
		</tr>
	</table>
</div>




<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
