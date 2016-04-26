<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<!-- Header End -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-8 col-md-offset-2" role="main">
			<h1>Modify Userinfo</h1>
			<p>Input your old password if you want to change something.</p>
			<form action="doModify.htm" method="post">
				<input hidden="hidden" name="step" value="2">
				<div class="form-group" id="usernameGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span class="glyphicon glyphicon-user" aria-hidden="true" ></span></span>
						<input readonly class="form-control" type="text" placeholder="Username" aria-describedby="sizing-addon1" value="${username}" id="username">
					</div>
				</div>
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span></span>
						<input class="form-control" type ="password" placeholder="Password" aria-describedby="sizing-addon1" name="password">
					</div>
				</div>
				<div class="form-group" id="password1Group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon2"><span class="glyphicon glyphicon-lock" aria-hidden="true"></span></span>
						<input class="form-control" type ="password" placeholder="New Password" aria-describedby="sizing-addon1" name="password1" id="password1">
					</div>
				</div>
				<div class="form-group" id="password2Group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon3"><span class="glyphicon glyphicon-check" aria-hidden="true"></span></span>
						<input class="form-control" type ="password" placeholder="Confirm Password" aria-describedby="sizing-addon1" name="password2" id="password2">
					</div>
				</div>
				<div class="form-group" id="emailGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon4"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
						<input class="form-control" type ="text" placeholder="E-mail" aria-describedby="sizing-addon1" value="${USERMODEL.email}" name="email" id="email">
					</div>
				</div>
				<p>
					<button class="btn btn-warning btn-lg" type="submit">Submit</button>
				</p>
			</form>
		</div>
	</div>
</div>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
