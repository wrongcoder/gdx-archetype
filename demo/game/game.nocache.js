function game(){var Y='',V=' top: -1000px;',tb='" for "gwt:onLoadErrorFn"',rb='" for "gwt:onPropertyErrorFn"',cb='");',ub='#',Xb='.cache.js',wb='/',Cb='//',Qb='14E842EBBF68C91396CF320CCE643CA0',Rb='1E05A32CB47B3EC7212A1DF6C1F3165F',Sb='33F549819E938797742DD202BC054D3A',Wb=':',lb='::',ec=':moduleBase',X='<!doctype html>',Z='<html><head><\/head><body><\/body><\/html>',ob='=',vb='?',Tb='B0388F365F273960FDA368720F73B21D',Ub='B82930FF2AA349BB1B4C6BED075CC725',qb='Bad handler "',W='CSS1Compat',ab='Chrome',_='DOMContentLoaded',Q='DUMMY',Vb='ED528A4393BF7B60E46E327CA1ED3328',dc='__gwtDevModeHook:game',Bb='base',zb='baseUrl',L='begin',R='body',K='bootstrap',yb='clear.cache.gif',nb='content',cc='end',bb='eval("',O='game',Pb='game.devmode.js',Ab='game.nocache.js',kb='game::',Lb='gecko',Mb='gecko1_8',M='gwt.codesvr.game=',N='gwt.codesvr=',bc='gwt/chrome/chrome.css',sb='gwt:onLoadErrorFn',pb='gwt:onPropertyErrorFn',mb='gwt:property',hb='head',_b='href',Kb='ie6',Jb='ie8',Ib='ie9',S='iframe',xb='img',eb='javascript',T='javascript:""',Yb='link',ac='loadExternalRefs',ib='meta',gb='moduleRequested',fb='moduleStartup',Hb='msie',jb='name',Eb='opera',U='position:absolute; width:0; height:0; border:none; left: -1000px;',Zb='rel',Gb='safari',db='script',Ob='selectingPermutation',P='startup',$b='stylesheet',$='undefined',Nb='unknown',Db='user.agent',Fb='webkit';var o=window;var p=document;r(K,L);function q(){var a=o.location.search;return a.indexOf(M)!=-1||a.indexOf(N)!=-1}
function r(a,b){if(o.__gwtStatsEvent){o.__gwtStatsEvent({moduleName:O,sessionId:o.__gwtStatsSessionId,subSystem:P,evtGroup:a,millis:(new Date).getTime(),type:b})}}
game.__sendStats=r;game.__moduleName=O;game.__errFn=null;game.__moduleBase=Q;game.__softPermutationId=0;game.__computePropValue=null;game.__getPropMap=null;game.__gwtInstallCode=function(){};game.__gwtStartLoadingFragment=function(){return null};var s=function(){return false};var t=function(){return null};__propertyErrorFunction=null;var u=o.__gwt_activeModules=o.__gwt_activeModules||{};u[O]={moduleName:O};var v;function w(){y();return v}
function x(){y();return v.getElementsByTagName(R)[0]}
function y(){if(v){return}var a=p.createElement(S);a.src=T;a.id=O;a.style.cssText=U+V;a.tabIndex=-1;p.body.appendChild(a);v=a.contentDocument;if(!v){v=a.contentWindow.document}v.open();var b=document.compatMode==W?X:Y;v.write(b+Z);v.close()}
function z(k){function l(a){function b(){if(typeof p.readyState==$){return typeof p.body!=$&&p.body!=null}return /loaded|complete/.test(p.readyState)}
var c=b();if(c){a();return}function d(){if(!c){c=true;a();if(p.removeEventListener){p.removeEventListener(_,d,false)}if(e){clearInterval(e)}}}
if(p.addEventListener){p.addEventListener(_,d,false)}var e=setInterval(function(){if(b()){d()}},50)}
function m(c){function d(a,b){a.removeChild(b)}
var e=x();var f=w();var g;if(navigator.userAgent.indexOf(ab)>-1&&window.JSON){var h=f.createDocumentFragment();h.appendChild(f.createTextNode(bb));for(var i=0;i<c.length;i++){var j=window.JSON.stringify(c[i]);h.appendChild(f.createTextNode(j.substring(1,j.length-1)))}h.appendChild(f.createTextNode(cb));g=f.createElement(db);g.language=eb;g.appendChild(h);e.appendChild(g);d(e,g)}else{for(var i=0;i<c.length;i++){g=f.createElement(db);g.language=eb;g.text=c[i];e.appendChild(g);d(e,g)}}}
game.onScriptDownloaded=function(a){l(function(){m(a)})};r(fb,gb);var n=p.createElement(db);n.src=k;p.getElementsByTagName(hb)[0].appendChild(n)}
game.__startLoadingFragment=function(a){return C(a)};game.__installRunAsyncCode=function(a){var b=x();var c=w().createElement(db);c.language=eb;c.text=a;b.appendChild(c);b.removeChild(c)};function A(){var c={};var d;var e;var f=p.getElementsByTagName(ib);for(var g=0,h=f.length;g<h;++g){var i=f[g],j=i.getAttribute(jb),k;if(j){j=j.replace(kb,Y);if(j.indexOf(lb)>=0){continue}if(j==mb){k=i.getAttribute(nb);if(k){var l,m=k.indexOf(ob);if(m>=0){j=k.substring(0,m);l=k.substring(m+1)}else{j=k;l=Y}c[j]=l}}else if(j==pb){k=i.getAttribute(nb);if(k){try{d=eval(k)}catch(a){alert(qb+k+rb)}}}else if(j==sb){k=i.getAttribute(nb);if(k){try{e=eval(k)}catch(a){alert(qb+k+tb)}}}}}t=function(a){var b=c[a];return b==null?null:b};__propertyErrorFunction=d;game.__errFn=e}
function B(){function e(a){var b=a.lastIndexOf(ub);if(b==-1){b=a.length}var c=a.indexOf(vb);if(c==-1){c=a.length}var d=a.lastIndexOf(wb,Math.min(c,b));return d>=0?a.substring(0,d+1):Y}
function f(a){if(a.match(/^\w+:\/\//)){}else{var b=p.createElement(xb);b.src=a+yb;a=e(b.src)}return a}
function g(){var a=t(zb);if(a!=null){return a}return Y}
function h(){var a=p.getElementsByTagName(db);for(var b=0;b<a.length;++b){if(a[b].src.indexOf(Ab)!=-1){return e(a[b].src)}}return Y}
function i(){var a=p.getElementsByTagName(Bb);if(a.length>0){return a[a.length-1].href}return Y}
function j(){var a=p.location;return a.href==a.protocol+Cb+a.host+a.pathname+a.search+a.hash}
var k=g();if(k==Y){k=h()}if(k==Y){k=i()}if(k==Y&&j()){k=e(p.location.href)}k=f(k);return k}
function C(a){if(a.match(/^\//)){return a}if(a.match(/^[a-zA-Z]+:\/\//)){return a}return game.__moduleBase+a}
function D(){var f=[];var g;function h(a,b){var c=f;for(var d=0,e=a.length-1;d<e;++d){c=c[a[d]]||(c[a[d]]=[])}c[a[e]]=b}
var i=[];var j=[];function k(a){var b=j[a](),c=i[a];if(b in c){return b}var d=[];for(var e in c){d[c[e]]=e}if(__propertyErrorFunc){__propertyErrorFunc(a,d,b)}throw null}
j[Db]=function(){var b=navigator.userAgent.toLowerCase();var c=function(a){return parseInt(a[1])*1000+parseInt(a[2])};if(function(){return b.indexOf(Eb)!=-1}())return Eb;if(function(){return b.indexOf(Fb)!=-1}())return Gb;if(function(){return b.indexOf(Hb)!=-1&&p.documentMode>=9}())return Ib;if(function(){return b.indexOf(Hb)!=-1&&p.documentMode>=8}())return Jb;if(function(){var a=/msie ([0-9]+)\.([0-9]+)/.exec(b);if(a&&a.length==3)return c(a)>=6000}())return Kb;if(function(){return b.indexOf(Lb)!=-1}())return Mb;return Nb};i[Db]={gecko1_8:0,ie6:1,ie8:2,ie9:3,opera:4,safari:5};s=function(a,b){return b in i[a]};game.__getPropMap=function(){var a={};for(var b in i){if(i.hasOwnProperty(b)){a[b]=k(b)}}return a};game.__computePropValue=k;o.__gwt_activeModules[O].bindings=game.__getPropMap;r(K,Ob);if(q()){return C(Pb)}var l;try{h([Kb],Qb);h([Ib],Rb);h([Eb],Sb);h([Jb],Tb);h([Gb],Ub);h([Mb],Vb);l=f[k(Db)];var m=l.indexOf(Wb);if(m!=-1){g=parseInt(l.substring(m+1),10);l=l.substring(0,m)}}catch(a){}game.__softPermutationId=g;return C(l+Xb)}
function E(){if(!o.__gwt_stylesLoaded){o.__gwt_stylesLoaded={}}function c(a){if(!__gwt_stylesLoaded[a]){var b=p.createElement(Yb);b.setAttribute(Zb,$b);b.setAttribute(_b,C(a));p.getElementsByTagName(hb)[0].appendChild(b);__gwt_stylesLoaded[a]=true}}
r(ac,L);c(bc);r(ac,cc)}
A();game.__moduleBase=B();u[O].moduleBase=game.__moduleBase;var F=D();if(o){o.__gwt_activeModules[O].canRedirect=true}var G=dc;var H=o.sessionStorage[G];if(H&&!o[G]){o[G]=true;var I=p.createElement(db);o[G+ec]=B();I.src=H;var J=p.getElementsByTagName(hb)[0];J.insertBefore(I,J.firstElementChild||J.children[0]);return false}E();r(K,cc);z(F);return true}
game.succeeded=game();