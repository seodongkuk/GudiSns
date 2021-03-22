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
                    <td><input type="text" name="userPw" id="password1"></td>
                    </tr>
                    <tr>
                        <td>PW재확인</td>
                    <td><input type="text" id="password2" name="userPw" onkeyup=test();></td>
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
                    <td><input type="text" name="email" value="${info.email}"></td>
                    </tr>
                    <tr>
                        <td>프로필사진</td>
                        <td>
                            <label for="file">업로드</label>
                            <input type="file" id="file">
                            <input class="upload_name" value="파일업로드 해주세요">
                        </td>
                    </tr>
                </table>
                <button type="submit"  >회원정보 수정</button>
            </form>
        </div>
        

    </div>
</body>
<script>
function next(){

if(confirm("글 수정을 취소하시겠습니까 ?"))
{
   //예 
//    this.self.close();
             
}
else
{
// 아니오

}




     function test() {
      var p1 = document.getElementById('password1').value;
      var p2 = document.getElementById('password2').value;
      var p3 = document.getElementById('password2ck');
      if( p1 != p2 ) {
        p3.style.visibility="visible";
    } else{
        p3.style.visibility="visible";
          p3.innerHTML="비밀번호가 일치 합니다!"
        
      }

    }



   $(document).ready(function () {
       var fileTarget = $('#file');
       fileTarget.on('change', function () { // 값이 변경되면
           var cur = $(".filebox input[type='file']").val();
           $(".upload_name").val(cur);
       });

   }
       var msg = "${msg}";

       if(msg != ""){
       	alert(msg);
       }   
       
       

</script>

</html>