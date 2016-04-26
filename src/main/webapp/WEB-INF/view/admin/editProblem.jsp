<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Header End -->
<div class="jumbotron" style="margin-bottom: 0px; padding-bottom: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1 id="title" onclick="editTitle()"></h1>
			<input class="form-control input-lg" type="text" placeholder="Problem Title" id="titleIn" value="${model.title}" onblur="editTitleEnd()">
			<div  style="font-size:21px">
				<span>Problem ID: ${model.id }<c:if test="${model.id==null}">NEW</c:if> /</span>
				<span>Time Limit: </span>
				<span id="timeSpan" onclick="edit('#timeSpan','#timeInput')">${model.timeLimit}</span>
				<input hidden="true" id="timeInput" onblur="show('#timeSpan','#timeInput')" style="width:100px"/>
				<span>MS / Memory Limit:</span>
				<span id="memorySpan" onclick="edit('#memorySpan','#memoryInput')">${model.memLimit}</span>
				<input hidden="true" id="memoryInput" onblur="show('#memorySpan','#memoryInput')" style="width:100px"/>
				<span>KB</span>
			</div>
		</div>
	</div>
</div>
<div class="jumbotron" style="padding-top: 0px;">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<div class="bs-callout bs-callout-info">
				<h4>Description</h4>
				<div id="desc"></div>
			</div>
			<div class="bs-callout bs-callout-warning">
				<h4>Input</h4>
				<div id="input"></div>
			</div>
			<div class="bs-callout bs-callout-warning">
				<h4>Output</h4>
				<div id="output"></div>
			</div>
			<div class="bs-callout bs-callout-danger">
				<h4>Sample Input</h4>
				<div id="sampleInput" class="sampleText"></div>
			</div>
			<div class="bs-callout bs-callout-danger">
				<h4>Sample Output</h4>
				<div id="sampleOutput" class="sampleText"></div>
			</div>
			
			<div class="bs-callout bs-callout-danger">
				<h4>Author/Source</h4>
				<div id="author"></div>
			</div>
			
			<p>
				<button id="editButton" class="btn btn-warning btn-lg" onclick="modify()">Edit</button>
				<button id="previewButton" class="btn btn-info btn-lg" onclick="preview()">Preview</button>
				<button id="submitButton" class="btn btn-success btn-lg disabled" onclick="submit()">Submit</button>
			</p>
		</div>
	</div>
</div>
<form id="form" action="doEditProblem.htm" method="post">
	<input hidden="true" name="id" id="idF" value="${model.id}">
	<input hidden="true" name="title" id="titleF" value="${model.title}">
	<input hidden="true" name="description" id="descF" value="${model.description}">
	<input hidden="true" name="input" id="inputF" value="${model.input}">
	<input hidden="true" name="output" id="outputF" value="${model.output}">
	<input hidden="true" name="sampleInput" id="sinputF" value="${model.sampleInput}">
	<input hidden="true" name="sampleOutput" id="soutputF" value="${model.sampleOutput}">
	<input hidden="true" name="timeLimit" id="timeF" value="${model.timeLimit}">
	<input hidden="true" name="memLimit" id="memoryF" value="${model.memLimit}">
	<input hidden="true" name="author" id="authorF" value="${model.author}">
</form>

<script>
	$(document).ready(function(){
		CKEDITOR.disableAutoInline = true;
		$('#editButton').attr('class','btn btn-warning btn-lg disabled');
		$('#titleIn').slideUp();
		addAndSetData();
		editorClassic();
		previewMode = false;
	});
	
	function preview(){
		previewMode = true;
		editTitleEnd();
		$('#previewButton').attr('class','btn btn-info btn-lg disabled');
		saveDataAndRemove();
		addAndSetData();
		$('#editButton').attr('class','btn btn-warning btn-lg');
		$('#submitButton').attr('class','btn btn-success btn-lg');
	}
	
	function modify(){
		previewMode = false;
		$('#editButton').attr('class','btn btn-warning btn-lg disabled');
		$('#submitButton').attr('class','btn btn-success btn-lg disabled');
		remove();
		addAndSetData();
		editorClassic();
		$('#previewButton').attr('class','btn btn-info btn-lg');
	}
	
	function editorClassic(){
		CKEDITOR.replace('descIn');
		CKEDITOR.replace('inputIn');
		CKEDITOR.replace('outputIn');
		CKEDITOR.replace('sampleInputIn');
		CKEDITOR.replace('sampleOutputIn');
		CKEDITOR.replace('authorIn');
	}
	
	function addAndSetData(){
		$('#desc').append("<div id='descIn'></div>");
		$('#input').append("<div id='inputIn'></div>");
		$('#output').append("<div id='outputIn'></div>");
		$('#sampleInput').append("<div id='sampleInputIn'></div>");
		$('#sampleOutput').append("<div id='sampleOutputIn'></div>");
		$('#author').append("<div id='authorIn'></div>")
		$('#descIn').html($('#descF').val());
		$('#inputIn').html($('#inputF').val());
		$('#outputIn').html($('#outputF').val());
		$('#sampleInputIn').html($('#sinputF').val());
		$('#sampleOutputIn').html($('#soutputF').val());
		$('#authorIn').html($('#authorF').val());
		$('#title').html($('#titleF').val());
	}
	 
	function saveDataAndRemove(){
		saveData();
		remove();
	}
	
	function remove(){
		$('#desc').children().remove();
		$('#input').children().remove();
		$('#output').children().remove();
		$('#sampleInput').children().remove();
		$('#sampleOutput').children().remove();
		$('#author').children().remove();
	}
	
	function saveData(){
		$('#descF').val(CKEDITOR.instances.descIn.getData());
		$('#inputF').val(CKEDITOR.instances.inputIn.getData());
		$('#outputF').val(CKEDITOR.instances.outputIn.getData());
		$('#sinputF').val(CKEDITOR.instances.sampleInputIn.getData());
		$('#soutputF').val(CKEDITOR.instances.sampleOutputIn.getData());
		$('#authorF').val(CKEDITOR.instances.authorIn.getData());
		$('#timeF').val($('#timeSpan').html());
		$('#memoryF').val($('#memorySpan').html());
		$('#titleF').val($('#title').html());
	}
	
	function submit(){
		$('#form').submit();
	}
	
	function edit(span,input){
		if (previewMode) return;
		$(span).attr('hidden','true');
		$(input).val($(span).html());
		$(input).removeAttr('hidden');
		$(input).focus();
	}
	
	function show(span,input){
		$(span).html($(input).val());
		$(input).attr('hidden','true');
		$(span).removeAttr('hidden');
	}
	function editTitle(){
		if (previewMode) return;
		$('#titleIn').slideDown();
	}
	function editTitleEnd(){
		$('#titleIn').slideUp();
		$('#title').html($('#titleIn').val());
		$('#titleF').val($('#titleIn').val());
	}
	
</script>


<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
