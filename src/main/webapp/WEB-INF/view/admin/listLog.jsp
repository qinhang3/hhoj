<!-- Header Begin -->
<jsp:include flush="true" page="/templates/header.jsp"></jsp:include> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Header End -->
<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Admin</h1>
		</div>
		<div class="col-md-12" role="main">
			<div class="row">
				<%@ include file = '/WEB-INF/view/admin/adminMenu.jsp' %>
				<div class="col-md-10">
					<form class="form-inline" id="form">
						<div class="form-group">
							<label class="sr-only" for="topic">Topic</label>
							<input id="topic" type="text" class="form-control" placeholder="Topic" name="topic" value="${topic}">
						</div>
						<div class="form-group">
							<label class="sr-only" for="type">username</label>
							<input id="type" type="text" class="form-control" placeholder="Type" name="type" value="${type}">
						</div>
						<input hidden="true" id="pageId" name="page" value="${page}">
						<button type="submit" class="btn btn-default">Search</button>
					</form>
				
					<table class="table table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>Time</th>
								<th>Type</th>
								<th>Topic</th>
								<th>Info</th>
								<th>Username</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="model">
								<tr>
									<td>${model.id}</td>
									<td><fmt:formatDate value="${model.time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>${model.type}</td>
									<td>${model.topic}</td>
									<td>${fn:substring(model.info,0,60)}</td>
									<td>${model.username }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<nav>
						<ul class="pager">
							<c:if test="${page!=0}">					
					  			<li class="previous"><a href="#" onclick="changePage(${page-1})"><span aria-hidden="true">&larr;</span></a></li>
							</c:if>
					    	<li class="next"><a href="#" onclick="changePage(${page+1})"><span aria-hidden="true">&rarr;</span></a></li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	function changePage(page){
		$('#pageId').val(page);
		$('form').submit();
	}
</script>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
