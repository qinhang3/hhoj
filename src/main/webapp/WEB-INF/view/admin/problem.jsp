<!-- Header Begin -->
<%@ include file = '/templates/header.jsp' %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
								<th>Tag  <a class="btn btn-primary btn-xs" href="addProblem.htm" target="_blank">Add Problem</a></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="problem" items="${list}">
								<tr>
									<td>${problem.id}</td>
									<td>${problem.title}</td>
									<td>
										<c:choose>
											<c:when test="${problem.isPublic==1}">
												<a class="btn btn-success btn-xs" onclick="setPublic(${problem.id},0)" style="width: 49.2px;">Public</a>											
											</c:when>
											<c:otherwise>
												<a class="btn btn-warning btn-xs" onclick="setPublic(${problem.id},1)" style="width: 49.2px;">Private</a>
											</c:otherwise>
										</c:choose>
										<a class="btn btn-info btn-xs" href="editProblem.htm?pid=${problem.id}" target="_blank">Edit</a>
										<a class="btn btn-warning btn-xs" href="javascript:rejudge(${problem.id})">Rejudge</a>
										<a class="btn btn-success btn-xs" href="${CONTEXTPATH}/problem/show.htm?pid=${problem.id}" target="_blank">Go</a>
										<a class="btn btn-danger btn-xs" onclick="deleteProblem(${problem.id})">Delete</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<nav>
						<ul class="pager">
							<c:if test="${page!=0}">
						  		<li class="previous"><a href="problem.htm?page=${page-1}"><span aria-hidden="true">&larr;</span></a></li>
							</c:if>
					    	<li class="next"><a href="problem.htm?page=${page+1}"><span aria-hidden="true">&rarr;</span></a></li>
						</ul>
					</nav>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function setPublic(pid,isPub){
		var msg = 'Set Problem '+pid+' ';
		if (isPub==0){
			msg = msg+'PRIVATE ?';
		} else {
			msg = msg+'PUBLIC ?';
		}
		var sure = window.confirm(msg);
		if (sure){
			$.post("publicProblem.htm",{"pid":pid,"isPublic":isPub},function(data){
				window.location.reload();
			});
		}
	}
	
	function deleteProblem(pid){
		var sure = window.confirm('Delete Problem '+pid+' ?');
		if (sure){
			$.post("deleteProblem.htm",{"pid":pid},function(json){
				window.location.reload();
			});
		}
	}
	
	function rejudge(pid){
		var sure = window.confirm('Rejudge all solution of  Problem '+pid+' ?');
		if (sure){
			$.post("rejudge.htm",{"pid":pid,"includeAc":"true"},function(data){
				if (data.success) {
					alert("Rejudge success , modify solution "+data.value);
				} else {
					alsert("Rejudge error ,  because of "+data.msg);
				}
			})
		}
	}
</script>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
