package com.common.mvpdemo2.model.shopping

import com.common.mvpdemo2.contract.IShoppingContact
import io.reactivex.disposables.CompositeDisposable

class ShoppingModel : IShoppingContact.Model {

    override var mDisposablePool: CompositeDisposable = CompositeDisposable()

}
