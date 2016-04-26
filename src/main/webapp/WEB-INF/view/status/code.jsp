<!-- Header Begin -->
 
<%@ include file = "/templates/header.jsp" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!-- Header End -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Code #${model.id }</h1>
			<p>Status : <code>${model.status }</code></p>
			<p>Author : <code>${model.username}</code></p>
			<p>Language : <code>${model.language }</code></p>
			<c:if test="${model.cid !=null}">
				<p>Contest ID :<code>${model.cid }</code></p>
			</c:if>
			<c:if test="${model.runTime != null}">
				<p>Run Time : <code>${model.runTime }</code><code>MS</code></p>
			</c:if>
			<c:if test="${model.runMemory != null}">
				<p>Run Memory : <code>${model.runMemory }</code><code>B</code></p>
			</c:if>
			<p>Submit Time : <code><fmt:formatDate value="${model.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></code></p>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1">
			<c:if test="${model.compileInfo != null}">
				<div class="bs-callout bs-callout-danger">
					<h4>Compile Info</h4>
					<pre>
						${model.compileInfoHtml}
					</pre>
				</div>
			</c:if>
			<div class="bs-callout bs-callout-warning">
				<h4>Code</h4>
				<c:choose>
					<c:when test="${model.language == 'JAVA' }">
						<pre class="brush: java;">${model.codeHtml}</pre>
					</c:when>
					<c:otherwise>
						<pre class="brush: cpp;">${model.codeHtml}</pre>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>
<script>
</script> 



<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
