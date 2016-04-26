<!-- Header Begin -->
<%@ include file = '/templates/header.jsp' %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
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
					<table class="table table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>Title</th>
								<th>Start Time</th>
								<th>End Time</th>
								<th>Tag  <a class="btn btn-primary btn-xs" href="addContest.htm" target="_blank">Add Contest</a></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="contest" items="${list}">
								<tr>
									<td>${contest.id}</td>
									<td>${contest.title}</td>
									<td><fmt:formatDate value="${contest.startTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td><fmt:formatDate value="${contest.endTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<c:choose>
											<c:when test="${contest.isPublic==0 }">
												<div class="btn-group">
													<a class="btn btn-danger btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:68px">
														Private
														<span class="caret"></span>
													</a>	
													<ul class="dropdown-menu" role="menu">
														<li><a onclick="setPublic(${contest.id},1)">Public</a></li>
														<li><a onclick="setPublic(${contest.id},2)">Register</a></li>
														<li><a onclick="setPublic(${contest.id},3)">Hidden</a></li>
													</ul>
												</div>
											</c:when>
											<c:when test="${contest.isPublic==1 }">
												<div class="btn-group">
													<a class="btn btn-success btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:68px">
														Public
														<span class="caret"></span>
													</a>	
													<ul class="dropdown-menu" role="menu">
														<li><a onclick="setPublic(${contest.id},0)">Private</a></li>
														<li><a onclick="setPublic(${contest.id},2)">Register</a></li>
														<li><a onclick="setPublic(${contest.id},3)">Hidden</a></li>
													</ul>
												</div>
											</c:when>
											<c:when test="${contest.isPublic==2}">
												<div class="btn-group">
													<a class="btn btn-warning btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:68px">
														Register
														<span class="caret"></span>
													</a>
													<ul class="dropdown-menu" role="menu">
														<li><a onclick="setPublic(${contest.id},0)">Private</a></li>
														<li><a onclick="setPublic(${contest.id},1)">Public</a></li>
														<li><a onclick="setPublic(${contest.id},3)">Hidden</a></li>
													</ul>
												</div>									
											</c:when>
											<c:when test="${contest.isPublic==3 }">
												<div class="btn-group">
													<a class="btn btn-default btn-xs dropdown-toggle" data-toggle="dropdown" aria-expanded="false" style="width:68px">Hidden<span class="caret"></span></a>	
													<ul class="dropdown-menu" role="menu">
														<li><a onclick="setPublic(${contest.id},0)">Private</a></li>
														<li><a onclick="setPublic(${contest.id},1)">Public</a></li>
														<li><a onclick="setPublic(${contest.id},2)">Register</a></li>
													</ul>
												</div>
											</c:when>
										</c:choose>
										<a class="btn btn-info btn-xs" href="editContest.htm?cid=${contest.id}" target="_blank">Edit</a>
										<a class="btn btn-warning btn-xs" href="${CONTEXTPATH}/contest/index.htm?cid=${contest.id}" target="_black">Go</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function setPublic(cid,isPub){
		var msg = 'Set Contest '+cid+' ';
		if (isPub==0){
			msg = msg+'PRIVATE ?';
		} else if (isPub == 1){
			msg = msg+'PUBLIC ?';
		} else if (isPub == 2){
			msg = msg+'REGISTER ?';
		} else if (isPub ==3){
			msg = msg+'HIDDEN ?'
		} else {
			return;
		}
		var sure = window.confirm(msg);
		if (sure){
			$.post("publicContest.htm",{"cid":cid,"isPublic":isPub},function(data){
				window.location.reload();
			});
		}
	}
	
	function hiddenContest(cid,isHidden){
		var sure = window.confirm('Hidden Contest '+cid+' ?');
		if (sure){
			$.post("hiddenContest.htm",{"cid":cid,"isHidden":isHidden},function(json){
				window.location.reload();
			});
		}
	}
</script>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
