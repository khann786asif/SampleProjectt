package com.example.sampleprojectt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private List<User> users = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User currentUser = users.get(position);

        byte[] bitmapData = currentUser.getImage(); // let this be your byte array
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapData , 0, bitmapData.length);

        String[] date = currentUser.getDateOfBirth().split("/");

        int currentYear = Year.now().getValue();
        int birthYear = Integer.parseInt(date[2]);
        int age = currentYear - birthYear;

        String fullName = currentUser.getFirstName()+" "+currentUser.getLastName();
        String detail = currentUser.getGender() + " | "+age+" | "+currentUser.getCountry();

        holder.textViewName.setText(fullName);
        holder.textViewDetail.setText(detail);
        holder.imageViewProfile.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void submitList(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewProfile, imageViewDelete;
        private TextView textViewName, textViewDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfile = itemView.findViewById(R.id.image_view_profile);
            imageViewDelete = itemView.findViewById(R.id.image_view_delete);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDetail = itemView.findViewById(R.id.text_view_detail);

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null){
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(users.get(position));
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onDeleteClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener  = listener;
    }
}
