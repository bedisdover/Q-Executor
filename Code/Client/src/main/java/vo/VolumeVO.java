package vo;

/**
 * Created by ZhangYF on 2016/8/30.
 */
public class VolumeVO {

    //交易时间段
    private String time;

    //预测用户交易量
    private long volume;

    public VolumeVO(String time, long volume) {
        this.time = time;
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}
