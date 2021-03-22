<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>알림 리스트 </title>
        <style>
        	body{
        		overflow: hidden;
        	}
            #alarmBox{
                position: relative;
                margin-left: 36%;
                border:1px solid black;
                width: 500px;
                height: 200px;
                padding: 10px;
                margin-top: 10px;
            }
            img{
                margin: 10px;
                width: 50px;
                height: 50px;
            }
            #userId{
                display: inline-block;
                margin-left: 0%;
                margin-top: 5%;
                vertical-align: top;
            }
            .content{
                margin-left: 10px;
                margin-top: 70px;
            }
            #alarmSet{
                display: none;
                background-color: gray;
                position: absolute;
                border: solid 1px black;
                width: 150px;
                height: 145px;
                text-align: left;
                margin-left: 680px;
                margin-top: 3%;
            }
            #container{
            	height: 1100px;
            	overflow: auto;
            	
            }
            #noAlarm{
            	margin-left: 1000px;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>      
    </head>
    <body>
    	<div id="container">
    	<!-- 기본값은 모두 체크 -->
        <div id="alarmSet">
            
            <form action="alarmSet" method="get" name="alarm">
            <!-- 현재 유저의 셋팅값을 모두 가져온다.. -->
         	<c:forEach items="${setting}" var="settings">
        		<c:choose>
        	    <c:when test="${settings.alarm_kind == '댓글알림' && settings.alarm_state == '1'}">
		            댓글알림<input type="checkbox" name="alarmChk" value="댓글알림" checked/>
		            <hr/>
	           </c:when>
			   <c:when test="${settings.alarm_kind == '댓글알림' && settings.alarm_state == '0'}">
		            댓글알림<input type="checkbox" name="alarmChk" value="댓글알림"/>
		            <hr/>
			   </c:when>
	            </c:choose>

	            <c:choose>
	            <c:when test="${settings.alarm_kind == '게시글알림' && settings.alarm_state == '1'}">
	            게시글알림<input type="checkbox" name="alarmChk" value="게시글알림" checked/>
	            <hr/>
	            </c:when>
	            <c:when test="${settings.alarm_kind == '게시글알림' && settings.alarm_state == '0'}">
	            게시글알림<input type="checkbox" name="alarmChk" value="게시글알림"/>
	            <hr/>
	            </c:when>
	            </c:choose>
	            
	           <c:choose>
	            <c:when test="${settings.alarm_kind == 'DM알림' && settings.alarm_state == '1'}">
	            DM알림<input type="checkbox" name="alarmChk" value="DM알림" checked/>
	            <hr/>
	            </c:when>
	            <c:when test="${settings.alarm_kind == 'DM알림' && settings.alarm_state == '0'}">
	            DM알림<input type="checkbox" name="alarmChk" value="DM알림"/>
	            <hr/>
	            </c:when>
	            </c:choose>
	            
	            <c:choose>
	            <c:when test="${settings.alarm_kind == '친구요청알림' && settings.alarm_state == '1'}">
	            친구요청알림<input type="checkbox" name="alarmChk" value="친구요청알림" checked/>
	            <hr/>
	            </c:when>
	            <c:when test="${settings.alarm_kind == '친구요청알림' && settings.alarm_state == '0'}">
	            친구요청알림<input type="checkbox" name="alarmChk" value="친구요청알림"/>
	            <hr/>
	            </c:when>
	            </c:choose>
	          </c:forEach>
	          </form>
        </div>
        <!--Alt+Shift+B 를 누르면 실행-->
        <h2 style="text-align: center;">알람 리스트 </h2>
            <button id="alarmSetting" style="margin-left: 35%;">수신설정</button>
            <button onclick="location.href='alarmAllChk?myId=${sessionScope.loginId}'" style="margin-left: 17.2%;">모든 알람 지우기</button>
            <!-- 현재 알림 리스트를 불러옴 -->
            <p id="noAlarm">현재 알림이 존재하지 않습니다...</p>
            <c:forEach items="${list}" var="alarm">
            	<!-- 만약 알림 내용이 친구요청알림 이면 해당 리스트 꺼내기 -->
            	<c:if test="${alarm.alarm_content == '친구요청알림'}">
		            <div id='alarmBox'>
		                <div class="content">
		                    ${alarm.user_id} 님으로부터 친구 요청이 들어왔습니다.
		                </div>
		                <div style="margin-left: 70%; margin-top: -15%;">
		                    ${alarm.alarm_reg_date}
		                </div>
		                <div style="margin-left: 65%; margin-top: 25%;float: left;">
		                    <button onclick="location.href='buddyAdd?myId=${sessionScope.loginId}&&budId=${alarm.user_id}'"> 수락하기 </button>
		                    <button onclick="location.href='buddyIgnore?myId=${sessionScope.loginId}&&budId=${alarm.user_id}'"> 무시하기 </button>
		                </div>
		            </div>
            	</c:if>
            	<!-- 만약 알림 내용이 게시글알림 이면 해당 리스트 꺼내기 -->
            	<c:if test="${alarm.alarm_content == '게시글알림'}">
            		<input type="hidden"  id="likeIdx" value="${alarm.alarm_idx}"/>
	            	<div id='alarmBox'>
		                <div class="content">
		                     ${alarm.user_id}님이 당신의 게시물을 추천 하였습니다.
		                </div>
		                <div style="margin-left: 70%; margin-top: -15%;">
		                    ${alarm.alarm_reg_date}
		                </div>
		                <button style="margin-top: 150px; margin-left: -10px;" onclick=delChk(likeIdx.value)>삭제</button>
            		</div>
            	</c:if>
            	<c:if test="${alarm.alarm_content == 'DM알림'}">
            	    <input type="hidden" id="dmIdx" value="${alarm.alarm_idx}"/>
	            	<div id='alarmBox'>
		                <div class="content">
		                    ${alarm.user_id}님으로부터 DM이 왔습니다
		                </div>
		                <div style="margin-left: 70%; margin-top: -15%;">
		                    ${alarm.alarm_reg_date}
		                </div>
		                <button style="margin-top: 150px; margin-left: -10px;" onclick=delDmChk(dmIdx.value)>삭제</button>
            		</div>
            	</c:if>
            	<c:if test="${alarm.alarm_content == '댓글알림'}">
            	    <input type="hidden" id="commentIdx" value="${alarm.alarm_idx}"/>
	            	<div id='alarmBox'>
		                <div class="content">
		                    ${alarm.user_id}님이 당신의 게시물에 댓글을 달았습니다.
		                </div>
		                <div style="margin-left: 70%; margin-top: -15%;">
		                    ${alarm.alarm_reg_date}
		                </div>
		                <button style="margin-top: 150px; margin-left: -10px;" onclick=delCommentChk(commentIdx.value)>삭제</button>
            		</div>
            	</c:if>
            </c:forEach>
            </div>
            <iframe src="navi.jsp" style="position:fixed; margin-left: 700px;" width="850px" height="80px" scrolling="no" frameborder="0"></iframe>

    </body>
