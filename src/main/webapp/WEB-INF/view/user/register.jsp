<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->


<div class="modal fade" id="checkcodeModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Last Step</h4>
			</div>
			<div class="modal-body">
				<div class="form-group">
					<img src="" class="img-thumbnail center-block" id="checkpic" onclick="changePic()">
				</div>
				
				<div class="form-group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1"><span
							class="glyphicon glyphicon-barcode" aria-hidden="true"></span></span> <input
							class="form-control" type="text" placeholder="Check Code"
							aria-describedby="sizing-addon1" name="checkcode" id="checkcode">
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary" onclick="checkcheckcodeModal()">Register</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-8 col-md-offset-2" role="main">
			<h1>Register</h1>
			<p>Check your information when the input filed turns <font color="red">RED</font>.</p>
			<form id="register" method="post" action="doRegister.htm">
				<input type="hidden" value="${Checkcodekey_register}" name="topic">
				<div class="form-group" id="usernameGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon1">
							<span class="glyphicon glyphicon-user" aria-hidden="true" >
							</span>
						</span>
						<input class="form-control" type="text" placeholder="Username" aria-describedby="sizing-addon1" name="username" value="${form.username}" id="username" onchange="OnInput('username',event)">
					</div>
				</div>
				<div class="form-group" id="password1Group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon2">
							<span class="glyphicon glyphicon-lock" aria-hidden="true">
							</span>
						</span>
						<input class="form-control" type ="password" placeholder="Password" aria-describedby="sizing-addon1" name="password1" value="${form.password1}" id="password1" oninput="OnInput('password1',event)">
					</div>
				</div>
				<div class="form-group" id="password2Group">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon3"><span class="glyphicon glyphicon-check" aria-hidden="true"></span></span>
						<input class="form-control" type ="password" placeholder="Confirm Password" aria-describedby="sizing-addon1" name="password2" value="${form.password2}" oninput="OnInput('password2',event)" id="password2">
					</div>
				</div>
				<div class="form-group" id="emailGroup">
					<div class="input-group input-group-lg">
						<span class="input-group-addon" id="sizing-addon4"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span></span>
						<input class="form-control" type ="text" placeholder="E-mail" aria-describedby="sizing-addon1" name="email" value="${form.email}" id="email" onchange="OnInput('email',event)">
					</div>
				</div>
				<input type="text" name="code" value="" hidden="hidden" id="code">
				
				<p>
					<button class="btn btn-success btn-lg" type="button" id="registerButton" onclick="check()">Register</button>
				</p>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="../js/register.js"></script>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->