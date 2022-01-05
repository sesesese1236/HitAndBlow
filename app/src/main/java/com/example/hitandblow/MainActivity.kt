package com.example.hitandblow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //問題の配列
    var level : Array<Int> = arrayOf()
    var mondai : Array<Int> = arrayOf(0,1,2,3,4,5,6,7,8,9)
    //答えの配列
    var answer : MutableList<Button> = mutableListOf()
    var btns : MutableList<Button> = mutableListOf()
    //こたえの位置
    var answerPoint: Int = 0
    //時間の操作
    var startTime : Long =0L
    var endTime : Long = 0L
    var totalTime :Long = 0L
    //ヒットアンドブローのカウント
    var hit: Int = 0
    var blow: Int = 0
    var countTry:Int = 0
    //回数から判断して時間を判断してランキングを決める
    //一人で一つしかランキングを登録できない
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初期化
        answer = mutableListOf(row1,row2,row3)
        btns = mutableListOf(input0,input1,input2,input3,input4,input5,input6,input7,input8,input9)
        level = arrayOf(0,0,0)
        btnonOff(false)
        checkOnOff()
    }
    //初期化変数
    fun levelCreate(view:View){
        mondai.shuffle()
        for(i in level.indices){
            level[i] = mondai[i]
        }
        startTime = System.currentTimeMillis()
        for(i in answer.indices){
            answer[i].isEnabled = true
            answer[i].text = ""
            textTry.text= "Try:"
            textHit.text="Hit:"
            textBlow.text="Blow:"
            countTry = 0
            hit = 0
            blow = 0
        }
        btnonOff(true)
        checkOnOff()
    }
    //ボタンDisable
    fun btnonOff(bool:Boolean){
        for(i in btns){
            i.isEnabled = bool
        }
    }
    fun checkOnOff(){
        for(i in answer){
            if(i.text == ""){
                btnCheck.isEnabled = false
                break
            }else{
                btnCheck.isEnabled = true
            }
        }
    }
    //ゲーム終わりかどうかチェックファンクション
    fun checkWin(){
        if(hit == level.size){
            endTime = System.currentTimeMillis()
            totalTime = (endTime-startTime)/1000
            for(i in answer.indices){
                answer[i].isEnabled = false
            }
            textTry.text = "Try:"+countTry
            textBlow.text = "Time:"+totalTime+"Second"
            textHit.text = "GameClear"
        }
    }
    //ヒットかブローかチェックファンクション
    fun checkHitBlow(view:View){
        blow=0
        hit=0
        for(i in level.indices){
            for(j in answer.indices){
                if(i==j) {
                    if(level[i] == Integer.parseInt(answer[j].text as String)){
                        hit++
                        break
                    }
                }else{
                    if (level[i] == Integer.parseInt(answer[j].text as String)) {
                        blow++
                    }
                }
            }
        }
        countTry++
        textTry.text = "Try:"+countTry
        textHit.text = "Hit:"+hit
        textBlow.text = "Blow:"+blow
        checkWin()
    }
    //答えのらん選べるファンクション
    fun select(view: View){
        for(i in answer.indices){
            if(answer[i] == view){
                answerPoint = i
            }
        }
        selected()
    }
    //答えのらん押せなくする
    fun selected(){
        for(i in answer.indices){
            answer[i].isEnabled = i != answerPoint
        }
    }
    //答えのボタン押されるとき
    fun AnswerInput(view: View){
        val v = view as Button
        val text = (v.text as String)
        checkNumber(text)
        if(answerPoint >= answer.size-1){
            answerPoint = 0
        }else{
            answerPoint++
        }
        selected()
        checkOnOff()
    }
    fun checkNumber(text: String){
        for(i in answer){
            if(i != answer[answerPoint]){
                if (i.text == text) {
                    answer[answerPoint].text = text
                    i.text = ""
                }
            }else{
                answer[answerPoint].text = text
            }
        }
    }
}
