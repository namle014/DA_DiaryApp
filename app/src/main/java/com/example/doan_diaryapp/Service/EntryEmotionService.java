package com.example.doan_diaryapp.Service;

import android.content.Context;
import android.database.Cursor;

import com.example.doan_diaryapp.Models.Emotion;
import com.example.doan_diaryapp.Models.EntryActivity;
import com.example.doan_diaryapp.Models.EntryEmotion;

import java.util.ArrayList;
import java.util.List;

public class EntryEmotionService extends BaseService {
    public EntryEmotionService(Context context) {
        super(context);
    }

    public EntryEmotion FindByEntryIdAndEmotionId(Class<EntryEmotion> clazz, int entryId, int emotionId) {
        db = this.getReadableDatabase();
        String selection = "EntryId=? AND EmotionId=?";
        String[] selectionArgs = new String[]{String.valueOf(entryId), String.valueOf(emotionId)};
        Cursor cursor = db.query(clazz.getSimpleName(), null, selection, selectionArgs, null, null, null);
        EntryEmotion object = null;
        if (cursor != null && cursor.moveToFirst()) {
            object = CreateModelObjectFromCursor(clazz, cursor);
            cursor.close();
        }
        return object;
    }
    public List<Emotion> getEmotionIdByMonthYear(int month, int year){
        db = this.getReadableDatabase();
        List<Emotion> entryList = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM EntryEmotion inner join Entry on EntryEmotion.EntryId = Entry.Id inner join Emotion on EntryEmotion.EmotionId = Emotion.Id", null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int dateColumnIndex = cursor.getColumnIndex("Date");
                int iconColumnIndex = cursor.getColumnIndex("Icon");
                int descEnColumnIndex = cursor.getColumnIndex("DescEn");
                int descViColumnIndex = cursor.getColumnIndex("DescVi");

                do {
                    String date = cursor.getString(dateColumnIndex).trim();
                    String[] parts = date.split("-");
                    int m = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    String icon = cursor.getString(iconColumnIndex);
                    String descEn = cursor.getString(descEnColumnIndex);
                    String descVi = cursor.getString(descViColumnIndex);
                    if(m == month && y == year){
                        entryList.add(new Emotion(icon, descEn, descVi));
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return entryList;
    }

    public List<Emotion> getEmotionIdByYear(int year){
        db = this.getReadableDatabase();
        List<Emotion> entryList = new ArrayList<>();
        try (Cursor cursor = db.rawQuery("SELECT * FROM EntryEmotion inner join Entry on EntryEmotion.EntryId = Entry.Id inner join Emotion on EntryEmotion.EmotionId = Emotion.Id", null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int dateColumnIndex = cursor.getColumnIndex("Date");
                int iconColumnIndex = cursor.getColumnIndex("Icon");
                int descEnColumnIndex = cursor.getColumnIndex("DescEn");
                int descViColumnIndex = cursor.getColumnIndex("DescVi");

                do {
                    String date = cursor.getString(dateColumnIndex).trim();
                    String[] parts = date.split("-");
                    int y = Integer.parseInt(parts[2]);
                    String icon = cursor.getString(iconColumnIndex);
                    String descEn = cursor.getString(descEnColumnIndex);
                    String descVi = cursor.getString(descViColumnIndex);
                    if(y == year){
                        entryList.add(new Emotion(icon, descEn, descVi));
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return entryList;
    }
}
