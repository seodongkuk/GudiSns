<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>ë´ íë¡í</title>
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
                /*ì¤ì ì ë ¬*/
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
        <!--Alt+Shift+B ë¥¼ ëë¥´ë©´ ì¤í-->
        <h2 style="text-align: center;">ë´ íë¡í </h2>
        
        <div id="dmBox">
            <div class="userId">
                USER
            </div>
            <div>
                <img style="width: 150px; height: 170px;" src="C:\Users\HSK\Pictures\ì¤ë¼í´íí´ë.png"/>
            </div>
                <input type="button" class="member" value="íìì ë³´ ìì ">
                <input type="button" class="member" value="íì íí´">

                <div class="friendList">
                    <p>ì¹êµ¬ëª©ë¡</p>
                    <hr style="border:solid 1px black;"/>
                    <span>
                        <img src="C:\Users\HSK\Pictures\ì¤ë¼í´íí´ë.png"/>
                    </span>
                    <div class="friendId">
                        USER1
                    </div>
                    <hr/>

                    <span>
                        <img src="C:\Users\HSK\Pictures\ì¤ë¼í´íí´ë.png"/>
                    </span>
                    <div class="friendId">
                        USER2
                    </div>
                    <hr/>

                    <span>
                        <img src="C:\Users\HSK\Pictures\ì¤ë¼í´íí´ë.png"/>
                    </span>
                    <div class="friendId">
                        USER3
                    </div>
                    <hr/>
                    <center>
                        <button>ì´ì </button>
                        <span>  1 </span>
                        <button>ë¤ì</button>
                    </center>
                </div>
        </div>
        <hr/>
        <div id="header">
            <center>
            <table>
                <tr>
                    <td>
                        <img src="ê²ìë¬¼ì´ë¯¸ì§.gif" style="margin-top:10px; width:400px; height:300px;"/>
                    </td>
                 </tr>
                <tr>
                <tr>
                    <td class="profileBoard">
                        <p id="hashTag">#ì½ë¡ë #ì½ë¡ëì¢ì</p>
                            <textarea style="width: 300px; resize: none; border: none; overflow: hidden;" readonly>
ëª¨ë ì½ë¡ë ì¡°ì¬íê³ ,
ì¢ì ëë©´ ë§ëì ë¤ê°ì´ ëì
ë§ì¤í¬ë ìë¼ê³ â¦!
ì´ì©êµ¬ ì ì©êµ¬ ì¬ë¼ì¬ë¼
                            </textarea>
                            <button id="moreShow">ëë³´ê¸°</button>
                     </td>
                     
                 </tr>
            <td>
                 <button class="btn-like" style="font-size: 50px;background-color: white;border: none;float: left;">â¥</button>
                 <input style="float: left;margin-top: 30px;border: none;" type="text" value="+20">
             </td>
             </tr>
            <tr>
                <td style="margin:0; padding: 0;";>
                     <hr>
                </td>
             </tr>
             <tr>
                 <td>
                     <p style="color: grey;">ì ì²´ ëê¸ : 5ê°</p>
                     - USER2: ê·¸ëê·¸ë~!</br>
                     - USER3: ë§ì¤í¬ ê¼­ íì!</br>
                 </td>
             </tr>
             <tr>
                 <td>
                     <textarea style="width: 345px; resize: none;"  placeholder="ëê¸ì ìë ¥í´ì£¼ì¸ì."></textarea>
                     <input type="button" value="ëê¸ë±ë¡" style="float: right;height: 30;">
                 </td>
             </tr>
     
                
          </table>
        </center>
        </div>
        
        
        <iframe id="footer" src="navi.jsp" style="border: none;bottom: 0; left: 550; position: fixed; z-index: 5; top: 850; width: 100%;"></iframe>

    </body>
     <script>
         	$(function(){
		stickyFooter();

		$(window).scroll(stickyFooter).resize(stickyFooter);
	});


	function stickyFooter(){
		document_height = $(document).height(); // ë¬¸ì ì ì²´ ëì´
		document_scrollTop = $(document).scrollTop(); // ë¬¸ì ì ì²´ ëì´ ì¤ ì¤í¬ë¡¤ ìì¹
		window_height = $(window).height(); // ì°½ ëì´
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