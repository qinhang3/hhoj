<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!-- Header End -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Contest</h1>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1">
			<table class="table table-hover">
				<thead>
					<tr>
						<th width="5%">#</th>
						<th>Title</th>
						<th>Start Time</th>
						<th>End Time</th>
						<th>Public</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="model">
						<tr onclick="showCid(${model.id})" 
							<c:choose>
								<c:when test="${model.statusNow == 'Not Start'}">
									class = 'info' style="cursor:pointer;"
								</c:when>
								<c:when test="${model.statusNow == 'Running'}">
									class = "danger" style="cursor:pointer;color:red"
								</c:when>
								<c:otherwise>
									style="cursor:pointer"
								</c:otherwise>
							</c:choose>
						>
							<td>${model.id }</td>
							<td>${model.title }</td>
							<td><fmt:formatDate value="${model.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${model.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>
								<c:choose>
									<c:when test="${model.isPublic == 0}">
										<a class="btn btn-danger btn-xs" style="width:57px">Private</a>
									</c:when>
									<c:when test="${model.isPublic == 1}">
									<a class="btn btn-success btn-xs" style="width:57px">Public</a>
									</c:when>
									<c:when test="${model.isPublic == 2}">
										<a class="btn btn-warning btn-xs" style="width:57px">Register</a>
									</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<nav>
				<ul class="pager">
					<c:if test="${page!=0}">
				  		<li class="previous"><a href="list.htm?page=${page-1}"><span aria-hidden="true">&larr;</span></a></li>
					</c:if>
			    	<li class="next"><a href="list.htm?page=${page+1}"><span aria-hidden="true">&rarr;</span></a></li>
				</ul>
			</nav>
		</div>
	</div>
</div>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
