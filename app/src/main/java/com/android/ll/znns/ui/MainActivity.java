package com.android.ll.znns.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.ll.znns.R;
import com.android.ll.znns.ui.mainImageList.widget.fragment.DMFragment;
import com.android.ll.znns.ui.mainImageList.widget.fragment.GamesFragment;
import com.android.ll.znns.ui.mainImageList.widget.fragment.QTFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment mCurrentFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_games);
//        changeFragment(new DMFragment());
        changeFragment(new GamesFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_games:
                changeFragment(new GamesFragment());
                break;
            case R.id.nav_dm:
                changeFragment(new DMFragment());
                break;
            case R.id.nav_qt:
                changeFragment(new QTFragment());
                break;
            case R.id.nav_share:
                new MaterialDialog.Builder(MainActivity.this)
                        .title("关于应用")
                        .icon(getResources().getDrawable(R.drawable.ic_about))
                        .positiveText("确定")
                        .content(R.string.about_app)
                        .show();
                break;
//            case R.id.nav_personal:
//                new MaterialDialog.Builder(MainActivity.this)
//                        .title("关于作者")
//                        .icon(getResources().getDrawable(R.drawable.ic_personal))
//                        .positiveText("确定")
//                        .content(R.string.about_account)
//                        .show();
//                break;
            case R.id.nav_join_us:
                joinQQGroup("WP2QK9cpRqOc9WxNETv5lUC5axNYbQgy");
                break;
        }
        mToolbar.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /****************
     *
     * 发起添加群流程。群号：二次元(464192221) 的 key 为： WP2QK9cpRqOc9WxNETv5lUC5axNYbQgy
     * 调用 joinQQGroup(WP2QK9cpRqOc9WxNETv5lUC5axNYbQgy) 即可发起手Q客户端申请加群 二次元(464192221)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            Toast.makeText(MainActivity.this, "未安装手Q或安装的版本不支持" ,Toast.LENGTH_LONG).show();
            return false;
        }
    }




    // change fragment
    private void changeFragment(Fragment fragment) {
        if (mCurrentFragment == null || !fragment.getClass().getName().equals(mCurrentFragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragent_content, fragment).commit();
            mCurrentFragment = fragment;
        }


    }
}
