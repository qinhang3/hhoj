<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!-- Header End -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<%@ include file="/WEB-INF/view/contest/contestHeader.jsp" %>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" id="tables">
			
		</div>
	</div>
</div>

<script type="text/javascript" src="${CONTEXTPATH}/js/status.js"></script>
<script>
	$(document).ready(function(){
		updateContestStatus()
		setInterval(updateContestStatus, 5000);  
	});  
	function showCode(rid){
		url = 'code.htm?rid='+rid;
		window.open(url);
	}
</script> 



<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
