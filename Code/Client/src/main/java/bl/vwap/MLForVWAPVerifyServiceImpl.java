package bl.vwap;

import blservice.vwap.MLForVWAPVerifyService;
import libsvm.*;
import po.InforForMLPO;
import po.StockForMLPO;
import service.StockMLService;
import service.StockMLServiceImpl;
import vo.MLForVWAPPriceVO;
import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by admin on 2016/11/17.
 */
public class MLForVWAPVerifyServiceImpl extends TimerTask implements MLForVWAPVerifyService {
    @Resource
    private StockMLService stockService;

    private svm_problem problem;          //定义svm_problem对象
    private svm_parameter param  ;        //svm参数
    private svm_model model;              //训练的svm模型
    private svm_node[][] trainingData;   //训练集数据
    private svm_node[] predict;          //预测数据
    private double[] labels;            //对应的标记
    private int numOfStaticAttr;
    private int numOfDynamicAttr;

    //用于存储深夜计算数据的list
    private   ArrayList<ArrayList<Integer>> staticVolAllStock;
    private   ArrayList<ArrayList<Double>> staticPriceAllStock;
    private   ArrayList<ArrayList<svm_model>> dynamicPriceModelAllStock;

    //单例模式
    private static MLForVWAPServiceImpl single;

    //传入日期
    private Date date;

    public static  synchronized MLForVWAPServiceImpl getInstance(){
        if(single == null){
            single=new MLForVWAPServiceImpl();
        }
        return single;
    }


    private MLForVWAPVerifyServiceImpl(Date date) {
        stockService=new StockMLServiceImpl();
        this.numOfDynamicAttr=36;
        this.numOfStaticAttr=30;
        this.staticVolAllStock =new ArrayList<>();
        this.staticPriceAllStock=new ArrayList<>();

        this.date=date;

        ArrayList<Integer> list=new ArrayList<Integer>();
        ArrayList<Double>  listt=new ArrayList<Double>();
        for(int i=0;i<48;i++){
            list.add(-1);
            listt.add(-1.0);
        }
        for(int i=0;i<15;i++){
            staticVolAllStock.add(list);
            staticPriceAllStock.add(listt);
        }

        this.dynamicPriceModelAllStock=new ArrayList<>();
    }

    //svm相关数据初始化
    private void initSVM(){
        System.gc();

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

    }

