var tileSacarDinero = document.getElementById("tileSacarDinero");
var contentTileSacarDinero = document.getElementById("contentTileSacarDinero");
var contentExtTileSacarDinero = document.getElementById("contentExtTileSacarDinero");

var tileNuevoGasto = document.getElementById("tileNuevoGasto");
var contentTileNuevoGasto = document.getElementById("contentTileNuevoGasto");
var contentExtTileNuevoGasto = document.getElementById("contentExtTileNuevoGasto");

tileSacarDinero.onclick=function(){
	expandirTile(tileSacarDinero,contentTileSacarDinero,contentExtTileSacarDinero);
}
	
tileNuevoGasto.onclick=function(){
	expandirTile(tileNuevoGasto,contentTileNuevoGasto,contentExtTileNuevoGasto);
}

function expandirTile(tile,content,contentExt){	
	if(content.style.height!="300px"){
		fx(tile,[ 
		{'inicio':0,'fin':310,'u':'px','propCSS':'width'}, 
		{'inicio':0,'fin':310,'u':'px','propCSS':'height'}
		],500,true,desacelerado); 
							
		contentExt.style.display="block";
		content.style.height="300px";
		content.style.width="300px";	
	}else{
		fx(tile,[ 
		{'inicio':310,'fin':150,'u':'px','propCSS':'width'}, 
		{'inicio':310,'fin':150,'u':'px','propCSS':'height'}
		],300,true,desacelerado); 
		
		contentExt.style.display="none";
		content.style.height="150px";
		content.style.width="150px";	
	}
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