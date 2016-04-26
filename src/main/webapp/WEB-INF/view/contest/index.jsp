<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header End -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<%@ include file="/WEB-INF/view/contest/contestHeader.jsp" %>
			
			<table class="table table-hover">
				<tr>
					<th>Title</th>
					<td>${model.title }</td>
				</tr>
				<tr>
					<th>Contest ID</th>
					<td>${model.id }</td>
				</tr>
				<tr>
					<th>Start Time</th>
					<td><fmt:formatDate value="${model.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th>End Time</th>
					<td><fmt:formatDate value="${model.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
				<tr>
					<th>Status</th>
					<td>${model.statusNow}</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
