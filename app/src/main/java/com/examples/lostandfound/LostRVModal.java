package com.examples.lostandfound;

import android.os.Parcel;
import android.os.Parcelable;

public class LostRVModal implements Parcelable {
   private String itemname ;
   private String losername ;
   private String loserphone;
   private String loseremail ;
   private String itemloc;
   private String itemdesc ;
   private String lostitemid ;

   public LostRVModal(){

   }
    public LostRVModal(String itemname, String losername, String loserphone, String loseremail, String itemloc,
                       String itemdesc, String lostitemid) {
        this.itemname = itemname;
        this.losername = losername;
        this.loserphone = loserphone;
        this.loseremail = loseremail;
        this.itemloc = itemloc;
        this.itemdesc = itemdesc;
        this.lostitemid = lostitemid;
    }

    protected LostRVModal(Parcel in) {
        itemname = in.readString();
        losername = in.readString();
        loserphone = in.readString();
        loseremail = in.readString();
        itemloc = in.readString();
        itemdesc = in.readString();
        lostitemid = in.readString();
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

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getLosername() {
        return losername;
    }

    public void setLosername(String losername) {
        this.losername = losername;
    }

    public String getLoserphone() {
        return loserphone;
    }

    public void setLoserphone(String loserphone) {
        this.loserphone = loserphone;
    }

    public String getLoseremail() {
        return loseremail;
    }

    public void setLoseremail(String loseremail) {
        this.loseremail = loseremail;
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

    public String getLostitemid() {
        return lostitemid;
    }

    public void setLostitemid(String lostitemid) {
        this.lostitemid = lostitemid;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemname);
        parcel.writeString(losername);
        parcel.writeString(loserphone);
        parcel.writeString(loseremail);
        parcel.writeString(itemloc);
        parcel.writeString(itemdesc);
        parcel.writeString(lostitemid);
    }
}
