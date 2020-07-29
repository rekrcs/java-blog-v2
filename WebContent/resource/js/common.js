console.clear();

// lib 시작 
String.prototype.replaceAll = function(org, dest) {
	return this.split(org).join(dest);
}

function getUriParams(url) {
	url = url.trim();
	url = url.replaceAll('&amp;', '&');
	if (url.indexOf('#') !== -1) {
		var pos = url.indexOf('#');
		url = url.substr(0, pos);
	}

	var params = {};

	url.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) {
		params[key] = value;
	});
	return params;
}

function jq_attr($el, attrName, elseValue) {
	var value = $el.attr(attrName);

	if (value === undefined || value === "") {
		return elseValue;
	}

	return value;
}

// lib 끝

function MobileSideBar__init() {
	var $btnToggleMobileSideBar = $('.btn-toggle-mobile-side-bar');

	$btnToggleMobileSideBar.click(function() {
		if ($(this).hasClass('active')) {
			$(this).removeClass('active');
			// 사이드바 안보이게함
			$('.mobile-side-bar').removeClass('active');
		} else {
			$(this).addClass('active');
			// 사이드바 보이게함
			$('.mobile-side-bar').addClass('active');
		}
	});
}

$(function() {
	MobileSideBar__init();
});

/* 글작성중 페이지 빠져가나가 경고 라이브러리 */
/*	var onBeforeUnloadSetted = false;
	var onBeforeUnload = function(e) {
		return '떠나시겠습니까?'; // 요새 브라우저는 이 메시지가 아닌 자체의 메세지가 나옵니다.
	};

	function applyOnBeforeUnload() {
		if (onBeforeUnloadSetted)
			return;
		$(window).bind('beforeunload', onBeforeUnload); // 떠날 때 실행되는 함수를 등록
		onBeforeUnloadSetted = true;
	}

	function removeOnBeforeUnload() {
		$(window).unbind('beforeunload', onBeforeUnload); // 떠날 때 실행되는 함수를 해제
		onBeforeUnloadSetted = false;
	} */
	/*글작성중 페이지 빠져가나가 경고 라이브러리 끝 */
