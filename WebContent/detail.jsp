<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>구디SNS</title>
<style>
	body {
		width: 850px;
		height: 850px;
		margin-left: 25%;
		padding: 5px;
		border: 1px solid black;
		overflow: hidden;
	}
	
	table, td, th, tr {
		border: 1px solid white;
		border-collapse: collapse;
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
	}
	
	input[type='submit'] {
		height: 70px;
	}
	
	input[type='text'] {
		height: 30px;
	}
	
	input[type='password'] {
		height: 30px;
	}
	
	input[type='submit'] {
		background-color: rgb(240, 207, 207);
		border: 0px;
	}
	
	td {
		text-align: center;
	}
	
	th {
		font-size: 20;
	}
	button{
		background-color: rgb(240, 207, 207);
		border: 0px;
		position: absolute; 
		left: 1200px; 
		top: 130px;
		}
</style>
</head>
<div class="main">
	<body>
		<table style="float: left; margin-top: 55px;">
			<tr>
				<td rowspan="2"><img src="유저프로필.gif" width="80" height="80"></td>
				<td><input type="button" value="${loginId}"
					style="background-color: white; font-weight: bold;"></td>
			</tr>
			<tr>
				<td><input type="button" value="로그아웃"
					onclick="location.href='logout'"></td>
			</tr>
			<button onclick="location.href='newWriting.jsp'">
				새 글쓰기
			</button>
		</table>
		<h2>구디SNS</h2>
		<hr>
		<select name="array" style="position: absolute; left: 1200px; z-index: 1">
			<option value="추천순">추천순</option>
			<option value="최신순">최신순</option>
		</select>
	
		<div>
			<img src="타인프로필.gif" width="75" height="75" style="float: left;">
            <input type="button"value="USER_ID" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
       <table id="board_idx">
           <tr>
           <td>
            <img src="게시물이미지.gif" width="600" height="450"style="margin-top:100px" />
        </td>
            </tr>
        <tr>
        
       <td>
            <button id="like">♥</button>
            <input style="float: left;margin-top: 15px;border: none;" type="text" value="좋아요숫자">
            <input type="text"value="작성날짜:2021-03-10"style="float: right;border: none;margin-top: 15px;">
        </td>
        </tr>
        <tr>
        <td>
            <p style="font-size: 15px; color: grey;" >
            	댓글 모두 ${rcnt}개입니다.</p>
        </td>
    </tr>
    <form action="rwrite" method="POST">
    	<tr>
        <td>
        	<input type="hidden" name="user_id" value="${dto.user_id}"/>
        	<input type="hidden" name="board_idx" value="${dto.board_idx}"/>
            <b>${dto.user_id}</b> : <input type="text" name="content" size="70" placeholder="댓글을 입력해주세요."> 
            <button>댓글 작성</button>
        </td>
    </tr>
    </form>

           
     </table>
		</div>
		<iframe src="navi.jsp" width="850px" height="1000px" scrolling="no" frameborder="0"></iframe>
	</body>
	<!-- style="position: absolute; float: left; scroll-behavior: auto;" -->
</div>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>