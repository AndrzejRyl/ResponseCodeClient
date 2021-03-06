package com.fleenmobile.androidinterviewtask.main

import com.fleenmobile.androidinterviewtask.R
import com.fleenmobile.androidinterviewtask.api.ApiService
import com.fleenmobile.androidinterviewtask.injection.ResourceProvider
import com.fleenmobile.androidinterviewtask.util.LocalRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author FleenMobile at 2018-02-24
 */
class MainActivityPresenter(
        private val resourceProvider: ResourceProvider,
        private val apiService: ApiService,
        private val compositeDisposable: CompositeDisposable,
        private val localRepository: LocalRepository
) : MainActivityContract.Presenter<MainActivityContract.View> {

    private var view: MainActivityContract.View? = null

    override fun attachView(view: MainActivityContract.View) {
        this.view = view

        view.showResponseCode(localRepository.responseCode)
        view.showCounter(localRepository.fetchCount)
    }

    override fun detachView() {
        this.view = null
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun onFetchContentClicked() {
        view?.hideButton()
        view?.showProgress()

        compositeDisposable.add(
                apiService
                        .nextPath()
                        .switchMap { apiService.responseCode(it.path) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnTerminate {
                            view?.hideProgress()
                            view?.showButton()
                        }
                        .subscribe(
                                { onNextResponseCode(it.responseCode) },
                                { onError() }
                        )
        )
    }

    private fun onNextResponseCode(code: String) {
        localRepository.fetchCount++
        localRepository.responseCode = code

        view?.showResponseCode(code)
        view?.showCounter(localRepository.fetchCount)
    }

    private fun onError() {
        val message = resourceProvider.getString(R.string.error_message)
        view?.showError(message)
    }
}