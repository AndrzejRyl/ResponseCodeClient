package com.fleenmobile.androidinterviewtask.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.fleenmobile.androidinterviewtask.R
import com.fleenmobile.androidinterviewtask.showToast

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
