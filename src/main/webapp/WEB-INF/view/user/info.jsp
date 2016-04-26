<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Info</h1>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1">
			<c:choose>
				<c:when test="${errmsg != null}">
				</c:when>
				<c:otherwise>
						
					<table class="table table-hover">
						<tr>
							<th>Username</th>
							<td>${userinfoModel.username}</td>
						</tr>
						<tr>
							<th>CreateTime</th>
							<td>${userinfoModel.createTime}
						</tr>
						<tr>
							<th>ModifyTime</th>
							<td>${userinfoModel.modifyTime }</td>
						</tr>
						<c:choose>
							<c:when test="${owner}">
								<tr>
									<th>E-mail</th>
									<td>${userinfoModel.email }</td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
