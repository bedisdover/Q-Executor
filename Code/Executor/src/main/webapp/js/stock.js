/**
 * Created by song on 16-8-3.
 *
 * stock相关操作，包含当前数据，基本数据，自选股、最近浏览等
 */

var ip = 'http://112.74.95.45';

/**
 * 股票ID
 */
var id = '';

/**
 * 默认显示记录数量
 * @type {number}
 */
var display_num = 5;

$(function () {
    hotStock.getData();
    id = getUrlParam('id');
    if (id == null) {
        top.location = '404.html';
    }

    init();

    setInterval(function () {
        currentData.getCurrentData();
    }, 3000);
});

/**
 * 初始化
 */
function init() {
    $('#code').text('(' + id + ')');

    basicData.getBasicData();
    currentData.getCurrentData();
    portfolio.init();

    $('#tab-1').tab();
    $('#tab-2').tab();
}

/**
 * 最新数据对象
 * @type {{getCurrentData: currentData.getCurrentData, showCurrentData: currentData.showCurrentData}}
 */
var currentData = {
    getCurrentData: function () { // 获取最新数据
        jQuery.ajax({
            url: 'http://hq.finance.ifeng.com/q.php?l=' + id,
            dataType: 'script',
            cache: true,
            success: function () {
                currentData.showCurrentData(eval('json_q')[id]);
            }
        });
    },
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
    showCurrentData: function (data) {
        $('#price').text(data[0]);
        $('#price-2').text(data[0]);
        $('#close').text(data[1]);

        var color_inc = data[2] > 0 ? 'red' : 'green';
        $('#inc-dec-num').text(data[2]).css('color', color_inc);
        $('#inc-dec-rate').text(data[3] + '%').css('color', color_inc);
        if (data[2] > 0) {
            $('#increase-decrease').attr('src', '../images/increase.png');
        } else {
            $('#increase-decrease').attr('src', '../images/decrease.png');
        }

        $('#open').text(data[4]);
        $('#high').text(data[5]);
        $('#low').text(data[6]);

        $('#volume').text(format.format_number(data[9] / 100) + '手');
        $('#amount').text(format.format_number(data[10]) + '元');

        $("#amplitude").text(((data[5] - data[6]) / data[1] * 100).toFixed(2) + "%");

        $("#turnover").text((basicData.stockBasicInfo.outstanding) ?
            (((data[9] / basicData.stockBasicInfo.outstanding / 1e4) * 100).toFixed(2) + "%") : " --");

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
            format.format_time(hq_time.getMonth() + 1) + "月" +
            format.format_time(hq_time.getDate()) + "日 " +
            format.format_time(hq_time.getHours()) + ":" +
            format.format_time(hq_time.getMinutes()) + ":" +
            format.format_time(hq_time.getSeconds()));
    }
};

/**
 * 基本信息对象
 * @type {{stockBasicInfo: {name: string, code: string, industry: string, area: string, pe: number, outstanding: number, totals: number, totalAssets: number, liquidAssets: number, fixedAssets: number, reserved: number, reservedPerShare: number, eps: number, bvps: number, pb: number, timeToMarket: number}, getBasicData: basicData.getBasicData, show: basicData.show}}
 */
var basicData = {
    /**
     * 股票基本信息
     *
     * code;//股票代码
     * name;//股票公司名称
     * industry;//股票的类型
     * area;//股票的公司创建地
     * pe;//市盈率
     * outstanding;//流通股本(万)
     * totals;//总股本(万)
     * totalAssets;//总资产(万)
     * liquidAssets;//流动资产(万)
     * fixedAssets;//固定资产(万)
     * reserved;//公积金(万)
     * reservedPerShare;//每股公积金(元)
     * eps;//每股收益
     * bvps;//每股净资
     * pb;//市净率
     * timeToMarket;//股票的上市日期
     * @type {{name: string, code: string, industry: string, area: string, pe: number, outstanding: number, totals: number, totalAssets: number, liquidAssets: number, fixedAssets: number, reserved: number, reservedPerShare: number, eps: number, bvps: number, pb: number, timeToMarket: number}}
     */
    stockBasicInfo: {
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
    },
    getBasicData: function () { // 获取基本信息
        jQuery.ajax({
            url: '/BasicComInfo',
            cache: false,
            dataType: 'json',
            data: 'codeNum=' + id,
            success: function (data) {
                basicData.stockBasicInfo = data;

                basicData.show();
            }
        })
    },
    show: function () { // 显示基本信息
        $('#name').text(this.stockBasicInfo.name);
        $('#name-2').text(this.stockBasicInfo.name);
        $('#type').text(this.stockBasicInfo.industry);
        $('#area').text(this.stockBasicInfo.area);
        $('#pe_ttm').text(this.stockBasicInfo.pe);
        $('#outstanding').text(format.format_number(this.stockBasicInfo.outstanding * 1e4) + '元');
        $('#total').text(format.format_number(this.stockBasicInfo.totals * 1e4) + '元');
        $('#totalAssets').text(format.format_number(this.stockBasicInfo.totalAssets * 1e4) + '元');
        $('#marketValue').text(format.format_number(this.stockBasicInfo.totalAssets * 1e4) + '元');
        $('#liquidAssets').text(format.format_number(this.stockBasicInfo.liquidAssets * 1e4) + '元');
        $('#fixedAssets').text(format.format_number(this.stockBasicInfo.fixedAssets * 1e4) + '元');
        $('#reserved').text(format.format_number(this.stockBasicInfo.reserved * 1e4) + '元');
        $('#reservedPerShare').text(this.stockBasicInfo.reservedPerShare + '元');
        $('#eps').text(this.stockBasicInfo.eps + '元');
        $('#eps-2').text(this.stockBasicInfo.eps + '元');
        $('#bvps').text(this.stockBasicInfo.bvps + '元');
        $('#pb').text(this.stockBasicInfo.pb);
        $('#timeToMarket').text(basicData.formatTime(this.stockBasicInfo.timeToMarket));
    },
    /**
     * 格式化时间，将long型转换为'yy年MM月dd日'格式
     * @param time
     */
    formatTime: function (time) {
        var date = new Date(time);

        return date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日';
    }
};

