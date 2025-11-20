package com.example.pawmatch.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pawmatch.R;
import com.example.pawmatch.model.Pet;

import java.util.List;

public class PetCardAdapter extends RecyclerView.Adapter<PetCardAdapter.VH> {
    private List<Pet> data;

    public PetCardAdapter(List<Pet> data){ this.data = data; }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(VH holder, int position){
        Pet p = data.get(position);
        holder.tvName.setText(p.getName() + ", " + p.getAge());
        holder.tvDetails.setText(p.getType() + " · " + p.getBreed());
        if(p.getPhotos()!=null && !p.getPhotos().isEmpty()){
            Glide.with(holder.itemView.getContext()).load(p.getPhotos().get(0)).into(holder.imgPet);
        } else {
            holder.imgPet.setImageResource(android.R.drawable.ic_menu_report_image);
        }
    }

    @Override public int getItemCount(){ return data.size(); }

    public Pet removeTop(){
        if(data.isEmpty()) return null;
        return data.remove(0);
    }

    public void setData(List<Pet> newData){
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView imgPet;
        TextView tvName, tvDetails;
        VH(View itemView){
            super(itemView);
            imgPet = itemView.findViewById(R.id.imgPet);
            tvName = itemView.findViewById(R.id.tvName);
            tvDetails = itemView.findViewById(R.id.tvDetails);
        }
    }
}
