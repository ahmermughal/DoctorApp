package com.idevelopstudio.doctorapp.authDoctorDetails;

import android.app.Dialog;
import android.os.Bundle;

import androidx.core.app.DialogCompat;
import androidx.fragment.app.Fragment;

import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.DialogCountryListBinding;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorDetailsBinding;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthUserDetailsBinding;
import com.idevelopstudio.doctorapp.utils.Helper;

import java.util.ArrayList;
import java.util.Locale;

import timber.log.Timber;


public class AuthDoctorDetailsFragment extends Fragment {

    private FragmentAuthDoctorDetailsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthDoctorDetailsBinding.inflate(getLayoutInflater());
        ArrayList<String> countires = Helper.getCountries();

        binding.buttonCountry.setOnClickListener(v -> setupAndShowCountryDialog(countires));


        return binding.getRoot();
    }


    private void setupAndShowCountryDialog(ArrayList<String> countries){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogCountryListBinding dialogBinding = DialogCountryListBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries);

        dialogBinding.listViewCountries.setAdapter(adapter);
        dialogBinding.listViewCountries.setTextFilterEnabled(true);
        dialogBinding.listViewCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                binding.buttonCountry.setText(((TextView)view).getText().toString());
                binding.buttonCountry.setTextColor(getResources().getColor(android.R.color.primary_text_light));
                if(((TextView)view).getText().toString().equals("Pakistan")){
                    showPMDCTextField();
                    binding.buttonUploadIdCard.setVisibility(View.GONE);
                }else{
                    hidePMDCTextField();
                    binding.buttonUploadIdCard.setVisibility(View.VISIBLE);
                }
                dialog.dismiss();
            }
        });
        dialogBinding.countrySearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void showPMDCTextField(){
        binding.pmdcEditText.setVisibility(View.VISIBLE);
        binding.textViewPmdc.setVisibility(View.VISIBLE);
    }

    private void hidePMDCTextField(){
        binding.pmdcEditText.setVisibility(View.GONE);
        binding.textViewPmdc.setVisibility(View.GONE);
    }


}