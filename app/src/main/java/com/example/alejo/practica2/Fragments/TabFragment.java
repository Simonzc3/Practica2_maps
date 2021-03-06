package com.example.alejo.practica2.Fragments;

/**
 * Created by Usuario on 16/10/2017.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejo.practica2.R;


public class TabFragment extends Fragment {

    private String title;
    private String nameTab1;
    private String nameTab2;

    private String option;
    private int numberTabs = 0;

    private ListFragment[] zonesList;

    private ListFragment temp;
    private Bundle arg;

    public static TabLayout tabLayout;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager viewPager;
    public static int int_items = 3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //zonesList.add(new Zone4ListFragment());
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.view_pager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        /**
         * Read arguments to determine which zone has to be
         *  initiated
         */
        //option = getArguments().getString("option");
        numberTabs = 2;
        zonesList = new ToursFragment[numberTabs];
        for(int i = 0; i < numberTabs; i++){
            Bundle arg= new Bundle();
            arg.putInt("position", i);
            zonesList[i] = new ToursFragment();
            zonesList[i].setArguments(arg);
        }
        setStringNames("Tours", "Available tours", "Scheduled tours");

        return x;

    }


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            for(int i = 0; i < numberTabs; i++){
                if (i == position){
                    return zonesList[i];
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return nameTab1;
                case 1:
                    return nameTab2;
            }
            return null;
        }
    }

    /** Method to set names to all string variables **/
    private void setStringNames(String _title, String _nameTab1, String _nameTab2){
        title = _title;
        nameTab1 = _nameTab1;
        nameTab2 = _nameTab2;
    }
}
