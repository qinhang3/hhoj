<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Problem</h1>

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
						<th>Time</th>
						<th>Memory</th>
						<th>AC/Try</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="model">
						<tr onclick="showPid(${model.id})" style="cursor:pointer;"
							<c:if test="${acPidList[model.id]}">
								class="success"
							</c:if>
						>
							<td>${model.id }</td>
							<td>${model.title }</td>
							<td>${model.timeLimit }</td>
							<td>${model.memLimit }</td>
							<td>${model.acCnt}/${model.tryCnt}</td>
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
