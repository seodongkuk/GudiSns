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
		margin-bottom: 25px;
	}

	#like{
		margin-left: -610px; 
		float: left;
	}
	div.board{
		height: 905px;
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
<body>
<div class="board">
		<table style="float: left; margin-top: 55px;">
			<tr>
				<td rowspan="2"><img src="기본프사.png" width="80" height="80"></td>
				<td><input type="button" value="${loginId}"
					style="background-color: white; font-weight: bold;"></td>
			</tr>
			<tr>
				<td><input type="button" value="로그아웃"
					onclick="location.href='logout'"></td>
			</tr>
			<!-- <button button id="WriteBtn" onclick="location.href='newWriting.jsp'">
				새 글쓰기
			</button> -->
		</table>
		
		<h2>구디SNS</h2>
		<hr/>
	
		
		<div>
			<img src="기본프사.png" width="75" height="75" style="float: left;">

            <input type="button"value="${dto.user_id}" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >

  
       <table>
           <tr>
           	 <button onclick=edit()>글 수정하기</button>
           	 <%-- <button onclick="location.href='del?board_idx=${dto.board_idx}'" style="margin-top: 10px;margin-bottom: -34px;">
           		게시글삭제</button> --%>
           		<td>
            		<img src="/GudiSns/photo/${dto.newFileName}" alt="이미지 없음"width="600" height="450"style="margin-top:100px" />
				<p>	<span id="hash1"></span><span id="hash2"></span> </p>
            			<p>${dto.content}</p>
            					
        		</td>

           </tr>
        		<td>
					<p style="margin-top: -75px;">${dto.writedate}</p>
                </td>
           <!-- 좋아요 기능! -->
			<div class="like">
				<c:if test="${ loginId eq null }">
					추천 기능은 <button onclick="location.href='./'"><b class="w3-text-blue">로그인</b></button> 후 사용 가능합니다.<br/>
					<img src="./img/unlike.png" style="width:30px; margin-top:10px; margin-left:10px;padding-bottom:10px;"/>
					<span id="like_cnt" class="like_count">${cnt}</span>					
				</c:if>
				<c:if test="${ loginId ne null }">
					<img id="likebtn" src="./img/unlike.png" style="width:30px; margin-top:10px; margin-left:10px; padding-bottom:10px;"/>
					<span id="like_cnt" class="like_count">${cnt}</span>
				</c:if>
			
			</div>
        </table>
		</div>
		<!-- 댓글 개수 불러오기 -->
        <table>
        	<tr>
		        <td>
		            <p style="font-size: 15px; color: grey;" >
		            	댓글 모두 ${rcnt}개입니다.</p>
		        </td>
		    </tr>
	    </table>     
   
	    <!-- 댓글  수정, 삭제 폼 -->
        <table>				
			<c:forEach items="${list}" var="Reply2" varStatus="status">
				<input type="hidden" id="board_idx${status.index}" name="brd" value="${dto.board_idx}"/>  
				<input type="hidden" id="cmt_idx${status.index}" name="cmt" value="${Reply2.cmt_idx}"/>   
				<input type="hidden" name="content" value="${Reply2.content}"/>       
			<!-- 댓글 보이는 창! -->
			<tr>
			<!-- <td>${status.index}</td>-->
				<td>${Reply2.user_id}</td>
				<td>
					<span id="changeMsg${status.index}">${Reply2.content}</span>
					<c:if test="${Reply2.user_id eq loginId }">
						<input style="display:none;" type="text" class="edittext" id="content${status.index}" value="${Reply2.content}"/>
					</c:if>
				</td>
				<td>
					<c:if test="${Reply2.user_id eq loginId }">
					
						<button style="display:none;" id="redit${status.index}" class="complete">수정적용</button>
						<button id="upclick${status.index}" class="update">수정하기</button>
						<button class="delete" onclick="location.href='rdel?board_idx=${dto.board_idx}&&cmt_idx=${Reply2.cmt_idx}'">삭제</button>										 
					
					</c:if>
				</td>
			</tr>
			
			</c:forEach>
		</table>
		<!-- 댓글 작성 폼 -->
		<c:if test="${loginId eq null }">
		    댓글을 입력하려면<button onclick="location.href='./'"><b>로그인</b></button> 해주세요
		</c:if>
	    <c:if test="${loginId ne null}">
		    <form action="rwrite" method="POST">
		        <td>
		        	<input type="hidden" id="user_id" name="user_id" value="${loginId}"/>
		        	<input type="hidden" id="board_idx" name="board_idx" value="${dto.board_idx}"/>
		            <b>${loginId}</b> : <input type="text" name="content" size="70" placeholder="댓글을 입력해주세요."> 
		            <button>댓글 작성</button>
		        </td>
		    </form>
		</c:if>
	</div>
	<iframe src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>
</body>
<script>

//수정시도버튼
$("body").on("click", "[id^=upclick]", function(event) {
	console.log("선택한 값"+this.id);
	var number = this.id.substring(7);
	//console.log($('#content'+number));
	//var $button = $('.update'+number);
	//console.log($button);
	$(".edittext").hide();
	$('#content'+number).show();
	$("#redit"+number).show();
	$(".delete").hide();
	$(".update").hide();
	
});
//수정완료버튼
$("body").on("click", "[id^=redit]", function(event) {
	console.log("댓글 수정 요청 ");
	console.log( this.id);
	var number = this.id.substring(5,1000);
	console.log(number);
	
	var $content = $('#content'+number).val();
	var idx = $("#cmt_idx"+number).val();
	console.log(idx+"/"+$content);
	
	var $changeMsg = $('#changeMsg'+number);
	
	var params = {};
	params.content = $content;
	params.idx = idx;
	
	$.ajax({
		url: "redit",
          type: "post",
          data: params,
          dataType:'JSON',
          success: function (data) {
        	console.log(data);
       	   	console.log(data.edit);
       	 	$changeMsg.html(data.edit);
       	 	alert("댓글을 수정하였습니다.");
       	 	
       	  }
	});
	$(".complete").hide();
	$(".edittext").hide();
	$(".update").show();
	$(".delete").show();
});


var a = "${dto.hashTag}";

var hash =a.split('#');

var sh = "#";
if(hash[1] != undefined){
document.getElementById("hash1").innerHTML = sh+hash[1];
}
if(hash[2] != undefined){
    document.getElementById("hash2").innerHTML = sh+hash[2];
}





var loginid = "${loginId}";
var dloginid = "${dto.user_id}";

function edit() {
	
        console.log(loginid+"/"+dloginid)
    if(loginid == dloginid){
        location.href='writeEdit?board_idx=${dto.board_idx}';
    }else{
        alert("당신은 수정할 수있는 권한이 없습니다.");
    }
};




likeCnt(); 
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
              success: function (data) {

           	 	console.log(data.result);
           	 	if(data.result==0){
           	 		$("#likebtn").attr("src","./img/like.png");
           	 	likeCnt();
           	 	}else if(data.result==1){
           	 		$("#likebtn").attr("src","./img/dislike.png");
           	 	likeCnt();
           	 	}
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
            	
               }
		});
	};
	
	
var msg = "${msg}";
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>