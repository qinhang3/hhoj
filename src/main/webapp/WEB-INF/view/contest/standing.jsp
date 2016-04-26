<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Header End -->

<div class="jumbotron" style="padding-bottom: 0px;margin-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<%@ include file="/WEB-INF/view/contest/contestHeader.jsp" %>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-12 table-responsive" role="main">
			<table class="table table-hover table-bordered text-center">
				<thead>
					<tr class="text-center">
						<th>Rank</th>
						<th>Username</th>
						<th>Solve</th>
						<th>Time</th>
						<c:forEach var="problem" items="${model.problemModels}" varStatus="var">
							<th> ${fn:substring(ENGINDEX,var.index,var.index+1)}</th>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="user" items="${standingModel.rank}" varStatus="status">
						<tr>
							<td>
								${status.index+1 }
							</td>
							<td>
								${user.username }
							</td>
							<td>
								<c:choose>
									<c:when test="${fn:length(model.problemModels) == user.acCnt}">
										<a class="btn btn-warning btn-xs">AK</a>
									</c:when>
									<c:otherwise>
										${user.acCnt}
									</c:otherwise>
								</c:choose>
								
							</td>
							<td>${user.allTime }</td>
							<c:forEach var="problem" items="${model.problemModels }">
								<c:choose>
									<c:when test="${standingModel.fbTime[problem.id]!=null && standingModel.fbTime[problem.id] == user.info[problem.id].acTime}">
										<td class="warning">
											${user.info[problem.id].printStr}
										</td>	
									</c:when>
									<c:otherwise>
										<td class="${user.info[problem.id].color}">
											${user.info[problem.id].printStr}											
										</td>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->