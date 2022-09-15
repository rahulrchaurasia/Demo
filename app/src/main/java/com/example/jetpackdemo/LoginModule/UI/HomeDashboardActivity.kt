package com.example.jetpackdemo.LoginModule.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.CarouselTransformer
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardEntity
import com.example.jetpackdemo.LoginModule.Repository.DashboardRepository
import com.example.jetpackdemo.LoginModule.Repository.LoginRepository
import com.example.jetpackdemo.LoginModule.ViewModel.DashboardViewModel
import com.example.jetpackdemo.LoginModule.ViewModel.LoginViewModel
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.DashboardViewModelFactory
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.LoginViewModelFactory
import com.example.jetpackdemo.R
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.Utility.NetworkUtils
import com.example.jetpackdemo.databinding.ActivityHomeDashboardBinding
import com.example.jetpackdemo.databinding.ContentHomeMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Runnable

/****************************************************************************************************
 * Task For Async Task parallel :-- > we Call  UserConstant ans DynamicDashboard Api parallel
// https://www.youtube.com/watch?v=dTqOVsdj0pY&t=555s
//https://discuss.kotlinlang.org/t/how-to-measure-execution-time-of-an-aync-query-request-inside-kotlin-coroutines/23352

 ****************************************************************************************************/
class HomeDashboardActivity : BaseActivity() {

    lateinit var bindingRoot : ActivityHomeDashboardBinding
    lateinit var binding : ContentHomeMainBinding
    lateinit var layout: View
    lateinit var viewModel: DashboardViewModel

    lateinit var dashBoardAdapter: DashBoardAdapter

    lateinit var viewPager2 : ViewPager2
    lateinit var sliderHandler : Handler

    private var viewpager2Job: Job? = null


    // var sliderHandler = Handler(Looper.myLooper())
   //  var sliderRun : Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRoot = ActivityHomeDashboardBinding.inflate(layoutInflater)
        setContentView(bindingRoot.root)

        layout = bindingRoot.root
        binding = bindingRoot.includedHomeMain
        viewPager2 =  binding.viewPager


        init()

        if(NetworkUtils.isNetworkAvailable(this)){

            viewModel.getParallelAPIForUser_Dasbboard(fbaID = "89158")

        }else{
            showSnackBar(layout,Constant.NetworkError)
        }

        //viewModel.getParallelAPIForUser_Dasbboard_ObservingBoth(fbaID = "89158")
        getDashboardResponse()
        getUserConstantResponse()



    }

    override fun onPause() {
        super.onPause()

//        if (sliderRun != null) {
//
//            sliderHandler.removeCallbacks(sliderRun)
//        }
    }

    override fun onResume() {
        super.onResume()
//        if (sliderRun != null) {
//
//            sliderHandler.postDelayed(sliderRun,3000)
//        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }




    private fun init(){

        var demoDatabase = DemoDatabase.getDatabase(applicationContext)
        var dashboardRepository = DashboardRepository(RetrofitHelper.retrofitLoginApi,demoDatabase)
        var viewModelFactory = DashboardViewModelFactory(dashboardRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DashboardViewModel::class.java)


        sliderHandler = Handler(Looper.myLooper()!!)
        initData()


    }

     fun initJOB() {

        cancelJob()

         viewpager2Job =   lifecycleScope.launch(Dispatchers.IO){

         delay((3*1000))
             Log.d("VIEWPAGER", "Coroutine viewPager Current Item position " + viewPager2.currentItem)

             viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)



         }


    }
    fun cancelJob() {
        viewpager2Job?.cancel()
    }

    private fun initData(){

        dashBoardAdapter = DashBoardAdapter(ArrayList())
       binding.rvImgSlide.apply {

           setHasFixedSize(true)
           layoutManager = LinearLayoutManager(this@HomeDashboardActivity)
           adapter = dashBoardAdapter
       }

    }

    // for creating Runnable
  private val  sliderRun = Runnable {

        Log.d("VIEWPAGER", "viewPager Current Item position " + viewPager2.currentItem)

        viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)



    }

    private fun getDashboardResponse(){

        viewModel.dashBoardDataLiveData.observe(this, {

            when(it){

                is Response.Loading ->{
                    showDialog()
                }

                is Response.Success -> {

                    cancelDialog()

                    it.data?.let {
                        Log.d(Constant.TAG_Coroutine +" Dasbboard :", it.toString())
                        loadViewPager(it.MasterData.Dashboard)
                       // dashBoardAdapter.setData(it.MasterData.Dashboard)
                        binding.rvImgSlide.scheduleLayoutAnimation()

                    }
                }

                is Response.Error -> {
                    cancelDialog()
                    Snackbar.make(layout,it.errorMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                }
            }
        })

    }

    private fun getUserConstantResponse(){

        viewModel.constantData.observe(this, {

            when(it){

                is Response.Loading ->{
                    //showDialog()
                }

                is Response.Success -> {

                   // cancelDialog()

                    it.data?.let {
                        Log.d(Constant.TAG_Coroutine + " UserConstant :", it.toString())


                    }
                }

                is Response.Error -> {
                  //  cancelDialog()
                    Snackbar.make(layout,it.errorMessage.toString(), Snackbar.LENGTH_SHORT).show()
                    Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                }
            }
        })

    }


    private fun loadViewPager(listInsur: List<DashboardEntity>){



        dashBoardAdapter = DashBoardAdapter(listInsur)
        viewPager2.adapter = dashBoardAdapter


        setupCarousel(listInsur)


        // binding.includedHomeMain.rvImgSlide.isNestedScrollingEnabled = false


    }

    private fun setupCarousel(listInsur: List<DashboardEntity>){

        viewPager2.offscreenPageLimit = 3
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewPager2.setPageTransformer(CarouselTransformer(this))




        viewPager2.registerOnPageChangeCallback(

            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

//                    sliderHandler.removeCallbacks(sliderRun)
//                    sliderHandler.postDelayed(sliderRun, 3000)

                      initJOB()

                }


            }

        )

    }

    private fun setupCarouselOLD(listInsur: List<DashboardEntity>){

        viewPager2.offscreenPageLimit = 3
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        viewPager2.setPageTransformer(CarouselTransformer(this))




        viewPager2.registerOnPageChangeCallback(

            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    sliderHandler.removeCallbacks(sliderRun)
                    sliderHandler.postDelayed(sliderRun, 3000)


                }


            }

        )

    }


    fun stopViewPager()  {

        if (sliderRun != null) {

                sliderHandler.removeCallbacks(sliderRun)

        }



    }

    fun getSliderImagePosition(position: Int)  {


        // Toast.makeText(requireContext(),"Pos"+position ,Toast.LENGTH_SHORT).show()

        viewPager2.setCurrentItem(position, true)


    }
    //getParallelAPIForUser_Dasbboard
}