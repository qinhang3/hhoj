<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:forEach var="problem" items="${list}" varStatus="var">
	<tr <c:if test="${problem.timeLimit == null }"> class="danger" style="color:red"</c:if>>
		<td>${fn:substring(ENGINDEX,var.index,var.index+1)}</td> 
		<td>${problem.id }</td>
		<td>${problem.title}</td>
		<td>${problem.timeLimit }</td>
		<td>${problem.memLimit }</td>
		<td>
			<a class="btn btn-info btn-xs" href="editProblem.htm?pid=${problem.id}" target="_blank">Edit</a>
			<c:choose>
				<c:when test="${var.index == 0 }">
					<a class="btn btn-default btn-xs" onclick="">Move Up</a>
				</c:when>
				<c:otherwise>
					<a class="btn btn-warning btn-xs" onclick="moveUpProblem(${var.index})">Move Up</a>
				</c:otherwise>
			</c:choose>
			<c:if test="${cid != null}">
				<a class="btn btn-danger btn-xs" onclick="rejudge(${problem.id})">Rejudge</a>
			</c:if>
			<a class="btn btn-danger btn-xs" onclick="deleteProblem(${var.index})">Delete</a>
		</td>
	</tr>
</c:forEach>
	<tr hidden>
		<td>
			<input hidden value="${pids}" id="problems" name="problems">
		</td>
	</tr>