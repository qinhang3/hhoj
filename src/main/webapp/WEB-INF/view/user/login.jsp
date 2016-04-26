<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header End -->

<div class="jumbotron" >
	<div class="container">
		<div class="col-md-8 col-md-offset-2" role="main">
			<h1>Login</h1>
			<p>Input your Username & Password and get started.</p>
			<form id="login" method="post" action="doLogin.htm">
				<input name="url" hidden="true" value="${url }">
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span class="glyphicon glyphicon-user" aria-hidden="true"></span></span>
						<input type="text" class="form-control" placeholder="Username" aria-describedby="sizing-addon1" name="username">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
						<input class="form-control" type ="password" placeholder="Password" aria-describedby="sizing-addon1" name="password">
					</div>
				</div>
				<p>
					<button class="btn btn-success btn-lg">Submit</button>
					<a class="btn btn-warning btn-lg" type="button" href="reset.htm">Forget Password</a>
				</p>
			</form>
		</div>
	</div>
</div>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
