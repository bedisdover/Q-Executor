/**
 * Created by song on 16-8-3.
 *
 * stock.html相关操作
 */

"use strict";

var ws = null;

$(function() {
    connect();

    initCharts();
});

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

/**
 * 断开服务器连接
 */
function disconnect() {
    if (ws != null) {
        ws.close();
        ws = null;
    }
}

/**
 * 初始化图表
 */
function initCharts() {
    var width = $('#current-data').innerWidth();

    // 减去well的边距(padding: 19px, border: 1px)
    $('#graphs').find('div').css({
        'width': width - 40 + 'px',
        'height': width * 0.3 + 'px'
    });

    // $('#radarChart').css({
    //     'width': width - 30 + 'px',//panel-body padding=15
    //     'height': '400px'
    // });
}

