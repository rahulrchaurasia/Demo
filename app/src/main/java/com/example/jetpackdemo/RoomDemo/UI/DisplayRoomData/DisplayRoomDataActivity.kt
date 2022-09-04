package com.example.jetpackdemo.RoomDemo.UI.DisplayRoomData

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.RoomDemo.UI.ViewModelRoom.RoomDemo1ViewModel
import com.example.jetpackdemo.RoomDemo.UI.ViewModelRoom.RoomDemo1ViewModelFactory

import com.example.jetpackdemo.databinding.ActivityDisplayRoomDataBinding

/**************************************************************************************************************

  Task : we have to used Animation for Loading RecyclerView

Requirement 1>:  in xml for recycler bind used
            android:layoutAnimation="@anim/layout_animation"

 2>   binding.rvDisplay.scheduleLayoutAnimation()   // after reloading the data we have to again shedule the Animation


 ************************************************************************************************************/

class DisplayRoomDataActivity : AppCompatActivity() {

    lateinit var binding:ActivityDisplayRoomDataBinding
    lateinit var displayRoomAdapter: DisplayRoomAdapter

    lateinit var viewModel: RoomDemo1ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDisplayRoomDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("Room View Data ")
        }

       // displayRoomModel = Model
        initData()

        var database = DemoDatabase.getDatabase(applicationContext)
       var viewModelFactory = RoomDemo1ViewModelFactory(database)
        viewModel = ViewModelProvider(this,viewModelFactory).get(RoomDemo1ViewModel::class.java)

        initData()


        binding.rvDisplay.addOnScrollListener( object  : RecyclerView.OnScrollListener(){

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0 && binding.fabButton.getVisibility() != View.VISIBLE) {
                    binding.fabButton.show()
                } else if (dy < 0 && binding.fabButton.getVisibility() == View.VISIBLE) {
                    binding.fabButton.hide()
                }
            }

        })


        binding.fabButton.setOnClickListener(){
            binding.fabButton.hide()
            binding.rvDisplay.postDelayed(Runnable {

                binding.rvDisplay.smoothScrollToPosition(0)
            }, 200)
        }
        viewModel.getContactList().observe(this, {

           // binding.rvDisplay.adapter = DisplayRoomAdapter(it)

            displayRoomAdapter.setData(it)
            binding.rvDisplay.scheduleLayoutAnimation()

            // region commented For set to last Postopn of RecyclerView


//            binding.rvDisplay.postDelayed(Runnable {
//
//                binding.rvDisplay.scrollToPosition(displayRoomAdapter.itemCount -1)
//
//            }, 1000)

            //endregion

        })
    }


    fun initData(){

        displayRoomAdapter = DisplayRoomAdapter(ArrayList())      // Set Data to Empty

        binding.rvDisplay.apply {

             setHasFixedSize(true)
            layoutManager=  LinearLayoutManager(this@DisplayRoomDataActivity)
            adapter = displayRoomAdapter

        }

    }
}