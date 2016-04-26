<!-- Header Begin -->
<jsp:include flush="true" page="/templates/header.jsp"></jsp:include> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->

<table class="table table-hover">
	<tr>
		<th>id</th>
		<th>usename</th>
		<th>description</th>
		<th>url</th>
		<th>Ip</th>
	</tr>
	<c:forEach items="${list}" var="model">
		<tr>
			<td>${model.id }</td>
			<td>${model.username}</td>
			<td>${model.description }</td>
			<td>${model.url }</td>
			<td>${model.ipAddress }</td>
		</tr>
	</c:forEach>
</table>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->