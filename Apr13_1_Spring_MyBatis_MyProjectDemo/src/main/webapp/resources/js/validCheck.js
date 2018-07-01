// <input>을 넣었을때
// 그 input이 비어있으면 true
// 그 input에 글자가 있으면 false
function isEmpty(field) {
	return (!field.value) ? true : false;
}

// <input>, 최소 글자 수를 넣었을때
// 그 input에 쓴 내용이 그 글자수 보다 낮으면 true
// 그 input에 쓴 내용이 그 글자수 보다 높으면 false
function lessThan(field, charCount) {
	return (field.value.length < charCount);
}

// <input>을 넣었을때
// 그 input에 한글 있으면 true
// 그 input에 한글 없으면 false
function containsHangul(field) {
	alert("dd");
	var t = "1234567890qwertyuiopasdfghjklzxcvbnm";
	for (var i = 0; i < field.value.length; i++) {
		if (t.indexOf(field.value.toLowerCase()[i]) == -1) {
			return true;
		}
	}
	return false;
}

// <input>, 문자열 세트 넣으면
// 그 input에 문자열에 있는 글자가 없으면 true
// 그 input에 문자열에 있는 글자가 있으면 false
function notContains(field, charSet){
	for (var i = 0; i < charSet.length; i++) {
		if( field.value.indexOf(charSet[i]) != -1){
			return false;
		}
	}	
	return true;
}

// <input> 두 개 넣었을때
// 그 두 개 값 다르면 true
// 그 두 개 값 같으면 false
function notEquals(field1, field2){
	return (field1.value != field2.value);
}

// <input> 넣었을 때
// 거기에 숫자 아닌게 있으면 true
// 거기에 숫자만 있으면 false
function isNotNumber(field){
	return isNaN(field.value);
}

// <input type="file">, 확장자 넣었을 때
// 그 파일이 아니면 true
// 그 파일이 맞으면 false
function isNotType(field, type){
	// field.value // 업로드 하려는 파일명 xxx.PNG
	return (field.value.toLowerCase().indexOf(type) == -1);
}
































