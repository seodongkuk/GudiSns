<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>내 프로필</title>
        <style>
            #header{
                position: relative;
                width: 800px;
                height: 250px;
                margin-left: 550px;
                left: 0; 
                right: 0; 
                top: 0;
                
                overflow:auto;
            }
            #dmBox{
                position: relative;
                border:1px solid black;
                width: 800px;
                height: 500px;
                /*중앙 정렬*/
                top: 0;
                right: 0;
                bottom: 0;
                left: 0;
                margin: auto;
            }
            .friendList{
                position: absolute;
                margin: auto;
                top: -10;
                bottom: 0;
                margin-left: 50%;
                border:1px solid black;
                width: 350px;
                height: 350px;
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
                height: 70px;
                margin: 10px 20px;
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
                margin-left: 10px;
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
                margin-left: 40%;
                margin-top: 20px;
                float: left;
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>        
        <link rel="stylesheet" sype="text/css" href="./css/Likebtn.css">
    </head>
    <body>
        <!--Alt+Shift+B 를 누르면 실행-->
        <h2 style="text-align: center;">내 프로필 </h2>
        
        <div id="dmBox">
            <div class="userId">
                USER
            </div>
            <div>
                <img style="width: 150px; height: 170px;" src="C:\Users\HSK\Pictures\오라클홈폴더.png"/>
            </div>
                <input type="button" class="member" value="회원정보 수정">
                <input type="button" class="member" value="회원 탈퇴">

                <div class="friendList">
                    <p>친구목록</p>
                    <hr style="border:solid 1px black;"/>
                    <span>
                        <img src="C:\Users\HSK\Pictures\오라클홈폴더.png"/>
                    </span>
                    <div class="friendId">
                        USER1
                    </div>
                    <hr/>

                    <span>
                        <img src="C:\Users\HSK\Pictures\오라클홈폴더.png"/>
                    </span>
                    <div class="friendId">
                        USER2
                    </div>
                    <hr/>

                    <span>
                        <img src="C:\Users\HSK\Pictures\오라클홈폴더.png"/>
                    </span>
                    <div class="friendId">
                        USER3
                    </div>
                    <hr/>
                    <center>
                        <button>이전</button>
                        <span>  1 </span>
                        <button>다음</button>
                    </center>
                </div>
        </div>
        <hr/>
        <div id="header">
           	<c:forEach items="${list}" var="myL">
           	<button onclick="location.href='del?board_idx=${myL.board_idx}'">게시글삭제</button>
            <table>
                <tr>
                    <td>
      					<input type="hidden" name="board_idx" value="${myL.board_idx}"/>
                        <img src="게시물이미지.gif" style="margin-top:10px; width:400px; height:300px;"/>
                        <!-- src 파일경로 지정해주기.. -->
                    </td>
                 </tr>
                <tr>
                    <td class="profileBoard">
                        <p id="hashTag">#코로나 #코로나종식</p>
                            <textarea style="width: 300px; resize: none; border: none; overflow: hidden;" readonly>
                            ${myL.content}
                            </textarea>
                            <button id="moreShow" onclick="location.href='detail?board_idx=${myL.board_idx}'">더보기</button>
                     </td> 
                 </tr>
            <tr>
           		 <td>
                 <button class="btn-like" style="font-size: 50px;background-color: white;border: none;float: left;">♥</button>
                 <input style="float: left;margin-top: 30px;border: none;" type="text" value="+20">
            	 </td>
             </tr>
            <tr>
                <td style="margin:0; padding: 0;">
                     <hr>
                </td>
             </tr>
             <tr>
                 <td>
                     <p style="color: grey;">전체 댓글 : 5개</p>
                     - USER2: 그래그래~!</br>
                     - USER3: 마스크 꼭 하자!</br>
                 </td>
             </tr>
             <tr>
                 <td>
                     <textarea style="width: 345px; resize: none;"  placeholder="댓글을 입력해주세요."></textarea>
                     <input type="button" value="댓글등록" style="float: right;height: 30;">
                 </td>
             </tr>     
          </table>
        	</c:forEach>
        </div>
        
        
        
        
        <iframe src="navi.jsp" width="850px" height="1000px" scrolling="no" frameborder="0"></iframe>

    </body>
     <script>
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
     </script>

</html>