package com.android.ll.znns.ui.typeImageList.model;

import android.text.TextUtils;

import com.android.ll.znns.domain.TypeImageDomain;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TypeImageListModelImpl implements TypeImageListModel {
    @Override
    public void getTypeImageList(final String url, final TypeImageListModelImpl.OnGetTypeImageListener litener) {


        Observable<List<TypeImageDomain>> observable = Observable.create(new Observable.OnSubscribe<List<TypeImageDomain>>() {
            @Override
            public void call(Subscriber<? super List<TypeImageDomain>> subscriber) {
                List<TypeImageDomain> typeImageDomains = new ArrayList();
                try {
                    Document document = Jsoup.connect(url).get();
                    Element element = document.getElementsByClass("photo-list-box").first();
//                    Logger.e(element.toString());
                    Elements elementsA = element.getElementsByTag("a");
                    Logger.e("Elements   " +  elementsA.toString());
                    for (Element a : elementsA) {
                        String linkUrl = a.attr("abs:href");
                        Logger.e("linkUrl   " +  linkUrl.toString());

                        Document big = Jsoup.connect(linkUrl).get();
                        Element bigE = big.getElementById("bigImg");
                        String bigSrc = bigE.attr("src");

                        Logger.e("bigSrc   " +  bigSrc.toString());

                        Elements img = a.getElementsByTag("img");
                        String src = img.attr("src");
                        if(TextUtils.isEmpty(src)){
                            src = img.attr("srcs");
                        }
                        String width = img.attr("width");
                        String height = img.attr("height");
                        if(TextUtils.isEmpty(src)){
                            break;
                        }
                        typeImageDomains.add(new TypeImageDomain(Integer.valueOf(width), Integer.valueOf(height), src, bigSrc));
                    }
                } catch (IOException e) {
                    subscriber.onError(e);
                }
                System.out.print(typeImageDomains.get(0).getHeight());
                subscriber.onNext(typeImageDomains);
            }
        });


        Subscriber<List<TypeImageDomain>> subscriber = new Subscriber<List<TypeImageDomain>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                litener.OnError((Exception) e);
            }

            @Override
            public void onNext(List<TypeImageDomain> typeImageDomains) {
                litener.onSuccess(typeImageDomains);

            }
        };


        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public interface OnGetTypeImageListener {
        void onSuccess(List<TypeImageDomain> imageDomainList);

        void OnError(Exception e);
    }
}
