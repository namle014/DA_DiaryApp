package com.example.doan_diaryapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan_diaryapp.Models.Language;
import com.example.doan_diaryapp.Service.LanguageService;

import java.util.ArrayList;

public class SettingActivity extends BaseActivity {

    Dialog dialog;
    TextView textViewSubTheme, textViewSubLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewSubTheme = findViewById(R.id.textviewSubTheme);
        textViewSubLanguage = findViewById(R.id.textviewSubLanguage);

        initLanguage();
        customDialog();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.setting_title));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void onLayoutThemeClick(View view) {
        dialog.show();
    }

    public void onLayoutLanguageClick(View view) {
        Intent intent = new Intent(this, ChangeLanguage.class);
        startActivity(intent);
        initLanguage();
    }

    void initLanguage() {
        LanguageService languageService = new LanguageService(this);
        Language language = languageService.FindById(Language.class, 1);
        if (language.getIsActive() == 1) {
            textViewSubLanguage.setText(getString(R.string.language_vi));
        }
        else {
            textViewSubLanguage.setText(getString(R.string.language_en));
        }
    }

    public void onLayoutFeedbackClick(View view) {
        Intent intent = new Intent(this, ContactActivity.class);
        startActivity(intent);
    }

    public void customDialog() {
        dialog = new Dialog(SettingActivity.this);
        dialog.setContentView(R.layout.dialog_display_mode);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_display_mode));
        dialog.setCancelable(true);

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        RadioButton rBtnLight = dialog.findViewById(R.id.rBtnLight);
        RadioButton rBtnDark = dialog.findViewById(R.id.rBtnDark);
        RadioButton rBtnSystem = dialog.findViewById(R.id.rBtnSystem);

        int displayMode = getSharedPreferences("MODE", Context.MODE_PRIVATE).getInt("displayMode", 2);
        if (displayMode == 0) {
            rBtnLight.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_light));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else if (displayMode == 1){
            rBtnDark.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_dark));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            rBtnSystem.setChecked(true);
            textViewSubTheme.setText(getString(R.string.display_mode_system));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
        rBtnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 0);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_light));
                dialog.dismiss();
            }
        });

        rBtnDark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                SharedPreferences.Editor editor = getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 1);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_dark));
                dialog.dismiss();
            }
        });

        rBtnSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                SharedPreferences.Editor editor = getSharedPreferences("MODE", Context.MODE_PRIVATE).edit();
                editor.putInt("displayMode", 2);
                editor.apply();
                textViewSubTheme.setText(getString(R.string.display_mode_system));
                dialog.dismiss();
            }
        });
    }
}