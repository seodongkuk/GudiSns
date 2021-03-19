<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>DM 리스트 페이지</title>
        <style>
	        body {
					width: 850px;
					height: auto;
					margin-left: 25%;
					padding: 5px;
					border: 1px solid black;
					overflow: scroll;
				}
				
            /* #dmBox{
                margin-left: 10%;
                border:1px solid black;
                width: 800px;
                height: 1000px;
            } */
            #dmRoom{
                margin-left: 20%;
                border:1px solid lightgray;
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
        </style>
        
    	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    </head>
    <body>
        <!--Alt+Shift+B ë¥¼ ëë¥´ë©´ ì¤í-->
        <h2 style="text-align: center;">DM 리스트  </h2>
        
            <p style="text-align: center;">채팅방</p>
            
            <p id="noDm" style="text-align: center;">현재 DM이 없습니다...</p>
			<c:forEach items="${list}" var="dm">
            <div id='dmRoom'>
                <span>
                    <img src="C:\Users\HSK\Pictures\ì¤ë¼í´íí´ë.png"/>
                </span>
                <div id="userId">
                    <c:if test="${dm.send_id != sessionScope.loginId}">${dm.send_id}</c:if>
                    <c:if test="${dm.recieve_id != sessionScope.loginId}">${dm.recieve_id}</c:if>
                </div>
                <div style="margin-left: 10px;">
                   	${dm.content}
                </div>
                <div style="margin-left: 70%; margin-top: -15%;">
                   ${dm.sendtime}
                </div>
                <div style="margin-left: 85%; margin-top: 25%;">
                    <button onclick="location.href='DM_list?idx=${dm.dm_idx}'">
                        	입장
                    </button>
                </div>
            </div>
            </c:forEach>
            <iframe style="margin-top: 10px;"
	 				src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0" ></iframe>
    </body>
    <script>
    	var success = "${success}";
    	console.log(success);
		if(success){
			$('#noDm').css('display','none');
		}else{
			$('#noDm').css('display','block');
		}
    </script>
</html>