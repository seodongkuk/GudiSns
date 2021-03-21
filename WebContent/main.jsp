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
	
	div.board{
		height: 800px;
		width: 100%;
		overflow: scroll;
	}
	#arr{
		left: 1300px; 
		z-index: 1;
	}
</style>

</head>
<body>

    <div class="main">
        <table style="float: left; margin-top: 55px;">
            <tr>
                <td rowspan="2"><img src="유저프로필.gif" width="80px" height="80px"></td>
                <td><input type="button" value="${loginId}" style="background-color: white; font-weight: bold;"></td>
            </tr>
            <tr>
                <td><input type="button" value="로그아웃" onclick="location.href='logout'"></td>
            </tr>
            <button id="WriteBtn" onclick="location.href='newWriting.jsp'">새 글쓰기</button>
        </table>
        <h2 style="font-size: 70;padding-left: 265px;">구디SNS</h2>
        <hr>
        <select id="arr" onchange="arrayEvt()" name="select">
        	<option value="" selected disabled>게시글 정렬</option>      
            <option class="recommend" value="추천순">추천순</option>
            <option class="latest" value="최신순">최신순</option>
        </select>
       
        	<div class="board">
            <c:forEach items="${flist}" var="flist">
            		${flist.hashTag}
            		<form action="singo" method="post">
	                	<input type="hidden" name="board_idx" value="${flist.board_idx}" />
	                	<input type="hidden" name="user_id" value="${flist.user_id}" />
	                	<input type="submit" value="신고하기" 
	                	style=" width: 100px; height: 20px; margin-left:75%;">
            		</form>
                <img src="타인프로필.gif" width="75" height="75" style="float: left;">
                <button style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;"
                    onclick="location.href='otherProfile?id=${flist.user_id}'">
                    ${flist.user_id}</button>
                <table id="board_idx">
                    <tr>
                        <td>
                            <img src="게시물이미지.gif" width="600" height="450" style="margin-top:100px" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                                <p>${flist.content}</p>
                                <button id="moreShow" name="board_idx" value="${flist.board_idx}"
                                    onclick="location.href='detail?board_idx=${flist.board_idx}'">더보기</button>

                        </td>
                        <td>
		                <div class="like">
							<c:if test="${ loginId eq null }">
								추천 기능은 <button type="button" id="login"><b class="w3-text-blue">로그인</b></button> 후 사용 가능합니다.<br/>
								<i class="fa fa-heart" style="font-size:16px;color:red"></i>
								<span id="like_cnt" class="like_count">${cnt}</span>					
							</c:if>
							<c:if test="${ loginId ne null }">
								<button id="likebtn"> 추천해줘~
								<i class="fa fa-heart" style="font-size:16px;color:red"></i>
								</button> <span id="like_cnt" class="like_count">${cnt}</span>
							</c:if>
						</div>
					<p> 작성날짜 : <input style="float: right; border: none; margin-left: -500px;"/>${flist.writedate}</p>
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
    </div>
    <iframe src="navi.jsp" width="850px" height="80px" scrolling="no" frameborder="0"></iframe>
</body>

	


	<!-- style="position: absolute; float: left; scroll-behavior: auto;" -->
<%-- =======
	<div class="main">
			<table style="float: left; margin-top: 55px;">
				<tr>
					<td rowspan="2"><img src="유저프로필.gif" width="80" height="80"></td>
					<td><input type="button" id="loginId" value="${loginId}"
						style="background-color: white; font-weight: bold;"></td>
				</tr>
				<tr>
					<td><input type="button" value="로그아웃"
						onclick="location.href='logout'"></td>
				</tr>
				<button id="WriteBtn" onclick="location.href='newWriting.jsp'">새글쓰기</button>
			</table>
			<h2>구디SNS</h2>
			<hr>
			<select id="array" onchange="arrayEvt()">
				<option class="recommend" value="recommend">추천순</option>
				<option class="latest" value="latest">최신순</option>
			</select>
			<div class="board">
				<c:forEach items="${flist}" var="flist">
					<input type="hidden" name="board_idx" value="${flist.board_idx}"/>
					<img src="타인프로필.gif" width="75" height="75" style="float: left;">
		            <input type="button"value="USER_ID" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
		       		<table id="board_idx">
			           	<tr>
				           <td>
				           		<img src="게시물이미지.gif" width="600" height="350"style="margin-top:50px" />
			        		</td>
			       		</tr>
		        		<tr>
		        			<td>
		        			<c:forEach items="${list}" var="myL">
		        				<p>${flist.content}</p>
		        				<button id="moreShow" name="board_idx" value="${myL.board_idx}" onclick="location.href='detail?board_idx=${myL.board_idx}'">더보기</button>
		        			</c:forEach>
		        			</td>
		        			
                     
		       				<td>
					            <button id="like">♥</button>
					            <input style="float: left; border: none; margin-left: -580px;" 
					            	type="text" value="좋아요숫자"><span id="likecnt"></span>
					            <input style="float: right;border: none; margin-left: -500px;"
					            	type="text"value="작성날짜:2021-03-10">
		        			</td>
		        		</tr>
		   				<form action="rlist" method="POST">
				        	<tr>
					        	<td>
					            <p style="font-size: 15px; color: grey;" >
					            	댓글 모두 ${rcnt}개입니다.</p>
				        		</td>
				    		</tr>
		   	 			</form>
		     		</table>
				</c:forEach>
			</div>
			<iframe 
				src="navi.jsp" width="850px" height="80px" scrolling="no" frameborder="0"></iframe>
	</div>
</body>--%>

<script>
	var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
	
	function arrayEvt(){
		var selectVal = $('#arr').val();
    	console.log(selectVal);
    	
    	if(selectVal=="최신순"){
    		location.href='./array?select='+selectVal;
    	}
    	else if(selectVal=="추천순"){
    		location.href='./array?select='+selectVal;
    	}
	}

 // 추천버튼 클릭시(추천 추가 또는 추천 제거)
	$("#likebtn").click(function(){
		var $idx = $("#board_idx");
		 var $id = $("#user_id");
		 var params = {};
		 params.idx = $idx.val();
		 params.id = $id.val();
		$.ajax({
			url: "like",
              type: "get",
              data: params,
              dataType:'JSON',
              success: function () {
           	   	likeCnt();	
              }
		});
	});
	
	 function likeCnt(){
		var $idx = $("#board_idx");
		var params = {};
		 params.idx = $idx.val();
		$.ajax({
			url: "likecnt",
            type: "get",
               data: params,
               dataType: 'JSON',
               success: function (data) {
            	   $("#like_cnt").html(data.cnt);	
            	   console.log($("#like_cnt"));
            	   console.log(data.cnt);
            	   console.log(cnt);
            	   
               }
		});
	}
	
	likeCnt(); 
</script>
</html>