package cn.edu.nju.software.service;

import cn.edu.nju.software.po.InforForMLPO;
import cn.edu.nju.software.po.StockForMLPO;
import cn.edu.nju.software.vo.MLForVWAPPriceVO;
import libsvm.*;
import java.util.ArrayList;


/**
 * Created by Liu on 2016/8/16.
 */
public class MLForVWAPServiceImpl implements MLForVWAPService {

    private StockMLService stockService;
    private svm_problem problem;          //定义svm_problem对象
    private svm_parameter param  ;        //svm参数
    private svm_model model;              //训练的svm模型
    private svm_node[][] trainingData;   //训练集数据
    private svm_node[] predict;          //预测数据
    private double[] labels;            //对应的标记
    private int numOfStaticAttr;
    private int numOfDynamicAttr;

    public MLForVWAPServiceImpl( ) {
        stockService=new StockMLServiceImpl();
        this.numOfDynamicAttr=36;
        this.numOfStaticAttr=30;
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
        param.cache_size = 195;
        param.eps = 0.01;
        param.C = 1.9;

        //训练模型
        model = svm.svm_train(problem, param);
    }

    //获取指定股票指定时间片下用于静态模型的训练集和预测数据
    private void initStaticData(String stockID, int currentTime, Type type){
        ArrayList<StockForMLPO> poList=stockService.getStockDataML(stockID,200,currentTime);
        trainingData=new svm_node[poList.size()-5][numOfStaticAttr];
        labels=new double[poList.size()-5];
        predict=new svm_node[numOfStaticAttr];

        for(int i=0;i<poList.size()-4;i++){
            svm_node thisNode1=null;
            svm_node thisNode2=null;
            svm_node thisNode3=null;
            svm_node thisNode4=null;
            svm_node thisNode5=null;
            svm_node thisNode6=null;
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
                thisNode5.value=thisPO.getVol()/100000.0;

                thisNode6=new svm_node();
                thisNode6.index=k*6+6;
                thisNode6.value=thisPO.getAvg();

                if(i == poList.size()-5){
                    predict[k*6]=thisNode1;
                    predict[k*6+1]=thisNode2;
                    predict[k*6+2]=thisNode3;
                    predict[k*6+3]=thisNode4;
                    predict[k*6+4]=thisNode5;
                    predict[k*6+5]=thisNode6;
                }else{
                    trainingData[i][k*6]=thisNode1;
                    trainingData[i][k*6+1]=thisNode2;
                    trainingData[i][k*6+2]=thisNode3;
                    trainingData[i][k*6+3]=thisNode4;
                    trainingData[i][k*6+4]=thisNode5;
                    trainingData[i][k*6+5]=thisNode6;
                }
            }

            //标记
            if(i>4){
                switch (type){
                    case VOL:
                        labels[i-5]=poList.get(i).getVol()/100000.0;
                        break;
                    case PRICE:
                        labels[i-5]=poList.get(i).getAvg();
                        break;
                }
            }
        }

    }

    //获取指定股票指定时间片下用于动态模型的训练集和预测数据
    private void initDynamicData(String stockID, int currentTime){
        ArrayList<InforForMLPO> poList=  stockService.getDynamicInforML( stockID, 200 , currentTime+1);

        trainingData=new svm_node[poList.size()][numOfDynamicAttr];
        labels=new double[poList.size()];
        predict=new svm_node[numOfDynamicAttr];

        InforForMLPO thisInforPO;
        for(int i=0;i<poList.size();i++){
            thisInforPO=poList.get(i);

            //标记
            labels[i]=thisInforPO.getCurrentTime().getAvg();

            //训练属性
            StockForMLPO[] sevenArray  =new StockForMLPO[6];
            sevenArray[0]=thisInforPO.getFirstDay();
            sevenArray[1]=thisInforPO.getSecondDay();
            sevenArray[2]=thisInforPO.getThirdDay();
            sevenArray[3]=thisInforPO.getFirstTime();
            sevenArray[4]=thisInforPO.getSecondDay();
            sevenArray[5]=thisInforPO.getThirdTime();

            svm_node thisNode1;
            svm_node thisNode2;
            svm_node thisNode3;
            svm_node thisNode4;
            svm_node thisNode5;
            svm_node thisNode6;

            StockForMLPO   oneOfArray;
            for(int k=0;k<sevenArray.length;k++){
                oneOfArray=sevenArray[k];

                thisNode1=new svm_node();
                thisNode1.index=k*6+1;
                thisNode1.value=oneOfArray.getOpen();

                thisNode2=new svm_node();
                thisNode2.index=k*6+2;
                thisNode2.value=oneOfArray.getClose();

                thisNode3=new svm_node();
                thisNode3.index=k*6+3;
                thisNode3.value=oneOfArray.getLow();

                thisNode4=new svm_node();
                thisNode4.index=k*6+4;
                thisNode4.value=oneOfArray.getHigh();

                thisNode5=new svm_node();
                thisNode5.index=k*6+5;
                thisNode5.value=oneOfArray.getVol()/100000.0;

                thisNode6=new svm_node();
                thisNode6.index=k*6+6;
                thisNode6.value=oneOfArray.getAvg();

                trainingData[i][k*6]=thisNode1;
                trainingData[i][k*6+1]=thisNode2;
                trainingData[i][k*6+2]=thisNode3;
                trainingData[i][k*6+3]=thisNode4;
                trainingData[i][k*6+4]=thisNode5;
                trainingData[i][k*6+5]=thisNode6;
            }
        }

        //获取预测数据

        //前三天数据
        ArrayList<StockForMLPO> threeDayList=stockService.getStockDataML(stockID,3,currentTime);
        //前三个时间片数据
        ArrayList<StockForMLPO> todayList=stockService.getTodayInforML(stockID);

        StockForMLPO[] predictData=new StockForMLPO[6];
        predictData[0]=todayList.get(0);
        predictData[1]=todayList.get(1);
        predictData[2]=todayList.get(2);
        predictData[3]=todayList.get(currentTime-3);
        predictData[4]=todayList.get(currentTime-2);
        predictData[5]=todayList.get(currentTime-1);

        svm_node thisNode1;
        svm_node thisNode2;
        svm_node thisNode3;
        svm_node thisNode4;
        svm_node thisNode5;
        svm_node thisNode6;
        StockForMLPO onePO;
        for(int i=0;i<predictData.length;i++){
            onePO=threeDayList.get(i);

            thisNode1=new svm_node();
            thisNode1.index=i*6+1;
            thisNode1.value=onePO.getOpen();

            thisNode2=new svm_node();
            thisNode2.index=i*6+2;
            thisNode2.value=onePO.getClose();

            thisNode3=new svm_node();
            thisNode3.index=i*6+3;
            thisNode3.value=onePO.getLow();

            thisNode4=new svm_node();
            thisNode4.index=i*6+4;
            thisNode4.value=onePO.getHigh();

            thisNode5=new svm_node();
            thisNode5.index=i*6+5;
            thisNode5.value=onePO.getVol()/100000.0;

            thisNode6=new svm_node();
            thisNode6.index=i*6+6;
            thisNode6.value=onePO.getAvg();

            predict[i*6]=thisNode1;
            predict[i*6+1]=thisNode2;
            predict[i*6+2]=thisNode3;
            predict[i*6+3]=thisNode4;
            predict[i*6+4]=thisNode5;
            predict[i*6+5]=thisNode6;
        }


    }


    //返回最新数据下静态预测的48个成交量
    public ArrayList<Double>   getStaticVol(String stockID){

        ArrayList<Double> list=new ArrayList<>();

        for(int i=1;i<49;i++){
            initStaticData(stockID,i,Type.VOL);
            initSVM();
            Double predictValue= svm.svm_predict(model,predict)*1000000;
            list.add(predictValue);
       }

        return list;
    }

    //返回最新数据下静态预测的48个价格
    public ArrayList<Double>   getStaticPrice(String stockID){

        ArrayList<Double> list=new ArrayList<>();

        for(int i=0;i<48;i++){
            initStaticData(stockID,i,Type.PRICE);
            initSVM();
            Double predictValue=svm.svm_predict(model,predict);
            list.add(predictValue);
        }

        return list;
    }


    //返回最新数据下动态预测的均价
    public MLForVWAPPriceVO getDynamicPrice(String stockID){
        MLForVWAPPriceVO vo = null;
        int currentTime = -1;
        ArrayList<Double> list = null;

        ArrayList<StockForMLPO> todayList=stockService.getTodayInforML(stockID);
        currentTime=todayList.size();
        if(currentTime <= 3){
            //未到达动态预测条件，返回静态预测结果
            list=this.getStaticPrice(stockID);
        }else if(currentTime == 48){
            //今日所有时间片真实结果已出现,返回所有真实值
            StockForMLPO thisPO;
            list=new ArrayList<>();
            for(int i=0;i<currentTime;i++){
                thisPO=todayList.get(i);
                list.add(thisPO.getAvg());
            }
        }else{
            //处于股市交易进行中:list包含3个部分，1.真实已产生的价格数据；2.动态预测的价格数据；3.静态预测的价格数据
            StockForMLPO thisPO;
            list=new ArrayList<>();
            //第一部分
            for(int i=0;i<currentTime;i++){
                thisPO=todayList.get(i);
                list.add(thisPO.getAvg());
            }
            //第二部分
            initDynamicData(stockID,currentTime);
            initSVM();
            Double predictValue=svm.svm_predict(model,predict);
            list.add(predictValue);
            //第三部分
            ArrayList<Double> staticList=this.getStaticPrice(stockID);
            for(int k=currentTime+1;currentTime<staticList.size();k++){
                list.add(staticList.get(k));
            }
        }

        vo=new MLForVWAPPriceVO(list,currentTime);
        return  vo;
    }


    //枚举类：价格和成交量两种类型
    public enum Type{
            VOL,PRICE
    }

}
