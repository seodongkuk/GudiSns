<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<!-- 제이쿼리 사용을 위해 선언 -->
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            body{
                width: 850px;
				height: 850px;
				margin-left: 25%;
				padding: 5px;
				border: 1px solid black;
				overflow: hidden;
            }
            div.find{
            	text-align: center;
            	margin-top: 5%;
            }
            table{
            	width: 650px;
                /* height: 100px; */
                margin: auto;
            	text-align: center;
            }
        </style>	
	</head>
	<body>
		<div class="find">
	        <form action="find" method="get">
	            <select name="find">
	                <option value="none">===선택===</option>
	                <option value="HashTag">해시태그</option>
	                <option value="User" selected>유저</option>
	            </select>
	            <input type="text" name="search" placeholder="검색어를 입력해주세요.">
	            <button>검색</button>
	        </form>
    	</div>
    	<br>
    	<div style="height: 83%">
	    	<c:forEach items="${list}" var="member2">
			    <table>
			        <tr>
			            <td rowspan="2"><img src="기본프사.png" width="70" height="70"></td>
			            <td style="padding-right: 70px;">
			            	<button onclick="location.href='otherProfile?id=${member2.user_id}'">${member2.user_id}</button>
			            </td>  
			            <td style="padding-left: 40px;">
			                <button onclick="location.href = 'budReq'">친구요청</button>
			                <!-- <button id="budReq">친구요청</button> -->
			                <button onclick="location.href='DM_Room?id=${member2.user_id}&&create=${sessionScope.loginId}'">DM보내기</button>
			                <button onclick=delChk()>친구삭제</button>
			            </td>          
			        </tr>
			    </table>
			</c:forEach>
		</div>
	    <iframe style="position: absolute; margin-top: -20px" 
	    	src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>
	</body>
	<script>
	
		/* $('#budReq').click(function()){
			
		} */
	
	
		//Service에서 지정한 msg 값을 alert로 View
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
		
		/* var msg2 = "${confirm}"
		if(msg2 != ""){
			confirm(msg2);
		} */
		
		function delChk(){
			var chk = confirm('정말 삭제하시겠습니까?');
			if(chk){
				location.href = "budDel";
			}
		}
		
	</script>
</html>