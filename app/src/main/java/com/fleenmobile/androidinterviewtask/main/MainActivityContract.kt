package com.fleenmobile.androidinterviewtask.main

/**
 * @author FleenMobile at 2018-02-24
 */
interface MainActivityContract {

    interface View {
        fun showResponseCode(code: String)
        fun showCounter(count: Int)
        fun showError(message: String)
        fun showButton()
        fun hideButton()
        fun showProgress()
        fun hideProgress()
    }

    interface Presenter<in V : View> {
        fun attachView(view: V)
        fun detachView()
        fun clear()
        fun onFetchContentClicked()
    }
}