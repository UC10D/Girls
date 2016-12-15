package com.android.ll.znns.ui.mainImageList.model;

import com.android.ll.znns.domain.Constant;
import com.android.ll.znns.domain.ImageListDomain;

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

public class ImageListModelImpl implements ImageListModel {

    @Override
    public void GetImageList(final String type, final int page, final GetImageListenter listener) {

        Observable<List<ImageListDomain>> observable = Observable.create(new Observable.OnSubscribe<List<ImageListDomain>>() {
            @Override
            public void call(Subscriber<? super List<ImageListDomain>> subscriber) {
                List<ImageListDomain> imageListDomainList = new ArrayList();
                try {
                    String url = null;
                    if(page == 1) {
                        url = Constant.BASE_URL + type;
                    } else {
                        url = Constant.BASE_URL.concat(type).concat(String.valueOf(page)).concat(".html");
                    }

                    Document document = Jsoup.connect(url).get();
                    Elements imageListElements = document.getElementsByClass("photo-list-padding");

                    for (Element imageListElement : imageListElements) {
                        //URL
                        Elements link = imageListElement.getElementsByClass("pic");
                        String linkUrl = Constant.BASE_HOST.concat(link.attr("href"));

                        //图片信息
                        Element image = imageListElement.select("img").first();
                        String imageUrl = image.attr("src");
                        String imageTitle = image.attr("title").trim();
                        imageListDomainList.add(new ImageListDomain(linkUrl, imageUrl, imageTitle));
                    }
                    subscriber.onNext(imageListDomainList);

                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });

        Subscriber<List<ImageListDomain>> subscriber = new Subscriber<List<ImageListDomain>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.OnError((Exception) e);
            }

            @Override
            public void onNext(List<ImageListDomain> imageListDomains) {
                listener.onSuccess(imageListDomains);
            }
        };

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }

    public interface GetImageListenter {
        void onSuccess(List<ImageListDomain> imageList);

        void OnError(Exception e);
    }
}
