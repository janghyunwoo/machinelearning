
var rating;
var movieid;
var maxPage;

// 사용자가 기존에 선택한 영화(모달창)을 종료하고 
// 다른 영화(모달창)를 선택하면 이전에 작업한 것들을 초기화 해주는 함수
function resetVar(select_movieid) {
	
	//선택된 movieid 저장
	movieid = select_movieid;
	// 선택된 영화의 rating 값을 null로
	rating = null;

	//선택된 영화의 collapse를 닫친 상태로 변환
	var cur1=document.getElementById("rating"+movieid);
	cur1.className="collapse filter-panel hide";
	
	// 선택된 영화의 collapse 버튼의 text를 평가하기로 바꾸기
	$("#ratingbutton"+movieid).text(function(i, text){
        return text = "평가하기";
    }) 
    
    // 선택된 영화의 별 버튼의 값을 초기화
	$("#comment"+movieid).val("");
	for (var i=1;i<=5;i++){
		var cur=document.getElementById("star"+i+"_"+movieid);
		cur.className="fa fa-star";
	}
	
	// 선택된 영화의 코맨트 및 페이지 개수 가져오기
	getrating(1);
	
}

//Movie Information -> 평가하기 버튼 토글
function likeToggle(textval){
//	alert(textval.text);
	 $(textval).text(function(i, text){
         return text === "평가하기" ? "닫기" : "평가하기";
     }) 
}

function add(ths,sno,now_movieid){
	rating = sno;
	
//	alert(rating+":"+movieid);
	
	for (var i=1;i<=5;i++){
	var cur=document.getElementById("star"+i+"_"+now_movieid);
	cur.className="fa fa-star";
	}

	for (var i=1;i<=sno;i++){
	var cur=document.getElementById("star"+i+"_"+now_movieid);
	if(cur.className=="fa fa-star")
	{
	cur.className="fa fa-star checked";
	}
	}

}

function ratingsave() {
 
	$.ajax({
		url : "rating.do",
		type : "post",
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		data : {
			"movieid" : movieid,
			"rating" : rating,
			"comment" :$("#comment"+movieid).val()
		},
		beforeSend : function() {

			//유효성 검사 등..
			if (rating==null) {
				alert("평가를 해주세요~");
				return false;
			}
			
			return true; 

		},
		success : function(result) {
			alert(result);
			//ratingSaveResult(result);
			
		},
		error : function(result) {
			confirm("서버 오류3");
		}

	});

}

function ratingSaveResult(result) {
	
	var insertResultUpdate = document.getElementById("returnUpdate");
	
	//기존에 추가된 dom 노드 삭제 
	if(insertResultUpdate.hasChildNodes()){
		insertResultUpdate.removeChild(insertResultUpdate.firstChild);
	}
	
	var hh2 = document.createElement("h2"); // <h2></h2>
	hh2.textContent = result; // <h2>ㅎㅎㅎ</h2>

	//returnid를 가지고있는 테그에 자식 노드를 더한다.
	document.getElementById("returnUpdate").appendChild(hh2);
	
	//모달창 띄운다
	$("#resultUpdate").modal();

}

