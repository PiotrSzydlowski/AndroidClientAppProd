package szydlowskiptr.com.epz.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {

    public static final String MY_PREFERENCE_NAME = "preferences";
    public static final String USER_ID_PREF = "user_id";
    public static final String BASKET_TOTAL_PREF = "basket_total";
    public static final String CART_ITEM_PREF = "cartItem";
    public static final String PRODUCT_ID_PREF = "product_id";
    public static final String PRODUCT_BY_CAT_ID_PREF = "product_by_cat_id";
    public static final String MAG_ID_PREF = "mag_id";
    public static final String ITEM_TOTAL_PREF = "itemTotal";
    public static final String ADDRESS_DOOR_NUMBER_PREF = "address_door_number";
    public static final String POSTAL_CODE_PREF = "postal_code";
    public static final String CITY_PREF = "city";
    public static final String ADDRESS_STREET_PREF = "address_street";
    public static final String ADDRESS_STREET_NUMBER_PREF = "address_street_number";
    public static final String EMPTY_BASKET = "empty_basket";
    public static final String ACTIVE_ORDER = "active_order";
    public static final String USER_BANNED = "user_banned";
    public static final String OPEN_FROM = "open_from";
    public static final String OPEN_TO = "open_to";
    public static final String OPEN = "open";
    public static final String TEMP_OPEN = "temp_open";
    public static final String ORDER_MSG = "order_msg";

    public static void registerPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void registerPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
//        pref.registerOnSharedPreferenceChangeListener(listener);
    }

    public static void unregisterPref(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        pref.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public static String loadUserIdFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(USER_ID_PREF, null);
    }

    public static String loadEmptyBasketFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(EMPTY_BASKET, null);
    }

    public static String loadAddressDoorNumberFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ADDRESS_DOOR_NUMBER_PREF, null);
    }

    public static String loadPostalCodeFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(POSTAL_CODE_PREF, null);
    }

    public static String loadAddressStreetFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ADDRESS_STREET_PREF, null);
    }

    public static String loadAddressStreetNumberFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ADDRESS_STREET_NUMBER_PREF, null);
    }

    public static String loadCityFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(CITY_PREF, null);
    }

    public static String loadItemTotalFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ITEM_TOTAL_PREF, null);
    }

    public static String loadBasketTotalFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(BASKET_TOTAL_PREF, null);
    }

    public static String loadCartItemFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(CART_ITEM_PREF, null);
    }

    public static String loadMagIdFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(MAG_ID_PREF, null);
    }

    public static String loadProdIdFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(PRODUCT_ID_PREF, null);
    }

    public static String loadActiveOrderFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ACTIVE_ORDER, null);
    }

    public static String loadProdByCatIdFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(PRODUCT_BY_CAT_ID_PREF, null);
    }

    public static String loadIfUserBannedFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(USER_BANNED, null);
    }

    public static String loadOpenFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(OPEN, null);
    }

    public static String loadTempOpenFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(TEMP_OPEN, null);
    }

    public static String loadOpenFromFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(OPEN_FROM, null);
    }

    public static String loadTempOpenToFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(OPEN_TO, null);
    }

    public static String loadOrderMsgFromPref(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return pref.getString(ORDER_MSG, null);
    }

    public static void saveCartItemInPref(Context context, String cartItem) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(CART_ITEM_PREF, cartItem);
        editor.apply();
    }

    public static void saveIfUserBannedInPref(Context context, String userBanned) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(USER_BANNED, userBanned);
        editor.apply();
    }

    public static void saveIfOpenInPref(Context context, String open) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(OPEN, open);
        editor.apply();
    }

    public static void saveIfTempOpenInPref(Context context, String tempOpen) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(TEMP_OPEN, tempOpen);
        editor.apply();
    }

    public static void saveOpenFromInPref(Context context, String openFrom) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(OPEN_FROM, openFrom);
        editor.apply();
    }

    public static void saveOpenToInPref(Context context, String openTo) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(OPEN_TO, openTo);
        editor.apply();
    }

    public static void saveUserIdInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(USER_ID_PREF, value);
        editor.apply();
    }

    public static void saveEmptyBasketInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(EMPTY_BASKET, value);
        editor.apply();
    }

    public static void saveActiveOrderInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ACTIVE_ORDER, value);
        editor.apply();
    }

    public static void saveAddressDoorNumberInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ADDRESS_DOOR_NUMBER_PREF, value);
        editor.apply();
    }

    public static void savePostalCodeInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(POSTAL_CODE_PREF, value);
        editor.apply();
    }

    public static void saveCityInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(CITY_PREF, value);
        editor.apply();
    }

    public static void saveAddressStreetInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ADDRESS_STREET_PREF, value);
        editor.apply();
    }

    public static void saveAddressStreetNumberInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ADDRESS_STREET_NUMBER_PREF, value);
        editor.apply();
    }

    public static void saveMagIdInPref(Context context, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(MAG_ID_PREF, value);
        editor.apply();
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences pref = context.getSharedPreferences(MY_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        return editor;
    }

    public static void saveItemTotalInPref(Context context, String cartItem) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ITEM_TOTAL_PREF, cartItem);
        editor.apply();
    }

    public static void saveProdIdInPref(Context context, String prodId) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PRODUCT_ID_PREF, prodId);
        editor.apply();
    }

    public static void saveProdByCatIdInPref(Context context, String catId) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PRODUCT_BY_CAT_ID_PREF, catId);
        editor.apply();
    }

    public static void saveBasketTotalInPref(Context context, String total) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(BASKET_TOTAL_PREF, total);
        editor.apply();
    }

    public static void saveOrderMessageInPref(Context context, String message) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(ORDER_MSG, message);
        editor.apply();
    }
}
