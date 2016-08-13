/**
 * Created by song on 16-8-12.
 *
 * 搜索股票相关
 */

/**
 * 股票列表
 * @type {Array}
 */
var stockList = [];

/**
 * 历史搜索股票
 * @type {Array}
 */
var search_history;

/**
 * 默认显示股票条数
 * @type {number}
 */
var display_num = 5;

$(function () {
    search_history = $.cookie('search_history') == null ? [] : eval($.cookie('search_history'));

    initData();

    var text_elem = $('#search-text');
    var dropdown = $('#dropdown-menu');

    text_elem.on('focus', function () {
        // 默认不显示搜索结果下拉框
        if (text_elem.val() == '') {
            if (search_history.length != 0) { // 搜索内容为空时，显示搜索历史
                showHistory();
            }
        } else {
            dropdown.show();
        }
    }).on('input propertychange', function () {
        search(text_elem.val());
        dropdown.show();
    }).on('keypress', function (e) {
        if (e.which == 13) { // 回车
            jumpPage(getSelected().find('td:nth-child(2)').text());
        }
    }).on('keydown', function (e) {
        var selected = getSelected();
        var value = selected.find('td:nth-child(1)').text();

        if (e.which == 38) { // ↑
            if (!$(selected).is($('#content').find('tr').first())) {
                $(selected).removeClass('active').prev().addClass('active');
                text_elem.val(value);
            }
        } else if (e.which == 40) { // ↓
            if (!$(selected).is($('#content').find('tr').last())) {
                $(selected).removeClass('active').next().addClass('active');
                text_elem.val(value);
            }
        }
    }).focus();

    $(document.body).on('click', function (e) {
        if (!$(e.target).parents('div').is(dropdown) && !$(e.target).is(text_elem)) {
            dropdown.hide();
        }
    });
});

/**
 * 获得候选列表选中的股票
 */
function getSelected() {
    return $('#content').find('tr').filter(function () {
        return $(this).hasClass('active');
    });
}

/**
 * 初始化数据
 */
function initData() {
    jQuery.getJSON('../js/basicInfo.json', function (data) {
        stockList = data;
    });
}

/**
 * 搜索为空时，显示搜索历史
 */
function showHistory() {
    appendStocks(search_history);

    $('#table-footer').show();

    $('#clear-history').on('click', function () {
        $.cookie('search_history', null);
    }).show();

    $('#dropdown-menu').show();
}

/**
 * 搜索股票
 * @param content
 */
function search(content) {
    if (content == '') {
        return;
    }

    if (!isNaN(content) || content.substr(0, 2) == 'sh' || content.substr(0, 2) == 'sz') {
        searchCode(content);
    } else {
        searchName(content);
    }
}

/**
 * 按名称搜索股票
 * @param name
 */
function searchName(name) {
    var temp = [];
    for (var i = 0; i < stockList.length; i++) {
        if (contains(stockList[i].name, name)) {
            temp.push(stockList[i]);
        }
    }

    appendStocks(temp);

    /**
     * 判断一个字符串是否完全包含另一个字符串，字符可以不连续，但相互顺序需保持一致
     *  如：  中国石油 （中油） ----> 返回true
     *       中国石油 （油中） ----> 返回false
     * @param stockName
     * @param content
     * @returns {boolean}
     */
    function contains(stockName, content) {
        var last = -1;
        for (var i = 0; i < content.length; i++) {
            for (var j = last + 1; j < stockName.length; j++) {
                if (stockName[j] == content[i]) {
                    last = j;
                    break;
                }
            }

            if (j == stockName.length) {
                return false;
            }
        }

        return true;
    }
}

/**
 * 按代码搜索股票
 * @param code
 */
function searchCode(code) {
    if (code == '') {
        return;
    }

    var temp = [];
    for (var i = 0; i < stockList.length; i++) {
        if (stockList[i].code.indexOf(code) != -1) {
            temp.push(stockList[i]);
        }
    }

    appendStocks(temp);
}

/**
 * 向提示列表中添加多只股票
 * @param stocks
 */
function appendStocks(stocks) {
    var content = $('#content');
    var footer = $('#table-footer');

    content.empty();
    footer.hide();

    if (stocks.length == 0) {
        content.append('<tr class="text-center"><td>很抱歉，未找到符合条件的股票。。。</td></tr>');
        return;
    }

    var number = Math.min(stocks.length, display_num);

    for (var i = 0; i < number; i++) {
        appendStock(stocks[i]);
    }

    if (stocks.length > display_num) {
        footer.show();
        $('#change').off('click')
            .on('click', function () {
                appendStocks(stocks.slice(display_num))

                $('#search-text').focus();
            }).show();
    }

    content.find('tr').each(function () {
        $(this).on('click', function () {
            jumpPage($(this).find('td:nth-child(2)').text());
        });
    }).first().addClass('active');
}

/**
 * 向提示列表中添加单只股票
 * @param stock
 */
function appendStock(stock) {
    if (stock == null) {
        return;
    }

    $('#content').append(
        '<tr>' +
        '<td>' + stock.name + '</td>' +
        '<td>' + stock.code + '</td>' +
        '<td>' + stock.industry + '</td>' +
        '</tr>'
    );
}

/**
 * 跳转界面
 * @param code 股票代码
 */
function jumpPage(code) {
    top.location = 'stock.html?id=' + code;
    console.log(code);
}