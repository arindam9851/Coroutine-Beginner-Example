package com.allcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val RESULT_1="Result#1"
    private val RESULT_2="Result#2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_click.setOnClickListener {
            CoroutineScope(IO).launch {
                fakeApiRequest()
            }

        }
    }

    private suspend fun setTestOnMainThread(input: String){
        withContext(Main){
            setNewText(input)
        }
    }

    private fun setNewText(input: String){
        val newText=txt_text.text.toString()+"\n$input"
        txt_text.setText(newText)
    }
    private suspend fun fakeApiRequest(){
        val result1=getResult1FromApi() // it wait until the result then set the result to text view.after completing completing this the 2nd fun call
        println("debug: $result1")
        setTestOnMainThread(result1)

        // if you want then pass $result1 in getResult2FromApi method
        val result2=getResult2FromApi()
        println("debug: $result2")
        setTestOnMainThread(result2)
    }
    private suspend fun getResult1FromApi() :String{
        logThread("getResult1FromApi")
        delay(1000)
        return RESULT_1
    }

    private suspend fun getResult2FromApi() :String{
        logThread("getResult2FromApi")
        delay(1000)
        return RESULT_2
    }
    private fun logThread(methodName: String){
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }
}