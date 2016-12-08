package com.zrj.dllo.meetyou.find.listfind;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;
import com.zrj.dllo.meetyou.myinterface.EndlessRecyclerOnScrollListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemDislikeClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemImgClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemLikeClickListener;
import com.zrj.dllo.meetyou.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 */

public class ListFindFragment extends AbsBaseFragment implements Contract.View {
    private RecyclerView mRecyclerView;
    private Contract.Presenter mPresenter;
    private ListFindAdapter mListFindAdapter;//Adapter
    private final static int DISTANCE = 2000;//单位米
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public static ListFindFragment newInstance() {

        Bundle args = new Bundle();

        ListFindFragment fragment = new ListFindFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fra_list_find;
    }

    @Override
    protected void initView() {
        mRecyclerView = bindView(R.id.fra_list_find_recyclerview);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
    }

    @Override
    protected void initDatas() {
        mListFindAdapter = new ListFindAdapter();
        mRecyclerView.setAdapter(mListFindAdapter);

        //初始化完成,开始请求网络数据
        mPresenter.getListFindData(getActivity(), DISTANCE);
        initHead();

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mStaggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int endPosition) {
                simulateLoadMoreData(endPosition);
            }
        });

        mListFindAdapter.setLikeClickListener(new RecyclerViewItemLikeClickListener() {
            @Override
            public void onItemLike(View view, int position, Person person) {
                Toast.makeText(context, "fff点击了喜欢", Toast.LENGTH_SHORT).show();
                //1.向本地数据库中数据存储(存储自己的喜欢列表)
                mPresenter.setLikePersonInLocalP(person);
                //2.向对方这个人发送好友申请
                mPresenter.sendGoodFriendsRequestP(person);
                mListFindAdapter.delectFromPos(position);
            }
        });
        mListFindAdapter.setDislikeClickListener(new RecyclerViewItemDislikeClickListener() {
            @Override
            public void onItemDislike(View view, int position, Person person) {
                Toast.makeText(context, "点击了不喜欢", Toast.LENGTH_SHORT).show();
                mListFindAdapter.delectFromPos(position);
            }
        });
        mListFindAdapter.setImgClickListener(new RecyclerViewItemImgClickListener() {
            @Override
            public void onItemImg(View view, int position, Person person) {
                Intent intent = new Intent(context, ListTTActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", person);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

    }

    /**
     * 实现加载更多数据
     */
    private void simulateLoadMoreData(final int endPosition) {

        Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        List<Person> data = mPresenter.loadMoreData(context, DISTANCE);
                        mListFindAdapter.insertData(data, endPosition);
                        Toast.makeText(context, "Load Finished!", Toast.LENGTH_SHORT).show();
                        return null;
                    }
                }).subscribe();
    }


    private void initHead() {
        List<String> imgUrls = new ArrayList<>();
        imgUrls.add("http://images2.china.com/game/zh_cn/picnews/11128819/20140314/18394327_20140314115011898593008.jpg");
        imgUrls.add("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
        imgUrls.add("http://image92.360doc.com/DownloadImg/2016/01/0121/63840877_39.jpg");

        View itemView = LayoutInflater.from(context).inflate(R.layout.item_list_find_header, null);
        Banner banner = (Banner) itemView.findViewById(R.id.item_list_find_banner);
        banner.setImages(imgUrls);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setBannerAnimation(Transformer.DepthPage);
        banner.setDelayTime(1500);
        banner.isAutoPlay(true);
        banner.setMinimumHeight(200);
        //banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        mListFindAdapter.addHeaderView(itemView);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setDataFromNet(List<Person> listFindBean) {
        mListFindAdapter.setData(listFindBean);
    }
}
