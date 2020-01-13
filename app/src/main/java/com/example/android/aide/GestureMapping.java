package com.example.android.aide;


public class GestureMapping  {
    private String mGestureName;
    private int mImgResourceId;
    public GestureMapping(String gestureName,int imgResourceId){
        mGestureName=gestureName;
        mImgResourceId=imgResourceId;
    }
    public String getmGestureName(){return mGestureName;}
    public int getmImgResourceId(){return mImgResourceId;}
}
