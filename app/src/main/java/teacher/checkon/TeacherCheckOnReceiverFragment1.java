package teacher.checkon;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.zhangbolun.jinjidebishe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangbolun on 2017/4/21.
 */

public class TeacherCheckOnReceiverFragment1 extends Fragment {
    private String TAG="booleanZhang";
    private String date;
    
    @BindView(R.id.fragmentteacher_checkon_receiver1_dp)DatePicker datePicker;
    @BindView(R.id.fragmentteacher_checkon_receiver1_rv)RecyclerView recyclerView;
    @BindView(R.id.fragmentteacher_checkon_receiver1_btn)Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_teacher_checkon_receiver1,container,false);
        ButterKnife.bind(this,view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=Integer.toString(datePicker.getYear())+"-"+Integer.toString(datePicker.getMonth()+1)+"-"+Integer.toString(datePicker.getDayOfMonth());
                Log.d(TAG, date);
            }
        });
        
        
        return view;
    }
}
