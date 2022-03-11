package com.iai.ishoes.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SensorValueMap {
    private Integer sensorType;
    private Integer p;
    private Integer inputDigitalValue;
    public SensorValueMap(Integer sensorType, Integer p, Integer inputDigitalValue){
        this.sensorType=sensorType;
        this.p=p;
        this.inputDigitalValue=inputDigitalValue;
    }

    public int getSensorType() {
        return sensorType;
    }

    public int getP() {
        return p;
    }

    public int getInputDigitalValue() {
        return inputDigitalValue;
    }

}
