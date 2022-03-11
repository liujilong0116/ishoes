package com.iai.ishoes.bean;

//import com.baidu.mapapi.model.LatLng; //百度地图     先不需要

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageEvent {
//    private List<LatLng> latLngList;     //百度地图
    private boolean positioning;
    private String accuracy;
    private String latitude;
    private String longitude;
    private boolean restart;
    private boolean isSport;
    private boolean query;
    private boolean isShowTrack;
    private String stepCount;
    private String distance;
    private int direction;

    public MessageEvent() {
    }

    public MessageEvent(String stepCount, String distance) {
        this.stepCount = stepCount;
        this.distance = distance;
    }

//    public int getDirection() {
//        return direction;
//    }
//
//    public void setDirection(int direction) {
//        this.direction = direction;
//    }
//
//    public String getStepCount() {
//        return stepCount;
//    }
//
//    public void setStepCount(String stepCount) {
//        this.stepCount = stepCount;
//    }
//
//    public String getDistance() {
//        return distance;
//    }
//
//    public void setDistance(String distance) {
//        this.distance = distance;
//    }
//
    public boolean isShowTrack() {
        return isShowTrack;
    }
//
//    public void setShowTrack(boolean showTrack) {
//        isShowTrack = showTrack;
//    }
//
////    public boolean isQuery() {
////        return query;
////    }
////
////    public void setQuery(boolean query) {
////        this.query = query;
////    }
//
//    public boolean isSport() {
//        return isSport;
//    }
//
//    public void setSport(boolean sport) {
//        isSport = sport;
//    }
//
////    public boolean isRestart() {
////        return restart;
////    }
//
////    public void setRestart(boolean restart) {
////        this.restart = restart;
////    }
//
//    public String getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(String latitude) {
//        this.latitude = latitude;
//    }
//
//    public String getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(String longitude) {
//        this.longitude = longitude;
//    }
//
//    public String getAccuracy() {
//        return accuracy;
//    }
//
//    public void setAccuracy(String accuracy) {
//        this.accuracy = accuracy;
//    }
//
////    public boolean isPositioning() {
////        return positioning;
////    }
////
////    public void setPositioning(boolean positioning) {
////        this.positioning = positioning;
////    }
//
//    public List<LatLng> getLatLngList() {
//        return latLngList;
//    }
//
//    public void setLatLngList(List<LatLng> latLngList) {
//        this.latLngList = latLngList;
//    }

}
