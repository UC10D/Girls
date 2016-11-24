package com.android.ll.znns.ui.typeImageList.persenter;

import com.android.ll.znns.domain.TypeImageDomain;
import com.android.ll.znns.ui.typeImageList.model.TypeImageListModel;
import com.android.ll.znns.ui.typeImageList.model.TypeImageListModelImpl;
import com.android.ll.znns.ui.typeImageList.view.TypeImageListView;

import java.util.List;

public class TypeImageListPersenterImpl implements TypeImageListModelImpl.OnGetTypeImageListener, TypeImageListPersenter {
    private TypeImageListView mTypeImageListView;
    private TypeImageListModel mTypeImageListModel;

    public TypeImageListPersenterImpl(TypeImageListView typeImageListView) {
        mTypeImageListView = typeImageListView;
        mTypeImageListModel = new TypeImageListModelImpl();
    }

    @Override
    public void onSuccess(List<TypeImageDomain> typeImageDomains) {
        mTypeImageListView.hideLoading();
        mTypeImageListView.receiveImageList(typeImageDomains);
    }

    @Override
    public void OnError(Exception e) {
        mTypeImageListView.hideLoading();
    }


    @Override
    public void startGetImageList(String url) {
        mTypeImageListModel.getTypeImageList(url, this);
        mTypeImageListView.showLaoding();

    }
}
