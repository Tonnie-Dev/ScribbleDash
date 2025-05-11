package com.tonyxlab.scribbledash.di

import android.content.Context
import com.tonyxlab.scribbledash.presentation.screens.draw.DrawViewModel
import com.tonyxlab.scribbledash.presentation.screens.draw.handling.DrawUiState.RandomVectorData
import com.tonyxlab.scribbledash.presentation.screens.preview.PreviewViewModel
import com.tonyxlab.scribbledash.presentation.screens.stats.StatsViewModel
import com.tonyxlab.utils.getRawVectorPathData
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    // Provide the required function type for DrawViewModel
    single<(Context) -> RandomVectorData> {
        { context: Context ->
            val allVectors = getRawVectorPathData(context)
            if (allVectors.isEmpty()) {
                RandomVectorData(emptyList(), 0f, 0f)
            } else {
                val (_, vectorData) = allVectors.entries.random()
                val (paths, vpWidth, vpHeight) = vectorData
                RandomVectorData(paths, vpWidth, vpHeight)
            }
        }
    }

    viewModelOf( ::DrawViewModel)

    viewModelOf(::PreviewViewModel)
    viewModelOf(::StatsViewModel)
    /*
        viewModel { DrawViewModel() }
        viewModel { PreviewViewModel() }

No Compose Koin context setup, taking default. Use KoinContext(), KoinAndroidContext()
or KoinApplication() function to setup or create Koin context and avoid such message.


    single { androidContext() }  */
}


val randomVectorProviderModule = module {
    single<() -> RandomVectorData> {
        val context = get<Context>()
        val vectorDataMap = getRawVectorPathData(context)

        if (vectorDataMap.isEmpty()) {
            return@single {
                RandomVectorData(emptyList(), 0f, 0f)
            }
        }

        val randomEntry = vectorDataMap.entries.random()
        val (paths, viewportWidth, viewportHeight) = randomEntry.value

        return@single {
            RandomVectorData(paths, viewportWidth.toFloat(), viewportHeight.toFloat())
        }
    }
}




