package com.android.ll.znns.ui.mainImageList.view;

import com.android.ll.znns.domain.ImageListDomain;

import java.util.List;


public interface ImageListView {
    void showLaoding();

    void hideLoading();

    void showLoadFaild(Exception e);

    void receiveImageList(List<ImageListDomain> imageListDomains);

}
