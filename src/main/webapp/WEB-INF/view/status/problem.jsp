<!-- Header Begin -->
 
<%@ include file = "/templates/header.jsp" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Status</h1>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1">
			<form class="form-inline" id="form">
				<div class="form-group">
					<label class="sr-only" for="pid">problem id</label>
					<input type="text" class="form-control" placeholder="Problem Id" name="pid" value="${pid}">
				</div>
				<div class="form-group">
					<label class="sr-only" for="username">username</label>
					<input type="text" class="form-control" placeholder="User Name" name="username" value="${username}">
				</div>
				<div class="form-group">
					<select class="form-control" name="status">
						<option value="">Status</option>
						<c:forEach var="item" items="${allStatus}">
							<option value="${item}"
								<c:if test="${item == status}">
									selected
								</c:if>
							>${item}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<select class="form-control" name="language">
						<option value="">Language</option>
						<c:forEach var="item" items="${allLanguage}">
							<option value="${item}"
								<c:if test="${item == language}">
									selected
								</c:if>
							>${item}</option>
						</c:forEach>
					</select>
				</div>
				<input hidden="true" id="pageId" name="page" value="${page}">
				<button type="submit" class="btn btn-default">Search</button>
			</form>
			<table class="table table-hover" id="status">
				<thead>
					<tr>
						<th>RunId</th>
						<th>Pid</th>
						<th>Username</th>
						<th>Status</th>
						<th class="hidden-xs hidden-sm">language</th>
						<th class="hidden-xs hidden-sm">Time</th>
						<th class="hidden-xs hidden-sm">Memory</th>
						<th class="hidden-xs hidden-sm">SubmitTime</th>
					</tr>
				</thead>
				<tbody id="tbody">
					
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

<script type="text/javascript" src="${CONTEXTPATH}/js/status.js"></script>
<script>
	$(document).ready(function(){
		updateProblemStatus();
		setInterval(updateProblemStatus, 5000);  
	});
	function showCode(rid){
		url = 'code.htm?rid='+rid;
		window.open(url);
	}
</script> 



<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
