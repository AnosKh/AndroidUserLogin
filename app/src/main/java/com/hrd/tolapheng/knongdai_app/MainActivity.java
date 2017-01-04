package com.hrd.tolapheng.knongdai_app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hrd.tolapheng.knongdai_app.fragment.FragmentDrawer;
import com.hrd.tolapheng.knongdai_app.fragment.nham.FragmentNham;
import com.hrd.tolapheng.knongdai_app.fragment.nham.FragmentToshTenh;
import com.hrd.tolapheng.knongdai_app.util.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout mDrawerLayout;
    private View containerView;


    Button nav_drawer_btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpage);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(viewPager);

        nav_drawer_btn_close = (Button) findViewById(R.id.nav_drawer_btn_close);
        nav_drawer_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext() , "HHHH" , Toast.LENGTH_SHORT);
            }
        });

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        Button nav_drawer_btn_close = (Button) findViewById(R.id.nav_drawer_btn_close);
        nav_drawer_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                Toast.makeText( getApplicationContext() , "CLOSE" , Toast.LENGTH_SHORT).show();
            }
        });



        Session.readUserSession(getApplicationContext());
        Log.d("readUserSession" , Session.isLogin + " " + Session.username);

        if(Session.isLogin == false){
            TextView btnLogin = (TextView) findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this , LoginActivity.class));
                }
            });
        }else{
            TextView btnLogout = (TextView) findViewById(R.id.btnLogout);
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Session.clearSession();
                    startActivity(new Intent(MainActivity.this , MainActivity.class));
                }
            });
        }




        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = getApplicationContext().getPackageName();
            //Retriving package info
            packageInfo = getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.d("Package Name=", getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.d("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.d("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.d("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                Toast.makeText(getApplicationContext() , query , Toast.LENGTH_LONG ).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext() , newText , Toast.LENGTH_LONG ).show();
                return false;
            }


        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        if(position == 3){
            Session.clearSession();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentToshTenh(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentNham(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentNham(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentNham(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentNham(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
        adapter.addFragment(new FragmentNham(), "ONE");
        adapter.addFragment(new FragmentNham(), "TWO");
        adapter.addFragment(new FragmentNham(), "THREE");
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

}