</html>
<script>

	var id = "${sessionScope.loginId}";
	
	var list = "${list}";
	if(document.getElementById('alarmBox')){
		//채팅 시작 문구를 숨긴다.
		$('#noAlarm').css('display','none');
	} 

	//만약 수신설정 화면이 없을때 누르면 보이게하고
	//아니라면 사라지게 한다
	$("#alarmSetting").click(function(){
		if($("#alarmSet").css("display") == 'none'){
			$("#alarmSet").css({display:'block'});
		}
		else{
			$("#alarmSet").css({display:'none'});
			
			var alarm = document.alarm;
			alarm.submit();

		}
	});


	//자신의 게시글 좋아요 알림 삭제
	function delChk(idx){
		var chk = confirm('정말 삭제하시겠습니까?');
		if(chk){
			$.ajax({
				type:'get'
				,url:'likeAlarmDel?myId='+id+'&&idx='+idx
				,dataType:'JSON'
				,success:function(data){
					console.log('삭제완료');
				},error:function(e){
					console.log(e);
				}
			});
			location.reload();
		}
	}
	
	//자신에게 온 DM 알림 삭제
	function delDmChk(idx){
		var chk = confirm('정말 삭제하시겠습니까?');
		if(chk){
			$.ajax({
				type:'get'
				,url:'dmAlarmDel?myId='+id+'&&idx='+idx
				,dataType:'JSON'
				,success:function(data){
					console.log('삭제완료');
				},error:function(e){
					console.log(e);
				}
			});
			location.reload();
		}
	}
	
	//자신의 게시물 댓글 알림 삭제
	function delCommentChk(idx){
		var chk = confirm('정말 삭제하시겠습니까?');
		if(chk){
			$.ajax({
				type:'get'
				,url:'commentAlarmDel?myId='+id+'&&idx='+idx
				,dataType:'JSON'
				,success:function(data){
					console.log('삭제완료');
				},error:function(e){
					console.log(e);
				}
			});
			location.reload();
		}
	}
</script>