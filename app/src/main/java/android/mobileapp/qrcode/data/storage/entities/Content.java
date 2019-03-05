package android.mobileapp.qrcode.data.storage.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.mobileapp.qrcode.data.DatabaseInfo;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vuongluis on 4/14/2018.
 * @author vuongluis
 * @version 0.0.1
 */

@Entity(tableName = DatabaseInfo.Tables.Content)
public class Content implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DatabaseInfo.Content.COLUMN_CONTENT_ID)
    @SerializedName("contentID")
    private int mContentID;

    @ColumnInfo(name = DatabaseInfo.Content.COLUMN_CONTENT_TYPE)
    @SerializedName("contentType")
    private String mContentType;

    @ColumnInfo(name = DatabaseInfo.Content.COLUMN_CONTENT_DATA)
    @SerializedName("contentData")
    private String mContentData;

    @ColumnInfo(name = DatabaseInfo.Content.COLUMN_CONTENT_DATE)
    @SerializedName("mContentDate")
    private String mContentDate;

    @ColumnInfo(name = DatabaseInfo.Content.COLUMN_CONTENT_USER_ID)
    @SerializedName("mContentUserID")
    private int mContentUserID;

    public Content(int contentID, String contentType, String contentData, String contentDate, int contentUserID) {
        this.mContentID = contentID;
        this.mContentType = contentType;
        this.mContentData = contentData;
        this.mContentDate = contentDate;
        this.mContentUserID = contentUserID;
    }

    @Ignore
    public Content(){
    }

    protected Content(Parcel in) {
        mContentID = in.readInt();
        mContentType = in.readString();
        mContentData = in.readString();
        mContentDate = in.readString();
        mContentUserID = in.readInt();
    }

    public static final Creator<Content> CREATOR = new Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel in) {
            return new Content(in);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    public static Creator<Content> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.mContentID);
        dest.writeString(this.mContentType);
        dest.writeString(this.mContentData);
        dest.writeString(this.mContentDate);
        dest.writeValue(this.mContentUserID);
    }

    public int getContentID() {
        return mContentID;
    }

    public void setContentID(int mContentID) {
        this.mContentID = mContentID;
    }

    public String getContentType() {
        return mContentType;
    }

    public void setContentType(String mContentType) {
        this.mContentType = mContentType;
    }

    public String getContentData() {
        return mContentData;
    }

    public void setContentData(String mContentData) {
        this.mContentData = mContentData;
    }

    public String getContentDate() {
        return mContentDate;
    }

    public void setContentDate(String mContentDate) {
        this.mContentDate = mContentDate;
    }

    public int getContentUserID() {
        return mContentUserID;
    }

    public void setContentUserID(int mContentUserID) {
        this.mContentUserID = mContentUserID;
    }
}
