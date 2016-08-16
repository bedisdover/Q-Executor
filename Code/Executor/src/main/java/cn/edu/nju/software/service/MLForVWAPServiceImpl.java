package cn.edu.nju.software.service;

import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import libsvm.*;
import java.util.ArrayList;


/**
 * Created by Liu on 2016/8/16.
 */
public class MLForVWAPServiceImpl implements MLForVWAPService {

    private StockMLService stockService;
    private svm_problem problem;     //定义svm_problem对象
    private svm_parameter param  ;    //svm参数
    private svm_model model;       //训练的svm模型
    private svm_node[][] trainingData; //训练集数据
    private svm_node[] predict;        //预测数据
    private double[] labels;          //对应的标记
    private int numOfAttr=30;

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

    //获取指定股票指定时间片下的训练集和预测数据
    private void initData(String stockID,int currentTime){
        ArrayList<StockForMLPO> poList=stockService.getStockDataML(stockID,200,currentTime);
        trainingData=new svm_node[poList.size()-5][ numOfAttr];
        labels=new double[poList.size()-5];
        predict=new svm_node[numOfAttr];

        for(int i=0;i<poList.size();i++){
            svm_node thisNode1;
            svm_node thisNode2;
            svm_node thisNode3;
            svm_node thisNode4;
            svm_node thisNode5;
            svm_node thisNode6;
            StockForMLPO thisPO;

            //连续5天的6个指标作为训练集的一条数据
            for(int k=0;k<5;k++){
                thisPO=poList.get(i+k);

                thisNode1=new svm_node();
                thisNode1.index=k*6+1;
                thisNode1.value=thisPO.getOpen();

                thisNode2=new svm_node();
                thisNode2.index=k*6+2;
                thisNode2.value=thisPO.getClose();

                thisNode3=new svm_node();
                thisNode3.index=k*6+3;
                thisNode3.value=thisPO.getLow();

                thisNode4=new svm_node();
                thisNode4.index=k*6+4;
                thisNode4.value=thisPO.getHigh();

                thisNode5=new svm_node();
                thisNode5.index=k*6+5;
                thisNode5.value=thisPO.getVol();

                thisNode6=new svm_node();
                thisNode6.index=k*6+6;
                thisNode6.value=thisPO.getAvg();

                if(i<poList.size()-5){
                    trainingData[i][k*6]=thisNode1;
                    trainingData[i][k*6+1]=thisNode2;
                    trainingData[i][k*6+2]=thisNode3;
                    trainingData[i][k*6+3]=thisNode4;
                    trainingData[i][k*6+4]=thisNode5;
                    trainingData[i][k*6+5]=thisNode6;
                }else{
                    predict[k*6]=thisNode1;
                    predict[k*6+1]=thisNode2;
                    predict[k*6+2]=thisNode3;
                    predict[k*6+3]=thisNode4;
                    predict[k*6+4]=thisNode5;
                    predict[k*6+5]=thisNode6;
                }
            }

            //标记
            if(i>4){
                labels[i-5]=poList.get(i).getAvg();
            }
        }

    }

    //返回最新数据下静态预测的48个成交量
    public ArrayList<Integer>   getStaticVol(String stockID){

        ArrayList<Integer> list=new ArrayList<>();

        for(int i=0;i<48;i++){
           initData(stockID,i);
           initSVM();
           Integer predictValue=(int) (svm.svm_predict(model,predict));
            list.add(predictValue);
       }

        return list;
    }


    //返回最新数据下动态预测的均价
    public MLForVWAPPriceVO getDynamicPrice(String stockID){

        ArrayList<Double> list=new ArrayList<>();
        int currentTime=1;
        MLForVWAPPriceVO vo=new MLForVWAPPriceVO(list,currentTime);
        return  vo;
    }

}
