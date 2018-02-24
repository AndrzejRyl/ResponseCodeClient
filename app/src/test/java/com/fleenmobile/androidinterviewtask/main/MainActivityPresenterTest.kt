package com.fleenmobile.androidinterviewtask.main

import com.fleenmobile.androidinterviewtask.api.ApiService
import com.fleenmobile.androidinterviewtask.api.data.NextPathResponse
import com.fleenmobile.androidinterviewtask.api.data.ResponseCodeResponse
import com.fleenmobile.androidinterviewtask.injection.ResourceProvider
import com.fleenmobile.androidinterviewtask.util.LocalRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*

/**
 * @author FleenMobile at 2018-02-24
 */
class MainActivityPresenterTest : BaseTest() {

    @Mock
    private lateinit var view: MainActivityContract.View

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var compositeDisposable: CompositeDisposable

    private lateinit var presenter: MainActivityPresenter

    private val stubFetchCount = 1
    private val stubResponseCode = "code"
    private val stubString = "string"

    @Before
    override fun setup() {
        super.setup()

        trampolineRxPlugin()

        `when`(localRepository.fetchCount).thenReturn(stubFetchCount)
        `when`(localRepository.responseCode).thenReturn(stubResponseCode)
        `when`(resourceProvider.getString(anyInt())).thenReturn(stubString)

        presenter = MainActivityPresenter(
                resourceProvider,
                apiService,
                compositeDisposable,
                localRepository
        )

        presenter.attachView(view)

        verify(localRepository, times(1)).fetchCount
        verify(localRepository, times(1)).responseCode
        verify(view, times(1)).showCounter(stubFetchCount)
        verify(view, times(1)).showResponseCode(stubResponseCode)
    }

    @After
    override fun tearDown() {
        super.tearDown()

        verifyNoMoreInteractions(view, apiService, resourceProvider, localRepository, compositeDisposable)
    }


    @Test
    fun clear() {
        presenter.clear()

        verify(compositeDisposable, times(1)).clear()
    }

    @Test
    fun `should show response code if everything runs smoothly`() {
        mockSuccessfulNextPathRequest()
        mockSuccessfulResponseCodeRequest()

        presenter.onFetchContentClicked()

        commonOnFetchButtonClickedVerification()
        commonApiServiceUsageVerification()
        verify(localRepository, times(1)).fetchCount = stubFetchCount + 1
        verify(localRepository, times(1)).responseCode = stubResponseCode

        // First invocation is at the startup of the screen
        verify(view, times(2)).showCounter(stubFetchCount)
        verify(view, times(2)).showResponseCode(stubResponseCode)
        verify(localRepository, times(3)).fetchCount
    }

    @Test
    fun `should show error if next path request fails`() {
        mockFailedNextPathRequest()

        presenter.onFetchContentClicked()

        verify(apiService, times(1)).nextPath()
        commonOnFetchButtonClickedVerification()
        commonErrorVerification()
    }

    @Test
    fun `should show error if response code requst fails`() {
        mockSuccessfulNextPathRequest()
        mockFailedResponseCodeRequest()

        presenter.onFetchContentClicked()

        commonApiServiceUsageVerification()
        commonOnFetchButtonClickedVerification()
        commonErrorVerification()
    }

    private fun commonApiServiceUsageVerification() {
        verify(apiService, times(1)).nextPath()
        verify(apiService, times(1)).responseCode(anyString())
    }

    private fun commonOnFetchButtonClickedVerification() {
        verify(view, times(1)).showProgress()
        verify(view, times(1)).hideButton()
        verify(view, times(1)).hideProgress()
        verify(view, times(1)).showButton()
        verify(compositeDisposable, times(1)).add(any())
    }

    private fun commonErrorVerification() {
        verify(resourceProvider, times(1)).getString(anyInt())
        verify(view, times(1)).showError(stubString)
    }

    private fun mockSuccessfulNextPathRequest() {
        `when`(apiService.nextPath()).thenReturn(Observable.just(NextPathResponse(stubString)))
    }

    private fun mockSuccessfulResponseCodeRequest() {
        `when`(apiService.responseCode(anyString()))
                .thenReturn(Observable.just(ResponseCodeResponse(stubString, stubResponseCode)))
    }

    private fun mockFailedNextPathRequest() {
        `when`(apiService.nextPath()).thenReturn(Observable.error(IllegalStateException("Wrong IP!")))
    }

    private fun mockFailedResponseCodeRequest() {
        `when`(apiService.responseCode(anyString()))
                .thenReturn(Observable.error(IllegalStateException("Wrong path!")))

    }
}