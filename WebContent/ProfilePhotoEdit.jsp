<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
			body{
				position: relative;
				width: 100%;
				height: 100%;
			}
			#newProfilePhoto{
				position: absolute;
				width:50%;
				left: 30%;
				margin-top:10%;
			}
			#photo{
				width: 50%;
			}
		</style>
	</head>
	<body onload="window.resizeTo(800,1000)">
		<h2 style="text-align: center;">사진을 선택하여 주세요</h2>
		<input type="hidden" name="id" value="${sessionScope.loginId}"/>
		<div id="newProfilePhoto">
		<form action="profilePhoto" class="filebox" method="post" enctype="multipart/form-data" name="fomvl">
			<input type="file" id="photo" name="profilePhoto" value="사진업로드"/>
		</form>
		</div>
		<button style="margin-left:40%;margin-top:20%; height:30px;">사진 업데이트!!!</button>
	</body>
	<script>
	</script>
</html>