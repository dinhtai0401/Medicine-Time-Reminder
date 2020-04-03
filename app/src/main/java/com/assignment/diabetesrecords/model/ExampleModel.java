package vn.phoneandhisfriends.washcar_store.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExampleModel implements Serializable, Parcelable, Comparable<ExampleModel>{
    private int avt;
    private String name;
    private String phone;
    private float price;
    private String date;
    private String time;
    public ExampleModel() {
    }

    public ExampleModel(int avt, String name, String phone,float price, String date, String time) {
        this.avt = avt;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.date = date;
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAvt() {
        return avt;
    }

    public void setAvt(int avt) {
        this.avt = avt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected ExampleModel(Parcel in) {
        this.avt = in.readInt();
        this.name = in.readString();
        this.phone = in.readString();
        this.price = in.readFloat();
        this.time = in.readString();
    }

    public static final Creator<ExampleModel> CREATOR = new Creator<ExampleModel>() {
        @Override
        public ExampleModel createFromParcel(Parcel in) {
            return new ExampleModel(in);
        }

        @Override
        public ExampleModel[] newArray(int size) {
            return new ExampleModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(avt);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeFloat(price);
        dest.writeString(time);
    }


    @Override
    public int compareTo(@NonNull ExampleModel o) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date firstDate = null;
        Date secondDate = null;
        try {
            secondDate = simpleDateFormat.parse(this.getDate()+" "+this.getTime());
            firstDate = simpleDateFormat.parse(o.getDate()+" "+o.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (firstDate != null && secondDate != null) {
            return firstDate.before(secondDate) ? 1 : -1;
        }
        return 0;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
