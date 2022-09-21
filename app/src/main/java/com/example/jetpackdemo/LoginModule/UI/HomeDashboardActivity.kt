package com.example.jetpackdemo.LoginModule.UI

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.demo.kotlindemoapp.HomeMain.CarouselViewPager.CarouselTransformer
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.CameraGalleryDemo.ActivityResultLauncherDemoActivity
import com.example.jetpackdemo.CameraGalleryDemo.MultiplePermissionActivity
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardEntity
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardMenu
import com.example.jetpackdemo.LoginModule.Repository.DBDashboardMenuRepository
import com.example.jetpackdemo.LoginModule.Repository.DashboardRepository
import com.example.jetpackdemo.LoginModule.UI.Adapter.DashBoardAdapter
import com.example.jetpackdemo.LoginModule.UI.Adapter.DashBoardMenuAdapter
import com.example.jetpackdemo.LoginModule.ViewModel.DashboardViewModel
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.DashboardViewModelFactory
import com.example.jetpackdemo.Response
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.Utility.NetworkUtils
import com.example.jetpackdemo.Utility.showAlerDialog
import com.example.jetpackdemo.databinding.ActivityHomeDashboardBinding
import com.example.jetpackdemo.databinding.ContentHomeMainBinding
import com.example.jetpackdemo.webView.CommonWebViewActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import java.lang.Runnable

/****************************************************************************************************
 * Task For Async Task parallel :-- > we Call  UserConstant ans DynamicDashboard Api parallel
// https://www.youtube.com/watch?v=dTqOVsdj0pY&t=555s
//https://discuss.kotlinlang.org/t/how-to-measure-execution-time-of-an-aync-query-request-inside-kotlin-coroutines/23352

 ****************************************************************************************************/
class HomeDashboardActivity : BaseActivity() {

    //region Declaration
    lateinit var bindingRoot : ActivityHomeDashboardBinding
    lateinit var binding : ContentHomeMainBinding
    lateinit var layout: View
    lateinit var viewModel: DashboardViewModel

    lateinit var dashBoardAdapter: DashBoardAdapter
    lateinit var dashBoardMenuAdapter: DashBoardMenuAdapter

    lateinit var viewPager2 : ViewPager2
    lateinit var sliderHandler : Handler

    private var viewpager2Job: Job? = null


    // var sliderHandler = Handler(Looper.myLooper())
   //  var sliderRun : Runnable? = null

    //endregion

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

    //region Event
    override fun onPause() {
        super.onPause()

        //region commented
//        if (sliderRun != null) {
//
//            sliderHandler.removeCallbacks(sliderRun)
//        }
        //endregion
    }

    override fun onResume() {
        super.onResume()

        //region comment
//        if (sliderRun != null) {
//
//            sliderHandler.postDelayed(sliderRun,3000)
//        }
        //endregion
//
    }

    override fun onBackPressed() {
      //  super.onBackPressed()

      //  layout.showAlerDialog(this)


        showAlert("Exit","Do you want to exit!!"){ type ->

            when(type){
                "Y" -> {
                    toast("Logout Successfully...!!")
                    this@HomeDashboardActivity.finish()
                }
                "N" -> {
                    toast("Cancel logout")
                }

            }



        }
       // this.finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        dashBoardAdapter.cancelJob()
    }

    //endregion

    //region Init method
    private fun init(){

        var demoDatabase = DemoDatabase.getDatabase(applicationContext)
        var dashboardRepository = DashboardRepository(RetrofitHelper.retrofitLoginApi,demoDatabase)
        var viewModelFactory = DashboardViewModelFactory(dashboardRepository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(DashboardViewModel::class.java)


        sliderHandler = Handler(Looper.myLooper()!!)
        initData()


    }

    private fun initData(){

        dashBoardAdapter = DashBoardAdapter(ArrayList())
       binding.rvImgSlide.apply {

           setHasFixedSize(true)
           layoutManager = LinearLayoutManager(this@HomeDashboardActivity)
           adapter = dashBoardAdapter
       }

        dashBoardMenuAdapter = DashBoardMenuAdapter(this,ArrayList()){ it : DashboardMenu ->

            // Here we'll receive callback of
            // every recyclerview item click
            // Now, perform any action here.
            // for ex: navigate to different screen

            navigateDashboardMenu(it)


        }
        binding.rvProduct.apply {

            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HomeDashboardActivity)
            adapter = dashBoardMenuAdapter
        }
        binding.rvProduct.scheduleLayoutAnimation()

    }

    //endregion

    //region Api Response
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

                        dashBoardMenuAdapter.setData(DBDashboardMenuRepository.getDashBoardMenu())
                        binding.rvProduct.scheduleLayoutAnimation()

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
    //endregion

    //region Job {Couroutine }  for handling Viewpager2 to run continously
    fun initJOB() {

        cancelJob()

//        viewpager2Job =   lifecycleScope.launch(Dispatchers.Main){
//
//
//
//           var delayViewpage2 = delayViewpager2()
//
//
//            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
//
//        }

        //////////////

        viewpager2Job =   lifecycleScope.launch(Dispatchers.Main){

            val job = CoroutineScope(Dispatchers.IO).launch{
                delayViewpager2()
            }

            job.join()

            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)

        }




        // Note : For UI update we must to come the other thered To "Main Thread" Only
//        withContext(Dispatchers.Main){
//
//            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
//
//        }




    }

    suspend fun delayViewpager2(){
        delay((4*1000))
        Log.d("VIEWPAGER", "Coroutine viewPager Current Item position " + viewPager2.currentItem)


    }

    fun cancelJob() {
        viewpager2Job?.cancel()
    }
    //endregion

    //region ViewPager2 Setup
    private fun loadViewPager(listInsur: MutableList<DashboardEntity>){



        dashBoardAdapter = DashBoardAdapter(listInsur,viewPager2)
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
                   // Log.d("VIEWPAGER", "Coroutine viewPager page Selection triggered " + position)

//                    sliderHandler.removeCallbacks(sliderRun)
//                    sliderHandler.postDelayed(sliderRun, 3000)

                      initJOB()

                }


            }

        )

    }

    //endregion

    fun navigateDashboardMenu(menuEntity: DashboardMenu){


        when(menuEntity.id){

            "1" -> {
                startActivity(Intent(this, HomePageActivity::class.java))
            }
            "2" -> {
                startActivity(Intent(this, CommonWebViewActivity::class.java))
            }
            "3" -> {
                startActivity(Intent(this, ActivityResultLauncherDemoActivity::class.java))
            }
            "4" -> {
                startActivity(Intent(this, MultiplePermissionActivity::class.java))
            }
        }

    }


    //region Runnable Not in Used
    // for creating Runnable
    private val  sliderRun = Runnable {

        Log.d("VIEWPAGER", "viewPager Current Item position " + viewPager2.currentItem)

        viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)



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

    //endregion


}