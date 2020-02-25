package com.example.tittactoe

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private var cells = mutableMapOf<Int,String>()
    private var isX = true
    private var winner:String = ""
    private val totalCell = 9
    private lateinit var txtResult: TextView
    private val x = "X"
    private val o = "O"
    private var btns = arrayOfNulls<Button>(totalCell)
    private val combinations:Array<IntArray> = arrayOf(
        intArrayOf(0,1,2),
        intArrayOf(3,4,5),
        intArrayOf(6,7,8),
        intArrayOf(0,3,6),
        intArrayOf(1,4,7),
        intArrayOf(2,5,8),
        intArrayOf(0,4,8),
        intArrayOf(2,4,6))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtResult = findViewById(R.id.txtResult)
        for(i in 1..totalCell){
            var button=findViewById<Button>(resources.getIdentifier("bt$i","id",packageName))
            button.setOnClickListener(this)
            btns[i-1] = button
        }
    }
    override fun onClick(v: View?) {
        btSelected(v as Button)
    }
    private fun btSelected(button: Button){
        var index = 0
        when(button.id){
            R.id.bt1-> index=0
            R.id.bt2-> index=1
            R.id.bt3-> index=2
            R.id.bt4-> index=3
            R.id.bt5-> index=4
            R.id.bt6-> index=5
            R.id.bt7-> index=6
            R.id.bt8-> index=7
            R.id.bt9-> index=8
        }
        playGame(index,button)
        checkWinner()
        updateGame()
    }
    private fun checkWinner(){
        if(cells.isNotEmpty()){
            for (combina in combinations){
                var(a,b,c)=combina
                if(cells[a]!=null && cells[a]==cells[b] && cells[a]==cells[c]){
                    this.winner=cells[a].toString()
                }
            }
        }
    }
    private fun updateGame(){
        when{
            winner.isNotEmpty()->{
                txtResult.text = resources.getString(R.string.Ganador,winner)
                txtResult.setTextColor(Color.BLUE)
            }
            cells.size==totalCell->{
                txtResult.text = "Empate"
            }
            else->{
                txtResult.text = resources.getString(R.string.netx_player,if(isX)x else o)
            }
        }
    }
    private fun playGame(index:Int,button:Button){
        if(!winner.isNullOrEmpty()){
            Toast.makeText(this,"Juego Finalizado",Toast.LENGTH_LONG).show()
            return
        }
        when{
            isX->cells[index]=x
            else->cells[index]=o
        }
        button.text=cells[index]
        button.isEnabled=false
        isX=!isX
    }
    fun resetButton(){
        for(i in 1..totalCell){
            var button=btns[i-1]
            button?.text=""
            button?.isEnabled=true
        }
    }
    fun newGame(){
        cells= mutableMapOf()
        isX=true
        winner=""
        txtResult.text=resources.getString(R.string.netx_player,x)
        txtResult.setTextColor(Color.BLACK)
        resetButton()
    }
    fun reset(view:View){
        newGame()
        Log.d("NEW GAME",view.toString())
    }

}
