$(document).ready(function() {

	$('.lms-tree ul li.option-more ul').hide();
	
	$('.lms-tree ul li span.desplegar').click(function() {
		var enlaceEstado = $(this).parent().hasClass('option-more');
		if(enlaceEstado == 1){
			$(this).parent().removeClass("option-more");
			$(this).parent().addClass("option-less");
			$(this).parent().find('ul').slideDown();
		}else{
			$(this).parent().removeClass("option-less");
			$(this).parent().addClass("option-more");
			$(this).parent().find('ul').slideUp();
		}

	});	
	
});

