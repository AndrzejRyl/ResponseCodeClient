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
    }

    override fun detachView() {
        this.view = null
    }

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun onFetchContentClicked() {
        compositeDisposable.add(
                apiService
                        .nextPath()
                        .switchMap { apiService.responseCode(it.path) }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { onNextResponseCode(it.responseCode) },
                                { onError(it) }
                        )
        )
    }

    private fun onNextResponseCode(code: String) {
        localRepository.fetchCount++
        localRepository.responseCode = code

        view?.showResponseCode(code)
        view?.showCounter(localRepository.fetchCount)
    }

    private fun onError(error: Throwable) {
        val message = resourceProvider.getString(R.string.error_message)
        view?.showError(message)
    }
}