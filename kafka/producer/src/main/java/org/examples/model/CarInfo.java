package org.examples.model;

import java.util.Date;

public class CarInfo {
    int roadId;
    int speed;
    String plateNumber;
    Date takePictureTime;

    public CarInfo() {
    }

    public CarInfo(int roadId, int speed, String plateNumber, Date takePictureTime) {
        this.roadId = roadId;
        this.speed = speed;
        this.plateNumber = plateNumber;
        this.takePictureTime = takePictureTime;
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Date getTakePictureTime() {
        return takePictureTime;
    }

    public void setTakePictureTime(Date takePictureTime) {
        this.takePictureTime = takePictureTime;
    }
}
