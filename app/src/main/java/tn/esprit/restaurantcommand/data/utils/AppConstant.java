package tn.esprit.restaurantcommand.data.utils;

import java.util.regex.Pattern;

public interface AppConstant {
    public final String IMAGE_URL="imageURL";
    public final String TOKEN="token";
    public final String UserID="userId";
    public final int IMAGE_REQ=1;
    public final  String CLOUD_NAME="dss4xd1fl";
    public final String API_KEY="342446947666699";
    public final String API_SECRET="ZX9piGGGyDBR9MuxSD_nYzc7A7g";
    public final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public final String KEY_PASSWORD="password";
    public final String KEY_EMAIL="email";
}
