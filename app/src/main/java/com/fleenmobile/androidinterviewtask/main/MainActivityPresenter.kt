package com.fleenmobile.androidinterviewtask.main

/**
 * @author FleenMobile at 2018-02-24
 */
class MainActivityPresenter : MainActivityContract.Presenter<MainActivityContract.View> {

    private var view: MainActivityContract.View? = null

    override fun attachView(view: MainActivityContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFetchContentClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}