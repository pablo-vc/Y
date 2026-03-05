package com.example.y.ui.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.y.R;
import com.example.y.adapters.FeedPagerAdapter;
import com.example.y.databinding.FragmentFeedBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class FeedFragment extends Fragment {

    FragmentFeedBinding binding;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFeedBinding.inflate(getLayoutInflater());
        initUI();
        return binding.getRoot();
    }

    private void initUI() {
        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;
        FeedPagerAdapter adapter = new FeedPagerAdapter(this);

        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Global");
            } else {
                tab.setText("Siguiendo");
            }
        }).attach();
    }
}