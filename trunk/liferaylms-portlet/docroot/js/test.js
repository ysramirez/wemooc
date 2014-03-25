AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable',function(A) {

	A.all('div.question-page-current ul.sortable').each(function(node) {
		new A.Sortable(
		{
			container: node,
		    nodes: 'li'
		});
	});
});

(function ($) {
		   $.fn.liveDraggable = function (opts) {
			  this.on("mouseover", function() {
				 if (!$(this).data("init")) {
					$(this).data("init", true).draggable(opts);
				 }
			  });
			  return this;
		   };
		}(jQuery));
  
$(document).ready(function() {
		$('.draganddrop').each(function() {
			var elem = '#'+$(this).attr('id');
			$(elem + ' > .items > div').liveDraggable({
				helper: function(event) {
					var $this = $(this);
					var helperList = $('<ul class="draggable-helper">');
					helperList.append($this.clone());
					return helperList;
				},
				drag: function( event, ui ) {
					ui.offset = {"top" : "0", "left" : "0"};
					$(ui.helper).html(ui.draggable);        
				},
				cursorAt: { top: 0, left: 0 }
			});
			
			$(elem + ' > .drop > .drop-containers > div').liveDraggable({
				helper: function(event) {
					var $this = $(this);
					if(!$this.hasClass('base')){
						var helperList = $('<ul class="draggable-helper">');
						helperList.append($this.clone());
						return helperList;
					}
				},
				drag: function( event, ui ) {
					var $this = $(this).parent();
					if(!$this.hasClass('base')){
						ui.offset = {"top" : "0", "left" : "0"};
						$(ui.helper).html(ui.draggable); 
					}
				},
				cursorAt: { top: 0, left: 0 }
			});
			
			$(elem + ' > .drop > .drop-containers').droppable({
				tolerance : 'pointer',
				accept: elem + ' > .items > div',
				drop: function(event, ui) {
					var $this = $(this);
					if (!$this.hasClass('occupied')){
						$this.text('');
						$this.append(ui.draggable);
						$this.addClass('occupied');
						$this.removeClass('base');
						$('input[name="' + $this.attr("name")+'hidden"]').val(ui.draggable.attr('id'));
					}
				}
			});
			$(elem + ' > .items').droppable({
				tolerance : 'pointer',
				accept: elem + ' > .drop > .drop-containers > div',
				drop: function(event, ui) {
					var $this = $(this);
					var padre = ui.draggable.parent();
					$this.append(ui.draggable);
					padre.text(
						Liferay.Language.get(
							'drop',
							(padre.attr('id')).replace(/[^\d]/g, '')
						)
					);
					padre.removeClass('occupied');
					padre.addClass('base');
					$('input[name="' + padre.attr("name")+'hidden"]').val(-1);
				}
			});
			
		});
	});