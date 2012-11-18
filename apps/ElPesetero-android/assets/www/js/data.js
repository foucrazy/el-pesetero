
var debug=false;

document.addEventListener("deviceready", onDeviceReady, false);

function onDeviceReady() {
    if (debug)alert("onDeviceReady");
    var pictureSource=navigator.camera.PictureSourceType;
    document.addEventListener("backbutton", onBackKeyDown, false);
}

// Handle the back button
function onBackKeyDown() {
	goTo('#inicio');
}

//Called when a photo is successfully retrieved
function onPhotoDataSuccess(imageData) {
  // Uncomment to view the base64 encoded image data
  // console.log(imageData);

  // Get image handle
  var smallImage = document.getElementById('adjunto');

  // Unhide image elements
  smallImage.style.display = 'block';

  // Show the captured photo
  // The inline CSS rules are used to resize the image
  smallImage.src = "data:image/jpeg;base64," + imageData;
}

//Called if something bad happens.
function onFail(message) {
  alert('Failed because: ' + message);
}  

// A button will call this function
function capturePhoto() {
	if (debug)alert("capturePhoto");
  // Take picture using device camera and retrieve image as base64-encoded string
  navigator.camera.getPicture(onPhotoDataSuccess, onFail, {
	  quality : 75, 
	  destinationType : Camera.DestinationType.DATA_URL, 
	  sourceType : Camera.PictureSourceType.CAMERA, 
	  allowEdit : true,
	  encodingType: Camera.EncodingType.JPEG,
	  targetWidth: 800,
	  targetHeight: 600,
	  popoverOptions: CameraPopoverOptions,
	  saveToPhotoAlbum: false
	 });
}  

function goTo(subpage){	
	if (debug)alert("goTo");
	$(subpage).css("display","block");
	var positionX=$(subpage).offset().left;
	var positionY=$(subpage).offset().top;
	$('html, body').animate({scrollLeft:positionX}, 300,function() {
		$('html, body').animate({scrollTop:positionY}, 500,function() {});
	});	
}

function back(subpage){
	if (debug)alert("back");
	$('html, body').animate({scrollTop:0}, 300,function() {
		$('html, body').animate({scrollLeft:0}, 300,function() {});
		$(subpage).css("display","none");
	  });	
}

var url='data/initial.json';
//url='http://el-pesetero.cloudfoundry.com/user/show/30.json';
$.getJSON(url, function(data) {
	//Total disponible en pantalla inicial
	$('#totalBalance').html(data.totalBalance+'&euro;');
	
	//Fondos disponibles en seccion fondos
	var fundList='<ul class="listview fluid">';
	$.each(data.funds, function(k,fund){
		fundList = fundList + "<li class=\"bg-color-blueLight\"><div class=\"icon\"><img src=\"css/images/"+fund.type.name+
			"icon.png\"></div><div class=\"data\"><h4>"+fund.name+"</h4>"+fund.quantity+"&nbsp;&euro;<p>"+fund.type.name+"</p></div></li>";
	});	
	fundList = fundList + '</ul>'
	$('#funds').html(fundList);
	
	//Fondos disponibles en seccion sacar dinero
	var fundSelect='<select>';
	$.each(data.funds, function(k,fund){
		if (fund.type.name=='bankAccount'){
			fundSelect = fundSelect + "<option value=\""+fund.ids+"\">"+fund.name+"</option>";
		}
	});	
	fundSelect = fundSelect + '</select>'	
	$('#selectFunds').html(fundSelect);
	$('#selectBankFunds').html(fundSelect);
	
	//Tipos de gasto en seccion tipos de gasto
	var categoriesList='<ul class="listview fluid">';
	$.each(data.topCategories, function(k,parentCategory){
		var icon='subCategoryIcon';
		if (parentCategory.subCategories.length>0){
			icon='categoryIcon';
		}
		
		var subCategoriesList='';
		$.each(parentCategory.subCategories, function(k,subCategories){
			var icon='subCategoryIcon';
			if (subCategories.subCategories.length>0){
				icon='categoryIcon';
			}			
			subCategoriesList = subCategoriesList + 
				"<li class=\"subCategoriesList bg-color-red fg-color-white\"><div class=\"icon\"><img src=\"css/images/"+icon+
				".png\"></div><div class=\"data\">"+subCategories.name+"</div></li>";
		});
		
		categoriesList = categoriesList + "<li class=\"bg-color-blueLight\"><div class=\"icon\"><img src=\"css/images/"+icon+
			".png\"></div><div class=\"data\"><h4>"+parentCategory.name+"</h4>Subcategorias:"+parentCategory.subCategories.length+
			"</div>"+subCategoriesList+"</li>";
	});	
	categoriesList = categoriesList + '</ul>'
	$('#topCategories').html(categoriesList);	
	
	
	//Tipos de gasto en seccion nuevo gasto
	var categoriesSelect='<select>';
	$.each(data.topCategories, function(k,parentCategory){
		
		var subCategoriesList='';
		$.each(parentCategory.subCategories, function(k,subCategories){	
			subCategoriesList = subCategoriesList + 
				"<option> - "+subCategories.name+"</option>";
		});
		
		categoriesSelect = categoriesSelect + "<option>"+parentCategory.name+"</option>"+subCategoriesList;
	});	
	categoriesSelect =categoriesSelect + '</select>'
	$('#selectCategory').html(categoriesSelect);
	
	//Ultimos movimientos
	var totalExpenses=0;
	var expensesList='<ul class="listview fluid">';
	$.each(data.lastExpenses, function(k,expense){
		expensesList = expensesList + "<li class=\"bg-color-blueLight\"><div class=\"icon\"><img src=\"css/images/"+expense.from.type.name+
			"icon.png\"></div><div class=\"data\"><h4>"+expense.name+"</h4>"+expense.quantity+"&nbsp;&euro;<p>"+expense.expenseDate+"</p></div></li>";
		totalExpenses=totalExpenses+expense.quantity;
	});	
	expensesList = expensesList + '</ul>'
	$('#lastExpenses').html(expensesList);	
	
	//Importe acumulado de los ultimos movimientos	
	$('#totalExpenses').html(totalExpenses+'&euro;');
	
	if (debug)alert("data parsed");
});