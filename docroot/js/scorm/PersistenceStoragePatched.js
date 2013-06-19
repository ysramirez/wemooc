Type.createNamespace('Player');

Player.PersistentStateStorage.prototype.$3 = function($p0) {
	try {
		this.$0.setObjectItem('scormpool', $p0);
		this.$2 = true;
		var element = window;
		var eventName = "scormevent";
		if (document.createEvent) {
			var evt = document.createEvent("HTMLEvents");
		    evt.initEvent(eventName, false, true);
		    element.dispatchEvent(evt);
		} else {
			element.fireEvent("on"+eventName);
		}
	} catch ($0) {
		alert('Error! Can\'t save data to Local Storage. Problem can be related to storage size limit.');
		this.$2 = false;
	}
}

Player.ContentPlayer.prototype.$1B = function() {
	this.$7 = document.getElementById('navigationContainer');
	this.$8 = Delegate.create(this, this.$1D);
	if (isNullOrUndefined(this.$7)) {
		this.$7 = document.createElement('div');
		this.$7.id = 'navigationContainer';
		this.$7.style.display = 'none';
		this.$10 = this.$1C('btnPrevious',
				PlayerConfiguration.BtnPreviousLabel);
		this.$11 = this.$1C('btnContinue',
				PlayerConfiguration.BtnContinueLabel);
		this.$12 = this.$1C('btnExit', PlayerConfiguration.BtnExitLabel);
		this.$13 = this.$1C('btnExitAll',
				PlayerConfiguration.BtnExitAllLabel);
		this.$14 = this.$1C('btnAbandon',
				PlayerConfiguration.BtnAbandonLabel);
		this.$15 = this.$1C('btnAbandonAll',
				PlayerConfiguration.BtnAbandonAllLabel);
		this.$16 = this.$1C('btnSuspendAll',
				PlayerConfiguration.BtnSuspendAllLabel);
		var $0 = document.getElementById('placeholder_navigationContainer');
		if (!isNullOrUndefined($0)) {
			$0.appendChild(this.$7);
		}
	}
}

Player.ContentPlayer.prototype.$26 = function() {
	if (this.$1) {
		API_BASE.LOG.displayMessage('Loading ' + this.$C.getScormType()
				+ ': ' + this.$C.getIdentifier(), '0', null);
	}
	this.$23();
	var regexp = /^(http(?:s)?\:\/\/[a-zA-Z0-9\-]+(?:\.[a-zA-Z0-9\-]+)*\.[a-zA-Z]{2,6}(?:\/?|(?:\/[\w\-]+)*)(?:\/?|\/\w+\.[a-zA-Z]{2,4}(?:\?[\w]+\=[\w\-]+)?)?(?:\&[\w]+\=[\w\-]+)*)$/;
	if (regexp.test(this.$C.getUrl())) { 
		this.$3.src = this.$C.getUrl();
	} else {
		var urls = this.$C.getUrl().split(/(https?\:\/\/)/);
		if (urls.length > 3) {
			this.$3.src = urls[3] + urls[4];
		} else {
			this.$3.src = this.$C.getUrl();
		}
	}
}




Player.PersistentStateStorage.createClass('Player.PersistentStateStorage');
Player.ContentPlayer.createClass('Player.ContentPlayer');
PlayerConfiguration.createClass('PlayerConfiguration');
Run.createClass('Run');
PlayerConfiguration.Debug = false;
PlayerConfiguration.StorageSupport = false;
PlayerConfiguration.TreeMinusIcon = null;
PlayerConfiguration.TreePlusIcon = null;
PlayerConfiguration.TreeLeafIcon = null;
PlayerConfiguration.TreeActiveIcon = null;
PlayerConfiguration.BtnPreviousLabel = 'Previous';
PlayerConfiguration.BtnContinueLabel = 'Continue';
PlayerConfiguration.BtnExitLabel = 'Exit';
PlayerConfiguration.BtnExitAllLabel = 'Exit All';
PlayerConfiguration.BtnAbandonLabel = 'Abandon';
PlayerConfiguration.BtnAbandonAllLabel = 'Abandon All';
PlayerConfiguration.BtnSuspendAllLabel = 'Suspend All';
Run.$0 = null;
Run.$1 = null;
Run.$2 = null;