<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link rel="stylesheet" href="./bootstrap-4.6.0-dist/css/bootstrap-grid.min.css">
        <link rel="stylesheet" href="./bootstrap-4.6.0-dist/css/bootstrap-reboot.min.css">
        <link rel="stylesheet" href="./bootstrap-4.6.0-dist/css/bootstrap.min.css">
<style>
	

	
	input[type='text']{
		width:100%;
	}
	
	textarea{
		width: 100%;
		height: 150px;
		resize: none;
	}	

</style>
</head>
<body>
<center>
	<!-- 노출되지 안항야 하거나 서버에 보내는 내용이 길 경우는 POST -->
		<table class="table table-dark" style="width: 800px">
			<tr>
				<th>글번호</th>
				<td>${report_detail.board_idx}</td>
			</tr>
			<tr>
				<th>글작성날짜</th>
				<td>${report_detail.writedate}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${report_detail.user_id}</td>
			</tr>
			<tr>
				<th>글내용</th>
				<td>${report_detail.content}</td>
			</tr>
			<tr>
				<th>이미지</th>
				<td><img src="/GudiSns/photo/${report_detail.newFileName}" alt="이미지 없음"width="600" height="450"style="margin-top:100px"/></td>
			</tr>
		</table>
		  <script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script src="./bootstrap-4.6.0-dist/js/bootstrap.bundle.min.js"></script>
    <script src="./bootstrap-4.6.0-dist/js/bootstrap.min.js"></script>
    <iframe src="manager_bottom.jsp" width="800px" height="500px" scrolling="no" frameborder="0"></iframe>
	</center>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>