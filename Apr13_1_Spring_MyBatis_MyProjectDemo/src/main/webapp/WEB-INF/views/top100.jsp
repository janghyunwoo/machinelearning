<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<style type="text/css">
			.bootimg{
			width:60%;
			height:auto;
			}
		</style>
	</head>

    <!-- Page Content -->
    <div class="container">

      <!-- Page Heading -->
      <h1 class="my-4">TOP 100
      </h1>

      <div class="row">
      <c:forEach var="d" items="${MovieContent }">
        <div class="col-lg-3 col-md-4 col-sm-6 portfolio-item">
          <div class="card h-100" >
            <a href="#" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }"><img class="card-img-top" src="${d.img}" alt=""></a>
            <div class="card-body">
              <h4 class="card-title">
                <a href="#" data-toggle="modal" data-target=".bd-example-modal-lg${d.movieid }">${d.title }</a>
              </h4>
              <p class="card-text"><fmt:formatDate value="${d.releaseday }" dateStyle="long" /> ${d.genreid }</p>
            </div>
          </div>
        </div>
      </c:forEach>
      </div>
      <!-- /.row -->

      <!-- Pagination -->
      <ul class="pagination justify-content-center">
        <li class="page-item">
          <a class="page-link" href="#" aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
            <span class="sr-only">Previous</span>
          </a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#">1</a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#">2</a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#">3</a>
        </li>
        <li class="page-item">
          <a class="page-link" href="#" aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
            <span class="sr-only">Next</span>
          </a>
        </li>
      </ul>

    </div>
    <!-- /.container -->
    
    
<c:forEach var="d" items="${MovieContent }">
<div class="modal fade bd-example-modal-lg${d.movieid }" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">

      <div class="modal-header">
        <h5 class="modal-title h4" id="myLargeModalLabel"> 자세히 보기 </h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
					<table class="table table-borderless">

						<tbody>
							<tr>
								<th rowspan="3" style="width: 50%"><img
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
						</tbody>
					</table>
					<table class="table table-borderless">
						<tbody>
							<tr>
								<th rowspan="2" style="width: 65%">
									<form>
										<div>
											<label for="comment">◆평가하기</label>
										</div>
										<div> <textarea
												class="form-control" rows="5" id="comment"></textarea>
										</div>
										<!-- </span> -->

									</form>
								</th>
								<th style="width: 35%">제목</th>
							</tr>
							<tr>
								<th>dasfeasf</th>
							</tr>
						</tbody>
					</table>

					<!-- <form >
							<div><label for="comment">◆평가하기</label></div>
						<span  style="width: 70%">
							<textarea class="form-control" rows="5" id="comment"></textarea>
						</span>

					</form> -->



				</div>
      <div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
		</div>
    </div>
  </div>
</div>
</c:forEach>


</html>
