/*
 *
 * Project Name: 	e-yantra Project:AIDE(AI and IoT Enabled home automation for Disabled and Elderly
 * Author List: 	Srushti Sachdev,Smruti Kshirsagar
 * Filename: 		Device.java
 * Functions: 		NONE
 * Global Variables:	NONE
 *
 */
package com.example.android.aide;

public class Device {
    private String NAME;
    private String TYPE;
    private String STATUS;
    private String GESTURE_ON;
    private String GESTURE_OFF;
    private String documentId;

    public Device(){}

    public String getDocumentId(){
        return documentId;
    }

    public void setDocumentId(String documentId){
        this.documentId = documentId;
    }

    public Device(String NAME, String TYPE,String STATUS, String GESTURE_ON, String GESTURE_OFF){
        this.NAME = NAME;
        this.TYPE = TYPE;
        this.STATUS = STATUS;
        this.GESTURE_ON = GESTURE_ON;
        this.GESTURE_OFF = GESTURE_OFF;
    }

    public String getName() {
        return NAME;
    }

    public String getType() {
        return TYPE;
    }

    public String getStatus() { return STATUS; }

    public String getGestureOn() { return GESTURE_ON; }

    public String getGestureOff() { return GESTURE_OFF; }
}
