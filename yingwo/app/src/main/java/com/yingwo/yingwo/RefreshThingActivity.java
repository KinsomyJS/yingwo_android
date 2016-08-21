package com.yingwo.yingwo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.yingwo.yingwo.Adapter.ChoosePhotoListAdapter;
import com.yingwo.yingwo.Listener.SoftKeyBoardListener;
import com.yingwo.yingwo.model.TokenEvent;
import com.yingwo.yingwo.utils.HttpUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.HorizontalListView;

/**
 * Created by FJS0420 on 2016/8/1.
 */

public class RefreshThingActivity extends AppCompatActivity{

    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    private final int REQUEST_CODE_CROP = 1002;
    private final int REQUEST_CODE_EDIT = 1003;

    private List<PhotoInfo> mPhotoList;

    private ChoosePhotoListAdapter mChoosePhotoListAdapter;

    @BindView(R.id.btn_choosepic)
    ImageView btn_choosepic;
    @BindView(R.id.btn_emoj)
    ImageView btn_emoj;
    @BindView(R.id.edit_content)
    EditText edit_content;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.lv_photo)
    HorizontalListView mLvPhoto;
    private Toolbar toolbar;
    private LinearLayout block_keyboardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refreshthing);
        //注册EventBus
        EventBus.getDefault().register(this);
        init();

    }

    @OnClick(R.id.btn_choosepic)
    public void choosepic(){
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY,8, mOnHanlderResultCallback);
    }
    private void init(){
        ButterKnife.bind(this);
        initImageLoader(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tv_title.setText("新鲜事");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        block_keyboardview = (LinearLayout) findViewById(R.id.block_keyboardview);
        mPhotoList = new ArrayList<>();
        mChoosePhotoListAdapter = new ChoosePhotoListAdapter(this, mPhotoList);
        mLvPhoto.setAdapter(mChoosePhotoListAdapter);
        //注册软键盘的监听
        SoftKeyBoardListener.setListener(this,
                new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
                    @Override
                    public void keyBoardShow(int height) {
                        block_keyboardview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void keyBoardHide(int height) {
                        block_keyboardview.setVisibility(View.GONE);

                    }
                });

    }

    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                mPhotoList.addAll(resultList);
                mChoosePhotoListAdapter.notifyDataSetChanged();
                Log.i("resultonHanlderSuccess",mPhotoList.size() + "");
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(RefreshThingActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_freshthing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_release) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String jsonstr = HttpUtil.getToken("http://yw.zhibaizhi.com/yingwophp/qiniu/getAT");
                        JSONObject jsonObject = new JSONObject(jsonstr);
                        String token  = (String) jsonObject.get("info");
                        Log.i("json",token);
                        Log.i("resultjson",mPhotoList.size() + "" + mPhotoList.get(0).getPhotoPath());
                        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
                        UploadManager uploadManager = new UploadManager();
                        String data = mPhotoList.get(0).getPhotoPath();
                        String key = null;
                        uploadManager.put(data, key, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        //res包含hash、key等信息，具体字段取决于上传策略的设置。
                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                        Log.i("qiniu", "\r\n " + info.isOK() );
                                    }
                                }, null);
//                        EventBus.getDefault().post(new TokenEvent(jsonstr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void TokenEventBus(TokenEvent event){
        Log.i("result",event.getMsg());
        Log.i("result",mPhotoList.size() + "");
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        UploadManager uploadManager = new UploadManager();
        String data = "kinsomy's test";
        String key = null;
        String token = event.getMsg();
        uploadManager.put(data, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置。
                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        Log.i("qiniu", "\r\n " + info.isOK() );
                    }
                }, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //反注册EventBus
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
