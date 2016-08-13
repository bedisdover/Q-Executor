// import {echarts} from "./echarts";
/**
 * Created by song on 16-8-3.
 *
 * stock.html相关操作
 */

/**
 * 股票ID
 */
var id = '';

/**
 * 股票基本信息
 *
 * code;//股票代码
 * name;//股票公司名称
 * industry;//股票的类型
 * area;//股票的公司创建地
 * pe;//市盈率
 * outstanding;//流通股本
 * totals;//总股本(万)
 * totalAssets;//总资产(万)
 * liquidAssets;//流动资产
 * fixedAssets;//固定资产
 * reserved;//公积金
 * reservedPerShare;//每股公积金
 * eps;//每股收益
 * bvps;//每股净资
 * pb;//市净率
 * timeToMarket;//股票的上市日期
 * @type {{name: string, code: string, industry: string, area: string, pe: number, outstanding: number, totals: number, totalAssets: number, liquidAssets: number, fixedAssets: number, reserved: number, reservedPerShare: number, eps: number, bvps: number, pb: number, timeToMarket: number}}
 */
var stockBasicInfo = {
    name: '',
    code: '',
    industry: '',
    area: '',
    pe: 0,
    outstanding: 0,
    totals: 0,
    totalAssets: 0,
    liquidAssets: 0,
    fixedAssets: 0,
    reserved: 0,
    reservedPerShare: 0,
    eps: 0,
    bvps: 0,
    pb: 0,
    timeToMarket: 0
};

$(function () {
    id = getUrlParam('id');
    init();

    setInterval(function () {
        getCurrentData();
    }, 3000);
});

/**
 * 初始化
 */
function init() {
    $('#name').text('浦发银行');
    $('#code').text('(' + id + ')');

    getCurrentData();
    getBasicData();

    initCharts();

    var kLine_day = drawKLine;
    kLine_day.type = 'dailyKLine';
    getKLine('/KLineDay', kLine_day);
    var kLine_week = drawKLine;
    kLine_week.type = 'weeklyKLine';
    getKLine('/KLineWeek', kLine_week);
    // var kLine_month = drawKLine;
    // kLine_month.type = 'monthlyKLine';
    // getKLine('/KLineMonth', kLine_month);

    // 固定侧边栏
    fix_sidebar($('.sidebar-left'));
}

/**
 * 获取最新数据
 */
function getCurrentData() {
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
        url: '/BasicComInfo',
        cache: false,
        dataType: 'json',
        data: 'codeNum=' + id,
        success: function (data) {
            stockBasicInfo = data;

            showBasicData();
        }
    })
}

/**
 * 显示基本信息
 */
function showBasicData() {
    $('#name').text(stockBasicInfo.name);
    $('#type').text(stockBasicInfo.industry);
    $('#area').text(stockBasicInfo.area);
    $('#pe_ttm').text(stockBasicInfo.pe);
    $('#outstanding').text(stockBasicInfo.outstanding);
    $('#total').text(stockBasicInfo.totals);
    $('#totalAssets').text(stockBasicInfo.totalAssets);
    $('#liquidAssets').text(stockBasicInfo.liquidAssets);
    $('#fixedAssets').text(stockBasicInfo.fixedAssets);
    $('#reserved').text(stockBasicInfo.reserved);
    $('#reservedPerShare').text(stockBasicInfo.reservedPerShare);
    $('#eps').text(stockBasicInfo.eps);
    $('#eps-2').text(stockBasicInfo.eps);
    $('#bvps').text(stockBasicInfo.bvps);
    $('#pb').text(stockBasicInfo.pb);
    $('#timeToMarket').text(stockBasicInfo.timeToMarket);
}

/**
 * 获取K线数据
 * @param url K线对应的url
 * @param callback 数据传输成功后的回调函数
 */
function getKLine(url, callback) {
    jQuery.ajax({
        url: url,
        dataType: 'json',
        data: 'codeNum=' + id,
        success: function (data) {
            var array = [];

            for (var i = 0; i < data.length; i++) {
                array.push([data[i].date, data[i].open, data[i].high, data[i].low, data[i].high])
            }

            callback(callback.type, array);
        }
    });
}

