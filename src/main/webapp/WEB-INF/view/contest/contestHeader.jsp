<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<h1>Contest</h1>
<c:choose>
	<c:when test="${notStart}">
		<h2><small>Contest Not Start.Please Wait~</small></h2>
	</c:when>
	<c:otherwise>
		<ul class="nav nav-tabs nav-justified">
			<li role="presentation" id="index"><a href="${CONTEXTPATH}/contest/index.htm?cid=${cid}">Index</a></li>
			<li class="dropdown" id="problems" onmouseover="$('#problems').addClass('open')" onmouseout="$('#problems').removeClass('open')">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false">
					Problems
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu" role="menu" aria-labelledby="drop1">
					<li role="presentation"><a role="menuitem" tabindex="-1" href="${CONTEXTPATH}/contest/problems.htm?cid=${cid}">All</a></li>
					<li role="presentation" class="divider"></li>
					<c:forEach items="${model.problemModels}" var="problemModel" varStatus="status">
						<li role="presentation">
							<a role="menuitem" tabindex="-1" href="${CONTEXTPATH}/contest/show.htm?cid=${cid}&index=${status.index}">
								Problem ${fn:substring(ENGINDEX,status.index,status.index+1)}
							</a>
						</li>
					</c:forEach>
				</ul>
			</li>
			<li role="presentation" id="show"><a href="${CONTEXTPATH}/contest/show.htm?cid=${cid}&index=0">Show</a></li>
			<c:if test="${USERNAME != null }">
				<li role="presentation" id="status"><a href="${CONTEXTPATH}/status/contest.htm?cid=${cid}">Status</a></li>
			</c:if>
			<li role="presentation" id="standing"><a href="${CONTEXTPATH}/contest/standing.htm?cid=${cid}">Standing</a></li>
		</ul>
		<div class="progress">
			<div class="progress-bar progress-bar-striped active"
				role="progressbar" aria-valuenow="45" aria-valuemin="0"
				aria-valuemax="100" style="width: ${model.timePercent}%">
				<span class="sr-only"></span>
			</div>
		</div>
		<script>
			$(document).ready(function(){
				$('#'+$('#methodName').val()).attr('class','active');
				if ($('#servletName').val() == 'status'){
					$('#status').attr('class','active');
				}
			}); 
			
		</script>		
	</c:otherwise>
</c:choose>