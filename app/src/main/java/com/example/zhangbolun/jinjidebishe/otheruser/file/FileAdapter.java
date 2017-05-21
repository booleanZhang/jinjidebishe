package com.example.zhangbolun.jinjidebishe.otheruser.file;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;
import com.example.zhangbolun.jinjidebishe.fileOperation.HttpDownloader;

import java.util.List;

/**
 * Created by zhangbolun on 2017/5/19.
 */

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {
    private Context mContext;
    private List<FileItem> mFileItemList;
    private String TAG="文件下载";

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView name;
        TextView sender;
        TextView dateTime;
        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            name=(TextView) view.findViewById(R.id.file_otheruser_item_name);
            sender=(TextView) view.findViewById(R.id.file_otheruser_item_sender);
            dateTime=(TextView) view.findViewById(R.id.file_otheruser_item_datetime);
        }
    }

    public FileAdapter(List<FileItem> fileItemList){
        mFileItemList=fileItemList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.file_otheruser_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                final FileItem fileItem=mFileItemList.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpDownloader httpDownloader=new HttpDownloader();
                        httpDownloader.downfile(fileItem.getPosition(),"家校通下载的文件/",fileItem.getSender()+fileItem.getName());
                    }
                }).start();
                Toast.makeText(mContext, "已经将文件保存到:家校通下载的文件夹下。文件名为:"+fileItem.getId()+fileItem.getSender()+fileItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        FileItem fileItem=mFileItemList.get(position);
        holder.name.setText(fileItem.getName());
        holder.sender.setText(fileItem.getSender());
        holder.dateTime.setText(fileItem.getDate()+" "+fileItem.getTime());
    }

    public int getItemCount(){
        return mFileItemList.size();
    }
}
