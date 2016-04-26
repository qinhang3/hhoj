<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>
		<c:choose>
			<c:when test="${TITLE == null }">
				HHOJ
			</c:when>
			<c:otherwise>
				${TITLE}
			</c:otherwise>
		</c:choose>
	</title>
	<link href="${CONTEXTPATH}/css/bootstrap.css" rel="stylesheet">
	<link href="${CONTEXTPATH}/css/docs.min.css" rel="stylesheet">
	<link href="${CONTEXTPATH}/css/my.css" rel="stylesheet">
	<link href="${CONTEXTPATH}/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
	
	<link type="text/css" rel="stylesheet" href="${CONTEXTPATH}/js/syntaxhighlighter/styles/shCoreDefault.css"/>
	
</head>
	
<body>
	<script src="${CONTEXTPATH}/js/jquery.min.js"></script>
	<script src="${CONTEXTPATH}/js/bootstrap.min.js"></script>
		
	${hiddenFiled}

	<nav class="navbar navbar-inverse navbar-static-top" style="margin-bottom: 0px;">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-7">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${CONTEXTPATH}">HHOJ</a>
			</div>
	
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-7">
				<ul class="nav navbar-nav">
					<li id="indexHeader"><a href="${CONTEXTPATH}/.">Home</a></li>
					<li id="problemHeader"><a href="${CONTEXTPATH}/problem/list.htm?page=0">Problem</a></li>
					<li id="statusHeader"><a href="${CONTEXTPATH}/status/problem.htm?page=0">Status</a></li>
					<li id="contestHeader"><a href="${CONTEXTPATH}/contest/list.htm?page=0">Contest</a></li>
					<c:if test="${INgod || INadmin}">
						<li id="adminHeader"><a href="${CONTEXTPATH}/admin/index.htm">Admin</a></li>
					</c:if>
					<li>
	          			<a href="${CONTEXTPATH}/index/bug.htm?url=${QUERYURL}">Find BUG</a>
	          		</li>
				</ul>
				<form class="navbar-form navbar-left" action="${CONTEXTPATH}/problem/show.htm" id="pidGoForm">
	            	<div class="form-group">
	                	<input type="text" class="form-control" placeholder="Enter Problem Id" name="pid" id="pidGo">
	            	</div>
	            	<button type="submit" class="btn btn-default">GO</button>
          		</form>
          		
          		<form class="navbar-form navbar-left" action="${CONTEXTPATH}/contest/index.htm" id="cidGoForm" hidden>
	            	<div class="form-group">
	                	<input type="text" class="form-control" placeholder="Enter Contest Id" name="cid" id="cidGo">
	            	</div>
	            	<button type="submit" class="btn btn-default">GO</button>
          		</form>
          	
	
				<ul class="nav navbar-nav navbar-right">
					<c:choose>
						<c:when test='${USERMODEL == null}'>
							<li><a href="${CONTEXTPATH}/user/login.htm">Login</a></li>
							<li><a href="${CONTEXTPATH}/user/register.htm">Register</a></li>
						</c:when>
						<c:otherwise>
							<li class="dropdown" class="active">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
									${USERMODEL.username}
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="${CONTEXTPATH}/user/info.htm">Info</a></li>
									<li><a href="${CONTEXTPATH}/user/modify.htm">Modify</a></li>
									<li class="divider"></li>
									<li><a href="${CONTEXTPATH}/user/logout.htm">Logout</a></li>
								</ul>
							</li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</div>
	</nav>
	
	
	<div class="col-md-12" role="main">
		<c:if test='${USERMODEL.emailStatus == 0}'>
			<div class="modal fade" id="checkcodeModalHeader">
				<div class="modal-dialog modal-lg">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<h4 class="modal-title">Certify E-mail Address</h4>
						</div>
						<div class="modal-body">
							<div class="form-group">
								<img src="" class="img-thumbnail center-block" id="checkpicHeader" onclick="changePicHeader()">
							</div>
							<form id="certifyEmailForm" action="${CONTEXTPATH}/user/certifyEmail.htm" method="post">
								<div class="form-group">
									<div class="input-group input-group-lg">
										<span class="input-group-addon" id="sizing-addon1">
											<span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>
										</span>
											<input class="form-control" type="text" placeholder="Check Code" name="code" aria-describedby="sizing-addon1" id="checkcodeHeader">
											<input hidden="true" name="topic" value="CHECKEMAIL">
									</div>
								</div>
							</form>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
							<button type="button" class="btn btn-primary" onclick="submitHeader()">Submit</button>
						</div>
					</div>
					<!-- /.modal-content -->
				</div>
				<!-- /.modal-dialog -->
			</div>
			<!-- /.modal -->
			<div class="alert alert-danger alert-dismissible" role="alert">
				<div class="row">
					<div class="col-md-8 col-md-offset-2">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					    You CAN NOT submit code until 
					    <strong><a href="javascript:changePicHeader();$('#checkcodeModalHeader').modal();">certify your E-mail address</a></strong>.
					    Your E-mail is <strong>${USERMODEL.email}</strong> now.
					    <strong><a href="${CONTEXTPATH}/user/modify.htm">Modify Here</a></strong>.
					    Thank you :-)
					    
					</div>
				</div>
			</div>
			<script>
				function changePicHeader() {
					$('#checkpicHeader').attr("src","../checkcode/getPic.htm?topic=CHECKEMAIL&" + Math.random());
				}
				function submitHeader(){
					$.get('../checkcode/checkPic.htm',{"topic":"CHECKEMAIL","code":$('#checkcodeHeader').val()},function(data){
						if (data.value){
							$('#certifyEmailForm').submit();
						} else {
							changePicHeader();
							alert("Error Checkcode, please Retry.");
						}
					})
				}
			</script>
		</c:if>
		<c:if test='${errmsg != null}'>
			<div class="alert alert-danger alert-dismissible" role="alert">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					    <strong>Oh snap ! </strong>${errmsg }
					</div>
				</div>
			</div>
		</c:if>
		<c:if test='${sucmsg != null}'>
			<div class="alert alert-success alert-dismissible" role="alert">
				<div class="row">
					<div class="col-md-6 col-md-offset-3">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<strong>Oh yeah ! </strong>${sucmsg }
					</div>
				</div>
			</div>
		</c:if>
	</div>
	<input hidden="true" id="servletName" value="${SERVLETNAME}">
	<input hidden="true" id="methodName" value="${METHODNAME}">
	<input hidden="true" id="sessionId" value="${SESSIONID}">
	<script>
		$(document).ready(function(){
			if ($('#methodName').val()=='status' && $('#servletName').val()=='problem'){
				$('#statusHeader').attr('class','active');
				return;
			}
			if ($('#servletName').val()==''){
				$('#indexHeader').attr('class','active');
				return;
			}
			$('#'+$('#servletName').val()+'Header').attr('class','active');
		}); 
		
		function showPid(id){
			$('#pidGo').val(id);
			$('#pidGoForm').submit();
		}
		
		function showCid(id){
			$('#cidGo').val(id);
			$('#cidGoForm').submit();
		}
		
	</script>