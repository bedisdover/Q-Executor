package cn.edu.nju.software.service;

import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import libsvm.svm_model;
import libsvm.svm_problem;

import java.util.ArrayList;

/**
 * Created by admin on 2016/8/16.
 */
public class MLForVWAPServiceImpl implements MLForVWAPService {

    private StockMLService stockService;
    private svm_problem problem;     //定义svm_problem对象
    private svm_problem param  ;    //svm参数
    private svm_model model;       //训练的svm模型



    //返回最新数据下动态预测的均价
    public MLForVWAPPriceVO getDynamicPrice(String stockID){

        ArrayList<Double> list=new ArrayList<>();
        int currentTime=1;
        MLForVWAPPriceVO vo=new MLForVWAPPriceVO(list,currentTime);
        return  vo;
    }


    //返回最新数据下静态预测的48个成交量
    public ArrayList<Integer>   getStaticVol(String stockID){
        ArrayList<Integer> list=new ArrayList<>();

        return list;
    }
}
