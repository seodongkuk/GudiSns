<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<title>구디SNS</title>
<style>
	body {
		width: 850px;
		height: 90%;
		margin-left: 25%;
		padding: 5px;
		border: 1px solid black;
		overflow: hidden;
	}
	table, td, th, tr {
		border: 1px solid white;
		border-collapse: collapse;
		padding: 5px;
	}
	
	th {
		width: 50px;
		font-size: 20;
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
		margin-right: 110px;
    	margin-top: 50px;
	}
	
	td {
		text-align: center;
	}
	
	#WriteBtn{
		background-color: rgb(240, 207, 207);
		border: 0px;
		/* position: absolute; 
		left: 1300px; 
		top: 130px; */
		margin-left: 450px;
    	margin-top: 45px;
		}
	
	div.board{
		height: 770px;
		width: 100%;
		overflow: scroll;
	}
	#arr{
		margin-left: 418px;
    	margin-top: 12px;
	}
	div.like{
		margin-left: -1292px;
	}
</style>

</head>
<body style="overflow:scroll; width:832px; height:1029px;">

    <div class="main">
        <table style="float: left; margin-top: 55px; margin-bottom: 20px;">
            <tr>
            	<!-- 프로필사진 -->
                <td rowspan="2"><img src="기본프사.png" width="80px" height="80px"></td>
                <td><input type="button" value="${loginId}" style="background-color: white; font-weight: bold;"></td>
            </tr>
            <tr>
                <td><input type="button" value="로그아웃" onclick="location.href='logout'"></td>
            </tr>
        </table>
        <h2 style="font-size: 70;padding-left: 265px;">구디SNS</h2>
        <hr>
	        <button id="WriteBtn" onclick="location.href='newWriting.jsp'">
	        	새 글쓰기</button>
	        
	        <select id="arr" onchange="arrayEvt()" name="select">
	        	<option value="" selected disabled>게시글 정렬</option>      
	            <option value="추천순"<c:if test="${select eq '추천순'}">selected</c:if>>추천순</option>
	            <option value="최신순"<c:if test="${select eq '최신순'}">selected</c:if>>최신순</option>
	        </select>
       
        	<div class="board">
            <c:forEach items="${flist}" var="flist">
            		<form action="singo" method="post">
	                	<input type="hidden" name="board_idx" value="${flist.board_idx}" />
	                	<input type="hidden" name="user_id" value="${flist.user_id}" />
	                	<input type="submit" value="신고하기" 
	                	style=" width: 75px; height: 25px; float: right; margin-bottom: -5px; ">
            		</form>
            		<!-- 프로필사진 -->
                <img src="기본프사.png" width="75" height="75" style="float: left; margin-left: 20px;">
                <button style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;"
                    onclick="location.href='otherProfile?id=${flist.user_id}'">
                    ${flist.user_id}</button>
                <table id="tabl">
                    <tr>
                        <c:if test="${flist.newFileName ne null }">
                        <td>
                            <img src="/GudiSns/photo/${flist.newFileName}" width="600" height="450" style="margin-top:100px; margin-left: -73px;" />
                        </td>
                        </c:if>
                        <c:if test="${flist.newFileName eq null }">
                         <td>
                            <img src="/GudiSns/photo/${flist.newFileName}" width="600" height="0" style="margin-top:100px; visibility: hidden;" />
                        </td>
                        </c:if>
                    </tr>
                    <tr>
                        <td style="padding-right: 140px;">
			            		<p>${flist.hashTag}</p>
                                <p>${flist.content}</p>
                                <button id="moreShow" name="board_idx" value="${flist.board_idx}"
                                    onclick="location.href='detail?board_idx=${flist.board_idx}'">더보기</button>
                        </td>
                        <td>
							<p style="margin-top: -17px; margin-left: -225px;">${flist.writedate}</p>
                        </td>
                        <td>
                        	<p style="margin-top: -17px; margin-left: -500px;">추천 수: ${flist.cnt}</p>
                        </td>
                        
                    </tr>
                    <tr>
                    	<td style="text-align: left; padding-left: 150px; font-weight: 600;">댓글 개수는 ${flist.rcnt} 개 입니다.</td>
                    </tr>
                </table>
                    <br>
            </c:forEach>
            <div id="nolist"><a href="todayTag">친구가없습니다 친구추가를원하시면 눌러주세요</a></div>
        </div>
    </div>
    <iframe src="navi.jsp" width="850px" height="80px" scrolling="no" frameborder="0"></iframe>
</body>

<script>

	var nolist = "${flist}";
	

	if(nolist != ""){
		$("#nolist").css("display","none");
	}
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}

	function arrayEvt(){
		var selectVal = $('#arr').val();
    	console.log(selectVal);
    	if(nolist != ""){
			location.href='./array?select='+selectVal;
    	}else{
    		return;
    	}
	}	

	
</script>
</html>