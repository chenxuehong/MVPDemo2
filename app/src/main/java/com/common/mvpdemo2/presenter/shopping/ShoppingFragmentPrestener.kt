package com.common.mvpdemo2.presenter.shopping

import com.common.mvpdemo2.contract.IShoppingContact
import com.common.mvpdemo2.model.shopping.ShoppingModel
import com.jaydenxiao.common.presenter.BasePresenterKt

class ShoppingFragmentPrestener : BasePresenterKt<IShoppingContact.View, IShoppingContact.Model>(), IShoppingContact.Presenter {
    override fun createModel(): IShoppingContact.Model {
        return ShoppingModel()
    }

}
