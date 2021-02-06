package com.teamounce.ounce.review.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.teamounce.ounce.R
import com.teamounce.ounce.databinding.ActivitySearchBinding
import com.teamounce.ounce.review.adapter.ListFoodSearchAdapter
import com.teamounce.ounce.review.model.CatFood
import com.teamounce.ounce.review.viewmodel.SearchViewModel
import com.teamounce.ounce.util.StatusBarUtil

class SearchActivity : AppCompatActivity() {
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.apply {
            lifecycleOwner = this@SearchActivity
            searchViewModel = searchViewModel
        }
        StatusBarUtil.setStatusBar(this)

        val data = mutableListOf<CatFood>()
        addDummyData(data)

        val searchLinearAdapter = ListFoodSearchAdapter(data.toMutableList(), ListFoodSearchAdapter.TYPE_LINEAR)
        binding.rvReviewSearchLinear.apply {
            adapter = searchLinearAdapter
            setHasFixedSize(true)
        }

        setSearchTypeChangeListener(searchLinearAdapter, data)

        searchViewModel.isListSearch.observe(this, { isList ->
            if (isList) {
                binding.apply {
                    btnLayoutList.isActivated = true
                    btnLayoutGrid.isActivated = false
                }
            } else {
                binding.apply {
                    btnLayoutList.isActivated = false
                    btnLayoutGrid.isActivated = true
                }
            }
        })

        searchViewModel.isNetworkAccess.observe(this, { isConnected ->
            if(isConnected) {
                binding.apply {
                    clayoutSearch.visibility = View.VISIBLE
                    linearReviewSearchType.visibility = View.VISIBLE
                    clayoutDisconnected.visibility = View.GONE
                }
            } else {
                binding.apply {
                    clayoutSearch.visibility = View.GONE
                    linearReviewSearchType.visibility = View.GONE
                    clayoutDisconnected.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        registerNetworkCallback()
    }

    override fun onStop() {
        super.onStop()
        terminateNetworkCallback()
    }

    private val networkCallBack = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            searchViewModel.isNetworkConnected()
            Snackbar.make(binding.root, "네트워크가 연결되었습니다", Snackbar.LENGTH_SHORT).show()
        }

        override fun onLost(network: Network) {
            searchViewModel.isNetworkDisconnected()
            Snackbar.make(binding.root, "네트워크가 끊겼습니다", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallBack)
    }

    private fun terminateNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallBack)
    }

    private fun setSearchTypeChangeListener(
        searchLinearAdapter: ListFoodSearchAdapter,
        data: MutableList<CatFood>
    ) {
        binding.btnLayoutList.setOnClickListener {
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_LINEAR)
            binding.rvReviewSearchLinear.layoutManager = LinearLayoutManager(this)
            searchLinearAdapter.notifyDataSetChanged()
        }

        binding.btnLayoutGrid.setOnClickListener {
            searchLinearAdapter.datas.clear()
            searchLinearAdapter.datas = data.toMutableList()
            binding.rvReviewSearchLinear.adapter = searchLinearAdapter
            searchLinearAdapter.changeLayout(ListFoodSearchAdapter.TYPE_GRID)
            binding.rvReviewSearchLinear.layoutManager = GridLayoutManager(this, 3)
            searchLinearAdapter.notifyDataSetChanged()
        }
    }

    private fun addDummyData(data: MutableList<CatFood>) {
        data.apply {
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
            add(
                CatFood(
                    img_cat_food = "https://www.meijer.com/content/dam/meijer/product/0082/92/7450/22/0082927450225_1_A1C1_1200.png",
                    txt_company = "Brand",
                    txt_cat_food = "Cat Food Nyummy"
                )
            )
        }
    }

}