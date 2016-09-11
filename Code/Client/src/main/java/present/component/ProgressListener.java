package present.component;

/**
 * Created by song on 16-9-11.
 *
 * 进度条(时间线)监听
 */
public interface ProgressListener {

    /**
     * 鼠标滑动,时间线的值改变
     * @param percent 鼠标所在位置占总长度的比例
     */
    void valueChanged(double percent);

    /**
     * 获取要显示的提示信息
     * @param percent 鼠标所在位置占总长度的比例
     */
    String getToolTipText(double percent);
}
