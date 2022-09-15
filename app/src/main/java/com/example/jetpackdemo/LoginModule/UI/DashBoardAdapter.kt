package com.example.jetpackdemo.LoginModule.UI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackdemo.LoginModule.DataModel.model.DashboardEntity
import com.example.jetpackdemo.databinding.DashboardProdItemModelBinding
import com.example.jetpackdemo.databinding.SliderItemModelBinding

class DashBoardAdapter (private var listDashBoard: List<DashboardEntity>) :
 RecyclerView.Adapter<DashBoardAdapter.DasbboardHolder>(){

    //private lateinit var binding: DashboardProdItemModelBinding
    private lateinit var binding: SliderItemModelBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DasbboardHolder {

//        binding = DashboardProdItemModelBinding.inflate(
//            LayoutInflater.from(parent.context),parent,false)

        binding = SliderItemModelBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

        return DasbboardHolder(binding.root)

    }

    override fun onBindViewHolder(holder: DasbboardHolder, position: Int) {
       var dashboardEntity = listDashBoard[position]
        binding.apply {

            txtProductName.text = dashboardEntity.menuname
            txtProductDesc.text = dashboardEntity.description
           // imgIcon.
        }
    }

    override fun getItemCount() = listDashBoard.size


    class DasbboardHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    fun setData(listDashBoard: List<DashboardEntity>){

        this.listDashBoard = listDashBoard
        notifyDataSetChanged()
    }
}