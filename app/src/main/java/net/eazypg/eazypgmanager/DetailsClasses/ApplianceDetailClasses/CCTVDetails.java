package net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses;

public class CCTVDetails {

    public String id, roomNo, brand, model, timeSinceInstallation, nightVision, noOfChannels, recordingResolution;

    public CCTVDetails(String id, String roomNo, String brand, String model, String timeSinceInstallation, String nightVision, String noOfChannels, String recordingResolution) {
        this.id=id;
        this.roomNo = roomNo;
        this.brand = brand;
        this.model = model;
        this.timeSinceInstallation = timeSinceInstallation;
        this.nightVision = nightVision;
        this.noOfChannels = noOfChannels;
        this.recordingResolution = recordingResolution;
    }

    public CCTVDetails() {

        roomNo = "";
        brand = "";
        model = "";
        timeSinceInstallation = "";
        nightVision = "";
        noOfChannels = "";
        recordingResolution = "";
    }
}
