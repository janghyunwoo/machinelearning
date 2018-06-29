<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Movie Recommend</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/bootstrap/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/bootstrap/css/4-col-portfolio.css" rel="stylesheet">
    
    <!-- Bootstrap core JavaScript -->
    <script src="resources/bootstrap/vendor/jquery/jquery.min.js"></script>
    <script src="resources/bootstrap/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script> -->
	<link rel="shortcut icon" type="image⁄x-icon" href="resources/img/movie.png">

<style>

/*  body{

  font-family: "Helvetica Nene", Helvetica, Arial, 맑은 고딕;,"malgun gothic", sans-serif;

 } */

 </style>

<script type="text/javascript">
/* function aaa() {
	alert(":::ss");
} */

</script>
  </head>

  <body>

    <!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">★Movie Recommend★</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link" href="top100.go">Top
							100 <span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item"><a class="nav-link" href="#">유저기반 추천</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">영화기반 추천</a>
					</li>
				</ul>
				<ul class="navbar-nav ml-auto">
					<c:if test="${sessionScope.loginMember != null }">
						<li class="nav-item"><a class="nav-link" href="#">My Page</a></li>
						<li class="nav-item"><a class="nav-link" href="logout.do">로그 아웃</a></li>
					</c:if>
					<c:if test="${sessionScope.loginMember == null }">
						<li class="nav-item"><a class="nav-link" href="login.go">로그인/회원가입</a></li>
						
					</c:if>
				</ul>
				<!-- <div id="custom-search-input">
						<div class="input-group">
							<form action="http://www.naver.com" method="get" role="search">
								<input type="text" class="search-query form-control"
									placeholder="Search" />
							</form>
						</div>
					</div> -->
				<div class="input-group col-sm-3" >
					<input type="text" class="form-control" placeholder="Search" name="q">
				</div>
			</div>
		</div>
	</nav>
<%-- <button onclick="aaa()">${ContentPage} ss</button>ssdfsdfasfasfsdj --%>
	<!-- Page Content -->
    <div class="container">
		<jsp:include page="${ContentPage}"></jsp:include>
    </div>
    <!-- Footer -->
    <footer class="py-5 bg-dark" >
      <div class="container">
        <p class="m-0 text-center text-white">Copyright &copy; Your Website 2018</p>
      </div>
      <!-- /.container -->
    </footer>



  </body>

</html>