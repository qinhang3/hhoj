<!-- Header Begin -->
<%@ include file="/templates/header.jsp" %>
<!-- Header End -->
<div class="jumbotron">
	<div class="container">
<div class="col-md-8 col-md-offset-2" style="padding-top: 48px;">
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
      	<c:forEach items="${picList}" var="model" varStatus="var">
	        <li data-target="#carousel-example-generic" data-slide-to="${var.index}" <c:if test="${var.index==0}">class="active"</c:if>></li>
      	</c:forEach>
      </ol>
      <div class="carousel-inner" role="listbox">
      	<c:forEach items="${picList}" var="model" varStatus="var">
	        <div class="item <c:if test="${var.index==0}">active</c:if>">
	          <img width="100%" data-src="holder.js/900x500/auto/#777:#555/text:Building" alt="Pic[900x500]" src="${model}" data-holder-rendered="true">
	        </div>
      	</c:forEach>
      </div>
      <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div>
   </div>
    </div>
    </div>
<!-- Footer Begin -->
<jsp:include flush="true" page="/templates/footer.jsp"></jsp:include>
<!-- Footer End -->
