package android.mobileapp.qrcode.data.storage.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Folder implements Parcelable {

    private int resId;

    private String name;

    private String type;

    private String path;

    public Folder() {}

    public Folder(int resId, String name, String type, String path) {
        this.resId = resId;
        this.name = name;
        this.type = type;
        this.path = path;
    }

    protected Folder(Parcel in) {
        resId = in.readInt();
        name = in.readString();
        type = in.readString();
        path = in.readString();
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>() {
        @Override
        public Folder createFromParcel(Parcel in) {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size) {
            return new Folder[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(resId);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(path);
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Creator<Folder> getCREATOR() {
        return CREATOR;
    }
}
