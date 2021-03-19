<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
     <head>
        <meta charset="utf-8">  
        <style>
            body{
                    width: 700px;
                    margin-left:20%;
                    padding: 50px;
                    border: 1px solid black;
             }
           .title{
                text-align: center;
                border-bottom: 1px solid #eee;
                margin: 0;
                padding-top: 25px;
                padding-bottom: 25px;
            }
            .textBox{
                width: 700px;
                height: 300px;
            }
            .button a{
                margin-left: 75%;
            }
            .button a button{
                font-size: 9px;
                background-color: white;
                padding: 10px 20px;
                text-decoration: none;
                border-radius: 5px;
            }
        </style>
    </head>
    <body> 
        <h2 class='title'>신고페이지</h2>
        <form action="reportAction" method="post" id="reportAction">
            <input type="hidden" name="loginId" value="${loginId}">
            <input type="hidden" name="board_idx" value="${idx}">
            <input type="hidden" name="user_id" value="${user_id}">
            <input class='textBox' type="text" name="content" placeholder="신고사유를 작성해주세요."/>   
        </form>
        <br/>
        <div class="button">
            <button type="submit" form="reportAction">신고하기</button>
            <button type="button" onclick="location.href='flist'">취소</button>
        </div>
    </body>
    <script>
            var msg = "${msg}";
            if(msg!=""){
                alert(msg);			
            }		
            
            function a(){
            	alert(msg);
            }
        </script>
</html>