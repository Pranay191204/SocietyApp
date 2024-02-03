package com.database.society;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<Movie> list;

    public MovieAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie movie = list.get(position);

        holder.textTitle.setText(movie.getTitle());
        holder.textNotice_id.setText(String.valueOf(movie.getNotice_id()));
        holder.textValid_till.setText(movie.getValid_till());
        holder.textNotice_status.setText(movie.getNotice_status());
        holder.textDate.setText(movie.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTitle, textNotice_id, textValid_till,textNotice_status,textDate;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.main_title);
            textDate = itemView.findViewById(R.id.main_date);
            textValid_till = itemView.findViewById(R.id.main_valid_till);
            textNotice_status = itemView.findViewById(R.id.main_notice_status);
            textNotice_id = itemView.findViewById(R.id.main_notice_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(view.getContext(),Notice_content.class);
                    i.putExtra("notice_id",textNotice_id.getText().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(i);

                }
            });
        }
    }
}