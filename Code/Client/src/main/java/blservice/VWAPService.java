package blservice;

import bl.VWAP_Param;
import vo.VolumeVO;

import java.util.List;

/**
 * Created by ZhangYF on 2016/8/24.
 */
public interface VWAPService {
    /**
     * 预测用户各时段交易量
     * @param param 传入VWAP中需要的参数，详见@see VWAP_Param
     * @return 预测的各时间段的交易量
     * @throws Exception
     */
    public List<VolumeVO> predictVn(VWAP_Param param) throws Exception;

}
