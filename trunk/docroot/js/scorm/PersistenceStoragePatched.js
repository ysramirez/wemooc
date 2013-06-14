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