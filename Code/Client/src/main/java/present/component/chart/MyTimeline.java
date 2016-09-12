package present.component.chart;

import org.jfree.chart.axis.SegmentedTimeline;

import java.util.Date;

/**
 * Created by song on 16-9-10.
 * <p>
 * 自定义时间线,去除无数据的时间点的影响
 */
public class MyTimeline {
    // 475b9dd7dfcfb5f38bccc0276d161c22

    /**
     * 获取日线的时间线
     *
     * @return 不包含周六, 周日及法定假日
     */
    public static SegmentedTimeline getTimeLineForDay() {
        SegmentedTimeline timeline = SegmentedTimeline.newMondayThroughFridayTimeline();

        return timeline;
    }

    /**
     * |0~9:29|9:30~11:30|11:31~12:59|13:00~15:00|15:01~23:59|
     * |      |<<<<<<<<<<|           |<<<<<<<<<<<|           |
     *
     * @return
     */
    public static SegmentedTimeline getTimeLineFor5Minute() {
        SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 1351, 89);
//        SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 121, 89);
//        timeline.setBaseTimeline(SegmentedTimeline.newFifteenMinuteTimeline());
        timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 780 * SegmentedTimeline.MINUTE_SEGMENT_SIZE);
        timeline.setBaseTimeline(getBaseLine());
//        long time = new Date(2016, 8, 1, 15, 0).getTime();
//        timeline.addException(time, time + 12 * 60 * SegmentedTimeline.MINUTE_SEGMENT_SIZE);
//        timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 570 * SegmentedTimeline.MINUTE_SEGMENT_SIZE);
//        timeline.setStartTime(new Date(2016, 8, 2, 9, 30, 0).getTime());


        return timeline;
    }

    public static SegmentedTimeline getTimeLineFor15Minute() {
        SegmentedTimeline timeline = new SegmentedTimeline(900000L, 22, 64);
        timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 38L * timeline.getSegmentSize());
        timeline.setBaseTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

        return timeline;
    }

    private static SegmentedTimeline getBaseLine() {
        SegmentedTimeline timeline = new SegmentedTimeline(900000L, 22, 64);
        timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 38L * timeline.getSegmentSize());
        timeline.setBaseTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());

        return timeline;
    }
}
