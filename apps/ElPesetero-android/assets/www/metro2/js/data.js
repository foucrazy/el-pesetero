/*var myJson = '{"mail":"kortatu@gmail.com","topCategories":[{"name":"Vivienda","subCategories":[{"name":"Alquiler/Hipoteca","subCategories":[]}]}],"totalBalance":8378,"username":"kortatu"}';
var myJsonObj = jsonParse(myJson);
alert(myJsonObj.mail);
for (var k in myJsonObj) {
  alert(k + '=' + myJsonObj[k]);
}*/

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
	
	
});