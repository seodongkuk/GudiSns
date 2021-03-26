<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="imgfile\icon.jpg">
    <title>update_profile</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <style>
        body {
            margin-left: 42%;
        }

        #main {
            position: absolute;
            left: 530px;
            border: 1px solid black;
            width: 700px;
            height: 850px;
        }

        .update_name {
            /* background-color: tomato; */
            position: absolute;
            top: 15px;
            left: 200px;
            margin-top: 70px;
            margin-bottom: 80px;
            padding-left: 38px;
            width: 280px;
            height: 50px;
        }

        .container {
            position: absolute;
            top: 190px;
            left: 190px;
        }

        tr {
            height: 50px;
            /* text-align: center; */
        }

        button {
            position: absolute;
            width: 100px;
            height: 50px;
            top: 650px;
            left: 300px;
            /* 아마 버튼 타입을쓰지않을까>?*/
        }

        .filebox input[type="file"] {
            position: absolute;
            width: 0;
            height: 0;
            padding: 0;
            overflow: hidden;
            border: 0;
        }

        .filebox label {
            position: relative;
            left: 170px;
            display: inline-block;
            padding: 10px 20px;
            color: #999;
            vertical-align: middle;
            background-color: #fdfdfd;
            cursor: pointer;
            border: 1px solid #ebebeb;
            border-radius: 5px;
        }

        /* 파일 얿로드 버튼 */
        .filebox .upload_name {
            display: inline-block;
            position: relative;
            left: -80px;
            height: 35px;
            font-size: 10px;
            padding: 0 10px;
            vertical-align: middle;
            background-color: #f5f5f5;
            border: 1px solid #ebebeb;
            border-radius: 5px;

        }
        
        #password2ck{
            position: absolute;
            visibility: hidden;
            color: red;
        }
    </style>
</head>

<body>
    <div id="main">
        <div class="update_name">
            <h1>회원정보 수정</h1>
        </div>
        <div class="container">
            <form action="userinfoupdate" method="post">
            	<input type="hidden" name="userId" value="${sessionScope.id}"/>
      
                <table class="filebox">
                    <tr>
                        <td>ID</td>
                   <td><input type="text" name="userId" value="${info.id}"readonly></td>
                    </tr>
                    <tr>
                        <td>PW</td>
                    <td><input type="password" name="userPw" id="newPw" maxlength="10" placeholder="비밀번호 "></td>
                    </tr>
                    <tr>
                        <td>PW재확인</td>
                    <td>
                        <input type="password" name="userPw" id="pwConfirm" maxlength="10" placeholder="비밀번호 확인."/>
                        <br/>
                        <span id="passChk"></span>
                    </td>
                    </tr>
                    <tr>
                    
                        <td></td>
                    </tr>
                    <tr>
                        <td>NAME</td>
                    <td><input type="text" name="userName" value="${info.name}"></td>
                    </tr>
                    <tr>
                        <td>PHONE</td>
                    <td><input type="text" name="phone" value="${info.phone}"></td>
                    </tr>
                    <tr>
                        <td>EMAIL</td>
                    <td><input type="email" name="email" value="${info.email}"></td>
                    </tr>
                    <tr>
                    <td>
                       <input type="button" id="btn" value="회원정보 수정"/>
                       </td>
                     </tr>
                </table>
              
            </form>
        </div>
        

    </div>
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


</script>
</html>