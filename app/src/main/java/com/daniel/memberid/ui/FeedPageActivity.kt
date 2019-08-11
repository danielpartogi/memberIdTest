package com.daniel.memberid.ui

import android.app.Activity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.memberid.R
import com.daniel.memberid.ui.adapter.FeedPageAdapter
import com.daniel.memberid.viewModel.FeedPageViewModel
import kotlinx.android.synthetic.main.content_feed_page.*
import android.content.Intent
import android.util.Log
import com.daniel.memberid.SharedPreference
import com.daniel.memberid.ui.adapter.FeedPagePagination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class FeedPageActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: FeedPageViewModel
    private var adapter: FeedPageAdapter? = null
    private var poin: Int? = 0
    private var typeArray: Array<String>? = emptyArray()
    private lateinit var linearLayoutManager: LinearLayoutManager
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_page)
        val toolbar: Toolbar = findViewById(R.id.feed_page_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)


        linearLayoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this).get(FeedPageViewModel::class.java)
        adapter = FeedPageAdapter()
        recycler_feed_page.layoutManager = linearLayoutManager
        recycler_feed_page.adapter = adapter

        viewModel.awardList.observe(this, Observer {
            if(it!=null){
                adapter?.setSurveys(it)
            }
        })

        viewModel.logout.observe(this, Observer {
            if(it){
                SharedPreference(this).logout()
                val int = Intent(this, WelcomePageActivity::class.java)
                startActivity(int)
                finish()
            }
        })

        viewModel.awardListFiltered.observe(this, Observer {
            if(it!=null){
                adapter?.setSurveys(it)
            }
        })



        viewModel.getAwards()

        recycler_feed_page.addOnScrollListener(object : FeedPagePagination(linearLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getMoreItem()
            }
        })

    }

    fun getMoreItem(){
        GlobalScope.launch(context = Dispatchers.Main) {
            val data = viewModel.addAward(typeArray?.toMutableList(), poin)
            Log.d("Simulation", "delay 2s")
            delay(2000)
            Log.d("Simulation", "end of simulation")
            adapter?.addData(data)
            isLoading = false
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.feed_page, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {

        R.id.action_settings -> {
            val i = Intent(this, FilterActivity::class.java)
                i.putExtra("poin", poin)
                i.putExtra("type", typeArray)
            startActivityForResult(i, 1)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {

            }
            R.id.Logout -> {
                viewModel.logout()
            }

        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== 1){
            if(resultCode == Activity.RESULT_OK){
                poin = data?.getIntExtra("poin", 10000)
                typeArray = data?.getStringArrayExtra("type")
                viewModel.setFilter(poin, typeArray?.toMutableList())
            }
        }
    }
}
