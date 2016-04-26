<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<!-- Header End -->
<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>No Permission</h1>
			<p>This page will show when we think you visit something secret</p>
			<p>May be your login is time out </p>
			<p>
				<a class="btn btn-warning btn-lg" href="javascript:history.go(-1)">Go Back</a>
				<a class="btn btn-success btn-lg" href="${CONTEXTPATH}/user/login.htm?url=${url}">Login</a>
			</p>
		</div>
	</div>
</div>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
