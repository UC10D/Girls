package com.android.ll.znns.ui.mainImageList.model;

public interface ImageListModel {
    void GetImageList(String type, int page, ImageListModelImpl.GetImageListenter listener);
}
