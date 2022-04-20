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
import java.lang.NumberFormatException

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
            R.id.button8->numberButtonClicked("8")
            R.id.button9->numberButtonClicked("9")
            R.id.buttonMinus->operatorButtonClicked("-")
            R.id.buttonPlus->operatorButtonClicked("+")
            R.id.buttonDivider->operatorButtonClicked("/")
            R.id.buttonModule->operatorButtonClicked("%")
            R.id.buttonMulti->operatorButtonClicked("X")
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
        resultTextView.text=calculateExpression()
    }
    fun clearButtonClicked(v:View){
        expressionTextView.text=""
        resultTextView.text=""
        hasOperator=false
        isOperator=false

    }
    fun historyButtonClicked(v:View){

    }
    fun reultButtonClicked(v:View){
        val expressionTexts=expressionTextView.text.split(" ")
        if(expressionTextView.text.isEmpty()||expressionTexts.size==1){
            return
        }
        if( expressionTexts.size!=3&&hasOperator){
            Toast.makeText(this,"아직 완성되지 않은 수식입니다",Toast.LENGTH_SHORT).show()
            return
        }
        if(expressionTexts[0].isNumber().not()||expressionTexts[2].isNumber().not()){
            Toast.makeText(this,"오류가 발생했습니다",Toast.LENGTH_SHORT).show()
            return
        }

        val exceptionText=expressionTextView.text.toString()
        val resultText=calculateExpression()

        resultTextView.text=""
        expressionTextView.text=resultText

        isOperator=false
        hasOperator =false


    }

    private  fun calculateExpression() :String{
        val expressionTexts=expressionTextView.text.split(" ")
        if(hasOperator.not()|| expressionTexts.size!=3){
            return  ""
        }else if(expressionTexts[0].isNumber().not()||expressionTexts[2].isNumber().not()){
            return  ""
        }
        val exp1=expressionTexts[0].toBigInteger()
        val exp2=expressionTexts[2].toBigInteger()
        val op=expressionTexts[1]

        return  when(op){
            "+"->(exp1+exp2).toString()
            "-"->(exp1-exp2).toString()
            "X"->(exp1*exp2).toString()
            "/"->(exp1/exp2).toString()
            "%"->(exp1%exp2).toString()
            else ->""

        }

    }

}

fun String.isNumber(): Boolean {
    return  try {
        this.toBigInteger()
          true
    }catch (e : NumberFormatException){
          false

    }
}