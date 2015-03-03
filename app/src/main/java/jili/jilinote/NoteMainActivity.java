package jili.jilinote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jili.jilinote.adapter.NoteAdapter;
import jili.jilinote.entity.AppInfo;
import jili.jilinote.util.UploadHelper;

public class NoteMainActivity extends BaseActivity {
    private List<AppInfo> applicationList = new ArrayList<AppInfo>();

    //    private ApplicationAdapter mAdapter;
    private NoteAdapter mAdapter;
    private ImageButton mFabButton;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    private GridView mGotegridView;

    private static UploadHelper.UploadComponentInfoTask uploadComponentInfoTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_note_main);

        // Set explode animation when enter and exit the activity
        //Utils.configureWindowEnterExitTransition(getWindow());

        // Handle Toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Handle DrawerLayout
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Handle ActionBarDrawerToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();

        // Handle different Drawer States :D
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        // Handle DrawerList
        LinearLayout mDrawerList = (LinearLayout) findViewById(R.id.drawerList);

        // Handle ProgressBar
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Init DrawerElems NOTE Just don't do this in a live app :D
        final SharedPreferences pref = getSharedPreferences("com.mikepenz.applicationreader", 0);
        ((Switch) mDrawerList.findViewById(R.id.drawer_autoupload)).setChecked(pref.getBoolean("autouploadenabled", false));
        ((Switch) mDrawerList.findViewById(R.id.drawer_autoupload)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("autouploadenabled", isChecked);
                editor.apply();
            }
        });

        mDrawerList.findViewById(R.id.drawer_opensource).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Libs.Builder()
                        .withFields(R.string.class.getFields())
                        .withVersionShown(true)
                        .withLicenseShown(true)
                        .withActivityTitle(getString(R.string.drawer_opensource))
                        .withActivityTheme(R.style.AboutTheme)
                        .start(NoteMainActivity.this);
            }
        });
        ((ImageView) mDrawerList.findViewById(R.id.drawer_opensource_icon)).setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_github).colorRes(R.color.secondary).actionBarSize());

        // Fab Button
        mFabButton = (ImageButton) findViewById(R.id.fab_button);
        mFabButton.setImageDrawable(new IconicsDrawable(this, FontAwesome.Icon.faw_upload).color(Color.WHITE).actionBarSize());
        mFabButton.setOnClickListener(fabClickListener);
        Utils.configureFab(mFabButton);

//        mRecyclerView = (RecyclerView) findViewById(R.id.list);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setItemAnimator(new CustomItemAnimator());
//        mRecyclerView.setItemAnimator(new ReboundItemAnimator());
//
////        mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);
//        ArrayList<NoteInfo> noteInfos = new ArrayList<NoteInfo>();
//
//
//        mAdapter = new NoteAdapter(noteInfos, R.layout.row_application, MainActivity.this);
//
//        mRecyclerView.setAdapter(mAdapter);
//
//        Log.v("cacacaca",mAdapter.getItemCount()+"");
//
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.theme_accent));
//        mSwipeRefreshLayout.setRefreshing(true);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new InitializeApplicationsTask().execute();
//            }
//        });
//
//        new InitializeApplicationsTask().execute();
//
//        if (savedInstanceState != null) {
//            if (uploadComponentInfoTask != null) {
//                if (uploadComponentInfoTask.isRunning) {
//                    uploadComponentInfoTask.showProgress(this);
//                }
//            }
//        }
//
//        //show progress
//        mRecyclerView.setVisibility(View.GONE);
//        mProgressBar.setVisibility(View.VISIBLE);

        mGotegridView = (GridView) findViewById(R.id.gridView);
        mGotegridView.setAdapter(new GridViewAdapter());
        mGotegridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = (String) view.getTag();
                DetailActivity.launch(NoteMainActivity.this, view.findViewById(R.id.image), url);
            }
        });


        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_note_main;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            uploadComponentInfoTask = UploadHelper.getInstance(MainActivity.this, applicationList).uploadAll();
        }
    };


    public void animateActivity(AppInfo appInfo, View appIcon) {
        Intent i = new Intent(this, AppDetailActivity.class);
        i.putExtra("appInfo", appInfo.getComponentName());

        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create((View) mFabButton, "fab"), Pair.create(appIcon, "appIcon"));
        startActivity(i, transitionActivityOptions.toBundle());
    }


    private static class GridViewAdapter extends BaseAdapter {

        @Override public int getCount() {
            return 10;
        }

        @Override public Object getItem(int i) {
            return "Item " + String.valueOf(i + 1);
        }

        @Override public long getItemId(int i) {
            return i;
        }

        @Override public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.grid_item, viewGroup, false);
            }

            String imageUrl = "http://lorempixel.com/800/600/sports/" + String.valueOf(i + 1);
            view.setTag(imageUrl);

            ImageView image = (ImageView) view.findViewById(R.id.image);
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(image);

            TextView text = (TextView) view.findViewById(R.id.text);
            text.setText(getItem(i).toString());

            return view;
        }
    }


//    private class InitializeApplicationsTask extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected void onPreExecute() {
//            mAdapter.clearApplications();
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            applicationList.clear();
//
//            //Query the applications
//            final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//
//            List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
//            for (ResolveInfo ri : ril) {
//                applicationList.add(new AppInfo(MainActivity.this, ri));
//            }
//            Collections.sort(applicationList);
//
//            for (AppInfo appInfo : applicationList) {
//                //load icons before shown. so the list is smoother
//                appInfo.getIcon();
//            }
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//            //handle visibility
//            mRecyclerView.setVisibility(View.VISIBLE);
//            mProgressBar.setVisibility(View.GONE);
//            ArrayList<NoteInfo> noteInfos = new ArrayList<NoteInfo>();
//            for(int i =0;i<5;i++){
//                NoteInfo n = new NoteInfo();
//                n.setName("Note "+i);
//                noteInfos.add(n);
//            }
//
//            //set data for list
////            mAdapter.addApplications(applicationList);
//            mAdapter.addApplications(noteInfos);
//            mSwipeRefreshLayout.setRefreshing(false);
//
//            super.onPostExecute(result);
//        }
//    }
}
