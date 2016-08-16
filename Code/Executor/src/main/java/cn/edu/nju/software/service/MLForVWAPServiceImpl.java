package cn.edu.nju.software.service;

import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import libsvm.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by admin on 2016/8/16.
 */
public class MLForVWAPServiceImpl implements MLForVWAPService {

    private StockMLService stockService;
    private svm_problem problem;     //定义svm_problem对象
    private svm_parameter param  ;    //svm参数
    private svm_model model;       //训练的svm模型
    private svm_node[][] trainingData; //训练集数据
    private double[] labels;          //对应的标记



    //返回最新数据下动态预测的均价
    public MLForVWAPPriceVO getDynamicPrice(String stockID){

        ArrayList<Double> list=new ArrayList<>();
        int currentTime=1;
        MLForVWAPPriceVO vo=new MLForVWAPPriceVO(list,currentTime);
        return  vo;
    }


    //svm相关数据初始化
    private void initSVM(){
        // 定义svm_problem对象
        problem = new svm_problem();
        problem.l = trainingData.length; // 向量个数
        problem.x = trainingData; // 训练集向量表
        problem.y = labels; // 对应的lable数组

        // 定义svm_parameter对象
        param = new svm_parameter();
        param.svm_type = svm_parameter.EPSILON_SVR;
        param.kernel_type = svm_parameter.LINEAR;
        param.cache_size = 100;
        param.eps = 0.00001;
        param.C = 1.9;

        //训练模型
        model = svm.svm_train(problem, param);

    }

    //返回最新数据下静态预测的48个成交量
    public ArrayList<Integer>   getStaticVol(String stockID){

        ArrayList<StockForMLPO> iterator=stockService.getStockDataML(stockID,200,1);
        StockForMLPO thisPO;


        ArrayList<Integer> list=new ArrayList<>();

        return list;
    }
}
