<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
  <script src="//code.jquery.com/jquery-2.2.4.min.js"></script>
        <title>INPUTs</title>
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
      

      
            <h2>아이디 찾기</h2>
            
    <table>
    <form action="idfind1" method="post">
        
        <tr>
            <td>
                <input type="text" name="userName" id="userName" placeholder="이름을 입력해주세요"/>
            </td>
        </tr>
            <br/>
            <td>
                <input type="text" name="userPhone" id="userPhone" placeholder="전화번호를 입력해주세요"/>
            </td>  
            <tr>
                <td>
        <input type="button" id="btn1" value="아이디 찾기"/>
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
            <input style="margin: 10;" type="button" value="비밀번호 찾기" onclick="location.href='pw_Find.jsp'"/></br>
        </td>
        <td>
            <input type="button"  value="로그인 하기" onclick="location.href='index.jsp'" />
        </td>   
    
          
        </CENTER>
    </body>
    <script>
    var msg = "${msg}";

    if(msg != ""){
    	alert(msg);
    }
    
    
    
    $("#btn1").click(function(){
		 
		 var $name = $("#userName");
		 var $phone = $("#userPhone");
		 
		if($name.val()==''){
			alert('가입하신 이름을 입력해주세요.');
			$name.focus();
		}else if($phone.val()==''){
			alert('가입하신 핸드폰 번호를 입력해주세요.');
			$phone.focus();
       }else{
       	var params = {};
			params.name = $name.val();
			params.phone = $phone.val();
       	$.ajax({
				type:'get'
				,url:'idfind'
				,data: params
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj.use);
					if(obj.use == ""){//아이디 값이 없다면
						alert("이름, 핸드폰번호를 다시 확인 후 입력해주세요.");
					}else{
						location.href="./idfind1?id="+obj.use;
					}
				}
				,error:function(e){
					console.log(e);
				}
			});
      }
	}); 
    </script>
</html>