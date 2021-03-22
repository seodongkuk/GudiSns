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
      

      
            <h1>비밀번호 재설정</h1>
            
    <table>
        <form action="pwupdate" method="post">
   		
			<input type="hidden" name="userId" value="${sessionScope.id}"/>
	    	<input type="hidden" name="email" value="${sessionScope.email}"/>
	    
        <tr>
                <td>
                  
                </td>
        </tr>
        <tr>
        <td>
            PW <input type="password" name="pw" placeholder="비밀번호를 입력해주세요."/>
        </td>  
    </tr>
        <tr>
            <td>
                PW 재확인 <input type="password" name="pw" placeholder="비밀번호를 재입력 해주세요."/>
            </td>
        </tr>
        <tr>
            <td style="color: red;"></td>
        </tr>

         <br/>
       
         <td> 
        <input type="submit" value="비밀번호 재설정"/>
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