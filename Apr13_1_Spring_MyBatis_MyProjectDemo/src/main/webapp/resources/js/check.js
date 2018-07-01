function writeReplCheck(writeForm){
	// submit을 수행한 form속의 isr_txt
	var txtField = writeForm.isr_txt;
	
	if(isEmpty(txtField)){
		alert("내용 확인");
		txtField.focus();
		return false;
	}
	return true;
}
function snsSearchCheck(){
	// 이 문서에서 이름이 snsSearchForm인 form속의 is_txt
	var txtField = document.snsSearchForm.is_txt;
	if (isEmpty(txtField)) {
		alert("입력 확인");
		txtField.value = "";
		txtField.focus();
		return false;
	}
	return true;
}
function snsWriteCheck() {
	var txtField = document.snsWriteForm.is_txt;
	if (isEmpty(txtField)) {
		alert("입력 확인");
		txtField.value = "";
		txtField.focus();
		return false;
	}
	return true;
} 
/*function updateCheck() {
	var idField = document.updateForm.im_id;
	var pwField = document.updateForm.im_pw;
	var pwChkField = document.updateForm.im_pwChk;
	var nameField = document.updateForm.im_name;
	var addrField = document.updateForm.im_addr;
	var imgField = document.updateForm.im_img;

	if (isEmpty(idField) || containsHangul(idField)) {
		alert("id 확인");
		idField.value = "";
		idField.focus();
		return false;
	} else if (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM")) {
		alert("pw 확인");
		pwField.value = "";
		pwChkField.value = "";
		pwField.focus();
		return false;
	} else if (isEmpty(nameField)) {
		alert("이름 확인");
		nameField.value = "";
		nameField.focus();
		return false;
	} else if (isEmpty(addrField)) {
		alert("거주지 확인");
		addrField.value = "";
		addrField.focus();
		return false;
	} else if (isEmpty(imgField)) {
		return true;
	} else if (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
			&& isNotType(imgField, ".jpg") && isNotType(imgField, ".bmp")) {
		alert("사진 똑바로");
		imgField.value = "";
		imgField.focus();
		return false;
	}
	return true;
}*/

