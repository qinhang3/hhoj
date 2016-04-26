<!-- Header Begin -->
<jsp:include flush="true" page="/templates/header.jsp"></jsp:include> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Header End -->
<div class="jumbotron">
	<div class="container">
		<div class="col-md-10 col-md-offset-1" role="main">
			<h1>Admin</h1>
		</div>
		<div class="col-md-12" role="main">
			<div class="row">
				<%@ include file = '/WEB-INF/view/admin/adminMenu.jsp' %>
				<div class="col-md-10">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Upload Picture</h3>
						</div>
						<div class="panel-body">
							<form method="post" action="http://5.gdutcode.sinaapp.com/fileUpload.php?session=${SESSIONID}" enctype="multipart/form-data">
								<div class="form-group">
									<label for="typeInput">Type</label>
									<input name="type" id="typeInput" class="form-control">
								</div>
								<div class="form-group">
									<label for="fileInput">File</label>
									<input type="file" name="file" id="fileInput">
								</div>
								<input name="url" value="${FULLURL}" hidden="true">
								<button type="submit" class="btn btn-default">Submit</button>
							</form>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">Picture Manager</h3>
						</div>
						<div class="panel-body">
							<div class="form-group form-inline">
								<div class="input-group">								
									<div class="input-group-addon">Type Filter</div>
									<input id="filterFilter" class="form-control" placeholder="Type">
								</div>
								<button type="submit" class="btn btn-primary" onclick="filterNow()">Filter</button>
							</div>
							<div class="row" id="fileList1">
							</div>
							<div class="form-group">
								<input id="loadMoreButton" type="button" class="btn btn-default" onclick="loadPictureList()">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function(){
		filterNow();
	});
	function loadPictureList(){
		var url = "http://5.gdutcode.sinaapp.com/getFileList.php?callback=?&type="+type+"&offset="+offset+"&session="+$("#sessionId").val();
		$.getJSON(url,function(data){
			if (data.length == 0){
				$('#loadMoreButton').val('No More.').addClass('disabled');
			} else {
				data.forEach(function(e){
					var url = 'http://gdutcode-hhoj.stor.sinaapp.com/'+e;
					var html = '<div class="col-sm-6 col-md-4"><div class="thumbnail"><img src="'+url+'" alt="'+e+'">'
					+'<div class="caption"><p>'+e+'</p><p><a href="'+url+'" class="btn btn-primary">Show</a>'
					+' <a href="javascript:deletePic(\''+e+'\')" class="btn btn-danger">Delete</a></p></div>' 
					+'</div></div></div>';
					$('#fileList1').append(html);
					offset++;
				});
			}
		});
	}
	function filterNow(){
		$('#fileList1').children().remove();
		$('#loadMoreButton').val('Load More..').removeClass('disabled');
		type = $('#filterFilter').val();
		offset = 0;
		loadPictureList();
	}
</script>

<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->