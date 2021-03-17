<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="imgfile\icon.jpg">
    <title>others_Profile</title>
    <style>
        
        #containar{
            display: table; 
            border: 1px solid black;
            margin-left: auto;
             margin-right: auto;
             padding: 100px;
        }
        
        #profileName{
           background-color: whitesmoke;
           display: table; 
            margin-left: auto;
             margin-right: auto;
            /* box-sizing: border-box;
            margin-top: 80px;
           
            margin-right: 570px;
            text-align: left; */
            /* padding-left: 162px;
            width: 500px;
            height: 50px; */
        }
        /* ì ì²´ ë§ì§ ê° ì£¼ê¸° */
        #topMenu{
            box-sizing: border-box;
            background-color: yellow;
            display: table; 
            margin-left: auto;
             margin-right: auto;
             margin-bottom: 30px;
           /* margin-right: 570px;
            width: 500px;
            height: 120px; */
        }
        #topMenu::after{
            
            box-sizing: border-box;
            margin-bottom: 30px;
            margin-right: 570px;
            content:"";
            clear: both;
            display: block;
        }
        #userPhoto{
            position: relative;
            left: 10px;
            box-sizing: border-box;
            margin-left: 30px;
            margin-right: 100px;
            padding-top: 13px;
            width:80px;
            height: 90px;
            background-color: red;
        }
        #topnev1{
            position: relative;
            left: 20px;
            box-sizing: border-box;
            margin-top: 13px;
            margin-right: 25px;
            padding-top: 13px;
            width:65px;
            height:75px;
            text-align: center;
        }
        #topnev2{
            position: relative;
            left: 20px;
            box-sizing: border-box;
            margin-top: 13px;
            margin-right: 25px;
            padding-top: 13px;
            width:65px;
            height:75px;
            text-align: center;
        }
        #topnev3{
            position: relative;
            left: 20px;
            box-sizing: border-box;
            margin-top: 13px;
            margin-right: 25px;
            padding-top: 13px;
            width:65px;
            height:75px;
            text-align: center;
        }
        .topM{
            float: left;
            box-sizing: border-box;
            background-color: cyan;
            margin-right: 50px;
        }
        /* í ë§ì§ íë¡í ì¬ì§ ìë¦¬ ì°¨ì§íê² ììì ëºê²ì  boxsizingì¨ì ìì ê³ ì ìì¼ì ì¬ì§*/
        #boardMenu::after{
            clear: both;
            display: block;
        }
        #bBoxCk{
            visibility: hidden;
            float: right;
            background-color: aqua;
        }
        #bBoxMenu{
            visibility: visible;
        }
        .bMenubox2{
            margin-left: 50px;
            margin-bottom: 30px;
            width: 400px;
            height: 240px;
            background-color: red;
        }
        
        .bMenubox2 img{
            box-sizing: border-box;
        }
        /* í¹ìëª°ë¼ì ë³´ëë°ì¤ë¡ ëì³ë ìëì´ê°ê² ìëë©´ì´ë¶ë¶ ìì íê¸° */
        .bMenubox3{
            text-align: center;
            box-sizing: border-box;
            margin-bottom: -48px;
        }
        .bMenubox4{
            box-sizing: border-box;
            padding-top: 10px;
            padding-left: 40px;
            width: 500px;
            height: 150px;
            background-color: brown;
        }
        .textbox{
            background-color: white;
            padding: 10px;
            width: 400px;
            height: 50px;
            text-align: center;
            overflow: hidden;
            vertical-align: top;
            text-overflow: ellipsis;
            word-break: break-all;
        }

        .bMenubox5{
            margin-left: -40px;
            text-align: center;
        }
        .heart{
            position: absolute;
            top: 720px;
            margin-left: -10px; 
        }

        .bMenubox6{
            background-color: teal;
        }
        .bm6Ch1{
            background-color: thistle;
        }
        .bm6Ch1 p{
            font-size: 12px;
            margin-left: 70px;
        }
        .bm6Ch2{
            position: static;
            display: block;
            height: 50px;
            background-color: tomato;
        }
        .bm6Ch2 button{
            position: relative;
            top: 8px;
            left: 400px;
            /* width: 100%;
            height: 10px; */
        }
        #boardMenu{
            display: table; 
            margin-left: auto;
             margin-right: auto;
            width: 500px;
            height: 600px;
            margin-bottom: 60px;
            background-color: bisque;
        }
       /* #bottomMenu{
            width: 500px;
            height: 60px;
            background-color: blueviolet;
        }  */
        /* #bottomMenu::after{
            content: "";
            clear: both;
            display: block;
        }
        .bmenu{
            float: left;
            background-color: crimson;
            margin-right: 50px;
        }
        */
        #updateA{
            visibility:hidden;
        }
    </style>
    
