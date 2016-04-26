<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Judge Socket Manager</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<c:choose>
					<c:when test="${model == null}">
						<div class="col-sm-6 col-md-6">
							<div class="thumbnail">
								<div class="caption">
									<h3>New Server Port</h3>
									<div class="form-group">
										<label for="portInput">Port</label>
										<input class="form-control" id="serverPort"></input>
									</div>
									<p>
										<a href="javascript:judgeServerStart($('#serverPort').val())" class="btn btn-primary" >Start</a>
									</p>
								</div>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="col-sm-6 col-md-6">
							<div class="thumbnail">
								<div class="caption">
									<h3>Server Port Running</h3>
									<div class="form-group">
										<label>Port</label>
										<input class="form-control" readonly value="${model.port}"></input>
										<input id="serverPort" hidden="hidden">
									</div>
									<div class="form-group">
										<label>Status</label>
										<input class="form-control" readonly value="${model.status}"></input>
									</div>
									<p>
										<a href="javascript:stopJudgeServer()" class="btn btn-primary">Stop</a>
									</p>
								</div>
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				<div class="col-sm-6 col-md-6">
					<div class="thumbnail">
						<div class="caption">
							<h3>New Client</h3>
							<div class="form-group">
								<label for="portInput">Address</label>
								<input class="form-control" id="clientAddress"></input>
							</div>
							<div class="form-group">
								<label for="portInput">Port</label>
								<input class="form-control" id="clientPort"></input>
							</div>
							<p>
								<a href="javascript:judgeClientStart($('#clientAddress').val(),$('#clientPort').val())" class="btn btn-primary" >Connect</a>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">Judge Client</h3>
		</div>
		<div class="panel-body">
			<div class="row">
				<c:forEach items="${servers}" var="item">
					<div class="col-sm-6 col-md-4">
						<div class="thumbnail">
							<div class="caption">
								<h3>${item.value.judgeName}<small>${item.value.status}</small></h3>
									<p>Address</p>
									<p><code>${item.value.socket.inetAddress}:${item.value.socket.port}</code></p>
									<p>Last Query</p>
									<p><code><fmt:formatDate value="${item.value.lastQuery}" pattern="yyyy-MM-dd HH:mm:ss"/></code></p>
									<p>
										<a href="javascript:disConnect('${item.key}')" class="btn btn-primary" >DisConnect</a>
									</p>
							</div>
						</div>
					</div>													
				</c:forEach>
			</div>
		</div>
	</div>
