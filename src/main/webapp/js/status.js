
function initClass(){
	cnt = $('#cnt').val();
	for (i = 0;i<cnt;i++){
		nowStatus = $('#sta'+i).text();
		updateClass(nowStatus,i);
	}
}
function updateProblemStatus(){
	$.get("ajaxProblem.htm",{pid:$('#pid').val(),username:$('#username').val(),status:$('#status').val(),language:$('#language').val(),page:$('#page').val()},function(data){
		$('#tbody').children().remove();
		$('#tbody').append(data);
	});
}

function updateContestStatus(){
	$.get("ajaxContest.htm",{cid:$('#cid').val()},function(data){
		if (data.indexOf("This page will show when we think you visit something secret") != -1){
			window.location.reload();
			return;
		}
		$('#tables').append(data);
		var cnt = $('#tables').children().length;
		for (var i=0;i<cnt-1;i++){
			$('.userTable:eq(0)').remove()
		}
	});
}
function updateClass(nowStatus,i){
	if (nowStatus=='Pending' || nowStatus=='RePending'){
		$('#tr'+i).attr('class','active');
	} else if (nowStatus == 'Accepted') {
		$('#tr'+i).attr('class','success');
		$('#tr'+i).attr('style','color:green');
	} else if (nowStatus == 'Wrong Answer'){
		$('#tr'+i).attr('class','danger');
		$('#tr'+i).attr('style','color:red');
	} else if (nowStatus == 'Running'){
		$('#tr'+i).attr('class','info');
	} else {
		$('#tr'+i).attr('class','warning');
	}
}

function changePage(page){
	$('#pageId').val(page);
	$('form').submit();
}
