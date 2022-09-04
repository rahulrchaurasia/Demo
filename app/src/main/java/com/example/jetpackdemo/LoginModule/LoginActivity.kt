package com.example.jetpackdemo.LoginModule

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdemo.APIState
import com.example.jetpackdemo.BaseActivity
import com.example.jetpackdemo.HomePage.HomePageActivity
import com.example.jetpackdemo.LoginModule.API.APIService
import com.example.jetpackdemo.LoginModule.DataModel.RequestEntity.LoginRequestEntity
import com.example.jetpackdemo.LoginModule.Repository.LoginRepository
import com.example.jetpackdemo.LoginModule.ViewModel.LoginViewModel
import com.example.jetpackdemo.LoginModule.ViewmodelFactory.LoginViewModelFactory
import com.example.jetpackdemo.MVVMDemo.UI.MVVMDemoActivity
import com.example.jetpackdemo.MVVMDemo.UI.MVVMGetConstantActivity
import com.example.jetpackdemo.R
import com.example.jetpackdemo.RetrofitHelper
import com.example.jetpackdemo.RoomDemo.Database.DemoDatabase
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var viewModel: LoginViewModel
    lateinit var viewParent : CoordinatorLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

         viewParent = binding.lyparent

        var demoDatabase = DemoDatabase.getDatabase(applicationContext)
        var loiginRepository = LoginRepository(RetrofitHelper.retrofitLoginApi,demoDatabase)
        var viewModelFactory = LoginViewModelFactory(loiginRepository)

        viewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)


        // For Flow here
       lifecycleScope.launchWhenStarted {

           viewModel.LoginStateFlow.collect{

               when(it){

                   is APIState.Loading -> {
                       showDialog()
                   }

                   is APIState.Success -> {
                       cancelDialog()
                      it.data?.let {

                          Log.d(Constant.TAG_Coroutine, it.MasterData.EmailID)

                          Log.d(Constant.TAG_Coroutine, it.MasterData.toString())

                          showSnackBar(viewParent, "Dear ${it.MasterData.FullName}, You Login Successfully!!")

                          startActivity(Intent(this@LoginActivity, HomePageActivity::class.java))
                      }




                   }
                   is APIState.Failure -> {

                       cancelDialog()

                       showSnackBar(viewParent,it.errorMessage?: Constant.ErrorMessage)
                       Log.d(Constant.TAG_Coroutine, it.errorMessage.toString())
                   }
                   is APIState.Empty -> {
                       cancelDialog()

                   }

               }
           }
       }


        // When WE Used Live Data , we have to observer

        viewModel.loginLiveData.observe( this,{

            when(it){

                is APIState.Loading -> {
                    showDialog()
                }

                is APIState.Success -> {
                    cancelDialog()
                    it.data?.let {

                        Log.d(Constant.TAG_Coroutine,"Using LiveData"+ it.MasterData.EmailID)

                        Log.d(Constant.TAG_Coroutine, "Using LiveData" +it.MasterData.toString())


                        showSnackBar(viewParent, "Dear ${it.MasterData.FullName}, You Login Successfully!!")
                    }
                    startActivity(Intent(this, HomePageActivity::class.java))


                }
                is APIState.Failure -> {

                    cancelDialog()

                    showSnackBar(viewParent,"Using LiveData" +it.errorMessage?: Constant.ErrorMessage)
                    Log.d(Constant.TAG_Coroutine,"Using LiveData" + it.errorMessage.toString())


                }
                is APIState.Empty -> {
                    cancelDialog()

                }

            }


        })

        binding.includeLogin.btnSignIn.setOnClickListener {

//


            if(validate()){

            var loginRequestEntity  =    LoginRequestEntity(
                                        AppID = "",
                                        AppPASSWORD = "",
                                        AppUSERID = "",
                                        DeviceId = "eb3b33f11a6c28b9",
                                        DeviceName	= "",
                                        DeviceOS	= "",
                                        EmailId	= "",
                                        FBAId	=	0,
                                        IpAdd	= "",
                                        IsChildLogin	= "",
                                        LastLog	= "",
                                        MobileNo	= "",
                                        OldPassword	= "",
                                        Password	=	binding.includeLogin.etPassword.text.toString(),
                                        TokenId	=	"",
                                        UserId	= 0,
                                        UserName	=	binding.includeLogin.etEmail.text.toString(),
                                        UserType	= "",
                                        VersionNo	= "",

                )

            /*************************************************************************************************/
                                    //  Flow Demo  //
            /*************************************************************************************************/

               viewModel.getLoginUsingFlow(loginRequestEntity)


            /************************************************************************************************************/
                                //  Flow with LiveData   // { Repository is called by flow and viewModel used Live data
                                                         //  so tha Observer is triggered at activity
            /************************************************************************************************************/

             // viewModel.getLoginUsingLiveData(loginRequestEntity)


            }
        }

    }

    fun validate() : Boolean {


        var blnCheck : Boolean = true

        if(binding.includeLogin.etEmail.text.isNullOrBlank()){

            Snackbar.make(viewParent, "Required Email ID!!", Snackbar.LENGTH_SHORT).show()
            blnCheck = false
        }else if(binding.includeLogin.etPassword.text.isNullOrEmpty()){

            Snackbar.make(viewParent, "Required Password!!", Snackbar.LENGTH_SHORT).show()
            blnCheck = false
        }


        return blnCheck
    }
}