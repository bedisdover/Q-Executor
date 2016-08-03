/**
 * Created by song on 16-8-3.
 *
 * stock.html相关操作
 */

"use strict";

var ws = null;

$(function() {
    // connect();

});
$(window).unload(function(){
    alert(3)
})
window.onunload = function() {
    alert(0)
}
window.onbeforeunload = function() {
    alert(1);
};

/**
 * 连接服务器，获取数据
 */
function connect() {
    var target = 'ws://' + 'localhost:8080/stock';

    if ('WebSocket' in window) {
        ws = new WebSocket(target);
    } else if ('MozWebSocket' in window) {
        ws = new MozWebSocket(target);
    } else {
        alert('您的浏览器版本过低，部分功能无法支持，请更换更高版本的浏览器！！！');
        return;
    }

    ws.onopen = function (event) {

    };
    
    ws.onmessage = function (event) {
        console.log(event.data);
    };
    
    ws.onclose = function (event) {

    };
}

function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}

