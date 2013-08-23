Type.createNamespace('Player');

SCORM_1_2.API_LIB.prototype.$27 = function($p0) {
	var $0 = false;
	if ($p0) {
		if (this.$1F.getDataTreeValue('cmi.core.exit') == null || this.$1F.getDataTreeValue('cmi.core.exit') == '') {
			this.$1F.setDataTreeValue('cmi.core.exit', 'suspend', false);
		}
		if (this.$1F.getDataTreeValue('cmi.core.exit') === 'suspend') {
			$0 = true;
			this.$1F.setDataTreeValue('cmi.core.entry', 'resume', false);
		} else {
			this.$1F.setDataTreeValue('cmi.core.entry', '', false);
		}
		if (this.$1F.getDataTreeValue('cmi.core.lesson_status') === 'not attempted') {
			if (this.$1F.getDataTreeValue('cmi.core.lesson_mode') === 'browse') {
				this.$1F.setDataTreeValue('cmi.core.lesson_status',
						'browsed', false);
			} else {
				this.$1F.setDataTreeValue('cmi.core.lesson_status',
						'completed', false);
			}
		}
		if (this.$1F.getDataTreeValue('cmi.core.lesson_mode') === 'normal') {
			if (this.$1F.getDataTreeValue('cmi.core.credit') === 'credit') {
				if (this.$1F.getDataTreeValue('cmi.core.lesson_status') === 'completed') {
					if (this.$1F
							.getDataTreeValue('cmi.student_data.mastery_score') !== ''
							&& this.$1F
									.getDataTreeValue('cmi.core.score.raw') !== '') {
						if (parseFloat(this.$1F
								.getDataTreeValue('cmi.core.score.raw')) >= parseFloat(this.$1F
								.getDataTreeValue('cmi.student_data.mastery_score'))) {
							this.$1F.setDataTreeValue(
									'cmi.core.lesson_status', 'passed',
									false);
						} else {
							this.$1F.setDataTreeValue(
									'cmi.core.lesson_status', 'failed',
									false);
						}
					}
				}
			}
		}
		if (this.$1F.getDataTreeValue('cmi.core.session_time') === (this.$1E['cmi.core.session_time'])['defaultvalue']) {
			this.$1F.setDataTreeValue('cmi.core.session_time', this
					.$28((new Date().getTime() - this.$24) / 1000), false);
		}
		if (this.$1F.getDataTreeValue('cmi.core.session_time') != null
				&& this.$1F.getDataTreeValue('cmi.core.session_time') !== '') {
			this.$1F.setDataTreeValue('cmi.core.total_time', this.$29(
					this.$1F.getDataTreeValue('cmi.core.total_time'),
					this.$1F.getDataTreeValue('cmi.core.session_time')),
					false);
		}
	}
	if (!$0
			&& this.$1F.getLessonstatus() !== this.$1F
					.getDataTreeValue('cmi.core.lesson_status')) {
		this.$1F.setLessonstatus(this.$1F
				.getDataTreeValue('cmi.core.lesson_status'));
		if (this.$1F.getRoot() != null) {
			this.$1F.getRoot().reEvaluate();
		}
	}
	if (this.$1F.getRoot() != null) {
		this.$1F.getRoot().onEventSCO(
				new API_BASE.BaseActivityTreeNodeEventArgs(this.$1F, 1));
	}
}

SCORM_1_2.API_LIB.prototype.LMSInitialize = function(param) {
	this.$23 = '0';
	this.$22 = false;
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (!this.$21) {
			this.$21 = true;
			this.$23 = '0';
			this.$1F.setDataTreeValue('cmi.core.session_time',
					(this.$1E['cmi.core.session_time'])['defaultvalue'],
					false);
			this.$24 = new Date().getTime();
			API_BASE.LOG.displayMessage('LMSInitialize with param: \''
					+ param + '\'', this.$23, this.$25(this.$23));
			return 'true';
		} else {
			//this.$23 = '101';
		}
	} else {
		this.$23 = '201';
	}
	API_BASE.LOG.displayMessage('LMSInitialize with param: \'' + param
			+ '\'', this.$23, this.$25(this.$23));
	return 'false';
}

