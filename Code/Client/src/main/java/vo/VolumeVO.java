package vo;

/**
 * Created by ZhangYF on 2016/8/30.
 */
public class VolumeVO {

    //交易时间段
    private String time;

    //预测用户交易量
    private int volume;

    public VolumeVO(String time, int volume) {
        this.time = time;
        this.volume = volume;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}
