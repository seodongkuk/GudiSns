<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div id="idfind"></div>
<script>	
	var id = "${sessionScope.id}";
	var reg_date = "${sessionScope.reg_date}";
	
	if(id != ""){
		//session 에 loginId 가 있으면 로그인 박스를 보여준다.
		var content=" 회원님의 아이디는" + id+"입니다." +
		"가입날짜는" +reg_date + "입니다.";
		

		document.getElementById("idfind").innerHTML = content;		
	}
	
</script>