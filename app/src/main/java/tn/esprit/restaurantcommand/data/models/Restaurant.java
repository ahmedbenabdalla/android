package tn.esprit.restaurantcommand.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Restaurant  implements Parcelable {
    private String _id,name,address,imageUrl;


    public Restaurant() {

    }

    public Restaurant(String _id, String name, String address, String imageUrl) {
        this._id = _id;
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    protected Restaurant(Parcel in) {
        _id = in.readString();
        name = in.readString();
        address = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(imageUrl);
    }
}