SCORM_1_2.API_LIB.prototype.LMSFinish = function(param) {
	this.$23 = '0';
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (this.$21 && (!this.$22)) {
			this.$21 = false;
			this.$22 = true;
			var $0 = this.$1F.getDataTreeValue('nav.event');
			this.$27(true);
			if (this.$1F.getRoot() != null) {
				this.$1F.getRoot().onEventSCO(
						new API_BASE.BaseActivityTreeNodeEventArgs(
								this.$1F, 0));
				if (!isNullOrUndefined($0) && $0.length > 0) {
					this.$1F.getRoot().requestNavigation($0);
				} else if (this.$20) {
					this.$1F.getRoot().requestNavigation('continue');
				}
			}
			API_BASE.LOG.displayMessage('LMSFinish with param: \'' + param
					+ '\'', this.$23, this.$25(this.$23));
			return 'true';
		} else {
			//this.$23 = '301';
		}
	} else {
		this.$23 = '201';
	}
	API_BASE.LOG.displayMessage('LMSFinish with param: \'' + param + '\'',
			this.$23, this.$25(this.$23));
	return 'false';
}

SCORM_1_2.API_LIB.prototype.LMSGetValue = function(element) {
	this.$23 = '0';
	if (!this.$21) {
		this.LMSInitialize('');
	}
	if (this.$21) {
		if (isNullOrUndefined(element)) {
			element = '';
		}
		if (element !== '') {
			var $0 = new RegExp(this.$9, 'g');
			var $1 = element.replace($0, '.n.');
			if (!isUndefined(this.$1E[$1])) {
				if ((Type.safeCast(this.$1E[$1], Object))['mod'] !== 'w') {
					element = element.replace($0, '$1.');
					var $2 = this.$1F.getDataTreeValue(element);
					if (!isUndefined($2)) {
						this.$23 = '0';
						API_BASE.LOG.displayMessage('LMSGetValue '
								+ element + ', \'' + $2 + '\'', this.$23,
								this.$25(this.$23));
						return $2;
					} else {
						this.$23 = '201';
					}
				} else {
					this.$23 = (Type.safeCast(this.$1E[$1], Object))['readerror'];
				}
			} else {
				var $3 = '._children';
				var $4 = '._count';
				if ($1.substr($1.length - $3.length, $1.length) === $3) {
					var $5 = $1.substr(0, $1.length - $3.length);
					if (!isUndefined(this.$1E[$5])) {
						this.$23 = '202';
					} else {
						this.$23 = '201';
					}
				} else if ($1.substr($1.length - $4.length, $1.length) === $4) {
					var $6 = $1.substr(0, $1.length - $4.length);
					if (!isUndefined(this.$1E[$6])) {
						this.$23 = '203';
					} else {
						this.$23 = '201';
					}
				} else {
					if (element.startsWith('cmi.')) {
						this.$23 = '201';
					} else {
						this.$23 = '401';
					}
				}
			}
		} else {
			this.$23 = '201';
		}
	} else {
		this.$23 = '301';
	}
	API_BASE.LOG.displayMessage('LMSGetValue ' + element + ', \'\'',
			this.$23, this.$25(this.$23));
	return '';
}

SCORM_1_2.API_LIB.prototype.LMSCommit = function(param) {
	this.$23 = '0';
	if (isNullOrUndefined(param)) {
		param = '';
	}
	if (param === '') {
		if (this.$21) {
			this.$27(false);
			API_BASE.LOG.displayMessage('LMSCommit with param: \'' + param
					+ '\'', this.$23, this.$25(this.$23));
			var $0 = this.$1F.getDataTreeValue('nav.event');
			if ($0) {
				this.LMSFinish('');
			}
			return 'true';
		} else {
			this.$23 = '301';
		}
	} else {
		this.$23 = '201';
	}
	API_BASE.LOG.displayMessage('LMSCommit with param: \'' + param + '\'',
			this.$23, this.$25(this.$23));
	return 'false';
}

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
			var evt = document.createEventObject();
			evt.eventName = "click";
			document.getElementById('clicker').fireEvent("onclick", evt);
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

