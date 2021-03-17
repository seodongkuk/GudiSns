<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>구디SNS</title>
        

        <style>
           table,td,th,tr{
                border: 1px solid white;
                border-collapse: collapse;
                
            } 
           h2{
               padding: 0;
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
            td{
                text-align: center;
            }
            th{
                font-size: 20;
            }

        </style>
    </head>
    <body>
        
        
        
        <center>
            <table style="float: left;margin-top:10px;">
                <tr>
                    <td rowspan="2"><img src="유저프로필.gif" width="100" height="100"></td>
                    <td>
                   <input type="button"value="${loginId}" style="background-color: white;font-weight:bold;">
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button"value="로그아웃" onclick="location.href='logout'">
                        
                </td>
                </tr>
               
                <input type="button"value="새글쓰기" style="float: right;margin-top: 110px;">
            </table>
       <h2>
        구디SNS
        </h2>
        <hr>
        <select name="정렬" style="float: right;">
            <option value="추천순">추천순</option>
            <option value="최신순">최신순</option>
            </select>
        
      
       
        <iframe style="position: absolute;float: left; scroll-behavior: auto;left: 0;" src="board.html" width="90%" height="80%" frameborder="0">
        <hr></iframe>
        
        <iframe style=" margin-top: 40% ; position: fixed;left: 30%;" src="navi.html" width="1000px" height="500px" frameborder="0" ></iframe>
        
    </body>
</center>
<script src="//code.jquery.com/jquery.min.js"></script>
<script>
var msg = "${msg}";

if(msg != ""){
	alert(msg);
}

</script>
</html>