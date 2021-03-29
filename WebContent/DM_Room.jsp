<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>DM 채팅창 페이지</title>
        <style>
        	body {
				width: 850px;
				height: 850px;
				margin-left: 25%;
				padding: 5px;
				border: 1px solid black;
				overflow: hidden;
			}
            /* #dmBox{
                margin-left: 10%;
                border:1px solid black;
                width: 800px;
                height: 1000px;
            } */
            #dmRoom{
                background-color: lightgray;
                margin-left: 135px;
                border:1px solid black;
                border-radius: 5px;
                width: 550px;
                height: 600px;
                padding: 10px;
                margin-top: 70px;
                overflow: scroll;
            }
            img{
                margin: 10px;
                width: 50px;
                height: 50px;
            }
            #userId{
                position: absolute;
                margin-left: 15%;
                margin-top: -13%;
                font-weight: 600;
                font-size: large;
            }
            #otherMsgBox{
                position: relative;
                background-color: white;
                border-radius: 10px;
                width: 90%;
                height: auto;
                margin-bottom: 10px;
            }
            #myMsgBox{
                position: relative;
                border-radius: 10px;
                background-color: white;
                width: 90%;
                height: auto;
                margin-bottom: 10px;
                margin-top: 10px;
                margin-left: 10%;
                padding-top: 5px;
            }
            .myChat{
                margin-left: 40%; 
                width: 200px;
                word-break: break-all;
                margin-bottom: 15px;
            }
            .otherChat{
                margin-left: 5%; 
                width: 200px;
                word-break: break-all;
                margin-bottom: 15px;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery"></script>
        <!-- 출처: https://nowonbun.tistory.com/566 [명월 일지] 날짜 관련 라이브러리 함수 -->
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        
    </head>
    <body>
        <!--Alt+Shift+B 를 누르면 실행-->
        <h2 style="text-align: center;">DM방 </h2>
            <div id='dmRoom'>
            	<p id="noChat">채팅을 시작해주세요.</p>
            	<c:forEach items="${list}" var="msg">
            	<!-- 내 메시지가 가장 밑으로 오게 -->
            	<c:if test="${msg.send_id == sessionScope.loginId}">
                <div id='myMsgBox'>
                    <p style="position: absolute; margin-left: 1%; top: auto; bottom: 5%;  padding-left: 3px;">
                        ${msg.sendtime}
                    </p>
                    	<!-- 만약 상대방이 읽지 않았다면 읽지않은거니 표시 -->
	                    <c:if test="${not msg.read_state || msg.read_state == null}">
	                    <span id="beforeRead" style="margin-left: 70%; margin-top: 5%; bottom: auto;">
	                        <span style="color: blue;">new </span>읽지 않음
	                    </span>
	                    </c:if>
                    <div class="myChat">
                        <p style="padding-bottom: 10px;">
	                        ${msg.content}
                        </p>
                    </div>
                </div>
                </c:if>
                <!-- 상대방 메시지 -->
            	<c:if test="${msg.send_id != sessionScope.loginId}">
            	<div id='otherMsgBox'>
                    <span>
                        <img src="기본프사.png"/>
                    </span>
                    <div id="userId">
                        <c:if test="${msg.send_id != sessionScope.loginId}">${msg.send_id}</c:if>
                    </div>
                    <p style="position: absolute; margin-left: 70%; top: auto; bottom: 5%;">
                        ${msg.sendtime}
                    </p>
                    <div class="otherChat">
                        <p style="padding-bottom: 10px;">
                        ${msg.content}
                        </p>
                    </div>
                </div>
                </c:if>
            	</c:forEach>
            </div>
            <!-- 채팅 보내는 역할 -->
            <div style="margin-top: 10px;">
                <textarea maxlength="200" name="msg" id="inputMsg" style="width: 30%; margin-left: 30%; resize: none;"></textarea>
            </div>
                <input type="submit" value="전송" id="msg-send" 
                	style="width: 50px; height: 30px; margin-left: 520px; margin-top: -33px;"/>
     		<!-- <html:include id="footer" src="navi.html" style="border: none;bottom: 0; left: 550; position: fixed; z-index: 5; top: 850; width: 100%;"/> -->		
	 			<iframe 
	 				src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0" ></iframe>
    </body>
    <script type="text/javascript">

	</script>
    <script>
    

	   // Ajax 통신중인지를 확인하는 전역변수 설정
    	//채팅방 들어올때 무조건 스크롤 맨밑으로...
    	$("#dmRoom").scrollTop($("#dmRoom")[0].scrollHeight);
    	//만약 대화 내용이 있다면...
    	var list = "${list}";
    	var size = 0;

    	if(list != ""){
    		//채팅 시작 문구를 숨긴다.
			$('#noChat').css('display','none');
		}
        
        var inputMsg = document.getElementById('inputMsg');
        
    	// 로컬에서 테스트할 때 사용하는 URL입니다.
     	var webSocket = new WebSocket('ws://localhost/GudiSns/webChatServer');
    	
    	//웹소켓이 연결되었을 때 실행되는 얘들?
    	webSocket.onerror = function(e){
    		onError(e);
    	};
    	webSocket.onopen = function(e){
    		onOpen(e);
    		console.log(e);
    	};
    	webSocket.onmessage = function(e){
    		onMessage(e);
    	};
    	
    	function onMessage(e){
    		//가져온 채팅을 담는다
    		var chatMsg = event.data;
    		
    		//날짜 관련 함수(moment를 이용해 포맷 맞출것임)
    		var today = new Date();
    		
    		var sessionId = "${sessionScope.loginId}";
    		
    		var chatId = chatMsg.split(':');
    		var fromId = chatId[0];
    		var toId = "";
    		var chkId = getParameterByName('id');
    		var content = chatId[1];
    		
    		if(chkId == fromId){
    			toId = fromId;
    			console.log('아이디와 파라메터 아이디랑 다릅니다.'+toId);
    		}else{
    			toId = chkId;
    			console.log('아이디 파라메터 비교'+toId);
    		}
			//자기 채팅방이면 (파라메터 비교시 동일)
			//자기 채팅방이 아니면..()
    		


    		//채팅내용을 가져와서 $chat에 담는다.(박스에다가...)
    		//상대방한테 보여질 내용
    		var $chat = $("<div id='otherMsgBox'><span><img src='기본프사.png'/></span>"
                +"<div id='userId'>"+chatId[0] +"</div>"
                +"<p style='position: absolute; margin-left: 70%; top: auto; bottom: 5%;'>"
                   +moment(today).format('YY/MM/DD HH:mm')
                +"</p>"
                +"<div class='otherChat'>"
                    +"<p style='padding-bottom: 10px;'>"
                       + chatId[1] +"</p></div></div>");
    		
			//채팅 내용이 있다면...
    		if(chatMsg != null){
    			$('#noChat').css('display','none');
    		}
    		
    		//메인 내용에 추가한다.
    			$('#dmRoom').append($chat);

    		

    		
    		$("#dmRoom").scrollTop($("#dmRoom")[0].scrollHeight);
    	}
    		
    	function onOpen(e){
    		console.log(msg);
    	}
    	
    	function onError(e){
    		alert(e.data);
    	}
    	
    	function send(){
    		
    		var timeout = null;
    		var sessionId =  "${sessionScope.loginId}";
    		var chatMsg = inputMsg.value;
    		var patId = getParameterByName('id');
        	var idx = getParameterByName('idx');
        	
    		
    		//날짜 관련 함수(moment를 이용해 포맷 맞출것임)
    		var today = new Date();
    		
    		//아이디 구분..?
    		//채팅 내용이 없으면 보내지 않음
    		if(chatMsg == ''){
    			return;
    		}
    		//채팅 내용을 보낸다
    		//내 채팅이 보여질 부분
/*     		var $chat = $("<div id='myMsgBox'>"+
                    "<p style='position: absolute; margin-left: 1%; top: auto; bottom: 5%;  padding-left: 3px;'>"
                    +moment(today).format('YY/MM/DD HH:mm')
                +"</p>"
                +"<span id='beforeRead' style='margin-left: 70%; margin-top: auto; margin-bottom: 10%;'>"
                +"<span style='color: blue;'>new </span>읽지 않음"
                +"</span>"
                +"<div class='myChat'>"
                +"<p style='padding-bottom: 10px;'>"
                +chatMsg
                +"</p>"
                +"</div>"
                +"</div>");
    		$('#dmRoom').append($chat); */
    		//만약 DM방 초대받은 사람이면..(FROM 초대받은 사람, TO 생성자)
    		if(patId == sessionId){
    			//webSocket에 해당 내용을 전달한다.
    			webSocket.send(sessionId+":"+chatMsg+":"+getParameterByName('create')+":"+idx);
    			//DB에 저장하기 위해 ajax 방식으로 호출
            	setInterval(function() {
            		location.reload();
            	},100);
    		//메시지를 보내면 0.1초마다 페이지 새로고침
    		}else{
    			//webSocket에 해당 내용을 전달한다.
    			webSocket.send(sessionId+":"+chatMsg+":"+patId+":"+idx);
/*         		$(function(){
    				$.ajax({
    					type: "POST"
    					,url: "DM_Room?id="+patId+"&&create="+getParameterByName('create')+"&&idx="+idx
    					,data:{
    						fromId: encodeURIComponent(patId)
    						,toId: encodeURIComponent(getParameterByName('create'))
    						,content: encodeURIComponent(chatMsg)
    						,idx: encodeURIComponent(idx)
    					}
    					,success: function(result){
    						
    					}
    				});
    			}); */
    			
            	setInterval(function() {
            		location.reload();
            	},100);
    		}
    		


    		//채팅내용이 있으면 채팅 없을 경우의 내용 숨김
    		if(chatMsg != null){
    			$('#noChat').css('display','none');
    		}
    		//채팅 내용을 보내고 입력값을 초기화 함
    		inputMsg.value = "";
    		//항상 채팅창이 아래로 내려오게...
    		$("#dmRoom").scrollTop($("#dmRoom")[0].scrollHeight);
    	}
    </script>
    
    <!-- 채팅 전송 기능.. 클릭해서 보낼 수 있다. -->
    <script type="text/javascript">
	$(function(){
		$('#msg-send').click(function(){
			$('#inputMsg').focus();
			send();
		});
		
	});
	
	//URL 파라메터를 가져오는 역할
	function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }


</script>
</html>