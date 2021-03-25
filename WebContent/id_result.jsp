<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
    <script src="//code.jquery.com/jquery-2.2.4.min.js"></script>
        <title>아이디 확인</title>
        <style>
               h1{
               padding: 30;
               font-size: 70;
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
      

      
            <h1>아이디 찾기</h1>
            
 <table>
        <form>
        <tr>
            <td>
                    회원님의 ID는  ${idfind}  입니다.
            </td>
        </tr>
            <br/>
            <td>
              
            </td>  
    </form>
</table>
</br>
</br>



<hr/>

        <td>    
            <input style="margin: 10;" type="button" value="비밀번호 찾기" onclick="location.href='pw_Find.jsp'"/></br>
        </td>
        <td>
            <input type="button" value="로그인 하기" />
        </td>   

          
        </CENTER>
    </body>
    <script>

    </script>
</html>