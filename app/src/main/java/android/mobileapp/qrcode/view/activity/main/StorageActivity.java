package android.mobileapp.qrcode.view.activity.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.mobileapp.qrcode.app.Application;
import android.mobileapp.qrcode.data.storage.entities.Folder;
import android.mobileapp.qrcode.di.module.main.StorageModule;
import android.mobileapp.qrcode.helper.Utils;
import android.mobileapp.qrcode.scan.R;
import android.mobileapp.qrcode.view.BaseActivity;
import android.mobileapp.qrcode.view.activity.main.adapter.StorageAdapter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

public class StorageActivity extends BaseActivity implements StorageAdapter.StorageInterface {

    private RecyclerView rcvFolder;
    private ConstraintLayout mainLayout;

    @Inject
    Context mContext;

    @Inject
    StorageAdapter mStorageAdapter;

    private ArrayList<Folder> arrFolder;

    HashMap<String, ArrayList<Folder>> hierarchyFolder = new HashMap<>();
    private int mIndex = 0;

    @Override
    public void distributedDaggerComponents() {
        Application.getInstance().getAppComponent().plus(new StorageModule(this)).inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_storage;
    }

    @Override
    protected void initAttributes() {
        rcvFolder = findViewById(R.id.rcvFolder);
        mainLayout = findViewById(R.id.mainLayout);
        initComponents();
    }

    @Override
    protected void initViews() {
        Intent intent = getIntent();
        arrFolder = intent.getParcelableArrayListExtra("arrFolder");
        hierarchyFolder.put(String.valueOf(mIndex), arrFolder);
        mStorageAdapter.updateFolder(arrFolder);
        mStorageAdapter.attachInterface(this);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.black));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setStatusBarGradient(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.custom_bg);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
            window.setBackgroundDrawable(background);
        }
    }

    private void initComponents() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rcvFolder.setLayoutManager(mLayoutManager);
        rcvFolder.setItemAnimator(new DefaultItemAnimator());
        rcvFolder.setAdapter(mStorageAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void subChildFolder(Folder folder) {
        File root = new File(folder.getPath());
        ArrayList<Folder> temps = new ArrayList<>();
        if (root.isDirectory()) {
            File[] files = root.listFiles();
            temps.clear();
            for (File f : files) {
                temps.add(new Folder(R.drawable.ic_folder, f.getName(), (f.isDirectory() ? "Directory" : "File"), f.getPath()));
            }
            mIndex++;
            hierarchyFolder.put(String.valueOf(mIndex), temps);
            mStorageAdapter.updateFolder(temps);
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("Path", folder.getPath());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mIndex == 0) {
            super.onBackPressed();
        } else {
            mIndex--;
            arrFolder = hierarchyFolder.get(String.valueOf(mIndex));
            mStorageAdapter.updateFolder(arrFolder);
        }
    }
}