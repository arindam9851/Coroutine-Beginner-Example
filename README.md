# Coroutine Beginner Example
**COROUTINE**

When you use coroutine you need to do work on background thread . Thats all about Coroutine. That RXJava , AsyncTask does. Coroutine is same thing is another option for threading in Android.

**MainActivity for coroutine**

**Practical Example of Co-Routine**

1> Making a request using Retrofit or Volly
2>Accessing the internal Databse on phone ROOM,SQL

**Why Use Co-Routine**

Imagine you need to make a request, once you have that resukt then you need to make a another request and so on. Its a sequestial request call. To do this we have RX java , Live data . But its very messy. But with co-routine its very easy.

**STEP 1:**
Use coroutine dependancy
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1"

![Screenshot](https://user-images.githubusercontent.com/8407230/118372619-3dd31200-b5d0-11eb-8a01-986cfcba6adb.png)



**Coroutine are not thread. They are like thread but they are not thread** 

![Screenshot1](https://user-images.githubusercontent.com/8407230/118372683-7ecb2680-b5d0-11eb-816d-9534b39cbed7.png)

**Many coroutine are operating inside One thread**
we use Delay(1000). this is a coroutine thing. You might be thinking is same as Thread.sleep(1000).
this is defferent. that i mention earlier many coroutine are operating in single thred.
**Delay(1000) -- it delay single coroutine not entire thread. it not interfare any other coroutine**
**Thread.sleep(1000)-- it sleep entire thread. it sleep all coroutine under that Thread**


CoroutineScope- is way to organise coroutine in group. Coroutine is like a job.So Coroutine job groupng a bunch of job togetger. CoroutineScope inherite coroutine context

Coroutine Scope 
**IO**- its input output for network or databse
**Main**- for main thread UI related
**Default**-- for heavy compition work like filter a large list then pick any elemant in defferent index

**launch**-- its for coroutine builder.

**withContext(Main)** -- switch the context of coroutine whatever mentions


Coroutine is thread independent, means network call in one thread and get result in another thread.

**Network TimeOut With Coroutine**
instead of call launch we use **withTimeoutOrNull** then pass the timeOut


**Coroutine Job**

**JobActivity for coroutine job example**

Recomened design patten for this JOb is MVVM

![Screenshot2](https://user-images.githubusercontent.com/8407230/118375908-66640780-b5e2-11eb-9308-58f0842988c1.png)



what happend in commonly. when you go to repository and make a request and some reason network get slow and you need to cancle that Job. in past it's very diffecult to do .. to do this there is lots of code and its a pain. and use of coroutine its very easy.

**job.complete()** --  complete the job with return TRUE or FALSE

**job.completeExceptionally()**--  complete the job with return exception 

**CompletableJob**--   gives you ability to complete the job on your own terms. means you decide when the job complete and when not. you don't let the coroutine decide when the job is complete 

**job.invokeOnCompletion**  -- its allow to either complete or cancel the job and update the UI accordingly once completing the job.whether the job is completed or cancel this code will be execute

If Job is cancel you can't reuse that job. you have to initialize new job
