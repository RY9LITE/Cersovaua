package com.example.kursovya.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.kursovya.R;
import com.example.kursovya.model.Pet;
import com.bumptech.glide.Glide;

import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Pet pet);
    }


    private List<Pet> items;
    private OnItemClickListener listener;

    public PetAdapter(List<Pet> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvName;
        TextView tvSub;
        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.imgPet);
            tvName = v.findViewById(R.id.tvName);
            tvSub = v.findViewById(R.id.tvSub);
        }
    }

    @Override
    public PetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pet pet = items.get(position);

        holder.tvName.setText(pet.getName());
        holder.tvSub.setText(pet.getType() + " • " + pet.getAge());

        Glide.with(holder.img.getContext())
                .load(pet.getPhotoUrl())
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(pet);
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public Pet removeAt(int position) {
        Pet p = items.remove(position);
        notifyItemRemoved(position);
        return p;
    }

    public void addAt(Pet pet, int position) {
        items.add(position, pet);
        notifyItemInserted(position);
    }

    public List<Pet> getItems() {
        return items;
    }
}

