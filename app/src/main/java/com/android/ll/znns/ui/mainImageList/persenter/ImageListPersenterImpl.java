package com.android.ll.znns.ui.mainImageList.persenter;

import com.android.ll.znns.domain.ImageListDomain;
import com.android.ll.znns.ui.mainImageList.model.ImageListModel;
import com.android.ll.znns.ui.mainImageList.model.ImageListModelImpl;
import com.android.ll.znns.ui.mainImageList.view.ImageListView;

import java.util.List;


public class ImageListPersenterImpl implements ImageListModelImpl.GetImageListenter, ImageListPersenter {
    private ImageListView imageListView;
    private ImageListModel imageListModel;

    public ImageListPersenterImpl(ImageListView imageListView) {
        this.imageListView = imageListView;
        this.imageListModel = new ImageListModelImpl();
    }


    @Override
    public void onSuccess(List<ImageListDomain> imageList) {
        imageListView.receiveImageList(imageList);
        imageListView.hideLoading();
    }

    @Override
    public void OnError(Exception e) {
        imageListView.showLoadFaild(e);
        imageListView.hideLoading();
    }

    @Override
    public void startGetImageList(String type, int page) {
        imageListView.showLaoding();
        imageListModel.GetImageList(type, page, this);
    }
}
