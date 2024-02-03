package com.database.society;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PageAdapter  extends FragmentStatePagerAdapter {
    int numOfTab;
    public PageAdapter(FragmentManager fm, int numOfTab) {
        super(fm);
        this.numOfTab=numOfTab;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                OpenComplain tb1=new OpenComplain();
                return tb1;
            case 1:
                CloseComplain tb2=new CloseComplain();
                return tb2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTab;
    }
}
