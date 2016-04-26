<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header End -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Submit Too Fast..</h1>
			<p>Please calm down.Retry in <span id="second"></span> second.</p>
			<form id="form" method="post">
				<input hidden="true" value="${model.language}" name="language">
				<textarea hidden="true" name="code">${model.code}</textarea>
				<input hidden="true" value="${model.pid}" name="pid">
				<c:if test="${model.cid != null}">
					<input hidden="true" value="${model.cid}" name="cid">
					<input hidden="true" value="${model.index}" name="index">
				</c:if>
			</form>
		</div>
	</div>
</div>

<script>
	$("document").ready(function(){
		second = 5;
		calc();
		setInterval(calc, 1000);
	});
	function calc(){
		if(second == 0){
			$('#form').submit();
			return;
		}
		$('#second').html(second);
		second = second-1;
	}
</script>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
