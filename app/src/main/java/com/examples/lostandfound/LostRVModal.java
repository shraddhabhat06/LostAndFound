package com.examples.lostandfound;



import android.os.Parcel;
import android.os.Parcelable;

public class LostRVModal implements Parcelable {
    //creating variables for our different fields.
    private String itemName;
    private String loserName ;
    private String loserPhone;
    private String loserEmail ;
    private String itemLocation;
    private String itemDesc;
    private String itemId;


    public String getItemId() {
        return itemId;
    }

    public void setCourseId(String itemId) {
        this.itemId = itemId;
    }


    //creating an empty constructor.
    public LostRVModal() {

    }

    protected LostRVModal(Parcel in) {
        itemName = in.readString();
        itemId = in.readString();
        itemDesc = in.readString();
        itemLocation = in.readString();
        loserEmail = in.readString();
        loserName = in.readString();
        loserPhone = in.readString();
    }

    public static final Creator<LostRVModal> CREATOR = new Creator<LostRVModal>() {
        @Override
        public LostRVModal createFromParcel(Parcel in) {
            return new LostRVModal(in);
        }

        @Override
        public LostRVModal[] newArray(int size) {
            return new LostRVModal[size];
        }
    };

    //creating getter and setter methods.
    public String getItemName() {
        return itemName;
    }

    public void setCourseName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getLoserName() {
        return loserName;
    }

    public void setLoserName(String loserName) {
        this.loserName = loserName;
    }

    public String getLoserPhone() {
        return loserPhone;
    }

    public void setLoserPhone(String loserPhone) {
        this.loserPhone = loserPhone;
    }

    public String getLoserEmail() {
        return loserEmail;
    }

    public void getLoserEmail(String loserEmail) {this.loserEmail = loserEmail;}

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String courseLink) {
        this.itemLocation = courseLink;
    }


    public LostRVModal(String itemId,String itemName,String loserName,String loserPhone,String loserEmail,String itemDesc,String itemLocation) {
        this.itemId =itemId ;
        this.itemName=itemName;
        this.loserName=loserName;
        this.loserPhone=loserPhone;
        this.loserEmail=loserEmail;
        this.itemDesc=itemDesc;
        this.itemLocation=itemLocation;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemId);
        dest.writeString(itemName);
        dest.writeString(loserName);
        dest.writeString(loserPhone);
        dest.writeString(loserEmail);
        dest.writeString(itemLocation);
    }
}
