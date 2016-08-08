package cn.edu.nju.software.svm;
import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import cn.edu.nju.software.service.MLForVWAPService;

/**
 * Created by LiuXingf on 2016/8/8.
 */
public class MLForVWAPServer implements MLForVWAPService {

    svm_problem problem;          //定义是vm_problem对象
    svm_parameter param;         //svm模型参数
    svm_model model;             //svm分类模型


    public MLForVWAPServer() {
       problem=new svm_problem();
        param=new svm_parameter();
        model=new svm_model();
    }

    public double getForecastPrice(String stockID){
        return 1.0;
    }

    public int    getForecastVol(String stockID){
        return 0;
    };
}
