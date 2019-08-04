package ru.n00ner.musicradioapp.di

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.provider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.n00ner.musicradioapp.datasource.RadioNetworkDatasource
import ru.n00ner.musicradioapp.datasource.network.RadioApiClient
import ru.n00ner.musicradioapp.domain.NetworkRepository
import ru.n00ner.musicradioapp.domain.usecase.GetCurrentTrackUseCase
import ru.n00ner.musicradioapp.domain.usecase.GetStreamUseCase
import ru.n00ner.musicradioapp.domain.usecase.GetTrackHistoryUseCase
import ru.n00ner.musicradioapp.domain.usecase.SendDislikeUseCase
import ru.n00ner.musicradioapp.presentation.AppPreferences
import ru.n00ner.musicradioapp.presentation.viewmodel.TrackHistoryViewModel
import ru.n00ner.musicradioapp.repository.NetworkRepositoryImpl
import ru.n00ner.musicradioapp.utils.BaseApp

class KodeinContainers {

    companion object {
        @ExperimentalCoroutinesApi
        val diBaseProject = Kodein {
            bind<RadioApiClient>() with provider { RadioApiClient() }
            bind<Context>() with provider {BaseApp.applicationContext()}
            bind<AppPreferences>() with provider { AppPreferences(instance()) }
            bind<RadioNetworkDatasource>() with provider { RadioNetworkDatasource(instance()) }
            bind<NetworkRepository>() with provider { NetworkRepositoryImpl(instance()) }
            bind<GetTrackHistoryUseCase>() with provider { GetTrackHistoryUseCase(instance()) }
            bind<GetStreamUseCase>() with provider { GetStreamUseCase(instance()) }
            bind<GetCurrentTrackUseCase>() with provider { GetCurrentTrackUseCase(instance(), instance()) }
            bind<SendDislikeUseCase>() with provider { SendDislikeUseCase(instance(), instance()) }
            bind<TrackHistoryViewModel>() with provider { TrackHistoryViewModel(instance()) }

        }
    }
}