<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header End -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<%@ include file="/WEB-INF/view/contest/contestHeader.jsp" %>
			<div class="row">
				<%int cnt = 0;%>
				<c:forEach items="${model.problemModels}" var="problemModel" varStatus="status">
					<div class="col-sm-6 col-md-4" style="cursor:pointer;" id="div<%=cnt %>" onclick="showProblem(${model.id},<%=cnt%>)">
						<div class="thumbnail">
							<div class="caption">
								<h3>Problem <%=(char)('A'+(cnt))%></h3>
								<p>${problemModel.title}</p>
								<%cnt++; %>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</div>

<script>
	//for(cnt=0;cnt<12;cnt++){console.debug($('#div'+cnt).offset().top);}
	function work(){
		max = $('#div0').height();
		for(cnt=1;cnt<<%=cnt%>;cnt++){
			if ($('#div'+cnt).offset().top == $('#div'+(cnt-1)).offset().top){
				//same line
				if ($('#div'+cnt).height() > max) max = $('#div'+cnt).height();
				$('#div'+cnt).height(max);
			} else {
				max = $('#div'+cnt).height();
			}
		}
	}
	
	function showProblem(cid,index){
		window.location.href="show.htm?cid="+cid+"&index="+index;
	}
	
</script>


<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
