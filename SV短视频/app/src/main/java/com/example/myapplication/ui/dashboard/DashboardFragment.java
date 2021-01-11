package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmcy.medialib.utils.MediaSelector;
import com.example.myapplication.R;
import com.example.myapplication.adapt.ImgRecyclerviewAdapter;
import com.example.myapplication.adapt.MediaPickAdapter;
import com.example.myapplication.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DashboardFragment extends Fragment {

    private MediaPickAdapter imageAdapter;
    private MediaPickAdapter videoAdapter;
    private final List<MessageBean> txList = new ArrayList<>();//一个全局的链表

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_message, container, false);
        initTxs();//下面的初始化方法
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyceler);//找到RecyclerView控件
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());//布局管理器
        recyclerView.setLayoutManager(layoutManager);
        ImgRecyclerviewAdapter adapter = new ImgRecyclerviewAdapter(txList);//适配器对象
        recyclerView.setAdapter(adapter);//设置适配器为上面的对象
        return root;

    }
    private void initTxs() {
        //初始化方法，为了能够创造出具体的子项
        for (int x = 0; x < 12; x++) {
            //嫌少？多来几次循环
          //  Random hh = new Random();
          //  int f = hh.nextInt(4) + 51;
            MessageBean a = new MessageBean( "http://192.144.212.162:8081/UploadVideo/2mrks3iz.jpeg" );
            txList.add(a);//加入到链表

        }
    }
}
