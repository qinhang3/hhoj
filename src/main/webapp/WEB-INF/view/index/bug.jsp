<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<!-- Header End -->
<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Bug<small>Thanks for help me to find bug:)</small></h1>
			<form method="post" action="bugSubmit.htm">
				<div class="form-group">
					<label>Url</label>
					<input name="url" value="${url}" readonly class="form-control">
				</div>
				<div class="form-group">
					<label>Your Name</label>
					<input name="username" value="${USERNAME}<c:if test="${USERNAME==null}">Guest</c:if>" readonly class="form-control">
				</div>
				<div class="form-group">
					<label>Something About This bug</label>
					<textarea name="description" class="form-control" style="height:300px"></textarea>
				</div>
				<p>
					<input type="submit" class="btn btn-warning btn-lg" value="Submit">
				</p>
			</form>
		</div>
	</div>
</div>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
