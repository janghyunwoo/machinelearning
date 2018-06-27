<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="robots" content="noindex, nofollow">

    <title>Movie Recommend</title>
    <link rel="shortcut icon" type="image⁄x-icon" href="resources/img/movie.png">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<link rel="stylesheet" href="resources/css/login.css">

	<script type="text/javascript" src="resources/js/login.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script defer src="https://use.fontawesome.com/releases/v5.0.8/js/all.js"></script>
    
	<script type="text/javascript" src="resources/js/check.js" charset="euc-kr"></script>
	<script type="text/javascript" src="resources/js/go.js" charset="euc-kr"></script>
<script type="text/javascript">
var searchRequestID = new XMLHttpRequest();
var searchRequestPW = new XMLHttpRequest();

function searchFunctionID() {

	searchRequestID.open("post", "MemberFindIDController?name=" + encodeURIComponent(document.getElementById("name").value) +
			"&y=" + encodeURIComponent(document.getElementById("y").value) +
			"&m=" + encodeURIComponent(document.getElementById("m").value) +
			"&d=" + encodeURIComponent(document.getElementById("d").value)
			, true);
	/*json요청/post 형식으로 전송 ///UserSearchServlet?userName=~~~인 uri로 지정/인코딩을 한다/userName을 가지는 id의 값을 가져온다.*/
	/* document: 현재 문서 내를 뜻함. */
	searchRequestID.onreadystatechange = searchIDProcessing;
	
	//searchRequestID.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	/* 정상적으로 받아오면 searchProcess실행 */
	searchRequestID.send(null);

}

function searchFunctionPW() {
	searchRequestPW.open("post", "MemberFindPWController?pw_id=" + encodeURIComponent(document.getElementById("pw_id").value) +
			"&pw_name=" + encodeURIComponent(document.getElementById("pw_name").value)
			, true);
	/*json요청/post 형식으로 전송 ///UserSearchServlet?userName=~~~인 uri로 지정/인코딩을 한다/userName을 가지는 id의 값을 가져온다.*/
	/* document: 현재 문서 내를 뜻함. */
	searchRequestPW.onreadystatechange = searchPWProcessing;
	
	//searchRequestID.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	/* 정상적으로 받아오면 searchProcess실행 */
	searchRequestPW.send(null);

}

function searchIDProcessing() {
	
	if (searchRequestID.readyState == 4 && searchRequestID.status == 200) {

		var insertResultID = document.getElementById("returnid");
		
		//기존에 추가된 dom 노드 삭제 
		if(insertResultID.hasChildNodes()){
			insertResultID.removeChild(insertResultID.firstChild);
		}
		
		var hh2 = document.createElement("h2"); // <h2></h2>
		hh2.textContent = searchRequestID.responseText; // <h2>ㅎㅎㅎ</h2>

		//returnid를 가지고있는 테그에 자식 노드를 더한다.
		document.getElementById("returnid").appendChild(hh2);
		
		//모달창 띄운다
		$("#resultID").modal();
		
		
		
	}
}

function searchPWProcessing() {
	
	if (searchRequestPW.readyState == 4 && searchRequestPW.status == 200) {
		
		var insertResultPW = document.getElementById("returnpw");
		
		//기존에 추가된 dom 노드 삭제 
		if(insertResultPW.hasChildNodes()){
			insertResultPW.removeChild(insertResultPW.firstChild);
		}
		
		var hh2 = document.createElement("h2"); // <h2></h2>
		hh2.textContent = searchRequestPW.responseText; // <h2>ㅎㅎㅎ</h2>

		//returnid를 가지고있는 테그에 자식 노드를 더한다.
		document.getElementById("returnpw").appendChild(hh2);
		
		//모달창 띄운다
		$("#resultPW").modal();
		
	}
}
</script>
</head>
<body>

	<!--
    you can substitue the span of reauth email for a input with the email and
    include the remember me checkbox
    -->
    <div class="container">
        <div class="card card-container">
            <!-- <img class="profile-img-card" src="//lh3.googleusercontent.com/-6V8xOA6M7BA/AAAAAAAAAAI/AAAAAAAAAAA/rzlHcD0KYwo/photo.jpg?sz=120" alt="" /> -->
            <img id="profile-img" class="profile-img-card" src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" />
			<!-- 아래껀 이전에 했던거 -->
            <%-- <img id="profile-img" class="profile-img-card" src="${cookie.lastLoginPicture.value }" /> --%>
            <p id="profile-name" class="profile-name-card"></p>
            <form class="form-signin" action="login.do" method="post" 
		name="loginForm" >
                <span id="reauth-email" class="reauth-email"></span>
                <input name="id" value="${cookie.lastLoginID.value }" required="required" oninvalid="loginCheck(this);"  oninput="loginCheck(this);" type="text" id="inputEmail" class="form-control" maxlength="10" placeholder="아이디"  autofocus>
                <input name="pw" required="required" oninvalid="loginCheck(this);" oninput="loginCheck(this);"  type="password" id="inputPassword" class="form-control" maxlength="10" placeholder="패스워드" >

                <div id="remember" class="checkbox">
                    <label>
                        <input name="im_autologin" type="checkbox">자동 로그인
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">로그인</button>
            </form><!-- /form -->
                <button class="btn btn-lg btn-primary btn-block btn-signin form-signin" data-toggle="modal" data-target="#add_project" >회원 가입</button>
            <a href="#" class="forgot-password" data-toggle="modal" data-target="#findID">
                	아이디 찾기
            </a>
			/
            <a href="#" class="forgot-password" data-toggle="modal" data-target="#findPW">
               		 패스워드 찾기
            </a>
            <p>
            <div align="center">${r }</div>
        </div><!-- /card-container -->
    </div><!-- /container -->
   <div align="center">
