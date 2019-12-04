package com.common.mvpdemo2.contract

import com.jaydenxiao.common.IListView
import com.jaydenxiao.common.IModel
import com.jaydenxiao.common.IPresenter

interface IShoppingContact {
    interface View : IListView<Presenter> {

    }

    interface Presenter : IPresenter<View, Model> {

    }

    interface Model : IModel {

    }
}
