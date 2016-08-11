/**
 * Created by song on 16-8-3.
 *
 * stock.html相关操作
 */

/**
 * 股票ID
 */
var id = 'sh600000';

$(function () {
    // id = getUrlParam('id');
    init();

    // setInterval(function () {
    //     getData();
    // }, 3000);
});

/**
 * 初始化
 */
function init() {
    $('#name').text('浦发银行');
    $('#code').text('(' + id + ')');

    getData();
    getBasicData();

    initCharts();
}

/**
 * 获取数据
 */
function getData() {
    jQuery.ajax({
        url: 'http://hq.finance.ifeng.com/q.php?l=' + id,
        dataType: 'script',
        cache: true,
        success: function () {
            showCurrentData(eval('json_q')[id]);
        }
    });
}

/**
 * 获取基本信息
 */
function getBasicData() {
    jQuery.ajax({
        url: '/nowTime',
        cache: false,
        dataType: 'json',
        data: 'codeNum=' + id,
        success: function (data) {
            console.log(data);
        }
    })
}

/**
 * 显示当前数据
 * @param data 当前数据，数据含义如下
 * 0:19.71                当前价格
 * 1:19.88                昨日收盘价
 * 2:-0.17                涨跌额
 * 3:-0.86                涨跌幅
 * 4:19.89                今日开盘价
 * 5:20.02                最高
 * 6:19.61                最低
 * 7:19.71
 * 8:19.73
 * 9:867473                成交量
 * 10:17184357.45        成交额
 * 11:19.71                买一报价
 * 12~15                买二至买五报价
 * 16:2900                买一申请2900股
 * 17~20                买二至买五申请
 * 21~25                卖一至卖五报价
 * 26~30                卖一至卖五申请
 * 31:4000
 * 32:2900
 * 33:4000
 * 34:1470881274        当前时间
 * 35:1470881276
 * 36:19.71
 */
function showCurrentData(data) {
    $('#price').text(data[0]);
    $('#price-2').text(data[0]);
    $('#close').text(data[1]);
    
    var color_inc = data[2] > 0 ? 'red' : 'green';
    $('#inc-dec-num').text(data[2]).css('color', color_inc);
    $('#inc-dec-rate').text(data[3]).css('color', color_inc);
    
    $('#open').text(data[4]);
    $('#high').text(data[5]);
    $('#low').text(data[6]);

    $('#volume').text(format_number(data[9] / 100) + '手');
    $('#amount').text(format_number(data[10]) + '元');

    $("#amplitude").html(((data[5] - data[6]) / data[1] * 100).toFixed(2) + "%");

    $('#buy-1-price').text(data[11]);
    $('#buy-2-price').text(data[12]);
    $('#buy-3-price').text(data[13]);
    $('#buy-4-price').text(data[14]);
    $('#buy-5-price').text(data[15]);
    $('#buy-1-amount').text(data[16] / 100);
    $('#buy-2-amount').text(data[17] / 100);
    $('#buy-3-amount').text(data[18] / 100);
    $('#buy-4-amount').text(data[19] / 100);
    $('#buy-5-amount').text(data[20] / 100);
    $('#sell-1-price').text(data[21]);
    $('#sell-2-price').text(data[22]);
    $('#sell-3-price').text(data[23]);
    $('#sell-4-price').text(data[24]);
    $('#sell-5-price').text(data[25]);
    $('#sell-1-amount').text(data[26] / 100);
    $('#sell-2-amount').text(data[27] / 100);
    $('#sell-3-amount').text(data[28] / 100);
    $('#sell-4-amount').text(data[29] / 100);
    $('#sell-5-amount').text(data[30] / 100);

    var buy = data[16] + data[17] + data[18] + data[19] + data[20];
    var sell = data[26] + data[27] + data[28] + data[29] + data[30];
    var commission = buy - sell;
    var committee = commission / (buy + sell) * 100;

    var color_committee = commission > 0 ? 'red' : 'green';
    $('#committee').text(committee.toFixed(2) + '%').css('color', color_committee);
    $('#commission').text(commission).css('color', color_committee);

    var hq_time = new Date(data[34] * 1000);
    $("#time").text(hq_time.getFullYear() + "年" +
        this.format_time(hq_time.getMonth() + 1) + "月" +
        this.format_time(hq_time.getDate()) + "日 " +
        this.format_time(hq_time.getHours()) + ":" +
        this.format_time(hq_time.getMinutes()) + ":" +
        this.format_time(hq_time.getSeconds()));
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

/**
 * 格式化时间格式，不足两位的补0
 *      如： 3 --> 03
 * @param _time
 * @returns {*}
 */
function format_time(_time) {
    if (_time < 10) {
        _time = "0" + _time;
    }
    return _time;
}

/**
 * 格式化数字，如：
 *      340000 --> 34万
 *      450000000 --> 4.5亿
 * @param _number
 */
function format_number(_number) {
    if (_number > 1e8) {
        return (_number / 1e8).toFixed(2) + '亿';
    } else if (_number > 1e4) {
        return (_number / 1e4).toFixed(2) + '万';
    }

    return _number;
}