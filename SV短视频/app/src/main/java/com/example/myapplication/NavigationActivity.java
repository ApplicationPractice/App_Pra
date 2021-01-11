package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.adapt.ViewPagerAdapter;
import com.example.myapplication.model.VideoModel;
import com.example.myapplication.ui.dashboard.DashboardFragment;
import com.example.myapplication.ui.home.HomeFragment;
import com.example.myapplication.ui.image.ImageFragment;
import com.example.myapplication.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationActivity extends AppCompatActivity {

    private final ArrayList<Fragment> framents = new ArrayList<Fragment>();
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private int mCurrent = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);*/
        framents.add(new HomeFragment());
        framents.add(new DashboardFragment());
        framents.add(new NotificationsFragment());
        fragmentTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i < framents.size(); i++) {
            Fragment fragment = framents.get(i);
            fragmentTransaction.add(R.id.nav_host_fragment, fragment);
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        switchFragment(0);
       navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    @Override
    protected void onPause() {
        super.onPause();
        framents.get(0).onPause();

    }
        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                 //   BarUtils.setStatusBarLightMode((Activity) mContext, false);
                    switchFragment(0);
                    return true;
                case R.id.navigation_dashboard:
               //     BarUtils.setStatusBarLightMode((Activity) mContext, true);//改变状态栏上方的字体颜色
                   switchFragment(1);
                    framents.get(0).onPause();
                    return true;
                case R.id.navigation_notifications:
               //     BarUtils.setStatusBarLightMode((Activity) mContext, false);
                    switchFragment(2);
                   framents.get(0).onPause();
                    return true;
            }
            return false;
        }
    };
    private void switchFragment(int index) {
        if (index != mCurrent) {
            fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < framents.size(); i++) {
                Fragment fragment = framents.get(i);
                if (index == i) {
                    fragmentTransaction.show(fragment);
                }
                if (i == mCurrent) {
                    fragmentTransaction.hide(fragment);

                }
            }
            fragmentTransaction.commit();
            mCurrent = index;
        }
    }
}