<div class="col-md-2">
	<div class="list-group">
		<a class="list-group-item" href="index.htm" id="index">
			<h4 class="list-group-item-heading">System Info</h4>
		</a>
		<a class="list-group-item" href="problem.htm?page=0" id="problem">
			<h4 class="list-group-item-heading">Problem</h4>
		</a>
		<a class="list-group-item" href="contest.htm?page=0" id="contest">
			<h4 class="list-group-item-heading">Contest</h4>
		</a>
		<a class="list-group-item" href="permission" id="permission">
			<h4 class="list-group-item-heading">Permission</h4>
		</a>
		<a class="list-group-item" href="logger.htm?page=0" id="logger">
			<h4 class="list-group-item-heading">Logger</h4>
		</a>
		<a class="list-group-item" href="picture.htm?page=0" id="picture">
			<h4 class="list-group-item-heading">Picture</h4>
		</a>				
	</div>
</div>
<script>
$(document).ready(function(){
	$('#'+$('#methodName').val()).attr('class','list-group-item active');
})
</script>