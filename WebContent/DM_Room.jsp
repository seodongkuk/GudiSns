<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>DM 채팅창 페이지</title>
        <style>
            /* #dmBox{
                margin-left: 10%;
                border:1px solid black;
                width: 800px;
                height: 1000px;
            } */
            #dmRoom{
                background-color: lightgray;
                margin-left: 35%;
                border:1px solid black;
                border-radius: 5px;
                width: 550px;
                height: 70%;
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
    </head>
    <body>
        <!--Alt+Shift+B 를 누르면 실행-->
        <h2 style="text-align: center;">USER1 DM방 </h2>
        
            <div id='dmRoom'>
            	<p id="noChat">채팅을 시작해주세요.</p>
            	<c:forEach items="${list}" var="msg">
            	<!-- 내 메시지가 가장 밑으로 오게 -->
            	<c:if test="${msg.send_id == sessionScope.loginId}">
                <div id='myMsgBox'>
                    <p style="position: absolute; margin-left: 1%; top: auto; bottom: 5%;  padding-left: 3px;">
                        ${msg.sendtime}
                    </p>
	                    <c:if test="${msg.read_state == 'false'}">
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
            	<c:if test="${msg.send_id != sessionScope.loginId}">
            	<div id='otherMsgBox'>
                    <span>
                        <img src="C:\Users\HSK\Pictures\오라클홈폴더.png"/>
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
            <div style="margin-top: 10px;">
                <textarea name="msg" id="inputMsg" style="margin-left: 35%; width: 30%; resize: none;"></textarea>
                <input type="submit" value="전송" id="msg-send" style="float: right; width: 70px; height: 35px; margin-right: 31%;"/>
            </div>
<!--             <html:include id="footer" src="navi.html" style="border: none;bottom: 0; left: 550; position: fixed; z-index: 5; top: 850; width: 100%;"/>
 -->		<jsp:include page="navi.jsp"/>
    </body>
    <script>
    	//채팅방 들어올때 무조건 스크롤 맨밑으로...
    	$("#dmRoom").scrollTop($("#dmRoom")[0].scrollHeight);
    	//만약 대화 내용이 있다면...
    	var list = "${list}";
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
    	};
    	webSocket.onmessage = function(e){
    		onMessage(e);
    	};
    	
    	
    	function onMessage(e){
    		//가져온 채팅을 담는다
    		var chatMsg = event.data;

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
    		var $chat = $("<div id='otherMsgBox'><span><img src='C:\Users\HSK\Pictures\오라클홈폴더.png'/></span>"
                +"<div id='userId'>"+chatId[0] +"</div>"
                +"<p style='position: absolute; margin-left: 70%; top: auto; bottom: 5%;'>"
                   +"21-03-10 PM 03:50"
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
    		
    	}
    	
    	function onError(e){
    		alert(e.data);
    	}
    	
    	function send(){
    		var sessionId =  "${sessionScope.loginId}";
    		var chatMsg = inputMsg.value;
    		var patId = getParameterByName('id');
    		
    		//아이디 구분..?
    		//채팅 내용이 없으면 보내지 않음
    		if(chatMsg == ''){
    			return;
    		}
    		//채팅 내용을 보낸다
    		//내 채팅이 보여질 부분
    		var $chat = $("<div id='myMsgBox'>"+
                    "<p style='position: absolute; margin-left: 1%; top: auto; bottom: 5%;  padding-left: 3px;'>"
                    +"21-03-10 PM 03:50"
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
    		$('#dmRoom').append($chat);
    		//만약 DM방 초대받은 사람이면..(FROM 초대받은 사람, TO 생성자)
    		if(patId == sessionId){
    			console.log(patId);
    			//DB에 저장하기 위해 ajax 방식으로 호출
        		$(function(){
    				$.ajax({
    					type: "POST"
    					,url: "./webChatServer"
    					,data:{
    						fromId: encodeURIComponent(sessionId)
    						,toId: encodeURIComponent(getParameterByName('create'))
    						,content: encodeURIComponent(chatMsg)
    					}
    					,success: function(result){
    						
    					}
    				});
    			});
    			webSocket.send(sessionId+":"+chatMsg+":"+getParameterByName('create'));
    		//만약 DM방 만든 사람이면..(FROM 생성자, TO 초대받은 사람)
    		}else{
    			//DB에 저장하기 위해 ajax 방식 호출
        		$(function(){
    				$.ajax({
    					type: "POST"
    					,url: "./webChatServer"
    					,data:{
    						fromId: encodeURIComponent(sessionId)
    						,toId: encodeURIComponent(patId)
    						,content: encodeURIComponent(chatMsg)
    					}
    					,success: function(result){
    						
    					}
    				});
    			});
    			webSocket.send(sessionId+":"+chatMsg+":"+patId);
    		}
    		

    		
    		
    		if(chatMsg != null){
    			$('#noChat').css('display','none');
    		}
    		//채팅 내용을 보내고 입력값을 초기화 함
    		inputMsg.value = "";
    		$("#dmRoom").scrollTop($("#dmRoom")[0].scrollHeight);
    	}
    </script>
    
    <!-- 채팅 전송 기능.. 엔터를 쳐도 보내고 클릭해도 보낼 수 있다. -->
    <script type="text/javascript">
	$(function(){
		$('#inputMsg').keydown(function(key){
			if(key.keyCode == 13){
				$('#inputMsg').focus();
				send();
			}
		});
		$('#msg-send').click(function(){
			send();
		});
		
	});
	
	function getParameterByName(name) {
        name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
        var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                results = regex.exec(location.search);
        return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
    }


</script>
</html>