package com.chengsheng.cala.htcm.views.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chengsheng.cala.htcm.R;

import java.util.List;
import java.util.Map;

public class AffirmAppointmentExamPersonAdapter extends RecyclerView.Adapter<AffirmAppointmentExamPersonAdapter.ExamPersonAdapter> {

    private Context context;
    private List<Map<String,String>> datas;

    public AffirmAppointmentExamPersonAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ExamPersonAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ExamPersonAdapter holder = new ExamPersonAdapter(LayoutInflater.from(context).inflate(R.layout.exam_person_item_layout,null));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExamPersonAdapter viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class ExamPersonAdapter extends RecyclerView.ViewHolder{

        TextView examPersonName;
        TextView examPersonMark;
        ImageView authenticationMarkA;
        TextView authenticationTextA;
        TextView examPersonID;
        TextView examPersonTel;
        LinearLayout checkExamDate;

        public ExamPersonAdapter(@NonNull View itemView) {
            super(itemView);

            examPersonName = itemView.findViewById(R.id.exam_person_name);
            examPersonMark = itemView.findViewById(R.id.exam_person_mark);
            authenticationMarkA = itemView.findViewById(R.id.authentication_mark_a);
            authenticationTextA = itemView.findViewById(R.id.authentication_text_a);
            examPersonID = itemView.findViewById(R.id.exam_person_id);
            examPersonTel = itemView.findViewById(R.id.exam_person_tel);
            checkExamDate = itemView.findViewById(R.id.check_exam_date);
        }
    }
}
