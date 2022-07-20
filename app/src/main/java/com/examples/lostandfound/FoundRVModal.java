package com.examples.lostandfound;

import android.os.Parcel;
import android.os.Parcelable;

public class FoundRVModal implements Parcelable {
    private String itemname ;
    private String findername ;
    private String finderphone;
    private String finderemail ;
    private String itemloc;
    private String itemdesc ;
    private String founditemid ;

    public FoundRVModal(){

    }
    public FoundRVModal(String itemname, String findername, String finderphone, String finderemail, String itemloc,
                        String itemdesc, String founditemid) {
        this.itemname = itemname;
        this.findername = findername;
        this.finderphone = finderphone;
        this.finderemail = finderemail;
        this.itemloc = itemloc;
        this.itemdesc = itemdesc;
        this.founditemid = founditemid;
    }

    protected FoundRVModal(Parcel in) {
        itemname = in.readString();
        findername = in.readString();
        finderphone = in.readString();
        finderemail = in.readString();
        itemloc = in.readString();
        itemdesc = in.readString();
        founditemid = in.readString();
    }

    public static final Creator<FoundRVModal> CREATOR = new Creator<FoundRVModal>() {
        @Override
        public FoundRVModal createFromParcel(Parcel in) {
            return new FoundRVModal(in);
        }

        @Override
        public FoundRVModal[] newArray(int size) {
            return new FoundRVModal[size];
        }
    };

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getFindername() {
        return findername;
    }

    public void setFindername(String findername) {
        this.findername = findername;
    }

    public String getFinderphone() {
        return finderphone;
    }

    public void setFinderphone(String finderphone) {
        this.finderphone = finderphone;
    }

    public String getFinderemail() {
        return finderemail;
    }

    public void setFinderemail(String finderemail) {
        this.finderemail = finderemail;
    }

    public String getItemloc() {
        return itemloc;
    }

    public void setItemloc(String itemloc) {
        this.itemloc = itemloc;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public String getFounditemid() {
        return founditemid;
    }

    public void setFounditemid(String founditemid) {
        this.founditemid = founditemid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemname);
        parcel.writeString(findername);
        parcel.writeString(finderphone);
        parcel.writeString(finderemail);
        parcel.writeString(itemloc);
        parcel.writeString(itemdesc);
        parcel.writeString(founditemid);
    }
}