function getrating(selectNum) {
	$.ajax({
		url : "getrating.do",
		type : "post",
		dataType : "json", // 데이터 타입을 제이슨 꼭해야함, 다른방법도 2가지있음
		cache : false, // 이걸 안쓰거나 true하면 수정해도 값반영이 잘안댐
		data : {
			"movieid" : movieid,
			"selectNum" : selectNum
		},
		success : function(data) {
			$("#tablebody"+movieid).html(""); 
			$("#pageNum"+movieid).html(""); 
			//$("#pageNum").html(""); 
			//confirm(data.pageCount);
			$.each(data.Comments, function(index, Comments) { // 이치를 써서 모든 데이터들을 배열에 넣음
				var items = [];
				var starating = [];
				
				for(var i=0;i<5;i++){
					if(i<Comments.rating){
						starating.push('<span class="fa fa-star checked"></span>');
					}else{
						starating.push('<span class="fa fa-star"></span>');
					}
				}
				
				items.push($("<td></td>", {
					addClass: "success",
					//아래 코드 동작하는 것! js에서도 el 코드 먹힌다.

					html : starating
				// 티알에 붙임,
				}).appendTo("#tablebody"+movieid).attr('data-title','평점'));
				
				if(Comments.comment == null){
					Comments.comment = "없음";
				}
				
				items.push("<td data-title='평가글'style='word-wrap: break-word;'>" + Comments.comment + "</td>");
				items.push("<td data-title='글쓴이'>" + Comments.id + "</td>");
				$("<tr/>", {
					addClass: "success",
					//아래 코드 동작하는 것! js에서도 el 코드 먹힌다.
					//addClass: "${ 1 == 2? 'warning':'danger'}",
					html : items
				// 티알에 붙임,
				}).appendTo("#tablebody"+movieid); // 그리고 그 tr을 테이블에 붙임
				
				
			});//each끝
			maxPage=data.pageCount;
			
			// 선택된 영화의 페이지 버튼 만들기
			makePagebutton(selectNum);
			
			
		},
		error : function(result) {
			confirm("서버 오류2");
		}
 
	});
}

function makePagebutton(selectNum){
	var startPageNum = parseInt((selectNum-1)/10)*10+1;
	var endPageNum;
	if((startPageNum + 9)>maxPage){
		
		endPageNum = maxPage;
	}else{
		endPageNum = startPageNum + 9;
	}
	
	//Page.Previous 버튼
	var page_previous = [];
	page_previous.push('<li class="page-item" id="prevbutton'+movieid+'" onclick="previousPage('+selectNum+')">'
			+'<a class="page-link" aria-label="Previous">'
			+'<span aria-hidden="true">&laquo;</span>'
			+'<span class="sr-only">Previous</span>'
			+'</a></li>');

	//page.pagenum 버튼 최대 10개
	var page = [];
	for (var i = startPageNum; i <= endPageNum ; i++) {
		
		page.push('<li class="page-item" id="pagebutton'+movieid+'_'+i+'">'
				+'<a class="page-link" onclick="getrating('+i+')">'+i+'</a>'
				+'</li>');
	}
	
	// page.next 버튼
	var page_next = [];
	page_next.push('<li class="page-item" id="nextbutton'+movieid+'" onclick="nextPage('+selectNum+')">'
			+'<a class="page-link" aria-label="Next">'
			+'<span aria-hidden="true">&raquo;</span>'
			+'<span class="sr-only">Next</span>'
			+'</a></li>');
	
	
	$("<ul></ul>", {
		addClass: "pagination justify-content-center",
	// 티알에 붙임,
	}).css('flex-wrap','wrap').css('overflow-y','auto').html(function() {
		var str='';
		for (var i = 0; i < page.length; i++) {
			str+=page[i];
		}
		return page_previous+str+page_next;
	}).appendTo("#pageNum"+movieid); // 그리고 그 tr을 테이블에 붙임
	
	// 버튼 css 요소 조정(비활성 활성 기능)
	pageButtonCSS(selectNum);
}

function pageButtonCSS(selectnum) {
	endPageStart = parseInt((maxPage -1) / 10)*10; //maxPage가 13이면 10을 반환
	
	$("#pagebutton"+movieid+"_"+selectnum).addClass("active");
	
	if(0<selectnum && selectnum < 11){
		$("#prevbutton"+movieid).addClass("disabled").removeAttr('onclick');
	}
	
	if(endPageStart < selectnum && selectnum <= maxPage ){
		$("#nextbutton"+movieid).addClass("disabled").removeAttr('onclick');
	}
	
}


function previousPage(selectNum){
	var previousPageNum = parseInt((selectNum-1)/10)*10-9;
	getrating(previousPageNum);
}

function nextPage(selectNum){
	var nextPageNum = parseInt((selectNum-1)/10)*10+11;
	getrating(nextPageNum);
}