<a href="/smb" >
<img style="margin-bottom: -9px; width:100px;" src="resources/img/homebutton.png"></a>
   </div>
    
    <!-- 아이디 찾기 Modal -->
    <div id="findID" class="modal fade" role="dialog" >
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">아이디 찾기</h4>
				</div>

				<div class="modal-body">
				<input id="name" required="required" type="text"  placeholder="이름 입력"  class="form-control"   >
					<table>
						<tr>
							<td>
							   <select id="y" class="form-control">
						<c:forEach var="y" begin="${curYear - 50 }" end="${curYear }">
							<option>${y }</option>
						</c:forEach>
					</select>
							</td>

							<td style=" font-size: 14pt; width: 50px;">년</td>
							<td>
					<select class="form-control" id="m" >
                    	<c:forEach var="m" begin="1" end="12">
							<option>${m }</option>
						</c:forEach>
                    	
               		 </select>
							</td>
							<td style=" font-size: 14pt; width: 50px;">월</td>

							<td>
					<select class="form-control" id="d" >
                    	<c:forEach var="d" begin="1" end="31">
							<option>${d }</option>
						</c:forEach>
                    	
               		 </select>
							</td>
						<td style=" font-size: 14pt;">
						일
						</td>
						</tr>
					</table>

				</div>
				<div class="modal-footer">
					<button class="add-project" onclick="searchFunctionID()" type="button" >찾기</button>
				</div>

			</div>
		</div>
	</div>
	
	<!-- id 찾기  결과 Modal -->
    <div id="resultID" class="modal fade" role="dialog" >
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">아이디 찾기 결과</h4>
				</div>

				<div id = "returnid" class="modal-body" align="center">
				

				</div>
				<div class="modal-footer">
					<button  class="add-project" data-dismiss="modal" aria-label="Close" >닫기</button>
				</div>

			</div>
		</div>
	</div>
	
	
	 <!-- 패스워드 찾기 Modal -->
    <div id="findPW" class="modal fade" role="dialog" >
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">패스워드 찾기</h4>
				</div>

				<div class="modal-body">
				<input id="pw_id" required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);" type="text"  placeholder="아이디 입력"  class="form-control"   >
				<input id="pw_name" required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);" type="text"  placeholder="이름 입력"  class="form-control"   >
					

				</div>
				<div class="modal-footer">
					<button class="add-project" onclick="searchFunctionPW()" type="button">찾기</button>
				</div>
				
			</div>
		</div>
	</div>
	
	<!-- pw 찾기  결과 Modal -->
    <div id="resultPW" class="modal fade" role="dialog" >
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">패스워드 찾기 결과</h4>
				</div>

				<div id = "returnpw" class="modal-body" align="center">
				

				</div>
				<div class="modal-footer">
					<button  class="add-project" data-dismiss="modal" aria-label="Close" >닫기</button>
				</div>

			</div>
		</div>
	</div>

<!-- 회원 가입 Modal -->
	<div id="add_project" class="modal fade" role="dialog" >
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">회원 가입</h4>
				</div>
				<form action="join.do" method="post"  name="joinForm">
				<!-- <form action="MemberJoinController" method="post" enctype="multipart/form-data" name="joinForm"> -->
				<div class="modal-body">
					
			    <input name="id" required="required" oninvalid="joinCheck(this);"  oninput="joinCheck(this);" type="text" id="inputEmail1" class="form-control" maxlength="10" placeholder="아이디"  autofocus>
                <input name="pw" required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);"  type="password" id="inputEmail2" class="form-control" maxlength="10" placeholder="패스워드" >
					
				<input name="pwck" required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);" type="password" placeholder="패스워드 확인 입력" id="inputEmail3" class="form-control"  >
				<!-- <input name="name" required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);" type="text"  placeholder="이름 입력" id="inputEmail4" class="form-control"   > -->
        			<table>
        				<tr>
        					<td style="width:40px; ">성별</td>
        					
        						<td><div data-toggle="buttons"><label class="btn btn-default btn-circle btn-lg active"><input type="radio" name="sex" value="F"><i class="fas fa-female"></i></label>
        						<label class="btn btn-default btn-circle btn-lg"><input type="radio" name="sex" value="M" checked="checked"><i class="fas fa-male"></i></label></div></td>
        				</tr>
        			</table>

					<table>
						<tr>
							<td>
							   <select name="im_y" class="form-control">
						<c:forEach var="y" begin="${curYear - 50 }" end="${curYear }">
							<option>${y }</option>
						</c:forEach>
					</select>
							</td>

							<td style=" font-size: 14pt; width: 50px;">년</td>
							<td>
							        			<select class="form-control" name="im_m" >
                    	<c:forEach var="m" begin="1" end="12">
							<option>${m }</option>
						</c:forEach>
                    	
               		 </select>
							</td>
							<td style=" font-size: 14pt; width: 50px;">월</td>

							<td>
							        	<select class="form-control" name="im_d" >
                    	<c:forEach var="d" begin="1" end="31">
							<option>${d }</option>
						</c:forEach>
                    	
               		 </select>
							</td>
						<td style=" font-size: 14pt;">
						일
						</td>
						</tr>
					</table>

					<!-- <table>
        				<tr>
        					<td>사진</td>
        					
        						<td>
        						<input required="required" oninvalid="joinCheck(this);" oninput="joinCheck(this);" name="img" type="file">
        						</td>
        				</tr>
        			</table> -->
				
				

				</div>
				<div class="modal-footer">
					<button type="submit" class="add-project" >가입</button>
				</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>
