package com.example.kursovya.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kursovya.R;
import com.example.kursovya.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Pet pet);
    }

    private final List<Pet> items = new ArrayList<>();
    private final OnItemClickListener listener;

    public PetAdapter(List<Pet> list, OnItemClickListener listener) {
        if (list != null) items.addAll(list);
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPet;
        TextView tvName, tvSub;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPet = itemView.findViewById(R.id.imgPet);
            tvName = itemView.findViewById(R.id.tvName);
            tvSub = itemView.findViewById(R.id.tvSub);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pet_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = items.get(position);

        holder.tvName.setText(pet.getName());
        holder.tvSub.setText(pet.getType() + " • " + pet.getAge());

        String uri = pet.getPhotoUri();

        if (uri != null && !uri.isEmpty()) {
            Glide.with(holder.imgPet.getContext())
                    .load(Uri.parse(uri))
                    .placeholder(R.drawable.ic_pet_placeholder)
                    .error(R.drawable.ic_pet_placeholder)
                    .into(holder.imgPet);
        } else {
            holder.imgPet.setImageResource(R.drawable.ic_pet_placeholder);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(pet);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void update(List<Pet> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public List<Pet> getItems() {
        return new ArrayList<>(items);
    }

    public void updateList(List<Pet> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }


}
