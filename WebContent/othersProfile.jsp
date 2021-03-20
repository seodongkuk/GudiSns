<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="imgfile\icon.jpg">
    <title>타인 프로필</title>
    <style>
		body {
			width: 850px;
			height: 90%;
			margin-left: 25%;
			padding: 5px;
			border: 1px solid black;
			overflow: scroll;
		}
		table, td, th, tr {
			border: 1px solid white;
			border-collapse: collapse;
		}
		h1{
			text-align: center;
		}
		h2 {
			font-size: 70;
			padding-left: 265px;
		}
		
		th, td {
			padding: 5px;
		}
		
		th {
			width: 50px;
			font-size: 20;
		}
		
		input[type='text'] {
			height: 30px;
		}
		
		input[type='submit'] {
			height: 70px;
			background-color: rgb(240, 207, 207);
			border: 0px;
		}
		
		td {
			text-align: center;
		}
		
		#WriteBtn{
			background-color: rgb(240, 207, 207);
			border: 0px;
			position: absolute; 
			left: 1300px; 
			top: 130px;
			}
		#like{
			margin-left: -610px; 
			float: left;
		}
		div.board{
			width: 100%;
			margin-top: 15px;
			overflow: scroll;
		}
	    #btn{
	    	width: 56px;
	    	float: left;
    		width: auto;
    		margin-top: 23px;
	    }
	    #other{
	    	background-color: white;
	    	font-weight:bold; 
	    	float: left; 
	    	margin-top: 40px;
	    	font-size: 20px;
	    }
    </style>
</head>
<body>
	<h1>${id} 프로필</h1>
	
        <table style="height: 20%">
	        <tr>
	            <td rowspan="2">
	            	<img src="기본프사.png" width="100" height="100" style="margin-left: 210px;">
	            </td>
	            <td style="padding-right: 70px;">${member2.user_id}</td>  
	            <td id="btn">
	                <button onclick="location.href = 'budReq'">친구<br>요청</button>&nbsp;&nbsp;&nbsp;
	                <button onclick="location.href='DM_Room?id=${member2.user_id}&&create=${sessionScope.loginId}'">
	                	DM<br>보내기</button>&nbsp;&nbsp;&nbsp;
	                <button onclick="location.href = 'budDel'">친구<br>삭제</button>
	            </td>          
	        </tr>
	    </table>
    
	<div class="board">
	     <c:forEach items="${list}" var="list">
	         <img src="타인프로필.gif" width="75" height="75" style="float: left;">
	         <button id="other" onclick="location.href='otherProfile?id=${list.user_id}'">
	         		${list.user_id}
	         </button>
	     		
	         <table>
	             <tr>
	         		<form action="singo" method="post">
		              	<td>
			            	<input type="hidden" name="board_idx" value="${list.board_idx}" />
			            	<input type="hidden" name="user_id" value="${list.user_id}" />
			            	<input style=" width: 75px; height: 25px; float: right; margin-bottom: -25px;" 
			            		type="submit" value="신고하기">
		              	</td>
	      			</form>
	             </tr>
	             <tr>
	                 <td>
	                     <img src="게시물이미지.gif" width="600" height="450" style="margin-top:50px" />
	                 </td>
	             </tr>
	             <tr>
	                 <td>
	                 	<p>${list.hashTag}</p>
	                    	<p>${list.content}</p>
	                    <button id="moreShow" name="board_idx" value="${list.board_idx}"
	                        onclick="location.href='detail?board_idx=${list.board_idx}'">더보기</button>
	                 </td>
	                 <td>
	                     <button id="like">♥</button>
	                     <input style="float: left; border: none; margin-left: -580px;" type="text"
	                         value="좋아요숫자"><span id="likecnt"></span>
	                     <input style="float: right; border: none; margin-left: -500px;
	      				type=" text" value="작성날짜:2021-03-10">
	                 </td>
	             </tr>
	             <form action="rlist" method="POST">
	                 <tr>
	                     <td>
	                         <p style="font-size: 15px; color: grey;">
	                             댓글 모두 ${rcnt}개입니다.</p>
	                     </td>
	                 </tr>
	             </form>
	         </table>
	     </c:forEach>
	 </div>
	<iframe src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>
</body>
<script>
    /* function a(){
        var con=document.getElementById("bBoxMenu");
        var atag = document.getElementById("updateA");
        if(con.style.visibility =="visible"){
            atag.style.visibility="visible";
        }else if(con.style.visibility="hidden"){
            con.style.visibility="visible";
        }
    } */

</script>

</html>