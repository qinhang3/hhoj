<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->

<div class="modal fade" id="submitModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Submit</h4>
			</div>
			<form method="post" action="doSubmit.htm" id="submitForm">
				<div class="modal-body">
						<div class="form-group">
							<label for="selectLanguage">Language</label>
							<select id="language" class="form-control" name="language">
								<c:forEach var="item" items="${allLanguage}">
									<option value="${item}"
										<c:if test="${item == language}">
											selected
										</c:if>
									>${item}</option>
								</c:forEach>
							</select>
						</div>
						<!-- 
						<div class="form-group">
							<label for="codeFile">File input</label> 
							<input type="file" id="codeFile" name="codeFile" onchange="console.log('!!!')">
							<p class="help-block">Select your code file OR paste</p>
						</div>
						 -->
						<div class="form-group">
							<label for="codetextarea">Code</label>
							<textarea class="form-control" id="codetextarea" name="code" style="height: 410px" placeholder="Compile Command:
C		: gcc -o a.out code.c -static -w -lm -std=c99 -O2 -DONLINE_JUDGE
CPP		: g++ -o a.out code.cpp -static -w -lm -O2 -std=c++11 -DONLINE_JUDGE
JAVA	: javac Main.java -d runDir"></textarea>
						</div>
						<input hidden="hidden" name="index" value="${pid}">
						<input hidden="hidden" name="pid" value="${problemModel.id }">
						<input hidden="hidden" name="cid" value="${cid}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="submit" class="btn btn-primary" >Submit</button>
				</div>
			</form>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<%@ include file="/WEB-INF/view/contest/contestHeader.jsp" %>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<div class="bs-callout bs-callout-info">
				<h4>Problem ${pid }</h4>
				<p>${problemModel.title}</p>
			</div>
			<div class="bs-callout bs-callout-info">
				<h4>Description</h4>
				<p>${problemModel.description}</p>
			</div>
			<div class="bs-callout bs-callout-warning">
				<h4>Input</h4>
				<p>${problemModel.input}</p>
			</div>
			<div class="bs-callout bs-callout-warning">
				<h4>Output</h4>
				<p>${problemModel.output}</p>
			</div>
			<div class="bs-callout bs-callout-danger sampleText">
				<h4>Sample Input</h4>
				<div>
					${problemModel.sampleInput}
				</div>
			</div>
			<div class="bs-callout bs-callout-danger sampleText">
				<h4>Sample Output</h4>
				<div>
					${problemModel.sampleOutput}
				</div>
			</div>
			<p>
				<c:choose>
					<c:when test="${USERMODEL.username == null}">
						<a class="btn btn-success btn-lg disabled">Login To Submit</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-success btn-lg" onclick="$('#submitModal').modal();">Submit</a>
					</c:otherwise>
				</c:choose>
			</p>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		if ($('#lastLanguage').val() != 'null') {
			$('#language').val($('#lastLanguage').val());
		}
	});  
</script>


<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
