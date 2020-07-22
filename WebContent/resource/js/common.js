console.clear();

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
