<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 500px;
	}
	table,th,td{
		border : 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
		text-align: center;
	}
	
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
	<!-- 노출되지 안항야 하거나 서버에 보내는 내용이 길 경우는 POST -->
		<table>
			<tr>
				<th>글번호</th>
				<td>${report_detail.board_idx}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${report_detail.user_id}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${report_detail.content}</td>
			</tr>
			<tr>
				<td colspan="2">
			
				</td>
			</tr>
		</table>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>