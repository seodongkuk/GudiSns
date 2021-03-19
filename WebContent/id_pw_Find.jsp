<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>아이디/비밀번호찾기</title>
        <style>
               h2{
               padding: 30;
               font-size: 70;
           }
           input[type='button'],input[type='submit']{
            width: 20%;
              height: 20%;
              background-color: rgb(240, 207, 207);
               border: 0px;
           }
table,td,th{
               /* border: 1px solid black;
                border-collapse: collapse;
                */
            } 
            
            td{
                padding: 10px;
            }
         
            input[type='button']{
                height: 30px;
                
            }
            td{
                text-align: center;
                
            }
            input[type="button"]{
                margin: 10;
            }
        </style>
    </head>

    <body>
        <CENTER>
        <div><img src="로고.gif" width="100" height="100"></div>
        <div style="font-size: 20;font-weight:bold;color: dimgrey;">
        <p>로그인에 문제가 있나요?</p>
        <p>당신이 찾고자 하는 것을</p>
        <p>눌러주세요.</p>
        </div>
       <td>
                <input type="button" value="아이디찾기" onclick="location.href='id_Find.jsp'">
            </td>
            <td>
                <input type="button" value="비밀번호찾기" onclick="location.href='pw_Find.jsp'">
            </td>
        <hr/>
        <input type="button" value="회원가입하기">
        <br>
        <input type="button" value="로그인하기">
        </CENTER>
    </body>
    <script>
      
    </script>
</html>