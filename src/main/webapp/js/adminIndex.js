  
function gcNow(){
	jQuery.getJSON("./gcNow.htm",
		function(data) {
			
		}
	);
}
function buildChart() {                                                  
	Highcharts.setOptions({                                                     
		global: {                                                               
			useUTC: false                                                       
		}                                                                       
	});                                                                         
																				
	var chart;                                                                  
	$('#container').highcharts({                                                
		chart: {                                                                
			type: 'spline',                                                     
			animation: Highcharts.svg, // don't animate in old IE               
			marginRight: 10,                                                    
			events: {                                                           
				load: function() {                                              
					// set up the updating of the chart each second             
					var series = this.series[0];  
					var index = 0;
					setInterval(function() {   
						jQuery.ajaxSettings.async = true;
						jQuery.getJSON("./info.htm",
								function(json) {
									index++;
									for(key in json){
										$('#'+key).html(json[key]);
									}
									var xx = (new Date()).getTime(); // current time         
									var yy = json["freePercent"];                                  
									series.addPoint([xx, yy], true, index>20);                    
								});												
					}, 5000);                                                   
				}                                                               
			}                                                                   
		},                                                                      
		title: {                                                                
			text: 'Memory Free Percent'                                            
		},                                                                      
		xAxis: {                                                                
			type: 'datetime',                                                   
			tickPixelInterval: 100                                              
		},                                                                      
		yAxis: {                                                                
			title: {                                                            
				text: 'Free Percnet(%)'                                                   
			},                                                                  
			plotLines: [{                                                       
				value: 1,                                                       
				width: 1,                                                       
				color: '#808080'                                                
			}]                                                                  
		},                                                                      
		tooltip: {                                                              
			formatter: function() {                                             
					return '<b>'+ this.series.name +'</b><br/>'+                
					Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
					Highcharts.numberFormat(this.y, 2);                         
			}                                                                   
		},                                                                      
		legend: {                                                               
			enabled: false                                                      
		},                                                                      
		exporting: {                                                            
			enabled: false                                                      
		},                                                                      
		series: [{                                                              
			name: 'Free Percent',                                                
			data: (function() {                                                 
				// generate an array of random data                             
				var data = [];
				jQuery.ajaxSettings.async = false;
				jQuery.getJSON("./info.htm",
						function(json) {
							//index++;
							for(key in json){
								$('#'+key).html(json[key]);
							}
							var xx = (new Date()).getTime(); // current time         
							var yy = json["freePercent"];                                  
							data.push({x:xx,y:yy});                    
						});													                                                               
				return data;                                                    
			})()                                                                
		}]                                                                      
	});                                                                         
}

function getJudgeDiv(){
	$.get('judgeStatus.htm',{},function(data){
		reflashJudgeDiv(data);
	});
}

function getMonitorDiv(){
	$.get('appStatus.htm',{},function(data){
		if (data.indexOf("This page will show when we think you visit something secret") != -1){
			window.location.reload();
			return;
		}
		$('#pressureMonitor').children().remove();
		$('#pressureMonitor').append(data);
	});
}


function reflashJudgeDiv(data){
	if (data.indexOf("This page will show when we think you visit something secret") != -1){
		window.location.reload();
		return;
	}
	var focus = document.activeElement.id;
	var serverPort = $('#serverPort').val();
	var clientAddress = $('#clientAddress').val();
	var clientPort = $('#clientPort').val();
	$('#judgeDiv').children().remove();
	$('#judgeDiv').append(data);
	$('#serverPort').val(serverPort);
	$('#clientAddress').val(clientAddress);
	$('#clientPort').val(clientPort);
	$('#'+focus).focus();
}

function judgeServerStart(port){
	$.post('newJudgeServer.htm',{"port":port},function(data){
		reflashJudgeDiv(data);
	})
}
function judgeClientStart(host,port){
	$.post('newJudgeClient.htm',{"host":host,"port":port},function(data){
		reflashJudgeDiv(data);
	});
}
function stopJudgeServer(){
	$.post('stopJudgeServer.htm',{},function(data){
		reflashJudgeDiv(data);
	});
}
function disConnect(tid){
	$.post('disConnectJudgeClient.htm',{"tid":tid},function(data){
		reflashJudgeDiv(data);
	})
}