function updateCheck(docBox) {

	var pwField = document.updateForm.pw;
	var pwChkField = document.updateForm.pwck;
	var nameField = document.updateForm.name;
	var sexField = document.updateForm.sex;
	var imgField = document.updateForm.img;
	
	//confirm(sexField.name+":"+docBox.name);
	if ((pwField.name == docBox.name) && (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM"))) {
/*		alert(isEmpty(pwField));
		alert(notEquals(pwField, pwChkField));
		alert(lessThan(pwField, 4));
		alert(notContains(pwField, "1234567890"));
		alert("pw 확인");*/
		docBox.setCustomValidity('pw 확인!!');
		//pwField.value = "";
		//pwChkField.value = "";
		pwField.focus();
		return false;
	} else if ((nameField.name == docBox.name) && isEmpty(nameField)) {
		//alert("이름 확인");
		docBox.setCustomValidity('이름 확인!!');
		nameField.value = "";
		nameField.focus();
		return false;
	} else if ((imgField.name == docBox.name) && (isEmpty(imgField)
			|| (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
					&& isNotType(imgField, ".jpg") && isNotType(imgField,
					".bmp")))) {
		//alert("사진 똑바로");
		docBox.setCustomValidity('사진 확인!!');
		imgField.value = "";
		imgField.focus();
		return false;
	}else {
		docBox.setCustomValidity('');
    }
	
	return true;
}

function joinCheck(docBox) {

	var idField = document.joinForm.id;
	var pwField = document.joinForm.pw;
	var pwChkField = document.joinForm.pwck;
//	var nameField = document.joinForm.name;
	var sexField = document.joinForm.sex;
//	var imgField = document.joinForm.img;

	if ((idField.name == docBox.name) && ( isEmpty(idField) || containsHangul(idField))) {

		docBox.setCustomValidity('id 확인!!');
			idField.value = "";
//			idField.focus();
			return false;
		
	} else if ((pwField.name == docBox.name) && (isEmpty(pwField) || notEquals(pwField, pwChkField)
			|| lessThan(pwField, 4) || notContains(pwField, "1234567890")
			|| notContains(pwField, "qwertyuiopasdfghjklzxcvbnm")
			|| notContains(pwField, "QWERTYUIOPASDFGHJKLZXCVBNM"))) {
/*		alert(isEmpty(pwField));
		alert(notEquals(pwField, pwChkField));
		alert(lessThan(pwField, 4));
		alert(notContains(pwField, "1234567890"));
		alert("pw 확인");*/
		docBox.setCustomValidity('pw 확인!!');
		//pwField.value = "";
		//pwChkField.value = "";
//		pwField.focus();
		return false;
	} /*else if ((nameField.name == docBox.name) && isEmpty(nameField)) {
		//alert("이름 확인");
		docBox.setCustomValidity('이름 확인!!');
		nameField.value = "";
		nameField.focus();
		return false;
	}*/  else if ((sexField.name == docBox.name) && isEmpty(sexField)) {
		//alert("이름 확인");
		docBox.setCustomValidity('성별 확인!!');
		sexField.value = "";
//		sexField.focus();
		return false;
	} /*else if ((imgField.name == docBox.name) && (isEmpty(imgField)
			|| (isNotType(imgField, ".png") && isNotType(imgField, ".gif")
					&& isNotType(imgField, ".jpg") && isNotType(imgField,
					".bmp")))) {
		//alert("사진 똑바로");
		docBox.setCustomValidity('사진 확인!!');
		imgField.value = "";
		imgField.focus();
		return false;
	}*/else {
		docBox.setCustomValidity('');
    }
	
	return true;
}


function loginCheck(textbox) {
	var idField = document.loginForm.id;
	var pwField = document.loginForm.pw;

	//alert(12121);
	//alert(idField.name);
	//alert(textbox.name);
	idField.setCustomValidity('아이디를 입력하세요!');
    if ((idField.name == textbox.name) && isEmpty(idField)) {
        textbox.setCustomValidity('아이디를 입력하세요!');
        idField.value = "";
		idField.focus();
		return false;
    }
    else if ((pwField.name == textbox.name) &&isEmpty(pwField)){
        textbox.setCustomValidity('패스워드를 입력하세요.');
        pwField.value = "";
		pwField.focus();
		return false;
    }
    else {
       textbox.setCustomValidity('');
    }
    return true;
}

//<input>을 넣었을때
//그 input이 비어있으면 true
//그 input에 글자가 있으면 false
function isEmpty(field) {

	return (!field.value) ? true : false;
}

//<input>, 최소 글자 수를 넣었을때
//그 input에 쓴 내용이 그 글자수 보다 낮으면 true
//그 input에 쓴 내용이 그 글자수 보다 높으면 false
function lessThan(field, charCount) {
	return (field.value.length < charCount);
}

//<input>을 넣었을때
//그 input에 한글 있으면 true
//그 input에 한글 없으면 false
function containsHangul(field) {

	var t = "1234567890qwertyuiopasdfghjklzxcvbnm";
	for (var i = 0; i < field.value.length; i++) {
		if (t.indexOf(field.value.toLowerCase()[i]) == -1) {
			return true;
		}
	}
	return false;
}

//<input>, 문자열 세트 넣으면
//그 input에 문자열에 있는 글자가 없으면 true
//그 input에 문자열에 있는 글자가 있으면 false
function notContains(field, charSet){
	for (var i = 0; i < charSet.length; i++) {
		if( field.value.indexOf(charSet[i]) != -1){
			return false;
		}
	}	
	return true;
}

//<input> 두 개 넣었을때
//그 두 개 값 다르면 true
//그 두 개 값 같으면 false
function notEquals(field1, field2){
	return (field1.value != field2.value);
}

//<input> 넣었을 때
//거기에 숫자 아닌게 있으면 true
//거기에 숫자만 있으면 false
function isNotNumber(field){
	return isNaN(field.value);
}

//<input type="file">, 확장자 넣었을 때
//그 파일이 아니면 true
//그 파일이 맞으면 false
function isNotType(field, type){
	// field.value // 업로드 하려는 파일명 xxx.PNG
	return (field.value.toLowerCase().indexOf(type) == -1);
}


