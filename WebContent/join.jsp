<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>회원가입</title>
        <style>
             h2{
               padding: 30;
               font-size: 70;
           }
           table{
               height: 40%;
               width: 50%
           }
            table,td,th{
               /* 
               border: 1px solid black;
                border-collapse: collapse;
                */
            } 
            th, td{
                padding: 10px;
            }
            input[type='text'],input[type='password'],input[type='email'],input[type='range']{
                width: 100%;
            }
            #photo{
                width: 60%;
            }
            #userId{
                width: 60%;
            }
            input[type='button'], input[type='submit']{
              
               background-color: rgb(240, 207, 207);
                border: 0px;
            }
        </style>
    </head>
    <body>
        <center>
        <h2>구디SNS 회원가입</h2>
        
        <form action="join" method="POST">
            <table>
                <tr>
                    <th>아이디</th>
                    <td>
                        <input id="userId" type="text" name="userId"/>
                        <input type="button" name="photoUpload" value="아이디중복체크">
                    </td>
                </tr>
                <tr>
                    <th>
                        <td style="color: red;">아이디 중복체크확인칸</td>
                    </th>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <input type="password" name="userPw"/>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호 확인</th>
                    <td>
                        <input type="password" name="userPwChk"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <td style="color: green;">비밀번호 일치 정보칸</td>
                    </th>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text" name="userName"/>
                    </td>
                </tr>
                <tr>
                    <th>핸드폰번호</th>
                    <td>
                        <input type="text" name="userPhone"/>
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>
                        <input type="email" name="email"/>
                    </td>
                </tr>
                <tr>
                    <th>프로필사진</th>
                    <td>
                        <input id="photo" type="text" name="photo"/>
                        <input type="button" name="photoUpload" value="사진업로드"/>
                    </td>
                </tr>
            </table>
            <input type="submit" name="회원가입버튼" value="회원가입완료" style="padding: 10;margin: 10; font-size: 15;"/>
            

        </form>
    </center>
    </body>
    <script>

var msg = "${msg}";
if(msg != ""){
	alert(msg);
}
</script>
</html>