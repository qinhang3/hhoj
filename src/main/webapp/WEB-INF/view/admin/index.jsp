<!-- Header Begin -->
<jsp:include flush="true" page="/templates/header.jsp"></jsp:include> 
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
							<h3 class="panel-title">Memory</h3>
						</div>
						<div class="panel-body">
							<div class="col-md-4">
								<p><span>Total Memory : </span><code id="totalMemory"></code><code>KB</code></p>
								<p><span>Free Memory : </span><code id="freeMemory"></code><code>KB</code></p>
								<p><span>Free Percent : </span><code id="freePercent"></code><code>%</code></p>
								<a class="btn btn-danger" onclick="gcNow()">System.gc</a>
							</div>
							<div class="col-md-8">
								<div id="container" style="width:100%;height:184px;margin:0 auto;"></div>
								<div style="text-align:center;clear:both;"></div>
							</div>
						</div>
					</div>
					<div id="pressureMonitor">
					</div>
					<div id="judgeDiv">
					</div>					
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${CONTEXTPATH}/js/highcharts.js"></script>
<script type="text/javascript" src="${CONTEXTPATH}/js/adminIndex.js"></script>
<script>
	$(document).ready(function(){
		buildChart();
		getJudgeDiv();
		getMonitorDiv();
		setInterval(getJudgeDiv, 5000);
		setInterval(getMonitorDiv, 5000);
	});
</script>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include> 
<!-- Footer End -->
