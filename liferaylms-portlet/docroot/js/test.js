(function ($) {
		   $.fn.liveDraggable = function (opts) {
			  this.on("mouseover", function() {
				 if (!$(this).data("init")) {
					$(this).data("init", true).draggable(opts);
				 }
			  });
			  return this;
		   };
		   $.fn.serial = function() {
		    	var content;
		        var array = [];
		        var $elem = $(this);
		        var n;
		        $elem.each(function(i) {
		            var menu = this.id;
		            
		            n= "_execactivity_WAR_liferaylmsportlet_" + menu + "_contentlist";
		            content = document.getElementById(n);
		            content.value="";
		            $('li', this).each(function(e) {
		                array.push(menu + '[' + e + ']=' + this.id);
		                content.value = content.value + menu + '[' + e + ']=' + this.id + '&';    
		            });
		        });
		        return array.join('&');
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
		$("div.question-page-current ul.sortable").sortable({
	        connectWith: '.sortable',
	        update: function(event, ui) {
	        	var position = $('.sortable').serial();
	        }
	    });
	});