<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
   
    <table>
       <form action="pwfind" method="post">	
     <h2>패스워드 찾기</h2>
        <form>
        <tr>
            <td>
                <input type="text" name="userId" placeholder="아이디"/>
                
            </td>
        </tr>
            <br/>
            <td>
                <input type="text" name="userName" placeholder="이름"/>
            </td>  
            <tr>
             <br/>
            <td>
             <input type="text" id="userPhone" name="userPhone" maxlength="13" placeholder="핸드폰번호."/>
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
</form>


<hr/>

         <td>    
            <input style="margin: 10;" type="button" value="아이디 찾기" onclick="location.href='id_Find.jsp'"/></br>
        </td>
         <td>    
            <input style="margin: 10;" type="button" value="홈으로 이동" onclick="location.href='index.jsp'"/></br>
        </td> 

          
        </CENTER>
    </body>
<script>
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);
	}
</script>
</html>
