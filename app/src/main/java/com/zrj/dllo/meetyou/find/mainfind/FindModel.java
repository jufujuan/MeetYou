package com.zrj.dllo.meetyou.find.mainfind;

/**
 * 这是 鞠福娟 创建的哟~
 * on 16/11/23.
 */

public class FindModel implements FindContract.Model {
    private FindContract.Presenter mPresenter;
    /**
     * 把Presenter层放到Model层
     *
     * @param presenter
     */
    @Override
    public void setPresenter(FindContract.Presenter presenter) {
        mPresenter=presenter;
    }
}
