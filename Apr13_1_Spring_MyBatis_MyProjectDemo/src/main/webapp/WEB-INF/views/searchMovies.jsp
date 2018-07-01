<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<script src="resources/js/selcetMovieProcessing.js"></script>
<!-- <script src="resources/js/top100PageProcessing.js"></script> -->
<link href="resources/css/top100.css" rel="stylesheet">

<style type="text/css">
</style>

<script type="text/javascript">
</script>

</head>

<!-- Page Content -->
    <div class="container">
      <!-- Page Heading -->
      <h1 class="my-4">TOP 100
      </h1>

      <div id="movieContent" class="row">
      <!-- 	<div id="movieContent"></div> -->
       <c:forEach var="d" items="${MovieContent }">
        <div class="col-lg-3 col-md-4 col-sm-6 portfolio-item">
          <div class="card h-100" >
            <a href="#" onclick="resetVar(${d.movieid })" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }"><img class="card-img-top" src="${d.img}" alt=""></a>
            <div class="card-body">
              <h4 class="card-title">
                <a href="#" onclick="resetVar(${d.movieid })" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }">${d.title }</a>
              </h4>
              <p class="card-text">출시: <fmt:formatDate value="${d.releaseday }" dateStyle="long" /></p>
              <p class="card-text">장르: ${d.genreid }</p>
              <p class="card-text">평점: ${d.avgrating }</p>
            </div>
          </div>
        </div>
      </c:forEach> 
      </div>
      <!-- /.row -->
      
      <!-- Pagination -->
	<div id='moviePageNum' style="align-content: center;">
		<ul class="pagination justify-content-center" style="flex-wrap: wrap; overflow-y: auto;">
			<li class="page-item ${(0<pageNow && pageNow<11)?'disabled':''}">
				<fmt:parseNumber var="pages1" integerOnly="true" value="${(pageNow-1)/10}"/>
				<a class="page-link" href="top100.go?selectNum=${pages1*10-9}" aria-label="Previous" >
					<span aria-hidden="true">&laquo;</span> <span class="sr-only">Previous</span>
				</a>
			</li>
			<c:forEach var="a" begin="${pageBegin }" end="${pageEnd}" step="1">
				<li class="page-item ${pageNow == a? 'active': ''}" <%-- id="pagebutton${a}" --%> >
					<a class="page-link" href="top100.go?selectNum=${a }"<%-- onclick="getTop100Page(${a})" --%>>${a}</a>
				</li>
			</c:forEach>
			<li class="page-item ${(pageEndBegin<pageNow && pageNow<=pageMax)?'disabled':''}">
				<fmt:parseNumber var="pages2" integerOnly="true" value="${(pageNow-1)/10 }"/>
				<a class="page-link" href="top100.go?selectNum=${pages2*10+11}" aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
					<span class="sr-only">Next</span>
				</a>
			</li>
		</ul>
	</div><fmt:parseNumber var="pages" integerOnly="true" value="${totalCnt/rowsPerPage }"/>
</div>
    <!-- /.container -->


<c:forEach var="d" items="${MovieContent }">
	<div class="modal fade bd-example-modal-lg${d.movieid }" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<div class="modal-header">
					<h5 class="modal-title h4" id="myLargeModalLabel">Movie
						Information</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<table class="table table-borderless h5"
						style="font-weight: normal;">
						<tbody>
							<tr>
								<th rowspan="4" style="width: 50%"><img
									class="rounded mx-auto d-block bootimg" src="${d.img}" alt=""></th>
								<th style="width: 10%">제목</th>
								<th style="width: 40%">${d.title }</th>
							</tr>
							<tr>
								<td>출시</td>
								<td><fmt:formatDate value="${d.releaseday }"
										dateStyle="long" /></td>
							</tr>
							<tr>
								<td>장르</td>
								<td>${d.genreid }</td>
							</tr>
							<tr>
								<td colspan="2" class="align-middle ">
									<button id="ratingbutton${d.movieid }"
										onclick="likeToggle(this)" type="button"
										class="btn btn-outline-info btn-lg btn-block"
										data-toggle="collapse" data-target="#rating${d.movieid }">평가하기</button>
								</td>
							</tr>
						</tbody>
					</table>
					<hr>
					<form name="ratingfrom${d.movieid }" onsubmit="return false;">
						<div id="rating${d.movieid }" class="collapse filter-panel">
							<table class="table table-borderless">
								<tbody>
									<tr>
										<th rowspan="2" style="width: 65%">
											<div>
												<label for="comment">◆평가하기</label>
											</div>
											<div>
												<textarea class="form-control" rows="5"
													id="comment${d.movieid }"></textarea>
											</div> <!-- </span> -->


										</th>
										<th rowspan="2" style="width: 35%">

											<table class="table table-borderless">
												<tbody>
													<tr>
														<td class="h3"><span class="fa fa-star"
															id="star1_${d.movieid }"
															onclick="add(this,1,${d.movieid })"></span> <span
															class="fa fa-star" id="star2_${d.movieid }"
															onclick="add(this,2,${d.movieid })"></span> <span
															class="fa fa-star" id="star3_${d.movieid }"
															onclick="add(this,3,${d.movieid })"></span> <span
															class="fa fa-star" id="star4_${d.movieid }"
															onclick="add(this,4,${d.movieid })"></span> <span
															class="fa fa-star" id="star5_${d.movieid }"
															onclick="add(this,5,${d.movieid })"></span></td>
													</tr>
													<tr>
														<td>
															<button class="add-project" onclick="ratingsave()"
																type="button">등록</button>
														</td>
													</tr>
												</tbody>
											</table>
										</th>
									</tr>
								</tbody>
							</table>
						</div>
					</form>
						<div id="no-more-tables">
							<table class="table table-striped cf" style="table-layout : fixed;">
<!-- 							<table class="table table-striped cf"> -->
								<thead class="cf">
									<tr class="text-center">
										<th style="width: 20%">평점</th>
										<th style="width: 60%">평가글</th>
										<th style="width: 20%">글쓴이</th>
									</tr>
								</thead>
								<tbody id='tablebody${d.movieid }'>
									<!-- js로 동적으로 추가해주는 영역 -->
								</tbody>
							</table>
						</div>
						<div id='pageNum${d.movieid }'  style="align-content: center;"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>
</c:forEach>

<!-- 평가 등록 후 출력 모달(예정) -->
<div id="resultUpdate" class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header login-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">등록 결과</h4>
				</div>

				<div id = "returnUpdate" class="modal-body" align="center">
				

				</div>


			</div>
		</div>
	</div>


</html>
