package com.optilink.android.boilerplate;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.optilink.android.boilerplate.flux.Dispatcher;
import com.optilink.android.boilerplate.flux.store.Store;
import com.optilink.android.boilerplate.flux.store.StoreImpl;
import com.optilink.android.boilerplate.retrofit.RestClient;
import com.optilink.android.boilerplate.util.DialogUtils;
import com.optilink.android.boilerplate.util.ThrowableUtil;
import com.squareup.otto.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final String DIALOG_TAG_LOADING_DATA = "DIALOG_TAG_LOADING_DATA";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.fab)
    FloatingActionButton mFab;

    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.nav_view)
    NavigationView mNavigationView;

    @Bind(R.id.content)
    TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        registerStore();

        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterStore();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        if (id == R.id.nav_login) {
            RestClient.getInstance().login("15818645501", "111111");
        } else if (id == R.id.nav_list) {
            RestClient.getInstance().todoList();
        } else if (id == R.id.nav_detail) {
            RestClient.getInstance().showWorkOrder(346);
        } else if (id == R.id.nav_upload) {
            RestClient.getInstance().uploadTaskAttachment(3277, Environment.getExternalStorageDirectory().getAbsolutePath() + "/1.jpg");
        } else if (id == R.id.nav_download) {
            RestClient.getInstance().downloadAttachment(
                    "http://youlian-production.oss-cn-shenzhen.aliyuncs.com/production/attachments/work_order_tasks/1176/work_118_task_1176_cargo.jpg");
        } else if (id == R.id.nav_share) {
            RestClient.getInstance().contributors("square", "retrofit");
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * subscribe otto bus event
     *
     * @param event
     */
    @Subscribe
    public void onStoreChangeEvent(Store.StoreChangeEvent event) {
        String type = event.action.getType();
        ArrayMap<String, Object> map = event.action.getData();

        switch (type) {
            case ActionConf.ACTION_SHOW_PROGRESS_DIALOG:
                DialogUtils.showProgressDialog(getSupportFragmentManager(), "正在加载数据...", DIALOG_TAG_LOADING_DATA);
                break;
            case ActionConf.ACTION_DISMISS_PROGRESS_DIALOG:
                DialogUtils.dismiss(getSupportFragmentManager(), DIALOG_TAG_LOADING_DATA);
                break;
            case ActionConf.ACTION_REPORT_ERROR:
                render(ThrowableUtil.printStackTrace((Throwable) map.get(ActionConf.ACTION_KEY_ONLY_ONE)));
                break;
            default:
                render(map.get(ActionConf.ACTION_KEY_ONLY_ONE));
                break;
        }
    }

    private void registerStore() {
        Dispatcher.getInstance().register(StoreImpl.getInstance().register(this));
    }

    private void unregisterStore() {
        Dispatcher.getInstance().unregister(StoreImpl.getInstance().unregister(this));
    }

    private void render(Object obj) {
        if (null != obj) {
            setContent(obj.toString());
        }
    }

    private void setContent(CharSequence content) {
        mContent.setText(content);
    }
}