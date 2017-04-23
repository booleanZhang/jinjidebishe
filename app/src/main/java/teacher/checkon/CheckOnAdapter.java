package teacher.checkon;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangbolun.jinjidebishe.R;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by zhangbolun on 2017/4/22.
 */

public class CheckOnAdapter extends RecyclerView.Adapter<CheckOnAdapter.ViewHolder> {
    private Context mContext;
    private List<CheckOn> mCheckOnList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView id;
        TextView name;

        public ViewHolder(View view){
            super(view);
            cardView=(CardView)view;
            id=(TextView)view.findViewById(R.id.checkon_item_id);
            name=(TextView)view.findViewById(R.id.checkon_item_name);
        }
    }

    public CheckOnAdapter(List<CheckOn> checkOnList){
        mCheckOnList= checkOnList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.checkon_item,parent,false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder,int position){
        CheckOn checkOn=mCheckOnList.get(position);
        holder.id.setText(checkOn.getId());
        holder.name.setText(checkOn.getName());
    }

    public int getItemCount(){
        return mCheckOnList.size();
    }
}
