
	window.onload=AutoRun();
	function AutoRun(){
		var rn = changeUsername($("#username").val());
		var rp1 = changePassword1($("#password1").val());
		var rp2 = changePassword2($("#password2").val());
		var em = changeEmail($("#email").val());
	}
	
	function Debug(){
		console.log($("#Username_minLenUsername"));
	}
	// Firefox, Google Chrome, Opera, Safari, Internet Explorer from version 9
	function OnInput(name, event) {
		if (name == "username") {
			changeUsername(event.target.value);
		} else if (name == "password1") {
			changePassword1(event.target.value);
		} else if (name == "password2") {
			changePassword2(event.target.value);
		} else if (name == "email") {
			changeEmail(event.target.value);
		}
	}
	// Internet Explorer
	function OnPropChanged(name, event) {
		if (name == "username") {
			changeUsername(event.target.value);
		} else if (name == "password1") {
			changePassword1(event.target.value);
		} else if (name == "password2") {
			changePassword2(event.target.value);
		}
	}

	function changeUsername(input) {
		if (input == "") {
			document.getElementById("usernameGroup").setAttribute("class","form-group");
			return false;
		} else if (input.length < $("#Username_minLenUsername").val())  {
			document.getElementById("usernameGroup").setAttribute("class","form-group has-error");
			return false;
		} else if (input.length > $("#Username_maxLenUsername").val()) {
			document.getElementById("usernameGroup").setAttribute("class","form-group has-error");
			return false;
		} else {
			jQuery.ajaxSettings.async = false;
			jQuery.getJSON("./checkUser.htm?username=" + input,
				function(data) {
					if (data.value != "") {
						document.getElementById("usernameGroup").setAttribute("class","form-group has-error");
//						$("#sizing-addon1").popover({
//							content: data.value
//						});
						return false;
					} else {
						document.getElementById("usernameGroup").setAttribute("class","form-group has-success");
						return true;
					}
				});
			var rul =  document.getElementById("usernameGroup").getAttribute("class") 
			if (rul == "form-group has-success") return true;
			return false;
		}
	}

	function changePassword1(input) {
		if (input.length == 0) {
			document.getElementById("password1Group").setAttribute("class","form-group");
		} else if (input.length < 6) {
			document.getElementById("password1Group").setAttribute("class","form-group has-error");
		} else {
			document.getElementById("password1Group").setAttribute("class","form-group has-success");
			return true;
		}
		return false;
	}

	function changePassword2(input) {
		//document.getElementById("password2Status").innerHTML = $("#password2").val();
		if (input.length == 0) {
			document.getElementById("password2Group").setAttribute("class","form-group");
		} else if ($("#password1").val() == $("#password2").val()) {
			document.getElementById("password2Group").setAttribute("class","form-group has-success");
			return true;
		} else {
			document.getElementById("password2Group").setAttribute("class","form-group has-error");
		}
		return false;
	}

	function check() {
		
//		console.log("username: "+$("#username").val());
//		console.log("password1: "+$("#password1").val());
//		console.log("password2: "+$("#password2").val());
//		console.log("checkcode: "+$("#code").val());
		
		var rn = changeUsername($("#username").val());
		var rp1 = changePassword1($("#password1").val());
		var rp2 = changePassword2($("#password2").val());
		var em = changeEmail($("#email").val());
//		console.log(rn);
//		console.log(rp1);
//		console.log(rp2);
//		console.log(em);
		if (rn && rp1 && rp2 && em) {
			opencheckCodeModal();
		} else {
			//alert("Some Error Info On Page.Please Check~");
			return;
		}
	}
	
	function opencheckCodeModal(){
		$("#checkcodeModal").modal();
		changePic();
	}
	
	function checkcheckcodeModal(){
		jQuery.getJSON("../checkcode/checkPic.htm?topic="+$("#Checkcodekey_register").val()+"&code=" + $("#checkcode").val(),
				function(data) {
					if (data.value != "true") {
						alert("Check Code Error!")
						$("#checkcode").val("");
						$("#checkcode").focus();
						changePic();
					} else {
						$("#code").val($("#checkcode").val());
						$("#register").submit();
					}
				}
			);
	}

	function changePic() {
		document.getElementById("checkpic").setAttribute("src","../checkcode/getPic.htm?topic="+$("#Checkcodekey_register").val()+"&" + Math.random());
	}
	
	function changeEmail(input) {
		if (input.length == 0){
			document.getElementById("emailGroup").setAttribute("class","form-group");
			return false;
		}
		var reg = /^([a-zA-Z0-9]+[_|\_|\.\-]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (reg.test(input)) {
			jQuery.ajaxSettings.async = false;
			jQuery.getJSON("./checkEmail.htm?email=" + input,
				function(data) {
					if (data.value != "") {
						document.getElementById("emailGroup").setAttribute("class","form-group has-error");
					} else {
						document.getElementById("emailGroup").setAttribute("class","form-group has-success");
					}
				});
		} else {
			document.getElementById("emailGroup").setAttribute("class","form-group has-error");
			return false;
		}
		var rul =  document.getElementById("emailGroup").getAttribute("class") 
		if (rul == "form-group has-success") return true;
		return false;
	}
