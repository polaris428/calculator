package com.example.calculator

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val expressionTextView:TextView by lazy {
        findViewById(R.id.expressionTextView)
    }
    private val resultTextView:TextView by lazy {
        findViewById(R.id.resultTextView)
    }
    private  var isOperator=false
    private  var hasOperator=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun buttonClicked(v: View){
        when (v.id){
            R.id.button0-> numberButtonClicked("0")
            R.id.button1->numberButtonClicked("1")
            R.id.button2->numberButtonClicked("2")
            R.id.button3->numberButtonClicked("3")
            R.id.button4->numberButtonClicked("4")
            R.id.button5->numberButtonClicked("5")
            R.id.button6->numberButtonClicked("6")
            R.id.button7->numberButtonClicked("7")
            R.id.button8->numberButtonClicked("9")
            R.id.button9->numberButtonClicked("9")
            R.id.buttonMinus->operatorButtonClicked("-")
            R.id.buttonPlus->operatorButtonClicked("+")
            R.id.buttonDivider->operatorButtonClicked("/")
            R.id.buttonModule->operatorButtonClicked("%")
            R.id.buttonMulti->operatorButtonClicked("*")
        }

    }

    private  fun operatorButtonClicked(operator:String){
        if(expressionTextView.text.isEmpty()){
            return
        }
        when{
            isOperator->{
                val text =expressionTextView.text.toString()
                expressionTextView.text=text.dropLast(1)+operator

            }
            hasOperator->{
                Toast.makeText(this,"연산자는 한번만 쓸 수 있습니다",Toast.LENGTH_SHORT).show()
                return

            }else->{
                expressionTextView.append(" $operator")
            }
        }
        val ssb=SpannableStringBuilder(expressionTextView.text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ssb.setSpan(
                ForegroundColorSpan(getColor(R.color.green)),
                expressionTextView.text.length-1,expressionTextView.text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            expressionTextView.text=ssb

            isOperator=true
            hasOperator=true
        }

    }

    private  fun  numberButtonClicked(number: String){
        if(isOperator){
            expressionTextView.append(" ")
        }
        isOperator=false
        val  expressionText=  expressionTextView.text.split(" ")
        if(expressionText.isEmpty()&&expressionText.last().length>=15){
            Toast.makeText(this,"15글자 까지 입력이 가능합니다",Toast.LENGTH_SHORT).show()
            return
        }else if(number=="0" && expressionText.last().isEmpty()){
            Toast.makeText(this,"0은 제일 앞에 올 수 없습니다",Toast.LENGTH_SHORT).show()
            return
        }
        expressionTextView.append(number)
    }
    fun clearButtonClicked(v:View){

    }
    fun historyButtonClicked(v:View){

    }
    fun reultButtonClicked(v:View){

    }
}