/**
 * 绘制K线图
 * @param type K线图类型(day/week/month)
 * @param data 数据
 */
function drawKLine(type, data) {
    var chart = echarts.init(document.getElementById(type));
    // 数据意义:开盘(open)，收盘(close)，最低(lowest)，最高(highest)
    var data0 = splitData(data);

    function splitData(rawData) {
        var categoryData = [];
        var values = [];
        for (var i = 0; i < rawData.length; i++) {
            categoryData.push(rawData[i].splice(0, 1)[0]);
            values.push(rawData[i])
        }
        return {
            categoryData: categoryData,
            values: values
        };
    }

    function calculateMA(dayCount) {
        var result = [];
        for (var i = 0, len = data0.values.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data0.values[i - j][1];
            }
            result.push((sum / dayCount).toFixed(3));
        }

        return result;
    }

    var option = {
        title: {
            text: id,
            left: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line'
            }
        },
        legend: {
            data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
        },
        grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
        },
        xAxis: {
            type: 'category',
            data: data0.categoryData,
            scale: true,
            boundaryGap: false,
            axisLine: {onZero: false},
            splitLine: {show: false},
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax'
        },
        yAxis: {
            scale: true,
            splitArea: {
                show: true
            }
        },
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series: [
            {
                name: '日K',
                type: 'candlestick',
                data: data0.values,
                markPoint: {
                    label: {
                        normal: {
                            formatter: function (param) {
                                return param != null ? Math.round(param.value) : '';
                            }
                        }
                    },
                    data: [
                        {
                            name: 'highest value',
                            type: 'max',
                            valueDim: 'highest'
                        },
                        {
                            name: 'lowest value',
                            type: 'min',
                            valueDim: 'lowest'
                        },
                        {
                            name: 'average value on close',
                            type: 'average',
                            valueDim: 'close'
                        }
                    ],
                    tooltip: {
                        formatter: function (param) {
                            return param.name + '<br>' + (param.data.coord || '');
                        }
                    }
                },
                markLine: {
                    symbol: ['none', 'none'],
                    data: [
                        [
                            {
                                name: 'from lowest to highest',
                                type: 'min',
                                valueDim: 'lowest',
                                symbol: 'circle',
                                symbolSize: 10,
                                label: {
                                    normal: {show: false},
                                    emphasis: {show: false}
                                }
                            },
                            {
                                type: 'max',
                                valueDim: 'highest',
                                symbol: 'circle',
                                symbolSize: 10,
                                label: {
                                    normal: {show: false},
                                    emphasis: {show: false}
                                }
                            }
                        ],
                        {
                            name: 'min line on close',
                            type: 'min',
                            valueDim: 'close'
                        },
                        {
                            name: 'max line on close',
                            type: 'max',
                            valueDim: 'close'
                        }
                    ]
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            }
        ]
    };

    chart.setOption(option);
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
    $('#inc-dec-rate').text(data[3] + '%').css('color', color_inc);

    $('#open').text(data[4]);
    $('#high').text(data[5]);
    $('#low').text(data[6]);

    $('#volume').text(format_number(data[9] / 100) + '手');
    $('#amount').text(format_number(data[10]) + '元');

    $("#amplitude").text(((data[5] - data[6]) / data[1] * 100).toFixed(2) + "%");

    $("#turnover").text((stockBasicInfo.outstanding) ?
        ((data[9] / stockBasicInfo.outstanding * 100).toFixed(2) + "%") : " --");

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
        format_time(hq_time.getMonth() + 1) + "月" +
        format_time(hq_time.getDate()) + "日 " +
        format_time(hq_time.getHours()) + ":" +
        format_time(hq_time.getMinutes()) + ":" +
        format_time(hq_time.getSeconds()));
}
/**
 * 初始化图表
 */
function initCharts() {
    var width = $('#current-data').innerWidth();

    // 减去well的边距(padding: 19px, border: 1px)
    $('#graphs').find('div').css({
        'width': width - 40 + 'px',
        'height': width * 0.5 + 'px'
    });
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