/**
 * 自选股对象
 * @type {{init: portfolio.init, getData: portfolio.getData, show: portfolio.show, needLogin: portfolio.needLogin, add_portfolio: portfolio.add_portfolio, remove_portfolio: portfolio.remove_portfolio, isPortfolio: portfolio.isPortfolio}}
 */
var portfolio = {
    init: function () {
        portfolio.getData();
        portfolio.add_portfolio();
        portfolio.remove_portfolio();
    },
    getData: function () {
        jQuery.ajax({
            url: '/getUserSelectedStock',
            success: function (data) {
                if (data.info == '用户未登录') {
                    portfolio.needLogin();
                } else {
                    if (portfolio.isPortfolio(data.object)) {
                        $('#add-portfolio').hide();
                        $('#remove-portfolio').show();
                    }

                    portfolio.show(data.object);
                }
            }
        })
    },
    show: function (data) {
        if (data == null) {
            return;
        }
        var content = $('#portfolio').find('tbody');
        var text_color;
        for (var i = 0; i < display_num; i++) {
            text_color = data[i].increase > 0 ? 'text-danger' : 'text-success';
            content.append('<tr>' +
                '<td><a href="stock.html?id=' + data[i].gid + '>' + data[i].name + '</a></td>' +
                '<td>' + data[i].nowPri + '</td>' +
                '<td class=text_color>' + data[i].increPer + '</td>' +
                '</tr>');
        }
    },
    needLogin: function () {
        $('#portfolio').find('table').hide()
            .end().append('<p/><p class="text-center">尚未登录，请先<a href="login.html">登录</a></p><p/>');
    },
    add_portfolio: function () {
        $('#add-portfolio').on('click', function () {
            alert(1);
            jQuery.ajax({
                url: '/addUserSelectedStock',
                data: 'codeNum=' + id,
                success: function (data) {
                    if (data.info == '用户未登录') {
                        top.location = 'login.html';
                    } else {
                        $('#add-portfolio').hide();
                        $('#remove-portfolio').show();
                    }
                }
            });
        });
    },
    remove_portfolio: function () {
        $('#remove-portfolio').on('click', function () {
            jQuery.ajax({
                url: '/deleteUserSelectedStock',
                data: 'codeNum=' + id,
                success: function (data) {
                    if (data.state == true) {
                        $('#remove-portfolio').hide();
                        $('#add-portfolio').show();
                    }
                }
            })
        })
    },
    /**
     * 判断当前页面股票是否是自选股
     */
    isPortfolio: function (data) {
        if (data == null) {
            return false;
        }

        for (var i = 0; i < data.length; i++) {
            if (data[i].gid == id) {
                return true;
            }
        }

        return false;
    }
};

var brower_history = {
    getData: function() {
        var codeList = $.cookie('search_history').split(',');

        jQuery.ajax({
            url: 'http://hq.finance.ifeng.com/q.php?l=' + $.cookie('search_history'),
            dataType: 'script',
            cache: true,
            success: function() {
                var array = [];
                var json_q = eval('json_q');
                
                for (var i = 0 ; i < codeList.length; i++) {
                    array.push(json_q[codeList[i]]);
                }
                
                show(array);
            }
        });
    },
    show: function(data) {
        var content = $('#browser').find('tbody');
        
        for (var i = 0; i < data.length; i++) {
            content.append('<tr>' +
                '<td><a href="stock.html?id=' + data[i].gid + '>' + data[i].name + '</a></td>' +
                '<td>' + data[i].nowPri + '</td>' +
                '<td class=text_color>' + data[i].increPer + '</td>' +
                '</tr>');
        }
    }
};

var hotStock = {
    getData: function() {
        jQuery.ajax({
            url: ip + '/HotStocks',
            type: 'get',
            success: function(data) {
                console.log(data);
            }
        })
    },
    show: function() {

    }
};

/**
 * 格式化对象
 * @type {{format_time: format.format_time, format_number: format.format_number}}
 */
var format = {
    /**
     * 格式化时间格式，不足两位的补0
     *      如： 3 --> 03
     * @param _time
     * @returns {*}
     */
    format_time: function (_time) {
        if (_time < 10
        ) {
            _time = "0" + _time;
        }
        return _time;
    },

    /**
     * 格式化数字，如：
     *      340000 --> 34万
     *      450000000 --> 4.5亿
     * @param _number
     */
    format_number: function (_number) {
        if (_number > 1e8) {
            return (_number / 1e8).toFixed(2) + '亿';
        } else if (_number > 1e4) {
            return (_number / 1e4).toFixed(2) + '万';
        }

        return _number;
    }
};
