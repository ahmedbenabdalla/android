package tn.esprit.restaurantcommand.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Dish implements Parcelable {

    private String _id,restaurantId,title,description,imageUrl,price,cookingTime;
    private Boolean isAvaible;

    public Dish( ) {

    }

    protected Dish(Parcel in) {
        _id = in.readString();
        restaurantId = in.readString();
        title = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        price = in.readString();
        cookingTime = in.readString();
        byte tmpIsAvaible = in.readByte();
        isAvaible = tmpIsAvaible == 0 ? null : tmpIsAvaible == 1;
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public Boolean getAvaible() {
        return isAvaible;
    }

    public void setAvaible(Boolean avaible) {
        isAvaible = avaible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(restaurantId);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
        parcel.writeString(price);
        parcel.writeString(cookingTime);
        parcel.writeByte((byte) (isAvaible == null ? 0 : isAvaible ? 1 : 2));
    }
}
