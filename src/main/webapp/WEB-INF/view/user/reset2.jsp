<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<!-- Header End -->
<div class="jumbotron">
	<div class="container">
		<div class="col-md-8 col-md-offset-2" role="main">
			<h1>Reset Password</h1>
			<p>You can get your token in your E-mail Now.</p>
			<form action="reset.htm" method="post">
				<input hidden="hidden" name="step" value="2">
				<div class="form-group" id="usernameGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span class="glyphicon glyphicon-user" aria-hidden="true" ></span></span>
						<input readonly class="form-control" type="text" placeholder="Username" aria-describedby="sizing-addon1" value="${model.username}" id="username">
					</div>
				</div>
				<div class="form-group" id="emailGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon4"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
						<input readonly class="form-control" type ="text" placeholder="E-mail" aria-describedby="sizing-addon1" value="${model.email}" id="email">
					</div>
				</div>
				<div class="form-group" id="tokenGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon4"><span class="glyphicon glyphicon-barcode" aria-hidden="true"></span></span>
						<input class="form-control" type ="text" placeholder="Token in E-mail" aria-describedby="sizing-addon1" name="token">
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
