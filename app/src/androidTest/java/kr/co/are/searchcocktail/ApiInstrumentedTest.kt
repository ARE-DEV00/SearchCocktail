package kr.co.are.searchcocktail

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import kr.co.are.searchcocktail.domain.model.ResultDomain
import kr.co.are.searchcocktail.domain.usecase.GetListCocktailFilterByAlcoholicUseCase
import kr.co.are.searchcocktail.domain.usecase.GetStreamTextByIdUseCase
import org.hamcrest.MatcherAssert
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class ApiInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var getStreamTextByIdUseCase: GetStreamTextByIdUseCase
    
    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testApiStreamTextGetData() = runTest {
        getStreamTextByIdUseCase()
            .catch {
                println("##### testApiServiceFetchData - catch")
                it.printStackTrace()
                fail("An error occurred: ${it.message}")
            }.collectLatest { result ->
                when(result){
                    is ResultDomain.Error -> {
                        fail("An error occurred: ${result.exception.message}")
                    }
                    ResultDomain.Loading -> {

                    }
                    is ResultDomain.Success -> {
                        MatcherAssert.assertThat(result, org.hamcrest.CoreMatchers.notNullValue())
                    }
                }
            }
    }


}