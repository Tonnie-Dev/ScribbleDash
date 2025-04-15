package com.tonyxlab.scribbledash.di

import com.tonyxlab.scribbledash.presentation.screens.draw.DrawViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { DrawViewModel() }
}
