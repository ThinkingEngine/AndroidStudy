package com.chengsheng.cala.htcm.module.activitys;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chengsheng.cala.htcm.base.BaseActivity;
import com.chengsheng.cala.htcm.constant.GlobalConstant;
import com.chengsheng.cala.htcm.R;
import com.chengsheng.cala.htcm.utils.PictureSelectorConfig;
import com.chengsheng.cala.htcm.widget.RatingBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class EstimateOrderActivity extends BaseActivity {

    private ImageView back;
    private TextView title;
    private RatingBar estimateStarNum;
    private GridView estimatePicSelector;

    private ArrayList<String> mPicList = new ArrayList<>();
    private PicGridView picGridView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_estimate_order;
    }

    @Override
    public void initView() {
        back = findViewById(R.id.title_header_estimate_order).findViewById(R.id.back_login);
        title = findViewById(R.id.title_header_estimate_order).findViewById(R.id.menu_bar_title);
        estimateStarNum = findViewById(R.id.estimate_star_num);
        estimatePicSelector = findViewById(R.id.estimate_pic_selector);

        title.setText("评价订单");
        estimateStarNum.setmOnStarChangeListener(new RatingBar.OnStarChangeListener() {
            @Override
            public void OnStarChanged(float selectedNumber, int position) {
                Toast.makeText(EstimateOrderActivity.this, "选中" + selectedNumber + "颗星", Toast.LENGTH_SHORT).show();
            }
        });

        picGridView = new PicGridView(mPicList);
        estimatePicSelector.setAdapter(picGridView);
        estimatePicSelector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if (mPicList.size() == GlobalConstant.MAX_SELECT_PIC_NUM) {
                        viewPluImg(position);
                    } else {
                        selectPic(GlobalConstant.MAX_SELECT_PIC_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    @Override
    public void getData() {

    }

    private void viewPluImg(int position) {
        Intent intent = new Intent(this, PlusImageActivity.class);
        intent.putStringArrayListExtra(GlobalConstant.IMG_LIST, mPicList);
        intent.putExtra(GlobalConstant.POSITION, position);
        startActivityForResult(intent, GlobalConstant.REQUEST_CODE_MAIN);
    }

    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    // 处理选择的照片的地址
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                picGridView.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == GlobalConstant.REQUEST_CODE_MAIN && resultCode == GlobalConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(GlobalConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            picGridView.notifyDataSetChanged();
        }
    }


    class PicGridView extends BaseAdapter {

        private List<String> mList;


        public PicGridView(List<String> mList) {
            this.mList = mList;

        }

        @Override
        public int getCount() {

            int count = mList == null ? 1 : mList.size() + 1;
            if (count > GlobalConstant.MAX_SELECT_PIC_NUM) {
                return mList.size();
            } else {
                return count;
            }
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(EstimateOrderActivity.this).inflate(R.layout.pic_selector_bg_layout, parent, false);
            ImageView iv = convertView.findViewById(R.id.can_upload_pic);
            if (position < mList.size()) {
                //代表+号之前的需要正常显示图片
                String picUrl = mList.get(position); //图片路径
                Log.e("TAG", picUrl);
                Glide.with(EstimateOrderActivity.this).load(picUrl).into(iv);
            } else {
                iv.setImageResource(R.mipmap.shoucang);//最后一个显示加号图片
            }
            return convertView;
        }
    }
}
