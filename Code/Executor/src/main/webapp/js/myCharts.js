/**
 * Created by song on 16-8-15.
 *
 * 图表相关操作
 */

$(function() {
    initCharts();

    timeSeriesChart.draw();

    kLine.getKLine('KLineDay');
    kLine.getKLine('KLineWeek');
    kLine.getKLine('KLineMonth');
});

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
 * K线图对象
 * @type {{getKLine: kLine.getKLine, drawKLine: kLine.drawKLine}}
 */
var kLine = {
    /**
     * 获取K线数据
     * @param type K线类型(day/week/month)
     */
    getKLine: function (type) {
        jQuery.ajax({
            url: '/' + type,
            dataType: 'json',
            data: 'codeNum=' + id,
            success: function (data) {
                var array = [];

                for (var i = 0; i < data.length; i++) {
                    array.push([data[i].date, data[i].open, data[i].close, data[i].low, data[i].high])
                }

                kLine.drawKLine(type, array);
            }
        });
    },

    /**
     * 绘制K线图
     * @param type K线图类型(day/week/month)
     * @param data 数据
     */
    drawKLine: function (type, data) {
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
                text: basicData.stockBasicInfo.name,
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

};

var timeSeriesChart = {
    draw: function() {
        jQuery.ajax({
            url: '/nowTime',
            cache: false,
            data: 'codeNum=' + id,
            success: function(data) {
                var array = data.object;
                var date, dataTime = [], dataPrice = [], dataPriceAvg = [];
                var temp = {
                    timeLine: '',
                    price: 0,
                    avePrice: 0
                };

                for (var i = 0; i < array.length; i++) {
                    temp = array[i];
                    // 去掉日期
                    dataTime.push(temp.timeLine.substr(11));
                    dataPrice.push(temp.price.toFixed(2));
                    dataPriceAvg.push(temp.avePrice.toFixed(2));
                }
                date = temp.timeLine.substr(0, 10);

                timeSeriesChart.drawChart(date, dataTime, dataPrice, dataPriceAvg);
            }
        })
    },

    drawChart: function(date, dataTime, dataPrice, dataPriceAvg) {
        var myChart = echarts.init(document.getElementById('timeSeriesChart'));
        var option = {
            title: {
                text: date
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['最新价格', '均价'],
                selectedMode: false
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: false},
                    dataView: {show: false, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: false}
                }
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    boundaryGap: false,
                    data: dataTime
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    axisLine: {onZero: false},
                    axisLabel: {
                        formatter: '{value}'
                    },
                    scale: true,
                    splitArea: {
                        show: true
                    }
                }
            ],
            series: [
                {
                    name: '最新价格',
                    type: 'line',
                    data: dataPrice
                },
                {
                    name: '均价',
                    type: 'line',
                    data: dataPriceAvg
                }
            ]
        };

        myChart.setOption(option);
    }
};