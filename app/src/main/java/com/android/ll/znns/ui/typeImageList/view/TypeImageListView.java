package com.android.ll.znns.ui.typeImageList.view;

import com.android.ll.znns.domain.TypeImageDomain;

import java.util.List;

public interface TypeImageListView {
    void showLaoding();

    void hideLoading();

    void showLoadFaild(Exception e);

    void receiveImageList(List<TypeImageDomain> typeImageDomains);

}
