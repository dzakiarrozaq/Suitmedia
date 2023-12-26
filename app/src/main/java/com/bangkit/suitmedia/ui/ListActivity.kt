package com.bangkit.suitmedia.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.content.Intent
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bangkit.adapter.UserAdapter
import com.bangkit.suitmedia.MainActivity
import com.bangkit.suitmedia.R
import com.bangkit.suitmedia.api.ApiConfig
import com.bangkit.suitmedia.databinding.ActivityListBinding
import com.bangkit.suitmedia.response.DataItem
import com.bangkit.suitmedia.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityListBinding
    private lateinit var adapterUser: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.swipeRefreshLayout.setOnRefreshListener(this)

        adapterUser = UserAdapter()
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.adapter = adapterUser
        binding.rvUser.setHasFixedSize(true)
        adapterUser.setClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: DataItem) {
                val mainActivityIntent = Intent(this@ListActivity, MainActivity::class.java)
                mainActivityIntent.putExtra(MainActivity.SELECTED, "${user.firstName} ${user.lastName}")
                setResult(Activity.RESULT_OK, mainActivityIntent)
                finish()
            }
        })

        getAllUsers(false)
    }

    private fun getAllUsers(isRefresh: Boolean){
        isLoading = true
        if (!isRefresh) {
            binding.progressbar.visibility = View.VISIBLE}

        Handler().postDelayed({
            val params = HashMap<String, String>()
            params["page"] = page.toString()
            ApiConfig.getApiService().getUser(params).enqueue(object : Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if (response.isSuccessful) {
                        totalPage = response.body()?.totalPages!!
                        val listUsers = response.body()?.data
                        Log.d(activity3, "onResponse: $listUsers")
                        if(listUsers!!.isNotEmpty()){
                            adapterUser.setList(listUsers as ArrayList<DataItem>)
                        }
                        binding.progressbar.visibility = View.GONE
                        isLoading = false
                        binding.swipeRefreshLayout.isRefreshing =false
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                    Toast.makeText(this@ListActivity, "Connection Failed...", Toast.LENGTH_SHORT).show()
                    binding.progressbar.visibility = View.GONE
                    isLoading = false
                    binding.swipeRefreshLayout.isRefreshing =false
                }

            }) }, 3000)
    }

    override fun onRefresh() {
        adapterUser.clearUsers()
        page = 2
        getAllUsers(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private var activity3 ="Third Screen"
        private var isLoading = false
        private var page: Int = 1
        private var totalPage: Int = 1
    }
}

