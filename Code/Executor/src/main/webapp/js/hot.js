/**
 * Created by song on 16-8-15.
 * 
 * 热门股票相关操作
 */

$(function() {
    hotStock.getData();
});

var hotStock = {
    getData: function() {
        jQuery.ajax({
            url: '/HotStocks',
            success: function(data) {
                console.log(data);
            }
        });
    }
};
