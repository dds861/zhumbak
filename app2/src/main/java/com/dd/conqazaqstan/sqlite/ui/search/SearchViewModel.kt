package com.dd.conqazaqstan.sqlite.ui.search

import com.dd.conqazaqstan.sqlite.base.BaseToolbarsViewModel
import com.dd.conqazaqstan.sqlite.model.ToolbarModel
import com.dd.conqazaqstan.sqlite.ui.main.MainToolbarsViewModel
import com.dd.domain.manager.ResourceManager
import com.dd.domain.model.MakalModel
import com.dd.domain.model.RequestMakalModel
import com.dd.domain.usecase.GetLocalMakalByCategoryIdUseCase
import com.dd.domain.usecase.GetLocalMakalByQueryTextUseCase
import java.util.*

class SearchViewModel(
        val resourceManager: ResourceManager,
        private val getLocalMakalByCategoryIdUseCase: GetLocalMakalByCategoryIdUseCase,
        private val getLocalMakalByQueryTextUseCase: GetLocalMakalByQueryTextUseCase
) : BaseToolbarsViewModel<SearchState, SearchNavigator.Navigation>() {
    /**
     * Default variables
     */
    override val initialViewState: SearchState = SearchState()
    /**
     * Custom variables
     */
    /**
     * Default functions
     */
    override fun onStartFirstTime(statePreloaded: Boolean) {
        checkDataState {
            executeUseCaseWithException(
                    {
                        val responseMakalModel = getLocalMakalByCategoryIdUseCase.execute(RequestMakalModel(categoryId = it.categoryId))
                        updateToNormalState {
                            copy(
                                    listMakals = responseMakalModel.list
                            )
                        }
                    },
                    { e ->
                        updateToErrorState(e)
                    })
        }
    }

    override fun onConfigureToolbars(mainToolbarsVm: MainToolbarsViewModel) {
        checkDataState { makalState ->
            mainToolbarsVm.onActionUpdateToolbar { toolbarModel ->
                toolbarModel.copy(
                        toolbarTitle = makalState.categoryTitle,
                        telegramButton = ToolbarModel.TelegramButton(
                                visibility = true
                        ),
                        searchButton = ToolbarModel.SearchButton(
                                visibility = true,
                                setOnQueryTextFocusChangeListener = {
                                    onActionFilterToolbarHintsByQueryText(it)
                                }
                        )
                )
            }
        }
    }

    /**
     * Custom functions
     */
    private fun onActionFilterToolbarHintsByQueryText(queryText: String) {
        //load results of hints by keyword
        checkDataState {
            executeUseCaseWithException(
                    {
                        val responseMakalModel = getLocalMakalByQueryTextUseCase.execute(RequestMakalModel(queryText = queryText))
                        updateToNormalState {
                            copy(
                                    listMakals = filterToolbarHintsByQueryText(queryText, responseMakalModel.list),
                                    adapterType = SearchState.AdapterType.HINT
                            )
                        }
                    },
                    { e ->
                        updateToErrorState(e)
                    })
        }
    }

    fun onActionFilterToolbarMakalsByQueryText(queryText: String) {
        mainToolbarsVm.onActionShowInterstitialAd()
        //update text on searchView
        mainToolbarsVm.onActionSearchViewText(queryText)
        //load makals to recyclerview
        checkDataState {
            executeUseCaseWithException(
                    {
                        val responseMakalModel = getLocalMakalByQueryTextUseCase.execute(RequestMakalModel(queryText = queryText))
                        updateToNormalState {
                            copy(
                                    listMakals = filterToolbarMakalsByQueryText(queryText, responseMakalModel.list),
//                                    listMakals = responseMakalModel.list,
                                    adapterType = SearchState.AdapterType.MAKALS
                            )
                        }
                    },
                    { e ->
                        updateToErrorState(e)
                    })
        }
    }

    private fun filterToolbarHintsByQueryText(queryText: String, list: List<MakalModel>): List<MakalModel> {
        if (queryText.isNotEmpty()) {
            val listMakalModels: MutableList<MakalModel> = mutableListOf()

            list.map { makalModel ->
                makalModel.address?.toLowerCase(Locale.ROOT)
                        ?.replace("\n", " ")
                        ?.replace(",", "")
                        ?.replace(".", "")
                        ?.replace(" - ", "")
                        ?.replace("- ", "")
                        ?.replace(" -", "")
                        ?.split(" ")
                        ?.filter { s -> s.contains(queryText) }
                        ?.map { MakalModel(address = it) }
            }.map { listMakals ->
                listMakals?.map {
                    listMakalModels.add(it)
                }
            }

            return listMakalModels.distinct()
                    .sortedByDescending { it.address }
                    .sortedBy { it.address?.length }
        } else {
            return listOf()
        }
    }

    private fun filterToolbarMakalsByQueryText(queryText: String, list: List<MakalModel>): List<MakalModel> {
        return if (queryText.isNotEmpty()) {
            val listMakalModels: MutableList<MakalModel> = mutableListOf()

            list.map { makalModel ->
                val addressText = makalModel.address
                val indexBeforeQueryText = addressText?.toLowerCase()?.indexOf(queryText)
                val address = indexBeforeQueryText?.minus(1)?.let {
                    makalModel.address?.get(it)
                }
                if (indexBeforeQueryText != null) {
                    if (indexBeforeQueryText < 0 || (indexBeforeQueryText >= 0 && address == ' ')) {
                        listMakalModels.add(makalModel)
                    }
                }
            }

            listMakalModels.distinct()
                    .sortedByDescending { it.address }
                    .sortedBy { it.address?.length }
        } else {
            listOf()
        }
    }
}