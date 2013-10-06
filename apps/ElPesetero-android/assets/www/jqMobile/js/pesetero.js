$(document).ready(function() {	
	
	//Detectar sistema utilizado
	var mobileOS;
	var mobileOSver;
	var e = navigator.userAgent;		
	var t;
	if (e.match(/iPad/i) || e.match(/iPhone/i)) {
		mobileOS = "iOS";
		t = e.indexOf("OS ")
	} else if (e.match(/Android/i)) {
		mobileOS = "Android";
		t = e.indexOf("Android ")
	} else if (e.match(/Mozilla/i)) {
		mobileOS = "Mozilla";
		t = e.indexOf("Mozilla ") 
	}else {
		mobileOS = "unknown"
	}
	
	if (mobileOS === "iOS" && t > -1) {
		mobileOSver = e.substr(t + 3, 3).replace("_", ".")
	} else if (mobileOS === "Android" && t > -1) {
		mobileOSver = e.substr(t + 8, 3)
	} else {
		mobileOSver = "unknown"
	}
	
	if (mobileOS == "unknown" || (mobileOS === 'Android' && Number(mobileOSver.charAt(0)) < 3)) {
		$('img').each(
			function() {
				$(this).attr('src',
					$(this).attr('src').replace('.svg', '.png'));
			});
	}
	
	document.addEventListener("backbutton", onBackKeyDown, false);
	

	//Sensibilidad de botones
	$.mobile.buttonMarkup.hoverDelay=100;
});

//Cierre de la app
function onBackKeyDown() {
	navigator.app.exitApp();
}	