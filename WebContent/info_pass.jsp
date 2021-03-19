<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>PW RESET</title>
        <style>


            
            td{
                padding: 10px;
                height: 80px;
            }
         
            input[type='button']{
                height: 30px;
                
            }
            td{
                text-align: center;
                
            }
            #pw{
                margin: 50px;
            }
        </style>
    </head>

    <body>

        <CENTER>
      

      
            <h1>비밀번호 확인</h1>
            
    <table>
  
<form action="infopw" method="post">
<input type="hidden" name="id" value="${sessionScope.id}"/>
		
        <tr>
         
        </tr>
        <tr>
        <td>
      				     회원정보를 수정하기 위해  현재 비밀번호를 입력해주세요
		 </td>  
    </tr>
        <tr>
            <td>
            PW <input type="password" name="userPw" placeholder="비밀번호를 입력 해주세요."'/>
            </td>
        </tr>
        <tr>
            <td style="color: red;">비밀번호가 일치 하지 않습니다.</td>
        </tr>

         <br/>
         
         <td> 
        <input type="submit" value="확인"/> 
        </td>       
     
    </form>

    </table>

</CENTER>
    </body>
<script>
var msg = "${msg}";

if(msg != ""){
	alert(msg);
}

</script>
</html>