    //获取指定股票指定时间片下用于静态模型的训练集和预测数据
    private void initStaticData(String stockID, int currentTime, Type type,Date date){
        ArrayList<StockForMLPO> poList=stockService.getStockDataMLTest(stockID,200,currentTime,date);
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
    private void initDynamicData(String stockID, int currentTime,Date date){
        ArrayList<InforForMLPO> poList=  stockService.getDynamicInforMLTest( stockID, 200 , currentTime,date);

        trainingData=new svm_node[poList.size()][numOfDynamicAttr];
        labels=new double[poList.size()];

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
    }


    //返回最新数据下静态预测的48个成交量
    private void  getStaticVol_svm(Date date){

        System.gc();
        ArrayList<Integer> list ;
        String[] all_stock=stockService .getStocksNeedCalTest();
        for(int j=0;j<all_stock.length;j++) {
            list=new ArrayList<Integer>();
            for (int i = 1; i < 49; i++) {
                initStaticData(all_stock[j],i,Type.VOL,date);
                initSVM();
                //训练模型
                model = svm.svm_train(problem, param);
                Double predictValue= svm.svm_predict(model,predict)*1000000;
                int value_int=predictValue.intValue();
                list.add( value_int);
            }
            System.out.println(all_stock[j] +" size: "+staticVolAllStock.size()+" getStaticVol_svm currentThread: "+Thread.currentThread().getId());
            this.staticVolAllStock.add(list);
        }


    }

    //返回最新数据下静态预测的48个价格
    private void getStaticPrice_svm(Date date){

        System.gc();
        ArrayList<Double> list;
        DecimalFormat df=new DecimalFormat("0.00");

        String[] all_stock=stockService .getStocksNeedCalTest();
        for(int j=0;j<all_stock.length;j++) {
            list=new ArrayList<Double>();
            for(int i=1;i<49;i++){
                initStaticData(all_stock[j],i,Type.PRICE,date);
                initSVM();
                //训练模型
                model = svm.svm_train(problem, param);
                Double predictValue= svm.svm_predict(model,predict);
                String twobit= df.format(predictValue);
                list.add(Double.parseDouble(twobit));
            }
            this.staticPriceAllStock.add(list);
        }
    }

    //动态预测的模型存储
    private void getDynamicPrice_svm(Date date) {

        System.gc();
        ArrayList<svm_model> list ;
        String[] all_stock = stockService.getStocksNeedCalTest();
        for (int j = 0; j < all_stock.length; j++) {
            list = new ArrayList<>();
            for (int i = 4; i < 49; i++) {
                initDynamicData(all_stock[j], i,date);
                initSVM();
                //训练模型
                model = svm.svm_train(problem, param);
                list.add(model);
            }
            this.dynamicPriceModelAllStock.add(list);
        }
    }

    //动态模型的预测数据
    private void initDynamicPredict(String stockID,int currentTime,Date date){
        predict=new svm_node[numOfDynamicAttr];

        //获取预测数据

        //前三天数据
        ArrayList<StockForMLPO> threeDayList=stockService.getStockDataMLTest(stockID,3,currentTime,date);
        //前三个时间片数据
        ArrayList<StockForMLPO> todayList=stockService.getTodayInforMLTest(stockID,date);

        StockForMLPO[] predictData=new StockForMLPO[6];
        predictData[0]=threeDayList.get(0);
        predictData[1]=threeDayList.get(1);
        predictData[2]=threeDayList.get(2);
        int size=todayList.size();
        predictData[3]=todayList.get(size-3);
        predictData[4]=todayList.get(size-2);
        predictData[5]=todayList.get(size-1);

        svm_node thisNode1;
        svm_node thisNode2;
        svm_node thisNode3;
        svm_node thisNode4;
        svm_node thisNode5;
        svm_node thisNode6;
        StockForMLPO onePO;
        for(int i=0;i<predictData.length;i++){
            onePO=predictData[i];

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



    //对外接口——成交量
    public ArrayList<Integer>   getStaticVol(String stockID,Date date){

        ArrayList<Integer> result =null;
        String[] all_stock=stockService.getStocksNeedCalTest();
        for(int i=0;i<all_stock.length;i++){
            if(stockID.equals(all_stock[i])){
                result = staticVolAllStock.get(i);
                break;
            }
        }

        return result;
    }

    //对外接口——价格
    public MLForVWAPPriceVO getDynamicPrice(String stockID, Date date){
        MLForVWAPPriceVO result=null;
        ArrayList<Double> list = null;
        int currentTime=-1;

        int indexOfTarget=-1;
        String[] all_stock=stockService.getStocksNeedCalTest();
        ArrayList<StockForMLPO> todayList=stockService.getTodayInforMLTest(stockID,date);
        currentTime=todayList.size();

        //获得股票在股票列表的第几个（表驱动思想）
        for(int i=0;i<all_stock.length;i++){
            if(stockID.equals(all_stock[i])){
                indexOfTarget=i;
                break;
            }
        }

        if(currentTime<=3){
            list=this.staticPriceAllStock.get(indexOfTarget);
        }else if(currentTime == 48){
            StockForMLPO thisPO;
            list=new ArrayList<Double>();
            for(int i=0;i<currentTime;i++){
                thisPO=todayList.get(i);
                list.add(thisPO.getAvg());
            }
        }else{

            //处于股市交易进行中:list包含3个部分，1.真实已产生的价格数据；2.动态预测的价格数据；3.静态预测的价格数据
            StockForMLPO thisPO;
            list=new ArrayList<Double>();

            //第一部分
            for(int i=0;i<currentTime;i++){
                thisPO=todayList.get(i);
                list.add(thisPO.getAvg());
            }

            //第二部分
            svm_model thisModel= this.dynamicPriceModelAllStock.get(indexOfTarget).get(currentTime-4);
            initDynamicPredict(stockID,currentTime,date);
            Double predictValue=svm.svm_predict(thisModel,predict);
            DecimalFormat df=new DecimalFormat("0.00");
            String twobit= df.format(predictValue);
            list.add(Double.parseDouble(twobit));

            //第三部分
            if(currentTime !=47) {
                ArrayList<Double> staticList = this.staticPriceAllStock.get(indexOfTarget);
                for (int k = currentTime + 1; k < staticList.size(); k++) {
                    list.add(staticList.get(k));
                }
            }
        }

        result=new MLForVWAPPriceVO(list,currentTime);
        return result;
    }


    @Override
    public void run() {
        try {
            this.staticPriceAllStock.clear();
            this.staticVolAllStock.clear();
            this.dynamicPriceModelAllStock.clear();

            this.getStaticVol_svm(date);
            this.getStaticPrice_svm(date);
            this.getDynamicPrice_svm(date);
        } catch (Exception e) {
            System.out.println("svm thread error!");
        }

    }


    //枚举类：价格和成交量两种类型
    public enum Type{
        VOL,PRICE
    }

}
