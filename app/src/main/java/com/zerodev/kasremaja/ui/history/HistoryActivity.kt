package com.zerodev.kasremaja.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexfu.sqlitequerybuilder.api.SQLiteQueryBuilder
import com.zerodev.kasremaja.R
import com.zerodev.kasremaja.data.model.history.DataHistory
import com.zerodev.kasremaja.root.App
import com.zerodev.kasremaja.ui.dashboard.DashboardState
import com.zerodev.kasremaja.ui.dashboard.DashboardViewModel
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity :
    AppCompatActivity() {

    private val data: MutableList<DataHistory> by lazy {
        ArrayList()
    }

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }


    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter(data)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        viewModel.state.observe(this, { state ->
                state?.let {
                    when(it){
                        is HistoryState.Loading     -> {
                            shHistory.visibility = View.VISIBLE
                            swHistory.isRefreshing = false
                            rvData.visibility = View.GONE
                        }

                        is HistoryState.Result      -> {
                            shHistory.visibility = View.INVISIBLE
                            rvData.visibility = View.VISIBLE

                            data.clear()
                            data.addAll(it.data.data)
                            adapter.notifyDataSetChanged()
                        }
                        is HistoryState.Error       ->{
                            App.showToast.toastThrowable(it.error)
                        }
                    }
                }
        })

        rvData.also {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }

        swHistory.setOnRefreshListener {
            viewModel.getData()
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.getData()
    }
}