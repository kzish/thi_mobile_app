package zw.co.qbit.thi_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.util.List;

import adapters.tasksAdapter;
import customDialogs.CustomErrorDialog;
import customDialogs.CustomInfoDialog;
import customDialogs.CustomProgressDialogOne;
import daoModels.tbl_task_modelDao;
import daoModels.tbl_user_modelDao;
import database.AndroidDatabaseManager;
import gridsheetfragment.worksheetFragment;
import models.tbl_task_model;
import models.tbl_user_model;
import syncPackages.syncFetchTasks;


public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
    public static NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtBranch;
    private Toolbar toolbar;
    public static tasksAdapter _tasksAdapter;
    public static int navItemIndex = 0;
    public static BootstrapEditText txt_search;
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    SearchView searchView;

    static GridView gridView;
    static List<tbl_task_model> tasks;
    public static tbl_user_model user;
    static boolean synced = false;

    public static CustomProgressDialogOne pd;
    public static CustomErrorDialog ed;
    public static CustomInfoDialog id;
    public static Activity activity;

    static TableRow tblrow1;
    static TableRow tblrow2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        pd = new CustomProgressDialogOne();
        id = new CustomInfoDialog();
        ed = new CustomErrorDialog();

        activity =  this;

        //ka1();
        gridView = (GridView) findViewById(R.id.gridview);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_search = (BootstrapEditText) findViewById(R.id.txt_search);
        tblrow1 =(TableRow)findViewById(R.id.tblrow1);
        tblrow2 =(TableRow)findViewById(R.id.tblrow2);


        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadAllTasks();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //setSupportActionBar(toolbar);
        mHandler = new Handler();
        activity = this;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);
        navigationView.setClickable(true);
        navigationView.bringToFront();//this is what make the side bar usabel

        //navigationView
        //fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.txtname);
        txtBranch = (TextView) navHeader.findViewById(R.id.txtbranch);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        loadNavHeader();
        setUpNavigationView();
        if (savedInstanceState == null) {
            loadHomeFragment();
        }


        setMenuItems();

    }


    public static void setStats() {

        tbl_user_model user = thi.daoSession.getTbl_user_modelDao().loadAll().get(0);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_name).setTitle(user.username);
        int total_docs = 0;
        int total_docs_synced = 0;
        int total_docs_un_synced = 0;

       /*tbl_user_modelDao userdao = thi.daoSession.getTbl_user_modelDao();
        tbl_user_model user = userdao.queryBuilder().where(tbl_user_modelDao.Properties.IsLoggedin.eq(true)).unique();


        tbl_qualDao dao =  thi.daoSession.getTbl_qualDao();
        List<tbl_qual> docsList = dao.queryBuilder().
                where(
                        tbl_qualDao.Properties.Branch_id.eq(user.branch_id),
                        tbl_qualDao.Properties.User_id.eq(user.user_id)
                ).list();

        total_docs  = docsList.size()-1;

        for (tbl_qual obj:  docsList) {
            if(!isSynced(obj.android_client_id))
            {
                total_docs_un_synced++;
            }
            else
            {
                total_docs_synced++;
            }
        }

        if(total_docs==-1)total_docs=0;

        if(total_docs_un_synced==-1)total_docs_un_synced=0;

        total_docs_synced=(total_docs-total_docs_un_synced);

        if(total_docs_synced==-1)total_docs_synced=0;

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_synced).setTitle(total_docs_synced + " synced");
        menu.findItem(R.id.nav_un_synced).setTitle(total_docs_un_synced + " not synced");
        menu.findItem(R.id.nav_total).setTitle(total_docs + " total documents");


        int numItemsToFetch=0;
        numItemsToFetch  = thi.daoSession.getTbl_items_to_fetch_from_transferDao().loadAll().size();

        TextView txt_num_items_to_fetch;
        txt_num_items_to_fetch=(TextView) MenuItemCompat.getActionView(navigationView.getMenu().
                findItem(R.id.nav_pull_my_transferred_clients));


        txt_num_items_to_fetch.setGravity(Gravity.CENTER_VERTICAL);
        txt_num_items_to_fetch.setTypeface(null, Typeface.BOLD);
        txt_num_items_to_fetch.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        txt_num_items_to_fetch.setText(numItemsToFetch+"");
        */

    }


    void setMenuItems() {
        //set the menu items
        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {


                    new AlertDialog.Builder(activity)
                            .setTitle("Confirmation")
                            .setMessage("Do you really want to logout?")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    tbl_user_modelDao dao = thi.daoSession.getTbl_user_modelDao();
                                    List<tbl_user_model> users = dao.loadAll();
                                    for (tbl_user_model u : users) {
                                        dao.delete(u);
                                    }
                                    activity.startActivity(new Intent(activity, SplashActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    //activity.startActivity(new Intent(activity, SplashActivity.class));
                                    activity.finish();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                    // startActivity(new Intent(HomeActivity.this, AndroidDatabaseManager.class));
                    return true;
                }
            });


            menu.findItem(R.id.nav_download_tasks).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    pd.showCustomDialog(HomeActivity.this, "fetching tasks...");
                    syncFetchTasks.sync();
                    return true;
                }
            });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        loadAllTasks();
        setUpNavigationView();
        setStats();

    }

    //lod all of my taskss
    public static void loadAllTasks() {

        String search_param = txt_search.getText().toString();
        tbl_task_modelDao tdao = thi.daoSession.getTbl_task_modelDao();
        if (!search_param.equals("")) {
            tasks = tdao.queryBuilder().whereOr(
                    tbl_task_modelDao.Properties.Grower_name.like(search_param),
                    tbl_task_modelDao.Properties.Grower_number.like(search_param)
            ).orderAsc(tbl_task_modelDao.Properties.Grower_name).list();
        } else {
            tasks = tdao.queryBuilder().orderAsc().orderAsc(tbl_task_modelDao.Properties.Grower_name).list();
        }


        if(tasks.size()>0)
        {
            tblrow1.setVisibility(View.INVISIBLE);
            tblrow2.setVisibility(View.INVISIBLE);
        }
        else
        {
            tblrow1.setVisibility(View.VISIBLE);
            tblrow2.setVisibility(View.VISIBLE);
        }
        _tasksAdapter = new tasksAdapter(activity, tasks);
        gridView.setAdapter(_tasksAdapter);
        _tasksAdapter.notifyDataSetChanged();
        setStats();
    }

    private void loadNavHeader() {


    }


    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        drawer.closeDrawers();

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                //fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
         /*   case 0:
                tasksFragment tFragment          = new tasksFragment();
                return tFragment;
            case 1:
                worksheetFragment pFragment          = new worksheetFragment();
                return pFragment;
            case 2:
                AttachPhotosFragment at  = new AttachPhotosFragment();
                return at;
            case 3:
                MarkLandsFragment lf  = new MarkLandsFragment();
                return lf;
            case 4:
                AboutFragment af  = new AboutFragment();
                return af;
                */
            default:
                return new worksheetFragment();

        }
    }

    private void setToolbarTitle() {

        try {

            getSupportActionBar().setTitle("");
        } catch (Exception ex) {
            //Log.e("main title2",ex.getMessage()+"kz");
        }


    }


    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                loadHomeFragment();
                return;
            }
        }
        super.onBackPressed();
//        if (!searchView.isIconified()) {
//            searchView.setIconified(true);
//        } else {
//
//        }
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {


                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;

            }
        });


    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.e("saint", "saint");

        return false;
    }


}
