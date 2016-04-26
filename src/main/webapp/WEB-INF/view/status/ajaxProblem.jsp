<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<c:forEach var="model" items="${list}">
	<tr
		<c:choose>
			<c:when test="${model.status=='Pending' || model.status=='Repending'}">
				class="active"
			</c:when>
			<c:when test="${model.status=='Accepted'}">
				class="success" style="color:green"
			</c:when>
			<c:when test="${model.status=='Wrong Answer'}">
				class="danger" style="color:red"
			</c:when>
			<c:otherwise>
				class="warning"
			</c:otherwise>
		</c:choose>
	>
		<td>${model.id }</td>
		<td onclick="showPid(${model.pid})" style="cursor:pointer;">${model.pid}</td>
		<c:choose>
			<c:when test="${fn:length(model.username)>16}">
				<td title="${model.username}">${fn:substring(model.username,0,13)}...</td>
			</c:when>
			<c:otherwise>
				<td>${model.username }</td>					
			</c:otherwise>
		</c:choose>
		<td <c:if test="${model.username==USERNAME || acPidList[model.pid] || INadmin}">onclick='showCode(${model.id})' style="cursor:pointer;"</c:if>>
			<c:if test="${model.statusShort == 'PD' || model.statusShort == 'RJ'}">
				<img src="http://gdutcode-hhoj.stor.sinaapp.com/pic_557ae8a6468de.gif">
			</c:if>
			<span class="hidden-xs">${model.status}</span>
			<span class="hidden-sm hidden-md hidden-lg">${model.statusShort}</span>
		</td>
		<td class="hidden-xs hidden-sm">${model.language }</td>
		<td class="hidden-xs hidden-sm">${model.runTime }</td>
		<td class="hidden-xs hidden-sm">${model.runMemory }</td>
		<td class="hidden-xs hidden-sm"><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	</tr>
</c:forEach>