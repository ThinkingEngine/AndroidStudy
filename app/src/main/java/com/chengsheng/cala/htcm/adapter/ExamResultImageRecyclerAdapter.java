package com.chengsheng.cala.htcm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.widget.ShowExamImageDialog;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class ExamResultImageRecyclerAdapter extends RecyclerView.Adapter<ExamResultImageRecyclerAdapter.ImageViewHolder> {
    private Context context;
    private String[] images;

    public ExamResultImageRecyclerAdapter(Context context, String[] strings) {
        this.context = context;
        this.images = strings;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ImageViewHolder vh = new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.single_image_layout, null));
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int i) {

        if (images.length > 0) {
            viewHolder.examResultImage.setImageURI(images[i]);
            final List<String> image = new ArrayList<>();
            for (int j = 0; j < images.length; j++) {
                image.add(images[j]);
            }
            viewHolder.examResultImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ShowExamImageDialog(context, image).show();
                }
            });
        }else{
            Toast.makeText(context,"测试状态暂无数据",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView examResultImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            examResultImage = itemView.findViewById(R.id.exam_result_image);
        }
    }
}