Player.ContentPlayer.prototype.$22 = function($p0, $p1) {
	if ($p1.get_eventType() === 5 || $p1.get_eventType() === 3
			|| $p1.get_eventType() === 4) {
		if (this.$1 && this.$C != null) {
			API_BASE.LOG.displayMessage('Unloading '
					+ this.$C.getScormType() + ': '
					+ this.$C.getIdentifier(), '0', null);
			
		}
		if ($p1.get_eventType() === 5) {
			this.$C = null;
			this.$B = null;
			this.$D = null;
			if (!isNullOrUndefined(update_scorm)) {
				update_scorm(null);
			}
			this.hidePlayer(!this.$1);
			document.getElementById('open-close-scorm-menu').style.display = 'none';
			if (this.$1) {
				API_BASE.LOG.displayMessage('End Session!', '0', null);
			}
		} else {
			if (this.$B != null && this.$C.getScormType() === 'sco'
					&& !this.$B.isInitAttempted()) {
				return;
			}
		}
		if ($p1.get_eventType() !== 4) {
			this.$F = true;
			this.$3.attachEvent('onload', this.$4);
		} else {
			if (isNullOrUndefined(this.$C)) {
				API_BASE.LOG
						.displayMessage('No SCO to deliver!', '0', null);
				this.$23();
			}
			this.$12.disabled = true;
			this.$14.disabled = true;
		}
		this.$3.src = '/liferaylms-portlet/blank.html';
	} else if ($p1.get_eventType() === 1) {
		if (this.$B != null) {
			if (!this.$B.isFinishAttempted()) {
				this.$23();
			}
			if (this.$17 != null) {
				this.$17.saveItemCMI(this.$C.getIdentifier(), this.$C
						.getDataTree());
			}
		}
	} else if ($p1.get_eventType() === 0) {
		if (this.$17 != null) {
			if (this.$2 === 1) {
				this.$17.saveState(null, (this.$9).getStoredStatuses(),
						null, null, null);
			} else if (this.$2 === 2) {
				var $0 = this.$9;
				this.$17
						.saveState(
								$0.getADLCPData(),
								$0.getStoredStatuses(),
								($0.savedSuspendedActivity != null) ? null
										: ($0.isObjectivesglobaltoSystem()) ? $0
												.getClonedGlobalObjectives()
												: null,
								($0.savedSuspendedActivity != null) ? $0.savedSuspendedActivity
										.getIdentifier()
										: null,
								($0.savedSuspendedActivity != null) ? $0
										.getClonedGlobalObjectives() : null);
			}
		}
	} else if ($p1.get_eventType() === 2) {
		this.$28($p1.get_treeNode());
	}
};

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

Player.ContentPlayer.prototype.$28 = function($p0) {
	if ($p0 != null) {
		var $0 = this.$D;
		if (this.$17 != null) {
			$p0.setDataTree(this.$17.getItemCMI($p0.getIdentifier()));
		}
		if (this.$2 === 1) {
			this.$B = new SCORM_1_2.API_LIB($p0);
		} else if (this.$2 === 2) {
			this.$B = new SCORM_1_3.API_1484_11_LIB($p0);
		}
		this.$C = $p0;
		this.$D = this.$C.getData();
		this.$9.setActiveAPI(this.$B);
		if ($0 != null && this.$E != null) {
			$0.getIcon().src = this.$E;
		}
		if (!isNullOrUndefined(PlayerConfiguration.TreeActiveIcon)
				&& PlayerConfiguration.TreeActiveIcon.length > 0
				&& !isNullOrUndefined(this.$D.getIcon())) {
			this.$E = this.$D.getIcon().src;
			this.$D.getIcon().src = PlayerConfiguration.TreeActiveIcon;
		}
		var $1 = $p0.getHideLMSUI();

		if (!isNullOrUndefined(this.$13)) {
			this.$13.style.display = ($1.contains('exitall')) ? 'none'
					: 'inline';
			this.$13.disabled = false;
		}

		if (!this.$F) {
			this.$26();
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