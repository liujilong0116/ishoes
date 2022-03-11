package com.iai.ishoes.utils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.iai.ishoes.bean.SensorValueMap;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DigitalVoltageToPressureUtils {
    public static LoadingCache<SensorValueMap, Double> sensorComputeValueCache = CacheBuilder.newBuilder()
            .expireAfterWrite(6, TimeUnit.HOURS)
            .build(new CacheLoader<SensorValueMap,Double>() {
                @Override
                public Double load(SensorValueMap sensorValueMap) {
                    return DigitalVoltageToPressureUtils.getPressure(sensorValueMap.getSensorType(),sensorValueMap.getP(),sensorValueMap.getInputDigitalValue());
                }
            });

    //传感器类型与公式的前后脚掌的关系
    private static List<List<Integer>> sensorTypeRelationShipList = Arrays.asList(
          Arrays.asList(8,6),
          Arrays.asList(9,7),
          Arrays.asList(10,8),
          Arrays.asList(13,11),
          Arrays.asList(14,12),
          Arrays.asList(15,13),
          Arrays.asList(16,14),
          Arrays.asList(18,15),
          Arrays.asList(22,19)
    );
    //传感器类型与公式的前后脚掌的关系,引脚独立
    private static List<Integer> sensorTypeRelationShipList2 = Arrays.asList(
            9,7,9,7,9,7,9,7
    );

    public static Double getPressure(int sensorType,int pointNumber,int inputDigitalVolatageValue){
        if(sensorTypeRelationShipList.get(sensorType-1)==null){
            return 0d;
        }
        List<Integer> shipList= sensorTypeRelationShipList.get(sensorType-1);
        return translate(shipList.get(pointNumber/4),inputDigitalVolatageValue);
//        return translate(sensorTypeRelationShipList2.get(pointNumber),inputDigitalVolatageValue);//应杰明需求，每个点都不一样的type
    }
    /**
     *
     * @param n 曲线类型
     * @param inputDigitalVolatageValue  数字电压
     * @return
     */
    private static Double  translate(int n,int inputDigitalVolatageValue){
        int R= 10;
        double U = 3.3;
        if(inputDigitalVolatageValue<=0||inputDigitalVolatageValue>=4096){
            return 0d;
        }
        BigDecimal k = BigDecimal.valueOf(58.8).divide(BigDecimal.valueOf(5+(n-1)*0.5),4, RoundingMode.HALF_UP);
        BigDecimal x = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(R * inputDigitalVolatageValue).divide(BigDecimal.valueOf(  (U * 4096) / 0.825 / 4 - inputDigitalVolatageValue),4,RoundingMode.HALF_UP),4,RoundingMode.HALF_UP) ;
        return k.multiply(x).doubleValue();
    }



}
