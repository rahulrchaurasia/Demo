package com.example.jetpackdemo.KotlinDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.example.jetpackdemo.APIState
import com.example.jetpackdemo.Utility.Constant
import com.example.jetpackdemo.databinding.ActivityKotlinBasicDemoBinding

class KotlinBasicDemoActivity : AppCompatActivity() {

    /*
     enum : value Restricted
     sealed   : type restricted
     */
    private lateinit var binding: ActivityKotlinBasicDemoBinding

    val fname : String = "umesh"
    val name : String? = "Rudra"
    val lname : String? = null

    var arryName : ArrayList<String>? = null

    var list : MutableList<String> = ArrayList()    // mostly used : list is emply declare



    val mutableList = mutableListOf<String>()

    val arrayList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityKotlinBasicDemoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.apply {

            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            setTitle("KOTLIN DEMO")
        }

        // non-null asserted !!
        Log.d(Constant.TAG_KOTLIN, fname.length.toString())


        // safe call

        Log.d(Constant.TAG_KOTLIN, name?.length.toString())

        // safe call with let

        name?.let {
            Log.d(Constant.TAG_KOTLIN, "length is ${it.length} ")
        }

        // Elvis Operator

        val len = lname?.length ?: "1"
        Log.d(Constant.TAG_KOTLIN, len.toString())


      // var myData = arryName?.size

       // list.size

        Log.d(Constant.TAG_KOTLIN,"List size "+ list.size.toString())


        binding.btnEnum.setOnClickListener {

            val day  = Day.SUNDAY
            Log.d(Constant.TAG_Coroutine,"Enum DEmo "+ day)
            Log.d(Constant.TAG_Coroutine,"Enum DEmo "+ day.numb)


        }

        binding.btnGeneric.setOnClickListener {

            var name: Company<String> = Company<String>("GeeksforGeeks")
            var rank: Company<Int> = Company<Int>(12)
        }

        binding.btnSealed.setOnClickListener {

//            val redTile : Tile = Tile.Red(type = "White", price = 600)
//
//            val blueTile : Tile = Tile.Blue(price = 250)

           // Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color ${redTile.type } and  price ${redTile.price}" )



            val dataRed :Tile = Tile.Red(type = "whiteBrick", price = 500)


            val objApple = Fruit.Apple()
            val objMango = Fruit.Mango()
            val objBanana = Fruit.Banana()

            display(objApple)
            display(objMango)
            display(objBanana)

            val dataBlue : Tile = Tile.Blue(800,"blueDark")
            val dataWhite : Tile = Tile.White()

            displayTile(dataBlue)
            displayTile(dataWhite)

            displayTile(dataRed)
            //  Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color ${red.type } and  price ${red.price}" )


            // For generic

      //      val tileRed : TileState<String,String,Int>  = TileState.Red(type = "RED")     // call generic method
//
//            val tileBlue = TileState.Blue(type = "BLUE" , quantity = 20)
//
//           // val tileWhite = TileState.White
//
//            val tileBlack = TileState.Black(type = "BLACK", price = 800, quantity = 14)


            //displayGenericTile(TileState<String>)

            val tileRed : TileState1<String>  = TileState1.Red(type = "RED")     // call generic method

            val tileBlue : TileState1<String> = TileState1.Blue(type = "BLUE" , price = 930)

             //val tileWhite  : TileState1<String> = TileState1.White()

            val tileBlack : TileState1<String> = TileState1.Black(type = "BLACK")

            displayGenericTile(tileRed)
            displayGenericTile(tileBlue)
            displayGenericTile(tileBlack)



        }

    }


    fun display(fruit: Fruit)
    {
        when(fruit)
        {
            is Fruit.Apple ->
            Log.d(Constant.TAG_Coroutine," ${fruit.x} is good for iron"  )
            is Fruit.Mango ->
            Log.d(Constant.TAG_Coroutine," ${fruit.x} is delicious"  )
            is Fruit.Banana ->
                Log.d(Constant.TAG_Coroutine," ${fruit.x} is Healthy"  )
        }
    }

    fun displayTile(tile: Tile){
        when(tile){

            is Tile.Blue ->{
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color blue ${tile.type}  and ${tile.price}"  )
            }
            is Tile.Red -> {
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color red ${tile.type}" )
            }
            is Tile.Black -> {
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color Black ${tile.price}" )
            }
            is Tile.White -> {
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color white  NO parameter here }" )
            }
        }
    }



    fun displayGenericTile(tileState1 : TileState1<String>){

        when(tileState1){
            is TileState1.Black ->{
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color red ${tileState1.type}" )
            }
            is TileState1.Blue -> {
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color red ${tileState1.type}" )
            }
            is TileState1.Red -> {
                Log.d(Constant.TAG_Coroutine,"Sealed DEmo  Tile Color red ${tileState1.type}" )
            }
        }
    }
}


enum class Day(val numb : Int){

     SUNDAY(1),
    MONDAY(2),
    TUESDAY(3),
    WEDNESSDAY(4),
    THURSDAY(5),
    FRIDAY(6),
    SATURDAY(7)
}

//sealed class Tile (var mainType : String ,var mainPrice : Int ? = null ){
//
//    class Red(val type : String , val price: Int): Tile( mainType = type, mainPrice = price)
//
//    class Blue(val price : Int, mainType: String) : Tile(mainType = mainType)
//
//    class Black(val type : String, val price: Int, val quantity : Int, mainType: String): Tile(
//        mainType
//    )
//
//}

sealed class Tile ( ){

    class White(): Tile( )

    class Red(val type : String , val price: Int): Tile( )

    class Blue(val price : Int,val type: String) : Tile()

    class Black(val type : String, val price: Int, val quantity : Int, mainType: String): Tile()

}

sealed class TileState<T> (
                        val stype: T? = null,
                        val sprice: T? = null,
                        val squantity: T? = null,

) {

   // class White<T> : TileState<T>()

    class Red<T>(val type: T ) : TileState<T>(stype = type)

    class Blue<T>(val type: T, val quantity: T) : TileState<T>(stype = type , squantity = quantity)

    class Black<T>(val type: T, val price: T, val quantity: T) : TileState<T>()


}

sealed class TileState1<T> (
    val stype: T? = null,
    val sprice: Int? = null,
    ) {

   //  class White<T> : TileState<T>(stype = null)

    class Red<T>(val type: T ) : TileState1<T>(stype = type)

    class Blue<T>(val type: T, price: Int) : TileState1<T>(stype = type , sprice = price)

    class Black<T>(val type: T) : TileState1<T>(stype = type )


}
sealed class Fruit(val x : String)
{
    // Two subclasses of sealed class defined within
    class Apple : Fruit("Apple")
    class Mango : Fruit("Mango")
    class Banana : Fruit("Banana")
}

class Company<T> (text : T){
    var x = text
    init{
        //println(x)
        Log.d(Constant.TAG_Coroutine,"Generic DEmo  ${x}" )

    }
}

class Case<out T> {
    private val contents = mutableListOf<T>()
    fun produce(): T = contents.last()         // Producer: OK
  //  fun consume(item: T) = contents.add(item)  // Consumer: Error
}