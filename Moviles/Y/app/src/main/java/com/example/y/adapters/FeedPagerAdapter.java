package com.example.y.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FeedPagerAdapter extends FragmentStateAdapter {

    public FeedPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new GlobalFeedFragment();
        } else {
            return new FollowingFeedFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
