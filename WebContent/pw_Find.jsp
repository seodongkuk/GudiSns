<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
       <form action="pwfind" method="post">
        <title>비밀번호찾기</title>
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
        </style>
    </head>

    <body>

        <CENTER>
      

      
            <h2>패스워드 찾기</h2>
            
    <table>
        <form>
        <tr>
            <td>
                <input type="text" name="userId" placeholder="아이디"/>
            </td>
        </tr>
            <br/>
            <td>
                <input type="email" name="userEamil" placeholder="이메일"/>
            </td>  
            <tr>
                <td>
        <input type="submit" value="비밀번호 찾기"/>
        </td>
        </tr>
       </td>       
       <tr>
           <td style="color: red;">입력하신 정보와 일치하는 아이디가 없습니다.
            이름 또는 전화번호를 다시 확인해주세요</td>
        </tr> 
    </form>

    </table>



<hr/>

        <td>    
            <input style="margin: 10;" type="button" value="아이디 찾기"/></br>
        </td>
        <td>
            <input type="button" value="로그인 하기" />
        </td>   

          
        </CENTER>
    </body>
    <script>
      
    </script>
</html>