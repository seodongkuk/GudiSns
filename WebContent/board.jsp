<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>구디SNS</title>


        <style>
        body{
        margin-left:10px;
        }
            table,tr,th,td{
                border:1px solid white;
                border-collapse: collapse;
            }
           h2{
               padding: 50;
               font-size: 70;
           }
            th,td{
                padding: 10px;
                
            }
            th{
                width: 50px;
            }
            input[type='submit']{
                height: 70px;
            }
            input[type='text']{
                height: 30px;
            }
            input[type='password']{
                height: 30px;
            }
            input[type='submit'],input[type='button']{
                background-color: rgb(240, 207, 207);
               border: 0px;
            }
            p{
                margin: 0; 
                height:5px;
            }
.btn-like {
  color: transparent;
  text-shadow: 0 0 2px rgba(255,255,255,.7), 0 0 0 #000;
}
.btn-like:hover {
  text-shadow: 0 0 0 red;
}
.btn-like.done {
  color: inherit;
  text-shadow: 0;
}
.btn-like.done:hover {
  color: transparent;
  text-shadow: 0 0 0 #777;
}
        </style>
    </head>
    <body>
            <img src="타인프로필.gif" width="75" height="75" style="float: left;">
            <input type="button"value="USER_ID" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
       <table>
           <tr>
           <td>
            <img src="게시물이미지.gif" width="600" height="450"style="margin-top:100px" />
        </td>
            </tr>
        <tr>
        
       <td>
            <button class="btn-like" style="font-size: 50px;background-color: white;border: none;float: left;">♥</button>
            <input style="float: left;margin-top: 15px;border: none;" type="text" value="좋아요숫자">
            <input type="text"value="작성날짜:2021-03-10"style="float: right;border: none;margin-top: 15px;">
        </td>
        </tr>
        <tr>
        <td>
            <p style="font-size: 15px; color: grey;" >
            	댓글 모두 ${rcnt}개입니다.</p>
        </td>
    </tr>
    <form action="rwrite" method="POST">
    	<tr>
        <td>
        	<input type="hidden" name="user_id" value="${dto.user_id}"/>
        	<input type="hidden" name="board_idx" value="${dto.board_idx}"/>
            <b>${dto.user_id}</b> : <input type="text" name="content" size="70" placeholder="댓글을 입력해주세요."> 
            <button>댓글 작성</button>
        </td>
    </tr>
    </form>

           
     </table>
       
            
    </body>
<script src="//code.jquery.com/jquery.min.js"></script>

<script>
    $(".btn-like").click(function() {
        $(this).toggleClass("done");
    })
    </script>
</html>