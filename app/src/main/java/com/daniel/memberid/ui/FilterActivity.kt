package com.daniel.memberid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daniel.memberid.R
import com.daniel.memberid.databinding.ActivityFilterBinding
import com.daniel.memberid.viewModel.FilterViewModel
import android.content.Intent
import android.app.Activity


class FilterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFilterBinding
    private lateinit var viewModel: FilterViewModel
    private var poin: Int? = 0
    private var typeArray: Array<String>? = emptyArray()

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        val toolbar: Toolbar = findViewById(R.id.filter_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_filter)
        viewModel = ViewModelProviders.of(this).get(FilterViewModel::class.java)

        if(intent.extras!=null){
            poin = intent.getIntExtra("poin", 10000)
            typeArray = intent.getStringArrayExtra("type")
            viewModel.setPoinAndType(poin,typeArray)
        }

        binding.viewModel = viewModel


        binding.seekBar.max = 100
        binding.seekBar.progress = 1
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {

                viewModel.setPoinChanged(i)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })

        binding.closeImage.setOnClickListener {
            onBackPressed()
        }


        viewModel.resetsPoin.observe(this, Observer {
            if(it && !viewModel.isClearFilter.value!!){
                binding.seekBar.progress = 1
                val data = Intent()
                data.putExtra("poin", 10000)
                data.putExtra("type", viewModel.currentTypeArrayString.toTypedArray())
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        })

        viewModel.filtered.observe(this, Observer {
           if(it && !viewModel.isClearFilter.value!!){
               val data = Intent()
               data.putExtra("poin", viewModel.currentPoin)
               data.putExtra("type", viewModel.currentTypeArrayString.toTypedArray())
               setResult(Activity.RESULT_OK, data)
               finish()
           }
        })
        viewModel.isClearFilter.observe(this, Observer {
            if(it){
                val data = Intent()
                data.putExtra("poin", 10000)
                data.putExtra("type", emptyList<String>().toTypedArray())
                setResult(Activity.RESULT_FIRST_USER, data)
                finish()
            }
        })

        viewModel.isPoinChanged.observe(this, Observer {
            if(it){
                binding.seekBar.progress = viewModel.currentPoin/10000
            }
        })

        viewModel.isClearType.observe(this, Observer {
            if(it){
                val data = Intent()
                data.putExtra("poin", viewModel.currentPoin)
                data.putExtra("type", emptyList<String>().toTypedArray())
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.filter, menu)
        return true
    }


}