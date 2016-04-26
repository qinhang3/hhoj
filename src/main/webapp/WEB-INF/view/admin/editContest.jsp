<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- Header End -->

<div class="modal fade" id="addModal">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Add Problem</h4>
			</div>
			
			<div class="modal-body">
				<div class="form-group">
					<label for="adds">Input Problem Ids</label>
					<input type="text" class="form-control" placeholder="eg: 1000-1008,1010" id="adds">
				</div>
			</div>
			<div class="modal-footer">
				<button type="submit" class="btn btn-primary" onclick="addProblem($('#adds').val())">Submit</button>
			</div>
		</div>
		<!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<c:choose>
				<c:when test="${model.id==null}">
					<h1>New Contest</h1>
				</c:when>
				<c:otherwise>
					<h1>Contest #${model.id}</h1>
				</c:otherwise>
			</c:choose>
			<form method="post" action="doEditContest.htm">
				<input id="cid" name="cid" value="${model.id }" hidden>
				<div class="bs-callout bs-callout-info">
					<h4>Information</h4>
					<div class="form-group">
						<label for="title">Title</label>
	        			<input type="text" class="form-control" id="title" name="title" value="${model.title}">
	        		</div>
					<div class="form-group">
						<label for="startTime">Start Time</label>
	        			<div class="input-group date dateSelect" id="startTime" data-date-format="yyyy-mm-dd hh:ii:ss">
		                	<input class="form-control" type="text" name="startTime" value="<fmt:formatDate value="${model.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />">
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	            		</div>
	        		</div>
	        		<div class="form-group">
						<label for="endTime">End Time</label>
	        			<div class="input-group date dateSelect" id="endTime" data-date-format="yyyy-mm-dd hh:ii:ss">
		                	<input class="form-control" type="text" name="endTime" value="<fmt:formatDate value="${model.endTime}" pattern="yyyy-MM-dd HH:mm:ss" />">
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	            		</div>
	        		</div>
				</div>
				<div class="bs-callout bs-callout-warning">
					<h4>Problem</h4>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>PID</th> 
								<th>Title</th>
								<th>Time Limit</th>
								<th>Memory Limit</th>
								<th>
									Tag
									<a class="btn btn-info btn-xs" onclick="$('#addModal').modal()">Add Problem</a>
								</th>
							</tr>
						</thead>
						<tbody id="problemsTable">
						</tbody>
					</table>
					<input hidden value="${model.problems}" id="problems" name="problems">
				</div>
				<div class="bs-callout bs-callout-danger">
					<h4>Member <small>No need to change here if the contest will be public.</small></h4>
				</div>
				<p>
					<button class="btn btn-success btn-lg">Submit</button>
				</p>
			</form>
		</div>
	</div>
</div>
<script>
	$(document).ready(function(){
		$('.dateSelect').datetimepicker({
	        weekStart: 1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			forceParse: 0,
	        showMeridian: 0,
	        minView: 0,
			maxView: 4,
			pickerPosition: 'bottom-left'
	    });
		$.get("getNewProblems.htm",{list:$('#problems').val(),cid:$('#cid').val()},function(data){
			reflashProblem(data);
		});
	});
	
	function deleteProblem(index){
		$.get("getNewProblems.htm",{list:$('#problems').val(),del:index,cid:$('#cid').val()},function(data){
			reflashProblem(data);
		});
	}
	
	function moveUpProblem(index){
		$.get("getNewProblems.htm",{list:$('#problems').val(),moveUp:index,cid:$('#cid').val()},function(data){
			reflashProblem(data);
		});
	}
	
	function addProblem(indexs){
		$.get("getNewProblems.htm",{list:$('#problems').val(),add:indexs,cid:$('#cid').val()},function(data){
			reflashProblem(data);
			$('#addModal').modal('hide');
			$('#adds').val('');
		});
	}
	
	function reflashProblem(data){
		$('#problems').remove();
		$('#problemsTable').children().remove();
		$('#problemsTable').append(data);		
	}
	
	function rejudge(pid){
		cid = $('#cid').val();
		var sure = window.confirm('Rejudge all solution of Problem '+pid+' in Contest '+cid+' ?');
		if (sure){
			$.post("rejudge.htm",{"pid":pid,"cid":cid},function(data){
				if (data.success) {
					alert("Rejudge success , modify solution "+data.value);
				} else {
					alsert("Rejudge error ,  because of "+data.msg);
				}
			});
		}
	}

</script>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
