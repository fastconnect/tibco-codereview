var currentElementId = null;

function bold(elementId) {
	var obj = document.getElementById(elementId);
	if (obj != null) {
	//	obj.style.fontWeight = 'bold';
		obj.style.border = '2px solid #FFF';
		obj.style.backgroundColor = '#B0B0B0';
	}
}

function slim(elementId) {
	var obj = document.getElementById(elementId);
	if (obj != null) {
	//	obj.style.fontWeight = 'inherit';
		obj.style.border = '1px solid #FFF';
		obj.style.backgroundColor = '#A0A0A0';
	}
}

function hide(elementId) {
	var obj = document.getElementById(elementId);
	if (obj != null) {
		obj.style.visibility = 'collapse';
		obj.style.display = 'none';
	}
}

function show(elementId) {
	var obj = document.getElementById(elementId);
	if (obj != null) {
		obj.style.display = 'inherit';
		obj.style.visibility = 'inherit';
	}
}

function switchVisibility(elementId) {
	var obj = document.getElementById(elementId);
	if (obj.style.display != 'inherit') {
		show(elementId);
	} else {
		hide(elementId);
	}
}

function menue(elementId) {
	if (currentElementId != null) {
		hide(currentElementId);
		slim('b' + currentElementId);
	}
	currentElementId = elementId;
	show(elementId);
	bold('b' + elementId);
}

function codeReviewSectionMaskData(elementId) {
	switchVisibility(elementId);
}