package tn.esprit.restaurantcommand.data.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class PreferencesUtils {

   public PreferencesUtils(){

   }

   public static boolean save(String value,String key, Context context)
   {
       android.content.SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
       android.content.SharedPreferences.Editor prefsEditor=prefs.edit();
       prefsEditor.putString(value,key);
       prefsEditor.apply();
       return true;
   }
   public static String getSaved(Context context,String toSave)
   {
       android.content.SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(context);
       return prefs.getString(toSave,null);
   }
   public static void remove(String key, Context context)
   {
       android.content.SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(context);
       android.content.SharedPreferences.Editor prefsEditor=prefs.edit();
       prefsEditor.remove(key);
       prefsEditor.apply();
   }


}
