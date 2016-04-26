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
						<input hidden="hidden" name="pid" value="${model.id}">
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
			<h1>${model.title}</h1>
			<p>Problem ID: ${model.id} / Time Limit: ${model.timeLimit} MS /
				Memory Limit: ${model.memLimit } KB</p>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<div class="bs-callout bs-callout-info"
				id="callout-xref-input-group">
				<h4>Description</h4>
				<p>${model.description}</p>
			</div>
			<div class="bs-callout bs-callout-warning"
				id="callout-xref-input-group">
				<h4>Input</h4>
				<p>${model.input}</p>
			</div>
			<div class="bs-callout bs-callout-warning"
				id="callout-xref-input-group">
				<h4>Output</h4>
				<p>${model.output}</p>
			</div>
			<div class="bs-callout bs-callout-danger sampleText">
				<h4>Sample Input</h4>
				<div>
					<p>${model.sampleInput}</p>
				</div>
			</div>
			<div class="bs-callout bs-callout-danger sampleText">
				<h4>Sample Output</h4>
				<div>
					<p>${model.sampleOutput}</p>
				</div>
			</div>
			<c:if test="${model.author != null}">
				<div class="bs-callout bs-callout-info">
					<h4>Author/Source</h4>
					<div>
						<p>${model.author}</p>
					</div>
				</div>
			</c:if>
			<p>
				<c:choose>
					<c:when test="${USERMODEL.username == null}">
						<a class="btn btn-success btn-lg disabled">Login To Submit</a>
					</c:when>
					<c:otherwise>
						<a class="btn btn-success btn-lg" onclick="$('#submitModal').modal();">Submit</a>
					</c:otherwise>
				</c:choose>
				<a class="btn btn-warning btn-lg" href="${CONTEXTPATH}/status/problem.htm?page=0&pid=${model.id}">Status</a>
			</p>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		if ($('#lastLanguage').val() != 'null') {
			$('#language').val($('#lastLanguage').val());
		}
		CKEDITOR.disableAutoInline = true;
	});  
</script>


<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
