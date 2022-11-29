package zw.co.qbit.thi_app;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import gridsheetfragment.tab1Fragment;
import gridsheetfragment.tab2Fragment;
import gridsheetfragment.tab3Fragment;
import gridsheetfragment.tab4Fragment;
import gridsheetfragment.worksheetFragment;



public class TabActivity extends AppCompatActivity  {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String grower_id;//grab the grower id and pass it into the fragments
    String land_id;//grab the land id and pass it into the fragments
    String tag="TabActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        grower_id = getIntent().getStringExtra("grower_id");
        land_id = getIntent().getStringExtra("land_id");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

        private void setupViewPager(ViewPager viewPager) {


            worksheetFragment wsf =  new worksheetFragment();
            tab1Fragment t1f =  new tab1Fragment();
            tab2Fragment t2f =  new tab2Fragment();
            tab3Fragment t3f =  new tab3Fragment();
            tab4Fragment t4f =  new tab4Fragment();





            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(wsf, "worksheet");
            adapter.addFragment(t1f, "batch 1");
            adapter.addFragment(t2f, "batch 2");
            adapter.addFragment(t3f, "batch 3");
            adapter.addFragment(t4f, "batch 4");

            viewPager.setClipToPadding(false);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setPageMargin(0);
            viewPager.setOffscreenPageLimit(adapter.getCount());
            viewPager.setAdapter(adapter);


            viewPager.setAdapter(adapter);
        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }

        public void OnFragmentInteractionListener()
        {
            //
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

