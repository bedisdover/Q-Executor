/**
 * Created by song on 16-8-3.
 *
 * stock.html相关操作
 */

$(function () {
    initCharts();
    // getData();
    getCurrentData();
});

/**
 * 获取数据
 */
function getData() {
    // console.log(record);
    // jQuery.ajax({
    //     url: 'http://api.finance.ifeng.com/akweekly/?callback=callback&code=sh600000&type=last',
    //     type: 'get',
    //     dataType: 'jsonp',
    //     jsonp: 'callback',
        // data: 'callback=callback',
        // success: function (data) {
        //     console.log(data);
        //     console.log(data.record);
        // }
    // });
    jQuery.getJSON('http://api.finance.ifeng.com/akweekly/?code=sh600000&type=last&format=jsonp&callback=?', function(data) {
        console.log(data);
    });
}

function callback() {
    alert(1);
}

/**
 * 获取当前数据
 */
function getCurrentData() {
    // jQuery.ajax({
    //     url: 'http://hq.sinajs.cn/list=sh601006',
    //     type: 'GET',
    //     dataType: 'script',
        // dataType: 'JSONP',
        // jsonp: 'callback',
        // success: function(data) {
        //     console.log(data);
        // }
    // });

    jQuery.getScript('http://hq.sinajs.cn?list=sh601006', function(data) {
        console.log(data);
    });
    // var s = document.createElement('script');
    // s.async = true;
    // s.src = 'http://hq.sinajs.cn/list=sh601006';
    // var script = '<script src="http://hq.sinajs.cn/list=sh601006" >' +
        // 'var elements = hq_str_sh601006.split(",");' +
        // 'console.log(elements);' +
        // '</script>';
    // $('body').append($(script));

    var elements = hq_str_sh601006.split(',');
    console.log(elements);
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

