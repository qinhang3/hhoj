<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="userTable">
<c:forEach var="problem" items="${model.problemModels}" varStatus="var">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h4 class="panel-title">Problem ${fn:substring(ENGINDEX,var.index,var.index+1)}</h4>
		</div>
		<c:choose>
			<c:when test="${statusModel.statusList[problem.id] != null}">
				<table class="table table-hover">
					<thead>
						<tr class="active">
							<th>RunId</th>
							<th>SubmitTime</th>
							<th class="hidden-xs hidden-sm">language</th>
							<th class="hidden-xs hidden-sm">Time</th>
							<th class="hidden-xs hidden-sm">Memory</th>
							<th>Status</th>
						</tr>
					</thead>
					<c:forEach var="statusModel" items="${statusModel.statusList[problem.id]}">
						<tr onclick="showCode(${statusModel.id})"
							<c:choose>
								<c:when test="${statusModel.status=='Pending' || statusModel.status=='Repending'}">
									class="active" style="cursor:pointer;"
								</c:when>
								<c:when test="${statusModel.status=='Accepted'}">
									class="success" style="cursor:pointer;color:green"
								</c:when>
								<c:when test="${statusModel.status=='Wrong Answer'}">
									class="danger" style="cursor:pointer;color:red"
								</c:when>
								<c:otherwise>
									class="warning" style="cursor:pointer;"
								</c:otherwise>
							</c:choose>
						>
							<td>${statusModel.id}</td>
							<td>
								<span class="hidden-xs hidden-sm">
									<fmt:formatDate value="${statusModel.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</span>
								<span class="hidden-md hidden-lg">
									<fmt:formatDate value="${statusModel.createTime}" pattern="MM-dd HH:mm" />
								</span>
							</td>
							<td class="hidden-xs hidden-sm">${statusModel.language }</td>
							<td class="hidden-xs hidden-sm">${statusModel.runTime }</td>
							<td class="hidden-xs hidden-sm">${statusModel.runMemory }</td>
							<td>
								<c:if test="${statusModel.statusShort == 'PD' || statusModel.statusShort == 'RJ'}">
									<img src="http://gdutcode-hhoj.stor.sinaapp.com/pic_557ae8a6468de.gif">
								</c:if>
								${statusModel.status }
							</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:otherwise>
				<div class="panel-body">
					<p>No Records~</p>
				</div>
			</c:otherwise>
		</c:choose>
	</div>				
</c:forEach>
</div>
