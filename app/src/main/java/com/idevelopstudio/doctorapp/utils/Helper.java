package com.idevelopstudio.doctorapp.utils;

import com.idevelopstudio.doctorapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class Helper {

    public static ArrayList<String> getCountries(){
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        return countries;
    }

    public static int getSelectedBgByColor(int i){

        switch (i){
            case R.color.pastelBlue:
                return R.drawable.card_pastel_blue_selected;
            case R.color.pastelGreen:
                return R.drawable.card_pastel_green_selected;
            case R.color.pastelPink:
                return R.drawable.card_pastel_pink_selected;
            case R.color.pastelPurple:
                return R.drawable.card_pastel_purple_selected;
            case R.color.pastelSoftPink:
                return R.drawable.card_pastel_soft_pink_selected;
            case R.color.pastelYellow:
                return R.drawable.card_pastel_yellow_selected;
            case R.color.pastelBabyBlue:
                return R.drawable.card_pastel_baby_blue_selected;
            case R.color.pastelLightPurple:
                return R.drawable.card_pastel_light_purple_selected;
            case R.color.pastelMaroon:
                return R.drawable.card_pastel_maroon_selected;
            default:
                return R.drawable.card_pastel_yellow_selected;
        }
    }
}
