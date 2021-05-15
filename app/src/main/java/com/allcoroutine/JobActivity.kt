package com.allcoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_job.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class JobActivity : AppCompatActivity() {
    private val PROGRESS_MAX=100
    private val PROGRESS_START=0
    private val JOB_TIME=4000 //ms
    private lateinit var job: CompletableJob
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job)
        job_button.setOnClickListener {
            if(!:: job.isInitialized){
                initJob()
            }
            job_progress_bar.startJobOrCancel(job)
        }

    }
    // This is extension function
    fun ProgressBar.startJobOrCancel(job: Job){
        if(this.progress>0){
            println("${job} is already active . Cancelling ")
            resetJob()
        }
        else{
            job_button.setText("Cancel job #1")
            // Its call totally independent coroutine with this particular job. it create a coroutine context independent to any other coroutine
            CoroutineScope(IO+job).launch {
                println("Coroutine ${this} is activated with this ${job}")
                for(i in PROGRESS_START..PROGRESS_MAX){
                    delay((JOB_TIME / PROGRESS_MAX).toLong())
                    this@startJobOrCancel.progress=i
                }
                updateJobCompleteTextView("Job is complete")
            }
            // It cancel this particular job.
//            job.cancel()
        }

    }

    private fun updateJobCompleteTextView(msg: String){
        GlobalScope.launch(Main){
            job_complete_text.setText(msg)
        }
    }

    private fun resetJob() {
        if(job.isActive || job.isCompleted){
            job.cancel(CancellationException("Resetting Exception"))
        }
        initJob()

    }

    fun initJob(){
        job_button.setText("Start Job #1")
        updateJobCompleteTextView("")
        job= Job()
        job.invokeOnCompletion {
            it?.message.let {
                var msg=it
                if (msg.isNullOrBlank()){
                    msg= "Unknown cancellation error"
                }
                println("${job} was canceled .. Reason $msg")
                showToast(msg)
            }
        }
        job_progress_bar.max=PROGRESS_MAX
        job_progress_bar.progress=PROGRESS_START
    }
    // Use Global scope because i want to call this function unber coroutine scope or job invokeCompletion or from any where
    fun showToast(msg:String){
        GlobalScope.launch(Main){
            Toast.makeText(this@JobActivity,msg,Toast.LENGTH_SHORT).show()

        }
    }
}