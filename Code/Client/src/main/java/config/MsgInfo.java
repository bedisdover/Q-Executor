package config;

/**
 * Created by Jiayiwu on 16/7/29.
 * Mail:wujiayi@lgdreamer.com
 * Change everywhere
 */
public class MsgInfo {
    private boolean state;
    private String info;
    private Object object = null;

    public MsgInfo(boolean state, String info) {
        this.state = state;
        this.info = info;
    }

    public MsgInfo(boolean state, String info, Object object) {
        this.state = state;
        this.info = info;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
