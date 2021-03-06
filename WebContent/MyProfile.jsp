<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>내 프로필</title>
        <style>
        	body{
                width: 850px;
                height: 95%;
                margin-left:25%;
                padding: 5px;
                border: 1px solid black;
                overflow: hidden;
            }
            #header{
                position: relative;
                width: 800px;
                height: 460px;
                margin-left: 20px;
                left: 0; 
                right: 0; 
                top: 0;
                overflow:scroll;
            }
            #dmBox{
                /* position: relative; */
                border:1px solid black;
                width: 100%;
                height: 42%;
                /* overflow: scroll; */
                /*중앙 정렬*/
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
                margin: auto;
            }
            div.mypf{
            	margin-left: 47px;
            }
            .friendList{
                /* position: absolute;
                margin: auto;
                top: -435px;
    			left: -90px; */
                bottom: 0;
                margin-left: 50%;
                margin-top: -352px;
                border:1px solid black;
                width: 350px;
                height: 329px;
            }
            img{
                margin-left: 20px;
                width: 50px;
                height: 50px;
            }
            .friendId{
                display: inline-block;
                vertical-align: top;
            }

            .friendList > p{
                margin-left: 10px;
            }
            .member{
                display: flex;
			    width: 150px;
			    height: 75px;
			    margin: 8px 20px;
			    padding-left: 30px;
			    text-align: center;
            }
            .userId{
                margin-left:25px;
                margin-top: 50px;
                margin-bottom: 10px;
                font-weight: 600;
                font-size: 20px;
            }
            .friendList button{
                margin-top: 10px;
                margin-right: auto;
            }
            /* .profileBoard{
                width: 300px;
                word-break: break-all;
            } */
            #hashTag{
                color: grey;
            }
            #moreShow{
                margin-left: 251px;
                margin-top: -3px;
                float: left;
            }
            a{
	           	margin-left: 10px;
	           	padding-top: 10px;            
            }
            a:link{
            	color:black;
            	text-decoration: none;
            }
            a:visited{
            	color:black;
            	text-decoration: none;
            }
            a:hover{
            	color:blue;
            	text-decoration: underline;
            }
            td{
            	text-align: center;
            }
            div.paging{
            	margin-left: 116px;
    			margin-top: 28px;
            }
            #page{
            	font-weight: 600;
            	color: purple;
            	margin: 10px 5px 5px 10px;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>        
        <link rel="stylesheet" sype="text/css" href="./css/Likebtn.css">
    </head>
    <body>
        <h2 style="text-align: center;">내 프로필 </h2>
        
        <div id="dmBox">
        	<div class="mypf">
	            <div class="userId" name="myId">
	                ${sessionScope.loginId}
	            </div>
	            <div>
	                <img style="width: 150px; height: 150px;" src="기본프사.png"/>
	            </div>
	                <input
	                	type="button" class="member" value="회원정보 수정" onclick="location.href='info_pass.jsp'">
	                <input style="padding-left: 40px;" 
	                	type="button" class="member" value="회원 탈퇴" onclick="location.href='user_out.jsp'">
	                
	                  
			          <input type="hidden" name="id" value="${sessionScope.id}"/>
        	</div>

                <div class="friendList">
                    <p>친구목록</p>
                    <hr style="border:solid 1px black;"/>
                    <c:forEach items="${budlist}" var="myBud">
                    	<table>
                    		<tr class="friendId">
                    			<!-- 경로설정 -->
                    			<td style="padding-top: 10px"><img src="기본프사.png"/><br/> 
                    			<td style="padding-top: 24px"><a href="otherProfile?id=${myBud.bud_id}">
                    				${myBud.bud_id}</a><br/>
                    			</td>
                    		</tr>
                    	</table>
                    </c:forEach>
                    <div class="paging">
                    	<c:if test="${currPage == 1}">이전</c:if>
                    	<c:if test="${currPage > 1}">
	                        <a href="MyProfile?page=${currPage - 1}">이전</a>
                    	</c:if>
                        <span id="page">${currPage}</span>
                        <c:if test="${currPage == maxPage}">다음</c:if>
                        <c:if test="${currPage < maxPage}">
                        	<a href="MyProfile?page=${currPage + 1}">다음</a>
                        </c:if>
                    </div>
                </div>
        </div>
        <hr/>
        <div id="header">
           	<c:forEach items="${list}" var="myL">
           	
			<input type="hidden" name="board_idx" value="${myL.board_idx}"/>
            <table style="margin: auto; padding: 15px;">
            	<tr>
                     <td>
                         <button onclick="location.href='del?board_idx=${myL.board_idx}'" style="margin-top: 6px;margin-left: 475px;">
           							게시글삭제</button>
           			<td>
           		</tr>
                <tr>
                    <!-- <td>
                        <img src="게시물이미지.gif" style="margin-top:10px; width:400px; height:300px;"/>
                    </td> -->
                    <c:if test="${myL.newFileName ne null }">
                        <td>
                            <img src="/GudiSns/photo/${myL.newFileName}" style="width:540px; height:300px;" />
                        </td>
                    </c:if>
                    <c:if test="${myL.newFileName eq null }">
                         <td>
                            <img src="/GudiSns/photo/${myL.newFileName}" style="width:540px; height:0px; visibility: hidden;" />
                        </td>
                   </c:if>
                 </tr>
                 
                <tr>
                    <td class="profileBoard">
                        	<p>${myL.hashTag}</p>
                            <p>${myL.content}</p>
                            <button id="moreShow" onclick="location.href='detail?board_idx=${myL.board_idx}'">더보기</button>
                     </td> 
                     <td>
							<p style="margin-top: -17px;margin-left: -85px;">${myL.writedate}</p>
                        </td>
                 </tr>
                 <br/>
 				<tr>
                    <td style="text-align: left; padding-left: 188px; font-weight: 600;">댓글 개수는 ${myL.rcnt} 개 입니다.</td>
               	</tr>
          </table>
          
        	</c:forEach>
        	<p id="noBoard" style="text-align: center;">현재 게시글이 없습니다...</p>
        </div>
        <iframe src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>

    </body>
     <script>   
  
 	
     var a = "${myL.hashTag}";

     var hash =a.split('#');

     var sh = "#";
     if(hash[1] != undefined){
     document.getElementById("hash1").innerHTML = sh+hash[1];
     }
     if(hash[2] != undefined){
         document.getElementById("hash2").innerHTML = sh+hash[2];
     }
     
     
         	$(function(){
		stickyFooter();

		$(window).scroll(stickyFooter).resize(stickyFooter);
	});


	function stickyFooter(){
		document_height = $(document).height(); // 문서 전체 높이
		document_scrollTop = $(document).scrollTop(); // 문서 전체 높이 중 스크롤 위치
		window_height = $(window).height(); // 창 높이
		footer_height = $("footer").height();

		gap = document_height - footer_height - window_height; 
		bottom = document_scrollTop - gap ; 

		if(document_scrollTop > gap){
			$("footer").css("bottom", bottom+"px");
		}else{
			$("footer").css("bottom","0");
		}
	}

    $(".btn-like").click(function() {
	    $(this).toggleClass("done");
    });
    
    var msg = "${delmsg}";

    if(msg != ""){
    	alert(msg);
    }
    

	var nolist = "${list}";
	

	if(nolist != ""){
		$("#noBoard").css("display","none");
	}
    
    
    
     </script>

</html>