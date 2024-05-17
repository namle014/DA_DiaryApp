package com.example.doan_diaryapp.ui.collection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doan_diaryapp.Models.EntryPhoto;
import com.example.doan_diaryapp.Models.ImportantDay;
import com.example.doan_diaryapp.R;
import com.example.doan_diaryapp.RecordActivity;
import com.example.doan_diaryapp.Service.EntryPhotoService;
import com.example.doan_diaryapp.Service.EntryService;
import com.example.doan_diaryapp.databinding.CarouselLayoutBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Locale;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ItemViewHolder> {

    private ArrayList<CarouselModel> list;
    private Context context;
    EntryPhotoService entryPhotoService;

    public CarouselAdapter(ArrayList<CarouselModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(CarouselLayoutBinding.inflate(LayoutInflater.from(context), parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        CarouselModel model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private CarouselLayoutBinding binding;

        public ItemViewHolder(CarouselLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            // Set OnClickListener for the ImageView
            binding.carouselImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the clicked item
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // Handle click event here
                        CarouselModel model = list.get(position);
                        showDaily(model.getImagePath());
                        Log.d("CarouselAdapter", "onClick: Image clicked at position " + position);
                    }
                }
            });
        }

        public void bind(CarouselModel model) {
            Glide.with(context)
                    .load(model.getImagePath())
                    .into(binding.carouselImageView);
        }
    }



    private void showDaily(String Photo) {
        Intent intent = new Intent(context, RecordActivity.class);
        entryPhotoService=new EntryPhotoService(context);
        String date = entryPhotoService.getDate(Photo);
        intent.putExtra("Date", date);
        context.startActivity(intent);
    }


}
