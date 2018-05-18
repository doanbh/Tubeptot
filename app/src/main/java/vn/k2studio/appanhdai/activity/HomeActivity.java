package vn.k2studio.appanhdai.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.Utils.ConfigJson;
import vn.k2studio.appanhdai.Utils.Constant;
import vn.k2studio.appanhdai.Utils.SharedPrefs;
import vn.k2studio.appanhdai.adapter.ViewPagerAdapter;
import vn.k2studio.appanhdai.fragment.CreateNewFragment;
import vn.k2studio.appanhdai.fragment.ListNewsFragment;
import vn.k2studio.appanhdai.model.UserInfo;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private View mHeaderViewNav;
    private TextView txtHeaderAccount;
    private ImageView imgHeaderAcount;
    private UserInfo mUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initToolbar();
        setupDrawerLayout();
        mHeaderViewNav = navigationView.getHeaderView(0);
        txtHeaderAccount = mHeaderViewNav.findViewById(R.id.txt_nav_email);
        imgHeaderAcount = mHeaderViewNav.findViewById(R.id.img_avatar);
        ConfigJson configJson = new ConfigJson();
        mUserInfo =
                configJson.getUser(SharedPrefs.getInstance().get(Constant.USER_INFO, String.class));
        txtHeaderAccount.setText(mUserInfo.getName());
        drawerToggle = setupDrawerToggle();
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.nav_logout_right:
                SharedPrefs.getInstance().put(Constant.USER_INFO, null);
                Intent j = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(j);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(ListNewsFragment.newInstance(), "Danh sách tin");
        adapter.addFrag(CreateNewFragment.newInstance(), "Đăng tin");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawerLayout() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        int id = menuItem.getItemId();

                        switch (id) {
                            case R.id.nav_home:
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                                finish();
                                break;
                            case R.id.nav_logout:
                                SharedPrefs.getInstance().put(Constant.USER_INFO, null);
                                Intent j =
                                        new Intent(getApplicationContext(), RegisterActivity.class);
                                startActivity(j);
                                finish();
                                break;
                        }

                        return true;
                    }
                });
    }
}
