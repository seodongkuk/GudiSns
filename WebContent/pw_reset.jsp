<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
        <title>PW RESET</title>
        <style>


            
            td{
                padding: 10px;
                height: 80px;
            }
         
           input[type='button']{
            position: absolute;
            width: 130px;
            height: 40px;
            top: 450px;
            left: 47%;
            
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
       <form action="pwupdate?id=${id}" method="POST">
   		
		
        <tr>
                <td>
                  
                </td>
        </tr>
        <tr>
       <tr>
                <tr>
                    <th>비밀번호 변경</th>
                    <td>
                        <input type="password" id="newPw" name="newPw" maxlength="10" placeholder="새 비밀번호를 입력해주세요."/>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호 재확인</th>
                    <td>
                        <input type="password" id="pwConfirm" maxlength="10" placeholder="새 비밀번호 재확인."/>
                        <br/>
                        <span id="passChk"></span>
                    </td>
                </tr>

         <br/>
       
         <td> 
        <input type="button" id="btn" value="비밀번호 재설정"/>
        </td>       
     

    </table>
        </form>

</CENTER>
    </body>
    
 <script>
	var msg="${msg}";
	if(msg!=""){
		alert(msg);
	}
	
	
	var $newPw = $("#newPw");
	var $pwConfirm = $("#pwConfirm");
	var pwChk = false;
	
	//pw에 한글 입력안되게(영어 숫자 특수문자만)
	$(document).ready(function(){
		  $("input[name=newPw]").keyup(function(event){ 
		   if (!(event.keyCode >=37 && event.keyCode<=40)) {
		    var inputVal = $(this).val();
		    $(this).val(inputVal.replace(/[^a-z0-9~!@#$%^&*()_.,+<>?:{}]/gi,''));
		   }
		  });
		});
	
 	//pw가 5자 이상인가?
    $('#newPw').focusout(function(){
        if($(this).val()!==$('#pwConfirm').val()){
            $('#passChk').html('비밀번호가 일치하지 않습니다.');
            $('#passChk').css('color','red');
            pwChk = false;
        }else{
            $('#passChk').html('비밀번호가 일치합니다.');
            $('#passChk').css('color','green');
            pwChk = true;
        }
    }); 
    //pw와 pw확인이 값이 일치하는가?
    $('#pwConfirm').keyup(function(){
        if($(this).val()!==$('#newPw').val()){
            $('#passChk').html('비밀번호가 일치하지 않습니다.');
            $('#passChk').css('color','red');
            pwChk = false;
        }else{
            $('#passChk').html('비밀번호가 일치합니다.');
            $('#passChk').css('color','green');
            pwChk = true;
        }
    });
    
    $('#btn').click(function(){
    	if($newPw.val()==""||$pwConfirm==""){//비밀번호 또는 비밀번호확인이 공백일때
    		console.log($newPw.val());
    		alert("비밀번호를 입력해주세요.")
    		$newPw.focus();
    	}else if($newPw.val().length<5){
    		alert("비밀번호는 5자 이상 입력해주세요!!");
    		$newPw.focus();
    	}else if(pwChk==false){
    		alert("비밀번호가 일치하지 않습니다.");
    		$newPw.focus();
    	}else{
    		console.log("비밀번호가 수정되었습니다.");
    		$('form').submit();
    	}
    });
    
    /* else{
	alert("비밀번호가 변경되었습니다.");
	location.href="login.jsp";
} */
	</script>
</html>