</head>

<body>
    <div id="containar">
        <div id="profileName">
            <h1> ${id} 프로필</h1>
        </div>
    
        <div id="topMenu">
            <div id="userPhoto" class="topM"><img src="#" alt="프로필사진"></div>
            <div id="topnev1" class="topM"><a href="#">친구<br>요청</a></div>
            <div id="topnev2" class="topM"><a href="DM_Room?id=${id}&&create=${sessionScope.loginId}">DM <br>보내기</a></div>
            <div id="topnev3" class="topM"><a href="#">친구<br>삭제</a></div>
        </div>
    
        <div id="boardMenu">
            <div id="bBoxCk" class="bBox" onclick=a();>
                <div id="bBoxMenu" class="bMenubox1" data-visibility="true"><a href="#">.&nbsp;.&nbsp;.&nbsp;</a></div>
            </div>
            <a href="#" style="position: absolute; left: 1062px; top: 370px;" id="updateA" >신고</a>
            <div class="bMenubox2"><img src="#" alt="2"></div>
            <div class="bMenubox3">
                <p class="#">#해시태그  #해시태그</p>
            </div>
            <div class="bMenubox4">
                <p class="textbox">비·집회·결사의 자유, 정부나 법원의 권한에 관하여 특별한 조
                    치를 할 수 있다결사의 별한 조치를 할 수 있다결사의 자유,  사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부조치를 할 수 있다결사의 자유, 정부 특별한 조치를 할 수 있다결사의 자유, 정부나 법원의 권한에 관하여 특별한 조치를 할 수 있다.</p>
                     <div class="bMenubox5"><button>더보기</button></div>
                     <button class="heart" type="submit"><img src="" alt="하트"> </button>
                     <p style="display: inline; margin-left: 57px;position: absolute; top: 704px;">+111</p>  
                     <p style="display: inline; margin-left: 360px;position: absolute; top: 704px;">yyyy-mm-dd</p>
    
         </div>
            <div class="bMenubox6">
                <hr>
                <div class="bm6Ch1">
                    <p>전체댓글</p>
                    <p class="comment">user2 : 댓글</p>
                    <p class="comment">user3 : 댓글</p>
                </div>
                <hr>
                <div class="bm6Ch2">
                    <p style="display: inline-block; margin-left: 65px; position: absolute; top: 864px;
    
                    ">본인아이디</p>
                    <input type="text" placeholder="댓글을 입력해주세요" style="display: inline-block; margin-left: 180px; position: absolute; top: 877px;
    
                    ">
                    <button type="submit" >댓글쓰기</button>
                 </div>
            </div>
    
    
        </div>
    
        <!-- jsp:include or iframeì¼ë¡ ê³µíµ uiì²ë¦¬ ?í ì§ ë§ì§ ìê° --> 
         <!-- <div id="bottomMenu">  -->
    
            <!-- ê³µì©ì²ë¦¬ë¶ë¶ -->
            <!-- <div class="bmenu"><a href="#">ë©ì¸</a></div>
            <div class="bmenu"><a href="#">ê²ì</a></div>
            <div class="bmenu"><a href="#">DM</a></div>
            <div class="bmenu"><a href="#">ìë¦¼</a></div>
            <div class="bmenu"><a href="#">ë´ íë¡í</a></div> -->
        <!-- </div> -->
    </div>
</body>
<script>
    function a(){
        var con=document.getElementById("bBoxMenu");
        var atag = document.getElementById("updateA");
        if(con.style.visibility =="visible"){
            atag.style.visibility="visible";
        }else if(con.style.visibility="hidden"){
            con.style.visibility="visible";
        }
    }
    // function b(){
    //     var con=document.getElementById("bBoxMenu");
    //     var atag = document.getElementById("updateA");
    //     if(con.style.visibility =="visible"){
    //         atag.style.visibility =="visible";
    //         $("bBoxMenu").data("visibility") == false;
    //     }
    // }
</script>

</html>