<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="utf-8">
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
	}
	
	h2 {
		font-size: 70;
		padding-left: 265px;
	}

	#like{
		margin-left: -610px; 
		float: left;
	}
	div.board{
		height: 75%;
		width: 100%;
		overflow: scroll;
	}
	/* #replylist{
	 	border: 1px solid black;
		border-collapse: collapse;
		padding: 5px 10px;
		text-align: center;
	} */
</style>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
</head>
<div class="board">
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
			<button>
				새 글쓰기
			</button>
		</table>
		
		<h2>구디SNS</h2>
		<hr/>
	
		
		<div>
			<span id="hash1"></span><span id="hash2"></span>
			<img src="타인프로필.gif" width="75" height="75" style="float: left;">

            <input type="button"value="${dto.user_id}" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >

  
       <table>
           <tr>
        
           	<!-- <button onclick=edit()>글 수정하기</button>-->
          
		           

           		<td>
            		<img src="게시물이미지.gif" alt="이미지 없음"width="600" height="450"style="margin-top:100px" />
        		<p> 작성날짜 : 
                           <input style="float: right; border: none; margin-left: -500px;"/>${flist.writedate}
	         				</p>
        		</td>
        		
           </tr>
           <!-- 좋아요 기능! -->
			<div class="like">
				<c:if test="${ loginId eq null }">
					추천 기능은 <button type="button" id="login"><b class="w3-text-blue">로그인</b></button> 후 사용 가능합니다.<br/>
					<span class="like_count"></span>					
				</c:if>
				<c:if test="${ loginId ne null }">
					<button id="likebtn"> 추천하기!
					<span class="like_count">${cnt}</span>
					</button> 
				</c:if>
			
			</div>
        </table>
		</div>

        
        <table>
        	<tr>
		        <td>
		            <p style="font-size: 15px; color: grey;" >
		            	댓글 모두 ${rcnt}개입니다.</p>
		        </td>
		    </tr>
	    </table>     
        <table>
		    <tr>
				<th>작성자</th>
				<th>내용</th>
				<th>삭제</th>
			</tr>	
			
			<c:forEach items="${list}" var="Reply2">
			<tr>
				<td>${Reply2.user_id}</td>
				<td>${Reply2.content}</td>
				<td>
					<c:if test="${Reply2.user_id eq  loginId }">
						<td><a href="rdel?board_idx=${dto.board_idx}&&cmt_idx=${Reply2.cmt_idx}">삭제</a></td>										 
					</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
	    <form action="rwrite" method="POST">
	        <td>
	        	<input type="hidden" id="user_id" name="user_id" value="${loginId}"/>
	        	<input type="hidden" id="board_idx" name="board_idx" value="${dto.board_idx}"/>
	            <b>${loginId}</b> : <input type="text" name="content" size="70" placeholder="댓글을 입력해주세요."> 
	            <button>댓글 작성</button>
	        </td>
	    </form>
		</div>
	<iframe src="navi.jsp" width="850px" height="1000px" scrolling="no" frameborder="0"></iframe>
	</body>
</div>
<script>
var a = "${dto.hashTag}";

var hash =a.split('#');

var sh = "#";
if(hash[1] != undefined){
document.getElementById("hash1").innerHTML = sh+hash[1];
}
if(hash[2] != undefined){
    document.getElementById("hash2").innerHTML = sh+hash[2];
}






function edit() {
	
var loginid = "${loginId}";
var dloginid = "${dto.user_id}";
        console.log(loginid+"/"+dloginid)
    if(loginid == dloginid){
        location.href='writeEdit?board_idx=${dto.board_idx}';
    }else{
        alert("당신은 수정할 수있는 권한이 없습니다.");
    }
};	

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
               success: function (params) {
            	   $(".like_count").html(params);	
            	   console.log(params);
               }
		});
	};
	likeCnt(); 
var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>