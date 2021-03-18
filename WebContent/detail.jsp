<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>구디SNS</title>


        <style>
        body{
        margin-left:10%;
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
   		<button onclick="location.href='writeEdit'"></button>
    	<input type="hidden" name="board_idx" value="${dto.board_idx}"/>
            <img src="타인프로필.gif" width="75" height="75" style="float: left;">
            <input type="button"value="USER_ID" style="background-color: white;font-weight:bold; float: left; margin-top: 40px;font-size: 20px;" >
       <table>
           <tr>
           <td>
            <img src="" width="800" height="500"style="margin-top:100px" />
        </td>
            </tr>
            <tr>
            	<td>
            		<p>해시태그 들어올자리</p>
            		<p>${dto.content}</p>
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
           <td style="margin:0; padding: 0;">
                <hr>
           </td>
        </tr>
        <tr>
            <td>
                <input  style="font: size 5px;border: none; color: darkgray;" value="전체댓글 3개"/>
                <p>1번댓글</p><br>
                <p>2번댓글</p><br>
                <p>3번댓글</p><br>
            </td>
        </tr>
        <tr>
            <td>
                <input type="text" style="width: 80%;">
                <input type="button" value="댓글등록" style="float: right ;height: 30;">
            </td>
        </tr>

           
     </table>
       
            
    </body>
<script src="//code.jquery.com/jquery.min.js"></script>

<script>
    $(".btn-like").click(function() {
        $(this).toggleClass("done");
    })
    </script>
</html>