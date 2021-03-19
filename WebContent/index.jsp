<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>구디SNS</title>


        <style>
           h2{
               padding: 50;
               font-size: 70;
           }
            table{
                
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
    <script src="//code.jquery.com/jquery.min.js"></script>
    <script>

    </script>
    <body>
        
        <center>
            <h2><img src="???" width="100" height="100">구디SNS</h2>
        
        <table>
            <form action="login" method="POST">
  
                <tr>
                    <th>ID</th>
                    <td>
                        <input type="text" name="userId" placeholder="아이디"/>
                    </td>
                    <td rowspan="2">
                        <input type="submit" value="로그인"/>
    
                    </td>
                </tr>
                <tr>
                    <th>PW</th>
                    <td>
                        <input type="password" name="userPw" placeholder="비밀번호"/>
                    </td>
                </tr>
            </form>
            <tr>
                <td colspan="3">
                    <input type="button" value="회원가입" onclick="location.href='join.jsp'"/>
                    <input type="button" value="아이디비밀번호찾기" onclick="location.href='id_pw_Find.jsp'"/>
                </td>
            </tr>
        </table>
        </center>
    </body>

<script>

	var msg = "${msg}";
	
	window.onkeydown = function(){
		var kcode = event.keyCode;
		if(kcode == 116){
			msg = "";
		}
	}
	
	if(msg != ""){
		alert(msg);
	}
</script>
</html>