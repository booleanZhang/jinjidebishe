package otheruser.notification;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbolun on 2017/4/25.
 */

public class OtheruserNotificationReceiverFragment extends Fragment {
    @BindView(R.id.fragmentotheruser_notification_receiver_rv)RecyclerView recyclerView;
    @BindView(R.id.fragmentotheruser_notification_receiver_srl)SwipeRefreshLayout swipeRefreshLayout;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_otheruser_notification_receiver,container,false);
        ButterKnife.bind(this,view);


        return view;
    }
}
