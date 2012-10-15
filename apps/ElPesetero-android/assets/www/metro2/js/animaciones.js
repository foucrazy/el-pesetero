//http://www.disegnocentell.com.ar/notas2.php?id=239

var tileSacarDinero = document.getElementById("tileSacarDinero");
var iconSacarDinero = document.getElementById("iconSacarDinero");
var contentTileSacarDinero = document.getElementById("contentTileSacarDinero");
var contentExtTileSacarDinero = document.getElementById("contentExtTileSacarDinero");
var btnCancelarSacarDinero = document.getElementById("btnCancelarSacarDinero");

var tileNuevoGasto = document.getElementById("tileNuevoGasto");
var iconNuevoGasto = document.getElementById("iconNuevoGasto");
var contentTileNuevoGasto = document.getElementById("contentTileNuevoGasto");
var contentExtTileNuevoGasto = document.getElementById("contentExtTileNuevoGasto");
var btnCancelarNuevoGasto = document.getElementById("btnCancelarNuevoGasto");

iconSacarDinero.onclick=function(){	
	if(contentTileSacarDinero.style.height!="300px"){
		expandirTile(tileSacarDinero,contentTileSacarDinero,contentExtTileSacarDinero);
		if(contentTileNuevoGasto.style.height=="300px"){
			contraerTile(tileNuevoGasto,contentTileNuevoGasto,contentExtTileNuevoGasto);
		}
	}else{
		contraerTile(tileSacarDinero,contentTileSacarDinero,contentExtTileSacarDinero);
	}
}
	
iconNuevoGasto.onclick=function(){
	if(contentTileNuevoGasto.style.height!="300px"){
		expandirTile(tileNuevoGasto,contentTileNuevoGasto,contentExtTileNuevoGasto);
		if(contentTileSacarDinero.style.height=="300px"){
			contraerTile(tileSacarDinero,contentTileSacarDinero,contentExtTileSacarDinero);
		}
	}else{
		contraerTile(tileNuevoGasto,contentTileNuevoGasto,contentExtTileNuevoGasto);
	}
}

btnCancelarSacarDinero.onclick=function(){
	contraerTile(tileSacarDinero,contentTileSacarDinero,contentExtTileSacarDinero);
}

btnCancelarNuevoGasto.onclick=function(){
	contraerTile(tileNuevoGasto,contentTileNuevoGasto,contentExtTileNuevoGasto);
}


function expandirTile(tile,content,contentExt){		
	fx(tile,[ 
		{'inicio':150,'fin':310,'u':'px','propCSS':'width'}, 
		{'inicio':150,'fin':310,'u':'px','propCSS':'height'}
		],650,true,linear); 
									
	content.style.height="300px";
	content.style.width="300px";
	contentExt.style.display="block";
}

function contraerTile(tile,content,contentExt){
	fx(tile,[ 
	 		{'inicio':310,'fin':150,'u':'px','propCSS':'width'}, 
	 		{'inicio':310,'fin':150,'u':'px','propCSS':'height'}
	 		],400,true,linear); 
	 		
	contentExt.style.display="none";
	content.style.height="150px";
	content.style.width="150px";	
}

function transicion(curva,ms,callback){ 
    this.ant=0.01; 
    this.done_=false; 
    var _this=this; 
    this.start=new Date().getTime(); 
    this.init=function(){ 
        setTimeout(function(){ 
                if(!_this.next()){ 
                    callback(1); 
                    _this.done_=true; 
                    window.globalIntervalo=0; 
                    return; 
                } 
                callback(_this.next()); 
                _this.init(); 
            },13); 
    } 
    this.next=function(){ 
        var now=new Date().getTime(); 
        if((now-this.start)>ms) 
            return false; 
        return this.ant=curva((now-this.start+.001)/ms,this.ant); 
    } 
} 

function linear(p,ant){ 
    var maxValue=1, minValue=.001, totalP=1, k=1; 
    var delta = maxValue - minValue;  
    var stepp = minValue+(Math.pow(((1 / totalP) * p), k) * delta);  
    return stepp;  
} 
function senoidal(p,ant){ 
    return (1 - Math.cos(p * Math.PI)) / 2; 
} 

function desacelerado(p,ant){ 
    var maxValue=1, minValue=.001, totalP=1, k=.25; 
    var delta = maxValue - minValue;  
    var stepp = minValue+(Math.pow(((1 / totalP) * p), k) * delta);  
    return stepp;  
} 

function acelerado(p,ant){ 
    var maxValue=1, minValue=.001, totalP=1, k=7; 
    var delta = maxValue - minValue;  
    var stepp = minValue+(Math.pow(((1 / totalP) * p), k) * delta);  
    return stepp;  
} 

function elasticoFuerte(p,ant){ 
    if(p<=0.6){ 
        return Math.pow(p*5/3,2);} 
    else{ 
        return Math.pow((p-0.8)*5,2)*0.6+0.6; 
    } 
} 

function elasticoSuave(p,ant){ 
    if(p<=0.6){ 
        return Math.pow(p*5/3,2); 
    }else{ 
        return Math.pow((p-0.8)*5,2)*0.4+0.6; 
    } 
} 


function fx(obj,efectos,ms,cola,curva){ 
    if(!window.globalIntervalo) 
        window.globalIntervalo=1; 
    else { 
        if(cola) 
            return setTimeout(function(){fx(obj,efectos,ms,cola,curva)},30); 
        else 
            return; 
    }     
    var t=new transicion( 
    curva,ms,function(p){ 
        for (var i=0;efectos[i];i++){ 
            if(efectos[i].fin<efectos[i].inicio){ 
                var delta=efectos[i].inicio-efectos[i].fin; 
                obj.style[efectos[i].propCSS]=(efectos[i].inicio-(p*delta))+efectos[i].u; 
                if(efectos[i].propCSS=='opacity'){ 
                    obj.style.zoom=1; 
                    obj.style.MozOpacity = (efectos[i].inicio-(p*delta)); 
                    obj.style.KhtmlOpacity = (efectos[i].inicio-(p*delta)); 
                    obj.style.filter='alpha(opacity='+100*(efectos[i].inicio-(p*delta))+')'; 
                } 
            } 
            else{ 
                var delta=efectos[i].fin-efectos[i].inicio; 
                obj.style[efectos[i].propCSS]=(efectos[i].inicio+(p*delta))+efectos[i].u; 
                if(efectos[i].propCSS=='opacity'){ 
                    obj.style.zoom=1; 
                    obj.style.MozOpacity = (efectos[i].inicio+(p*delta)); 
                    obj.style.KhtmlOpacity = (efectos[i].inicio+(p*delta)); 
                    obj.style.filter='alpha(opacity='+100*(efectos[i].inicio+(p*delta))+')'; 
                } 
            } 
        } 
         
    }); 
    t.init(); 
    t=null; 
} 