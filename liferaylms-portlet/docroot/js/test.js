AUI().ready('node','aui-io-request','aui-parse-content','aui-sortable','dd-constrain', 'dd-proxy', 'dd-drop',function(A) {

	A.all('div.question-page-current ul.sortable').each(function(node) {
		new A.Sortable(
		{
			container: node,
		    nodes: 'li'
		});
	});
	
    //Listen for all drag:start events
    A.DD.DDM.on('drag:start', function(e) {
        //Get our drag object
        var drag = e.target;
        //Set some styles here
        drag.get('node').setStyle('opacity', '.25');
        drag.get('dragNode').set('innerHTML', drag.get('node').get('innerHTML'));
        drag.get('dragNode').setStyles({
            opacity: '.5',
            borderColor: drag.get('node').getStyle('borderColor'),
            backgroundColor: drag.get('node').getStyle('backgroundColor')
        });
    });
    //Listen for a drag:end events
    A.DD.DDM.on('drag:end', function(e) {
        var drag = e.target;
        //Put our styles back
        drag.get('node').setStyles({
            visibility: '',
            opacity: '1'
        });
    });
    	
	A.all('.draganddrop').each(function(node) {
		var elem = '#'+node.attr('id');
		
		A.all(elem + ' > .items > div').each(function(v,k) {
			var dd = new A.DD.Drag({
				container: elem,
	        	node: v,
	        	groups: ['group1', 'group2']
			}).plug(A.Plugin.DDProxy, {
				moveOnEnd: false
			}).plug(A.Plugin.DDConstrained, {
		        constrain2node: elem
		    });
		});
		
	    
		A.all(elem + ' > .drop > .drop-containers > div').each(function(v, k) {
			var dd = new A.DD.Drag({
				node: v,
				groups: ['group2']
			}).plug(A.Plugin.DDProxy, {
				moveOnEnd: false
			}).plug(A.Plugin.DDConstrained, {
		        constrain2node: elem
		    });
		});
		
		A.all(elem + ' > .drop > .drop-containers').each(function(v, k) {
			var droppable = new A.DD.Drop({
	            node: v,
	            groups: ['group1']
	        });
		
			droppable.on('drop:hit', function(e) {
				var drop = e.drop.get('node'),
	            drag = e.drag.get('node');
				var padre = drag.get('parentNode');
				if (!drop.hasClass('occupied') && drag.attr('tagName').toLowerCase() === 'div' && padre.hasClass('items')) {
					drop.text('');
					drop.append(drag);
					drop.addClass('occupied');
					drop.removeClass('base');
					A.one('input[name="' + drop.attr("name")+'hidden"]').val(drag.attr('id'));
				}
		    });
		});
		
		
		A.all(elem + ' > .items').each(function(v,k) {
			var droppable = new A.DD.Drop({
	            node: v,
	            groups: ['group2']
	        });
			
			droppable.on('drop:hit', function(e) {
				var drop = e.drop.get('node'),
	            drag = e.drag.get('node');
				var padre = drag.get('parentNode');
				if (drag.attr('tagName').toLowerCase() === 'div' && padre.hasClass('drop-containers')) {
					drop.append(drag);
					padre.text(
						Liferay.Language.get(
							'drop',
							(padre.attr('id')).replace(/[^\d]/g, '')
						)
					);
					padre.removeClass('occupied');
					padre.addClass('base');
					A.one('input[name="' + padre.attr("name")+'hidden"]').val(-1);
				}
		    });
		});
		
	});
	
});
/*
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
*/