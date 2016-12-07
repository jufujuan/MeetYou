package com.zrj.dllo.meetyou.find.listfind.chen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zrj.dllo.meetyou.Person;
import com.zrj.dllo.meetyou.R;
import com.zrj.dllo.meetyou.base.AbsBaseFragment;
import com.zrj.dllo.meetyou.find.listfind.ListFindBean;
import com.zrj.dllo.meetyou.find.listfind.ListFindPresenter;
import com.zrj.dllo.meetyou.find.listfind.SpaceItemDecoration;
import com.zrj.dllo.meetyou.find.ttfind.ListTTActivity;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemDislikeClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemImgClickListener;
import com.zrj.dllo.meetyou.myinterface.RecyclerViewItemLikeClickListener;
import com.zrj.dllo.meetyou.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * If there is no bug, then it is created by ChenFengYao on 2016/12/6,
 * otherwise, I do not know who create it either.
 */

public class ListFindFragment extends AbsBaseFragment implements Contract.View {
    private RecyclerView mRecyclerView;
    private Contract.Presenter mPresenter;
    private ListFindAdapter mListFindAdapter;//Adapter
    private final static int DISTANCE = 2000;//单位米

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
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    protected void initDatas() {
        mListFindAdapter = new ListFindAdapter();
        mRecyclerView.setAdapter(mListFindAdapter);

        //初始化完成,开始请求网络数据
        mPresenter.getListFindData(getActivity(),DISTANCE);
        initHead();

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
                Intent intent=new Intent(context, ListTTActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("person",person);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }

    private void initHead(){
        List<String> imgUrls=new ArrayList<>();
        imgUrls.add("http://images2.china.com/game/zh_cn/picnews/11128819/20140314/18394327_20140314115011898593008.jpg");
        imgUrls.add("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
        imgUrls.add("http://image92.360doc.com/DownloadImg/2016/01/0121/63840877_39.jpg");

        View itemView= LayoutInflater.from(context).inflate(R.layout.item_list_find_header,null);
        Banner banner= (Banner) itemView.findViewById(R.id.item_list_find_banner);
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
