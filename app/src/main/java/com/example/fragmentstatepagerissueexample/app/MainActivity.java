package com.example.fragmentstatepagerissueexample.app;

import android.graphics.Color;
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

        mPager = (ViewPager)findViewById(R.id.pager);
        mPagerAdapter = new ExamplePagerAdapter(getSupportFragmentManager());

        if (savedInstanceState != null) {
            fragmentIsSwitched = savedInstanceState.getBoolean("fragmentIsSwitched");
        } else {
            fragmentIsSwitched = false;
        }

        updateAdapterData();
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            fragmentIsSwitched = !fragmentIsSwitched;
            updateAdapterData();
            // Force our view pager back to index 0 in order to detach the index 2 fragment and save its state.
            mPager.setCurrentItem(0);
            mPagerAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateAdapterData() {
        if (fragmentIsSwitched) {
            mPagerAdapter.labels[2] = "4";
            mPagerAdapter.colors[2] = Color.BLUE;
        } else {
            mPagerAdapter.labels[2] = "3";
            mPagerAdapter.colors[2] = Color.GREEN;
        }
    }

    // Uncomment these to try the fixed adapter!
//    public class ExamplePagerAdapter extends FixedFragmentStatePagerAdapter {
    public class ExamplePagerAdapter extends FragmentStatePagerAdapter {

        String[] labels = new String[] {
                "1",
                "2",
                "3"
        };

        int[] colors = new int[] {
                Color.RED,
                Color.YELLOW,
                Color.GREEN
        };

        public ExamplePagerAdapter(FragmentManager fm) {
            super(fm);
        }
        // Uncomment these to try the fixed adapter!
//        @Override
//        public String getTag(int position) {
//            return labels[position];
//        }

        @Override
        public Fragment getItem(int i) {
            return PlaceholderFragment.newInstance(labels[i], colors[i]);
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
            Bundle args = getArguments();

            // Background color always comes from arguments.
            rootView.setBackgroundColor(args.getInt("color"));

            // If we have a saved state, we pull our label from that. Otherwise it comes from args.
            TextView label = (TextView)rootView.findViewById(R.id.text_label);
            if (savedInstanceState == null) {
                label.setText(args.getString("label"));
            } else {
                label.setText(savedInstanceState.getString("savedStateLabel"));
            }

            // And our "arguments label" shows what this fragment *should* be showing.
            TextView argsLabel = (TextView)rootView.findViewById(R.id.args_label);
            argsLabel.setText(getResources().getString(R.string.should_be_showing, args.getString("label")));

            return rootView;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("savedStateLabel", getArguments().getString("label"));
        }
    }

}
