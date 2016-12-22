package com.android.ll.znns.ui.typeImageList.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.ll.znns.R;
import com.android.ll.znns.adapter.SpacesItemDecoration;
import com.android.ll.znns.adapter.ViewHolder;
import com.android.ll.znns.adapter.recyclerview.CommonAdapter;
import com.android.ll.znns.adapter.recyclerview.PullAdapter;
import com.android.ll.znns.domain.TypeImageDomain;
import com.android.ll.znns.ui.ImageDetialActivity;
import com.android.ll.znns.ui.typeImageList.persenter.TypeImageListPersenter;
import com.android.ll.znns.ui.typeImageList.persenter.TypeImageListPersenterImpl;
import com.android.ll.znns.ui.typeImageList.view.TypeImageListView;

import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class TypeImageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, TypeImageListView {
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private TypeImageListPersenter mPersenter;
    private StaggeredGridLayoutManager mLayoutManager;
//    private CommonAdapter mAdapter;
    private CommonAdapter mAdapter;
    private String mLinkUrl;
    private String mTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_image);
        initView();
        initDate();
        setListener();

    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.sw_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.receiverview);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

    }

    private void initDate() {
        mLinkUrl = getIntent().getStringExtra("linkUrl");
        if (mLinkUrl.isEmpty()) {
            Toast.makeText(this, "加载图片列表失败", Toast.LENGTH_SHORT).show();
            return;
        }

        mTitle = getIntent().getStringExtra("title");
        //设置ToolBar tit了 必须放在    getSupportActionBar().setDisplayHomeAsUpEnabled(true);之前 不然没有效果
        if (!mTitle.isEmpty()) {

            mToolbar.setTitle(mTitle);
        }


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPersenter = new TypeImageListPersenterImpl(this);
        mPersenter.startGetImageList(mLinkUrl);
    }

    private void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onRefresh() {

        mPersenter.startGetImageList(mLinkUrl);
    }

    @Override
    public void showLaoding() {
        mSwipeRefreshLayout.measure(View.MEASURED_SIZE_MASK, View.MEASURED_HEIGHT_STATE_SHIFT);
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFaild(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveImageList(final List<TypeImageDomain> typeImageDomains) {
        if (mAdapter == null) {
//            mAdapter = new CommonAdapter<TypeImageDomain>(TypeImageActivity.this, R.layout.view_item_type_image,
//                    typeImageDomains) {
//                @Override
//                public void convert(ViewHolder holder, final TypeImageDomain typeImageDomain) {
//                    holder.setImageWithUrlAndSize(R.id.imv_second_item, typeImageDomain.getUrl(), typeImageDomain.getWidth(), typeImageDomain.getHeight());
//                    holder.setOnClickListener(R.id.imv_second_item, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(TypeImageActivity.this, ImageDetialActivity.class);
////                            intent.putParcelableArrayListExtra("imagelist", (ArrayList) typeImageDomains);
//                            intent.putExtra("imageurl", typeImageDomain.getFullSizeUrl());
//                            ActivityOptionsCompat options =
//                                    ActivityOptionsCompat.makeSceneTransitionAnimation(TypeImageActivity.this);
//                            ActivityCompat.startActivity(TypeImageActivity.this, intent, options.toBundle());
//                        }
//                    });
//                }
//            };
            mAdapter = new CommonAdapter<TypeImageDomain>(TypeImageActivity.this, R.layout.view_item_type_image, typeImageDomains) {
                @Override
                public void convert(ViewHolder holder, final TypeImageDomain typeImageDomain) {
//                    holder.setImageWithUrlAndSize(R.id.imv_second_item, typeImageDomain.getUrl(), typeImageDomain.getWidth(), typeImageDomain.getHeight());
                    holder.setImageWithUrl(R.id.imv_second_item, typeImageDomain.getUrl());
                    holder.setOnClickListener(R.id.imv_second_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TypeImageActivity.this, ImageDetialActivity.class);
//                            intent.putParcelableArrayListExtra("imagelist", (ArrayList) typeImageDomains);
                            intent.putExtra("imageurl", typeImageDomain.getFullSizeUrl());
                            ActivityOptionsCompat options =
                                    ActivityOptionsCompat.makeSceneTransitionAnimation(TypeImageActivity.this);
                            ActivityCompat.startActivity(TypeImageActivity.this, intent, options.toBundle());
                        }
                    });
                }
            };
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
            //设置item之间的间隔
            SpacesItemDecoration decoration = new SpacesItemDecoration(5);
            mRecyclerView.addItemDecoration(decoration);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            mAdapter.notifyDataSetChanged();

        }
    }
}
