package com.fleenmobile.androidinterviewtask.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.fleenmobile.androidinterviewtask.R
import com.fleenmobile.androidinterviewtask.injection.ResourceProvider
import com.fleenmobile.androidinterviewtask.injection.RetrofitProvider
import com.fleenmobile.androidinterviewtask.showToast
import com.fleenmobile.androidinterviewtask.util.LocalRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    @BindView(R.id.response_code_value)
    lateinit var responseCodeTextView: TextView

    @BindView(R.id.fetch_counter_value)
    lateinit var counterTextView: TextView

    private lateinit var presenter: MainActivityContract.Presenter<MainActivityContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        inject()
        presenter.attachView(this)
    }

    private fun inject() {
        val resourceProvider = ResourceProvider(this)
        val apiService = RetrofitProvider().provideApiService()
        val localRepository = LocalRepositoryImpl(this)

        presenter = MainActivityPresenter(
                resourceProvider,
                apiService,
                CompositeDisposable(),
                localRepository
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.clear()
    }

    override fun showResponseCode(code: String) {
        responseCodeTextView.text = code
    }

    override fun showCounter(count: Int) {
        counterTextView.text = "$count"
    }

    override fun showError(message: String) {
        showToast(message, Toast.LENGTH_LONG)
    }

    @OnClick(R.id.fetch_content)
    fun onFetchContent() {
        presenter.onFetchContentClicked()
    }
}
