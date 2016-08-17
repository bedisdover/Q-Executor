/**
 * Created by song on 16-8-15.
 *
 * 订单相关操作
 */

$(function () {
    getData();
    
    setInterval(function() {
        getData();
    }, 3000);
});

/**
 * 获取订单相关数据
 */
function getData() {
    perShare.getData();
    largeOrder.getData();
    // timeSeries.getData();
    priceSeries.getData();
}

/**
 * 交易性质
 * @type {string[]}
 */
var type = ['买盘', '卖盘', '中性盘'];

var perShare = {
    getData: function () {
        jQuery.ajax({
            url: '/PerStockInfo',
            cache: false,
            data: 'codeNum=' + id,
            success: function (data) {
                perShare.showLatest(data);
            }
        })
    },

    /**
     * change_price : 0.11
     * price : 71.47
     * time : "10:46:57"
     * totalNum : 421673
     * type : 0
     * volume : 59
     */
    showLatest: function (data) {
        var content = $('#per-share').find('tbody');

        content.empty();
        for (var i = 0; i < 5; i++) {
            if (data[i].time !== undefined) {
                // TODO 买盘颜色
                content.append('<tr>' +
                    '<td>' + data[i].time + '</td>' +
                    '<td>' + data[i].price + '</td>' +
                    '<td>' + data[i].volume + '</td>' +
                    '<td>' + type[data[i].type] + '</td>' +
                    '</tr>');
            }
        }
    }, 
    showAll: function(data) {
        
    }
};

var largeOrder = {
    getData: function () {
        jQuery.ajax({
            url: '/ComStockInfo',
            cache: false,
            data: 'codeNum=' + id,
            success: function (data) {
                largeOrder.showLatest(data);
            }
        })
    },
    /**
     * f_price: 71.33
     * price : 71.34
     * time : "10:46:42"
     * type : 0
     * volume : 612
     */
    showLatest: function(data) {
        var content = $('#large-order').find('tbody');

        content.empty();
        for (var i = 0; i < 5; i++) {
            if (data[i].time !== undefined) {
                content.append('<tr>' +
                    '<td>' + data[i].time + '</td>' +
                    '<td>' + data[i].price + '</td>' +
                    '<td>' + data[i].volume + '</td>' +
                    '<td>' + type[data[i].type] + '</td>' +
                    '</tr>');
            }
        }
    }
};

var priceSeries = {
    getData: function () {
        jQuery.ajax({
            url: '/StockInfoByPrice',
            cache: false,
            data: 'codeNum=' + id,
            success: function (data) {
                priceSeries.showLatest(data);
            }
        })
    },
    /**
     * percent : 0.004
     * price : 67.21
     * trunover : 304
     */
    showLatest: function(data) {
        var content = $('#price-series').find('tbody').empty();
        for (var i = 0; i < 5; i++) {
            if (data[i].price !== undefined) {
                content.append('<tr>' +
                    '<td>' + data[i].price + '</td>' +
                    '<td>' + data[i].trunover + '</td>' +
                    '<td>' + (data[i].percent * 100).toFixed(2) + '</td>' +
                    '</tr>');
            }
        }
    }
};

var timeSeries = {
    getData: function () {
        jQuery.ajax({
            url: '/StockInfoByTime',
            cache: false,
            data: 'codeNum=' + id,
            success: function (data) {
                console.log(data);
            }
        })
    },
    showLatest: function(data) {
        var content = $('#time-series').find('tbody').empty();
        
        for (var i = 0; i < 5; i++) {
            if (data[i].time !== undefined) {
                content.append('<tr>' +
                    '<td>' + data[i].time + '</td>' +
                    '<td>' + data[i].price + '</td>' +
                    '</tr>');
            }
        }
    }
};
