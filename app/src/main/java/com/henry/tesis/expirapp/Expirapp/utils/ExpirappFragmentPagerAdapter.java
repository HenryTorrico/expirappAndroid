package com.henry.tesis.expirapp.Expirapp.utils;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.henry.tesis.expirapp.Expirapp.activity.ProductFragment;
import com.henry.tesis.expirapp.Expirapp.activity.ProxToExpireFragment;

public class ExpirappFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Lista de Productos", "Proximos a Caducar"};
    private Context context;

    public ExpirappFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("position"+position);
        switch (position) {
            case 0:
                return ProductFragment.newInstance(position);
            case 1:
                return ProxToExpireFragment.newInstance(position);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}