package com.example.fragmentstatepagerissueexample.app;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    private boolean fragmentIsSwitched;
    private ExamplePagerAdapter mPagerAdapter;
    private ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            fragmentIsSwitched = savedInstanceState.getBoolean("fragmentIsSwitched");
        } else {
            fragmentIsSwitched = false;
        }

        mPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("fragmentIsSwitched", fragmentIsSwitched);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            fragmentIsSwitched = !fragmentIsSwitched;
            mPager.setCurrentItem(0);
            mPagerAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class ExamplePagerAdapter extends FragmentStatePagerAdapter {

        public ExamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            PlaceholderFragment fragment = null;
            switch(i) {
                case 0:
                    fragment = PlaceholderFragment.newInstance("Fragment One", Color.RED);
                    break;
                case 1:
                    fragment = PlaceholderFragment.newInstance("Fragment Two", Color.YELLOW);
                    break;
                case 2:
                    if (fragmentIsSwitched) {
                        fragment = PlaceholderFragment.newInstance("Fragment Four", Color.BLUE);
                    } else {
                        fragment =  PlaceholderFragment.newInstance("Fragment Three", Color.GREEN);
                    }
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public static PlaceholderFragment newInstance(String label, int color) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString("label", label);
            args.putInt("color", color);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            rootView.setBackgroundColor(getArguments().getInt("color"));
            TextView label = (TextView)rootView.findViewById(R.id.text_label);
            if (savedInstanceState == null) {
                label.setText(getArguments().getString("label"));
            } else {
                label.setText(savedInstanceState.getString("savedStateLabel"));
            }
            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("savedStateLabel", getArguments().getString("label"));
        }
    }

}
