<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
<title>구디SNS</title>
<style>
	body {
		width: 830px;
		height: 850px;
		margin-left: 20%;
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
	
	input[type='submit'], input[type='button'] {
		background-color: rgb(240, 207, 207);
		border: 0px;
	}
	
	td {
		text-align: center;
	}
	
	th {
		font-size: 20;
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
			<input type="button" value="새글쓰기" style="position: absolute; left: 1090px; top: 130px;">
		</table>
		<h2>구디SNS</h2>
		<hr>
		<select name="array" style="position: absolute; left: 1090px; z-index: 1">
			<option value="추천순">추천순</option>
			<option value="최신순">최신순</option>
		</select>
	
		<iframe style="position: absolute; float: left; scroll-behavior: auto;"
				src="board.jsp" width="800px" height="600px" frameborder="0">
		</iframe>
		<iframe src="navi.jsp" width="800px" height="1000px" scrolling="no" frameborder="0"></iframe>
	</body>
</div>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>